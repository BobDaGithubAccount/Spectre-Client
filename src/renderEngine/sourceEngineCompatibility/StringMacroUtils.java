package renderEngine.sourceEngineCompatibility;

public class StringMacroUtils {

    public static int makeID(String id) {
        if (id.length() != 4) {
            throw new IllegalArgumentException("String must be exactly 4 characters long");
        }
        byte[] bytes = id.getBytes();
        return (bytes[3] << 24) | (bytes[2] << 16) | (bytes[1] << 8) | bytes[0];
    }
    public static String unmakeID(int id) {
        byte[] bytes = new byte[] {
            (byte) id,
            (byte) (id >>> 8),
            (byte) (id >>> 16),
            (byte) (id >>> 24)
        };
        return new String(bytes);
    }
}