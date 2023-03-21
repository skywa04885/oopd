package nl.cas_en_luke.oopd_eindopdracht;

import java.util.List;

public class Level {
    private final String name;
    private final Map map;
    private final List<Wave.Builder> waveBuilders;
    private final double initialBalance;
    private final double initialHealth;

    public Level(final String name, final Map map, final List<Wave.Builder> waveBuilders, final double initialBalance,
                 final double initialHealth) {
        this.name = name;
        this.map = map;
        this.waveBuilders = waveBuilders;
        this.initialBalance = initialBalance;
        this.initialHealth = initialHealth;
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

    public double getInitialBalance() {
        return initialBalance;
    }

    public double getInitialHealth() {
        return initialHealth;
    }
}
