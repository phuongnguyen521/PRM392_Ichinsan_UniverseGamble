package com.example.prm392_ichinsan_universegamble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        etUsername = (EditText) findViewById(R.id.etUsername);
        btnGo.setEnabled(false);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int character = etUsername.length();
                btnGo.setEnabled(character > 0 && character <= 15);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, CharactersActivity.class);
                intent.putExtra("username", etUsername.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}