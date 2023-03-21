package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.timers.UpdateTimer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public abstract class Projectile extends DynamicSpriteEntity implements UpdateExposer, Collided, SceneBorderCrossingWatcher {
    public static abstract class Builder<T extends Builder> {
        private double speed;

        public Builder(final double speed) {
            this.speed = speed;
        }

        public T setSpeed(final double speed) {
            this.speed = speed;

            return (T) this;
        }

        /**
         * Gets the velocity vector based on the speed in the builder and the given angle from the positive x axis.
         *
         * @param angle the angle from the positive x asis.
         * @return the velocity vector that has been computed.
         */
        protected Vector2D getVelocityVectorFromAngle(final double angle) {
            return new Vector2D(
                    speed * Math.cos(angle),
                    speed * Math.sin(angle)
            );
        }

        public abstract Projectile build(final Vector2D position, final double angle);
    }

    private final List<Effect.Builder<?>> effectBuilders;
    private final UpdateTimer updateTimer;
    private final Vector2D velocity;
    private Vector2D position;

    protected Projectile(final String resource, final List<Effect.Builder<?>> effectBuilders, final Vector2D velocity,
                         final Vector2D position) {
        super(resource, new Coordinate2D(position.getX(), position.getY()));

        // Centers the image and sets the rotation speed.
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        setRotationSpeed(Math.min(velocity.getNorm(), 10.0));

        // Creates the update timer.
        this.updateTimer = UpdateTimer.newBuilder()
                .build();

        // Sets the instance variables.
        this.effectBuilders = effectBuilders;
        this.velocity = velocity;
        this.position = position;
    }

    public List<Effect.Builder<?>> getEffectBuilders() {
        return effectBuilders;
    }

    public List<Effect> buildEffects() {
        return effectBuilders.stream().map(Effect.Builder::build).toList();
    }

    @Override
    public void explicitUpdate(final long timestampLong) {
        updateTimer.update();

        // Performs the position update based on the velocity.
        position = position.add(velocity.scalarMultiply(updateTimer.getDeltaTime()));

        // Changes the anchor point based on the current position.
        setAnchorLocation(new Coordinate2D(position.getX(), position.getY()));
    }

    @Override
    public String toString() {
        return "Projectile{" +
                "effectBuilders=" + effectBuilders +
                ", updateTimer=" + updateTimer +
                ", velocity=" + velocity +
                ", position=" + position +
                '}';
    }

    @Override
    public void onCollision(List<Collider> colliders) {
        for (final Collider collider : colliders) {
            // Ignore all collisions unless they're with a balloon.
            if (!(collider instanceof final Balloon balloon)) {
                continue;
            }

            // Reinterprets the collider as the balloon.
            balloon.addEffects(buildEffects());

            // Since we've hit a balloon, remove the current projectile.
            remove();

            // Breaks since we've already collided.
            return;
        }
    }

    /**
     * Gets called once the projectile crossed the scene border.
     *
     * @param sceneBorder the scene border.
     */
    @Override
    public void notifyBoundaryCrossing(final SceneBorder sceneBorder) {
        remove();
    }
}
