import jssc.SerialPort;
import jssc.SerialPortException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin on 24/02/2016.
 */
public class LoggerThread extends Thread {

    private SerialPort serialPort;

    public LoggerThread(String port){
        this.serialPort = new SerialPort(port);
    }

    @Override
    public void run(){
        try {
            File file = new File("logs.txt");
            File points = new File("input.point");
            FileWriter fw = new FileWriter(file);
            FileWriter fwPoint = new FileWriter(points);
            FrameLogger fl = new FrameLogger();
            fl.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
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
            serialPort.openPort();//Open serial port
            serialPort.setParams(9600, 8, 1, 0);//Set params.
            String inputLine;
            StringBuilder message = new StringBuilder();

            while(true){
                inputLine=serialPort.readString();
                if(inputLine!=null) {
                    message.append(inputLine);
                    if(message.toString().contains("\n")){
                        Color col;
                        String[] log = message.toString().split("\n");
                        String messageWithoutTag = log[0]+"\n";
                        StringBuilder finalMessage = new StringBuilder("[");
                        finalMessage.append(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                                .append(":")
                                .append(Calendar.getInstance().get(Calendar.MINUTE))
                                .append("] : ");

                        if (messageWithoutTag.contains("/d")) {
                            col = FrameLogger.DEBUG;
                            messageWithoutTag = messageWithoutTag.substring(3);
                        } else if (messageWithoutTag.contains("/e")) {
                            col = FrameLogger.ERROR;
                            messageWithoutTag = messageWithoutTag.substring(3);
                        } else if (messageWithoutTag.contains("/i")) {
                            col = FrameLogger.INFO;
                            messageWithoutTag = messageWithoutTag.substring(3);
                        } else if (messageWithoutTag.contains("/w")) {
                            col = FrameLogger.WARNING;
                            messageWithoutTag = messageWithoutTag.substring(3);
                        } else if(messageWithoutTag.contains("/p")) {
                            col = FrameLogger.POINT;
                            messageWithoutTag = messageWithoutTag.substring(3);
                            fwPoint.write(messageWithoutTag);
                        }
                        else{
                            col = FrameLogger.DEFAULT;
                        }
                        finalMessage.append(messageWithoutTag);
                        fw.write(finalMessage.toString());
                        fl.display(finalMessage.toString(), col);
                        message = new StringBuilder();
                        for(int i = 1; i < log.length; i++) {
                            message.append(log[i]);
                            if(i<log.length-1)message.append("\n");
                        }
                    }
                }
            }
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
