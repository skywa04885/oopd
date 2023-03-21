package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.impl.CustomFont;
import com.github.hanyaeger.api.entities.impl.DynamicTextEntity;
import javafx.scene.paint.Color;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;

public class InfoText extends DynamicTextEntity implements UpdateExposer {
    private static final String FONT_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/fonts/space_mission_medium.otf";

    private final GameScene gameScene;

    public InfoText(final Coordinate2D position, final GameScene gameScene) {
        super(position);

        this.gameScene = gameScene;

        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        setFont(new CustomFont(FONT_RESOURCE, 50.0));
        setStrokeColor(Color.WHITE);
        setStrokeWidth(2.0);
        setFill(Color.BLACK);
        setViewOrder(1.0);

        update();
    }

    private void update() {
        switch (gameScene.getState()) {
            case Finished -> {
                final double remainingSeconds = Math.round(gameScene.getNextLevelTime() - gameScene.getUpdateTimer()
                        .getLastUpdateTime());

                setVisible(true);
                setText("Next level in " + remainingSeconds + " seconds");
            }
            case Resting -> {
                final double remainingSeconds = Math.round(gameScene.getWave().getRestTimeFinished() - gameScene.getUpdateTimer()
                        .getLastUpdateTime());

                setVisible(true);
                setText("Next wave in " + remainingSeconds + " seconds");
            }
            default -> {
                setVisible(false);
            }
        }
    }

    @Override
    public void explicitUpdate(long l) {
        update();
    }
}
