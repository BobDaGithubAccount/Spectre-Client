package renderEngine.sourceEngineCompatibility.lumpParsers;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import renderEngine.sourceEngineCompatibility.Lump;
import renderEngine.sourceEngineCompatibility.LumpType;
import renderEngine.sourceEngineCompatibility.ValveMapFormatLoader;

public class VertexParser {
	public static float[] read() throws Exception {
		Lump lump = ValveMapFormatLoader.getLump(LumpType.LUMP_VERTEXES);
		ByteBuffer bb = lump.getBb();
		int x = 0;
		ArrayList<Float> floats = new ArrayList<Float>();
		while(bb.hasRemaining()) {
			floats.add(bb.getFloat());
			x++;
		}
		float[] verticesArray = new float[x];
		for(int i = 0; i < floats.size(); i++) {
			verticesArray[i] = floats.get(i);
		}
		return verticesArray;
	}
}
