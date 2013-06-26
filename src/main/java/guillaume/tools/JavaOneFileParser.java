package guillaume.tools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Guillaume
 */
class JavaOneFileParser {

    private File index;
    
    public JavaOneFileParser(File index) {
        this.index = index;
    }

    public Map<String, URL> getDownloads() {
        
        Map<String, URL> downloads = new HashMap<String, URL>();
        
        try {
            
            Document doc = Jsoup.parse(index, "CP1252");
            Iterator<Element> it = doc.select("li").iterator();
            while(it.hasNext()) {
                
                Element element = it.next();

                if (!element.text().startsWith("title:")) {
                    continue;
                }

                String name = sanitize(element.text());
                
                try {
                
                    element = it.next(); // ignore abstract
                    element = it.next(); // ignore url

                    element = it.next(); // on pdf
                    URL link = getLink(element);
                    downloads.put(getName(name, link), link);

                    element = it.next(); // on mp4
                    link = getLink(element);
                    downloads.put(getName(name, link), link);

                } catch (IOException ex) {
                    System.out.println("ignoring : " + name);
                    System.out.println();
                }                
            }
 

            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return downloads;
    }

    private String sanitize(String text) {
        return text.substring(7).replace(':', ',').replace('\'', ' ').replace('?', ' ').trim();
    }
    
    private String getName(String name, URL link){
        
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - ").append(link.toExternalForm().substring(link.toExternalForm().lastIndexOf('/') + 1));
        return sb.toString();
    }

    private URL getLink(Element element) throws MalformedURLException {
        Element href = element.select("a").first();
        return new URL(href.text());
    }
    
}
