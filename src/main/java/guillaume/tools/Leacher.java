package guillaume.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 * @author Guillaume
 */
public class Leacher {

    private File index;
    private File directory;

    private int count;
    private int total;
    
    public Leacher(String url) {
        this.index = new File(url);
        count = 0 ;
    }

    public Leacher toTargetDirectory(String directory) {
        this.directory = new File(directory);
        return this;
    }

    public void run() {
        
        Map<String, URL> downloads = new HashMap<String, URL>();
        
        downloads.putAll(new JavaOneFileParser(index).getDownloads());        
        total = downloads.size();
        
        List<String> keys = new ArrayList(downloads.keySet());
        Collections.shuffle(keys);
        for (String key : keys) {
            try {
                count++;
                download(downloads.get(key), key);
            } catch (IOException ex) {
                log(ex.getMessage());
            }
        }
    }
    
    private void download(URL url, String name) throws IOException {
        
        File target = new File(directory, name);
        
        if (target.exists()) {
            // log("Skipping : " + target.getAbsolutePath());
            return;
        }
        DirtyFile dirty = null;
        try {
            dirty = new DirtyFile(target);
            FileUtils.copyURLToFile(url, dirty.getDirty());
            dirty.validate();
            log("    " + name + " downloaded");
        } catch (FileNotFoundException fnf) {
            log("Missing : " + target.getName());
            dirty.clean();
        } catch (SocketException e) {
            log("Socket error : " + target.getName());
            dirty.clean();
        }
        
        // if timeout exception -> delete file, flag for restart whole list return
        
    }


    private void log(String s) {
        
        StringBuilder b = new StringBuilder()
                .append(s)
                .append(" - ")
                .append(DateFormat.getInstance().format(Calendar.getInstance().getTime()))
                .append(" - ")
                .append (count)
                .append(" / ")
                .append(total);
        
        System.out.println(b.toString());
    }
    
}
