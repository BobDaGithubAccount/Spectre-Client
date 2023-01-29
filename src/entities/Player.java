package entities;

import lib.json.JSONObject;

import java.util.UUID;

public class Player {

    public float x;
    public float y;
    public float z;

    public float pitch;
    public float yaw;
    public float roll;

    public String name;
    public UUID connectionUUID;

    public Player(float x, float y, float z, float pitch, float yaw, float roll, String name, UUID connectionUUID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.name = name;
        this.connectionUUID = connectionUUID;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        json.put("z", z);
        json.put("pitch", pitch);
        json.put("yaw", yaw);
        json.put("roll", roll);
        json.put("name", name);
        json.put("connectionUUID", connectionUUID);
        return json.toString(1);
    }
}
