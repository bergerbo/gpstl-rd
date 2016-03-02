/**
 * Created by Benjamin on 02/03/2016.
 */
public class Log {
    private Type type;
    private String msg;

    public Log(Type t, String msg){
        this.type = t;
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
