package renderEngine;

import java.util.HashMap;

import entities.Entity;
import models.TexturedModel;

public class RenderObject {

	public HashMap<String, Entity> instances;
	public TexturedModel model;
	
	public RenderObject(HashMap<String, Entity> instances, TexturedModel model) {
		if(instances == null) {
			this.instances = new HashMap<String, Entity>();
		}
		else {
			this.instances = instances;
		}
		this.model = model;
	}
	
}
