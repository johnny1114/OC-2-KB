/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.o3relationDetection;


import java.util.ArrayList;


/**
 *
 * @author juanlossio
 */
public class rd1_tripleCombination {

    /**
     * @param args the command line arguments
     */
    
    static int max_number_entities = 4; 
    static int max_number_predicates = 3; 
    
    static ArrayList<String> is_related_sentence_ = new ArrayList();
    static ArrayList<String> is_related_index_ = new ArrayList();
    static ArrayList<String> is_related_predicate_ = new ArrayList();
    static ArrayList<String> is_related_subject_ = new ArrayList();
    static ArrayList<String> is_related_object_ = new ArrayList();    
    
    public static ArrayList<ArrayList<String>> tripleCombination(ArrayList<String> sentences_, ArrayList<String> entity_predicate_) {        
        ArrayList<ArrayList<String>> al_ = new ArrayList<>();
        try{
            for(int i=0;i<sentences_.size();i++){
                combine_entites_predicates(" " + entity_predicate_.get(i), " " + sentences_.get(i),i);
            }
            
            al_.add(is_related_index_);
            al_.add(is_related_sentence_);
            al_.add(is_related_subject_);
            al_.add(is_related_predicate_);
            al_.add(is_related_object_);
            
        }
        catch(Exception ex){
            System.out.println("Error : " + ex.toString());
        }
        return al_;
    }
    
    public static void combine_entites_predicates(String ent_pred_to_separate, String sentence__, int i){
        try{
            //System.out.println(sentence__);
            
            ent_pred_to_separate = ent_pred_to_separate.trim();
            String Words [] = ent_pred_to_separate.split(";");
            
            //System.out.println(Words[0]);
            //System.out.println(Words[1]);
            
            String  vector_1 [] = Words[0].split(",");
            String  vector_2 [] = Words[1].split(",");
            
            //System.out.println(vector_1.length + "   " + vector_2.length);
            
            if(vector_1.length>1 && vector_2.length>0){
                String  vector_entities [];
                String vector_predicates [];
                
                if(vector_1.length>max_number_entities){
                    vector_entities = order_array_list(sentence__,vector_1,max_number_entities);
                }else{
                    vector_entities = order_array_list(sentence__,vector_1,vector_1.length);
                }
                  
                if(vector_2.length>max_number_predicates){
                    vector_predicates = order_array_list(sentence__,vector_2,max_number_predicates);
                }else{
                    vector_predicates = order_array_list(sentence__,vector_2,vector_2.length);
                }
                
                
                for(int pre=0; pre<vector_predicates.length;pre++){
                    for(int sub=0; sub<vector_entities.length-1;sub++){
                        for(int obj=sub+1; obj<vector_entities.length;obj++){
                            is_related_index_.add(String.valueOf(i));

                            is_related_sentence_.add(sentence__.trim());

                            is_related_subject_.add(vector_entities[sub].trim());

                            is_related_predicate_.add(vector_predicates[pre].trim());

                            is_related_object_.add(vector_entities[obj].trim());

                            /*System.out.println(String.valueOf(i) + " " + 
                                    vector_entities[sub].trim() + " " + 
                                    vector_predicates[pre].trim() + " " + 
                                    vector_entities[obj].trim());
                            */
                        }
                    }
                }
            }
 
        }catch(Exception ex){
            //System.out.println(i +  "      "  + ent_pred_to_separate);
            //System.err.println("combine_entites_predicates : " + ex.toString());
        }
    }
           
    
    public static String[] order_array_list(String sentence__, String[] al_, int max____){
        String vector_return [] = new String [max____];
        for (int i=0;i<max____;i++){
            vector_return[i] = al_[i].trim();
        }
        
        try{
            for(int i=0;i<max____-1;i++){
                for(int j=i+1;j<max____;j++){
                    if(sentence__.indexOf(" " + vector_return[i].trim() + " ") > sentence__.indexOf(" " + vector_return[j].trim() + " ")){
                        String aux__ = vector_return[i];
                        vector_return[i] = vector_return[j]; 
                        vector_return[j] = aux__; 
                    } 
                }
            }
            
        }catch(Exception ex){
            System.err.println("order_array_list : " + ex.toString());
        }
        return vector_return;
    }
    
}
