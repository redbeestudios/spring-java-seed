package com.redbee.msseed.adapter.redis;

import com.redbee.msseed.adapter.redis.model.ConfirmationSeedModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.redbee.msseed.domain.Seed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName( "Confirmation redis  adapter test")
@ExtendWith(MockitoExtension.class)
public class ConfirmationRedisAdapterTest {

    @Mock
    private ConfirmationSeedRedisRepository confirmationUserRedisRepository;


    @Test
    @DisplayName( "Cuando intento obtener una confirmacion de redis por id y la confirmacion existe, entonces espero que me devuelva el objeto de confirmacion")
    void findById(){

        UUID uuid = UUID.randomUUID();
        ConfirmationSeedModel confirmationUserModel = getRedisMock(uuid.toString(),uuid);
        when( confirmationUserRedisRepository.findById(any(UUID.class))).thenReturn(Optional.of(confirmationUserModel));

        ConfirmationRedisAdapter adapter = new ConfirmationRedisAdapter(confirmationUserRedisRepository);
        Seed seed = adapter.findById(uuid.toString());

        assertEquals(seed.getSeedAmount(), new BigDecimal(confirmationUserModel.getAmount()));
        assertEquals(seed.getSeedId(),confirmationUserModel.getUuid().toString());
        assertEquals(seed.getSeedDate(), LocalDateTime.parse(confirmationUserModel.getDate(), DateTimeFormatter.ISO_DATE_TIME));

    }

    private ConfirmationSeedModel getRedisMock( String id, UUID uuid){
        return ConfirmationSeedModel.builder()
                .uuid(uuid)
                .amount("1")
                .date("2020-12-21T00:00:00")
                .build();
    }
}
