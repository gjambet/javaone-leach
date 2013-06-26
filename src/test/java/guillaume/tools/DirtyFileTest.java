package guillaume.tools;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileFilter;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Guillaume
 */
public class DirtyFileTest {

    private File workspace;
    private File reference;
    private DirtyFile df;
    
    public DirtyFileTest() {
        initializeWorkspace();
    }

    private void initializeWorkspace(){
        File tmp = new File(System.getProperty("java.io.tmpdir"));
        workspace = new File(tmp, this.getClass().getName());
        if (workspace.exists()){
            workspace.delete();
        }
        workspace.mkdir();
        workspace.deleteOnExit();
    }

    @Before
    public void before(){
 
        for (File f : workspace.listFiles()) {
            f.delete();
        }
        
        reference = new File(workspace, "pipo.xml");
        df = new DirtyFile(reference);
    }
    
    @Test
    public void shouldReturnADirtyReference() {
        assertThat(df.getDirty()).isNotNull();
        assertThat(df.getDirty().getName().endsWith(DirtyFile.EXT)).isTrue();
    }

    @Test
    public void testValidate() {
    
        DirtyFile df = new DirtyFile(reference);
        df.validate();
        
        assertThatWorkspace(workspace).doesNotContainsDirtyFiles();
        assertThatWorkspace(workspace).containsOnly(reference);
    
    }

    @Test
    public void testClean() {
        DirtyFile df = new DirtyFile(reference);
        df.clean();
        assertThat(workspace.listFiles()).isEmpty();
    }
    
    private WorkspaceChecker assertThatWorkspace(File workspace) {
        return new WorkspaceChecker(workspace);
    }

    private class WorkspaceChecker {
        
        private File workspace;

        private WorkspaceChecker(File workspace) {
            this.workspace = workspace;
        }

        private void containsOnly(File reference) {
            assertThat(workspace).isNotNull();
            assertThat(workspace.listFiles()).hasSize(1);
            assertThat(workspace.listFiles()).contains(reference);
        }

        private void doesNotContainsDirtyFiles() {
            assertThat(workspace.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return (pathname != null && pathname.getName().endsWith(DirtyFile.EXT));
                }
            })).isEmpty();
        }
    }
    
}
