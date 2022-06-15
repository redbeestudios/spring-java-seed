package com.redbee.msseed.adapter.kafka;

import com.redbee.msseed.adapter.kafka.exeption.NotificationException;
import com.redbee.msseed.adapter.kafka.model.SeedNotificationModel;
import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.properties.model.KafkaProperties;
import com.redbee.msseed.config.properties.model.TopicProperties;
import com.redbee.msseed.domain.Seed;
import org.apache.kafka.common.errors.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import com.redbee.msseed.config.properties.SpringConfigurationProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName( "Kafka adapter test")
@ExtendWith(MockitoExtension.class)
public class KafkaAdapterTest {


    private KafkaTemplate<String, SeedNotificationModel> kafkaTemplate =mock(KafkaTemplate.class);

    private SpringConfigurationProperties springConfigurationProperties = mock(SpringConfigurationProperties.class);

    @Test
    @DisplayName("Cuando intento notificar un seed creado y kafka responde una Excepcion, entonces espero que me devuelva un NotificationException")
    void testNotificateAuthenticationException(){
        Seed seed = buildSeedMock();
        when(springConfigurationProperties.getKafka()).thenReturn(new KafkaProperties("192.68.0.1",new TopicProperties("seed.creation")));
        when(kafkaTemplate.send(anyString(),any(SeedNotificationModel.class))).thenThrow(new AuthenticationException("test"));
        KafkaAdapter  adapter = new KafkaAdapter(kafkaTemplate, springConfigurationProperties);

        //when
        Throwable thrown = catchThrowable(() -> adapter.notify(seed, UUID.randomUUID()));
        //then

        assertThat(thrown)
                .isInstanceOf(NotificationException.class)
                .hasMessage(ErrorCode.KAFKA_EXCEPTION.getDetail(), "");
    }


    private Seed buildSeedMock(){
        return  Seed
                .builder()
                .seedId("1")
                .seedAmount(new BigDecimal(1))
                .seedDate(LocalDateTime.now())
                .build();
    }
}
