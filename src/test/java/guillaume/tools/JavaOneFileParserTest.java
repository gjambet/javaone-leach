/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guillaume.tools;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Guillaume
 */
public class JavaOneFileParserTest {
    
    @Test
    public void shouldGetDownloads() {
        
        File html = new File("src/main/resources/index.html");
        
        Assert.assertTrue(html.exists());
        
        JavaOneFileParser parser = new JavaOneFileParser(html);
        
        Assert.assertFalse(parser.getDownloads().keySet().isEmpty());
        
        
        
    }
    
}
