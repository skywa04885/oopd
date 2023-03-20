package nl.cas_en_luke.oopd_eindopdracht.maps;

import nl.cas_en_luke.oopd_eindopdracht.Map;
import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import nl.cas_en_luke.oopd_eindopdracht.trajectory.LinearSegmentTrajectory;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class SwampMap extends Map {
    public static final String BACKGROUND_IMAGE_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/maps/swamp/background.png";
    private static final String BACKGROUND_AUDIO_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/maps/swamp/background.mp3";
    public static final List<Trajectory> TRAJECTORIES = List.of(
            LinearSegmentTrajectory.newBuilder()
                    .addPoint(new Vector2D(106, 0))
                    .addPoint(new Vector2D(105, 24))
                    .addPoint(new Vector2D(108, 49))
                    .addPoint(new Vector2D(116, 84))
                    .addPoint(new Vector2D(132, 123))
                    .addPoint(new Vector2D(148, 143))
                    .addPoint(new Vector2D(181, 169))
                    .addPoint(new Vector2D(204, 206))
                    .addPoint(new Vector2D(233, 238))
                    .addPoint(new Vector2D(263, 265))
                    .addPoint(new Vector2D(286, 281))
                    .addPoint(new Vector2D(309, 352))
                    .addPoint(new Vector2D(313, 380))
                    .addPoint(new Vector2D(270, 464))
                    .addPoint(new Vector2D(207, 461))
                    .addPoint(new Vector2D(142, 465))
                    .addPoint(new Vector2D(89, 456))
                    .addPoint(new Vector2D(79, 380))
                    .addPoint(new Vector2D(77, 329))
                    .addPoint(new Vector2D(117, 323))
                    .addPoint(new Vector2D(167, 326))
                    .addPoint(new Vector2D(205, 298))
                    .addPoint(new Vector2D(238, 250))
                    .addPoint(new Vector2D(278, 204))
                    .addPoint(new Vector2D(323, 176))
                    .addPoint(new Vector2D(343, 193))
                    .addPoint(new Vector2D(370, 227))
                    .addPoint(new Vector2D(384, 252))
                    .addPoint(new Vector2D(415, 284))
                    .addPoint(new Vector2D(443, 314))
                    .addPoint(new Vector2D(390, 376))
                    .addPoint(new Vector2D(383, 423))
                    .addPoint(new Vector2D(384, 458))
                    .addPoint(new Vector2D(383, 500))
                    .build(),
            LinearSegmentTrajectory.newBuilder()
                    .addPoint(new Vector2D(440, 0))
                    .addPoint(new Vector2D(463, 52))
                    .addPoint(new Vector2D(493, 113))
                    .addPoint(new Vector2D(512, 150))
                    .addPoint(new Vector2D(560, 172))
                    .addPoint(new Vector2D(580, 206))
                    .addPoint(new Vector2D(584, 253))
                    .addPoint(new Vector2D(587, 305))
                    .addPoint(new Vector2D(578, 360))
                    .addPoint(new Vector2D(545, 388))
                    .addPoint(new Vector2D(525, 416))
                    .addPoint(new Vector2D(523, 438))
                    .addPoint(new Vector2D(554, 460))
                    .addPoint(new Vector2D(624, 458))
                    .addPoint(new Vector2D(689, 438))
                    .addPoint(new Vector2D(704, 389))
                    .addPoint(new Vector2D(699, 323))
                    .addPoint(new Vector2D(701, 241))
                    .addPoint(new Vector2D(699, 176))
                    .addPoint(new Vector2D(695, 110))
                    .addPoint(new Vector2D(700, 70))
                    .addPoint(new Vector2D(745, 61))
                    .addPoint(new Vector2D(799, 55))
                    .build()
    );

    public SwampMap() {
        super(BACKGROUND_IMAGE_RESOURCE, BACKGROUND_AUDIO_RESOURCE, TRAJECTORIES);
    }
}
