package preprocessamento;

import java.util.ArrayList;
import java.util.Map;

public class main {

	public static void main(String[] args) {
		LeiaCVS obj = new LeiaCVS();
		ArrayList<String> tweets = new ArrayList();	
		ArrayList<String> tweetsProcesso = new ArrayList();	
		tweets = obj.run("bullyng trabalho");
		for (String t : tweets)
	    {
			//---BAIXO NIVEL---
	  	    t = obj.removerEnderecos(t);
	  	    t = obj.removerFotos(t);
	  		t = obj.removerStopWords(t);
	  		t = obj.removerRisada(t);
	  		t = obj.trocarAbreviacoes(t);
	    	t = obj.removerCitation(t);
	  	    t = obj.trocarEmoticons(t);
	  		t = obj.removerNaoAlfabetico(t);
	  		t = obj.removerTextoRepetido(t);
	  		//---MEDIO NIVEL---
	  	    //---ALTO NIVEL ---
	  	    t = obj.removerPalavrasPequenas(t);
	  	    t = obj.padronizaProcessamento(t);
	  	    t = obj.padronizaProcessamento(t);
	  	    //---Adicionando ---
	  	    if (obj.removerTweetsPequenos(t))
	  	    	tweetsProcesso.add(t);
	    }
		obj.verificaRepetidos(tweetsProcesso);
		obj.escreveCSV(tweetsProcesso);
		System.out.println(tweets.size());
		System.out.println(tweetsProcesso.size());
	}
	

}
