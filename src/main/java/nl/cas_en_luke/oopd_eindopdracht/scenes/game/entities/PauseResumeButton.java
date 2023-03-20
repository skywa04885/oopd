package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import nl.cas_en_luke.oopd_eindopdracht.entities.Button;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;

public class PauseResumeButton extends Button {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/game/pause.png";

    private GameScene gameScene;

    public PauseResumeButton(final Coordinate2D position, final GameScene gameScene) {
        super(RESOURCE, position);

        this.gameScene = gameScene;

        setAnchorPoint(AnchorPoint.BOTTOM_LEFT);
    }

    @Override
    protected void onPressed() {
        if (gameScene.isActiveGWU()) {
            gameScene.pause();
        } else {
            gameScene.resume();
        }
    }
}
