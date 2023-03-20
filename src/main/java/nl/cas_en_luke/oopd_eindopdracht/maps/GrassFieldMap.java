package nl.cas_en_luke.oopd_eindopdracht.maps;

import nl.cas_en_luke.oopd_eindopdracht.Map;
import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import nl.cas_en_luke.oopd_eindopdracht.trajectory.LinearSegmentTrajectory;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class GrassFieldMap extends Map {
    public static final String BACKGROUND_IMAGE_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/maps/grass_field/background.png";
    private static final String BACKGROUND_AUDIO_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/maps/grass_field/background.mp3";
    public static final List<Trajectory> TRAJECTORIES = List.of(
            LinearSegmentTrajectory.newBuilder()
                    .addPoint(new Vector2D(0, 111))
                    .addPoint(new Vector2D(41, 115))
                    .addPoint(new Vector2D(137, 110))
                    .addPoint(new Vector2D(154, 82))
                    .addPoint(new Vector2D(156, 43))
                    .addPoint(new Vector2D(154, 0))
                    .build()
    );

    public GrassFieldMap() {
        super(BACKGROUND_IMAGE_RESOURCE, BACKGROUND_AUDIO_RESOURCE, TRAJECTORIES);
    }
}
