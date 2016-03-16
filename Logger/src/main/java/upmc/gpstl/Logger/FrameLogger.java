package upmc.gpstl.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Benjamin on 24/02/2016.
 */
public class FrameLogger extends JFrame{
    JTextPane logDisplay;
    JScrollPane scrollLog;

    public FrameLogger(){
        super("Logs");
        this.setSize(1200,800);
        this.setLayout(new BorderLayout());
        logDisplay = new JTextPane();
        logDisplay.setBackground(Settings.BACKGROUND);
        scrollLog = new JScrollPane(logDisplay);
        JPanel pan = new JPanel(new FlowLayout());
        JButton clear = new JButton("Clear");

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logDisplay.setText("");
            }
        });
        pan.add(clear);
        this.add(pan, BorderLayout.NORTH);
        this.add(scrollLog);
    }

    public void display(String mess, Color color){
        appendToPane(mess, color);
        logDisplay.repaint();
    }

    private void appendToPane(String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();

        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Courier New");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        aset = sc.addAttribute(aset, StyleConstants.Size, Settings.FONT_SIZE);

        int len = logDisplay.getDocument().getLength();
        logDisplay.setCaretPosition(len);
        logDisplay.setCharacterAttributes(aset, false);
        logDisplay.replaceSelection(msg);
    }
}
