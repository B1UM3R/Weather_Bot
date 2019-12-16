import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



class Graphic {
    private int[] date;
    private int[] temperature;
    private String city;

    Graphic(String city, int[] date, int[] temperature) {
        this.date = date;
        this.temperature = temperature;
        this.city = city;
    }

    JFrame createGraphic() {
        XYSeries series = new XYSeries("Погода");

        for (int i = 0; i < date.length; i++) {
            series.add(date[i], temperature[i]);
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("Погода в " + city, "Время", "Температура",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("График погоды на 5 дней");
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(720, 480);
        return frame;
    }
}
