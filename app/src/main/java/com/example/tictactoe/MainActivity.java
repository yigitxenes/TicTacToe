package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

    public boolean isValidMove(int row, int col){
        return board[row][col] == 0;
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
        }
    }
}