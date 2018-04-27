package com.example.thanhtung.tictactac;

/**
 * Created by thanhnam on 5/27/16.
 */


public class Game {

    // X - 1 & O - -1

    private static final int BOUNDARY = 50000;
    private int[][] map;		                // Current Map
    private int D;		                        // Map dimension
    private boolean end;
    private boolean playFirst = false;
    private int total;

    public int nextMoveX, nextMoveY, nextMoveType;

    public Game(int N){
        this.D = N + 4;
        map = new int[D][D];

        for (int i = 0; i < D; i++){
            for (int j = 0; j < D; j++){
                if (i < 2 || j < 2 || i > N +1 || j > N + 1) map[i][j] = BOUNDARY;
                else map[i][j] = 0;
            }
        }

        total = 0;
    }

    public void moveFirst(){
        this.playFirst = true;
        if (D == 8) {
            nextMoveX = 1;
            nextMoveY = 1;
            nextMoveType = 1;
        }
        else findBestSolution(total);
        map[nextMoveX+2][nextMoveY+2]=nextMoveType;
        total++;
    }

    // Return value: 1: Human win
    //              -1: Human lose
    //               0: Continue
    //              -2: Invalid move
    public int move(int x, int y, int type){
        if (map[x+2][y+2] != 0) return -2;
        map[x+2][y+2]=type;
        total++;

        if (!checkMove(x+2,y+2,type)) {
            System.out.println("You lose!");
            return -1;
        }

        if(!findBestSolution(total)) {
            System.out.println("You win!");
            return 1;
        }

        System.out.println("Continue!");
        //UI get nextMoveX and nextMoveY and update
        map[nextMoveX+2][nextMoveY+2]=nextMoveType;
        total++;
        return 0;
    }

    private boolean findBestSolution(int total){
        int result;
        for (int i = 2; i < D - 2; i++) {
            for (int j = 2; j < D - 2; j++) {
                if (map[i][j] != 0) continue;
                if (checkMove(i,j,1)) {
                    map[i][j] = 1;
                    result = findSolution(total+1);
                    map[i][j] = 0;
                    if (result % 2 == (playFirst?1:0)) {
                        System.out.println("Solution: " + (j - 1) + " - " + (i - 1) + " - X - " + (total+1));
                        nextMoveX = i - 2;  nextMoveY = j - 2;  nextMoveType = 1;
                        return true;
                    }
                }
                if (checkMove(i,j,-1)) {
                    map[i][j] = -1;
                    result = findSolution(total+1);
                    map[i][j] = 0;
                    if (result % 2 == (playFirst?1:0)) {
                        System.out.println("Solution: " + (j - 1) + " - " + (i - 1) + " - O - " + (total+1));
                        nextMoveX = i - 2;  nextMoveY = j - 2;  nextMoveType = -1;
                        return true;
                    }
                }
            }
        }
        System.out.println("No move");
        return false;
    }

    private int findSolution(int total){
        int step = total;
        int result;
        for (int i = 2; i < D - 2; i++) {
            for (int j = 2; j < D - 2; j++) {
                if (map[i][j] != 0) continue;
                if (checkMove(i,j,1)) {
                    map[i][j] = 1;
                    step++;
                    result = findSolution(step);
                    System.out.println(j - 1 + " - " + (i - 1) + " - X - " + step);
                    map[i][j] = 0;
                    return result;
                }
                if (checkMove(i,j,-1)) {
                    map[i][j] = -1;
                    step++;
                    result = findSolution(step);
                    System.out.println(j - 1 + " - " + (i - 1) + " - O - " + step);
                    map[i][j] = 0;
                    return result;
                }
            }
        }
        System.out.println("Total: " + total);
        return step;
    }


    // Stupid Code
    private boolean checkMove(int X, int Y, int type){
        if (
                (map[X - 2][Y - 2]	+ map[X - 1][Y - 1]	== type) ||	// 1 - 9
                (map[X][Y - 2]	    + map[X][Y - 1]	    == type) ||	// 2 - 10
                (map[X + 2][Y - 2] 	+ map[X + 1][Y - 1] == type) ||	// 3 - 11
                (map[X + 2][Y]	    + map[X + 1][Y]	    == type) ||	// 4 - 12
                (map[X + 2][Y + 2] 	+ map[X + 1][Y + 1]	== type) ||	// 5 - 13
                (map[X][Y + 2]	    + map[X][Y + 1]	    == type) ||	// 6 - 14
                (map[X - 2][Y + 2]	+ map[X - 1][Y + 1]	== type) ||	// 7 - 15
                (map[X - 2][Y]	    + map[X - 1][Y]	    == type) ||	// 8 - 16

                (map[X - 1][Y - 1]	+ map[X + 1][Y + 1] == type) ||	// 9 - 13
                (map[X][Y - 1]	    + map[X][Y + 1] 	== type) ||	// 10 - 14
                (map[X + 1][Y - 1]	+ map[X - 1][Y + 1] == type) ||	// 11 - 15
                (map[X + 1][Y]	    + map[X - 1][Y] 	== type) ||	// 12 - 16
                (map[X + 1][Y + 1]	+ map[X - 1][Y - 1] == type) ||	// 13 - 9
                (map[X][Y + 1]	    + map[X][Y - 1] 	== type) ||	// 14 - 10
                (map[X - 1][Y + 1]	+ map[X + 1][Y - 1] == type) ||	// 15 - 11
                (map[X - 1][Y]	    + map[X + 1][Y] 	== type)	// 16 - 12
                )
            return false;
        return true;
    }
}