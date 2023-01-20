package event;

import lib.json.JSONObject;

public interface IEvent {

    public String name();
    public boolean run(JSONObject json);
}
