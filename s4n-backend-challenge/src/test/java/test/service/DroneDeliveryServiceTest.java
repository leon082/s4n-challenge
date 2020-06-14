package test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import service.impl.DroneDeliveryServiceImpl;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class DroneDeliveryServiceTest {

    @Test
    public final void testOrders() {
        Assert.assertTrue(DroneDeliveryServiceImpl.getInstance().getOrders().size() > BigDecimal.ONE.intValue());
    }

    @Test
    public final void testFreeDrone() {
        Assert.assertNotNull(DroneDeliveryServiceImpl.getInstance().getFreeTransport());
    }

    @Test
    public final void testPendingOrder() {
        Assert.assertTrue(DroneDeliveryServiceImpl.getInstance().validatePendingOrder());
    }

    @Test
    public final void testFreeOrder() {
        Assert.assertNotNull(DroneDeliveryServiceImpl.getInstance().getFreeOrder());
    }

}
