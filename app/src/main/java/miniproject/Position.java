package miniproject;

import java.util.Objects;

/**
 * The class Position represents a position on the earth.
 */
public final class Position {
    private final Double latitude, longitude;
    private static final Double EARTH_RADIUS = 6371.0;

    /**
     * Constructor for the class Position.
     * @param latitude The latitude of the position.
     * @param longitude The longitude of the position.
     */
    public Position(Double latitude, Double longitude) {
        assert (latitude >= -90 && latitude <= 90) : "Invalid latitude value: must be between -90 and 90";
        assert (longitude >= -180 && longitude <= 180) : "Invalid longitude value: must be between -180 and 180";
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Position(Position other) {
        this.latitude = other.latitude;
        this.longitude = other.longitude;
    }

    /**
     * Calculates the distance between this position and another position on Earth. 
     * The distance is calculated using the Haversine formula
     *
     * @param other The other position to calculate the distance to.
     * @return The distance in kilometers between this position and the other position.
     */
    public Double getDistance(Position other) {

        Double p = Math.PI / 180;

        Double a = 0.5 - Math.cos((other.latitude - this.latitude) * p) / 2
                + Math.cos(this.latitude * p) * Math.cos(other.latitude * p) *
                  (1 - Math.cos((other.longitude - this.longitude) * p)) / 2;

        return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(a));
    }

    /**
     * Calculates the distance between two positions on Earth.
     *
     * @param a The first position.
     * @param b The second position.
     * @return The distance in kilometers between the two positions.
     *         The distance is calculated using the Haversine formula
     */
    public static Double getDistance(Position a, Position b) {
        return a.getDistance(b);
    }


    /**
     * Moves from the current position to a target position along a straight line path,
     * considering the Earth as a perfect sphere. The distance is calculated using the Haversine formula.
     *
     * @param from The current position.
     * @param to The target position.
     * @param distance The distance in Kilometer to move from the current position to the target position.
     * @return The new position after moving the specified distance. If the distance is greater than the
     *         distance between the current position and the target position, the target position is returned.
     *         The returned position is calculated using a linear interpolation between the current and target positions.
     *         TODO: This method does not take into account the toric aspect of the Earth.
     */
    public static Position moveTo(Position from, Position to, Double distance) {
        //TODO : Correct this function, does not take into account the toric aspect of the earth
        if(getDistance(from, to) < distance) {
            return to;
        }
        Double ratio = distance / getDistance(from, to);
        Double latitude = from.latitude + (to.latitude - from.latitude) * ratio;
        Double longitude = from.longitude + (to.longitude - from.longitude) * ratio;
        return new Position(latitude, longitude);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return latitude.equals(position.latitude) && longitude.equals(position.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
        
}
