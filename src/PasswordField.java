import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class PasswordField extends JPasswordField {
    @Override
    protected Document createDefaultModel() {
        return new PasswordDocument();
    }

    public class PasswordDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            str = str.replaceAll("\s", "");
            super.insertString(offs, str, a);
        }
    }
}
