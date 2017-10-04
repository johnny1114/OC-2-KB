/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.o5RDFcreation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import org.apache.jena.rdf.model.*;

/**
 *
 * @author jlossioventura
 */
public class RDFcreation {
    static String tutorialURI  = "http://obesity-cancer-uf/";
    static String Name   = "Brian McBride";
    static String Email1 = "jlossioventura@ufl.edu";
    static String Email2 = "bianjiang@ufl.edu";
    static String title        = "Obesity and cancer knowledge base";
    static String date         = "09/30/2017";
 
       
    public static String createRDF(ArrayList<ArrayList<String>> al_, ArrayList<Integer> al_aux, String directory_out) {
        // some definitions
        // 0   index
        // 1   sentence
        // 2   subject
        // 3   predicate;
        // 4   object;
        // 5   features
            
        String yourNamespace = "http://obesity-cancer-uf/";
        
        try{
            String givenName    = "oc";
            String familyName   = "oc";
            String fullName     = givenName + " " + familyName;
            // create an empty model
            Model model = ModelFactory.createDefaultModel();

            for(int j=0;j<al_aux.size();j++){
                Resource instance1 = model.createResource(yourNamespace + al_.get(2).get(j).replace(" ", "_"));
                RDFNode instance2 = model.createResource(yourNamespace + al_.get(4).get(j).replace(" ", "_"));
                Property hasName = ResourceFactory.createProperty(yourNamespace, al_.get(3).get(j).replace(" ", "_")); 
                //instance1.addProperty(hasName, instance2); 
                Statement s = ResourceFactory.createStatement(instance1, hasName, instance2);
                model.add(s); // add the statement (triple) to the model
            } 

            //model.write(System.out);
            //System.out.println(model);
            
            
            FileWriter fw = new FileWriter(directory_out + File.separator + "oc2kb.rdf");
            BufferedWriter bw = new BufferedWriter(fw);

            model.write(bw);

            bw.close();
            fw.close();

            
        }catch(Exception ex){
            System.err.println("Error createRDF: " + ex.toString());
        }
               
        
        return "";
    }
}
