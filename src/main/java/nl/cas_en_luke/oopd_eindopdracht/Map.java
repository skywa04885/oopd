package nl.cas_en_luke.oopd_eindopdracht;

import java.util.List;

public abstract class Map {
    private final String backgroundImageResource;
    private final String backgroundAudioResource;
    private final List<Trajectory> trajectories;

    public Map(final String backgroundResource, final String backgroundAudioResource,
               final List<Trajectory> trajectories) {
        this.backgroundImageResource = backgroundResource;
        this.backgroundAudioResource = backgroundAudioResource;
        this.trajectories = trajectories;
    }

    public String getBackgroundImageResource() {
        return backgroundImageResource;
    }

    public String getBackgroundAudioResource() {
        return backgroundAudioResource;
    }

    public List<Trajectory> getTrajectories() {
        return trajectories;
    }

    public Trajectory pickRandomTrajectory() {
        final int index = (int) (Math.random() * trajectories.size());
        return trajectories.get(index);
    }
}
