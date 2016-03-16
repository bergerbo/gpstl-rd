package upmc.gpstl.Logger; /**
 * Created by Benjamin on 24/02/2016.
 */

import jssc.SerialPortList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Set;

public class FrameSerial extends JFrame {

    private JLabel headerLabel;
    private JPanel controlPanel;
    String[] colorStrings = {"Error", "Debug", "Info", "Warning", "Default", "Background", "Point"};
    Integer[] sizeValue = {14, 16, 18, 20, 25, 36, 48};
    JComboBox<String> jc;
    JComboBox<Integer> sizeBox;

    public FrameSerial() {
        super("R&D Logger");
        this.setSize(600, 400);
        this.setLayout(new GridLayout(5, 1));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        this.add(headerLabel);
        initList();
    }

    private void initList() {
        headerLabel.setText("Select your serial port");

        JList list = new JList(getSerialPortNames()); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);

        JButton listenButton = new JButton("Listen");
        JButton refreshButton = new JButton("Refresh");

        listenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object temp = list.getSelectedValue();
                if (temp != null) {
                    String port = temp.toString();
                    new LoggerThread(port).start();
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((DefaultListModel) list.getModel()).removeAllElements();
                list.setModel(getSerialPortNames());
            }
        });

        JPanel config = new JPanel();
        config.setLayout(new FlowLayout());
        jc = new JComboBox<>(colorStrings);
        config.add(jc);
        JButton color = new JButton("Choose color");
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Choose your color", Color.WHITE);
                if (c != null)
                    saveColor(c);
            }
        });
        config.add(color);

        JPanel sizePane = new JPanel(new FlowLayout());
        JLabel size = new JLabel("Font size : ");
        sizeBox = new JComboBox<>(sizeValue);
        loadFontSize();
        sizeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFontSize();
            }
        });
        sizePane.add(size);
        sizePane.add(sizeBox);

        this.add(listScroller);
        controlPanel.add(refreshButton);
        controlPanel.add(listenButton);
        this.add(controlPanel);
        this.add(config);
        this.add(sizePane);
        this.setVisible(true);
    }

    private void saveColor(Color c) {

        String color = (String) jc.getSelectedItem();

        if (color.equals("Background")) {
            Settings.BACKGROUND = c;
        } else {
            Settings.setColor(color, c);
        }

        Settings.save();
    }

    private void saveFontSize() {
        Settings.FONT_SIZE = (int) sizeBox.getSelectedItem();
        Settings.save();
    }

    private void loadFontSize() {
        sizeBox.setSelectedItem(Settings.FONT_SIZE);
    }

    private DefaultListModel<String> getSerialPortNames() {
        DefaultListModel<String> lm = new DefaultListModel<>();
        String[] portNames = SerialPortList.getPortNames();
        for (String names : portNames) {
            lm.addElement(names);
        }
        return lm;
    }
}
