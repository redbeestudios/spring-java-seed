package com.redbee.msseed.adapter.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import com.redbee.msseed.domain.Seed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("ms-seed:seed-confirmation")
public class ConfirmationSeedModel {

    @Id
    private UUID uuid;
    private String amount;
    private String date;


    public static ConfirmationSeedModel fromDomain(Seed seed, UUID uuid){
        return ConfirmationSeedModel.builder()
                .amount(seed.getSeedAmount().toString())
                .date(seed.getSeedDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .uuid(uuid)
                .build();
    }

    public static Seed toDomain(ConfirmationSeedModel confirmationSeedModel){
        return Seed.builder()
                .seedAmount(new BigDecimal(confirmationSeedModel.getAmount()))
                .seedDate(LocalDateTime.parse(confirmationSeedModel.getDate(), DateTimeFormatter.ISO_DATE_TIME))
                .seedId(confirmationSeedModel.getUuid().toString())
                .build();
    }

}
