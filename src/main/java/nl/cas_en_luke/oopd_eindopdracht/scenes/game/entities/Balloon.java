package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.*;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.media.SoundClip;
import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import nl.cas_en_luke.oopd_eindopdracht.effects.DurationEffect;
import nl.cas_en_luke.oopd_eindopdracht.effects.InstantEffect;
import nl.cas_en_luke.oopd_eindopdracht.helpers.WeighedRandomCollection;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;
import nl.cas_en_luke.oopd_eindopdracht.timers.UpdateTimer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public abstract class Balloon extends DynamicSpriteEntity implements UpdateExposer, Collider {
    private static final String AAH_FUCK_SOUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/aaah_fuck.mp3";
    private static final String SMALL_EXPLOSION_SOUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/small_explosion.mp3";
    private static final String SHIT_SOUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/shit.mp3";

    private static final Logger LOGGER = Logger.getLogger(Balloon.class.getName());
    private static final double MAX_BEZIER_CURVE_OFFSET = 20.0;

    public static abstract class Builder<T extends Builder<T>> implements WeighedRandomCollection.IWeighedItem {
        public abstract Balloon build(final GameScene gameScene, final Trajectory trajectory);
    }

    private static Vector2D getRandomBezierCurveOffset() {
        return new Vector2D(Math.random() * MAX_BEZIER_CURVE_OFFSET, Math.random() * MAX_BEZIER_CURVE_OFFSET);
    }

    private final GameScene gameScene;
    private final Trajectory trajectory;
    private final LinkedList<DurationEffect> durationEffects;
    private final UpdateTimer updateTimer;
    private final Vector2D bezierCurveOffset;
    private final double damage;
    private double speed;
    private double health;
    private double t;

    protected Balloon(String resource, final GameScene gameScene, final Trajectory trajectory, final double damage, final double speed, final double health) {
        super(resource, trajectory.interpolateToCoordinate2D(0.0));
        setAnchorPoint(AnchorPoint.CENTER_CENTER);

        gameScene.getBalloons().add(this);

        durationEffects = new LinkedList<>();
        updateTimer = UpdateTimer.newBuilder().build();
        bezierCurveOffset = getRandomBezierCurveOffset();
        t = 0.0;

        this.gameScene = gameScene;
        this.trajectory = trajectory;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;

        // Pops the balloon if health is too low.
        if (this.health < 0.0) {
            pop();
        }
    }

    /**
     * Pops the balloon.
     */
    public void pop() {
        LOGGER.info("Balloon popped: " + this);

        // Plays the pop sound.
        final SoundClip popSoundClip = new SoundClip("nl/cas_en_luke/oopd_eindopdracht/audio/bloon_pop.mp3");
        popSoundClip.play();

        // Adds the balance to the game scene.
        gameScene.addBalance(damage * 0.5 + speed * 0.1 + health * 0.4);

        // Removes the balloon.
        remove();
    }

    /**
     * Adds all the given effects to the balloon.
     *
     * @param effects the effects to add.
     */
    public void addEffects(final List<Effect> effects) {
        for (final Effect effect : effects) {
            addEffect(effect);
        }
    }

    /**
     * Adds the given effect to the balloon.
     *
     * @param effect the effect to add.
     */
    public void addEffect(final Effect effect) {
        if (effect instanceof InstantEffect) {
            onInstantEffectAdded((InstantEffect) effect);
        } else if (effect instanceof DurationEffect) {
            onDurationEffectAdded((DurationEffect) effect);
        } else {
            LOGGER.severe("Received effect of non expected class: " + effect.getClass().getName());
        }
    }

    /**
     * Gets called once an instant effect has been added.
     *
     * @param instantEffect the added instant effect.
     */
    private void onInstantEffectAdded(final InstantEffect instantEffect) {
        LOGGER.info("New instant effect added to balloon: " + instantEffect);

        // Applies the instant effect.
        instantEffect.apply(this);
    }

    /**
     * Gets called once a duration effect has been added.
     *
     * @param durationEffect the added duration effect.
     */
    private void onDurationEffectAdded(final DurationEffect durationEffect) {
        LOGGER.info("New duration effect added to balloon: " + durationEffect);

        // Applies the duration effect.
        durationEffect.apply(this);

        // Adds the duration effect to the linked list.
        durationEffects.add(durationEffect);

        // Sorts the linked list based on the expiry time from nearby to distant. So we can cheaply
        //  check for which ones to remove.
        durationEffects.sort(Comparator.comparingDouble(DurationEffect::getExpiresAt));
    }

    /**
     * Gets called once the balloon has reached the target.
     */
    private void onTargetReach() {
        // Logs that the balloon has reached the target.
        LOGGER.info("Balloon has reached the target, balloon: " + this);

        // Updates the game scene.
        gameScene.setHealth(gameScene.getHealth() - damage);

        // Plays the sound effects.
        new SoundClip(SMALL_EXPLOSION_SOUND_RESOURCE).play();
        double random = Math.random();
        if (random < 0.3) {
            new SoundClip(AAH_FUCK_SOUND_RESOURCE).play();
        } else if (random < 0.6) {
            new SoundClip(SHIT_SOUND_RESOURCE).play();
        }

        // Removes the balloon.
        remove();
    }

    /**
     * Gets the current position vector.
     * @return the position vector.
     */
    private Vector2D getPosition() {
        final Coordinate2D coordinate2D = getAnchorLocation();
        return new Vector2D(coordinate2D.getX(), coordinate2D.getY());
    }

    /**
     * Sets the position to the given vector position.
     * @param positionVector the vector position.
     */
    private void setPosition(final Vector2D positionVector) {
        final Coordinate2D coordinate2D = new Coordinate2D(positionVector.getX(), positionVector.getY());
        setAnchorLocation(coordinate2D);
    }

    private void updatePosition() {
        // Computes the delta position, and converts it to a delta t. this needs to be done because the interpolation
        //  expects a number between 0 and 1.
        final double deltaPosition = updateTimer.getDeltaTime() * speed;
        final double deltaT = (1.0 / trajectory.getLength()) * deltaPosition;

        // Adds the delta t to the current t value (to move the balloon).
        t += deltaT;

        // Checks if the t passed 1, if so we've reached the end.
        if (t > 1.0) {
            onTargetReach();

            return;
        }

        // Gets the new position vector for the balloon, then adds the offsets.
        final Vector2D position = trajectory.interpolate(t)
                .add(bezierCurveOffset);

        // Sets the new position vector.
        setPosition(position);
    }

    private void updateEffects() {
        final LinkedList<DurationEffect> expiredDurationEffects = new LinkedList<>();

        // Finds all the expired effects.
        for (final DurationEffect durationEffect: durationEffects) {
            // Do nothing if the effect hasn't expired yet, actually, return because
            //  the other effects expire later.
            if (updateTimer.getLastUpdateTime() < durationEffect.getExpiresAt()) {
                return;
            }

            // Add the effect to the list of expired effects.
            expiredDurationEffects.add(durationEffect);
        }

        // Removes all the expired effects from the balloon (effect-wise).
        for (final DurationEffect expiredDurationEffect: expiredDurationEffects) {
            LOGGER.info("Effect expired: " + expiredDurationEffect);
            expiredDurationEffect.remove(this);
        }

        // Removes all the expired effects.
        durationEffects.removeAll(expiredDurationEffects);
    }

    @Override
    public void explicitUpdate(final long l) {
        updateTimer.update();

        updateEffects();
        updatePosition();
    }

    /**
     * Gets called once the balloon has been removed.
     */
    @Override
    public void notifyRemove() {
        super.notifyRemove();

        // Removes the balloon from the balloon list.
        gameScene.getBalloons().remove(this);
    }

    @Override
    public String toString() {
        return "Balloon{" +
                "gameScene=" + gameScene +
                ", effects=" + durationEffects +
                ", updateTimer=" + updateTimer +
                ", damage=" + damage +
                ", speed=" + speed +
                ", t=" + t +
                '}';
    }
}
