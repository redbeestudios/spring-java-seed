package com.redbee.msseed.adapter.kafka.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.redbee.msseed.domain.Seed;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SeedNotificationModel {

    private UUID uuid;
    private String amount;
    private String date;

    public static SeedNotificationModel fromDomain(Seed seed){
        return SeedNotificationModel.builder()
                .amount(seed.getSeedAmount().toString())
                .date(seed.getSeedDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
