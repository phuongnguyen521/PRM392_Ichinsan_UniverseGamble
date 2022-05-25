package com.example.prm392_ichinsan_universegamble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    private EditText etUsername;
    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsername = (EditText) findViewById(R.id.etUsername);
                if (TextUtils.isEmpty(etUsername.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Required", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
                    Intent intent = new Intent(SignUpActivity.this, CharactersActivity.class);
                    intent.putExtra("username", etUsername.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}