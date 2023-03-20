package nl.cas_en_luke.oopd_eindopdracht.effects.instant;

import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.effects.InstantEffect;
import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;

public class DamageInstantEffect extends InstantEffect {
    public static class Builder extends InstantEffect.Builder<Builder> {
        private double damage;

        public Builder(final double damage) {
            this.damage = damage;
        }

        public Builder setDamage(final double damage) {
            this.damage = damage;

            return this;
        }

        @Override
        public Effect build() {
            return new DamageInstantEffect(damage);
        }
    }

    public static Builder newBuilder(final double damage) {
        return new Builder(damage);
    }

    private final double damage;

    public DamageInstantEffect(final double damage) {
        this.damage = damage;
    }

    @Override
    public void apply(final Balloon balloon) {
        balloon.setHealth(balloon.getHealth() - damage);
    }

    @Override
    public String toString() {
        return "DamageEffect{" +
                "damage=" + damage +
                '}';
    }
}
