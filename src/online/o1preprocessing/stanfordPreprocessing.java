/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package online.o1preprocessing;

import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.util.StringUtils;

/** 
 * Preprocessing with Stanford. 
 * @author  Juan Antonio Lossio-Ventura 
 */ 
public class stanfordPreprocessing { 
	
    public static ArrayList<String> preprocess(String file_in){ 
        ArrayList<String> al_POS= new ArrayList<>();
        
        try{
            DocumentPreprocessor dp = new DocumentPreprocessor(file_in);
            for (List<HasWord> sentence : dp) {
                String sentence__ = StringUtils.join(sentence).toLowerCase();
                sentence__ = sentence__.replace(" '", "'");
                sentence__ = sentence__.replace("-lrb-", "(");
                sentence__ = sentence__.replace("-rrb-", ")");
                al_POS.add(sentence__);
                //System.out.println(sentence__);
            }
        }catch (Exception ex){
            System.err.println("Preprocess: " + ex.toString());
        }
        return al_POS;
    }
    
}