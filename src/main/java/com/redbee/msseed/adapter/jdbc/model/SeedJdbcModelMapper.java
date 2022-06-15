package com.redbee.msseed.adapter.jdbc.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SeedJdbcModelMapper implements RowMapper<SeedJdbcModel> {
    @Override
    public SeedJdbcModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return SeedJdbcModel.builder()
                .fechaSeed(getDate(rs, "Fecha_Seed"))
                .montoSeed(rs.getBigDecimal("Monto_seed"))
                .nroSeed(rs.getString("Nro_Seed"))
                .build();
    }

    private LocalDateTime getDate(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
