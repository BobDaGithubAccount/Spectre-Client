package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import logging.Logger;
import models.RawModel;

public class OBJLoader {
	
	public static Object[] loadLevelModel(File file) {
		try {
			Logger.log(file.getPath());
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			List<Vector3f> rawvertices = new ArrayList<Vector3f>();
			List<Vector2f> rawtextures = new ArrayList<Vector2f>();
			List<Vector3f> rawnormals = new ArrayList<Vector3f>();
			List<Integer> rawindices = new ArrayList<Integer>();
			float[] verticesArray = null;
			float[] normalsArray = null;
			float[] textureArray = null;
			int[] indicesArray = null;
			float scale = 1f;
			while (true) {
				line = br.readLine();
				if(line==null) {break;}
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
					Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					rawvertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
					Float.parseFloat(currentLine[2]));
					rawtextures.add(texture);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
					Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					rawnormals.add(normal);
				} else if (line.startsWith("f ")) {
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					rawindices.add(Integer.parseInt(vertex1[0]));
					rawindices.add(Integer.parseInt(vertex1[1]));
					rawindices.add(Integer.parseInt(vertex1[2]));
					rawindices.add(Integer.parseInt(vertex2[0]));
					rawindices.add(Integer.parseInt(vertex2[1]));
					rawindices.add(Integer.parseInt(vertex2[2]));
					rawindices.add(Integer.parseInt(vertex3[0]));
					rawindices.add(Integer.parseInt(vertex3[1]));
					rawindices.add(Integer.parseInt(vertex3[2]));
				} else if(line.startsWith("scale")) {
					scale = Float.parseFloat(currentLine[1]);
				}
			}
			br.close();
			verticesArray = new float[rawindices.size()];
			int vertI = 0;
			indicesArray = new int[rawindices.size() / 3];
			int indiceI = 0;
			textureArray = new float[(rawindices.size() / 3) * 2];
			int texI = 0;
			normalsArray = new float[rawindices.size()]; // * 3 / 3
			int normI = 0;
			for (int i = 0; i < rawindices.size(); i += 0) {
				Vector3f coord = rawvertices.get(rawindices.get(i++) - 1);
				Vector2f tex = rawtextures.get(rawindices.get(i++) - 1);
				Vector3f norm = rawnormals.get(rawindices.get(i++) - 1);
				indicesArray[indiceI] = indiceI;
				indiceI++;
				verticesArray[vertI++] = coord.x;
				verticesArray[vertI++] = coord.y;
				verticesArray[vertI++] = coord.z;
				textureArray[texI++] = tex.x;
				textureArray[texI++] = tex.y;
				normalsArray[normI++] = norm.x;
				normalsArray[normI++] = norm.y;
				normalsArray[normI++] = norm.z;
			}
			Object[] object = new Object[2];
			object[0] = Loader.loadToVAO(verticesArray, indicesArray, textureArray, normalsArray);
			object[1] = scale;
			return object;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;		}
	}

	
	public static RawModel loadObjModel(String fileName) {
		try {
			File file = new File(OBJLoader.class.getResource("/res/objects/" + fileName + "/" + fileName + ".obj").getFile());
			Logger.log(file.getPath());
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			List<Vector3f> rawvertices = new ArrayList<Vector3f>();
			List<Vector2f> rawtextures = new ArrayList<Vector2f>();
			List<Vector3f> rawnormals = new ArrayList<Vector3f>();
			List<Integer> rawindices = new ArrayList<Integer>();
			float[] verticesArray = null;
			float[] normalsArray = null;
			float[] textureArray = null;
			int[] indicesArray = null;
			while (true) {
				line = br.readLine();
				if(line==null) {break;}
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
					Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					rawvertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
					Float.parseFloat(currentLine[2]));
					rawtextures.add(texture);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
					Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					rawnormals.add(normal);
				} else if (line.startsWith("f ")) {
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					rawindices.add(Integer.parseInt(vertex1[0]));
					rawindices.add(Integer.parseInt(vertex1[1]));
					rawindices.add(Integer.parseInt(vertex1[2]));
					rawindices.add(Integer.parseInt(vertex2[0]));
					rawindices.add(Integer.parseInt(vertex2[1]));
					rawindices.add(Integer.parseInt(vertex2[2]));
					rawindices.add(Integer.parseInt(vertex3[0]));
					rawindices.add(Integer.parseInt(vertex3[1]));
					rawindices.add(Integer.parseInt(vertex3[2]));
				}
			}
			br.close();
			verticesArray = new float[rawindices.size()];
			int vertI = 0;
			indicesArray = new int[rawindices.size() / 3];
			int indiceI = 0;
			textureArray = new float[(rawindices.size() / 3) * 2];
			int texI = 0;
			normalsArray = new float[rawindices.size()]; // * 3 / 3
			int normI = 0;
			for (int i = 0; i < rawindices.size(); i += 0) {
				Vector3f coord = rawvertices.get(rawindices.get(i++) - 1);
				Vector2f tex = rawtextures.get(rawindices.get(i++) - 1);
				Vector3f norm = rawnormals.get(rawindices.get(i++) - 1);
				indicesArray[indiceI] = indiceI;
				indiceI++;
				verticesArray[vertI++] = coord.x;
				verticesArray[vertI++] = coord.y;
				verticesArray[vertI++] = coord.z;
				textureArray[texI++] = tex.x;
				textureArray[texI++] = tex.y;
				normalsArray[normI++] = norm.x;
				normalsArray[normI++] = norm.y;
				normalsArray[normI++] = norm.z;
			}
			return Loader.loadToVAO(verticesArray, indicesArray, textureArray, normalsArray);
		} catch(Exception e) {
			e.printStackTrace();
			return null;		}
	}
	
//	public static RawModel loadObjModel(String fileName) {
//		try {
//			File file = new File(OBJLoader.class.getResource("/res/objects/" + fileName + "/" + fileName + ".obj").getFile());
//			Logger.log(file.getPath());
//			InputStream is = new FileInputStream(file);
//			Obj obj = ObjUtils.convertToRenderable(ObjReader.read(is));
//			int[] indices = ObjData.getFaceVertexIndicesArray(obj);
//			float[] vertices = ObjData.getVerticesArray(obj);
//			float[] texCoords = ObjData.getTexCoordsArray(obj, 2);
//			float[] normals = ObjData.getNormalsArray(obj);
//			is.close();
//			return Loader.loadToVAO(vertices, indices, texCoords, normals);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}
