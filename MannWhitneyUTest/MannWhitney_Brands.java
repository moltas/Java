package mannWhitneyUTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

/**
 * Klass för att testa ett MannWhitney U test
 * @author Tda
 *
 */
public class MannWhitney_Brands {
	
	
	private int[] arrayX = new int[20];
	private int[] arrayY = new int[6];
	private int maxArray = 6;
	private Random rand = new Random();
	
	private static int n1 = 6; //Antal deltagare i grupp 1.
	private static int n2 = 6; //Antal deltagare i grupp 2. 
	private int nx;
	private double tx; //larger rank total
	
		
	private int[] ratings;//array som håller alla element från arrayX och arrayY

	
	//Map OriginalMap håller alla original ranker och AverageMap  håller värden med genomsnitts rank
	public Map<Integer, List<Double>> OriginalMap = new HashMap<Integer, List<Double>>();
	public Map<Integer, List<Double>> AverageMap = new HashMap<Integer, List<Double>>();
	
	
	//Metod som fyller arrayerna med random nummer.
	public void getRanks(){
		
		for(int i=0; i<maxArray; i++){
			
			arrayX[i] = rand.nextInt(10)+1;
			arrayY[i] = rand.nextInt(10)+1;
		}
		
		
	}
	
	//Metod som gör att man manuellt får mata in valfria nummer.
	public void getRanksManually(){
		
		Scanner scan = new Scanner(System.in);
		
		
		
		System.out.println("Enter six X ranks: ");
		for(int i=0; i<maxArray; i++){
			
			arrayX[i] = scan.nextInt();			
		}
		System.out.println("Enter six Y ranks: ");
		for(int i=0; i<maxArray; i++){
			
			arrayY[i] = scan.nextInt();			
		}
		
		scan.close();
	}
	
	
	
	//Fyller hashmap med värden från ratings(vilket är arrayX + arrayY)
    public void fyllMap() {
    	
    	//Kallar metoden combine som kombinerar grp1 array med grp2 array
    	combine();
    	
        for (int i = 0; i < ratings.length; i++) {

            //deklarerar listan entries
            List<Double> entries = OriginalMap.get(ratings[i]);

            if (entries == null) {
                entries = new ArrayList<Double>();
            }

            //Lägger till index + 1 till listan
            entries.add((double) (i + 1)); // 1-indexed position

            //lägger till allt i mappen OriginalMap
            OriginalMap.put(ratings[i], entries);

        }

    }//end method fyllMap
	
    //metod som hanterar alla värden som är lika och lägger värdena i en ny map som heter AverageMap
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

			

	
	//Metod som jämför T1 med T2
    public void högstaRankTotal(){
    	
        Iterator<Map.Entry<Integer, List<Double>>> i = AverageMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, List<Double>>> k = AverageMap.entrySet().iterator();
        
        
        double sumX = Summa( i,arrayX);
        double sumY = Summa( k,arrayY);
        System.out.println("\n");
        
        System.out.println("Jämför totala ranken hos T1 med T2 -");
        
        int retval = Double.compare(sumX, sumY);
       if( retval > 0)
       {
           
           System.out.println("T1 är större än T2 - ");
           System.out.println("T1: " + sumX + " " + "T2: " + sumY);
           System.out.println("\n");
           tx = sumX;
           nx = arrayX.length;
       }
       else if(retval < 0)
       {
           
           System.out.println("T2 är större än T1 - ");
           System.out.println("T2: " + sumY + " " + "T1: " + sumX);
           System.out.println("\n");
           tx = sumY;
           nx = arrayY.length;
       }
       else 
       {
           
           System.out.println("T1 och T2 är lika stora");
           System.out.println("\n");
           tx = sumY;
       }
       
        
    }
	
	
    private double Summa(Iterator<Map.Entry<Integer, List<Double>>> i,int[] array){//parameter som itererar alla entries i map
    	
        double val=0;
        	
          //while-loop som körs medan det finns värden kvar i map          
          while (i.hasNext()) {
        	  
              Entry e = i.next();
              String key = e.getKey().toString();
              List<Double> value = (List<Double>) e.getValue();
              
       
              for(int j=0 ; j< 6 ; j++){
              	
                 if(array[j] == Integer.parseInt(key)) {
                     
                     val=val+value.get(0);
                 }   
              }  
          }
          return val;
      }
	
	
	
    
	public void getU(){
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		double findU = n1 * n2 + nx * (nx + 1)/2-tx;
				
		System.out.println("U is: " + df.format(findU));
	}
	
	
	
	//Metod som lägger ihop arrayerna arrayX och arrayY
	public void combine(){
		//Kopierar värden från arrayX och arrayY till ratings	
		ratings = new int[arrayX.length + arrayY.length];
		System.arraycopy(arrayX, 0, ratings, 0,  arrayX.length);
		System.arraycopy(arrayY, 0, ratings, arrayX.length, arrayY.length);
		
		Arrays.sort(ratings);

	}
	
	
	
	//skriver ut ArrayX till konsolen
	public void printArrayX(){
		for(int i=0; i<maxArray; i++){
			System.out.println("Rank X: " + "[" + arrayX[i] + "]\n");
		}
		
	}
	//skriver ut ArrayY till konsolen
	public void printArrayY(){
		for(int i=0; i<maxArray; i++){
			System.out.println("Rank Y: " + "[" + arrayY[i] + "]\n");
		}
		
	}
	

	
	
	
	public static void main(String [] args){
		
		MannWhitney_Brands t = new MannWhitney_Brands();
		
		
		//Först en metod för att fylla arrayerna
		t.getRanks();
//		t.getRanksManually()
		
		
		
		//skriver ut arrayerna o deras värden i konsolen
		t.printArrayX();
		t.printArrayY();
		
		//fyller map med värden från båda arrayerna
		t.fyllMap();
//		
//		//hanterar ties
	    t.handleTies();
//	    
//	   //kollar vilket brand som har högst total rank 
	   t.högstaRankTotal();
//	   
//	   //fixar fram U åt oss
	   t.getU(); 
	   
	   
	}//end main
	
}
