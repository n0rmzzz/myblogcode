package com.test.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class WorkingJpaDomainEntity implements DomainEntity
{
    @Id
    @Column(name = "new_id")
    private Integer id;

    @Version
    @Column(nullable = false)
    private int lockVersion = 0;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String description;

    public WorkingJpaDomainEntity()
    {
        this.id = 0;
    }

    protected WorkingJpaDomainEntity(Integer id)
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
        if (!(obj instanceof WorkingJpaDomainEntity))
            return false;

        final WorkingJpaDomainEntity other = (WorkingJpaDomainEntity)obj;
        return getId().equals(other.getId());
    }

    public Integer getId()
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
