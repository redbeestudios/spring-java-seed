package com.redbee.msseed.application.usecase;

import com.redbee.msseed.application.port.out.SeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.redbee.msseed.application.port.in.GetSeedQuery;
import com.redbee.msseed.domain.Seed;

import java.util.Map;

@Component
@Slf4j
public class GetSeedUseCase implements GetSeedQuery {

    private SeedRepository seedRepository;

    public GetSeedUseCase(
        SeedRepository seedRepository
    ) {
        this.seedRepository = seedRepository;
    }

    @Override
    public Page<Seed> execute(int page, int size, Map<String,String> mappedFilters) {
        log.info("Ejecutando GetSeedUseCase con page {} size {} filter {} y mappedFilters {}", page, size, mappedFilters);
        Page<Seed> seeds = seedRepository.getByFilters(page, size);
        log.info("Seeds: {}", seeds.getContent());
        return seeds;
    }
}
