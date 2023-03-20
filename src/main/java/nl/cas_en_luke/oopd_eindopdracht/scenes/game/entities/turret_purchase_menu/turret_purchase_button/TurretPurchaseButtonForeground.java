package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turret_purchase_menu.turret_purchase_button;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class TurretPurchaseButtonForeground extends SpriteEntity {
    public TurretPurchaseButtonForeground(final String resource, final Coordinate2D position) {
        super(resource, position);

        setAnchorPoint(AnchorPoint.CENTER_CENTER);
    }
}
