package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.Pallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The repository for accessing data from pallets.
 */
@Repository
public class PalletRepository extends server.Repositories.Repository {
    /**
     * Retrieve a specific pallet.
     *
     * @param id The id (unique) of the pallet.
     * @return The found pallet.
     */
    public Pallet getPallet(Integer id) {
        String query = "SELECT * FROM pallets WHERE id=?";

        Pallet pallet = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // next() returns true if there were more rows found
            if (rs.next()) {
                pallet = new Pallet(rs.getInt("id"), rs.getInt("amount"),
                        rs.getDate("productionDate"), rs.getBoolean("isBlocked"),
                        rs.getString("location"), rs.getTimestamp("deliveryTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pallet;
    }
}