package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.RawMaterial;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The repository for accessing data from raw material.
 */
@Repository
public class RawMaterialRepository extends server.Repositories.Repository {

    /**
     * Retrieve all raw materials found in the database.
     *
     * @return List of all raw materials.
     */
    public List<RawMaterial> getAllRawMaterial() {
        String query = "SELECT * FROM rawMaterials";

        List<RawMaterial> rawMaterial = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            rawMaterial = new ArrayList<>();
            while (rs.next()) {
                rawMaterial.add(new RawMaterial(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("amount"), rs.getString("unit"),
                        rs.getTimestamp("lastDeliveryTime"), rs.getInt("lastDeliveryAmount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rawMaterial;
    }

    /**
     * Retrieve a given raw material found in the database.
     *
     * @param id Given raw material ID.
     * @return Given raw material.
     */
    public RawMaterial getRawMaterial(int id) {
        String query = "SELECT * FROM rawMaterials WHERE id=?";

        RawMaterial rawMaterial = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // To check if there more than one result, which there shouldn't be.
            if (rs.next()) {
                rawMaterial = new RawMaterial(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("amount"), rs.getString("unit"),
                        rs.getTimestamp("lastDeliveryTime"), rs.getInt("lastDeliveryAmount"));
            } else {
                throw new NoSuchElementException("Element not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rawMaterial;
    }
}
