/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.principal;

import java.util.ArrayList;
import online.o1preprocessing.stanfordPreprocessing;
import online.o2extractEntPre.extractEntityPredicate;
import online.o3relationDetection.*;
import online.o4predicateClassification.pc1_classifyPredicates;
import online.o5RDFcreation.RDFcreation;

/**
 *
 * @author jlossioventura
 */
public class oc2kb {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try{
            String file_in = "PATH_TO_YOUR_FILE/your_file.txt";
            String directory_out = "YOUR_DIRECTORY";
        
            //1. Preprocess the file
            ArrayList<String> sentences = stanfordPreprocessing.preprocess(file_in);
            //System.out.println(sentences.size() + "\n" + sentences.get(0));
            System.out.println("1. Finished: Preprocessing");

            //2. Extract entities and predicates from sentences
            ArrayList<String> entity_predicate = extractEntityPredicate.extract_entity_predicate(sentences);
            //System.out.println(entity_predicate.size() + "\n" + entity_predicate.get(0));
            System.out.println("2. Finished: Extraction of entities and predicates");

            //3. Triple combination and relation detection
            ArrayList<ArrayList<String>> al_ = rd1_tripleCombination.tripleCombination(sentences, entity_predicate); // triple combination
            Object [] obj = rd2_featuresExtraction.extract_features_from_datset(al_); // feature extraction for each triple
            al_.add((ArrayList<String>)obj[1]); 
            ArrayList<Integer> al_index_validated = rd3_detection.detect_relation(obj[0].toString());
            System.out.println("3. Finished: Relation detection");

            //4. Predicate classificationb
            pc1_classifyPredicates.classify_predicate(al_,al_index_validated);
            System.out.println("4. Finished: Predicate classification");

            //5. RDF creation
            RDFcreation.createRDF(al_,al_index_validated,directory_out);
            System.out.println("5. Finished: RDF creation");
            
        }catch(Exception ex){
            System.out.println("main : " + ex.toString());
        }
    }
}
