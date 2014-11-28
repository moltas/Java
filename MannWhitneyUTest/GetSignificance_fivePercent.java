package mannWhitneyUTest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

//Denna klass lägger in alla värden som fanns i tabellen för 5% significance level
//i ett 2d array, kanske inte bästa sättet, men visste att det skulle funka så orkade inte krångla

/**
 * Lägger in alla värden från 5% signifikanstabell i ett 2d array.
 * @author Tda
 *
 */

public class GetSignificance_fivePercent {
	
	/**
	 * Konstruktor
	 * @param n1
	 * @param n2
	 * @param output
	 * @param objOutput
	 */
	public GetSignificance_fivePercent(int n1, int n2, PrintWriter output, ObjectOutputStream objOutput){
		
		
		
		int[][] megaArray = new int[100][100];
		
		megaArray[2][8] = 0;
		megaArray[2][9] = 0;
		megaArray[2][10] = 0;
		megaArray[2][11] = 0;
		megaArray[2][12] = 1;
		megaArray[2][13] = 1;
		megaArray[2][14] = 1;
		megaArray[2][15] = 1;
		megaArray[2][16] = 1;
		megaArray[2][17] = 2;
		megaArray[2][18] = 2;
		megaArray[2][19] = 2;
		megaArray[2][20] = 2;
		
		megaArray[3][5] = 0;
		megaArray[3][6] = 1;
		megaArray[3][7] = 1;
		megaArray[3][8] = 2;
		megaArray[3][9] = 2;
		megaArray[3][10] = 3;
		megaArray[3][11] = 3;
		megaArray[3][12] = 4;
		megaArray[3][13] = 4;
		megaArray[3][14] = 5;
		megaArray[3][15] = 5;
		megaArray[3][16] = 6;
		megaArray[3][17] = 6;
		megaArray[3][18] = 7;
		megaArray[3][19] = 7;
		megaArray[3][20] = 8;
		
		megaArray[4][5] = 0;
		megaArray[4][6] = 1;
		megaArray[4][7] = 2;
		megaArray[4][8] = 3;
		megaArray[4][9] = 4;
		megaArray[4][10] = 4;
		megaArray[4][11] = 5;
		megaArray[4][12] = 6;
		megaArray[4][13] = 7;
		megaArray[4][14] = 9;
		megaArray[4][15] = 10;
		megaArray[4][16] = 11;
		megaArray[4][17] = 11;
		megaArray[4][18] = 12;
		megaArray[4][19] = 13;
		megaArray[4][20] = 14;
		
		megaArray[5][5] = 2;
		megaArray[5][6] = 3;
		megaArray[5][7] = 5;
		megaArray[5][8] = 6;
		megaArray[5][9] = 7;
		megaArray[5][10] = 8;
		megaArray[5][11] = 9;
		megaArray[5][12] = 11;
		megaArray[5][13] = 12;
		megaArray[5][14] = 13;
		megaArray[5][15] = 14;
		megaArray[5][16] = 15;
		megaArray[5][17] = 17;
		megaArray[5][18] = 18;
		megaArray[5][19] = 19;
		megaArray[5][20] = 20;
		
		megaArray[6][6] = 5;
		megaArray[6][7] = 6;
		megaArray[6][8] = 8;
		megaArray[6][9] = 10;
		megaArray[6][10] = 11;
		megaArray[6][11] = 13;
		megaArray[6][12] = 14;
		megaArray[6][13] = 16;
		megaArray[6][14] = 17;
		megaArray[6][15] = 19;
		megaArray[6][16] = 21;
		megaArray[6][17] = 22;
		megaArray[6][18] = 24;
		megaArray[6][19] = 25;
		megaArray[6][20] = 27;
		
		megaArray[7][7] = 8;
		megaArray[7][8] = 10;
		megaArray[7][9] = 12;
		megaArray[7][10] = 14;
		megaArray[7][11] = 16;
		megaArray[7][12] = 18;
		megaArray[7][13] = 20;
		megaArray[7][14] = 22;
		megaArray[7][15] = 24;
		megaArray[7][16] = 26;
		megaArray[7][17] = 28;
		megaArray[7][18] = 30;
		megaArray[7][19] = 32;
		megaArray[7][20] = 34;
		
		megaArray[8][8] = 13;
		megaArray[8][9] = 15;
		megaArray[8][10] = 17;
		megaArray[8][11] = 19;
		megaArray[8][12] = 22;
		megaArray[8][13] = 24;
		megaArray[8][14] = 26;
		megaArray[8][15] = 29;
		megaArray[8][16] = 31;
		megaArray[8][17] = 34;
		megaArray[8][18] = 36;
		megaArray[8][19] = 38;
		megaArray[8][20] = 41;
		
		megaArray[9][9] = 17;
		megaArray[9][10] = 20;
		megaArray[9][11] = 23;
		megaArray[9][12] = 26;
		megaArray[9][13] = 28;
		megaArray[9][14] = 31;
		megaArray[9][15] = 34;
		megaArray[9][16] = 37;
		megaArray[9][17] = 39;
		megaArray[9][18] = 42;
		megaArray[9][19] = 45;
		megaArray[9][20] = 48;
		
		megaArray[10][10] = 23;
		megaArray[10][11] = 26;
		megaArray[10][12] = 29;
		megaArray[10][13] = 33;
		megaArray[10][14] = 36;
		megaArray[10][15] = 39;
		megaArray[10][16] = 42;
		megaArray[10][17] = 45;
		megaArray[10][18] = 48;
		megaArray[10][19] = 52;
		megaArray[10][20] = 55;
		
		megaArray[11][11] = 30;
		megaArray[11][12] = 33;
		megaArray[11][13] = 37;
		megaArray[11][14] = 40;
		megaArray[11][15] = 44;
		megaArray[11][16] = 47;
		megaArray[11][17] = 51;
		megaArray[11][18] = 55;
		megaArray[11][19] = 58;
		megaArray[11][20] = 62;
		
		megaArray[12][12] = 37;
		megaArray[12][13] = 41;
		megaArray[12][14] = 45;
		megaArray[12][15] = 49;
		megaArray[12][16] = 53;
		megaArray[12][17] = 57;
		megaArray[12][18] = 61;
		megaArray[12][19] = 65;
		megaArray[12][20] = 69;
		
		megaArray[13][13] = 45;
		megaArray[13][14] = 50;
		megaArray[13][15] = 54;
		megaArray[13][16] = 59;
		megaArray[13][17] = 63;
		megaArray[13][18] = 67;
		megaArray[13][19] = 72;
		megaArray[13][20] = 76;
		
		megaArray[14][14] = 55;
		megaArray[14][15] = 59;
		megaArray[14][16] = 64;
		megaArray[14][17] = 69;
		megaArray[14][18] = 74;
		megaArray[14][19] = 78;
		megaArray[14][20] = 83;
		
		megaArray[15][15] = 64;
		megaArray[15][16] = 70;
		megaArray[15][17] = 75;
		megaArray[15][18] = 80;
		megaArray[15][19] = 85;
		megaArray[15][20] = 90;
		
		megaArray[16][16] = 75;
		megaArray[16][17] = 81;
		megaArray[16][18] = 86;
		megaArray[16][19] = 92;
		megaArray[16][20] = 98;
		
		megaArray[17][17] = 87;
		megaArray[17][18] = 93;
		megaArray[17][19] = 99;
		megaArray[17][20] = 105;
		
		megaArray[18][18] = 99;
		megaArray[18][19] = 106;
		megaArray[18][20] = 112;
		
		megaArray[19][19] = 113;
		megaArray[19][20] = 119;
		
		megaArray[20][20] = 127;
		
		System.out.println("\nDet kritiska U värdet är " + megaArray[n1][n2] + " för ett två-sidigt test på 5% signifikansnivån.");
		output.println("\nDet kritiska U värdet är " + megaArray[n1][n2] + " för ett två-sidigt test på 5% signifikansnivån.");
		
		try{
		objOutput.writeObject("\nDet kritiska U värdet är " + megaArray[n1][n2] + " för ett två-sidigt test på 5% signifikansnivån.");
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	
	
	
}
