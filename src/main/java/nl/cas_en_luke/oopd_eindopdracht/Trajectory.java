package nl.cas_en_luke.oopd_eindopdracht;

import com.github.hanyaeger.api.Coordinate2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class Trajectory {
    public abstract Vector2D interpolate(final double t);

    public Coordinate2D interpolateToCoordinate2D(final double t) {
        final Vector2D point = interpolate(t);

        return new Coordinate2D(point.getX(), point.getY());
    }

    public abstract double getLength();
}
