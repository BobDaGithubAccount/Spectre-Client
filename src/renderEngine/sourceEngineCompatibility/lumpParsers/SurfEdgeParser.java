package renderEngine.sourceEngineCompatibility.lumpParsers;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import renderEngine.sourceEngineCompatibility.ValveMapFormatLoader;
import renderEngine.sourceEngineCompatibility.structs.Lump;
import renderEngine.sourceEngineCompatibility.structs.LumpType;

public class SurfEdgeParser {
	public static int[] read() throws Exception {
		Lump lump = ValveMapFormatLoader.getLump(LumpType.LUMP_SURFEDGES);
		ByteBuffer bb = lump.getBb();
		int x = 0;
		ArrayList<Integer> values = new ArrayList<Integer>();
		while(bb.hasRemaining()) {
			values.add(bb.getInt());
			x++;
		}
		int[] toReturn = new int[x];
		for(int i = 0; i < values.size(); i++) {
			toReturn[i] = values.get(i);
		}
		return toReturn;
	}
}
