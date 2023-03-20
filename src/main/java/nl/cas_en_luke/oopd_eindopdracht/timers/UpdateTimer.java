package nl.cas_en_luke.oopd_eindopdracht.timers;

public class UpdateTimer {
    public static class Builder {
        private double scalar;

        private Builder() {
            scalar = Math.pow(10, -3); // Defaults to seconds.
        }

        public Builder setScalar(final double scalar) {
            this.scalar = scalar;

            return this;
        }

        public UpdateTimer build() {
            return new UpdateTimer(scalar);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final double scalar;
    private double lastUpdateTime;
    private double deltaTime;

    private UpdateTimer(final double scalar) {
        this.scalar = scalar;

        lastUpdateTime = (double) System.currentTimeMillis() * scalar;
        deltaTime = 0.0; // Preventing anything from happening.
    }

    public double getLastUpdateTime() {
        return lastUpdateTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public UpdateTimer update() {
        final double time = (double) System.currentTimeMillis() * scalar;

        this.deltaTime = time - lastUpdateTime;
        this.lastUpdateTime = time;

        return this;
    }

    @Override
    public String toString() {
        return "UpdateTimer{" +
                "scalar=" + scalar +
                ", lastUpdateTime=" + lastUpdateTime +
                ", deltaTime=" + deltaTime +
                '}';
    }
}
