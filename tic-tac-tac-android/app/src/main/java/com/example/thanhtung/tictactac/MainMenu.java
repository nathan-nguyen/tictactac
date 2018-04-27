package com.example.thanhtung.tictactac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainMenu extends AppCompatActivity {

    private EditText editText;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        init();
    }

    public void init(){
        Button button = (Button) findViewById(R.id.tic_tac_tac_button   );
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent tictactacIntent = new Intent(getApplicationContext(), TicTacTac.class);
                int N = Integer.parseInt(editText.getText().toString());
                if (N < 4 || N > 14) return;
                tictactacIntent.putExtra("N", Integer.parseInt(editText.getText().toString()));
                tictactacIntent.putExtra("playerFirst", checkBox.isChecked());
                startActivity(tictactacIntent);
            }
        });


    }
}
