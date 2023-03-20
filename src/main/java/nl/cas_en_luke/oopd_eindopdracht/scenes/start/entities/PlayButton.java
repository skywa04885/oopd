package nl.cas_en_luke.oopd_eindopdracht.scenes.start.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import nl.cas_en_luke.oopd_eindopdracht.Main;
import nl.cas_en_luke.oopd_eindopdracht.entities.Button;
import nl.cas_en_luke.oopd_eindopdracht.scenes.StartScene;

public class PlayButton extends Button {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/start/play.png";

    private final StartScene startScene;

    public PlayButton(final Coordinate2D position, final StartScene startScene) {
        super(RESOURCE, position);

        this.startScene = startScene;

        setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);
    }

    @Override
    protected void onPressed() {
        startScene.getMain().setActiveScene(Main.GAME_SCENE_LEVEL_ONE_ID);
    }
}
