package nl.cas_en_luke.oopd_eindopdracht.maps;

import nl.cas_en_luke.oopd_eindopdracht.Map;
import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import nl.cas_en_luke.oopd_eindopdracht.trajectory.LinearSegmentTrajectory;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class CornfieldMap extends Map {
    public static final String BACKGROUND_IMAGE_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/maps/cornfield/background.png";
    private static final String BACKGROUND_AUDIO_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/maps/cornfield/background.mp3";
    public static final List<Trajectory> TRAJECTORIES = List.of(
            LinearSegmentTrajectory.newBuilder()
                    .addPoint(new Vector2D(114, 3))
                    .addPoint(new Vector2D(117, 466))
                    .addPoint(new Vector2D(118, 484))
                    .addPoint(new Vector2D(142, 489))
                    .addPoint(new Vector2D(309, 492))
                    .addPoint(new Vector2D(329, 482))
                    .addPoint(new Vector2D(334, 405))
                    .addPoint(new Vector2D(333, 249))
                    .addPoint(new Vector2D(330, 219))
                    .addPoint(new Vector2D(347, 206))
                    .addPoint(new Vector2D(386, 206))
                    .addPoint(new Vector2D(482, 213))
                    .addPoint(new Vector2D(483, 236))
                    .addPoint(new Vector2D(479, 290))
                    .addPoint(new Vector2D(476, 339))
                    .addPoint(new Vector2D(479, 359))
                    .addPoint(new Vector2D(500, 366))
                    .addPoint(new Vector2D(527, 365))
                    .addPoint(new Vector2D(639, 367))
                    .addPoint(new Vector2D(688, 369))
                    .addPoint(new Vector2D(697, 354))
                    .addPoint(new Vector2D(702, 330))
                    .addPoint(new Vector2D(699, 172))
                    .addPoint(new Vector2D(700, 85))
                    .addPoint(new Vector2D(699, 1))
                    .build()
    );

    public CornfieldMap() {
        super(BACKGROUND_IMAGE_RESOURCE, BACKGROUND_AUDIO_RESOURCE, TRAJECTORIES);
    }
}
