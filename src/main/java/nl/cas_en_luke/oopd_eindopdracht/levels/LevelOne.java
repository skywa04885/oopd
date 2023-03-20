package nl.cas_en_luke.oopd_eindopdracht.levels;

import nl.cas_en_luke.oopd_eindopdracht.Level;
import nl.cas_en_luke.oopd_eindopdracht.Map;
import nl.cas_en_luke.oopd_eindopdracht.Wave;
import nl.cas_en_luke.oopd_eindopdracht.entities.balloons.BlueBalloon;
import nl.cas_en_luke.oopd_eindopdracht.entities.balloons.GreenBalloon;
import nl.cas_en_luke.oopd_eindopdracht.entities.balloons.RedBalloon;
import nl.cas_en_luke.oopd_eindopdracht.maps.GrassFieldMap;

import java.util.List;

public class LevelOne extends Level {
    public static final String NAME = "Level 1";
    public static final Map MAP = new GrassFieldMap();
    public static final List<Wave.Builder> WAVE_BUILDERS = List.of(
            Wave.newBuilder()
                    .setName("Wave one")
                    .setDuration(12.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .setSpawnInterval(400),
            Wave.newBuilder()
                    .setName("Wave two")
                    .setDuration(16.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .setSpawnInterval(300),
            Wave.newBuilder()
                    .setName("Wave three")
                    .setDuration(20.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .setSpawnInterval(200),
            Wave.newBuilder()
                    .setName("Wave four")
                    .setDuration(20.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .setSpawnInterval(100)
    );

    public LevelOne() {
        super(NAME, MAP, WAVE_BUILDERS);
    }
}
