package com.tinywebgears.gaedownload.core.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class MyFile implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String username;

	@Persistent
	Blob file;

	@Persistent
	private Key ownerKey;

	public MyFile() {
	}

	public MyFile(String name, String username, Blob file) {
		this.name = name;
		this.username = username;
		this.file = file;
	}

	public Long getKey() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return username;
	}

	public Blob getFile() {
		return file;
	}

	public Key getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(Key ownerKey) {
		this.ownerKey = ownerKey;
	}
}
