package upmc.gpstl.Logger;

import java.awt.*;
import java.util.Calendar;

/**
 * Created by Benjamin on 02/03/2016.
 */
public class Log {
    private Type type;
    private String msg;
    private Calendar time;
    
    public Log (String message){
        time = Calendar.getInstance();
        if (message.contains("/d")) {
            type = Type.DEBUG;
            msg= message.substring(3);
        } else if (message.contains("/e")) {
            type = Type.ERROR;
            msg = message.substring(3);
        } else if (message.contains("/i")) {
            type = Type.INFO;
            msg = message.substring(3);
        } else if (message.contains("/w")) {
            type = Type.WARNING;
            msg = message.substring(3);
        } else if (message.contains("/p")) {
            type = Type.POINT;
            msg = message.substring(3);
        } else {
            type = Type.DEFAULT;
        }
    }

    public Type getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String toString(){
        StringBuilder finalMessage = new StringBuilder("[");
        finalMessage.append(time.get(Calendar.HOUR_OF_DAY))
                .append(":")
                .append(time.get(Calendar.MINUTE))
                .append("] : ");

        finalMessage.append(msg);
        return finalMessage.toString();
    }
}
