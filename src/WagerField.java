import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class WagerField extends JTextField {
    @Override
    protected Document createDefaultModel() {
        return new WagerDocument();
    }

    static class WagerDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            str = str.replaceAll("\\D", "");
            super.insertString(offs, str, a);
        }
    }
}
