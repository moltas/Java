package NummerAnalysen;


import java.util.Scanner;

//Denna klass läser in nummer från användaren och analyserar talen som blivit inmatade.

/**
 * 
 * Denna klass läser in nummer från användaren och analyserar talen som blivit inmatade.
 */
public class NummerAnalysen {
    /**
     * 
     * Huvudmetoden som klassen körs ifrån 
     */
    public static void main(String [] args){
        
        int[] dinaTal;
        int antal;
        int summa = 0;
        int minstaTal = 100;
        int storstaTal = 0;
        
        Scanner scan = new Scanner(System.in); //skapar nytt Scannerobjekt
        
        System.out.println("Hur många tal vill du mata in?");     
        antal = scan.nextInt(); //sparar användarens input i variabeln antal
        
        dinaTal = new int[antal]; //Initierar arrayet dinaTal med index utifrån användares input.
        
        System.out.println("Mata in dina " + antal + " tal: ");
        
        for(int i=0; i<dinaTal.length; i++){   
            dinaTal[i] = scan.nextInt();//Det är här användaren matar in talen
            //if-sats för att få fram största talet
            if(dinaTal[i]>storstaTal){
                storstaTal = dinaTal[i];
            }
            //if-sats för att få fram minsta talet
            if(dinaTal[i]<minstaTal){
                minstaTal = dinaTal[i];
            }          
        }
        
        System.out.println("Talen du skrev in var följande: ");
        
        //denna loop skriver ut användarens tal.
        for(int i=0; i<dinaTal.length; i++){
               
            System.out.println(dinaTal[i] + " ");
            summa = summa + dinaTal[i]; //lägger ihop alla inmatade tal till en summa.
        }
        
        //prompt
        System.out.println("Antal tal:" + antal + "\nminsta talet:" + minstaTal + "\nstörsta talet:" + storstaTal);
    }
    
}
