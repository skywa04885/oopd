package nl.cas_en_luke.oopd_eindopdracht.effects.duration;

import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.effects.DurationEffect;
import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;

public class CryoDurationEffect extends DurationEffect {
    public static class Builder extends DurationEffect.Builder<Builder> {
        private double slowdown;

        public Builder(final double duration) {
            super(duration);

            this.slowdown = 10.0;
        }

        public Builder setSlowdown(final double slowdown) {
            this.slowdown = slowdown;

            return this;
        }

        @Override
        public Effect build() {
            return new CryoDurationEffect(getExpiresAt(), slowdown);
        }
    }

    public static Builder newBuilder(final double duration) {
        return new Builder(duration);
    }

    private final double slowdown;
    private double removedSpeed;

    private CryoDurationEffect(final double expiresAt, final double slowdown) {
        super(expiresAt);

        this.slowdown = slowdown;
    }

    @Override
    public void apply(final Balloon balloon) {
        super.apply(balloon);

        if (balloon.getSpeed() < slowdown) {
            removedSpeed = balloon.getSpeed();
        } else {
            removedSpeed = slowdown;
        }

        balloon.setSpeed(balloon.getSpeed() - removedSpeed);
    }

    @Override
    public void remove(final Balloon balloon) {
        super.remove(balloon);

        balloon.setSpeed(balloon.getSpeed() + removedSpeed);
    }
}
