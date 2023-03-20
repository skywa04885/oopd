package nl.cas_en_luke.oopd_eindopdracht.scenes.victory.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import nl.cas_en_luke.oopd_eindopdracht.Main;
import nl.cas_en_luke.oopd_eindopdracht.entities.Button;
import nl.cas_en_luke.oopd_eindopdracht.scenes.StartScene;
import nl.cas_en_luke.oopd_eindopdracht.scenes.VictoryScene;

public class BackToStartButton extends Button {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/victory/back_to_start.png";

    private final VictoryScene victoryScene;

    public BackToStartButton(final Coordinate2D position, final VictoryScene victoryScene) {
        super(RESOURCE, position);

        this.victoryScene = victoryScene;

        setAnchorPoint(AnchorPoint.CENTER_CENTER);
    }

    @Override
    protected void onPressed() {
        victoryScene.getMain().setActiveScene(Main.START_SCENE_ID);
    }
}
