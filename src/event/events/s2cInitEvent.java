package event.events;

import event.EventHandler;
import event.IEvent;
import lib.json.JSONObject;
import logging.Logger;
import main.MainGameLoop;
import main.Spectre;
import networking.Packet;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class s2cInitEvent implements IEvent {
    @Override
    public String name() {
        return Packet.SInitPacket;
    }

    @Override
    public boolean run(JSONObject json) {
        int port = json.getInt("port");
        String toGet = "http://" + MainGameLoop.ip + ":" + port + "/level";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(toGet))
                    .build();
            System.out.println(request.uri());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();


            byte[] data = responseString.getBytes(StandardCharsets.UTF_8);
            File jarFile = new File(Logger.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File levelsFolder = new File(jarFile.getParent() + "/server-levels");
            levelsFolder.mkdirs();
            File levelFile = new File(levelsFolder.getPath() + "/" + MainGameLoop.ip + ".level");
            levelFile.delete();
            levelFile.createNewFile();
            Files.write(Path.of(levelFile.getPath()), data);
            EventHandler.levelLoaded = true;
            Spectre.levelFile = levelFile;
        } catch(Exception e) {
            Logger.log("There was a fatal error fetching server level from " + toGet);
            Logger.log(e.getMessage());
            System.exit(-1);
        }
        return false;
    }
}
