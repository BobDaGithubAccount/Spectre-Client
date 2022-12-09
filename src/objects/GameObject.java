package objects;

import entities.Entity;

public abstract class GameObject {

	private String name;
	private Entity entity;
	
	public GameObject(String name, Entity entity) {
		this.name = name;
		this.entity = entity;
	}

	public abstract void run();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
