package com.example.tictactoe;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";
    boolean player1_turn = true;
    byte[][] board = new byte[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.board), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button btn = (Button) row.getChildAt(j);
                btn.setOnClickListener(new CellListener(i, j));
            }

        }
    }

    public boolean isValidMove(int row, int col) {
        return board[row][col] == 0;
    }

    public int gameEnded(int row, int col) {
        int symbol = board[row][col];

        // Check row
        boolean win = true;
        for (int i = 0; i < 3; i++) {
            if (board[row][i] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return symbol;
        }

        // Check column
        win = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][col] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return symbol;
        }

        // Check diagonal
        if (row == col) {
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return symbol;
            }
        }

        // Check anti-diagonal
        if (row + col == 2) {
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return symbol;
            }
        }

        // Check for draw
        boolean boardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    boardFull = false;
                    break;
                }
            }
        }
        if (boardFull) {
            return 0; // Draw
        }

        return -1; // Game not ended
    }



    class CellListener implements View.OnClickListener{
        int row,col;

        public CellListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (!isValidMove(row, col)){
                Toast.makeText(MainActivity.this, "Cell is already occupied.", Toast.LENGTH_LONG).show();
                return;
            }

            if (player1_turn){
                ((Button)v).setText(PLAYER_1);
                board[row][col] = 1;
            }else{
                ((Button)v).setText(PLAYER_2);
                board[row][col] = 2;
            }
            int gameResult = gameEnded(row, col);
            if (gameResult == -1){
                player1_turn = !player1_turn;
            }
            else if (gameResult == 0){
                Toast.makeText(MainActivity.this, "It is a Draw", Toast.LENGTH_LONG).show();
            }
            else if (gameResult == 1){
                Toast.makeText(MainActivity.this, "Player 1 Won", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(MainActivity.this, "Player 2 Won", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("player1Turn", player1_turn);
        byte[] boardSingle = new byte[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardSingle[i * 3 +j] = board[i][j];
            }
        }
        outState.putByteArray("board", boardSingle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1_turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle = savedInstanceState.getByteArray("board");
        for (int i = 0; i < 9; i++) {
            board[i/3][i%3] = boardSingle[i];
        }
        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button btn = (Button) row.getChildAt(j);
                if(board[i][j] == 1){
                    btn.setText("X");
                }
                else if (board[i][j] == 2) {
                    btn.setText("O");
                }
            }

        }

    }
}
