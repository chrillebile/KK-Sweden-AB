package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.Pallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
            List<Pallet> palletList = new ArrayList<>();
            this.parseResults(ps, palletList);
            pallet = palletList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pallet;
    }

    /**
     * Retrieve all pallets found in the database.
     *
     * @return The found pallets in a list.
     */
    public List<Pallet> getPallets(){
        String query = "SELECT * FROM pallets";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)){
            this.parseResults(ps, palletList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    /**
     * Retrieve pallets between two dates found in the database.
     *
     * @param startDate Date to search from.
     * @param endDate Date to search to.
     * @return The found pallets in a list.
     */
    public List<Pallet> getPallets(LocalDate startDate, LocalDate endDate) {
        String query = "SELECT * FROM pallets WHERE productionDate BETWEEN ? AND ?";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(startDate));
            ps.setString(2, String.valueOf(endDate));
            this.parseResults(ps, palletList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    public List<Pallet> getPallets(Boolean isBlocked){
        String query = "SELECT * FROM pallets WHERE isBlocked = ?";
        List<Pallet> palletList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setBoolean(1, isBlocked);
            this.parseResults(ps, palletList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    public List<Pallet> getPallets(Integer id){
        String query = "SELECT pallets.id, amount, productionDate, isBlocked, location, deliveryTime, recipeId, orderId FROM pallets JOIN orders ON orders.id = pallets.orderId WHERE customerId = ?";
        List<Pallet> palletList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);
            this.parseResults(ps, palletList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    /**
     * Create a pallet.
     *
     * @param palletToBeCreated The pallet object which will be saved to the database.
     * @param recipeId          Foregin key to recipes.
     * @param orderId           Foreign key to orders.
     * @return An empty Pallet object where only the id is set.
     */
    public Pallet createPallet(Pallet palletToBeCreated, int recipeId, int orderId) {
        String query = "INSERT INTO pallets (amount, productionDate, isBlocked, location, deliveryTime, recipeId, orderId)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Pallet pallet = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            ps.setInt(1, palletToBeCreated.getAmount());
            ps.setObject(2, palletToBeCreated.getProductionDate());
            ps.setBoolean(3, palletToBeCreated.isBlocked());
            ps.setString(4, palletToBeCreated.getLocation());
            ps.setTimestamp(5, palletToBeCreated.getDeliveryTime());
            ps.setInt(6, recipeId);
            ps.setInt(7, orderId);

            ps.executeUpdate();

            PreparedStatement row = connection.prepareStatement("SELECT last_insert_rowid()");
            ResultSet rs = row.executeQuery();

            connection.commit();

            pallet = new Pallet();
            pallet.setId(rs.getLong(1));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Could not create pallet. See error log for more information");
        }
        return pallet;
    }

    private void parseResults(PreparedStatement ps, List<Pallet> palletList) throws SQLException {
        ResultSet rs = ps.executeQuery();
        // next() returns true if there were more rows found
        while (rs.next()) {
            palletList.add(new Pallet(rs.getInt("id"), rs.getInt("amount"),
                    LocalDate.parse(rs.getString("productionDate")), rs.getBoolean("isBlocked"),
                    rs.getString("location"), rs.getTimestamp("deliveryTime")));
        }
        // If it is empty, then throw an error
        if (palletList.isEmpty()) {
            throw new NoSuchElementException("Elements not found");
        }
    }
}