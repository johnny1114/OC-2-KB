package online.o2extractEntPre;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jlossioventura
 */
public class extractEntityPredicate {

    static String file_entities;
    static String file_predicates;
    
    static ArrayList<String> al_sentences;
    static ArrayList<String> al_entities;
    static ArrayList<String> al_predicates;
    
    static ArrayList<String> stop_words_entities_ = new ArrayList<>(Arrays.asList("normal","intake","intakes","known",
            "preventing","has","have","influences","influence","identified","decreases","associated","associated with","not","acts","act",
            "decrease","high","low","lower","higher","other","on","affect","affects","i","ii","iii","is","a","46","24","use","uses"
            ,"targeting","or","25","be","part","parts","pre","light","novel","none","all","total","with","without","reported","half","benefits","benefit","changes","change",
            "new","be","are","as","at","and","this","well","may","increase","share","related","high","associated","links","play","plays","help","helps"));
    static ArrayList<String> stop_words_predicates_ = new ArrayList<>(Arrays.asList("under","now","well","while","ai","ua","'s","-"));
   
    
    public static ArrayList<String> extract_entity_predicate(ArrayList<String> sentences_) {
        ArrayList<String> al_sentences_entities_predicates= new ArrayList<>();

        file_entities = System.getProperty("user.dir") + File.separator + "resource/EntityDict.txt";
        file_predicates = System.getProperty("user.dir") + File.separator + "resource/PredicateDict.txt";
        
        al_entities = load_entities(file_entities);
        al_predicates = load_predicates(file_predicates);
        
        al_sentences = sentences_;

        
        for(int i=0;i<al_sentences.size();i++){
            try{
                al_sentences_entities_predicates.add(search_ent_pred(al_sentences.get(i)));
            }catch(Exception ex){
                System.err.println("online.o2extractEntPre : " + ex.toString());
                System.err.println("online.o2extractEntPre : " + i + " " + al_sentences.get(i));
            }
            
        }
        
        return al_sentences_entities_predicates;
    }
    
   
    public static String search_ent_pred(String line){
        
        line = line.replace(" '", "'");
        line = " " + line + " ";
                    
                    
        String aux_ent = "";
        String aux_pred = "";
        try{
            for (String al_entitie : al_entities) {
                if(line.contains(" " + al_entitie + " ")){
                    aux_ent = aux_ent + al_entitie + " , ";
                    
                    try{
                        line = line.replace(" " + al_entitie + " ", "  ");                        
                    }catch(Exception exxx){
                        System.err.println("search_ent_pred : " + exxx.toString());
                    }
                }
            }
            
            for (String al_predicate : al_predicates) {
                if(line.contains(" " + al_predicate + " ")){
                    aux_pred = aux_pred + al_predicate + " , ";
                    
                    try{
                        line = line.replace(" " + al_predicate + " ", "  ");
                    }catch(Exception exxx){
                        System.err.println("search_ent_pred : " + exxx.toString());
                    }
                }
            }
            
        }catch(Exception ex){
            System.err.println("search_ent_pred : " + ex.toString());
        }
        
        if(!aux_ent.equalsIgnoreCase("")){
            aux_ent = aux_ent.substring(0,aux_ent.trim().length()-1);
        }
        if(!aux_pred.equalsIgnoreCase("")){
            aux_pred = aux_pred.substring(0,aux_pred.trim().length()-1);
        }
      
        return aux_ent + " ; " + aux_pred;
    }
    
    
    public static ArrayList<String> load_entities(String file){
        ArrayList<String> _line = new ArrayList();
        int max_ = 0;
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                
                if (sLinea == null){
                    eof = true;
                }else{
                    String words [] = sLinea.split(";");
                    int numb_words = words[0].split(" ").length;
                    if(!stop_words_entities_.contains(words[0])){
                        _line.add(words[0]);
                        if(numb_words > max_){
                            max_ = numb_words;
                        }
                    }
                }
            }
            br.close();
            fr.close();
            _line = file_ordered(_line, max_);
        }catch(Exception ex){
            System.err.println("load_entities : " + ex.toString());
        }
        return _line;
    }
    
    public static ArrayList<String> load_predicates(String file){
        ArrayList<String> _line = new ArrayList();
        int max_ = 0;
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                
                if (sLinea == null){
                    eof = true;
                }else{
                    String words [] = sLinea.split(";");
                    int numb_words = words[0].split(" ").length;
                    if(!stop_words_predicates_.contains(words[0])){
                        _line.add(words[0]);
                        if(numb_words > max_){
                            max_ = numb_words;
                        }
                    }
                }
            }
            br.close();
            fr.close();
            _line = file_ordered(_line, max_);
        }catch(Exception ex){
            System.err.println("load_entities : " + ex.toString());
        }
        return _line;
    }
    
    
    private static ArrayList<String> file_ordered(ArrayList<String> __line, int max_){
        ArrayList<String> aux_line = new ArrayList();
        try{
            for(int i=max_; i>0 ;i--){
                for(int j=0;j<__line.size();j++){
                    int numb_words = __line.get(j).split(" ").length;
                    if(numb_words==i){
                        aux_line.add( __line.get(j).trim());
                    }
                }
            }
        }catch(Exception ex){
            System.err.println("file_ordered : " + ex.toString());
        }
        return aux_line;
    }
    
    
}
