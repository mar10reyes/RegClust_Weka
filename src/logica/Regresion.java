package logica;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;
import vistas.Ventana;

public class Regresion {

    public static void main(String[] args) {
    
        try {
            String ruta = "regresion.arff"; 
            Instances dataset = new Instances(new BufferedReader(new FileReader(ruta)));
            dataset.setClassIndex(1);
            
            LinearRegression lr = new LinearRegression();
            lr.buildClassifier(dataset);
            System.out.println(""+lr);
            System.out.println(""+Arrays.toString(lr.coefficients()));
            
            Evaluation ev = new Evaluation(dataset);
            ev.crossValidateModel(lr, dataset, 10, new Random(5), new String[]{});
            System.out.println(""+ev.toSummaryString());
            
            //Predicción
            Instance ins = new Instance(2);
            ins.setDataset(dataset);
            ins.setValue(0, 200);
            System.out.println("Predicción será "+lr.classifyInstance(ins));
            
            //garfica
            Map data = getInstanceData(dataset);
            data.put("coef", lr.coefficients());
            
            Ventana ventana = new Ventana(Ventana.REGRESION_GRAPH, data);
            ventana.setVisible(true);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Regresion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Regresion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Regresion.class.getName()).log(Level.SEVERE, null, ex);
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
