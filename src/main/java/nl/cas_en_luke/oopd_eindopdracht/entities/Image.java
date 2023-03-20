package nl.cas_en_luke.oopd_eindopdracht.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class Image extends SpriteEntity {
    protected Image(final String resource, final Coordinate2D position) {
        super(resource, position);
    }
}
