package nl.cas_en_luke.oopd_eindopdracht.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.StaticScene;
import nl.cas_en_luke.oopd_eindopdracht.Main;
import nl.cas_en_luke.oopd_eindopdracht.scenes.victory.entities.BackToStartButton;
import nl.cas_en_luke.oopd_eindopdracht.scenes.victory.entities.SubtitleImage;
import nl.cas_en_luke.oopd_eindopdracht.scenes.victory.entities.TitleImage;

public class VictoryScene extends StaticScene {
    private static final String BACKGROUND_IMAGE_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/victory/background.png";
    private static final String BACKGROUND_AUDIO_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/victory/background.mp3";

    private final Main main;

    public VictoryScene(final Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    @Override
    public void setupScene() {
        setBackgroundImage(BACKGROUND_IMAGE_RESOURCE);
        setBackgroundAudio(BACKGROUND_AUDIO_RESOURCE);
    }

    @Override
    public void setupEntities() {
        addEntity(new TitleImage(new Coordinate2D(getWidth() / 2.0, 180.0)));
        addEntity(new SubtitleImage(new Coordinate2D(getWidth() / 2.0, 260.0)));
        addEntity(new BackToStartButton(new Coordinate2D(getWidth() / 2.0, 470.0), this));
    }
}
