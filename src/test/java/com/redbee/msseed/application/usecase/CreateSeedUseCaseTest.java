package com.redbee.msseed.application.usecase;

import com.redbee.msseed.application.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import com.redbee.msseed.adapter.kafka.exeption.NotificationException;
import com.redbee.msseed.application.port.in.CreateSeedCommand;
import com.redbee.msseed.application.port.out.ConfirmationRepository;
import com.redbee.msseed.application.port.out.NotificationRepository;
import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.TestConfig;
import com.redbee.msseed.domain.Seed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@DisplayName("Seed UseCase Test")
@ExtendWith(MockitoExtension.class)
@Import({TestConfig.class})
public class CreateSeedUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private ConfirmationRepository confirmationRepository;


    @Test
    @DisplayName("Cuando intento intento crear un seed valido, entonces espero que me devuelva el seed con id")
    void testCreateSeedOk() {

        CreateSeedCommand.Command command = getCommandMockOk();
        Seed expected = createSeedExpected(command);

        doNothing().when(notificationRepository).notify(any(Seed.class), any(UUID.class));
        doNothing().when(confirmationRepository).save(any(Seed.class), any(UUID.class));

        CreateSeedUseCase useCase = new CreateSeedUseCase(notificationRepository, confirmationRepository);

        assertEquals(expected, useCase.createSeed(command));
    }

    @Test
    @DisplayName("Cuando intento crear un seed con el amount que no cumple con la condicion, entonces espero un error ValidationError")
    void testCreateSeedPasswordInvalid() {

        CreateSeedCommand.Command command = getCommandMockInvalid();
        CreateSeedUseCase useCase = new CreateSeedUseCase(notificationRepository, confirmationRepository);

        Throwable thrown = catchThrowable(() -> useCase.createSeed(command));
        assertThat(thrown)
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.AMOUNT_INVALID.getDetail());
    }

    @Test
    @DisplayName("Cuando intento crear un seed valido y la notificiacion da error, entonces espero que me devuelva el seed con id")
    void testCreateSeedNotificationError() {

        CreateSeedCommand.Command command = getCommandMockOk();
        Seed expected = createSeedExpected(command);

        doThrow(new NotificationException(ErrorCode.KAFKA_EXCEPTION)).when(notificationRepository).notify(any(Seed.class), any(UUID.class));
        doNothing().when(confirmationRepository).save(any(Seed.class), any(UUID.class));

        CreateSeedUseCase useCase = new CreateSeedUseCase(notificationRepository, confirmationRepository);

        assertEquals(expected, useCase.createSeed(command));
    }

    @Test
    @DisplayName("Cuando intento crear un seed valido y sucede un error al guardar en redis, entonces espero que me devuelva el seed con id")
    void testCreateSeedConfirmationError() {

        CreateSeedCommand.Command command = getCommandMockOk();
        Seed expected = createSeedExpected(command);

        doThrow(new NotificationException(ErrorCode.KAFKA_EXCEPTION)).when(confirmationRepository).save(any(Seed.class), any(UUID.class));

        CreateSeedUseCase useCase = new CreateSeedUseCase(notificationRepository, confirmationRepository);

        assertEquals(expected, useCase.createSeed(command));
    }

    private CreateSeedCommand.Command getCommandMockOk() {
        return CreateSeedCommand.Command.builder()
                .amount(new BigDecimal(1))
                .date(LocalDateTime.now())
                .build();
    }

    private CreateSeedCommand.Command getCommandMockInvalid() {
        return CreateSeedCommand.Command.builder()
                .amount(null)
                .date(LocalDateTime.now())
                .build();
    }

    private Seed createSeedExpected(CreateSeedCommand.Command command) {
        return Seed.builder()
                .seedAmount(command.getAmount())
                .seedDate(command.getDate())
                .build();
    }
}
