package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turret_purchase_menu.turret_purchase_button;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class TurretPurchaseButtonBackground extends SpriteEntity {
    private static final String BACKGROUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/scenes/game/buy_turret_bg_98.png";

    public TurretPurchaseButtonBackground(final Coordinate2D position) {
        super(BACKGROUND_RESOURCE, position);

        setAnchorPoint(AnchorPoint.TOP_LEFT);
    }
}
