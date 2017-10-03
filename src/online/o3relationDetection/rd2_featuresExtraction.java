/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.o3relationDetection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import online.resources.Functions;

/**
 *
 * @author jlossioventura
 */
public class rd2_featuresExtraction {

    /**
     * @param args the command line arguments
     */
    //number of words
    static int num_of_words_sentence;
    static int num_of_words_subject;
    static int num_of_words_object;
    static int num_of_words_predicate;
    
    //number of biomedical entities in UMLS 
    static int num_biomed_entities_sentence;
    static int num_biomed_entities_subject;
    static int num_biomed_entities_object;
    static int num_biomed_entities_predicate;
   
    //is sub/obj/pred in dictionary = MeSH, SNOMED, NCI, DOID
    static boolean is_subject_in_UMLS;
    static boolean is_object_in_UMLS;
    static boolean is_predicate_in_UMLS;
    
    //tf for sub/obj/pred  
    static int tf_subject;
    static int tf_object;
    static int tf_predicate;
    
    //for tf of sub/obj/pred of each sentence
    static int min_;
    static int max_;
    static double mean_;
    static double sd_;
    
    //for tf of all words in sub
    static int min_s;
    static int max_s;
    static double mean_s;
    static double sd_s;
    
    //for tf of all words in obj
    static int min_o;
    static int max_o;
    static double mean_o;
    static double sd_o;
    
    //for tf of each word in sub
    static int min_p;
    static int max_p;
    static double mean_p;
    static double sd_p;
    
    //distance between sub/obj/pred
    static int dist_sub_obj;
    static int dist_sub_pred;
    static int dist_pred_obj;
    
    
    
    
    static String is_a_relation = "?";
    
    //elements for dictionaries
    static HashMap hm_UMLS = new HashMap();
    static String file_UMLS;
    
    
    static HashMap hm_MeSH = new HashMap();
    static String file_MeSH;
    
    static HashMap hm_NCI = new HashMap();
    static String file_NCI;
   
    static HashMap hm_SNOMED = new HashMap();
    static String file_SNOMED;
    
    
    public static Object[] extract_features_from_datset(ArrayList<ArrayList<String>> al_) {
        
        Object[] obj = new Object[2];
        ArrayList<String> instances = new ArrayList<>();
        String line_ = Functions.load_file_(System.getProperty("user.dir") + File.separator + "resource/headingforARFF.txt");
        
        file_UMLS = System.getProperty("user.dir") + File.separator + "resource/UMLS.csv";
        file_MeSH = System.getProperty("user.dir") + File.separator + "resource/MeSH.csv";
        file_NCI = System.getProperty("user.dir") + File.separator + "resource/NCI.csv";
        file_SNOMED = System.getProperty("user.dir") + File.separator + "resource/SNOMED.csv";
        
        // Load different dictionaries
        hm_UMLS = Functions.load_terms_Dictionary(file_UMLS);
        hm_MeSH = Functions.load_terms_Dictionary(file_MeSH);
        hm_NCI = Functions.load_terms_Dictionary(file_NCI);
        hm_SNOMED = Functions.load_terms_Dictionary(file_SNOMED);
        
        //values_NounVerbAdj = Functions.load_file(file_values_NounVerbAdj);
        //System.out.println("Good noun verbs   "  +  values_NounVerbAdj.size());
        
        // index of al_
        // 0  index;
        // 1  sentence;
        // 2  subject;
        // 3  predicate;
        // 4  object;
                    
                
        try{            
            for(int i=0;i<al_.get(1).size();i++){
                num_of_words_sentence = Functions.number_of_word(al_.get(1).get(i));
                num_of_words_subject = Functions.number_of_word(al_.get(2).get(i));
                num_of_words_object = Functions.number_of_word(al_.get(4).get(i));
                num_of_words_predicate = Functions.number_of_word(al_.get(3).get(i));

                num_biomed_entities_sentence = Functions.nb_terms_Dictionary(al_.get(1).get(i), hm_UMLS);
                num_biomed_entities_subject = Functions.nb_terms_Dictionary(al_.get(2).get(i), hm_UMLS);
                num_biomed_entities_object = Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_UMLS);
                num_biomed_entities_predicate = Functions.nb_terms_Dictionary(al_.get(3).get(i), hm_UMLS);

                is_subject_in_UMLS = hm_UMLS.containsKey(al_.get(2).get(i));
                is_object_in_UMLS = hm_UMLS.containsKey(al_.get(4).get(i));
                is_predicate_in_UMLS = hm_UMLS.containsKey(al_.get(2).get(i));

                dist_sub_obj = Functions.distance(" " + al_.get(1).get(i), " " + al_.get(2).get(i) + " ", " " + al_.get(4).get(i)+ " ");
                dist_sub_pred = Functions.distance(" " + al_.get(1).get(i), " " + al_.get(2).get(i) + " ", " " + al_.get(4).get(i)+ " ");
                dist_pred_obj = Functions.distance(" " + al_.get(1).get(i), " " + al_.get(4).get(i)+ " ", " " + al_.get(4).get(i)+ " ");

                
                String aux_ = num_of_words_sentence + "," +
                        num_of_words_subject + "," +
                        num_of_words_object + "," +
                        num_of_words_predicate + "," +

                        //// Implica UMLS Atributos
                        num_biomed_entities_sentence + "," +
                        num_biomed_entities_subject + "," +
                        num_biomed_entities_object + "," +
                        num_biomed_entities_predicate + "," +

                        (is_subject_in_UMLS?1:0) + "," +
                        (is_object_in_UMLS?1:0) + "," +
                        (is_predicate_in_UMLS?1:0) + "," +

                        //// Implica Mesh Atributos
                        Functions.nb_terms_Dictionary(al_.get(1).get(i), hm_MeSH) + "," +
                        Functions.nb_terms_Dictionary(al_.get(2).get(i), hm_MeSH) + "," +
                        Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_MeSH) + "," +
                        Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_MeSH) + "," +

