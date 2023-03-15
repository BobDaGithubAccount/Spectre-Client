package renderEngine.sourceEngineCompatibility.lumpParsers;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.sourceEngineCompatibility.ValveMapFormatLoader;
import renderEngine.sourceEngineCompatibility.structs.Lump;
import renderEngine.sourceEngineCompatibility.structs.LumpType;
import renderEngine.sourceEngineCompatibility.structs.Plane;

public class PlaneParser {

	public static Plane[] read() throws Exception {
		Lump lump = ValveMapFormatLoader.getLump(LumpType.LUMP_PLANES);
		ByteBuffer bb = lump.getBb();
		int x = 0;
		ArrayList<Plane> values = new ArrayList<Plane>();
		while(bb.hasRemaining()) {
			values.add(new Plane(new Vector3f(bb.getFloat(), bb.getFloat(), bb.getFloat()), bb.getFloat(), bb.getInt()));
			x++;
		}
		Plane[] toReturn = new Plane[x];
		for(int i = 0; i < values.size(); i++) {
			toReturn[i] = values.get(i);
		}
		return toReturn;
	}
	
}
