package nl.cas_en_luke.oopd_eindopdracht.timers;

public class CoolDownTimer {
    public static class Builder {
        private double timeScalar;
        private double coolDownTime;
        private boolean immediate;

        private Builder() {
            timeScalar = Math.pow(10, -3); // Defaults to seconds.
            coolDownTime = 0.0;
        }

        public Builder setTimeScalar(final double timeScalar) {
            this.timeScalar = timeScalar;

            return this;
        }

        public Builder setCoolDownTime(final double coolDownTime) {
            this.coolDownTime = coolDownTime;

            return this;
        }

        public Builder setImmediate(final boolean immediate) {
            this.immediate = immediate;

            return this;
        }

        public CoolDownTimer build() {
            Double lastUsageTime = null;

            // if the timer is not immediate, act like an usage has just happened.
            if (!immediate) {
                lastUsageTime = (double) System.currentTimeMillis() * timeScalar;
            }

            // Construct the cool-down timer.
            return new CoolDownTimer(timeScalar, coolDownTime, lastUsageTime);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final double timeScalar;
    private final double coolDownTime;
    private Double lastUsageTime;

    /**
     * Constructs a new cool-down timer.
     *
     * @param timeScalar the time scalar.
     * @param coolDownTime the time to cool down.
     */
    private CoolDownTimer(final double timeScalar, final double coolDownTime, final Double lastUsageTime) {
        this.timeScalar = timeScalar;
        this.coolDownTime = coolDownTime;
        this.lastUsageTime = lastUsageTime;
    }

    /**
     * Gets called when we want to perform an operation that has a cool-down.
     *
     * @return returns true when we can do the operation, or false when we're still in a col-down.
     */
    public boolean use() {
        // Gets the current time.
        final double currentTime = (double) System.currentTimeMillis() * timeScalar;

        // Returns false if not enough time has passed, or if the we've never had an usage.
        if (lastUsageTime != null && currentTime - lastUsageTime < coolDownTime) {
            return false;
        }

        // Sets the last usage to the current time.
        lastUsageTime = currentTime;

        // Returns true so the callee knows to perform the cooled-down operation.
        return true;
    }
}
