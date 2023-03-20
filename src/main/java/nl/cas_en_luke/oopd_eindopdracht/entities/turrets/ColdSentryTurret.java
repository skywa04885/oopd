package nl.cas_en_luke.oopd_eindopdracht.entities.turrets;

import com.github.hanyaeger.api.Coordinate2D;
import nl.cas_en_luke.oopd_eindopdracht.entities.Projectile;
import nl.cas_en_luke.oopd_eindopdracht.entities.Turret;
import nl.cas_en_luke.oopd_eindopdracht.entities.projectiles.CryoBombProjectile;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;
import nl.cas_en_luke.oopd_eindopdracht.timers.CoolDownTimer;

/**
 * The cold sentry turret.
 */
public class ColdSentryTurret extends Turret {
    public static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/turrets/sentry_cold_64.png";
    public static final double RANGE = 120.0;
    public static final double ANGULAR_SPEED = Math.PI * 2.0;
    public static final double SHOOTING_ANGLE_THRESHOLD = Math.PI / 100.0;
    public static final double PRICE = 500.0;
    public static final CoolDownTimer.Builder COOL_DOWN_TIMER_BUILDER = CoolDownTimer.newBuilder()
            .setImmediate(true)
            .setCoolDownTime(1.5);
    public static final Projectile.Builder<CryoBombProjectile.Builder> PROJECTILE_BUILDER =
            CryoBombProjectile.newBuilder();

    public static class Builder extends Turret.Builder<Builder> {
        public Builder(final GameScene gameScene) {
            super(RESOURCE, gameScene, RANGE, ANGULAR_SPEED, PRICE, PROJECTILE_BUILDER, COOL_DOWN_TIMER_BUILDER,
                    SHOOTING_ANGLE_THRESHOLD);
        }

        @Override
        public Turret build(final Coordinate2D position) {
            return new ColdSentryTurret(resource, position, gameScene, range, angularSpeed, shootingAngleThreshold,
                    price, projectileBuilder, coolDownBuilder.build());
        }
    }

    public static Builder newBuilder(final GameScene gameScene) {
        return new Builder(gameScene);
    }

    /**
     * Constructs a new turret.
     *
     * @param resource               the sprite resource.
     * @param initialLocation        the initial location.
     * @param gameScene              the game scene.
     * @param range                  the range the turret has.
     * @param angularSpeed           the angular speed of the turret.
     * @param shootingAngleThreshold once the angle differences reached this threshold, start shooting.
     * @param price                  the price of the turret.
     * @param projectileBuilder      the builder of the projectiles.
     * @param coolDownTimer          the cool-down timer for when we're shooting.
     */
    private ColdSentryTurret(final String resource, final Coordinate2D initialLocation, final GameScene gameScene,
                             final double range, final double angularSpeed, final double shootingAngleThreshold,
                             final double price, final Projectile.Builder<?> projectileBuilder,
                             final CoolDownTimer coolDownTimer) {
        super(resource, initialLocation, gameScene, range, angularSpeed, shootingAngleThreshold, price,
                projectileBuilder, coolDownTimer);
    }
}
