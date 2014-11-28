package flygbolaget;

//Program där du kan välja mellan förstaklass och ekonomiklass och därefter välja
//plats inom vald klass.

import java.util.Scanner;

/**
 * Klass som låter användaren välja mellan förstaklass och ekonomiklass, därefter välja
 * en plats inom valda klassen.
 * 
 */
public class Flygbolaget {
    private static Scanner scan =new Scanner(System.in); 
    
    
    /**
     * 
     * Huvudmetoden som programmet körs ifrån
     */
    public static void main(String[] args) {
        //array o integers
        boolean plats[][] =new boolean[2][16];
        visaPlatser(plats);
        
        int väljKlass=0;
        int full;
        
        //do-while loop som körs om inte väljKlass är 3, alltså avsluta.
        do{
           //visas om planet är fullt 
           if(planetÄrFullt(plats)==true){
             System.out.println("Tyvärr planet är fullt");
             System.out.println("Tack och välkommen åter");
             break;
           }  //meny för att välja klass eller avsluta
              System.out.println("\n1 - Förstaklass");
              System.out.println("2 - Ekonomiklass");
              System.out.println("3 - Avsluta");
              System.out.print("Välj vilken klass du vill åka med, eller avsluta: ");
              //skannar klass
              väljKlass=scan.nextInt();
              
              //switch statement som kollar status på klassen man valt
              switch(väljKlass){
                 case 1:
                     if(kollaFullFörstaKlass(plats)==true){
                        System.out.print("Ledsen, men förstaklass är fullt. Vill du se om det finns ledig plats i Ekonomiklassen? 1 - ja, 0 - nej: ");
                        full=scan.nextInt();
                        if(full==1){
                           if(kollaFullEkonomiKlass(plats)==true){
                             System.out.print("Ledsen, finns inga lediga platser i ekonomiklassen.");
                             break;
                           }else{
                             kollaEkonomiKlass(plats);
                           }
                        }else{
                          System.out.println("Tack och välkommen åter");
                          break;
                        }
                      }else{
                          kollaFörstaKlass(plats);
                      }
                   
                    break;
                 case 2:
                      if(kollaFullEkonomiKlass(plats)==true){
                        System.out.print("Ledsen, men ekonomiklass är fullt. Vill du se om det finns ledig plats i förstaklassen? 1 - ja, 0 - nej: ");
                        full=scan.nextInt();
                        if(full==1){
                            if(kollaFullFörstaKlass(plats)==true){
                             System.out.print("Ledsen, finns inga lediga platser i förstaklass.");
                             break;
                           }else{
                             kollaFörstaKlass(plats);
                           }
                           
                        }else{
                          System.out.println("Tack och välkommen åter");
                          break;
                        }
                      }else{
                        kollaEkonomiKlass(plats);
                      }
                    
                    break;
                 case 3:
                     break;
                   default:
                       System.out.println("Finns inget sådant alternativ, försök igen.");
                       main(args);
                     break;
              }
             visaPlatser(plats);
        }while(väljKlass!= 3);
        
    }
    /**
     * metod som kollar om förstaklass är full
     */ 
    private static boolean kollaFullFörstaKlass(boolean[][] platser) {
      boolean ärFull=false;
        int räknare=0;
        for(int i=0;i<2;i++){
          for(int j=0;j<3;j++){
            if (kollaPlats(platser,i,j)==false){
              räknare++;
            } 
          }
        }
        if(räknare==6){
          ärFull=true;
        }
        return ärFull;
    }
    /**
     * metod som kollar efter platser i förstaklass
     */ 
    private static void kollaFörstaKlass(boolean platser[][]){
        System.out.print("Välj plats: 0-2 eller 10-12: ");
        int väljPlats=0;
        väljPlats=scan.nextInt();
        switch(väljPlats){
             case 0: 
                 if (kollaPlats(platser,0,0)==true){
                   platser[0][0]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaFörstaKlass(platser);
                 }
                 break;
             case 1: 
                 if (kollaPlats(platser,0,1)==true){
                   platser[0][1]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaFörstaKlass(platser);
                 }
                 break;
             case 2: 
                 if (kollaPlats(platser,0,2)==true){
                   platser[0][2]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaFörstaKlass(platser);
                 }
                 break;
             case 10: 
                 if (kollaPlats(platser,1,0)==true){
                   platser[1][0]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaFörstaKlass(platser);
                 }
                 break;
             case 11: 
                 if (kollaPlats(platser,1,1)==true){
                   platser[1][1]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaFörstaKlass(platser);
                 }
                 break;
             case 12: 
                 if (kollaPlats(platser,1,2)==true){
                   platser[1][2]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaFörstaKlass(platser);
                 }
                 break;
             default:
                 System.out.println("Den platsen finns inte i vald klass.");
                 kollaFörstaKlass(platser);
                 break;
         }
    }
     /**
      * metod som kollar om ekonomiklassen är full
      */ 
     private static boolean kollaFullEkonomiKlass(boolean platser[][]){
        boolean ärFull=false;
        int räknare=0;
        for(int i=0;i<2;i++){
          for(int j=3;j<8;j++){
              
            if (kollaPlats(platser,i,j)==false){
              räknare++;
            } 
          }
        }
        if(räknare==10){
          ärFull=true;
        }
        return ärFull;
     }
     /**
      * metod som kollar efter platser i ekonomiklass.
      */
     private static void kollaEkonomiKlass(boolean platser[][]){
        System.out.print("Välj plats: 3-7 eller 13-17: ");
        int väljPlats=0;
        väljPlats=scan.nextInt();
        //Switch statement för alla platser i ekonomiklassen, kollar valda platser o ser om den är upptagen.
        switch(väljPlats){
             case 3: 
                 if (kollaPlats(platser,0,3)==true){
                   platser[0][3]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 4: 
                 if (kollaPlats(platser,0,4)==true){
                   platser[0][4]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 5: 
                 if (kollaPlats(platser,0,5)==true){
                   platser[0][5]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 6: 
                 if (kollaPlats(platser,0,6)==true){
                   platser[0][6]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 7: 
                 if (kollaPlats(platser,0,7)==true){
                   platser[0][7]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 13: 
                 if (kollaPlats(platser,1,3)==true){
                   platser[1][3]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 14: 
                 if (kollaPlats(platser,1,4)==true){
                   platser[1][4]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 15: 
                 if (kollaPlats(platser,1,5)==true){
                   platser[1][5]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 16: 
                 if (kollaPlats(platser,1,6)==true){
                   platser[1][6]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             case 17: 
                 if (kollaPlats(platser,1,7)==true){
                   platser[1][7]=true;
                 }else{
                   System.out.println("Denna plats är upptagen.");
                   kollaEkonomiKlass(platser);
                 }
                 break;
             default:
                 System.out.println("Den platsen finns inte i vald klass.");
                 kollaEkonomiKlass(platser);
                 break;
         }
    }    
    /**
     * metod som kollar om planet är fullt
     */ 
    private static boolean planetÄrFullt(boolean platser[][]){
       boolean ärFull=false;
       int räknare=0;
       for(int i=0;i<2;i++){
         for(int j=0;j<8;j++){
            //räknar upp varje gång en plats blir upptagen, när alla platser är upptagna ändras ärFull till true. 
            if(platser[i][j]==true){
              räknare++;
            }
         }
       }
       if(räknare==16){
         ärFull=true;
       }
       return ärFull;
    }
    /**
     * metod som kollar om platsen är ledig
     */ 
    private static boolean kollaPlats(boolean platser[][],int rad, int kol){
       //om rad o kolumn är falsk så returneras true, annars false.
        if (platser[rad][kol]==false){
         return true;
       }else{
         return false;
       }
    }
    /**
     * metod som visar alla platser i planet
     */ 
    private static void visaPlatser(boolean platser[][]){
      //loopar och visar alla platserna på prompt  
      for(int i=0;i<2;i++){
          if(i==0){
             System.out.print("Förstaklass\t  Ekonomiklass\n");
             for(int j=0;j<8;j++){
               System.out.print("  0"+j+"  ");
             }
          }else{
             for(int j=10;j<18;j++){
               System.out.print("  "+j+"  ");
             }
          }
          System.out.print("\n");
          //loop som skriver ut ledig eller tagen beroende på platsens status.
          for(int j=0;j<8;j++){
             if (platser[i][j]==false){
               System.out.print("Ledig ");
             }else{
               System.out.print("Tagen "); 
             }
          }
          System.out.print("\n");
        }
    }
}
