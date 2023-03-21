package nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.balloons;

import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import nl.cas_en_luke.oopd_eindopdracht.scenes.game.entities.Balloon;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;

public class BlackBalloon extends Balloon {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/balloons/black_32.png";
    private static final double DAMAGE = 15.0;
    private static final double SPEED = 28.0;
    private static final double HEALTH = 10.0;

    public static class Builder extends Balloon.Builder<Builder> {
        @Override
        public Balloon build(final GameScene gameScene, final Trajectory trajectory) {
            return new BlackBalloon(gameScene, trajectory);
        }

        @Override
        public double getWeight() {
            return 4.0;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public BlackBalloon(final GameScene gameScene, final Trajectory trajectory) {
        super(RESOURCE, gameScene, trajectory, DAMAGE, SPEED, HEALTH);
    }
}
