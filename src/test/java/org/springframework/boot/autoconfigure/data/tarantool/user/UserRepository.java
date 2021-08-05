package org.springframework.boot.autoconfigure.data.tarantool.user;

import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.util.UUID;

public interface UserRepository extends TarantoolRepository<User, UUID> {
}
