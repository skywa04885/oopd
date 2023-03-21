package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.projectiles;

import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.effects.duration.CryoDurationEffect;
import nl.cas_en_luke.oopd_eindopdracht.effects.instant.DamageInstantEffect;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.Projectile;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.Arrays;
import java.util.List;

public class CryoBombProjectile extends Projectile {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/projectiles/cryo_bomb_16.png";
    private static final List<Effect.Builder<?>> EFFECT_BUILDERS = Arrays.asList(
            DamageInstantEffect.newBuilder(0.5),
            CryoDurationEffect.newBuilder(5.0)
    );
    private static final double SPEED = 400.0;

    public static class Builder extends Projectile.Builder<Builder> {
        public Builder() {
            super(SPEED);
        }

        @Override
        public Projectile build(final Vector2D position, final double angle) {
            final Vector2D velocity = getVelocityVectorFromAngle(angle);
            return new CryoBombProjectile(velocity, position);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private CryoBombProjectile(final Vector2D velocity, final Vector2D position) {
        super(RESOURCE, EFFECT_BUILDERS, velocity, position);
    }
}
