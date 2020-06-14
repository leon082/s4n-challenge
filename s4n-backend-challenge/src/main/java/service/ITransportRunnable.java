package service;

import model.Position;
import utils.CardinalPoints;
import utils.Direction;

public interface ITransportRunnable extends Runnable {

    public void startDelivery();

    public Position getNextPosition(Direction direction, CardinalPoints cardinal, Position nextPosition);

    public boolean validatePosition(Position position);
}
