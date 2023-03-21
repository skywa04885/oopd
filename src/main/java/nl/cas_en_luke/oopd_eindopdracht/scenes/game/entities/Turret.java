package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;
import nl.cas_en_luke.oopd_eindopdracht.timers.CoolDownTimer;
import nl.cas_en_luke.oopd_eindopdracht.timers.UpdateTimer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.Pair;

import java.util.logging.Logger;

public abstract class Turret extends DynamicSpriteEntity implements UpdateExposer {
    private static final Logger LOGGER = Logger.getLogger(Turret.class.getName());
    private static final double TURRET_BARREL_OFFSET = 15.0;

    public static abstract class Builder<T extends Builder<T>> {
        protected String resource;
        protected double range;
        protected double angularSpeed;
        protected double price;
        protected Projectile.Builder<?> projectileBuilder;
        protected CoolDownTimer.Builder coolDownBuilder;
        protected double shootingAngleThreshold;

        public Builder(final String resource, final double range, final double angularSpeed,
                       final double price, final Projectile.Builder<?> projectileBuilder,
                       final CoolDownTimer.Builder coolDownBuilder, final double shootingAngleThreshhold) {
            this.resource = resource;
            this.range = range;
            this.angularSpeed = angularSpeed;
            this.price = price;
            this.projectileBuilder = projectileBuilder;
            this.coolDownBuilder = coolDownBuilder;
            this.shootingAngleThreshold = shootingAngleThreshhold;
        }

        public T setResource(final String resource) {
            this.resource = resource;

            return (T) this;
        }

        public T setRange(final double range) {
            this.range = range;

            return (T) this;
        }

        public T setAngularSpeed(final double angularSpeed) {
            this.angularSpeed = angularSpeed;

            return (T) this;
        }

        public T setPrice(final double price) {
            this.price = price;

            return (T) this;
        }

        public T setProjectileBuilder(final Projectile.Builder<?> projectileBuilder) {
            this.projectileBuilder = projectileBuilder;

            return (T) this;
        }

        public T setCoolDownBuilder(final CoolDownTimer.Builder coolDownBuilder) {
            this.coolDownBuilder = coolDownBuilder;

            return (T) this;
        }

        public T setShootingAngleThreshold(final double shootingAngleThreshold) {
            this.shootingAngleThreshold = shootingAngleThreshold;

            return (T) this;
        }

        public double getRange() {
            return range;
        }

        public double getAngularSpeed() {
            return angularSpeed;
        }

        public double getPrice() {
            return price;
        }

        public Projectile.Builder<?> getProjectileBuilder() {
            return projectileBuilder;
        }

        public CoolDownTimer.Builder getCoolDownBuilder() {
            return coolDownBuilder;
        }

        public double getShootingAngleThreshold() {
            return shootingAngleThreshold;
        }

        public String getResource() {
            return resource;
        }

        public abstract Turret build(final GameScene gameScene, final Coordinate2D position);
    }

    private final GameScene gameScene;
    private final double range;
    private final double angularSpeed; // We're not using vector because this technically, is a scalar quantity.
    private final double shootingAngleThreshold;
    private final Projectile.Builder<?> projectileBuilder;
    private final UpdateTimer updateTimer;
    private final CoolDownTimer coolDownTimer;
    private double angle;

