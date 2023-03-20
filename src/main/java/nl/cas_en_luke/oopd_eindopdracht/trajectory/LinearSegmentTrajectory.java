package nl.cas_en_luke.oopd_eindopdracht.trajectory;

import javafx.geometry.Point2D;
import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;

/**
 * Linear segment trajectory class.
 * TODO: Optimize this algorithm to use lookup tables.
 */
public class LinearSegmentTrajectory extends Trajectory {
    public static class Builder {
        private ArrayList<Vector2D> points;

        /**
         * Constructs a new builder.
         */
        public Builder() {
            points = new ArrayList<>();
        }

        /**
         * Adds a new point to the trajectory.
         *
         * @param point the point to add.
         * @return the current builder.
         */
        public Builder addPoint(final Vector2D point) {
            points.add(point);

            return this;
        }

        /**
         * Gets the length of the trajectory that currently is being constructed.
         *
         * @return the computed length.
         */
        private double getLength() {
            double length = 0.0;

            // Sums all the distances.
            for (int i = 0; i < points.size() - 1; ++i) {
                // Gets the pair of points.
                final Vector2D point1 = points.get(i);
                final Vector2D point2 = points.get(i + 1);

                // Computes the distance between the points.
                final double distance = point1.distance(point2);

                // Adds the distance to the length.
                length += distance;
            }

            // Returns the length.
            return length;
        }

        public LinearSegmentTrajectory build() {
            final double length = getLength();

            return new LinearSegmentTrajectory(points, length);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final ArrayList<Vector2D> points;
    private final double length;

    /**
     * Constructs a new linear segment trajectory.
     *
     * @param points the points in the trajectory.
     * @param length the length of the trajectory.
     */
    private LinearSegmentTrajectory(final ArrayList<Vector2D> points, final double length) {
        this.points = points;
        this.length = length;
    }

    @Override
    public Vector2D interpolate(double t) {
        // Makes sure t is between 0 and 1.
        t = Math.min(Math.max(0.0, t), 1.0);

        // Finds the desired length.
        final double desiredLength = length * t;

        // Stays in loop and finds the segment to interpolate with.
        double currentLength = 0.0;
        for (int i = 0; i < points.size() - 1; ++i) {
            // Gets the pair of points.
            final Vector2D point1 = points.get(i);
            final Vector2D point2 = points.get(i + 1);

            // Computes the distance between the points.
            final double distance = point1.distance(point2);

            // Checks if we're in the segment that needs to be interpolated, if not continue.
            if (currentLength + distance < desiredLength) {
                currentLength += distance;
                continue;
            }

            // Calculates the t of the segment (the value we'll use to interpolate between point1 and point2).
            final double tOfSegment = ((t - (currentLength / length)) * length) / distance;

            // Performs the interpolation and returns the result.
            return point2.subtract(point1).scalarMultiply(tOfSegment).add(point1);
        }

        // Throws an exception (should never).
        throw new RuntimeException("Couldn't find section to interpolate in, shouldn't happen.");
    }

    @Override
    public double getLength() {
        return length;
    }
}
