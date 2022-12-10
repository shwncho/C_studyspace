import javax.swing.*;
import java.awt.*;

public class Main{
    static int N=6;
    static StringBuilder sb = new StringBuilder();
    static int answer=0;
    static JLabel[][] jLabel = new JLabel[N][N];


    static boolean promising(int row, int col, char[][] board){

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i<board.length; i++){
            if(board[i][col]=='Q') return false;
        }

        int i=row;
        int j=col;
        while(i>=0 && j>=0){
            if(board[i--][j--]=='Q')    return false;
        }
        i=row;
        j=col;
        while(i>=0 && j< board.length){
            if(board[i--][j++]=='Q')    return false;
        }
        return true;
    }

    static void DFS(int row, char[][] board){
        if(row== board.length){
            printBoard(board);
            answer++;
            return;
        }


        for(int i=0; i< board.length; i++){
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(promising(row,i,board)){
                board[row][i]='Q';
                jLabel[row][i].setBackground(Color.ORANGE);
                DFS(row+1,board);
                board[row][i]='.';
                jLabel[row][i].setBackground(Color.WHITE);

            }
        }
    }
    static int solve(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        char[][] board = new char[N][N];
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                board[i][j]='.';
                jLabel[i][j].setBackground(Color.BLUE);
            }
        }
        DFS(0,board);
        return answer;

    }

    static void printBoard(char[][] board){
        for(char[] chars : board){
            for(char ch : chars){
                sb.append(ch+" ");
            }
            sb.append("\n");
        }
        sb.append("\n");
    }

    static void guiSetUp() {


        JFrame jFrame = new JFrame("NQueen Visualizer.");

        jFrame.setLayout(new GridLayout(N, N));
        jFrame.setSize(500, 500);

        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);


        for(int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                jLabel[i][j] = new JLabel( "(" + i + "," + j + ")" );
                jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                jLabel[i][j].setSize(50, 50);

                jLabel[i][j].setOpaque(true);

                jFrame.add(jLabel[i][j]);
            }
        }
        jFrame.setVisible(true);

    }
    public static void main(String[] args){
        guiSetUp();
        int count;
        count=solve();
        System.out.println(sb);
        System.out.println(count);
    }
}
