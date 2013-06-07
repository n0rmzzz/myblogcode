package com.test.entity;

import com.test.type.UUID;
import com.test.type.UUIDUserType;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class JpaDomainEntity implements DomainEntity
{
    @Id
    @Type(type = UUIDUserType.CLASS_NAME)
    @Column(columnDefinition = "raw(16)")
    private UUID id;

    @Version
    @Column(nullable = false)
    private int lockVersion = 0;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String description;

    private static transient UUIDFactory uuidFactory = new ServerUUIDFactory();

    public JpaDomainEntity()
    {
        this.id = uuidFactory == null ? null : uuidFactory.generate();
    }

    protected JpaDomainEntity(UUID id)
    {
        this.id = id;
    }

    @Override
    public int hashCode()
    {
        return getId() == null ? 1 : getId().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this.id == null)
            return false;
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof JpaDomainEntity))
            return false;

        final JpaDomainEntity other = (JpaDomainEntity)obj;
        return getId().equals(other.getId());
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
}
