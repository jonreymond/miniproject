package miniproject;

import miniproject.utils.Time;

public abstract class Entity {
    private final String name;
    protected Position position;

    public Entity(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public abstract void update(Time dt);


}
