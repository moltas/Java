package mannWhitneyUTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

/**
 * Klass som läser textfil och kör ett Mann-whitney U test
 * implementerar Serializable så vi kan skriva till objekt
 * @author Tda
 *
 */

public class MannWhitney_Prov implements Serializable{
	
	private int[] grp1 = new int[15];
	private int[] grp2 = new int[15];
	
	private static int n1 = 15; //Antal deltagare i grupp 1.
	private static int n2 = 15; //Antal deltagare i grupp 2. 
	private int nx;
	private double tx; //larger rank total

	private int[] betyg;//array som håller alla element från grp1 och grp2
	
	//Map OriginalMap håller alla original ranker och AverageMap  håller värden med genomsnitts rank
	public Map<Integer, List<Double>> OriginalMap = new HashMap<Integer, List<Double>>();
	public Map<Integer, List<Double>> AverageMap = new HashMap<Integer, List<Double>>();
	

	/**
	 * Läser en fil från filNamn
	 * skriver ut på konsolen
	 * @param filNamn
	 */
	public void läsFil(String filNamn){	
		try{
			
			FileReader file = new FileReader(filNamn);		
			BufferedReader br = new BufferedReader(file);	

			//variabler för att hålla tillfällig data
			String line = "";
			String newString = "";
			String newString2 = "";
			int counter = 0;
			
			
			//Mitt undantag som kastar ArrayIndexOutOfBounds Exception
			//kastas när det är slut på plats i arrayet grp1
			if(grp1.length>15){
				throw new MittUndantag("Här var det trångt, skapa mer plats i arrayet");
			}
			//körs medans det finns rader att läsa			
			while((line = br.readLine()) != null){
				
				//räknare som håller koll på vilken rad man ska spara strängarna från
				if(counter<1){
					//sparar första raden, index 5-50, så vi får med alla siffror och inga bokstäver
					newString = line.substring(5, 50);					
				}
				
				counter++;	
				
				if(counter>=1){
					//sparar andra raden, index 5-50, så vi får med alla siffror och inga bokstäver
					newString2 = line.substring(5, 50);
				}
				//tar bort kommatecken från strängarna		
				String[] strArray = newString.split(",");
				String[] strArray2 = newString2.split(",");
				
				grp1 = new int[strArray.length];
				grp2 = new int[strArray2.length];
				
				for(int i=0; i<strArray.length; i++){
					try{
						//Parsar värden från array och matar array grp1
						//och grp2 array med värden
						grp1[i] = Integer.parseInt(strArray[i]);
						grp2[i] = Integer.parseInt(strArray2[i]);

					//fångar undantag ifall det inte är en Int	
					}catch(NumberFormatException e){
						System.out.println(e);
					}					
				}							
			}//end while
			
			br.close();
			
		}catch(IOException e){
			System.out.println("Det har skett ett problem. " + e);
		}
								
	}//end method läsFil
	
	/**
	 * Fyller hashmap med värden från ratings(vilket är grp1 + grp2)
	 */
    public void fyllMap() {
    	
    	//Kallar metoden combine som kombinerar grp1 array med grp2 array
    	combine();
    	
        for (int i = 0; i < betyg.length; i++) {

            //deklarerar listan entries
            List<Double> entries = OriginalMap.get(betyg[i]);

            if (entries == null) {
                entries = new ArrayList<Double>();
            }

            //Lägger till index + 1 till listan
            entries.add((double) (i + 1)); // 1-indexed position

            //lägger till allt i mappen OriginalMap
            OriginalMap.put(betyg[i], entries);

        }

    }//end method fyllMap
	
    /**
     * Metod som hanterar alla värden som är lika och lägger värdena i en ny map som heter AverageMap
     */
	public void handleTies(){
		
        System.out.println("\n\n");
        
        double average;
        
        //loopar igenom värdena i OriginalMap o skapar entry objekt "entry"
        for (Entry<Integer, List<Double>> entry : OriginalMap.entrySet()) {
            double sum = 0;
            int counter = 0;
            
            //Fyller listan value, med värden från map entry.
            List<Double> value = entry.getValue();
            
            int key = entry.getKey();
            
            List<Double> avg = entry.getValue();
            //Om det finns flera värden så loopas
            //dom och läggs ihop till summan
            for (double ent : value) {
                sum += ent;
                counter++;
            }
            //får fram average
            average = sum / counter;
            //lägger till average i listan
            value.add(average);
            
            for (int i = 0; i < value.size(); i++) {
                if (i == value.size() - 1) {
                    avg.remove(i);
                } else {
                    avg.set(i, average);
                }

            }
            //Fyller mappen med average
            for (int i = 0; i < value.size(); i++) {

            	AverageMap.put(key, avg);
            }
        }
        
    }//end method handleTies
	

