package miniproject.utils;

import java.util.Objects;
import java.util.Date;
/**
 * Class representing a unit of time
 * From https://github.com/brogovia/antSimulation/blob/master/projet/ch/epfl/moocprog/
 */
public final class Time implements Comparable<Time> {
    public static final Time ZERO = new Time(0);

    private final long timeInMinutes;

    // prevent direct instantiation
    private Time(long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    /**
     * Instantiate a new object of type {@link Time} from a time in hours
     *
     * @param seconds Le temps en secondes
     * @return Une nouvelle instance de {@link Time} avec la valeur
     *         spécifiée en paramètre.
     */
    public static Time fromHours(double hours) {
        return new Time((long) (hours * 60));
    }

    /**
     * Instantiate a new object of type {@link Time} from a time in minutes
     *
     * @param minutes The time in minutes
     * @return Une nouvelle instance de {@link Time} avec la valeur
     *         spécifiée en paramètre.
     */
    public static Time fromMinutes(long minutes) {
        return new Time(minutes);
    }

     /**
     * Instantiate a new object of type {@link Time} from a time in minutes
     *
     * @param days The time in days
     * @return Une nouvelle instance de {@link Time} avec la valeur
     *         spécifiée en paramètre.
     */
    public static Time fromDays(double days) {
        return new Time((long)(days * 24 * 600));
    }

    /**
     * return the value of this instance in hours
     *
     * @return the value of this instance in hours
     */
    public double toHours() {
        return timeInMinutes / 60d;
    }

    /**
     * return the value of this instance in hours
     *
     * @return the value of this instance in hours
     */
    public double toDays() {
        return timeInMinutes / (60d * 24d);
    }
    

    /**
     * return the value of this instance in minutes
     *
     * @return the value of this instance in minutes
     */
    public long toMinutes() {
        return timeInMinutes;
    }

  
    /**
     * Adds the given {@link Time} instance to the current instance.
     *
     * @param that The {@link Time} instance to add to the current instance.
     * @return A new {@link Time} instance representing the result of the addition.
     *
     * @throws NullPointerException If the given instance is {@code null}.
     */
    public Time plus(Time that) {
        if (that == null) {
            throw new NullPointerException("The given Time instance cannot be null.");
        }
        return new Time(this.timeInMinutes + that.timeInMinutes);
    }

    /**
     * Subtracts the given {@link Time} instance from the current instance.
     *
     * @param that The {@link Time} instance to subtract from the current instance.
     * @return A new {@link Time} instance representing the result of the subtraction.
     *         The result will be negative if the current instance is less than the given instance.
     *
     * @throws NullPointerException If the given instance is {@code null}.
     */
    public Time minus(Time that) {
        if (that == null) {
            throw new NullPointerException("The given Time instance cannot be null.");
        }
        return new Time(this.timeInMinutes - that.timeInMinutes);
    }


    /**
     * Checks if the current instance of {@link Time} is positive.
     * A positive time instance represents a duration greater than zero.
     *
     * @return {@code true} if the time is positive (greater than or equal to zero),
     *         {@code false} otherwise.
     */
    public boolean isPositive() {
        return timeInMinutes >= 0;
    }


    @Override
    public int compareTo(Time that) {
        if(this.timeInMinutes < that.timeInMinutes) {
            return -1;
        } else if(this.timeInMinutes > that.timeInMinutes) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Time) {
            Time that = (Time) o;
            return that.timeInMinutes == this.timeInMinutes;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeInMinutes);
    }

    @Override
    public String toString() {
        return (new Date(timeInMinutes * 60 * 1000)).toString();

    }
}
