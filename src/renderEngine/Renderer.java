package renderEngine;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;
import toolbox.Maths;

public class Renderer {

	private static final float FOV = 60f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f; /***maximum distance from camera one can see */

	public static void initRenderer() {
		createProjectionMatrix();
		MasterRenderer.shader.start();
		MasterRenderer.shader.loadProjectionMatrix(projectionMatrix);
		MasterRenderer.shader.stop();
	}
	
	private static Matrix4f projectionMatrix;
	
	public static void prepare() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void render(Map<TexturedModel, List<Entity>> entites) {
		for(Entry<TexturedModel, List<Entity>> e : entites.entrySet()) {
			prepareTexturedModel(e.getKey());
			List<Entity> list = e.getValue();
			for(Entity entity: list) {
				prepareInstance(entity);
				RawModel model = entity.getModel().getRawModel();
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private static void prepareTexturedModel(TexturedModel tModel) {
		RawModel model = tModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = tModel.getTexture();
		MasterRenderer.shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tModel.getTexture().getID());
	}
	
	private static void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private static void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(), entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
		MasterRenderer.shader.loadTransformationMatrix(transformationMatrix);
	}

//	public void render(Map<TexturedModel, List<Entity>> entities) {
//		for(Entry<TexturedModel, List<Entity>> e : entities.entrySet()) {
//			TexturedModel tModel = e.get.getModel();
//			RawModel model = tModel.getRawModel();
//			GL30.glBindVertexArray(model.getVaoID());
//			GL20.glEnableVertexAttribArray(0);
//			GL20.glEnableVertexAttribArray(1);
//			GL20.glEnableVertexAttribArray(2);
//			
//			Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(), entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
//			MasterRenderer.shader.loadTransformationMatrix(transformationMatrix);
//			
//			ModelTexture texture = tModel.getTexture();
//			MasterRenderer.shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
//			
//			GL13.glActiveTexture(GL13.GL_TEXTURE0);
//			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tModel.getTexture().getID());
//			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
//			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//			GL20.glDisableVertexAttribArray(0);
//			GL20.glDisableVertexAttribArray(1);
//			GL20.glDisableVertexAttribArray(2);
//			GL30.glBindVertexArray(0);
//		}
//	}
	
	private static void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan((Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float furstum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / furstum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / furstum_length);
		projectionMatrix.m33 = 0;
		
		
	}

}
