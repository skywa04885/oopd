package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.impl.CustomFont;
import com.github.hanyaeger.api.entities.impl.DynamicTextEntity;
import javafx.scene.paint.Color;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;

public class BalanceText extends DynamicTextEntity implements UpdateExposer {
    private static final String FONT_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/fonts/medium.ttf";

    private final GameScene gameScene;

    public BalanceText(final Coordinate2D position, final GameScene gameScene) {
        super(position);

        this.gameScene = gameScene;

        setAnchorPoint(AnchorPoint.TOP_RIGHT);
        setFont(new CustomFont(FONT_RESOURCE, 20.0));
        setFill(Color.WHITE);
        setViewOrder(1.0);

        update();
    }

    private void update() {
        final double balance = Math.round(gameScene.getBalance() * 100.0) / 100.0;
        setText("Balance: " + balance + "$");
    }

    @Override
    public void explicitUpdate(long l) {
        update();
    }
}
