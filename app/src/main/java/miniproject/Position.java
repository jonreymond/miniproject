package miniproject;

import java.util.Objects;

public final class Position {
    private final Double latitude, longitude;
    private static final Double EARTH_RADIUS = 6371.0;

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

    public Double getDistance(Position other) {

        Double p = Math.PI / 180;

        Double a = 0.5 - Math.cos((other.latitude - this.latitude) * p) / 2
                + Math.cos(this.latitude * p) * Math.cos(other.latitude * p) *
                  (1 - Math.cos((other.longitude - this.longitude) * p)) / 2;

        return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(a));
    }

    public static Double getDistance(Position a, Position b) {
        return a.getDistance(b);
    }


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
