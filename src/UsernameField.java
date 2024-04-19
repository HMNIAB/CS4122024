import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.util.regex.Pattern;

public class UsernameField extends JTextField {
    @Override
    protected Document createDefaultModel() {
        return new UsernameDocument();
    }

    static class UsernameDocument extends PlainDocument {
        private Pattern alphanumeric = Pattern.compile("[a-zA-Z0-9]");

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if(alphanumeric.matcher(str).matches()) {
                super.insertString(offs, str, a);
            }
        }
    }
}
