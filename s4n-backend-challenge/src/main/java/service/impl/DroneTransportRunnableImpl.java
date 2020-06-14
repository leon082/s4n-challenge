package service.impl;

import lombok.Getter;
import lombok.Setter;
import model.Drone;
import model.Position;
import repository.DeliveryWriter;
import service.ITransportRunnable;
import utils.CardinalPoints;
import utils.Constants;
import utils.Direction;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;


@Getter
@Setter
public class DroneTransportRunnableImpl implements ITransportRunnable {

    private Drone drone;

    private DeliveryWriter deliveryWriter;

    public DroneTransportRunnableImpl(Drone drone) {
        this.drone = drone;
        this.deliveryWriter = new DeliveryWriter();
    }

    @Override
    public void run() {

        this.drone.setState(false);


        this.startDelivery();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.drone.setState(true);
    }

    @Override
    public void startDelivery() {

        StringBuilder log = new StringBuilder(this.drone.getName() + "\n");

        for (String order : this.drone.getOrder().getOrders()) {

            this.drone.setPosition(new Position(BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue(), CardinalPoints.NORTE));
            char[] chars = order.toUpperCase().toCharArray();

            for (int i = 0; i < chars.length - 1; i++) {

                char value = chars[i];
                Optional<Direction> direction = Stream.of(Direction.values()).filter(p -> p.name == value).findFirst();
                if (direction.isPresent()) {

                    Position nextPosition = new Position(BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue(), drone.getPosition().getCardinalPoint());

                    nextPosition = getNextPosition(direction.get(), this.drone.getPosition().getCardinalPoint(), nextPosition);

                    this.drone.getPosition().setX(this.drone.getPosition().getX() + nextPosition.getX());
                    this.drone.getPosition().setY(this.drone.getPosition().getY() + nextPosition.getY());
                    this.drone.getPosition().setCardinalPoint(nextPosition.getCardinalPoint());


                    log.append(getLog(i, this.drone.getPosition())).append("\n");

                    if (!validatePosition(this.drone.getPosition())) {
                        log.append(Constants.OUT_ROUTE);
                        break;
                    }
                }
            }

        }
        deliveryWriter.writeFile(this.drone.getOrder().getName().replaceAll("in", "out"), log.toString());
    }

    public StringBuilder getLog(int i, Position position) {
        StringBuilder logResult = new StringBuilder();
        if (i == 0) {
            logResult.append(Constants.HEAD_FILE);
        }
        logResult.append("\n(" + position.getX() + "," + position.getY() + ") direccion " + position.getCardinalPoint().name());
        return logResult;
    }


    @Override
    public boolean validatePosition(Position position) {

        if (
                ((position.getY().intValue() <= Constants.LIMIT_DELIVERY.intValue()) && (position.getY().intValue() >= Constants.LIMIT_DELIVERY.negate().intValue())) &&
                        ((position.getX().intValue() <= Constants.LIMIT_DELIVERY.intValue()) && (position.getX().intValue() >= Constants.LIMIT_DELIVERY.negate().intValue()))
        ) {
            return true;
        }
        return false;
    }

    @Override
    public Position getNextPosition(Direction direction, CardinalPoints cardinal, Position nextPosition) {
        /*
         * A -> advance one step to front
         * I -> just turn left 90 grades but do not advance
         * D -> just turn right 90 grades but do not advance         *
         * */
        switch (cardinal) {
            case NORTE:
                this.toNorth(direction, nextPosition);
                break;
            case OCCIDENTE:
                this.toOcc(direction, nextPosition);
                break;
            case ORIENTE:
                this.toOri(direction, nextPosition);
                break;
            case SUR:
                this.toSur(direction, nextPosition);
                break;
        }
        return nextPosition;
    }

    private void toNorth(Direction direction, Position pos) {

        switch (direction) {
            case A:
                pos.setY(BigDecimal.ONE.intValue());
                break;
            case I:
                pos.setCardinalPoint(CardinalPoints.OCCIDENTE);
                break;
            case D:
                pos.setCardinalPoint(CardinalPoints.ORIENTE);
                break;
        }
    }

    private void toOcc(Direction direction, Position pos) {
        switch (direction) {
            case A:
                pos.setX(BigDecimal.ONE.negate().intValue());
                break;
            case I:
                pos.setCardinalPoint(CardinalPoints.SUR);
                break;
            case D:
                pos.setCardinalPoint(CardinalPoints.NORTE);
                break;
        }
    }

    public void toOri(Direction direction, Position pos) {
        switch (direction) {
            case A:
                pos.setX(BigDecimal.ONE.intValue());
                break;
            case I:
                pos.setCardinalPoint(CardinalPoints.NORTE);
                break;
            case D:
                pos.setCardinalPoint(CardinalPoints.SUR);
                break;
        }
    }

    public void toSur(Direction direction, Position pos) {
        switch (direction) {
            case A:
                pos.setY(BigDecimal.ONE.negate().intValue());
                break;
            case I:
                pos.setCardinalPoint(CardinalPoints.ORIENTE);
                break;
            case D:
                pos.setCardinalPoint(CardinalPoints.OCCIDENTE);
                break;
        }
    }
}
