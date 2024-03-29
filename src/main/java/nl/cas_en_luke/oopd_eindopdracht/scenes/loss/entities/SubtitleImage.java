package nl.cas_en_luke.oopd_eindopdracht.scenes.loss.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import nl.cas_en_luke.oopd_eindopdracht.entities.Image;

public class SubtitleImage extends Image {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/loss/subtitle.png";

    public SubtitleImage(final Coordinate2D position) {
        super(RESOURCE, position);

        setAnchorPoint(AnchorPoint.CENTER_CENTER);
    }
}
