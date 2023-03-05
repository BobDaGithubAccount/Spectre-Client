package renderEngine.sourceEngineCompatibility.lumpParsers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import renderEngine.sourceEngineCompatibility.Lump;
import renderEngine.sourceEngineCompatibility.LumpType;
import renderEngine.sourceEngineCompatibility.ValveMapFormatLoader;

public class VertexParser {
	public static void read() throws Exception {
		Lump lump = ValveMapFormatLoader.getLump(LumpType.LUMP_VERTEXES);
		ByteBuffer bb = lump.getBb();
		FloatBuffer bf = bb.asFloatBuffer();
	}
}
