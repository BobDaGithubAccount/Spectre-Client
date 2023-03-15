package renderEngine.sourceEngineCompatibility.structs;

import org.lwjgl.util.vector.Vector3f;

public class Plane {
	private Vector3f vec;
	private float dist;
	private int type;
	public Plane(Vector3f vec, float dist, int type) {
		super();
		this.vec = vec;
		this.dist = dist;
		this.type = type;
	}
	public Vector3f getVec() {
		return vec;
	}
	public void setVec(Vector3f vec) {
		this.vec = vec;
	}
	public float getDist() {
		return dist;
	}
	public void setDist(float dist) {
		this.dist = dist;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setVec(float x, float y, float z) {
		vec = new Vector3f(x,y,z);
	}
}
