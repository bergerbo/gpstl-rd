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
    public static Color ERROR;
    public static Color DEBUG;
    public static Color INFO;
    public static Color WARNING;
    public static Color DEFAULT;
    public static Color BACKGROUND;
    public static Color POINT;

    public static int FONT_SIZE;

    JTextPane logDisplay;
    JScrollPane scrollLog;

    public FrameLogger(){
        super("Logs");
        loadColor();
        this.setSize(1200,800);
        this.setLayout(new BorderLayout());
        logDisplay = new JTextPane();
        logDisplay.setBackground(BACKGROUND);
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

    private void loadColor() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(
                    "./config.json"));
            JSONObject jsonObject = (JSONObject) obj;

            String[] errorRGB = ((String) jsonObject.get("Error")).split(",");
            String[] debugRGB = ((String) jsonObject.get("Debug")).split(",");
            String[] infoRGB = ((String) jsonObject.get("Info")).split(",");
            String[] warningRGB = ((String) jsonObject.get("Warning")).split(",");
            String[] defaultRGB = ((String) jsonObject.get("Default")).split(",");
            String[] backgroundRGB = ((String) jsonObject.get("Background")).split(",");
            String[] pointRGB = ((String) jsonObject.get("Point")).split(",");
            String size = ((Long) jsonObject.get("Size")).toString();

            ERROR = new Color(Integer.valueOf(errorRGB[0]), Integer.valueOf(errorRGB[1]), Integer.valueOf(errorRGB[2]));
            DEBUG = new Color(Integer.valueOf(debugRGB[0]), Integer.valueOf(debugRGB[1]), Integer.valueOf(debugRGB[2]));
            INFO = new Color(Integer.valueOf(infoRGB[0]), Integer.valueOf(infoRGB[1]), Integer.valueOf(infoRGB[2]));
            WARNING = new Color(Integer.valueOf(warningRGB[0]), Integer.valueOf(warningRGB[1]), Integer.valueOf(warningRGB[2]));
            DEFAULT = new Color(Integer.valueOf(defaultRGB[0]), Integer.valueOf(defaultRGB[1]), Integer.valueOf(defaultRGB[2]));
            BACKGROUND = new Color(Integer.valueOf(backgroundRGB[0]), Integer.valueOf(backgroundRGB[1]), Integer.valueOf(backgroundRGB[2]));
            POINT = new Color(Integer.valueOf(pointRGB[0]), Integer.valueOf(pointRGB[1]), Integer.valueOf(pointRGB[2]));
            FONT_SIZE = Integer.valueOf(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        aset = sc.addAttribute(aset, StyleConstants.Size, FONT_SIZE);

        int len = logDisplay.getDocument().getLength();
        logDisplay.setCaretPosition(len);
        logDisplay.setCharacterAttributes(aset, false);
        logDisplay.replaceSelection(msg);
    }
}
