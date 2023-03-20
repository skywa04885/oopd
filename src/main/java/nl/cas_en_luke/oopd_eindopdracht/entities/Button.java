package nl.cas_en_luke.oopd_eindopdracht.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;

public abstract class Button extends SpriteEntity implements MouseButtonPressedListener, MouseEnterListener,
        MouseExitListener {
    protected Button(final String resource, final Coordinate2D position) {
        super(resource, position);

        setViewOrder(1.0);
    }

    @Override
    public void onMouseButtonPressed(final MouseButton mouseButton, final Coordinate2D coordinate2D) {
        if (mouseButton == MouseButton.PRIMARY) {
            onPressed();
        }
    }

    @Override
    public void onMouseEntered() {
        setCursor(Cursor.HAND);
        setBrightness(0.1);
        setRotate(4.0);
    }

    @Override
    public void onMouseExited() {
        setCursor(Cursor.DEFAULT);
        setBrightness(0.0);
        setRotate(0.0);
    }

    protected abstract void onPressed();
}
