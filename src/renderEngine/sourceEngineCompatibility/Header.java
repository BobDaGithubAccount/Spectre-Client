package renderEngine.sourceEngineCompatibility;

public class Header {
	private int identifier;
	private int version;
	private Lump[] lumps;
	private int mapRevision;
	public Header(int identifier, int version, Lump[] lumps, int mapRevision) {
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
	public Lump[] getLumps() {
		return lumps;
	}
	public void setLumps(Lump[] lumps) {
		this.lumps = lumps;
	}
	public int getMapRevision() {
		return mapRevision;
	}
	public void setMapRevision(int mapRevision) {
		this.mapRevision = mapRevision;
	}
}
//struct dheader_t
//{
//	int     ident;                  // BSP file identifier
//	int     version;                // BSP file version
//	lump_t  lumps[HEADER_LUMPS];    // lump directory array
//	int     mapRevision;            // the map's revision (iteration, version) number
//};