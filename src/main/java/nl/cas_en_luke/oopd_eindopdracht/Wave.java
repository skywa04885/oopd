package nl.cas_en_luke.oopd_eindopdracht;

import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;

import java.util.ArrayList;
import java.util.List;

public class Wave {
    public static class Builder {
        private String name;
        private double duration;
        private double restDuration;
        private int spawnInterval;
        private ArrayList<Balloon.Builder<?>> balloonBuilders;

        public Builder() {
            this.name = "Nameless";
            this.duration = 20.0;
            this.restDuration = 8.0;
            this.spawnInterval = 200;
            this.balloonBuilders = new ArrayList<>();
        }

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setDuration(double duration) {
            this.duration = duration;

            return this;
        }

        public Builder setRestDuration(double restDuration) {
            this.restDuration = restDuration;

            return this;
        }

        public Builder addBalloonBuilder(Balloon.Builder<?> balloonBuilder) {
            this.balloonBuilders.add(balloonBuilder);

            return this;
        }

        public Builder setSpawnInterval(int spawnInterval) {
            this.spawnInterval = spawnInterval;
            return this;
        }

        public Wave build() {
            final double currentTime = (double) System.currentTimeMillis() / 1000.0;
            final double restTimeFinished = currentTime + restDuration;
            final double spawnTimeFinished = restTimeFinished + duration;

            return new Wave(name, spawnInterval, balloonBuilders, restTimeFinished, spawnTimeFinished);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final String name;
    private final int spawnInterval;
    private final List<Balloon.Builder<?>> balloonBuilders;

    private final double restTimeFinished;
    private final double spawnTimeFinished;

    private Wave(final String name,  final int spawnInterval, final List<Balloon.Builder<?>> balloonBuilders,
                 final double restTimeFinished, final double spawnTimeFinished) {
        this.name = name;
        this.spawnInterval = spawnInterval;
        this.balloonBuilders = balloonBuilders;
        this.restTimeFinished = restTimeFinished;
        this.spawnTimeFinished = spawnTimeFinished;
    }

    public String getName() {
        return name;
    }

    public int getSpawnInterval() {
        return spawnInterval;
    }

    public List<Balloon.Builder<?>> getBalloonBuilders() {
        return balloonBuilders;
    }

    public double getRestTimeFinished() {
        return restTimeFinished;
    }

    public double getSpawnTimeFinished() {
        return spawnTimeFinished;
    }
}

