package com.redbee.msseed.application.port.in;

import org.springframework.data.domain.Page;
import com.redbee.msseed.domain.Seed;

import java.util.Map;

public interface GetSeedQuery {
    Page<Seed> execute(int page, int size, Map<String, String> filters);
}
