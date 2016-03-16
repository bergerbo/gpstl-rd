package upmc.gpstl.Logger;

import java.io.*;

/**
 * Created by Benjamin on 24/02/2016.
 */
public class Main {
    public static void main(String[] args) {
        File config = new File("./config.json");

        if (!config.exists()) {
            try {
                InputStream inputStream = ClassLoader.getSystemResourceAsStream("config.json");
                OutputStream outputStream =
                        new FileOutputStream(new File("./config.json"));

                int read;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Settings.load();
        FrameSerial f = new FrameSerial();
    }
}
