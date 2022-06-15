package com.redbee.msseed.adapter.jdbc;

import com.redbee.msseed.application.port.out.SeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.redbee.msseed.adapter.jdbc.model.SeedJdbcModel;
import com.redbee.msseed.adapter.jdbc.model.SeedJdbcModelMapper;
import com.redbee.msseed.domain.Seed;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class SeedJdbcAdapter implements SeedRepository {


    private JdbcTemplate jdbcTemplate;
    private static final String PATH_GET_SEEDS = "sql/getSeed.sql";
    private String getSeedsQuery;

    SeedJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.getSeedsQuery = SqlReader.readSql(PATH_GET_SEEDS);
    }

    @Override
    public Page<Seed> getByFilters(int page, int size) {

        String countQuery = buildCountQuery();

        String selectQuery = buildSelectQuery(page, size);

        log.info("Ejecutando query " + selectQuery);

        Pageable pageable = PageRequest.of(page, size);

        int totalElements = jdbcTemplate.queryForObject(countQuery, (rs, rowNum) -> rs.getInt(1));
        List<Seed> results = jdbcTemplate.query(selectQuery, new SeedJdbcModelMapper())
                .stream()
                .map(SeedJdbcModel::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, totalElements);
    }

    public String buildCountQuery() {
        return "SELECT COUNT(*) FROM " + this.getSeedsQuery;
    }

    public String buildSelectQuery(int page, int size) {
        StringBuilder sb = new StringBuilder(this.getSeedsQuery);

        sb.append(" ORDER BY Fecha_Alta_Movim DESC");
        int offset = page * size;
        sb.append(" OFFSET ").append(offset).append(" ROWS");
        sb.append(" FETCH NEXT ").append(size).append(" ROWS ONLY");

        return "SELECT * FROM " + sb.toString();
    }
}
