package renderEngine;

import java.util.HashMap;
import java.util.Map.Entry;

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
	
	@Override
	public String toString() {
		String toReturn = "{model=" + model.toString() + ";instances={";
		for(Entry<String, Entity> e : instances.entrySet()) {
			toReturn += e.getKey() + "=" + e.getValue().toString() + ";";
		}
		StringBuffer sb = new StringBuffer(toReturn);   
		sb.deleteCharAt(sb.length()-1);
		toReturn = sb.toString();
		toReturn += "}}";
		return toReturn;
	}
	
}
