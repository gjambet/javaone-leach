package guillaume.tools;

import java.io.File;
import java.io.IOException;

/**
 * @author Guillaume
 */
public class DirtyFile {

    private File dirty;
    public static final String EXT = ".dirty";
    
    public DirtyFile(File target) {
        dirty = newDirty(target);
    }
    
    private File newDirty(File f) {
        
        File dirty = new File(f.getParentFile(), f.getName() + EXT);
        
        if (!dirty.exists()){
            try {
                dirty.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return dirty;
    }
    

    public File getDirty() {
        return dirty;
    }

    public void validate() {
        dirty.renameTo(new File(dirty.getParent(), dirty.getName().substring(0, dirty.getName().length() - EXT.length())));
    }

    public void clean() {
        dirty.delete();
    }
    
}
