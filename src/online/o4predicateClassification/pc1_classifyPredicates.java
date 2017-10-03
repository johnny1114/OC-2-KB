/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.o4predicateClassification;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import static weka.core.SerializationHelper.read;
import weka.core.converters.ConverterUtils;
import static weka.core.SerializationHelper.read;

/**
 *
 * @author juanlossio
 */
public class pc1_classifyPredicates {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Integer> al_index = new ArrayList<>();

    
    private static void read_lines(String file) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        boolean eof = false;            
        while (!eof) {
            String sLinea = br.readLine();
            if (sLinea == null) {
                eof = true;
            }else{
                switch(sLinea.trim()){
                    case "associated":  
                        System.out.println("C1"); break;
                    case "is a":  
                        System.out.println("C2"); break;
                    case "causally upstream of, negative effect":  
                        System.out.println("C3"); break;
                    case "contributes to":  
                        System.out.println("C4"); break;
                    case "benefits":  
                        System.out.println("C5"); break;
                    case "causally upstream of, positive effect":  
                        System.out.println("C6"); break;
                    case "treats":  
                        System.out.println("C7"); break;
                    case "correlated with":  
                        System.out.println("C8"); break;
                    case "has":  
                        System.out.println("C9"); break;
                    case "causes or contributes to condition":  
                        System.out.println("C10"); break;
                    case "alters":  
                        System.out.println("C11"); break;
                    case "positively regulates":  
                        System.out.println("C12"); break;
                    default:  
                        System.out.println("C13"); break;
                }
            }
        }
        br.close();
        fr.close();
    }
    
    private static void read_index(String file) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        boolean eof = false;            
        while (!eof) {
            String sLinea = br.readLine();
            if (sLinea == null) {
                eof = true;
            }else{
                al_index.add(Integer.parseInt(sLinea.trim())-1);
            }
        }
        br.close();
        fr.close();
        System.out.println(al_index.size());
    }
    
    public static ArrayList<Integer> classify_predicate(String file_arff){
        ArrayList<Integer> al_aux = new ArrayList<>();
                
        try{
            InputStream stream = new ByteArrayInputStream(file_arff.getBytes());
            //System.out.println(stream);
            
            RandomForest ranfor = (RandomForest) read(System.getProperty("user.dir") + File.separator + "resource/relationdetection.model");
            
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(stream);
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
            System.err.println("o4classificationPredicateClasses : " + ex.toString());
        }
        return al_aux;
    }
    
    public static void classify_predicate(ArrayList<ArrayList<String>> al_, ArrayList<Integer> al_aux){
        //read_lines("/Users/jlossioventura/Desktop/TrueRelationComplete/cPredicateMapping.txt");
    }
    
}
