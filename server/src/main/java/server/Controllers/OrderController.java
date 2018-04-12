package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.DataResponse;
import server.Models.Order;
import server.Repositories.OrderRepository;
import server.Resources.OrderResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for orders.
 */
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    // The repository for orders.
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Getting all orders.
     *
     * @return The list of all orders as a api response.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getAllOrders() {
        List<Order> orderList = orderRepository.getAllOrders();
        List<OrderResource> orderResourceList = new ArrayList<>();

        for (Order order : orderList) {
            orderResourceList.add(new OrderResource(order));
        }
        return new ResponseEntity<>(new DataResponse(orderResourceList), HttpStatus.OK);
    }
}
