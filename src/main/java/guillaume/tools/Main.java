package guillaume.tools;

/**
 * @author Guillaume
 */
public class Main {
 
    public static void main(String[] args) {
        // "http://wiki.greptilian.com/java"
        new Leacher("src/main/resources/index.html").toTargetDirectory("E:\\JavaOneLeacher").run();
    }
    
    
}
