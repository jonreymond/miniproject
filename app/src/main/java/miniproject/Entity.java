package miniproject;

import miniproject.utils.Time;

/**
 * the class entity represent any real object positioned in a given position, and how it evolves over time.
 * 
 */
public abstract class Entity {
    private final String name;
    protected Position position;

    public Entity(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Returns the name of the entity.
     *
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current position of the entity.
     *
     * @return the current position of the entity. The returned position object is immutable and can be safely used without modification.
     */
    public Position getPosition() {
        return position;
    }


    /**
 * Updates the entity's state based on the elapsed time.//+
 *
 * This method is intended to be overridden by subclasses to define the specific behavior of the entity.//+
 * The update method should be called periodically to advance the entity's state in accordance with the elapsed time.//+
 *
 * @param dt The elapsed time since the last update. This parameter is used to calculate the change in the entity's state.//+
 *           The time is represented as a {@link Time} object, which provides methods for converting time units.//+
 *
 * @throws IllegalArgumentException If the provided {@code dt} is null.//+
 */
    public abstract void update(Time dt);


}
