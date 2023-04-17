package renderEngine.sourceEngineCompatibility.lumpParsers;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;
import renderEngine.sourceEngineCompatibility.ValveMapFormatLoader;
import renderEngine.sourceEngineCompatibility.structs.Lump;
import renderEngine.sourceEngineCompatibility.structs.LumpType;

public class NormalsParser {

	public static float[] read() throws Exception {
		Lump lump = ValveMapFormatLoader.getLump(LumpType.LUMP_VERTNORMALS);
		ByteBuffer bb = lump.getBb();
		int x = 0;
		ArrayList<Float> values = new ArrayList<Float>();
		while(bb.hasRemaining()) {
			values.add(bb.getFloat());
			x++;
		}
		float[] toReturn = new float[x];
		for(int i = 0; i < values.size(); i++) {
			toReturn[i] = values.get(i);
		}
		return toReturn;
	}

    public static Vector3f[] readVector3f() throws Exception {
        Lump lump = ValveMapFormatLoader.getLump(LumpType.LUMP_VERTNORMALS);
        ByteBuffer bb = lump.getBb();
        int x = 0;
        ArrayList<Vector3f> values = new ArrayList<Vector3f>();
        while(bb.hasRemaining()) {
            values.add(new Vector3f(bb.getFloat(),bb.getFloat(),bb.getFloat()));
            x++;
        }
        Vector3f[] toReturn = new Vector3f[x];
        for(int i = 0; i < values.size(); i++) {
            toReturn[i] = values.get(i);
        }
        return toReturn;
    }
	
}
