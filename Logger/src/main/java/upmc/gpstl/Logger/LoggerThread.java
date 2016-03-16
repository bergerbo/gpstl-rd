package upmc.gpstl.Logger;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Benjamin on 24/02/2016.
 */
public class LoggerThread extends Thread {

    private SerialPort serialPort;

    public LoggerThread(String port) {
        this.serialPort = new SerialPort(port);
    }

    @Override
    public void run() {
        try {
            File file = new File("logs.txt");
            File points = new File("input.point");
            FileWriter fw = new FileWriter(file);
            FileWriter fwPoint = new FileWriter(points);
            FrameLogger fl = new FrameLogger();
            fl.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent) {
                    fl.dispose();
                    try {
                        serialPort.closePort();
                        fw.close();
                        fwPoint.close();
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            });
            fl.setVisible(true);

//            FramePlot fp = new FramePlot();
//            fp.setVisible(true);

            serialPort.openPort();//Open serial port
            serialPort.setParams(9600, 8, 1, 0);//Set params.
            StringBuffer inputBuffer = new StringBuffer();

            while (true) {
                inputBuffer.append(serialPort.readString());
                String inputString = inputBuffer.toString();
                if (inputString.contains("\n")) {
                    Color col;
                    String[] logs = inputString.split("\n");

                    Log log = new Log(logs[0]);
                    String logString = log.toString();

                    if(log.getType() == Type.POINT){
                        fwPoint.write(logString);
                    } else {
                        fw.write(logString);
                    }

                    fl.display(logString, Settings.getColor(log.getType()));

                    inputBuffer = inputBuffer.delete(0,logs[0].length());
                }

            }
        } catch (SerialPortException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
