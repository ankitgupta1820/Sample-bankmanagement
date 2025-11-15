package bank.management.system;

import java.sql.*;

public class Conn {
    private Connection c;
    private Statement s;

    public Conn() {
        try {
            // Connect to MySQL
            c = DriverManager.getConnection(
                    "jdbc:mysql:///bankmanagementsystem",
                    "root",
                    "MyNewPass123!"
            );

            // Disable auto-commit → required for transaction management
            c.setAutoCommit(false);

            s = c.createStatement();
        } catch (Exception e) {
            System.out.println("Database Connection Error: " + e);
        }
    }

    // Getters
    public Statement getStatement() {
        return s;
    }

    public Connection getConnection() {
        return c;
    }

    // ✅ Commit transaction (ensures Atomicity + Durability)
    public void commit() {
        try {
            if (c != null) c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Rollback transaction if something fails (ensures Atomicity)
    public void rollback() {
        try {
            if (c != null) c.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Close resources
    public void close() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

