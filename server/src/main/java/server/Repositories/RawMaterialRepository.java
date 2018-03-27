package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.RawMaterial;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * The repository for accessing data from raw material.
 */
@Repository
public class RawMaterialRepository extends server.Repositories.Repository {
    public RawMaterial getRawMaterial(int id){
        String query = "SELECT * FROM rawMaterials WHERE id=?";

        RawMaterial rawMaterial = null;

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // To check if there more than one result, which there shouldn't be.
            if(rs.next()){
                rawMaterial = new RawMaterial(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("amount"), rs.getString("unit"),
                        rs.getTimestamp("lastDeliveryTime"), rs.getInt("lastDeliveryAmount"));
            }else {
                throw new NoSuchElementException("Element not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rawMaterial;
    }
}
