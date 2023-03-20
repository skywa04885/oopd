package nl.cas_en_luke.oopd_eindopdracht;

import java.util.List;

public class Level {
    private final String name;
    private final Map map;
    private final List<Wave.Builder> waveBuilders;

    public Level(final String name, final Map map, final List<Wave.Builder> waveBuilders) {
        this.name = name;
        this.map = map;
        this.waveBuilders = waveBuilders;
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public List<Wave.Builder> getWaveBuilders() {
        return waveBuilders;
    }
}
