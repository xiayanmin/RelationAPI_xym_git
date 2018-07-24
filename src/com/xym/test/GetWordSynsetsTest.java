package com.xym.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class GetWordSynsetsTest {

	
	 private static String WORDNET_PATH = "F:\\班级与部门\\夏艳敏学姐实验\\WordNet-3.0\\WordNet-3.0\\dict";

	    public static void main(String[] args) throws IOException{
	        File wnDir=new File(WORDNET_PATH);
	        URL url=new URL("file", null, WORDNET_PATH);
	        IDictionary dict=new Dictionary(url);
	        dict.open();//打开词典
	        getSynonyms(dict); //testing
	    }

	    
	    public static void getSynonyms(IDictionary dict){
	        // look up first sense of the word "go"
	        //IIndexWord idxWord2 = dict.
	        IIndexWord idxWord =dict.getIndexWord("turn", POS.NOUN);
	        for(IWordID wordID:idxWord.getWordIDs()){
	        	IWord word = dict.getWord(wordID);
		        ISynset synset = word.getSynset (); //ISynset是一个词的同义词集的接口

		     // iterate over words associated with the synset
		        for(IWord w : synset.getWords())
		            System.out.println(w.getLemma());//打印同义词集中的每个同义
		        
		        System.out.println();
	        }
	        
	    }
	        
//	        IWordID wordID = idxWord.getWordIDs().get(0) ; // 1st meaning
	        
	        
}
