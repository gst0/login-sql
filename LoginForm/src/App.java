import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarculaLaf;

public class App {
    public static void main(String[] args) throws Exception {
    
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        SignInForm signInForm = new SignInForm();
        signInForm.initialize();
    }
}
