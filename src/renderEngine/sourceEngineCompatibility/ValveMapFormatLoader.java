package renderEngine.sourceEngineCompatibility;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import info.ata4.io.buffer.ByteBufferUtils;
import logging.Logger;
import main.MainGameLoop;

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
        loadGameLumps();
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
	
	private static void loadGameLumps() throws Exception {
//        Lump lump = getLump(LumpType.LUMP_GAME_LUMP);
//        DataReader in = DataReaders.forByteBuffer(lump.getBuffer());
//
//        // hack for Vindictus
//        if (version == 20 && bo == ByteOrder.LITTLE_ENDIAN
//                && checkInvalidHeaders(in, false)
//                && !checkInvalidHeaders(in, true)) {
//            L.finer("Found Vindictus game lump header");
//            appId = VINDICTUS;
//        }
//
//        int glumps = in.readInt();
//
//        for (int i = 0; i < glumps; i++) {
//            int ofs, len, flags, vers, fourCC;
//
//            if (appId == DARK_MESSIAH) {
//                in.readInt(); // unknown
//            }
//
//            fourCC = in.readInt();
//
//            // Vindictus uses integers rather than unsigned shorts
//            if (appId == VINDICTUS) {
//                flags = in.readInt();
//                vers = in.readInt();
//            } else {
//                flags = in.readUnsignedShort();
//                vers = in.readUnsignedShort();
//            }
//
//            ofs = in.readInt();
//            len = in.readInt();
//
//            if (flags == 1) {
//                // game lump is compressed and "len" contains the uncompressed
//                // size, so use next entry offset to determine compressed size
//                in.seek(8, CURRENT);
//                int nextOfs = in.readInt();
//                if (nextOfs == 0) {
//                    // no next entry, assume end of game lump
//                    nextOfs = lump.getOffset() + lump.getLength();
//                }
//                len = nextOfs - ofs;
//                in.seek(-12, CURRENT);
//            }
//
//            // Offset is relative to the beginning of the BSP file,
//            // not to the game lump.
//            // FIXME: this isn't the case for the console version of Portal 2,
//            // is there a better way to detect this?
//            if (ofs - lump.getOffset() > 0) {
//                ofs -= lump.getOffset();
//            }
//
//            String glName = StringMacroUtils.unmakeID(fourCC);
//
//            // give dummy entries more useful names
//            if (glName.trim().isEmpty()) {
//                glName = "<dummy>";
//            }
//
//            // fix invalid offsets
//            if (ofs > lump.getLength()) {
//                int ofsOld = ofs;
//                ofs = lump.getLength();
//                len = 0;
//                L.log(Level.WARNING, "Invalid game lump offset {0} in {1}, assuming {2}",
//                        new Object[]{ofsOld, glName, ofs});
//            } else if (ofs < 0) {
//                int ofsOld = ofs;
//                ofs = 0;
//                len = 0;
//                L.log(Level.WARNING, "Negative game lump offset {0} in {1}, assuming {2}",
//                        new Object[]{ofsOld, glName, ofs});
//            }
//
//            // fix invalid lengths
//            if (ofs + len > lump.getLength()) {
//                int lenOld = len;
//                len = lump.getLength() - ofs;
//                L.log(Level.WARNING, "Invalid game lump length {0} in {1}, assuming {2}",
//                        new Object[]{lenOld, glName, len});
//            } else if (len < 0) {
//                int lenOld = len;
//                len = 0;
//                L.log(Level.WARNING, "Negative game lump length {0} in {1}, assuming {2}",
//                        new Object[]{lenOld, glName, len});
//            }
//
//            GameLump gl = new GameLump();
//            gl.setBuffer(ByteBufferUtils.getSlice(lump.getBuffer(), ofs, len));
//            gl.setOffset(ofs);
//            gl.setFourCC(fourCC);
//            gl.setFlags(flags);
//            gl.setVersion(vers);
//            gameLumps.add(gl);
//        }
//
//        L.log(Level.FINE, "Game lumps: {0}", glumps);
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
