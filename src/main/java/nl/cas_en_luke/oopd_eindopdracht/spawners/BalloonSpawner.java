package nl.cas_en_luke.oopd_eindopdracht.spawners;

import com.github.hanyaeger.api.entities.EntitySpawner;
import nl.cas_en_luke.oopd_eindopdracht.Wave;
import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;
import nl.cas_en_luke.oopd_eindopdracht.helpers.WeighedRandomCollection;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;

public class BalloonSpawner extends EntitySpawner {
    private final WeighedRandomCollection weighedRandomCollection;
    private final GameScene gameScene;

    public BalloonSpawner(final WeighedRandomCollection weighedRandomCollection, final GameScene gameScene, final Wave wave) {
        super(wave.getSpawnInterval());

        this.weighedRandomCollection = weighedRandomCollection;
        this.gameScene = gameScene;
    }

    @Override
    protected void spawnEntities() {
        final WeighedRandomCollection.IWeighedItem iWeighedItem = weighedRandomCollection.getRandomWeighedItem();
        if (!(iWeighedItem instanceof final Balloon.Builder<?> balloonBuilder)) {
            throw new RuntimeException("Weighed random collection contains something else than a balloon builder!");
        }

        final Balloon balloon = balloonBuilder.build(gameScene, gameScene.getLevel().getMap().pickRandomTrajectory());
        spawn(balloon);
    }
}
