package nl.cas_en_luke.oopd_eindopdracht.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.EntitySpawnerContainer;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import nl.cas_en_luke.oopd_eindopdracht.Level;
import nl.cas_en_luke.oopd_eindopdracht.Main;
import nl.cas_en_luke.oopd_eindopdracht.Wave;
import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;
import nl.cas_en_luke.oopd_eindopdracht.entities.Projectile;
import nl.cas_en_luke.oopd_eindopdracht.entities.Turret;
import nl.cas_en_luke.oopd_eindopdracht.entities.turrets.BoomSentryTurret;
import nl.cas_en_luke.oopd_eindopdracht.entities.turrets.ColdSentryTurret;
import nl.cas_en_luke.oopd_eindopdracht.entities.turrets.CrushingSentryTurret;
import nl.cas_en_luke.oopd_eindopdracht.helpers.WeighedRandomCollection;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.TurretPurchaseMenu;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.HealthText;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.PauseResumeButton;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.StopButton;
import nl.cas_en_luke.oopd_eindopdracht.spawners.BalloonSpawner;
import nl.cas_en_luke.oopd_eindopdracht.timers.UpdateTimer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

public class GameScene extends DynamicScene implements KeyListener, EntitySpawnerContainer, UpdateExposer {
    public enum State {
        Resting,
        Spawning,
        Waiting,
        Finished,
    }

    private static final String OBLITERATE_THE_NAZIS_SOUND_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/obliterate_the_nazis_for_your_motherland.mp3";
    private static final Logger LOGGER = Logger.getLogger(GameScene.class.getName());

    private final Main main;
    private final Level level;
    private final int nextSceneId;
    private final TurretPurchaseMenu.Builder turretPurchaseMenuBuilder;
    private final UpdateTimer updateTimer;
    private Iterator<Wave.Builder> waveBuilderIterator;
    private LinkedList<Balloon> balloons;
    private double health;
    private double nextLevelTime;
    private Wave wave;
    private State state;
    private BalloonSpawner balloonSpawner;

    public GameScene(final Main main, final Level level, final int nextSceneId) {
        this.main = main;
        this.level = level;
        this.nextSceneId = nextSceneId;

        turretPurchaseMenuBuilder = TurretPurchaseMenu.newBuilder(this)
                .addTurretBuilder(BoomSentryTurret.newBuilder(this))
                .addTurretBuilder(ColdSentryTurret.newBuilder(this))
                .addTurretBuilder(CrushingSentryTurret.newBuilder(this));

        this.updateTimer = UpdateTimer.newBuilder().build();
    }

    public Level getLevel() {
        return this.level;
    }

    public void setHealth(final double health) {
        System.out.println(health);
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public LinkedList<Balloon> getBalloons() {
        return balloons;
    }

    public void addProjectile(final Projectile projectile) {
        addEntity(projectile);

    }

    public void addTurret(final Turret turret) {
        addEntity(turret);
    }

    @Override
    public void setupScene() {
        // Sets the instance variables.
        this.balloons = new LinkedList<>();
        this.health = 100.0;
        this.waveBuilderIterator = level.getWaveBuilders().iterator();
        this.wave = waveBuilderIterator.next().build();
        this.state = State.Resting;

        // Sets the background image and audio.
        setBackgroundImage(level.getMap().getBackgroundImageResource(), true);
        setBackgroundAudio(level.getMap().getBackgroundAudioResource());
        setBackgroundAudioVolume(0.8);

        // Plays the initial sound.
        new SoundClip(OBLITERATE_THE_NAZIS_SOUND_RESOURCE).play();
    }

    @Override
    public void setupEntities() {
        addEntity(turretPurchaseMenuBuilder.build(new Coordinate2D(10, 10.0)));

        final Coordinate2D stopButtonPosition = new Coordinate2D(10.0, getHeight() - 10.0);
        addEntity(new StopButton(stopButtonPosition));

        final Coordinate2D pauseResumeButtonPosition = new Coordinate2D(140.0, getHeight() - 10.0);
        addEntity(new PauseResumeButton(pauseResumeButtonPosition, this));

        final Coordinate2D healthTextPosition = new Coordinate2D(getWidth() - 10.0, 10.0);
        final HealthText healthText = new HealthText(healthTextPosition, this);
        addEntity(healthText);
    }


    @Override
    public void onPressedKeysChange(final Set<KeyCode> set) {
        if (set.contains(KeyCode.D)) {
        }
    }

    @Override
    public void setupEntitySpawners() {

    }

    private void explicitUpdateResting() {
        if (wave.getRestTimeFinished() > updateTimer.getLastUpdateTime()) {
            return;
        }

        // Logs.
        LOGGER.info("Finished resting, entering spawning mode.");

        new SoundClip("nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/commander_there_is_a_new_wave_incoming.mp3").play();

        final WeighedRandomCollection weighedRandomCollection = new WeighedRandomCollection();
        for (final Balloon.Builder<?> balloonBuilder : wave.getBalloonBuilders()) {
            weighedRandomCollection.add(balloonBuilder);
        }

        balloonSpawner = new BalloonSpawner(weighedRandomCollection, this, wave);
        addEntitySpawner(balloonSpawner);

        state = State.Spawning;
    }

    private void explicitUpdateSpawning() {
        // Makes sure it's time to move on.
        if (wave.getSpawnTimeFinished() > updateTimer.getLastUpdateTime()) {
            return;
        }

        // Logs.
        LOGGER.info("Finished spawning, entering waiting mode.");

        // Removes the balloon spawner.
        balloonSpawner.remove();

        // Sets the state to wait (for all the entities to be gone).
        state = State.Waiting;
    }

    private void explicitUpdateWaiting() {
        // Makes sure it's time to move on.
        if (!balloons.isEmpty()) {
            return;
        }

        // Logs.
        LOGGER.info("Balloons empty ending level or moving on to next wave.");

        // If there is a next wave, build it, else end the level.
        if (waveBuilderIterator.hasNext()) {
            wave = waveBuilderIterator.next().build();
            state = State.Resting;

            LOGGER.info("Next wave present, entering resting mode.");
            return;
        }

        // Plays the sounds for the finish.
        new SoundClip("nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/the_red_army_destroyed_hitlers_balloons.mp3").play();

        // Sets the next level time and the state to finished.
        nextLevelTime = updateTimer.getLastUpdateTime() + 5.0;
        state = State.Finished;
    }

    private void explicitUpdateFinished() {
        // Makes sure it's time to switch to the next level.
        if (nextLevelTime > updateTimer.getLastUpdateTime()) {
            return;
        }

        // Switches to the next level.
        main.setActiveScene(nextSceneId);
    }

    @Override
    public void explicitUpdate(long l) {
        updateTimer.update();

        switch (state) {
            case Resting -> explicitUpdateResting();
            case Waiting -> explicitUpdateWaiting();
            case Spawning -> explicitUpdateSpawning();
            case Finished -> explicitUpdateFinished();
        }
    }
}
