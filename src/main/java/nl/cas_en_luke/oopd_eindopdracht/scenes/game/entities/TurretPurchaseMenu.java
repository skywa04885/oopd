package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.DynamicCompositeEntity;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turret_purchase_menu.TurretPurchaseButton;

import java.util.ArrayList;
import java.util.List;

public class TurretPurchaseMenu extends DynamicCompositeEntity {
    public static class Builder {
        private final ArrayList<Turret.Builder<?>> turretBuilders;

        private Builder() {
            this.turretBuilders = new ArrayList<>();
        }

        public Builder addTurretBuilder(final Turret.Builder<?> turretBuilder) {
            this.turretBuilders.add(turretBuilder);

            return this;
        }

        public TurretPurchaseMenu build(final GameScene gameScene, final Coordinate2D position) {
            final ArrayList<TurretPurchaseButton> turretPurchaseButtons = new ArrayList<>(turretBuilders.size());
            final TurretPurchaseMenu turretPurchaseMenu = new TurretPurchaseMenu(position, gameScene,
                    turretPurchaseButtons);

            for (int i = 0; i < turretBuilders.size(); ++i) {
                final Turret.Builder<?> turretBuilder = turretBuilders.get(i);
                final Coordinate2D coordinate2D = new Coordinate2D(100.0 * i, 0.0);

                turretPurchaseButtons.add(new TurretPurchaseButton(coordinate2D, turretPurchaseMenu, turretBuilder));
            }

            return turretPurchaseMenu;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final GameScene gameScene;
    private final List<TurretPurchaseButton> turretPurchaseButtons;

    private TurretPurchaseMenu(final Coordinate2D position, final GameScene gameScene,
                               final List<TurretPurchaseButton> turretPurchaseButtons) {
        super(position);

        this.gameScene = gameScene;
        this.turretPurchaseButtons = turretPurchaseButtons;

        setAnchorPoint(AnchorPoint.TOP_LEFT);
        setViewOrder(0.5);
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    @Override
    protected void setupEntities() {
        for (final TurretPurchaseButton turretPurchaseButton : turretPurchaseButtons) {
            addEntity(turretPurchaseButton);
        }
    }
}