    /**
     * Constructs a new turret.
     *
     * @param resource               the sprite resource.
     * @param initialLocation        the initial location.
     * @param gameScene              the game scene.
     * @param range                  the range the turret has.
     * @param angularSpeed           the angular speed of the turret.
     * @param shootingAngleThreshold once the angle differences reached this threshold, start shooting.
     * @param projectileBuilder      the builder of the projectiles.
     * @param coolDownTimer          the cool-down timer for when we're shooting.
     */
    protected Turret(final String resource, final Coordinate2D initialLocation, final GameScene gameScene,
                     final double range, final double angularSpeed, final double shootingAngleThreshold,
                     final Projectile.Builder<?> projectileBuilder, final CoolDownTimer coolDownTimer) {
        super(resource, initialLocation);

        this.gameScene = gameScene;
        this.range = range;
        this.angularSpeed = angularSpeed;
        this.shootingAngleThreshold = shootingAngleThreshold;
        this.projectileBuilder = projectileBuilder;
        this.coolDownTimer = coolDownTimer;

        updateTimer = UpdateTimer.newBuilder().build();
        angle = 0.0;

        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        setViewOrder(1000.0);

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
     * Extremely inefficient way to check for nearest balloons! Would rather use a KDTree, however
     * god knows how the Yeager internals work.
     *
     * @return The nearest balloon, or null if not found.
     */
    private Balloon getNearestBalloon() {
        // Gets the position vector of the current turret.
        final Vector2D entityPosition = new Vector2D(getAnchorLocation().getX(), getAnchorLocation().getY());

        // Constructs the initial empty pair.
        Pair<Double, Balloon> nearest = null;

        // Loops over all entities in attempt to find the nearest.
        for (final Balloon balloon : gameScene.getBalloons()) {
            // Gets the position vector of the balloon.
            final Vector2D balloonPosition = new Vector2D(balloon.getAnchorLocation().getX(),
                    balloon.getAnchorLocation().getY());

            // Computes the distance to the balloon.
            final double distanceToBalloon = entityPosition.distance(balloonPosition);

            // Makes sure the distance is within the range.
            if (distanceToBalloon > range) {
                continue;
            }

            // Ignores any that are not nearby.
            if (nearest != null && distanceToBalloon > nearest.getFirst()) {
                continue;
            }

            // Sets the new nearest.
            nearest = new Pair<>(distanceToBalloon, balloon);
        }

        // Returns null if nothing found, otherwise the nearest.
        return nearest == null ? null : nearest.getSecond();
    }

    /**
     * Orients towards the given balloon, this is not instantaneous, we're using an interpolation.
     *
     * @param balloon the balloon to orient towards.
     * @return true if we can fire at the target.
     */
    private boolean orientTowardsBalloon(final Balloon balloon) {
        // Gets the current and balloon position.
        final Vector2D turretPosition = new Vector2D(getAnchorLocation().getX(), getAnchorLocation().getY());
        final Vector2D balloonPosition = new Vector2D(balloon.getAnchorLocation().getX(),
                balloon.getAnchorLocation().getY());

        // Calculates the position of the balloon relative to the turret.
        final Vector2D relativePositionOfBalloon = balloonPosition.subtract(turretPosition);

        // Gets the angle relative to the positive x-axis. We're using a little trick, namely:
        //  we're sort-off converting to polar coordinates, however, we're not including the radius.
        double angleToBalloon = Math.atan(relativePositionOfBalloon.getY() / relativePositionOfBalloon.getX());

        // Using the sign of the coordinates of the relative position, determine in which quadrant of the
        //  two-dimensional rectangular/ cartesian coordinate system we are.
        if (relativePositionOfBalloon.getX() > 0.0 && relativePositionOfBalloon.getY() > 0.0) {
            // >> First quadrant (+, +).

        } else if (relativePositionOfBalloon.getX() < 0.0 && relativePositionOfBalloon.getY() > 0.0) {
            // >> Second quadrant (-, +).

            angleToBalloon += Math.PI; // 180 degrees.
        } else if (relativePositionOfBalloon.getX() < 0.0 && relativePositionOfBalloon.getY() < 0.0) {
            // >> Third quadrant (-, -).

            angleToBalloon += Math.PI; // 180 degrees.
        } else if (relativePositionOfBalloon.getX() > 0.0 && relativePositionOfBalloon.getY() < 0.0) {
            // >> Fourth quadrant (+, -).

            angleToBalloon += 2.0 * Math.PI; // 360 degrees.
        }

        // Gets the difference in the current angle of the turret, and the needed angle we would need to
        //  be oriented towards the balloon.
        final double deltaAngle = angleToBalloon - angle;

        // Determines the angular velocity, and caps it, so it is limited to the angular speed.
        final double angularVelocity = Math.signum(deltaAngle) * Math.min(angularSpeed * 0.2, angularSpeed);

        // Rotates the turret with the given angular velocity, however we have to multiply it by the delta time,
        //  since we're not calling this function every second.
        angle += angularVelocity * updateTimer.getDeltaTime();
        setRotate(-Math.toDegrees(Math.PI + angle));  // Yeager (JavaFX) is using degrees? Lmao.

        // Returns true if our angle is lower than the shooting threshold.
        return Math.abs(deltaAngle) < shootingAngleThreshold;
    }

    /**
     * Fires the turret.
     */
    private void fire() {
        // Logs that we're firing a projectile.
        LOGGER.info("Firing new projectile towards angle: " + angle);

        // Calculates the barrel offset of the turret (so it looks like it comes out of the barrel).
        final Vector2D turretBarrelOffset = new Vector2D(Math.cos(Math.PI + angle),Math.sin(Math.PI + angle))
                .scalarMultiply(TURRET_BARREL_OFFSET).negate();

        // Calculates the position of the projectile and shoots it.
        final Vector2D projectilePosition = getPosition().add(turretBarrelOffset);
        gameScene.shootProjectile(projectileBuilder, projectilePosition, angle);
    }

    /**
     * Gets called by the engine each frame.
     *
     * @param l the timestamp (which we don't use).
     */
    @Override
    public void explicitUpdate(final long l) {
        // Updates the update timer, since the internal timing sucks ass.
        updateTimer.update();

        // Finds the most nearby balloon, and if not found, simply returns.
        final Balloon balloon = getNearestBalloon();
        if (balloon == null) {
            return;
        }

        // Since we have a nearest balloon, orient the turret towards it, and returns from the update if we've
        //  not yet reached the needed angle difference threshold to shoot.
        if (!orientTowardsBalloon(balloon)) {
            return;
        }

        // Attempts to fire a bullet, which only happens if we're not in a cool-down.
        if (coolDownTimer.use()) {
            fire();
        }
    }
}
