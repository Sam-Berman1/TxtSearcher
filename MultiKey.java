package finalproject;

/**
 * 
 * @author Sam Berman
 * <p>The MultiKey class is used as a workaround 
 * for multiple keys in a Map.
 *
 */
public class MultiKey {
    private String[] keys;

    public MultiKey(String... keys) {
        this.keys = keys;
    }
    
    public String[] getMultiKeys() {
    	return keys;
    }
    
}


