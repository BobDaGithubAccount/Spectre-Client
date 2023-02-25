package renderEngine.sourceEngineCompatibility;

public class Lump {
	private int lumpOffsetInFileInBytes;//
	private int lumpLengthInBytes;
	private int lumpFormatVersion;
	private char[] id; //LENGTH=4
	public Lump(int lumpOffsetInFileInBytes, int lumpLengthInBytes, int lumpFormatVersion, char[] id) {
		super();
		this.lumpOffsetInFileInBytes = lumpOffsetInFileInBytes;
		this.lumpLengthInBytes = lumpLengthInBytes;
		this.lumpFormatVersion = lumpFormatVersion;
		this.id = id;
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
	public char[] getId() {
		return id;
	}
	public void setId(char[] id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "{lumpOffset:"+this.lumpOffsetInFileInBytes+",lumpLength:"+this.lumpLengthInBytes+",lumpFormat:"+this.lumpFormatVersion+",lumpIdentifier:"+String.valueOf(this.id)+"}";
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