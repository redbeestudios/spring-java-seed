package com.redbee.msseed.adapter.controller.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;
import com.redbee.msseed.domain.Seed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SeedRest {

    String seedId;
    LocalDateTime seedDate;
    BigDecimal seedAmount;

    public static SeedRest toChargebackRest(Seed seed) {
        return SeedRest.builder()
                .seedId(seed.getSeedId())
                .seedDate(seed.getSeedDate())
                .seedAmount(seed.getSeedAmount())
                .build();
    }
}
