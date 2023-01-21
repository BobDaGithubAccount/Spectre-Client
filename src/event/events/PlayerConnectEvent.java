package event.events;

import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import event.IEvent;
import lib.json.JSONObject;
import models.TexturedModel;
import networking.Packet;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;

public class PlayerConnectEvent implements IEvent {

	@Override
	public String name() {
		return Packet.SConnectPacket;
	}

	@Override
	public boolean run(JSONObject json) {
		Entity entity = Loader.loadObj("essential", new Vector3f(0f,0f,0f), new Vector3f(0f,0f,0f), 1);
		TexturedModel model = entity.getModel();
		ModelTexture t = model.getTexture();
		t.setShineDamper(1f);
		t.setReflectivity(0.5f);
		model.setTexture(t);
		entity.setModel(model);
		MasterRenderer.processEntity(entity, UUID.fromString(json.getString("uuid")));
		return false;
	}
	
}
