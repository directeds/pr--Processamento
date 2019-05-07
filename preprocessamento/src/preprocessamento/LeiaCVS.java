package preprocessamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LeiaCVS {
	
		
	public ArrayList<String> run(String x) {
		
		ArrayList<String> tweets = new ArrayList();
	    String arquivoCSV = x+".csv";
	    BufferedReader br = null;
	    String linha = "";
	    String csvDivisor = ";";
	    try {
	        br = new BufferedReader(new FileReader(arquivoCSV));
	        br.readLine();
	        while ((linha = br.readLine()) != null) {

	            String[] tweet = linha.split(csvDivisor);
	            tweets.add(tweet[4]);
	        }
	    	return tweets;

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return tweets;
	}
	
	public void verificaRepetidos(ArrayList<String> repetidos)
	{
		int tam = repetidos.size()-1;
		for (int x = 0; x < tam; x++) {
			String aux = repetidos.get(x);
		    repetidos.remove(aux);
		    while(repetidos.contains(aux))
		    {
		    	repetidos.remove(repetidos.indexOf(aux));
		    	System.out.println(aux);
		    	tam--;
		    }
		    repetidos.add(aux);
		}			
	}

	public void escreveCSV(ArrayList <String> tweets) {
		
		try (PrintWriter writer = new PrintWriter(new File("tweets.csv"))) {

		      StringBuilder sb = new StringBuilder();
		      sb.append("tweet pós-processado");
		      sb.append('\n');
		      
		      for (String t : tweets)
		      {
		    	  sb.append(t);
		    	  sb.append('\n');
		      }

		      writer.write(sb.toString());

		      System.out.println("arquivo CSV criado com sucesso!");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	}
	
	public String padronizaProcessamento(String tweet){
		tweet = tweet.toLowerCase();
		String tweetaux = null;
		Map<String,String> padronizacao = new HashMap<String,String>();
		padronizacao.put("   "," ");
		padronizacao.put("  "," ");
		padronizacao.put("   "," ");
		padronizacao.put("    "," ");
		padronizacao.put("     "," ");

		for (Map.Entry<String, String> entry : padronizacao.entrySet()) {
		    tweetaux = tweet.replaceAll(entry.getKey(),entry.getValue());
			tweet = tweetaux;
		}
		for (Map.Entry<String, String> entry : padronizacao.entrySet()) {
		    tweetaux = tweet.replaceAll(entry.getKey(),entry.getValue());
			tweet = tweetaux;
		}
		String stringfromArray = "" + new String(tweetaux);
		return stringfromArray;
	}
	
	public String removerPalavrasPequenas(String tweet){
		tweet = tweet.toLowerCase();
		String palavras[] = tweet.split(" ");
		
		for (int x = 0; x < palavras.length; x++)
		{

			if (palavras[x].length() < 3)
			{
				tweet = tweet.replaceAll("\\b"+palavras[x]+"\\b","");
			}
		}
		return tweet;
	}
	
	public Boolean removerTweetsPequenos(String tweet){
		tweet = tweet.toLowerCase();
		String palavras[] = tweet.split(" ");
		return palavras.length > 3;
	}

	public String removerCitation(String tweet){
		tweet = tweet.toLowerCase();
		char[] tweetchar = tweet.toCharArray();
		int i;
			while (tweet.contains("@"))
			{
				tweetchar = tweet.toCharArray();
				i = tweet.indexOf('@');
				int aux = i;
				if (tweetchar[i+1] == ' ' && i < tweetchar.length-1)
					i += 2;
				while(tweetchar[i] != ' ' && i < tweetchar.length-1)
				{
					tweetchar[i] = ' ';
					i++;
				}
				while (aux < tweetchar.length) 
				{
					if (i == tweetchar.length)
					{
						while(aux < tweetchar.length)
						{
							tweetchar[aux] = ' ';
							aux++;
						}
						break;
					}
					tweetchar[aux] = tweetchar[i];
					aux++;
					i++;
				}
				tweet = "" + new String(tweetchar);
			}
		return tweet;
	}
	
	public String removerRisada(String tweet){
		tweet = tweet.toLowerCase();
		char[] tweetchar = tweet.toCharArray();
		int i = 0;
			while (i < tweetchar.length  && i < tweetchar.length-3 && tweetchar[i] != ' ')
			{
				if(tweetchar[i] == tweetchar [i+1] && tweetchar[i+1] == tweetchar[i+2])
				{
					int aux = i;
					while(tweetchar[i] != ' ' && i < tweetchar.length)
					{
						tweetchar[i] = ' ';
						i++;
					}
					while (aux < tweetchar.length) 
					{
						if (i == tweetchar.length)
						{
							while(aux < tweetchar.length)
							{
								tweetchar[aux] = ' ';
								aux++;
							}
							break;
						}
						tweetchar[aux] = tweetchar[i];
						aux++;
						i++;
					}
					tweet = "" + new String(tweetchar);
				}
				i++;
			}
		return tweet;
	}
	
	public String removerEnderecos(String tweet){
		tweet = tweet.toLowerCase();
		char tweetchar[] = tweet.toCharArray();
		if (tweet.contains("http"))
		{
			int i = tweet.indexOf("http");
			int aux = i;
			
			int tam = tweetchar.length;
			tam--;
			while( i <= tam)
			{
				if (tweetchar[i] == ' ')
					break;
				tweetchar[i] = ' ';
				i++;
			}
			for (int v = i; v < tweetchar.length; v++)
			{
				tweetchar[aux] = tweetchar[i];
				aux++;
			}
			char[] tweetpronto;
			tweetpronto = new char[tweetchar.length-i];
			tweetpronto = tweetchar;
			
		}
		String stringfromArray = "" + new String(tweetchar);
		return stringfromArray;
		
	}
	
	public String removerFotos(String tweet){
		tweet = tweet.toLowerCase();
		char tweetchar[] = tweet.toCharArray();
		if (tweet.contains("pic.twitter"))
		{
			int i = tweet.indexOf("pic.twitter");
			int aux = i;
			
			int tam = tweetchar.length;
			tam--;
			while( i <= tam)
			{
				if (tweetchar[i] == ' ')
					break;
				tweetchar[i] = ' ';
				i++;
			}
			for (int v = i; v < tweetchar.length; v++)
			{
				tweetchar[aux] = tweetchar[i];
				aux++;
			}
			char[] tweetpronto;
			tweetpronto = new char[tweetchar.length-i];
			tweetpronto = tweetchar;
			
		}
		String stringfromArray = "" + new String(tweetchar);
		return stringfromArray;
		
	}
	
	public String removerStopWords(String tweet){
		String tweetaux = null;
		tweet = tweet.toLowerCase();
		String[] stopwords = {"pra","de","a","o","que","e","do","da","em","um","para","é","com","não","uma","os","no","se","na","por","mais","as","dos","como","mas","foi","ao","ele","das","tem","à","seu","sua","ou","ser","quando","muito","há","nos","já","está","eu","também","só","pelo","pela","até","isso","ela","entre","era","depois","sem","mesmo","aos","ter","seus","quem","nas","me","esse","eles","estão","você","tinha","foram","essa","num","nem","suas","meu","às","minha","têm","numa","pelos","elas","havia","seja","qual","será","nós","tenho","lhe","deles","essas","esses","pelas","este","fosse","dele","tu","te","vocês","vos","lhes","meus","minhas","teu","tua","teus","tuas","nosso","nossa","nossos","nossas","dela","delas","esta","estes","estas","aquele","aquela","aqueles","aquelas","isto","aquilo","estou","está","estamos","estão","estive","esteve","estivemos","estiveram","estava","estávamos","estavam","estivera","estivéramos","esteja","estejamos","estejam","estivesse","estivéssemos","estivessem","estiver","estivermos","estiverem","hei","há","havemos","hão","houve","houvemos","houveram","houvera","houvéramos","haja","hajamos","hajam","houvesse","houvéssemos","houvessem","houver","houvermos","houverem","houverei","houverá","houveremos","houverão","houveria","houveríamos","houveriam","sou","somos","são","era","éramos","eram","fui","foi","fomos","foram","fora","fôramos","seja","sejamos","sejam","fosse","fôssemos","fossem","for","formos","forem","serei","será","seremos","serão","seria","seríamos","seriam","tenho","tem","temos","tém","tinha","tínhamos","tinham","tive","teve","tivemos","tiveram","tivera","tivéramos","tenha","tenhamos","tenham","tivesse","tivéssemos","tivessem","tiver","tivermos","tiverem","terei","terá","teremos","terão","teria","teríamos","teriam"}; 
		String[] stopwordsUp = new String[stopwords.length];
		
		for (int x = 0; x < stopwords.length; x++)
		{
			tweetaux = tweet.replaceAll("\\b"+stopwords[x]+"\\b"," ");
			tweet = tweetaux;
		}
		
		String stringfromArray = "" + new String(tweetaux);
		return stringfromArray;
	}
	
	public String trocarAbreviacoes(String tweet){
		tweet = tweet.toLowerCase();
		String tweetaux = null;
		Map<String,String> abreviacoes = new HashMap<String,String>();
		abreviacoes.put("vc"," você ");
		abreviacoes.put("sdds","saudades");
		abreviacoes.put("sdd","saudade"); 
		abreviacoes.put("cê","você"); 
		abreviacoes.put("mds","meu deus"); 
		abreviacoes.put("vdd","verdade"); 
		abreviacoes.put("tva","estava"); 
		abreviacoes.put("tadin","coitado");
		abreviacoes.put("wpp","whatsapp");
		abreviacoes.put("hj","hoje");
		abreviacoes.put("tv","teve");
		abreviacoes.put(" n ","não");
		abreviacoes.put(" ñ ","não");
		abreviacoes.put("naum","não");
		abreviacoes.put(" q ","que");
		abreviacoes.put("eoq","que");
		abreviacoes.put("blz","beleza");
		abreviacoes.put("bl","beleza");
		abreviacoes.put("aki","aqui");
		abreviacoes.put("fmz","firmeza");
		abreviacoes.put("vlw","valeu");
		abreviacoes.put(" s ","sim");
		abreviacoes.put(" ss ","sim");
		for (Map.Entry<String, String> entry : abreviacoes.entrySet()) {
		    tweetaux = tweet.replaceAll(entry.getKey(),entry.getValue());
			tweet = tweetaux;
		}
		
		String stringfromArray = "" + new String(tweetaux);
		return stringfromArray;

	}
	
	public String trocarEmoticons(String tweet){
		tweet = tweet.toLowerCase();
		String tweetaux = null;
		Map<String,String> emoticons = new HashMap<String,String>();
		emoticons.put(":\\)"," feliz ");
		emoticons.put(" rs"," risos ");
		emoticons.put(" Rs "," risos ");  

		for (Map.Entry<String, String> entry : emoticons.entrySet()) {
		    tweetaux = tweet.replaceAll(entry.getKey(),entry.getValue());
			tweet = tweetaux;
		}
		String stringfromArray = "" + new String(tweetaux);
		return stringfromArray;
	}
	
	public String removerNaoAlfabetico(String tweet){
		tweet = tweet.toLowerCase();
		char[] tweetchar = tweet.toCharArray();
		int aux;

		char[] alfabeto = {' ','Á','á','Ã','ã','Â','â','À','à','Ç','ç','É','é','Ê','ê','Í','í','Ó','ó','Ô','ô','Õ','õ','Ú','ú','A','a','B','b','C','c','D','d','E','e','F','f','G','g','H','h', 'I','i','J','j','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r', 'S','s','T','t','U','u','V','v','X','x','W','w','Y','y','Z','z'};
		for (int x = 0; x < tweetchar.length; x++)
		{
			String letra = Character.toString(tweetchar[x]);
			if (!new String(alfabeto).contains(letra))
			{
				aux = x;
				tweetchar[x] = ' ';
				for (int v = x; v < tweetchar.length; v++)
				{
					tweetchar[aux] = tweetchar[v];
					aux++;
				}
			}
		}
		String stringfromArray = "" + new String(tweetchar);
		return stringfromArray;
	}
	
	
	public String removerTextoRepetido(String tweet){
		tweet = tweet.toLowerCase();
		char[] tweetchar = tweet.toCharArray();
		int x = 0,aux = 0;
		for (x = 0; x < tweetchar.length-2; x++)
		{
			if (tweetchar[x] == tweetchar[x+1] && tweetchar[x] == tweetchar[x+2] && x < tweetchar.length-1 && tweetchar[x] != ' ')
			{
				aux = x;
				char auxc = tweetchar[x];
				x++;
				while(tweetchar[x] == auxc && x < tweetchar.length-1)
				{
					tweetchar[x] = ' ';
					x++;
				}
				tweetchar[aux] = ' ';
				for (int v = x; v < tweetchar.length; v++)
				{
					tweetchar[aux] = tweetchar[v];
					aux++;
				}
			}
		}
		String stringfromArray = "" + new String(tweetchar);
		return stringfromArray;

	}

}