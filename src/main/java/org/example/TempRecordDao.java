package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDao {
    private Connection connection;


    public TempRecordDao() {}

    public TempRecordDao(Connection connection) {
        this.connection = connection;
    }


    private Connection getConnection() throws SQLException {
        return (this.connection != null) ? this.connection : DBConnection.getConnection();
    }

    private int getUnitId(Connection conn, String unitCode) throws SQLException {
        String sql = "SELECT id FROM temperature_units WHERE unit_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, unitCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Lämpötilayksikköä ei löytynyt tietokannasta: " + unitCode);
    }

    public boolean save(TempRecord record) {
        String sql = "INSERT INTO temp_records (input_value, input_unit_id, converted_value, converted_unit_id) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = getConnection();
            int inputUnitId = getUnitId(conn, record.getInputUnit());
            int convertedUnitId = getUnitId(conn, record.getConvertedUnit());

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, record.getInputValue());
                stmt.setInt(2, inputUnitId);
                stmt.setDouble(3, record.getConvertedValue());
                stmt.setInt(4, convertedUnitId);

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TempRecord> getAll() {
        List<TempRecord> records = new ArrayList<>();
        String sql = """
                SELECT r.id, r.input_value, u1.unit_code AS input_unit, 
                       r.converted_value, u2.unit_code AS converted_unit 
                FROM temp_records r
                JOIN temperature_units u1 ON r.input_unit_id = u1.id
                JOIN temperature_units u2 ON r.converted_unit_id = u2.id
                ORDER BY r.id DESC
            """;

        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    records.add(new TempRecord(
                            rs.getInt("id"),
                            rs.getDouble("input_value"),
                            rs.getString("input_unit"),
                            rs.getDouble("converted_value"),
                            rs.getString("converted_unit")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}