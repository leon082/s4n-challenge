package service.impl;

import lombok.Getter;
import model.Drone;
import model.Order;
import model.Position;
import repository.OrdersReader;
import service.IDeliveryService;
import service.ITransportRunnable;
import utils.CardinalPoints;
import utils.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class DroneDeliveryServiceImpl implements IDeliveryService<Drone> {

    private static DroneDeliveryServiceImpl instance;

    @Getter
    private List<Drone> drones;

    @Getter
    private List<Order> orders;


    private DroneDeliveryServiceImpl() {
        this.drones = new ArrayList<>();
        this.orders = OrdersReader.getInstance().getOrders();
    }


    public static DroneDeliveryServiceImpl getInstance() {
        if (instance == null) {
            instance = new DroneDeliveryServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean validatePendingOrder() {
        return !this.orders.stream().filter(i -> i.getState() == true).collect(Collectors.toList()).isEmpty();
    }

    @Override
    public void runProcess() {

        while (this.validatePendingOrder()) {

            Drone drone = getFreeTransport();

            if (drone != null) {

                ITransportRunnable runnable = new DroneTransportRunnableImpl(drone);
                Thread thread = new Thread(runnable);
                thread.start();

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

    @Override
    public Drone getFreeTransport() {

        if (this.drones.isEmpty() || (!validateFreeTransport() && this.drones.size() <= Constants.NUMBER_DRONES)) {
            this.drones.add(
                    Drone.builder()
                            .name(Constants.DRONE_NAME + (this.drones.size() + 1))
                            .position(new Position(BigDecimal.ZERO.intValue(), BigDecimal.ZERO.intValue(), CardinalPoints.NORTE))
                            .state(true)
                            .build());
        }

        Optional<Drone> first = this.drones.stream().filter(i -> i.getState() == true).findFirst();
        if (first.isPresent()) {
            first.get().setOrder(getFreeOrder());
            return first.get();
        }
        return null;

    }

    @Override
    public boolean validateFreeTransport() {
        return !this.drones.stream().filter(i -> i.getState() == true).collect(Collectors.toList()).isEmpty();
    }


    @Override
    public Order getFreeOrder() {
        Optional<Order> first = this.orders.stream().filter(i -> i.getState() == true).findFirst();
        if (first.isPresent()) {
            first.get().setState(false);
            return first.get();
        }
        return null;

    }


}
