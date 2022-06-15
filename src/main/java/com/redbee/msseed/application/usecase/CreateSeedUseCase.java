package com.redbee.msseed.application.usecase;

import com.redbee.msseed.application.exception.BusinessException;
import com.redbee.msseed.application.port.out.ConfirmationRepository;
import com.redbee.msseed.application.port.out.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.redbee.msseed.adapter.kafka.exeption.NotificationException;
import com.redbee.msseed.application.port.in.CreateSeedCommand;
import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.domain.Seed;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class CreateSeedUseCase implements CreateSeedCommand {

    private final NotificationRepository notificationRepository;
    private final ConfirmationRepository confirmationRepository;

    public CreateSeedUseCase(NotificationRepository notificationRepository,
                             ConfirmationRepository confirmationRepository) {
        this.notificationRepository = notificationRepository;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public Seed createSeed(Command command) {

        Seed seed = Seed.builder()
                .seedAmount(command.getAmount())
                .seedDate(command.getDate())
                .build();

        Optional.ofNullable(command.getAmount()).orElseThrow(() -> new BusinessException(ErrorCode.AMOUNT_INVALID));

        try {
            UUID uuid = UUID.randomUUID();
            confirmationRepository.save(seed, uuid);
            notificationRepository.notify(seed, uuid);
        } catch (NotificationException ne) {
            log.error("Error al notificar el seed creado");
        }

        return seed;
    }
}
