package nl.cas_en_luke.oopd_eindopdracht.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.StaticScene;
import nl.cas_en_luke.oopd_eindopdracht.Main;
import nl.cas_en_luke.oopd_eindopdracht.scenes.start.entities.PlayButton;
import nl.cas_en_luke.oopd_eindopdracht.scenes.start.entities.SubtitleImage;
import nl.cas_en_luke.oopd_eindopdracht.scenes.start.entities.TitleImage;

public class StartScene extends StaticScene {
    private static final String BACKGROUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/start/background.png";
    private static final String BACKGROUND_MUSIC_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/start/background.mp3";

    private final Main main;

    public StartScene(final Main main) {
        super();

        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    @Override
    public void setupScene() {
        setBackgroundImage(BACKGROUND_RESOURCE);
        setBackgroundAudio(BACKGROUND_MUSIC_RESOURCE);
    }

    @Override
    public void setupEntities() {
        // Creates the title.
        final Coordinate2D titleImagePosition = new Coordinate2D(40.0, 40.0);
        final TitleImage titleImage = new TitleImage(titleImagePosition);
        addEntity(titleImage);

        // Creates the subtitle.
        final Coordinate2D subtitleImagePosition = new Coordinate2D(40.0, 100.0);
        final SubtitleImage subtitleImage = new SubtitleImage(subtitleImagePosition);
        addEntity(subtitleImage);

        // Creates the play button.
        final Coordinate2D playButtonPosition = new Coordinate2D(getWidth() - 40.0, getHeight() - 40.0);
        final PlayButton playButton = new PlayButton(playButtonPosition, this);
        addEntity(playButton);
    }
}
