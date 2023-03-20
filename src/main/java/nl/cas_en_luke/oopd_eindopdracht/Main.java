package nl.cas_en_luke.oopd_eindopdracht;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import nl.cas_en_luke.oopd_eindopdracht.levels.LevelOne;
import nl.cas_en_luke.oopd_eindopdracht.levels.LevelThree;
import nl.cas_en_luke.oopd_eindopdracht.levels.LevelTwo;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;
import nl.cas_en_luke.oopd_eindopdracht.scenes.StartScene;
import nl.cas_en_luke.oopd_eindopdracht.scenes.VictoryScene;

import java.util.logging.Logger;

public class Main extends YaegerGame {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String GAME_TITLE = "Bloons TD 69";
    private static final Size GAME_SIZE = new Size(826, 532);

    public static final int GAME_SCENE_LEVEL_ONE_ID = 2;
    public static final int GAME_SCENE_LEVEL_TWO_ID = 3;
    public static final int GAME_SCENE_LEVEL_THREE_ID = 4;
    public static final int VICTORY_SCENE_ID = 5;
    public static final int START_SCENE_ID = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void setupGame() {
        setGameTitle(GAME_TITLE);
        setSize(GAME_SIZE);
    }

    @Override
    public void setupScenes() {
        addScene(START_SCENE_ID, new StartScene(this));
        addScene(VICTORY_SCENE_ID, new VictoryScene(this));
        addScene(GAME_SCENE_LEVEL_TWO_ID, new GameScene(this, new LevelTwo(), GAME_SCENE_LEVEL_THREE_ID));
        addScene(GAME_SCENE_LEVEL_THREE_ID, new GameScene(this, new LevelThree(), VICTORY_SCENE_ID));
        addScene(GAME_SCENE_LEVEL_ONE_ID, new GameScene(this, new LevelOne(), GAME_SCENE_LEVEL_TWO_ID));
    }

    @Override
    public void setActiveScene(int id) {
        super.setActiveScene(id);
    }
}