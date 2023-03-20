package nl.cas_en_luke.oopd_eindopdracht.entities.balloons;

import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import nl.cas_en_luke.oopd_eindopdracht.entities.Balloon;
import nl.cas_en_luke.oopd_eindopdracht.scenes.GameScene;

public class BlueBalloon extends Balloon {
    private static final String RESOURCE = "nl/cas_en_luke/oopd_eindopdracht/images/balloons/blue_32.png";
    private static final double DAMAGE = 2.0;
    private static final double SPEED = 20.0;
    private static final double HEALTH = 1.0;

    public static class Builder extends Balloon.Builder<Builder> {
        @Override
        public Balloon build(final GameScene gameScene, final Trajectory trajectory) {
            return new BlueBalloon(gameScene, trajectory);
        }

        @Override
        public double getWeight() {
            return 6.0;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
    public BlueBalloon(final GameScene gameScene, final Trajectory trajectory) {
        super(RESOURCE, gameScene, trajectory, DAMAGE, SPEED, HEALTH);
    }
}
