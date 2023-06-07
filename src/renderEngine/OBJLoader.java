package renderEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import logging.Logger;
import models.RawModel;

public class OBJLoader {

	public static RawModel loadObjModel(String fileName) {
		try {
			File file = new File(OBJLoader.class.getResource("/res/objects/" + fileName + "/" + fileName + ".obj").getFile());
			Logger.log(file.getPath());
			InputStream is = new FileInputStream(file);
			Obj obj = ObjUtils.convertToRenderable(ObjReader.read(is));
			int[] indices = ObjData.getFaceVertexIndicesArray(obj);
			float[] vertices = ObjData.getVerticesArray(obj);
			float[] texCoords = ObjData.getTexCoordsArray(obj, 2);
			float[] normals = ObjData.getNormalsArray(obj);
			is.close();
			return Loader.loadToVAO(vertices, indices, texCoords, normals);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
