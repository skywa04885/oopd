package nl.cas_en_luke.oopd_eindopdracht.entities.projectiles;

import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.effects.instant.DamageInstantEffect;
import nl.cas_en_luke.oopd_eindopdracht.entities.Projectile;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.Arrays;
import java.util.List;

public class CrushingBombProjectile extends Projectile {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/projectiles/crushing_bomb_16.png";
    private static final List<Effect.Builder<?>> EFFECT_BUILDERS = List.of(
            DamageInstantEffect.newBuilder(2.0)
    );
    private static final double SPEED = 200.0;

    public static class Builder extends Projectile.Builder<Builder> {
        public Builder() {
            super(SPEED);
        }

        @Override
        public Projectile build(final Vector2D position, final double angle) {
            final Vector2D velocity = getVelocityVectorFromAngle(angle);
            return new CrushingBombProjectile(velocity, position);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public CrushingBombProjectile(final Vector2D velocity, final Vector2D position) {
        super(RESOURCE, EFFECT_BUILDERS, velocity, position);
    }
}
