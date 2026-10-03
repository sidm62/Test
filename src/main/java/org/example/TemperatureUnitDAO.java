package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureUnitDAO {

    private Connection connection;

    public TemperatureUnitDAO() {}

    public TemperatureUnitDAO(Connection connection) {
        this.connection = connection;
    }

    private Connection getConnection() throws SQLException {
        return (this.connection != null) ? this.connection : DBConnection.getConnection();
    }

    public TemperatureUnit getByCode(String unitCode) throws SQLException {
        String sql = "SELECT id, unit_code, unit_name FROM temperature_units WHERE unit_code = ?";
        Connection conn = getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, unitCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TemperatureUnit(
                            rs.getInt("id"),
                            rs.getString("unit_code"),
                            rs.getString("unit_name")
                    );
                }
            }
        }
        return null;
    }

    public List<TemperatureUnit> getAll() {
        List<TemperatureUnit> units = new ArrayList<>();
        String sql = "SELECT id, unit_code, unit_name FROM temperature_units";

        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    units.add(new TemperatureUnit(
                            rs.getInt("id"),
                            rs.getString("unit_code"),
                            rs.getString("unit_name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }
}