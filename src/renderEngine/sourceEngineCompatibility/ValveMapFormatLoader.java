package renderEngine.sourceEngineCompatibility;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import info.ata4.io.buffer.ByteBufferUtils;
import logging.Logger;
import main.MainGameLoop;
import renderEngine.sourceEngineCompatibility.lumpParsers.VertexParser;
import renderEngine.sourceEngineCompatibility.structs.Lump;
import renderEngine.sourceEngineCompatibility.structs.LumpType;

public class ValveMapFormatLoader {
	//TODO STORE LEVEL DATA IN SOURCE-ENGINE .bsp FILES
	//SEE developer.valvesoftware.com
	//https://developer.valvesoftware.com/wiki/Source_BSP_File_Format#BSP_file_header
	//https://github.com/ata4/bspsrc/blob/master/src/main/java/info/ata4/bsplib/BspFile.java
	public static void loadSourceMap(String fileName) {
		try {
			load(fileName);
		} catch(Exception e) {
			Logger.log("There was a fatal error with loading the map " + fileName);
			Logger.log(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
    public static final int BSP_ID = StringMacroUtils.makeID("VBSP");
	public static ByteOrder bo;
    public static final int HEADER_LUMPS = 64;
    public static final int HEADER_SIZE = 1036;
    public static final int MAX_LUMPFILES = 128;
	public static List<Lump> lumps = new ArrayList<Lump>(HEADER_LUMPS);public static Lump getLump(LumpType type){return lumps.get(type.getIndex());}
    
	private static void load(String fileName) throws Exception {
		Logger.log("Attempting to load source map " + fileName);
		File file = new File(MainGameLoop.class.getResource("/res/levels/" + fileName + ".bsp").getFile());
		Logger.log("Attempting to load source map " + fileName + " with file " + file);
		lumps = new ArrayList<Lump>(HEADER_LUMPS);
        ByteBuffer bb = createBuffer(file, true);
        int version = bb.getInt();
        if(version!=19) {
        	throw new Exception("Invalid bsp version (supported: "+19+", provided " + version + ")");
        }
        loadLumps(bb);
        System.out.println(lumps);
        VertexParser.read();
	}
	
	private static void loadLumps(ByteBuffer bb) throws Exception {
		int numLumps = HEADER_LUMPS;
        for (int i = 0; i < numLumps; i++) {
            int vers, ofs, len, fourCC;
            ofs = bb.getInt();
            len = bb.getInt();
            vers = bb.getInt();
            // length of the uncompressed lump, 0 if not compressed
            fourCC = bb.getInt();
            LumpType type = LumpType.get(i, 19);
            Logger.log("Data: " + ofs + " " + len + " " + vers + " " + fourCC + " " + type.toString());
            Lump lump = new Lump(ofs,len,vers,fourCC,ByteBufferUtils.getSlice(bb, ofs, len),type);
            lumps.add(lump);
        }
	}
	
	private static ByteBuffer createBuffer(File file, boolean memoryMapping) throws Exception {
		ByteBuffer bb;
        if(memoryMapping) {
            bb = ByteBufferUtils.openReadOnly(file.toPath());
        }
        else {
            bb = ByteBufferUtils.load(file.toPath());
        }
        if(bb.capacity() < 4) {
            throw new Exception("Missing BSP header!");
        }
        bo = ByteOrder.LITTLE_ENDIAN;
        bb.order(bo);
        int ident = bb.getInt();
        if(ident == 0x504B0304 || ident == 0x504B0506 || ident == 0x504B0708) {
            throw new Exception("Loaded file is a zip archive");
        }
        if(bb.capacity() < 1036) {
            throw new Exception("Invalid or missing header");
        }
        if(ident != BSP_ID) {
        	throw new Exception("Invalid bsp identifier (supported: "+StringMacroUtils.unmakeID(BSP_ID)+", provided " + StringMacroUtils.unmakeID(ident) + ")");
        }
        return bb;
	}

	
}
