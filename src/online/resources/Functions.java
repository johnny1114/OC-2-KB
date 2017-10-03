/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package online.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author juanlossio
 */
public class Functions {
    
    
    /*
     * Caracteristicas directas del corpus
     */
    
    //Return number of words of a text
    public static int number_of_word(String term){
        int num_words = 0;
        String [] phrases = term.trim().split("[\\'\\ ]+");
        num_words = phrases.length;
        return num_words;
    }
    
    public static int nb_terms_Dictionary(String line, HashMap hm){
        int num = 0;
        String words[];
        String aux_;
        try{
            words = line.trim().split(" ");
            for(int i=0;i<words.length;i++){
                aux_ =  words[i];
                num = num + (int)(hm.containsKey(aux_.trim())?1:0);
                for(int j=i+1;j<words.length;j++){
                    aux_ = aux_ + " " + words[j];
                    num = num + (int)(hm.containsKey(aux_.trim())?1:0);
                }
                
            }
        }catch(Exception ex){
            System.err.println("nb_terms_Dictionary : " + ex.toString());
        }
        return num;
    }
    
    public static ArrayList<String> long_terms_Dictionary(String line, HashMap hm, HashMap hmsw){
        ArrayList<String> al = new ArrayList<>();
        ArrayList<String> al_aux = new ArrayList<>();
        
        String words[];
        String aux_;
        try{
            words = line.trim().split(" ");
            for(int i=0;i<words.length;i++){
                aux_ =  words[i];
                if(hm.containsKey(aux_.trim().toLowerCase()) && !hmsw.containsKey(aux_.trim().toLowerCase())){
                    try{
                        double x = Double.parseDouble(aux_);
                    }catch(Exception ex){
                        al.add(aux_);
                    }
                }
                for(int j=i+1;j<words.length;j++){
                    aux_ = aux_ + " " + words[j];
                    if(hm.containsKey(aux_.trim().toLowerCase()) && !hmsw.containsKey(words[j].trim().toLowerCase())){
                        al.add(aux_);
                    }
                }
                
            }
            
            String aux_rank;
            for(int i=0;i<al.size()-1;i++){
                for(int j=i+1;j<al.size();j++){
                    if(number_of_word(al.get(i))<number_of_word(al.get(j))){
                        aux_rank = al.get(i);
                        al.set(i, al.get(j));
                        al.set(j, aux_rank);
                    }
                }
            }
            
            for(int i=0;i<al.size();i++){
                if(!is_in(al_aux,al.get(i))){
                    al_aux.add(al.get(i));
                }
            }
            
        }catch(Exception ex){
            System.err.println("nb_terms_Dictionary : " + ex.toString());
        }
        return al_aux;
    }
    
    public static ArrayList<String> terms_Dictionary(String line, HashMap hm){
        ArrayList<String> al = new ArrayList<>();
        String words[];
        String aux_;
        try{
            words = line.trim().split(" ");
            for(int i=0;i<words.length;i++){
                aux_ =  words[i];
                if(hm.containsKey(aux_.trim())){
                    try{
                        double x = Double.parseDouble(aux_);
                    }catch(Exception ex){
                        al.add(aux_);
                    }
                }
                for(int j=i+1;j<words.length;j++){
                    aux_ = aux_ + " " + words[j];
                    if(hm.containsKey(aux_.trim())){
                        al.add(aux_);
                    }
                }
                
            }
        }catch(Exception ex){
            System.err.println("nb_terms_Dictionary : " + ex.toString());
        }
        return al;
    }
    
    private static boolean is_in(ArrayList<String> alc, String term){
        for(int i=0;i<alc.size();i++){
            if(alc.get(i).contains(term+ " ") || alc.get(i).contains(" " + term)){
                return true;
            }
        }
        return false;
    }
    
    public static ArrayList<String> load_file_without_CASE(String file){
        ArrayList<String> _line = new ArrayList();
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                if (sLinea == null){
                    eof = true;
                }else{
                    _line.add(sLinea.trim());
                }
            }
            br.close();
            fr.close();
            
        }catch(Exception ex){
            System.err.println("load_terms_ : " + ex.toString());
        }
        return _line;
    }
    
    public static ArrayList<String> load_file(String file){
        ArrayList<String> _line = new ArrayList();
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                if (sLinea == null){
                    eof = true;
                }else{
                    _line.add(sLinea.trim().toLowerCase());
                }
            }
            br.close();
            fr.close();
            
        }catch(Exception ex){
            System.err.println("load_terms_ : " + ex.toString());
        }
        return _line;
    }
    
    
    public static String load_file_(String file){
        String _line = "";
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                if (sLinea == null){
                    eof = true;
                }else{
                    _line = _line + sLinea + "\n";
                }
            }
            br.close();
            fr.close();
            
        }catch(Exception ex){
            System.err.println("load_file_ : " + ex.toString());
        }
        return _line;
    }
    
    
    
    public static HashMap load_terms_UMLS(String file){
        HashMap _UMLS = new HashMap();
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                if (sLinea == null){
                    eof = true;
                }else{
                    _UMLS.put(sLinea.trim(), 1);
                }
            }
            br.close();
            fr.close();
            
        }catch(Exception ex){
            System.err.println("load_terms_UMLS : " + ex.toString());
        }
        return _UMLS;
    }
    
    public static HashMap load_terms_Dictionary(String file){
        HashMap _UMLS = new HashMap();
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                if (sLinea == null){
                    eof = true;
                }else{
                    _UMLS.put(sLinea.trim(), 1);
                }
            }
            br.close();
            fr.close();
            
        }catch(Exception ex){
            System.err.println("load_terms_UMLS : " + ex.toString());
        }
        return _UMLS;
    }
    
    public static HashMap load_terms_Stop_Words(String file){
        HashMap _sw = new HashMap();
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String sLinea = br.readLine();
                if (sLinea == null){
                    eof = true;
                }else{
                    _sw.put(sLinea.trim(), 1);
                }
            }
            br.close();
            fr.close();
            
        }catch(Exception ex){
            System.err.println("load_terms_UMLS : " + ex.toString());
        }
        return _sw;
    }
   
    
    public static int distance(String sentence, String e1, String e2){
        int num_ = 0;
        try{
            int index_e1 = sentence.indexOf(e1);
            int index_e2 = sentence.indexOf(e2);
            
            if(index_e1<index_e2){
                String aux_ = sentence.substring(index_e1+e1.length(),index_e2).trim();
                num_ = number_of_word(aux_);
            }else{
                String aux_ = sentence.substring(index_e2+e2.length(),index_e1).trim();
                num_ = number_of_word(aux_);
            }
            
        }catch(Exception ex){
            //System.err.println("distance : " + ex.toString());
        }
        return num_;
    }
    
    
}
