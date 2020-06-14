package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utils.CardinalPoints;

@Setter
@Getter
@AllArgsConstructor
public class Position {

    Integer x;
    Integer y;
    CardinalPoints cardinalPoint;

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", cardinalPoint=" + cardinalPoint +
                '}';
    }
}
