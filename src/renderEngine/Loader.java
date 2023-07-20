package renderEngine;

import java.io.File;
import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.Entity;
import logging.Logger;
import models.RawModel;
import models.RawModels;
import models.TexturedModel;
import textures.ModelTexture;

public class Loader {

	public static Entity loadLevel(String fileName) {
		try {
			File file = new File(Loader.class.getResource("../res/levels/" + fileName + ".level").getFile());
			System.out.println(file);
			Object[] returned = OBJLoader.loadLevelModel(file);
			RawModel model = (RawModel) returned[0];
			ModelTexture texture = new ModelTexture(loadTexture("essential"));
			TexturedModel texturedModel = new TexturedModel(model, texture);
			Entity entity = new Entity(fileName, fileName, new Vector3f(0,0,0), new Vector3f(0,0,0), (float) returned[1]);
			HashMap<String, Entity> instances = new HashMap<String, Entity>();
			instances.put(entity.getName(), entity);
			MasterRenderer.setObject(fileName, new RenderObject(instances, texturedModel));
			return entity;
		}
		catch(Exception e) {
			e.printStackTrace();
			String name = UUID.randomUUID().toString();
			RawModel model1 = RawModels.getErrorCube();
			ModelTexture texture = new ModelTexture(loadTexture("essential"));
			TexturedModel texturedModel = new TexturedModel(model1, texture);
			Entity entity = new Entity(name, name, new Vector3f(0,0,0), new Vector3f(0,0,0), 1f);
			HashMap<String, Entity> instances = new HashMap<String, Entity>();
			instances.put(entity.getName(), entity);
			MasterRenderer.setObject(name, new RenderObject(instances, texturedModel));
			return entity;
		}
	}
	
	public static Entity loadObj(String fileName, Vector3f location, Vector3f rotation, float scale) {
		RawModel model = OBJLoader.loadObjModel(fileName);
		if(model==null) {
			String name = UUID.randomUUID().toString();
			RawModel model1 = RawModels.getErrorCube();
			ModelTexture texture = new ModelTexture(loadTexture("essential"));
			TexturedModel texturedModel = new TexturedModel(model1, texture);
			Entity entity = new Entity(name, name, location, rotation, 1f);
			HashMap<String, Entity> instances = new HashMap<String, Entity>();
			instances.put(entity.getName(), entity);
			MasterRenderer.setObject(name, new RenderObject(instances, texturedModel));
			return entity;
		}
		ModelTexture texture = new ModelTexture(loadTexture(fileName));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(fileName, fileName, location, rotation, 1f);
		HashMap<String, Entity> instances = new HashMap<String, Entity>();
		instances.put(entity.getName(), entity);
		MasterRenderer.setObject(fileName, new RenderObject(instances, texturedModel));
		return entity;
	}
	
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();

	public static RawModel loadToVAO(float[] positions, int[] indices, float[] textureCoords, float[] normals) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length, positions, indices, textureCoords, normals);
	}

	public static void cleanUp() {
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	public static int loadTexture(String fileName) {
		Texture texture = null;
		try {
			File file = new File(Loader.class.getResource("/res/objects/" + fileName + "/" + fileName + ".png").getFile());
			Logger.log(file.getPath());
			texture = TextureLoader.getTexture("PNG", new FileInputStream(file.getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		return textureID;
	}

	private static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private static void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private static void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
