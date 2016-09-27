import java.util.*;
import java.io.IOException;
import java.util.Scanner;

public class DataGenerator {
	private static int[][] map;		// Current Map
	private static int D;			// Map dimension
	
	public static FileIO fileIO = FileIO.instance();
	
	private static ArrayList<String> stringQueue = new ArrayList<>();
	
	private static int count = 0;
	
	public static void main(String[] args) throws IOException{
		//Scanner in = new Scanner(System.in);
		//System.out.print("N = ");
		//int N = in.nextInt();
		int N = 4;
		fileIO.appendlnEntry("N = " + N);
		D = N + 4;
		map = new int[D][D];
		
		for (int i = 0; i < D; i++){
			for (int j = 0; j < D; j++){
				if (i < 2 || j < 2 || i > N +1 || j > N + 1) map[i][j] = TicTacTac.BOUNDARY;
			else map[i][j] = TicTacTac.EMPTY;
			}
		}
	
		// N = 4
		count = 0;
		map[3][3] = TicTacTac.X;
		//TicTacTac.printMap(map, D);
		stringQueue.add("1-1-X; ");
		findSolution();
		map[3][3] = TicTacTac.EMPTY;
		stringQueue.remove(0);
	}
	
	public static void findSolution() throws IOException{
		boolean found = false;
		for (int i = 2; i < D - 2; i++) {
			for (int j = 2; j < D - 2; j++) {
				if (map[i][j] != TicTacTac.EMPTY) continue;
				
				if (TicTacTac.checkMove(map,i,j,TicTacTac.X)) {
					found = true;
					map[i][j] = TicTacTac.X;
					stringQueue.add((j - 2) + "-" + (i - 2) + "-X; ");
					findSolution();
					map[i][j] = TicTacTac.EMPTY;
					stringQueue.remove(stringQueue.size()-1);
				}
				
				if (TicTacTac.checkMove(map,i,j,TicTacTac.O)) {
					found = true;
					map[i][j] = TicTacTac.O;
					stringQueue.add((j - 2) + "-" + (i - 2) + "-O; ");
					findSolution();
					map[i][j] = TicTacTac.EMPTY;
					stringQueue.remove(stringQueue.size()-1);
				}
			}
		}
		if (!found){
			//TicTacTac.printMap(map, D);
			String line = stringQueue.size() + "\t" + (++count) + "\t\t";
			for (int i = 0; i < stringQueue.size(); i++)
				line+=stringQueue.get(i);
			fileIO.appendEntry(line);
		}
	}
}