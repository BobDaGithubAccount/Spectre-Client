package renderEngine.sourceEngineCompatibility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import logging.Logger;
import main.MainGameLoop;

public class ValveMapFormatLoader {
	//TODO STORE LEVEL DATA IN SOURCE-ENGINE .bsp FILES
	//SEE developer.valvesoftware.com
	//https://developer.valvesoftware.com/wiki/Source_BSP_File_Format#BSP_file_header
	public static void loadSourceMap(String fileName) {
		try {
			logic(fileName);
		} catch(Exception e) {
			Logger.log("There was a fatal error with loading the map " + fileName);
			Logger.log(e.getMessage());
			System.exit(-1);
		}
	}
		
	private static void logic(String fileName) throws Exception {
		Logger.log("Attempting to load source map " + fileName);
		File file = new File(MainGameLoop.class.getResource("/res/levels/" + fileName + ".bsp").getFile());
		Logger.log("Attempting to load source map " + fileName + " with file " + file);
		
	    FileInputStream fis = new FileInputStream(file);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    
	    byte[] bytes = Files.readAllBytes(file.toPath());
	    
	    byte[] header = new byte[1036];
	    for(int i = 0; i < 1036; i++) {
	    	header[i] = bytes[i];
	    }
	    
	    StringBuilder headerFileType = new StringBuilder();
	    for(int i = 0; i < 4; i++){
	    	headerFileType.append((char)header[i]);
	    }
	    if(!headerFileType.toString().equals("VBSP")) {
	    	bis.close();
	    	fis.close();
	    	Logger.log("Header doesn't indicate it is a VBSP file! HEADER-FILE-TYPE: " + headerFileType.toString());
	    	throw new Exception("Header doesn't indicate it is a VBSP file! HEADER-FILE-TYPE: " + headerFileType.toString());
	    }
	    Logger.log(headerFileType + " header parse check passed");
	    
	    int headerVersion = (int) header[4];
	    if(headerVersion!=19) {
	    	bis.close();
	    	fis.close();
	    	Logger.log("Header doesn't indicate it is a hl2 map file! BSP-FILE-VERSION: " + headerVersion);
	    	throw new Exception("Header doesn't indicate it is a hl2 map file! BSP-FILE-VERSION: " + headerVersion);
	    }
	    Logger.log("VBSP version header parse check passed");
	    
	    Lump[] lumps = new Lump[64];
	    Logger.log("Loading lump data!");
	    int count = 5;
	    for(int lumpID = 0; lumpID < 64; lumpID++) {
	    	int offset = (int) header[count];
	    	int length = (int) header[count+1];;
	    	int version = (int) header[count+2];;
	    	char[] identity = new char[4];
	    	identity[0] = (char) header[count+3];
	    	identity[1] = (char) header[count+4];
	    	identity[2] = (char) header[count+5];
	    	identity[3] = (char) header[count+6];
	    	Lump lump = new Lump(offset, length, version, identity);
	    	lumps[lumpID] = lump;
	    	System.out.println(lump.toString());
	    	count += 16;
	    }
	    Logger.log("Loaded lump data!");
	    
	    bis.close();
	    fis.close();
	}

}
