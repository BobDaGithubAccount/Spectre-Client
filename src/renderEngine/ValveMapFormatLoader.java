package renderEngine;

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
	    	throw new Exception("Header doesn't indicate it is a VBSP file! HEADER-FILE-TYPE: " + headerFileType.toString());
	    }
	    Logger.log(headerFileType + " header parse check passed");
	    
	    bis.close();
	    fis.close();
	}
	
	class lump {
		private int lumpOffsetInFileInBytes;
		private int lumpLengthInBytes;
		private int lumpFormatVersion;
		
	}
//	struct lump_t
//	{
//		int    fileofs;      // offset into file (bytes)
//		int    filelen;      // length of lump (bytes)
//		int    version;      // lump format version
//		char   fourCC[4];    // lump ident code
//	};
	
	
	class header {
		private int identifier;
		private int version;
		private lump[] lumps;
		private int mapRevision;
		public header(int identifier, int version, lump[] lumps, int mapRevision) {
			super();
			this.identifier = identifier;
			this.version = version;
			this.lumps = lumps;
			this.mapRevision = mapRevision;
		}
		public int getIdentifier() {
			return identifier;
		}
		public void setIdentifier(int identifier) {
			this.identifier = identifier;
		}
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
		public lump[] getLumps() {
			return lumps;
		}
		public void setLumps(lump[] lumps) {
			this.lumps = lumps;
		}
		public int getMapRevision() {
			return mapRevision;
		}
		public void setMapRevision(int mapRevision) {
			this.mapRevision = mapRevision;
		}
	}
//	struct dheader_t
//	{
//		int     ident;                  // BSP file identifier
//		int     version;                // BSP file version
//		lump_t  lumps[HEADER_LUMPS];    // lump directory array
//		int     mapRevision;            // the map's revision (iteration, version) number
//	};

}
