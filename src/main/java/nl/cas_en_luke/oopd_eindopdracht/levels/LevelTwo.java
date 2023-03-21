package nl.cas_en_luke.oopd_eindopdracht.levels;

import nl.cas_en_luke.oopd_eindopdracht.Level;
import nl.cas_en_luke.oopd_eindopdracht.Map;
import nl.cas_en_luke.oopd_eindopdracht.Wave;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.balloons.*;
import nl.cas_en_luke.oopd_eindopdracht.maps.SwampMap;

import java.util.List;

public class LevelTwo extends Level {
    public static final String NAME = "Level 2";
    public static final Map MAP = new SwampMap();
    public static final List<Wave.Builder> WAVE_BUILDERS = List.of(
            Wave.newBuilder()
                    .setName("Wave one")
                    .setDuration(12.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .addBalloonBuilder(YellowBalloon.newBuilder())
                    .addBalloonBuilder(WhiteBalloon.newBuilder())
                    .setSpawnInterval(300),
            Wave.newBuilder()
                    .setName("Wave two")
                    .setDuration(16.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .addBalloonBuilder(YellowBalloon.newBuilder())
                    .addBalloonBuilder(WhiteBalloon.newBuilder())
                    .addBalloonBuilder(CamoBalloon.newBuilder())
                    .setSpawnInterval(200),
            Wave.newBuilder()
                    .setName("Wave three")
                    .setDuration(20.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .addBalloonBuilder(YellowBalloon.newBuilder())
                    .addBalloonBuilder(WhiteBalloon.newBuilder())
                    .addBalloonBuilder(CamoBalloon.newBuilder())
                    .setSpawnInterval(100),
            Wave.newBuilder()
                    .setName("Wave four")
                    .setDuration(20.0)
                    .setRestDuration(8.0)
                    .addBalloonBuilder(RedBalloon.newBuilder())
                    .addBalloonBuilder(GreenBalloon.newBuilder())
                    .addBalloonBuilder(BlueBalloon.newBuilder())
                    .addBalloonBuilder(YellowBalloon.newBuilder())
                    .addBalloonBuilder(WhiteBalloon.newBuilder())
                    .addBalloonBuilder(CamoBalloon.newBuilder())
                    .setSpawnInterval(50)
    );
    private static final double INITIAL_BALANCE = 2000.0;
    private static final double INITIAL_HEALTH = 100.0;

    public LevelTwo() {
        super(NAME, MAP, WAVE_BUILDERS, INITIAL_BALANCE, INITIAL_HEALTH);
    }
}
