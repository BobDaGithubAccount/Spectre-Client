package models;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import renderEngine.Loader;
import textures.ModelTexture;

public class RawModels {

	public static float[] vertices = {			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,0.5f,-0.5f,		
			
			-0.5f,0.5f,0.5f,	
			-0.5f,-0.5f,0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			0.5f,0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			-0.5f,-0.5f,0.5f,	
			-0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,0.5f,
			-0.5f,0.5f,-0.5f,
			0.5f,0.5f,-0.5f,
			0.5f,0.5f,0.5f,
			
			-0.5f,-0.5f,0.5f,
			-0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,0.5f
			
	};
	
	public static float[] textureCoords = {
			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0

			
	};
	
	public static int[] indices = {
			0,1,3,	
			3,1,2,	
			4,5,7,
			7,5,6,
			8,9,11,
			11,9,10,
			12,13,15,
			15,13,14,	
			16,17,19,
			19,17,18,
			20,21,23,
			23,21,22

	};
	
	public static float[] normals = {			
			1f,1f,1f,	
			1f,1f,1f,
			1f,1f,1f,
			1f,1f,1f,
			
			1f,1f,1f,	
			1f,1f,1f,
			1f,1f,1f,
			1f,1f,1f,
			
			1f,1f,1f,	
			1f,1f,1f,
			1f,1f,1f,
			1f,1f,1f,
			
			1f,1f,1f,	
			1f,1f,1f,
			1f,1f,1f,
			1f,1f,1f,
			
			1f,1f,1f,	
			1f,1f,1f,
			1f,1f,1f,
			1f,1f,1f,
			
			1f,1f,1f,	
			1f,1f,1f,
			1f,1f,1f,
			1f,1f,1f,
			
	};

	
	public static Entity getErrorCube(Vector3f location, Vector3f rotation) {
		RawModel model = Loader.loadToVAO(vertices, indices, textureCoords, normals);
		ModelTexture texture = new ModelTexture(Loader.loadTexture("essential"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, location, rotation, 1f);
		return entity;
	}
	
	public static Entity getSkyboxCube(Vector3f location, Vector3f rotation) {
		RawModel model = Loader.loadToVAO(vertices, indices, textureCoords, normals);
		ModelTexture texture = new ModelTexture(Loader.loadTexture("skybox"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, location, rotation, 1024f);
		return entity;

	}
	
}
