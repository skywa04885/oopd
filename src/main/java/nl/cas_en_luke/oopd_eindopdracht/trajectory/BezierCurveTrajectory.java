package nl.cas_en_luke.oopd_eindopdracht.trajectory;

import nl.cas_en_luke.oopd_eindopdracht.Trajectory;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * A class used for bezier interpolation.
 */
public class BezierCurveTrajectory extends Trajectory {
    private static final Logger LOGGER = Logger.getLogger(BezierCurveTrajectory.class.getName());

    public static class Builder {
        private final ArrayList<Vector2D> points;

        private Builder() {
            this.points = new ArrayList<>();
        }

        public Builder addPoint(final Vector2D point) {
            this.points.add(point);

            return this;
        }

        public BezierCurveTrajectory build() {
            return new BezierCurveTrajectory(points);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final ArrayList<Vector2D> points;
    private Double length;

    private BezierCurveTrajectory(final ArrayList<Vector2D> points) {
        this.points = points;
    }

    private static Vector2D interpolateBetweenPoints(final double t, final Vector2D v1, final Vector2D v2) {
        return v2.subtract(v1)
                .scalarMultiply(t)
                .add(v1);
    }

    /**
     * Implementation of de Casteljau's algorithm.
     *
     * @param t a number between 0 and 1 indicating the percentage of the path we've already passed.
     * @return the two-dimensional point vector.
     */
    @Override
    public Vector2D interpolate(final double t) {
        assert t >= 0.0 && t <= 1.0;
        assert points.size() >= 1;

        // Creates the point lists.
        final ArrayList<Vector2D> points1 = new ArrayList<>(points);
        final ArrayList<Vector2D> points2 = new ArrayList<>(points.size() - 1);

        // Initializes the read and write points (these will be swapped during computation).
        ArrayList<Vector2D> readPoints = points1;
        ArrayList<Vector2D> writePoints = points2;

        // Stays in loop as long as there are points to interpolate between.
        while (readPoints.size() > 1) {
            // Performs the interpolation between the point pairs.
            for (int i = 0; i < readPoints.size() - 1; ++i) {
                // Gets the pair of vectors.
                final Vector2D v1 = readPoints.get(i);
                final Vector2D v2 = readPoints.get(i + 1);

                // Performs the interpolation in between the vectors.
                final Vector2D interpolatedVector = interpolateBetweenPoints(t, v1, v2);

                // Writes the interpolated vector.
                writePoints.add(interpolatedVector);
            }

            // Swaps the read and write points.
            final ArrayList<Vector2D> tempPoints = readPoints;
            readPoints = writePoints;
            writePoints = tempPoints;

            // Clears the vector that will be written to.
            writePoints.clear();
        }

        // Returns the interpolated point.
        return readPoints.get(0);
    }

    /**
     * Computes the length of the curve, at least, well it's an estimation.
     */
    private void computeLength() {
        // Logs that we're about to compute the length.
        LOGGER.info("Computing length of curve with " + points.size()
                + " points since the length hasn't been cached yet.");

        // Initializes the computation variables.
        int steps = 10;
        double previousLength;
        double currentLength = 0.0;

        // Records the start time.
        final long startTimeMillis = System.currentTimeMillis();

        // Stays in loop as long as we haven't reached the desired accuracy.
        do {
            // Logs about the iteration.
            LOGGER.info("Performing numerical estimation of curve length using " + steps + " steps");

            // Assigns the currnet length to the previous length/
            previousLength = currentLength;
            currentLength = 0.0;

            // Computes the t of each step.
            double tStep = 1.0 / (double) steps;

            // Loops over the steps and computes.
            for (int i = 0; i < steps - 1; ++i) {
                // Gets the two vectors.
                final Vector2D v1 = interpolate(tStep * (double) i);
                final Vector2D v2 = interpolate(tStep * (double) (i + 1));

                // Computes the distance between the pair.
                final double distance = v1.distance(v2);

                // Adds the distance to the total length.
                currentLength += distance;
            }

            // Increases the number of steps and logs it.
            steps *= 10;
        } while (Math.abs(currentLength - previousLength) > 1.0);
        length = currentLength;

        // Records the end time.
        final long endTimeMillis = System.currentTimeMillis();

        // Logs the computation results.
        LOGGER.info("Computed length " + length + " of bezier curve with " + points.size() + " points using " +
                steps + "steps, took: " + (endTimeMillis - startTimeMillis) + " milliseconds.");
    }

    /**
     * Gets the length of the curve (at least, an estimation). Or estimates it if not present.
     *
     * @return the length of the curve.
     */
    @Override
    public double getLength() {
        if (length == null) {
            computeLength();
        }

        return length;
    }
}
