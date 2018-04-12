package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The repository for accessing data from raw material.
 */
@Repository
public class OrderRepository extends server.Repositories.Repository {

    /**
     * Retrieve all orders found in the database.
     *
     * @return List of all orders.
     */
    public List<Order> getAllOrders() {
        String query = "SELECT * FROM orders";

        List<Order> orders = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(new Order(rs.getLong("id"), rs.getTimestamp("latestDeliveryTime"), rs.getInt("customerId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
