package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private boolean isFirstFocus = true;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setForeground(Color.GRAY);
        setText(placeholder);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isFirstFocus) {
                    isFirstFocus = false;
                    setText("");
                    setForeground(Color.BLACK);
                } else if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }
        });
    }

    @Override
    public String getText() {
        String text = super.getText();
        return text.equals(placeholder) ? "" : text;
    }
}


