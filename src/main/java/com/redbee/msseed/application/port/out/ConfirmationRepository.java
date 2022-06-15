package com.redbee.msseed.application.port.out;

import com.redbee.msseed.domain.Seed;

import java.util.UUID;

public interface ConfirmationRepository {

    void save(Seed seed, UUID uuid);

    Seed findById(String id);
}
