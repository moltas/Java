package Ordvändaren;

//Klass som vänder ord som användaren matar in.

import java.util.*;

/**
 * 
 * Klass som vänder på inmatade ord
 */
public class Ordvändaren {
    
    /**
     * 
     * Huvudmetoden som klassen körs ifrån
     */
    public static void main(String[] args){
       
        String input;
        String output = "";
        Stack<String> stacken = new Stack<String>();
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Skriv ett ord som du vill vända: ");
        input = scan.nextLine();
        
        for(int i=0; i<input.length();i++){
            //tar alla bokstäverna från strängen och matar dom till stacken
            stacken.push(input.substring(i,i+1));
        }
               
        //Här så poppar vi element från stacken, vilket gör att ordet blir omvänt.
        while(!stacken.isEmpty()){
            output += stacken.pop();
        }
        //prompt
        System.out.println("Om man vänder ordet: " 
                + input 
                + "\n" 
                + "Blir det: " 
                + output);
        
    }//slut på main metod
           
}//slut på klass Ordvändaren
