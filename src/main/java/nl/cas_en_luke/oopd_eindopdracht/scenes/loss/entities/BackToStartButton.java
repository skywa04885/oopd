package nl.cas_en_luke.oopd_eindopdracht.scenes.loss.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import nl.cas_en_luke.oopd_eindopdracht.Main;
import nl.cas_en_luke.oopd_eindopdracht.entities.Button;
import nl.cas_en_luke.oopd_eindopdracht.scenes.LossScene;

public class BackToStartButton extends Button {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/loss/back_to_start.png";

    private final LossScene lossScene;

    public BackToStartButton(final Coordinate2D position, final LossScene victoryScene) {
        super(RESOURCE, position);

        this.lossScene = victoryScene;

        setAnchorPoint(AnchorPoint.CENTER_CENTER);
    }

    @Override
    protected void onPressed() {
        lossScene.getMain().setActiveScene(Main.START_SCENE_ID);
    }
}
