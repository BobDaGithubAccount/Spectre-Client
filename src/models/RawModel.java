package models;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	private float[] positions;
	private int[] indices;
	private float[] textureCoords;
	private float[] normals;
	
	public RawModel(int vaoID, int vertexCount, float[] positions, int[] indices, float[] textureCoords, float[] normals) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.positions = positions;
		this.indices = indices;
		this.textureCoords = textureCoords;
		this.normals = normals;
	}

	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public float[] getPositions() {
		return positions;
	}

	public void setPositions(float[] positions) {
		this.positions = positions;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}
	
}
