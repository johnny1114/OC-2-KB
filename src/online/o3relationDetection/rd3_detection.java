/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.o3relationDetection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import static weka.core.converters.ConverterUtils.DataSource;
import static weka.core.SerializationHelper.read;
/**
 *
 * @author jlossioventura
 */
public class rd3_detection {
    
    public static ArrayList<Integer> detect_relation(String file_arff){
        ArrayList<Integer> al_aux = new ArrayList<>();
                
        try{
            InputStream stream = new ByteArrayInputStream(file_arff.getBytes());
            //System.out.println(stream);
            
            RandomForest ranfor = (RandomForest) read(System.getProperty("user.dir") + File.separator + "resource/relationdetection.model");
            
            DataSource source = new DataSource(stream);
            Instances testDataset = source.getDataSet();
            
            testDataset.setClassIndex(testDataset.numAttributes()-1);            

            for (int i = 0; i < testDataset.numInstances(); i++) {
                //get Instance object of current instance
                Instance newInst = testDataset.instance(i);
                //call classifyInstance, which returns a double value for the class
                double[] predranforvalues = ranfor.distributionForInstance(newInst);
                
                if(predranforvalues[0]>=0.94){
                    al_aux.add(i);
                    //System.out.println((i+1) + "   " + testDataset.classAttribute().value(0) + " : " + Double.toString(predranforvalues[0]));
                }
            }
        }catch(Exception ex){
            System.err.println("Error in rd3_detection.detect_relation : " + ex.toString());
        }
        return al_aux;
    }
}
