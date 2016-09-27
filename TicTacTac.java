import java.io.IOException;

public class TicTacTac {
	// X(1) ; O(-1)
	public static final int O = -1;
	public static final int EMPTY = 0;
	public static final int X = 1;	
	
    public static int BOUNDARY = 50000;
	
	public static FileIO fileIO = FileIO.instance();
	
	public static boolean checkMove(int[][] newMap, int X, int Y, int type){
		if (
			(newMap[X - 2][Y - 2]	+ newMap[X - 1][Y - 1]	== type)	||	// 1 - 9
			(newMap[X][Y - 2]		+ newMap[X][Y - 1]	== type) 		||	// 2 - 10
			(newMap[X + 2][Y - 2] 	+ newMap[X + 1][Y - 1] 	== type)	||	// 3 - 11
			(newMap[X + 2][Y]		+ newMap[X + 1][Y]	== type)		||	// 4 - 12
			(newMap[X + 2][Y + 2] 	+ newMap[X + 1][Y + 1]	== type) 	||	// 5 - 13
			(newMap[X][Y + 2]		+ newMap[X][Y + 1]	== type)		||	// 6 - 14
			(newMap[X - 2][Y + 2]	+ newMap[X - 1][Y + 1]	== type)	||	// 7 - 15
			(newMap[X - 2][Y]		+ newMap[X - 1][Y]	== type)		||	// 8 - 16

			(newMap[X - 1][Y - 1]	+ newMap[X + 1][Y + 1] 	== type)	||	// 9 - 13
			(newMap[X][Y - 1]		+ newMap[X][Y + 1] 	== type)		||	// 10 - 14
			(newMap[X + 1][Y - 1]	+ newMap[X - 1][Y + 1] 	== type)	||	// 11 - 15
			(newMap[X + 1][Y]		+ newMap[X - 1][Y] 	== type)		||	// 12 - 16
			(newMap[X + 1][Y + 1]	+ newMap[X - 1][Y - 1] 	== type)	||	// 13 - 9
			(newMap[X][Y + 1]		+ newMap[X][Y - 1] 	== type)		||	// 14 - 10
			(newMap[X - 1][Y + 1]	+ newMap[X + 1][Y - 1] 	== type)	||	// 15 - 11
			(newMap[X - 1][Y]		+ newMap[X + 1][Y] 	== type)			// 16 - 12
		)
			return false;
		return true;
    }
	
	public static void printMap(int[][] map, int D) throws IOException{
		for (int i = 2; i< D - 2; i++){
			for (int  j = 2; j < D - 2; j++){
				switch (map[i][j]){
					case EMPTY 	: fileIO.updateSaveData("- "); break;
					case X 		: fileIO.updateSaveData("X "); break;
					case O		: fileIO.updateSaveData("O "); break;
				}
			}
			fileIO.appendSaveData();
		}
		fileIO.appendEntry("");
    }
}