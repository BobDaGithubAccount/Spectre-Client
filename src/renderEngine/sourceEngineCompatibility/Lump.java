package renderEngine.sourceEngineCompatibility;

import java.nio.ByteBuffer;

public class Lump {
	private int lumpOffsetInFileInBytes;//
	private int lumpLengthInBytes;
	private int lumpFormatVersion;
	private int fourCC; //LENGTH=4
	private ByteBuffer bb = ByteBuffer.allocate(0);
	private LumpType type;
	public Lump(int lumpOffsetInFileInBytes, int lumpLengthInBytes, int lumpFormatVersion, int fourCC, ByteBuffer bb, LumpType type) {
		super();
		this.lumpOffsetInFileInBytes = lumpOffsetInFileInBytes;
		this.lumpLengthInBytes = lumpLengthInBytes;
		this.lumpFormatVersion = lumpFormatVersion;
		this.fourCC = fourCC;
		this.bb = bb;
		this.type = type;
	}
	public int getLumpOffsetInFileInBytes() {
		return lumpOffsetInFileInBytes;
	}
	public void setLumpOffsetInFileInBytes(int lumpOffsetInFileInBytes) {
		this.lumpOffsetInFileInBytes = lumpOffsetInFileInBytes;
	}
	public int getLumpLengthInBytes() {
		return lumpLengthInBytes;
	}
	public void setLumpLengthInBytes(int lumpLengthInBytes) {
		this.lumpLengthInBytes = lumpLengthInBytes;
	}
	public int getLumpFormatVersion() {
		return lumpFormatVersion;
	}
	public void setLumpFormatVersion(int lumpFormatVersion) {
		this.lumpFormatVersion = lumpFormatVersion;
	}
	public int getFourCC() {
		return fourCC;
	}
	public void setFourCC(int fourCC) {
		this.fourCC = fourCC;
	}
	public ByteBuffer getBb() {
		return bb;
	}
	public void setBb(ByteBuffer bb) {
		this.bb = bb;
	}
	public LumpType getType() {
		return type;
	}
	public void setType(LumpType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "{lumpOffset:"+this.lumpOffsetInFileInBytes+",lumpLength:"+this.lumpLengthInBytes+",lumpFormat:"+this.lumpFormatVersion+",lumpIdentifier:"+this.fourCC+"}";
	}
}
//Lump structure
//
//Then follows an array of 16-byte lump_t structures. HEADER_LUMPS is defined as 64, so there are 64 entries. However, depending on the game and version, some lumps can be undefined or empty.
//
//Each lump_t is defined in bspfile.h:
//
//struct lump_t
//{
//	int    fileofs;      // offset into file (bytes)
//	int    filelen;      // length of lump (bytes)
//	int    version;      // lump format version
//	char   fourCC[4];    // lump ident code
//};
//
//The first two integers contain the byte offset (from the beginning of the bsp file) and byte length of that lump's data block; an integer defining the version number of the format of that lump (usually zero), and then a four byte identifier that is usually 0, 0, 0, 0. For compressed lumps, the fourCC contains the uncompressed lump data size in integer form (see section Lump compression for details). Unused members of the lump_t array (those that have no data to point to) have all elements set to zero.
//
//Lump offsets (and their corresponding data lumps) are always rounded up to the nearest 4-byte boundary, though the lump length may not be. 