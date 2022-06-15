package com.redbee.msseed.application.port.out;

import com.redbee.msseed.domain.Seed;

import java.util.UUID;

public interface NotificationRepository {

    void notify(Seed seed, UUID notificationId);
}
