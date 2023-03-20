package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turret_purchase_menu;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.CompositeEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.userinput.MouseDraggedListener;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;
import javafx.scene.Cursor;
import nl.cas_en_luke.oopd_eindopdracht.entities.Turret;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.TurretPurchaseMenu;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turret_purchase_menu.turret_purchase_button.TurretPurchaseButtonBackground;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turret_purchase_menu.turret_purchase_button.TurretPurchaseButtonForeground;

public class TurretPurchaseButton extends CompositeEntity implements MouseEnterListener, MouseExitListener, MouseDraggedListener {
    private static final String AS_YOU_WISH_COMMANDER_AUDIO_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/as_you_wish_commander.mp3";

    private final TurretPurchaseMenu turretPurchaseMenu;
    private final Turret.Builder<?> turretBuilder;

    private final TurretPurchaseButtonBackground background;
    private final TurretPurchaseButtonForeground foreground;

    public TurretPurchaseButton(final Coordinate2D position, final TurretPurchaseMenu turretPurchaseMenu,
                                final Turret.Builder<?> turretBuilder) {
        super(position);

        this.turretPurchaseMenu = turretPurchaseMenu;
        this.turretBuilder = turretBuilder;

        this.background = new TurretPurchaseButtonBackground(new Coordinate2D(0.0, 0.0));
        this.foreground = new TurretPurchaseButtonForeground(turretBuilder.getResource(),
                new Coordinate2D(49.0, 49.0));

        setAnchorPoint(AnchorPoint.TOP_LEFT);
        setViewOrder(1.0);
    }

    @Override
    protected void setupEntities() {
        addEntity(background);
        addEntity(foreground);
    }


    @Override
    public void onMouseEntered() {
        this.background.setBrightness(0.1);
        this.background.setRotate(10.0);
        setCursor(Cursor.HAND);
    }

    @Override
    public void onMouseExited() {
        this.background.setBrightness(0.0);
        this.background.setRotate(0.0);
        setCursor(Cursor.DEFAULT);
    }

    @Override
    public void onDragged(final Coordinate2D coordinate2D) {
        // Sets the foreground anchor.
        foreground.setAnchorLocation(coordinate2D);
    }

    @Override
    public void onDropped(final Coordinate2D coordinate2D) {
        // Resets the foreground anchor.
        foreground.setAnchorLocation(new Coordinate2D(49.0, 49.0));

        // Plays the sound.
        new SoundClip(AS_YOU_WISH_COMMANDER_AUDIO_RESOURCE).play();

        // Computes the turret position.
        final Coordinate2D turretPosition = coordinate2D.add(getLocationInScene());

        // Adds the turret.
        turretPurchaseMenu.getGameScene().addTurret(turretBuilder.build(turretPosition));
    }
}
