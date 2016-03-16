package upmc.gpstl.Logger;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Rectangle;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Benjamin on 24/02/2016.
 */
public class FramePlot extends JFrame{
    private Chart plotPanel;
    private Scatter scatter;
    private ArrayList<Coord3d> coords;
    private Coord3d[] coordsArray;

    public FramePlot(){
        super("Plot");
        this.setSize(1200,800);
        this.setLayout(new BorderLayout());

        coords = new ArrayList<>();

        scatter = new Scatter();
        fillScatter();
        scatter.setColor(Color.BLUE);

        plotPanel = AWTChartComponentFactory.chart(Quality.Advanced, "newt");


        plotPanel.getScene().getGraph().add(scatter);

//        ChartLauncher.openChart(plotPanel, new Rectangle(0, 0, 122, 122), "122");

//        plotPanel = new Chart("newt");
        JPanel panel = new JPanel(); // Your code
        panel.setBorder(new MatteBorder(5, 5, 5, 5, java.awt.Color.BLACK)); // To see where is the JPanel drawn
        panel.setLayout(new BorderLayout()); // Use BorderLayout
        System.out.println(((Component)plotPanel.getCanvas()).getPreferredSize()); // Displays "java.awt.Dimension[width=0,height=0]", which is why it's not showing anything
        panel.add((Component)plotPanel.getCanvas(), BorderLayout.CENTER); // Will make the Canvas grow in size, as the JPanel is resized
        add(panel);
        setVisible(true);
    }

    private void refresh(){
//        plotPanel.getScene().clear();
        scatter.clear();
        fillScatter();
    }

    private void fillScatter(){

        coordsArray = new Coord3d[coords.size()];
        coordsArray = coords.toArray(coordsArray);
        scatter.setData(coordsArray);
        scatter.setColor(Color.BLUE);
//        plotPanel.getScene().getGraph().add(scatter);
    }

    public void addPoint(double x, double y, double z){
        coords.add(new Coord3d(x*100,y*100,z*100));
        refresh();
    };
}
