package entities;

import org.lwjgl.util.vector.Vector3f;

public class Entity {

	private String parent;
	private String name;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Entity(String name, String parent, Vector3f position, Vector3f rotation, float scale) {
		this.name = name;
		this.parent = parent;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParent() {
		return this.parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}
	
	public void increaseRoation(float dx, float dy, float dz) {
		this.rotation.x+=dx;
		this.rotation.y+=dy;
		this.rotation.z+=dz;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
