import java.io.IOException;
import java.util.Scanner;

public class TunChe {
    private static int[][] map;		// Current Map
    private static int D;			// Map dimension
    private static boolean end;
    private static final boolean playFirst = true;

/* Notes: Find i,j and X/O to minimize (totalXMove[i][j] + totalOMove[j][j]) and totalXMove[i][j] + totalOMove[i][j] % 2 == type */


    public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		System.out.print("N = ");
		int N = in.nextInt();
		D = N + 4;
		map = new int[D][D];

		for (int i = 0; i < D; i++){
			for (int j = 0; j < D; j++){
				if (i < 2 || j < 2 || i > N +1 || j > N + 1) map[i][j] = BOUNDARY;
			else map[i][j] = EMPTY;
			}
		}

		
		printMap();

		end = false;
		int total = 0;

		
		while (!end){
			// findNextMove();
			if (playFirst) {
				System.out.println("Turn: " + total);
				findBestSolution(total);
			}

			printMap();
			// if (end) break;
			System.out.println("*********************[Human turn]*********************");   
			int x = in.nextInt();
			int y = in.nextInt();
			int type = in.nextInt();
			map[y+1][x+1]=type;	    
			total++;
			if (!playFirst) {
				System.out.println("Turn: " + total);
				findBestSolution(total);
			}
		}

    }

    public static void printMap(){
		for (int i = 0; i< D; i++){
			for (int  j = 0; j < D; j++){
			switch (map[i][j]){
				case EMPTY : System.out.print(" -"); break;
				case X : System.out.print(" X"); break;
				case O: System.out.print(" O"); break;
				default: System.out.print("");
			}
			}
			System.out.println();
		}
    }

	public static void findBestSolution(int total){
		int result;
		for (int i = 2; i < D - 2; i++) {
			for (int j = 2; j < D - 2; j++) {
			if (map[i][j] != EMPTY) continue;
			if (checkMove(map,i,j,X)) {
				map[i][j] = X;
				result = findSolution(total+1);
				map[i][j] = EMPTY;
				if (result % 2 == (playFirst?1:0)) {
				System.out.println("Solution: " + (j - 1) + " - " + (i - 1) + " - X - " + (total+1));
				return;
				}
			}
			if (checkMove(map,i,j,O)) {
				map[i][j] = O;
				result = findSolution(total+1);
				map[i][j] = EMPTY;
				if (result % 2 == (playFirst?1:0)) {
				System.out.println("Solution: " + (j - 1) + " - " + (i - 1) + " - O - " + (total+1));
				return;
				}
			}
			}
		}
		System.out.println("No move");
	}

	public static int findSolution(int total){
		int step = total;
		int result;
		for (int i = 2; i < D - 2; i++) {
			for (int j = 2; j < D - 2; j++) {
			if (map[i][j] != EMPTY) continue;
			if (checkMove(map,i,j,X)) {
				map[i][j] = X;
				step++;
				result = findSolution(step);
				//if (result % 2 == 1 ) {
				System.out.println(j - 1 + " - " + (i - 1) + " - X - " + step);
				map[i][j] = EMPTY;
				return result;
				//}
				//step--;
				//map[i][j] = 0;
			}
			if (checkMove(map,i,j,O)) {
				map[i][j] = O;
				step++;
				result = findSolution(step);
				//if (result % 2 == 1) {
				System.out.println(j - 1 + " - " + (i - 1) + " - O - " + step);
				map[i][j] = EMPTY;
				return result;
				//}
				//step--;
				//map[i][j] = 0;
			}
			}
		}
		System.out.println("Total: " + total);
		return step;
	}





























































    public static void findNextMove(){
		int moveX, moveO;
		int nextMove = 0;
		int type = 0;
		// int minMove = 1000;
		int minMove = -1;
		int xPos = 0;
		int yPos = 0;
		int minType = 0;
		// int resultMinMove = 1000;
		int resultMinMove = -1;
		int resultXPos = 0;
		int resultYPos = 0;
		int resultType = 0;

		for (int i = 2; i < D - 2; i++) {
			for (int j = 2; j < D - 2; j++) {
				if (map[i][j] != EMPTY) continue;
				moveX = generateMove(i, j, X);
				moveO = generateMove(i, j, O);

				if (moveX > moveO){
					nextMove = moveX;
					type = 1;
				}
				else {
					nextMove = moveO;
					type = -1;
				}
				/*
				if (moveX == type) { 
					nextMove = moveO;
					type = -1;
				}
				else if (moveO == type) {
					nextMove = moveX;
					type = 1;
				}
				else if (moveX < moveO) {
					nextMove = moveX;
					type = 1;
				}
				else {
					nextMove = moveO;
					type = -1;
				}
				*/
				// if (nextMove < minMove && nextMove > 0) {
				if (nextMove > minMove) {
					minMove = nextMove;
					xPos = i - 2;
					yPos = j - 2;
					minType = type;
					if (minMove % 2 == 0) {
					resultMinMove = minMove;
					resultXPos = xPos;
					resultYPos = yPos;
					resultType = minType;
					}
				}
			}
		}
		
		// if (minMove == 1000) {
		if (minMove <= 0) {
			System.out.println("No more move");
			end = true;
			return;
		}
		// if (resultMinMove == 1000) {
		if (resultMinMove <= 0) {
			System.out.println("Result: " + minMove + " - " + xPos + " - " + yPos + " - Type: " + minType);
			System.out.println("You-Machine will lose!");
			map[xPos+2][yPos+2]=minType;
		}
		else {
			System.out.println("Result: " + resultMinMove + " - " + resultXPos + " - " + resultYPos + " - Type: " + resultType);
			map[resultXPos+2][resultYPos+2]=resultType;
		}
	}

	public static int generateMove(int X, int Y, int type){
		int[][] newMap = new int[D][D];
		int countMove = 0;
		for (int i = 0; i < D; i++)
			for (int j = 0; j < D; j++)
			newMap[i][j] = map[i][j];

		if (!checkMove(newMap,X,Y,type)) return 0;
		newMap[X][Y] = type;

		for (int i = 2; i < D - 2; i++) {
			for (int j = 2; j < D - 2; j++) {
				if (map[i][j] != EMPTY) continue;
				if (checkMove(newMap,i,j,X)) countMove++;
				if (checkMove(newMap,i,j,O)) countMove++;
			}
		}
		return countMove;
    }

}
