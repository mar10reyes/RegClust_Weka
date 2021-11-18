package graficas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficaDispersion {
    
    private double[] x;
    private double[] y;
    private int[] asig;
    
    public GraficaDispersion(double[] x, double[] y, int[] asig){
        this.x = x;
        this.y = y;
        this.asig = asig;
    }
    
    public ChartPanel generarGrafica(){
        
        XYDataset dataset = crearDataset();
        JFreeChart chart = ChartFactory.createScatterPlot("X vs Y","X", "Y", dataset);
        ChartPanel cp = new ChartPanel(chart);
        return cp; 
    }
    
    public XYDataset crearDataset() {
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries c1 = new XYSeries("C1");
        XYSeries c2 = new XYSeries("C2");
        XYSeries c3 = new XYSeries("C3");
               
        for (int i = 0; i < asig.length; i++) {
            
            switch (asig[i]) {
                case 0:
                    c1.add(x[i],y[i]);
                    break;
                case 1:
                    c2.add(x[i],y[i]);
                    break;
                case 2:
                    c3.add(x[i],y[i]);
                    break;
            }
        }
        
        dataset.addSeries(c1);
        dataset.addSeries(c2);
        dataset.addSeries(c3);
        
        return dataset; 
    }
    
}