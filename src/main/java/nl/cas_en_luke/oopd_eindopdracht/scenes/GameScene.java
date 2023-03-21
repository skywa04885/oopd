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
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.Balloon;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.Projectile;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.Turret;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turrets.BoomSentryTurret;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turrets.ColdSentryTurret;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.turrets.CrushingSentryTurret;
import nl.cas_en_luke.oopd_eindopdracht.helpers.WeighedRandomCollection;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.*;
import nl.cas_en_luke.oopd_eindopdracht.spawners.BalloonSpawner;
import nl.cas_en_luke.oopd_eindopdracht.timers.UpdateTimer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

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
    private static final String AS_YOU_WISH_COMMANDER_AUDIO_RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/audio/scenes/game/as_you_wish_commander.mp3";
    private static final Logger LOGGER = Logger.getLogger(GameScene.class.getName());

    private final Main main;
    private final Level level;
    private final int nextSceneId;
    private final TurretPurchaseMenu.Builder turretPurchaseMenuBuilder;
    private final UpdateTimer updateTimer;
    private Iterator<Wave.Builder> waveBuilderIterator;
    private LinkedList<Balloon> balloons;
    private double health;
    private double balance;
    private double nextLevelTime;
    private Wave wave;
    private State state;
    private BalloonSpawner balloonSpawner;

    public GameScene(final Main main, final Level level, final int nextSceneId) {
        this.main = main;
        this.level = level;
        this.nextSceneId = nextSceneId;

        turretPurchaseMenuBuilder = TurretPurchaseMenu.newBuilder()
                .addTurretBuilder(BoomSentryTurret.newBuilder())
                .addTurretBuilder(ColdSentryTurret.newBuilder())
                .addTurretBuilder(CrushingSentryTurret.newBuilder());

        this.updateTimer = UpdateTimer.newBuilder().build();
    }

    public State getState() {
        return state;
    }

    public double getNextLevelTime() {
        return nextLevelTime;
    }

    public Wave getWave() {
        return wave;
    }

    public UpdateTimer getUpdateTimer() {
        return updateTimer;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setHealth(final double health) {
        this.health = health;

        if (this.health < 0.0) {
            main.setActiveScene(Main.LOSS_SCENE_ID);
        }
    }

    public double getHealth() {
        return health;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(final double amount) {
        this.balance += amount;
    }

    public LinkedList<Balloon> getBalloons() {
        return balloons;
    }

    public void purchaseTurret(final Turret.Builder<?> turretBuilder, final Coordinate2D position) {
        // Make sure the turret can be bought.
        if (balance < turretBuilder.getPrice()) {
            throw new RuntimeException("Attempt made to purchase item with insufficient balance.");
        }

        // Removes the price from the balance.
        this.balance -= turretBuilder.getPrice();

        // Plays the sound indicating something has been bought.
        new SoundClip(AS_YOU_WISH_COMMANDER_AUDIO_RESOURCE).play();

        // Builds and adds the turret to the game.
        final Turret turret = turretBuilder.build(this, position);
        addEntity(turret);
    }

    public void shootProjectile(final Projectile.Builder<?> projectileBuilder, final Vector2D position,
                                final double angle) {
        // Builds and adds the projectile to the game.
        final Projectile projectile = projectileBuilder.build(position, angle);
        addEntity(projectile);
    }

    @Override
    public void setupScene() {
        // Sets the instance variables.
        this.balloons = new LinkedList<>();
        this.health = level.getInitialHealth();
        this.balance = level.getInitialBalance();
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
        addEntity(turretPurchaseMenuBuilder.build(this, new Coordinate2D(10, 10.0)));
        addEntity(new StopButton(new Coordinate2D(10.0, getHeight() - 10.0)));
        addEntity(new PauseResumeButton(new Coordinate2D(140.0, getHeight() - 10.0), this));
        addEntity(new HealthText(new Coordinate2D(getWidth() - 10.0, 10.0), this));
        addEntity(new BalanceText(new Coordinate2D(getWidth() - 10.0, 50.0), this));
        addEntity(new InfoText(new Coordinate2D(getWidth() / 2.0, getHeight() / 2.0), this));
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
