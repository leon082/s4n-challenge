package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
public class Drone {

    @Getter
    @Setter
    public String name;


    public Boolean state;


    private Order order;


    Position position;


    public synchronized void setState(boolean state) {
        this.state = state;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized Boolean getState() {
        return state;
    }

    public synchronized Order getOrder() {
        return order;
    }

    public synchronized void setOrder(Order order) {
        this.order = order;
    }

    public synchronized Position getPosition() {
        return position;
    }

    public synchronized void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public synchronized String toString() {
        return "Drone{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", order=" + order +
                ", position=" + position +
                '}';
    }
}