	/**
	 * Metod som jämför T1 med T2
	 * @param output
	 * @param objOutput
	 */
    public void högstaRankTotal(PrintWriter output, ObjectOutputStream objOutput){
    	try{
    	
    	//Itererar AverageMap och fyller två nya maps med samma värden
        Iterator<Map.Entry<Integer, List<Double>>> arrayGrp1RankTotal = AverageMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, List<Double>>> arrayGrp2RankTotal = AverageMap.entrySet().iterator();
        
        //summerar ranks till rätt array
        double sumGrp1 = Summa( arrayGrp1RankTotal,grp1);
        double sumGrp2 = Summa( arrayGrp2RankTotal,grp2);

  

        //Jämför två doublevärden, i detta fall Ranksumman för grp1 och grp2
        int jämför = Double.compare(sumGrp1, sumGrp2);
        
       if( jämför > 0)
       {
           
           System.out.println("Det finns en skillnad.\n-----------------------\nGrupp 1 har högre betyg än grupp 2 ");
           System.out.println("Grupp 1s totala rank: " + sumGrp1 + "\n" + "Grupps 2s totala rank: " + sumGrp2);
           System.out.println("\n");
           tx = sumGrp1;
           nx = grp1.length;
           output.println("Grupp 1 > Grupp 2");
           output.println("Grupp1 = " + sumGrp1);
           output.println("Grupp2 = " + sumGrp2);
           output.println("");
         //serializerar till objekt
           objOutput.writeObject("Grupp 1 > Grupp 2");
           objOutput.writeObject("Grupp1 = " + sumGrp1);
           objOutput.writeObject("Grupp2 = " + sumGrp2);
           output.println("");
       }
       else if(jämför < 0)
       {
           
           System.out.println("Det finns en skillnad.\n-----------------------\nGrupp 2 har högre betyg än grupp 1 ");
           System.out.println("Grupp 2s totala rank: " + sumGrp2 + "\n" + "Grupp 1s totala rank: " + sumGrp1);
           System.out.println("\n");
           tx = sumGrp2;
           nx = grp2.length;
           output.println("Grupp 2 > Grupp 1");
           output.println("Grupp2 = " + sumGrp2);
           output.println("Grupp1 = " + sumGrp1);
           output.println("");
           //serializerar till objekt
           objOutput.writeObject("Grupp 2 > Grupp 1");
           objOutput.writeObject("Grupp2 = " + sumGrp2);
           objOutput.writeObject("Grupp1 = " + sumGrp1);
       }
       else 
       {
           
           System.out.println("Ingen skillnad.\nGrupp 1 och grupp 2 har lika hög totalrank");
           System.out.println("\n");
           tx = sumGrp2;
           output.println("Ingen skillnad mellan grupperna.");
           objOutput.writeObject("Ingen skillnad mellan grupperna.");


       }

    	}catch(IOException e){
    		System.out.println(e);
    	} 
      
    }//end method HögstaRankTotal
	
    /**
     * Metod som summerar map och array
     * @param i 
     * @param array
     * @return sum
     */
    private double Summa(Iterator<Map.Entry<Integer, List<Double>>> i,int[] array){ //parameter som itererar alla entries i map
    	
        double sum=0;
        	
          //while-loop som körs medan det finns värden kvar i map          
          while (i.hasNext()) {
        	  //skapar variablar utav värden från map
              Entry e = i.next();
              String key = e.getKey().toString();
              List<Double> value = (List<Double>) e.getValue();
              
       
              for(int j=0 ; j< 6 ; j++){
              	//Om array är lika som nyckel
                 if(array[j] == Integer.parseInt(key)) {
                	 //matar sum med sum+value
                     sum=sum+value.get(0);
                 }   
              }  
          }
          return sum;
      }//end method summa
    

    /**
     * Metod som lägger ihop arrayerna grp1 och grp2 till ratings arrayet
     */
	public void combine(){
		//Kopierar värden från grp1 och grp2 till betyg		
		betyg = new int[grp1.length + grp2.length];
		System.arraycopy(grp1, 0, betyg, 0,  grp2.length);
		System.arraycopy(grp2, 0, betyg, grp1.length, grp2.length);
		
		Arrays.sort(betyg);
	}//end method combine
    
	
	/**
	 * skriver ut grp1 arrayet till konsolen och fil
	 * @param output
	 * @param objOutput
	 */
	public void printGrp1(PrintWriter output, ObjectOutputStream objOutput){
		try{
			System.out.println("Grupp 1: " + Arrays.toString(grp1));
			output.println("Grupp 1: " + Arrays.toString(grp1));
			//Serializerar till objekt
			objOutput.writeObject("Grupp 1: " + Arrays.toString(grp1));
		}catch(IOException e){
			System.out.println(e);
		}
	}//end method printGrp1
	
