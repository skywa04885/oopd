package nl.cas_en_luke.oopd_eindopdracht.effects;

import nl.cas_en_luke.oopd_eindopdracht.Effect;
import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;

public abstract class DurationEffect extends Effect {
    public static abstract class Builder<T extends Effect.Builder<T>> extends Effect.Builder<T> {
        private double duration;

        public Builder(final double duration) {
            this.duration = duration;
        }

        public T setDuration(final double duration) {
            this.duration = duration;

            return (T) this;
        }

        public double getExpiresAt() {
            return (double) System.currentTimeMillis() / 1000.0 + duration;
        }

        public abstract Effect build();
    }

    private final double expiresAt;
    private boolean applied;

    protected DurationEffect(final double expiresAt) {
        this.expiresAt = expiresAt;
        this.applied = false;
    }

    public final double getExpiresAt() {
        return expiresAt;
    }

    @Override
    public void apply(final Balloon balloon) {
        if (applied) {
            throw new RuntimeException("Effect has already been applied!");
        }

        applied = true;
    }

    public void remove(final Balloon balloon) {
        if (!applied) {
            throw new RuntimeException("Effect has never been applied!");
        }

        applied = false;
    }

    @Override
    public String toString() {
        return "DurationEffect{" +
                "expiresAt=" + expiresAt +
                '}';
    }
}
