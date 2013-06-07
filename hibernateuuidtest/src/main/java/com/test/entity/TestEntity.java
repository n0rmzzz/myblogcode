package com.test.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/*
<pre>
select * from TEST_TABLE;

CREATE TABLE "TEST_TABLE"
   (    "ID" RAW(16),
        "NEW_ID" NUMBER(10,0),
        "LOCKVERSION" NUMBER(10,0),
        "DESCRIPTION" VARCHAR2(255 CHAR),
        "NAME" VARCHAR2(255 CHAR)
   )

Insert into TEST_TABLE (ID,NEW_ID,LOCKVERSION,DESCRIPTION,NAME) values ('F45F269DE035453CB5E8950CC8C5391D',101, 0,'test 1','First record');
Insert into TEST_TABLE (ID,NEW_ID,LOCKVERSION,DESCRIPTION,NAME) values ('E0074DDD2C0C4B21A26D6A0BEEC49F80',102, 0,'test 2','Second record');
</pre>
 */

@Entity
@Table(name = "TEST_TABLE")
public class TestEntity extends JpaDomainEntity
{
    public TestEntity()
    {
    }

    @Override
    public String toString()
    {
        return "[Test Entity: [id: " + getId() + " name: " + getName() + " description: " + getDescription() + "]]";
    }
}
