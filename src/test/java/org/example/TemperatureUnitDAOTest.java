package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TemperatureUnitDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private TemperatureUnitDAO dao;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        dao = new TemperatureUnitDAO(mockConnection);
    }

    @Test
    @DisplayName("getByCode palauttaa yksikön kun se löytyy")
    public void testGetByCodeFound() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("unit_code")).thenReturn("C");
        when(mockResultSet.getString("unit_name")).thenReturn("Celsius");

        TemperatureUnit unit = dao.getByCode("C");

        assertNotNull(unit);
        assertEquals(1, unit.getId());
        assertEquals("C", unit.getUnitCode());
        assertEquals("Celsius", unit.getUnitName());
        verify(mockStatement).setString(1, "C");
    }

    @Test
    @DisplayName("getByCode palauttaa null kun yksikköä ei löydy")
    public void testGetByCodeNotFound() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        TemperatureUnit unit = dao.getByCode("UNKNOWN");

        assertNull(unit);
    }

    @Test
    @DisplayName("getAll palauttaa listan kaikista yksiköistä")
    public void testGetAll() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false); // Kaksi riviä tietokannassa

        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("unit_code")).thenReturn("C", "F");
        when(mockResultSet.getString("unit_name")).thenReturn("Celsius", "Fahrenheit");

        List<TemperatureUnit> units = dao.getAll();

        assertEquals(2, units.size());
        assertEquals("C", units.get(0).getUnitCode());
        assertEquals("F", units.get(1).getUnitCode());
    }
}