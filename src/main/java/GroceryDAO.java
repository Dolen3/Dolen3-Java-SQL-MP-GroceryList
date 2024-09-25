import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO is a class that mediates the transformation of data between the format of objects in Java to rows in a
 * database. The methods here are mostly filled out, you will just need to add a SQL statement.
 *
 * We may assume that the database has already created a table named 'Grocery'.
 * It contains a single column, named 'grocery_name' of type varchar(255).
 * The table will be automatically created by the databaseSetup() method in GroceryMain.java.
 */
public class GroceryDAO {

    
    private static Connection connection = null;

    static {
        connection = ConnectionUtil.getConnection();
    }

    /**
     * Select all of the rows of the grocery table.
     * @return a List of all the groceries contained within the database.
     */
    public List<String> getAllGroceries() {
        List<String> groceries = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT grocery_name FROM grocery";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                groceries.add(rs.getString("grocery_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
        }
        return groceries;
    }

    /**
     * Insert a new row into the grocery table.
     * @param groceryName the name of the grocery passed in from the GroceryService.
     */
    public void addGrocery(String groceryName) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO grocery (grocery_name) VALUES (?)";
            ps = connection.prepareStatement(sql);

            ps.setString(1, groceryName);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            // Close resources but not the connection
            if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
        }
    }
}
