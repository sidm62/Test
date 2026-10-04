package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TempRecordDaoTest {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private TempRecordDao dao;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        dao = new TempRecordDao(connection);
    }

    @Test
    public void testSaveSuccess() throws SQLException {
        // Mockataan yksiköiden haku (C ja F) ja lisäyksen onnistuminen
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(statement.executeUpdate()).thenReturn(1);

        TempRecord record = new TempRecord(0, 20.0, "C", 68.0, "F");

        assertTrue(dao.save(record));
    }

    @Test
    public void testSaveFailureWhenUnitNotFound() throws SQLException {

        when(resultSet.next()).thenReturn(false);

        TempRecord record = new TempRecord(0, 20.0, "UNKNOWN", 68.0, "F");

        assertFalse(dao.save(record));
    }

    @Test
    public void testGetAll() throws SQLException {

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getDouble("input_value")).thenReturn(20.0);
        when(resultSet.getString("input_unit")).thenReturn("C");
        when(resultSet.getDouble("converted_value")).thenReturn(68.0);
        when(resultSet.getString("converted_unit")).thenReturn("F");

        List<TempRecord> list = dao.getAll();

        assertEquals(1, list.size());
        assertEquals("C", list.get(0).getInputUnit());
    }
}