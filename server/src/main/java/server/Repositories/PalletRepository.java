package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.Pallet;
import server.Models.RawMaterial;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            List<Pallet> palletList;
            palletList = this.parseResults(ps);
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
    public List<Pallet> getPallets() {
        String query = "SELECT * FROM pallets";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            palletList = this.parseResults(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    /**
     * Retrieve pallets between two dates found in the database.
     *
     * @param startDate Date to search from.
     * @param endDate   Date to search to.
     * @return The found pallets in a list.
     */
    public List<Pallet> getPallets(LocalDate startDate, LocalDate endDate) {
        String query = "SELECT * FROM pallets WHERE productionDate BETWEEN ? AND ?";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(startDate));
            ps.setString(2, String.valueOf(endDate));
            palletList = this.parseResults(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    /**
     * Retrieve pallets which are either blocked or not.
     *
     * @param isBlocked Status of blockage.
     * @return The found pallets.
     */
    public List<Pallet> getPallets(boolean isBlocked) {
        String query = "SELECT * FROM pallets WHERE isBlocked = ?";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, isBlocked);
            palletList = this.parseResults(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    /**
     * Retrieve pallets found in the database from a given customer ID.
     *
     * @param id Recipe ID.
     * @return The found pallets.
     */
    public List<Pallet> getPallets(Integer id) {
        String query = "SELECT pallets.id, productionDate, isBlocked, location, deliveryTime, recipeId FROM pallets JOIN recipes ON recipes.id = pallets.recipeId WHERE pallets.recipeId = ?";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            palletList = this.parseResults(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palletList;
    }

    /**
     * Retrieve pallets found in the database from a given customer ID.
     *
     * @param id Customer ID.
     * @return The found pallets.
     */
    public List<Pallet> getPalletsByBlockStatus(Integer id) {
        String query = "SELECT pallets.id, productionDate, isBlocked, location, deliveryTime, orderId " +
                "FROM pallets " +
                "JOIN orders ON orders.id = pallets.orderId " +
                "WHERE orders.customerId = ? AND pallets.deliveryTime < CURRENT_TIMESTAMP";
        List<Pallet> palletList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            palletList = this.parseResults(ps);
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
        String query = "INSERT INTO pallets (productionDate, isBlocked, location, deliveryTime, recipeId, orderId)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Pallet pallet = new Pallet();
        if (this.updateRawMaterialAmount(recipeId)) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                ps.setObject(1, palletToBeCreated.getProductionDate());
                ps.setBoolean(2, palletToBeCreated.isBlocked());
                ps.setString(3, palletToBeCreated.getLocation());
                ps.setTimestamp(4, palletToBeCreated.getDeliveryTime());
                ps.setInt(5, recipeId);
                ps.setInt(6, orderId);

                ps.executeUpdate();

                PreparedStatement row = connection.prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = row.executeQuery();

                connection.commit();
                if (rs.next()) {
                    pallet.setId(rs.getLong(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();

                // Return friendly error. The regex will try to match the sqlexception. Usually, sqllite exceptions are
                // of the type '[SQLITE_CONSTRAINT]  Abort due to constraint violation (foreign key constraint failed)'
                // The regex will group the exception code (SQLITE_CONSTRAINT) and the corresponding message.
                // This allows us to show different errors based on the error.
                String regex = "\\[(SQLITE_[A-Z]+)\\]\\s+(.*)";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(e.getLocalizedMessage());
                m.find();

                if (m.group(1).equals("SQLITE_CONSTRAINT")) {
                    throw new Error("Could not create pallet." + m.group(2));
                }

                // Generic error if we don't have anything specific to show.
                throw new Error("Could not create pallet. See error log for more information");
            }
        }
        return pallet;
    }

    /**
     * Update amount on rawMaterials over one transaction.
     *
     * @param recipeId Recipe ID.
     * @return If it succeeded.
     */
   private boolean updateRawMaterialAmount(int recipeId) {
        String rawMaterialsToUpdate = "SELECT rawMaterials.Id AS rmId,  recipeIngredients.amount AS riAmount " +
                "FROM recipeIngredients JOIN rawMaterials on rawMaterials.id = recipeIngredients.rawMaterialId " +
                "WHERE recipeIngredients.recipeId = ?";
        String updateRawMaterial = "UPDATE rawMaterials SET amount = amount - 360 * ? WHERE rawMaterials.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(rawMaterialsToUpdate)) {
            connection.setAutoCommit(false);
            ps.setInt(1, recipeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement(updateRawMaterial);
                ps2.setInt(1, rs.getInt("riAmount"));
                ps2.setInt(2, rs.getInt("rmId"));
                ps2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Get latest inserted element.
     *
     * @return The row id of the last inserted element.
     */
    private long getLastInsertedRowId() {

        try (PreparedStatement ps = connection.prepareStatement("SELECT last_insert_rowid()")) {
            ResultSet rs = ps.executeQuery();
            return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Update block status.
     *
     * @param id        Pallet ID.
     * @param isBlocked New block status.
     * @return Changed pallet.
     */
    public Pallet updatePalletBlockStatus(int id, boolean isBlocked) {
        String query = "UPDATE pallets SET isBlocked = ? WHERE id = ?";
        Pallet pallet;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, isBlocked);
            ps.setInt(2, id);
            ps.executeUpdate();
            pallet = getPallet(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Could not update pallet. See error log for more information.");
        }
        return pallet;
    }

    /**
     * Update block status on multiple pallets.
     *
     * @param startDate Date to search from.
     * @param endDate   Date to search to.
     * @param id        Recipe ID.
     * @param isBlocked New block status.
     * @return Changed pallets.
     */
    public int updateMultipleBlockStatus(LocalDate startDate, LocalDate endDate, int id, boolean isBlocked) {
        String query = "UPDATE pallets SET isBlocked = ? WHERE recipeId = ? AND productionDate BETWEEN ? AND ?";
        int updatedRows = 0;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, isBlocked);
            ps.setInt(2, id);
            ps.setString(3, String.valueOf(startDate));
            ps.setString(4, String.valueOf(endDate));
            updatedRows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedRows;
    }

    /**
     * Execute the query and listing the result.
     *
     * @param ps The query as a prepared statement.
     * @return Retrieved pallets from the database.
     * @throws SQLException Throws something goes wrong with the query.
     */
    private List<Pallet> parseResults(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        List<Pallet> palletList = new ArrayList<>();
        // next() returns true if there were more rows found
        while (rs.next()) {
            palletList.add(new Pallet(rs.getInt("id"), LocalDate.parse(rs.getString("productionDate")),
                    rs.getBoolean("isBlocked"), rs.getString("location"),
                    rs.getTimestamp("deliveryTime")));
        }

        if (palletList.isEmpty()) {
            throw new NoSuchElementException();
        }

        return palletList;
    }
}