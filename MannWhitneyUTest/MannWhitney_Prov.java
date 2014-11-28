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
 * Klass som l�ser textfil och k�r ett Mann-whitney U test
 * implementerar Serializable s� vi kan skriva till objekt
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

	private int[] betyg;//array som h�ller alla element fr�n grp1 och grp2
	
	//Map OriginalMap h�ller alla original ranker och AverageMap  h�ller v�rden med genomsnitts rank
	public Map<Integer, List<Double>> OriginalMap = new HashMap<Integer, List<Double>>();
	public Map<Integer, List<Double>> AverageMap = new HashMap<Integer, List<Double>>();
	

	/**
	 * L�ser en fil fr�n filNamn
	 * skriver ut p� konsolen
	 * @param filNamn
	 */
	public void l�sFil(String filNamn){	
		try{
			
			FileReader file = new FileReader(filNamn);		
			BufferedReader br = new BufferedReader(file);	

			//variabler f�r att h�lla tillf�llig data
			String line = "";
			String newString = "";
			String newString2 = "";
			int counter = 0;
			
			
			//Mitt undantag som kastar ArrayIndexOutOfBounds Exception
			//kastas n�r det �r slut p� plats i arrayet grp1
			if(grp1.length>15){
				throw new MittUndantag("H�r var det tr�ngt, skapa mer plats i arrayet");
			}
			//k�rs medans det finns rader att l�sa			
			while((line = br.readLine()) != null){
				
				//r�knare som h�ller koll p� vilken rad man ska spara str�ngarna fr�n
				if(counter<1){
					//sparar f�rsta raden, index 5-50, s� vi f�r med alla siffror och inga bokst�ver
					newString = line.substring(5, 50);					
				}
				
				counter++;	
				
				if(counter>=1){
					//sparar andra raden, index 5-50, s� vi f�r med alla siffror och inga bokst�ver
					newString2 = line.substring(5, 50);
				}
				//tar bort kommatecken fr�n str�ngarna		
				String[] strArray = newString.split(",");
				String[] strArray2 = newString2.split(",");
				
				grp1 = new int[strArray.length];
				grp2 = new int[strArray2.length];
				
				for(int i=0; i<strArray.length; i++){
					try{
						//Parsar v�rden fr�n array och matar array grp1
						//och grp2 array med v�rden
						grp1[i] = Integer.parseInt(strArray[i]);
						grp2[i] = Integer.parseInt(strArray2[i]);

					//f�ngar undantag ifall det inte �r en Int	
					}catch(NumberFormatException e){
						System.out.println(e);
					}					
				}							
			}//end while
			
			br.close();
			
		}catch(IOException e){
			System.out.println("Det har skett ett problem. " + e);
		}
								
	}//end method l�sFil
	
	/**
	 * Fyller hashmap med v�rden fr�n ratings(vilket �r grp1 + grp2)
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

            //L�gger till index + 1 till listan
            entries.add((double) (i + 1)); // 1-indexed position

            //l�gger till allt i mappen OriginalMap
            OriginalMap.put(betyg[i], entries);

        }

    }//end method fyllMap
	
    /**
     * Metod som hanterar alla v�rden som �r lika och l�gger v�rdena i en ny map som heter AverageMap
     */
	public void handleTies(){
		
        System.out.println("\n\n");
        
        double average;
        
        //loopar igenom v�rdena i OriginalMap o skapar entry objekt "entry"
        for (Entry<Integer, List<Double>> entry : OriginalMap.entrySet()) {
            double sum = 0;
            int counter = 0;
            
            //Fyller listan value, med v�rden fr�n map entry.
            List<Double> value = entry.getValue();
            
            int key = entry.getKey();
            
            List<Double> avg = entry.getValue();
            //Om det finns flera v�rden s� loopas
            //dom och l�ggs ihop till summan
            for (double ent : value) {
                sum += ent;
                counter++;
            }
            //f�r fram average
            average = sum / counter;
            //l�gger till average i listan
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
	 * Metod som j�mf�r T1 med T2
	 * @param output
	 * @param objOutput
	 */
    public void h�gstaRankTotal(PrintWriter output, ObjectOutputStream objOutput){
    	try{
    	
    	//Itererar AverageMap och fyller tv� nya maps med samma v�rden
        Iterator<Map.Entry<Integer, List<Double>>> arrayGrp1RankTotal = AverageMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, List<Double>>> arrayGrp2RankTotal = AverageMap.entrySet().iterator();
        
        //summerar ranks till r�tt array
        double sumGrp1 = Summa( arrayGrp1RankTotal,grp1);
        double sumGrp2 = Summa( arrayGrp2RankTotal,grp2);

  

        //J�mf�r tv� doublev�rden, i detta fall Ranksumman f�r grp1 och grp2
        int j�mf�r = Double.compare(sumGrp1, sumGrp2);
        
       if( j�mf�r > 0)
       {
           
           System.out.println("Det finns en skillnad.\n-----------------------\nGrupp 1 har h�gre betyg �n grupp 2 ");
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
       else if(j�mf�r < 0)
       {
           
           System.out.println("Det finns en skillnad.\n-----------------------\nGrupp 2 har h�gre betyg �n grupp 1 ");
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
           
           System.out.println("Ingen skillnad.\nGrupp 1 och grupp 2 har lika h�g totalrank");
           System.out.println("\n");
           tx = sumGrp2;
           output.println("Ingen skillnad mellan grupperna.");
           objOutput.writeObject("Ingen skillnad mellan grupperna.");


       }

    	}catch(IOException e){
    		System.out.println(e);
    	} 
      
    }//end method H�gstaRankTotal
	
    /**
     * Metod som summerar map och array
     * @param i 
     * @param array
     * @return sum
     */
    private double Summa(Iterator<Map.Entry<Integer, List<Double>>> i,int[] array){ //parameter som itererar alla entries i map
    	
        double sum=0;
        	
          //while-loop som k�rs medan det finns v�rden kvar i map          
          while (i.hasNext()) {
        	  //skapar variablar utav v�rden fr�n map
              Entry e = i.next();
              String key = e.getKey().toString();
              List<Double> value = (List<Double>) e.getValue();
              
       
              for(int j=0 ; j< 6 ; j++){
              	//Om array �r lika som nyckel
                 if(array[j] == Integer.parseInt(key)) {
                	 //matar sum med sum+value
                     sum=sum+value.get(0);
                 }   
              }  
          }
          return sum;
      }//end method summa
    

    /**
     * Metod som l�gger ihop arrayerna grp1 och grp2 till ratings arrayet
     */
	public void combine(){
		//Kopierar v�rden fr�n grp1 och grp2 till betyg		
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
			//formeln f�r U 
	        double findU = n1 * n2 + nx * (nx + 1)/2-tx;
					
	        System.out.println("U �r: " + df.format(findU));
	        output.println("U �r: " + findU);
	        output.println("");		
	        objOutput.writeObject("U �r: " + findU);
		}catch(IOException e){
			System.out.println(e);
		}
	}//end method getU
	
	
	/**
	 * method som l�ser objektfiler
	 * @param filNamn
	 */
	public void l�sObjFil(String filNamn){
		
		try{
			//skapar ObjectInputStream s� vi kan l�sa fr�n objekt
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(filNamn));
			//skapar object som h�mtar data fr�n objektfilen
			Object Grp1Obj = (Object) is.readObject();
			Object Grp2Obj = (Object) is.readObject();
			Object grp1vsGrp2 = (Object) is.readObject();
			Object grp2Total = (Object) is.readObject();
			Object grp1Total = (Object) is.readObject();
			Object theU = (Object) is.readObject();
			Object CritVal = (Object) is.readObject();
			Object komm = (Object) is.readObject();
			//skriver till konsol/fil/objekt
			System.out.println("\n\n*Detta l�ses in fr�n objektfil*\n" + "------------------------------------------------");
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
	}//end method l�sObjFil
	
	/**
	 * Metod som l�ter anv�ndaren l�gga till kommentar
	 * @param output
	 * @param objOutput
	 */
	public void kommentar(PrintWriter output, ObjectOutputStream objOutput){
		try{	
		//Gui som l�ter anv�ndaren mata in kommentar	
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
			
			mp.l�sFil("/Users/Tda/desktop/ReadFiles/tentares.txt");
			
			//skriver ut gruppernas betyg
			mp.printGrp1(pw, objectWriter);
			mp.printGrp2(pw, objectWriter);
				
			mp.fyllMap();
			
			//hanterar ties
		    mp.handleTies();
		    		    
		    //Hittar h�gsta rankTotal
		    mp.h�gstaRankTotal(pw, objectWriter);
		    		    
		    //fixar fram U
			mp.getU(pw, objectWriter); 
	    	
			//klass f�r att f� fram signifikansv�rde f�r n1 och n2
			GetSignificance_fivePercent GF = new GetSignificance_fivePercent(n1, n2, pw, objectWriter);
			System.out.println("");

			//om man vill skriva kommentar
			mp.kommentar(pw, objectWriter);
						
			//L�ser objektfilen som skapades tidigare
			mp.l�sObjFil("/Users/Tda/desktop/ReadFiles/MWtestres.obj");
					
		    //st�nger ner printWriter
			pw.close();
			
		}catch(IOException e){
			System.out.println("N�t knasigt har skett " + e);
		}
		
	}//end main
	
	
}//end class MannWhitney_Prov
