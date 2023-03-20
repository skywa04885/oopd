package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.media.SoundClip;
import nl.cas_en_luke.oopd_eindopdracht.entities.Button;

public class StopButton extends Button {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/game/stop.png";
    private static final String DONT_RETRACT_SOUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/dont_retract.mp3";

    public StopButton(final Coordinate2D position) {
        super(RESOURCE, position);

        setAnchorPoint(AnchorPoint.BOTTOM_LEFT);
    }

    @Override
    protected void onPressed() {
        new SoundClip(DONT_RETRACT_SOUND_RESOURCE).play();
    }
}
