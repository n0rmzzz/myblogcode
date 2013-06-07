package com.test.entity;

import com.test.type.UUID;
import org.springframework.stereotype.Component;

@Component("serverUuidFactory")
public final class ServerUUIDFactory implements UUIDFactory
{
    public ServerUUIDFactory()
    {
    }

    public UUID generate()
    {
        java.util.UUID tmp = java.util.UUID.randomUUID();
        return new UUID(tmp.getMostSignificantBits(), tmp.getLeastSignificantBits());
    }
}
