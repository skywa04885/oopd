package nl.cas_en_luke.oopd_eindopdracht.effects;

import nl.cas_en_luke.oopd_eindopdracht.Effect;

public abstract class InstantEffect extends Effect {
    public static abstract class Builder<T extends Effect.Builder<T>> extends Effect.Builder<T> {}

    @Override
    public String toString() {
        return "InstantEffect{}";
    }
}
