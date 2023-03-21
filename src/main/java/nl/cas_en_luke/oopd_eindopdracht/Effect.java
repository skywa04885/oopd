package nl.cas_en_luke.oopd_eindopdracht;

import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.Balloon;

public abstract class Effect {
    public static abstract class Builder<T extends Builder<T>> {
        public abstract Effect build();
    }

    public abstract void apply(final Balloon balloon);
}