	/**
	 * skriver ut grp2 arrayet till konsolen och fil
	 * @param output
	 * @param objOutput
	 */
	public void printGrp2(PrintWriter output, ObjectOutputStream objOutput){
		try{
			System.out.println("Grupp 2: " + Arrays.toString(grp2));
			output.println("Grupp 2: " + Arrays.toString(grp2));
			output.println("");	
			//Serializerar till objekt
			objOutput.writeObject("Grupp 2: " + Arrays.toString(grp2));
		}catch(IOException e){
			System.out.println(e);
		}
	}//end method printGrp2
    
	
	/**
	 * Metod som hittar U och skriver ut till konsolen, fil och objekt
	 * @param output
	 * @param objOutput
	 */
	public void getU(PrintWriter output, ObjectOutputStream objOutput){
		try{
			DecimalFormat df = new DecimalFormat("#.#");
			//formeln för U 
	        double findU = n1 * n2 + nx * (nx + 1)/2-tx;
					
	        System.out.println("U är: " + df.format(findU));
	        output.println("U är: " + findU);
	        output.println("");		
	        objOutput.writeObject("U är: " + findU);
		}catch(IOException e){
			System.out.println(e);
		}
	}//end method getU
	
	
	/**
	 * method som läser objektfiler
	 * @param filNamn
	 */
	public void läsObjFil(String filNamn){
		
		try{
			//skapar ObjectInputStream så vi kan läsa från objekt
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(filNamn));
			//skapar object som hämtar data från objektfilen
			Object Grp1Obj = (Object) is.readObject();
			Object Grp2Obj = (Object) is.readObject();
			Object grp1vsGrp2 = (Object) is.readObject();
			Object grp2Total = (Object) is.readObject();
			Object grp1Total = (Object) is.readObject();
			Object theU = (Object) is.readObject();
			Object CritVal = (Object) is.readObject();
			Object komm = (Object) is.readObject();
			//skriver till konsol/fil/objekt
			System.out.println("\n\n*Detta läses in från objektfil*\n" + "------------------------------------------------");
			System.out.println(Grp1Obj + "\n" + Grp2Obj + "\n");
			System.out.println(grp1vsGrp2 + "\n" + grp2Total + "\n" + grp1Total + "\n");
			System.out.println(theU);
			System.out.println(CritVal);
			System.out.println(komm);
			
		}catch(IOException e){
			System.out.println(e);
		//kastas ifall klassen inte hittas	
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}					
	}//end method läsObjFil
	
	/**
	 * Metod som låter användaren lägga till kommentar
	 * @param output
	 * @param objOutput
	 */
	public void kommentar(PrintWriter output, ObjectOutputStream objOutput){
		try{	
		//Gui som låter användaren mata in kommentar	
		String nyKommentar = JOptionPane.showInputDialog(null, "Skriv en kommentar om resultatet: ");
		

		
		output.println("");
		output.println("Kommentar: " + nyKommentar);
		output.println("");
		objOutput.writeObject("\nKommentar: " + nyKommentar);
		
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	
	public static void main(String [] args){
		
		MannWhitney_Prov mp = new MannWhitney_Prov();
		
		try{
			
			PrintWriter pw = new PrintWriter("/Users/Tda/desktop/ReadFiles/MWtestres.txt");
			
			File objectFile = new File("/Users/Tda/desktop/ReadFiles/MWtestres.obj");
			ObjectOutputStream objectWriter = new ObjectOutputStream(new FileOutputStream(objectFile));
			
			mp.läsFil("/Users/Tda/desktop/ReadFiles/tentares.txt");
			
			//skriver ut gruppernas betyg
			mp.printGrp1(pw, objectWriter);
			mp.printGrp2(pw, objectWriter);
				
			mp.fyllMap();
			
			//hanterar ties
		    mp.handleTies();
		    		    
		    //Hittar högsta rankTotal
		    mp.högstaRankTotal(pw, objectWriter);
		    		    
		    //fixar fram U
			mp.getU(pw, objectWriter); 
	    	
			//klass för att få fram signifikansvärde för n1 och n2
			GetSignificance_fivePercent GF = new GetSignificance_fivePercent(n1, n2, pw, objectWriter);
			System.out.println("");

			//om man vill skriva kommentar
			mp.kommentar(pw, objectWriter);
						
			//Läser objektfilen som skapades tidigare
			mp.läsObjFil("/Users/Tda/desktop/ReadFiles/MWtestres.obj");
					
		    //stänger ner printWriter
			pw.close();
			
		}catch(IOException e){
			System.out.println("Nåt knasigt har skett " + e);
		}
		
	}//end main
	
	
}//end class MannWhitney_Prov