                        (hm_MeSH.containsKey(al_.get(2).get(i))?1:0) + "," +
                        (hm_MeSH.containsKey(al_.get(4).get(i))?1:0) + "," +
                        (hm_MeSH.containsKey(al_.get(4).get(i))?1:0) + "," +

                        //// Implica NCI Atributos
                        Functions.nb_terms_Dictionary(al_.get(1).get(i), hm_NCI) + "," +
                        Functions.nb_terms_Dictionary(al_.get(2).get(i), hm_NCI) + "," +
                        Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_NCI) + "," +
                        Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_NCI) + "," +

                        (hm_NCI.containsKey(al_.get(2).get(i))?1:0) + "," +
                        (hm_NCI.containsKey(al_.get(4).get(i))?1:0) + "," +
                        (hm_NCI.containsKey(al_.get(4).get(i))?1:0) + "," +

                        //// Implica SNOMED Atributos
                        Functions.nb_terms_Dictionary(al_.get(1).get(i), hm_SNOMED) + "," +
                        Functions.nb_terms_Dictionary(al_.get(2).get(i), hm_SNOMED) + "," +
                        Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_SNOMED) + "," +
                        Functions.nb_terms_Dictionary(al_.get(4).get(i), hm_SNOMED) + "," +

                        (hm_SNOMED.containsKey(al_.get(2).get(i))?1:0) + "," +
                        (hm_SNOMED.containsKey(al_.get(4).get(i))?1:0) + "," +
                        (hm_SNOMED.containsKey(al_.get(4).get(i))?1:0) + "," +

                        dist_sub_obj + "," +        
                        dist_sub_pred + "," +
                        dist_pred_obj + "," +  

                        is_a_relation +
                        "\n"
                        ;
                line_ = line_ + aux_;
                instances.add(aux_);
            }
            obj[0] = line_;
            obj[1] = instances;
        }catch(Exception ex){
            System.out.println("Error  " + ex.toString());
        }
        
        
        return obj;
        
    }
    
}
