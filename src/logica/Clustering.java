package logica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static logica.Regresion.getInstanceData;
import vistas.Ventana;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;


public class Clustering {

    public static void main(String[] args) {

        try {
            String ruta = "temp_velvi.arff";
            
            Instances dataset = new Instances(new BufferedReader(new FileReader(ruta)));
            
            SimpleKMeans skm = new SimpleKMeans();
            
            skm.setNumClusters(3);
            skm.setPreserveInstancesOrder(true);
            skm.buildClusterer(dataset);
            
            Instances centroides = skm.getClusterCentroids();
            
            for (int i = 0; i < centroides.numInstances(); i++) {
                Instance ins = centroides.instance(i);
                System.out.println("X: "+ins.toString(0) + " Y: "+ins.toString(1));
            }
            
            int[] asig  = skm.getAssignments();
            int[] clustersSizes = skm.getClusterSizes();
            
            System.out.println("Asignaciones "+Arrays.toString(asig));
            System.out.println("Instancias por cluster "+Arrays.toString(clustersSizes));
            
            //grafica
            Map data = getInstanceData(dataset);
            data.put("asig", asig);

            Ventana ventana = new Ventana(Ventana.CLUSTERING_GRAPH, data);
            ventana.setVisible(true);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Clustering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Clustering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Clustering.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static Map getInstanceData(Instances dataset) {
        
        Map<String, double[]> data = new HashMap<>();
        
        double[] xValues = new double[dataset.numInstances()];
        double[] yValues = new double[dataset.numInstances()];

        for(int i=0; i<dataset.numInstances(); i++) {

            double x = Double.valueOf(dataset.instance(i).toString(0));
            double y = Double.valueOf(dataset.instance(i).toString(1));

            xValues[i] = x;
            yValues[i] = y;
        }
        
        data.put("xValues", xValues);
        data.put("yValues", yValues);
        
        return data;
    }
    
}
