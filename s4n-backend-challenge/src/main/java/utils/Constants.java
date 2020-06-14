package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String PATH_FILES = "src/main/resources/orders/drone/";
    public static final String OUT_ROUTE = "\n\nEste pedido se sale del perimetro de domicilio.";
    public static final String HEAD_FILE = "\n\n == Reporte de entrega ==";
    public static final String ERROR_LIMIT_ORDERS = "The number of deliveries for this file is greater than the limit allowed ";
    public static final BigDecimal LIMIT_DELIVERY = new BigDecimal(10);
    public static final Integer NUMBER_DRONES = 20;
    public static final Integer LIMIT_ORDERS = 3;
    public static final String DRONE_NAME = "DRONE NUMBER ";

}
