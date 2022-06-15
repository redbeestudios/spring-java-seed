package com.redbee.msseed.adapter.controller;

import com.redbee.msseed.adapter.controller.model.SeedRest;
import com.redbee.msseed.application.port.in.GetSeedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.redbee.msseed.domain.Seed;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/ms-seed/seeds")
public class        SeedControllerAdapter {

    private GetSeedQuery getSeedQuery;

    public SeedControllerAdapter(GetSeedQuery getSeedQuery) {
        this.getSeedQuery = getSeedQuery;
    }
    @GetMapping
    public Page<SeedRest> getSeeds(
        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
        @RequestParam(name = "size", required = false, defaultValue = "50") int size,
        @RequestParam Map<String,String> queryParams
    ) {
        log.info("Ejecucion de get '/seeds' con queryParams: {}", queryParams.toString());

        Page<Seed> seeds = getSeedQuery.execute(page, size, queryParams);
        Page<SeedRest> restSeeds = seeds.map(SeedRest::toChargebackRest);

        log.info("Seeds obtenidas: {}", restSeeds.getContent());

        return restSeeds;
    }

}
