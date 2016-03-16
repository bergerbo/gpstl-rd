package upmc.gpstl.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hoboris on 3/16/16.
 */
public class Settings {
    public static Color BACKGROUND;
    public static int FONT_SIZE;

    private static HashMap<String,Color> colors = new HashMap<>();

    public static Color getColor(Type type) {
        return colors.get(type.toString().toLowerCase());
    }

    public static void setColor(String name, Color color){
        colors.put(name.toLowerCase(),color);
    }

    public static void save(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("size", FONT_SIZE);
            String backgroundRGB = BACKGROUND.getRed() + "," + BACKGROUND.getGreen() + "," + BACKGROUND.getBlue();
            jsonObject.put("background", backgroundRGB);

            JSONObject logColors = new JSONObject();
            for (Map.Entry e: colors.entrySet()) {
                Color c = (Color) e.getValue();
                String colorRGB = c.getRed() + "," + c.getGreen() + "," + c.getBlue();
                logColors.put(e.getKey(),colorRGB);
            }

            jsonObject.put("logColors",logColors);

            PrintWriter out = new PrintWriter("./config.json", "UTF-8");
            out.print(jsonObject.toJSONString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(
                    "./config.json"));

            JSONObject jsonObject = (JSONObject) obj;

            String size = ((Long) jsonObject.get("size")).toString();
            String[] backgroundRGB = ((String) jsonObject.get("background")).split(",");

            BACKGROUND = new Color(Integer.valueOf(backgroundRGB[0]), Integer.valueOf(backgroundRGB[1]), Integer.valueOf(backgroundRGB[2]));
            FONT_SIZE = Integer.valueOf(size);

            JSONObject logColors =  (JSONObject)jsonObject.get("logColors");
            Set<Map.Entry> entrySet = logColors.entrySet();
            for(Map.Entry e : entrySet){
                String type = e.getKey().toString().toLowerCase();
                String[] rgb = ((String)e.getValue()).split(",");
                Color color = new Color(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2]));
                colors.put(type,color);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
