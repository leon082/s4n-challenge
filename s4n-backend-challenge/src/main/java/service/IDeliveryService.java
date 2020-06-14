package service;

import model.Order;

public interface IDeliveryService<T> {


    public boolean validatePendingOrder();

    public void runProcess();

    public T getFreeTransport();

    public Order getFreeOrder();

    public boolean validateFreeTransport();

}
