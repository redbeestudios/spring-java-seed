package com.redbee.msseed.application.port.out;

import org.springframework.data.domain.Page;
import com.redbee.msseed.domain.Seed;

public interface SeedRepository {
    Page<Seed> getByFilters(int page, int size);
}
