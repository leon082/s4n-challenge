package repository;

import exceptions.ExceptionWriter;
import exceptions.OrdersLimitException;
import lombok.Getter;
import lombok.Setter;
import model.Order;
import utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class OrdersReader {

    private static OrdersReader instance;


    @Getter
    @Setter
    private List<Order> orders = new ArrayList<>();

    public static OrdersReader getInstance() {
        if (instance == null) {
            instance = new OrdersReader();
        }
        return instance;
    }

    public List<Order> getOrders() {

        try (Stream<Path> files = Files.list(Paths.get(Constants.PATH_FILES))) {
            files.forEach(i -> setOrder(i.toString()));
        } catch (IOException e) {
            ExceptionWriter.writeConsole(e.getMessage());
        }
        return this.orders;

    }

    private void setOrder(String file) {

        Path path = Paths.get(file);
        try (Stream<String> stream = Files.lines(path)) {
            List<String> orderLines = new ArrayList<>();


            stream.forEach(i -> orderLines.add(i));
            if (orderLines.size() > Constants.LIMIT_ORDERS.intValue()) {
                DeliveryWriter deliveryWriter = new DeliveryWriter();
                deliveryWriter.writeFile(getFileName(file).replaceAll("in", "out"), Constants.ERROR_LIMIT_ORDERS);
                throw new OrdersLimitException(Constants.ERROR_LIMIT_ORDERS + getFileName(file));
            } else {
                Order order = new Order();
                order.setName(getFileName(file));
                order.setState(true);
                order.setOrders(orderLines);
                this.orders.add(order);
            }

        } catch (IOException e) {
            ExceptionWriter.writeConsole(e.getMessage());
        } catch (OrdersLimitException e) {
            ExceptionWriter.writeConsole(e.getMessage());
        } catch (StringIndexOutOfBoundsException e) {
            ExceptionWriter.writeConsole(e.getMessage());
        }

    }

    private String getFileName(String fileName) throws StringIndexOutOfBoundsException {
        try {
            return fileName.substring(fileName.length() - 8, fileName.length());
        } catch (StringIndexOutOfBoundsException e) {
            throw e;
        }
    }


}
