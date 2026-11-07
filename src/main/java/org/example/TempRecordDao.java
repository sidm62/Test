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
        String sql = "SELECT * FROM temp_records ORDER BY id DESC";

        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    records.add(new TempRecord(
                            rs.getInt("id"),
                            rs.getDouble("input_value"),
                            rs.getString("input_unit_id"),
                            rs.getDouble("converted_value"),
                            rs.getString("converted_unit_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Virhe haettaessa tietoja: " + e.getMessage());
        }
        return records;
    }

    public List<UnitDTO> getUnitsByLanguage(String langCode) {
        List<UnitDTO> list = new ArrayList<>();
        String sql = "SELECT u.id, u.unit_code, t.name, t.description " +
                "FROM temperature_units u " +
                "JOIN temperature_unit_translations t ON u.id = t.unit_id " +
                "WHERE t.lang = ?";

        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, langCode);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(new UnitDTO(
                                rs.getInt("id"),
                                rs.getString("unit_code"),
                                rs.getString("name"),
                                rs.getString("description")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Virhe haettaessa yksiköitä: " + e.getMessage());
        }
        return list;
    }

    public String getTranslation(String key, String lang) {
        String sql = "SELECT translation_text FROM ui_translations WHERE translation_key = ? AND lang = ?";
        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, key);
                stmt.setString(2, lang);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("translation_text");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Virhe haettaessa käännöstä avaimelle '" + key + "': " + e.getMessage());
        }

        return key;
    }
}