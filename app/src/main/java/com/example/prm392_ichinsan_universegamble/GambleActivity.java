package com.example.prm392_ichinsan_universegamble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Random;

public class GambleActivity extends AppCompatActivity {

    //Variable
    private ArrayList<Character> characters;
    private String username;
    private TextView tvCoins;
    private Button btnStart;
    private EditText etGambedCoins;
    private TextView tvWelcome;
    private String cbName;
    private int deposit;

    //Checkbox
    private CheckBox cbCharacter1;
    private CheckBox cbCharacter2;
    private CheckBox cbCharacter3;

    //TextView
    private TextView tvCharacterName1;
    private TextView tvCharacterName2;
    private TextView tvCharacterName3;

    //Image
    private ImageView ivCharacter1;
    private ImageView ivCharacter2;
    private ImageView ivCharacter3;

    //Seekbar
    private SeekBar sbCharacter1;
    private SeekBar sbCharacter2;
    private SeekBar sbCharacter3;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.itemChangeCharacters:
                intent = new Intent(GambleActivity.this, CharactersActivity.class);
                int counter = 1;
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
                return true;
            case R.id.itemExit:
                intent = new Intent(GambleActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamble);


        setUsername();
        if (checkUsername()){
            setDialog();
        }
        getCharacters();
        setCharacters();

        //Anh xa
        btnStart = (Button) findViewById(R.id.btnStart);
        etGambedCoins = (EditText) findViewById(R.id.etGambedCoins);
        setEtGambleAndStartButton(false);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(tvWelcome.getText().toString() + " " + username);
        tvCoins = (TextView) findViewById(R.id.tvCoins);

        cbCharacter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbCharacter1.isChecked()){
                    setEtGambleAndStartButton(true);
                    cbCharacter2.setEnabled(false);
                    cbCharacter3.setEnabled(false);
                    cbName = "cbCharacter1";
                } else {
                    setEtGambleAndStartButton(false);
                    cbCharacter2.setEnabled(true);
                    cbCharacter3.setEnabled(true);
                    cbName = null;
                }
            }
        });

        cbCharacter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbCharacter2.isChecked()){
                    setEtGambleAndStartButton(true);
                    cbCharacter1.setEnabled(false);
                    cbCharacter3.setEnabled(false);
                    cbName = "cbCharacter2";
                } else {
                    setEtGambleAndStartButton(false);
                    cbCharacter1.setEnabled(true);
                    cbCharacter3.setEnabled(true);
                    cbName = null;
                }
            }
        });

        cbCharacter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbCharacter3.isChecked()){
                    setEtGambleAndStartButton(true);
                    cbCharacter2.setEnabled(false);
                    cbCharacter1.setEnabled(false);
                    cbName = "cbCharacter3";
                } else {
                    setEtGambleAndStartButton(false);
                    cbCharacter1.setEnabled(true);
                    cbCharacter2.setEnabled(true);
                    cbName = null;
                }
            }
        });

        etGambedCoins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    deposit = Integer.parseInt(etGambedCoins.getText().toString());
                } catch (Exception e){
                    deposit = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    deposit = Integer.parseInt(etGambedCoins.getText().toString());
                } catch (Exception e){
                    deposit = 0;
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbCharacter1.setChecked(false);
                cbCharacter2.setChecked(false);
                cbCharacter3.setChecked(false);
                boolean result = isTheFirst();
                if (result){
                    calculatePocket(20,false);
                    setDialogGamble("You Won!", "Your pocket increases $20");
                } else {
                    calculatePocket(deposit, true);
                    if (tvCoins.getText().toString().equals("$0"))
                    {
                        Intent intent = new Intent(GambleActivity.this, BrokeActivity.class);
                        int counter = 1;
                        intent.putExtra("username",username);
                        for (Character character : characters) {
                            intent.putExtra("character" + counter, character);
                            counter++;
                        }
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        setDialogGamble("You Lose!", "Luck is always there for you!");
                    }
                }
                cbCharacter1.setEnabled(true);
                cbCharacter2.setEnabled(true);
                cbCharacter3.setEnabled(true);
                sbCharacter1.setProgress(0);
                sbCharacter2.setProgress(0);
                sbCharacter3.setProgress(0);
                setEtGambleAndStartButton(false);
            }
        });

    }

    private void setUsername(){
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            username = extras.getString("username");
        }
    }

    private boolean checkUsername(){
        return TextUtils.isEmpty(username);
    }

    //https://www.geeksforgeeks.org/android-alert-dialog-box-and-how-to-create-it/
    //https://developer.android.com/guide/topics/ui/dialogs#java
    private void setDialog(){
        //Create builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(GambleActivity.this);
        //Set Title
        builder.setTitle("Who are you?");

        //Set Message
        builder.setMessage("You shall enter username first!");

        //Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with OK name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GambleActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Create the Alert Dialog
        AlertDialog alertDialog = builder.create();

        //Show the Alert Dialog box
        alertDialog.show();

    }

    //https://www.vogella.com/tutorials/AndroidParcelable/article.html#:~:text=A%20Parcelable%20is%20the%20Android,to%20the%20standard%20Java%20serialization.
    private void getCharacters(){
        int counter = 1;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            if (CollectionUtils.isEmpty(characters)){
                characters = new ArrayList<>();
            }
            while (counter < 4)
            {
                Character character = (Character) extras.getParcelable("character" + counter);
                characters.add(character);
                counter++;
            }
        }
    }

    private void setCharacters(){
        //Set Checkbox
        cbCharacter1 = (CheckBox) findViewById(R.id.cbCharacter1);
        cbCharacter2 = (CheckBox) findViewById(R.id.cbCharacter2);
        cbCharacter3 = (CheckBox) findViewById(R.id.cbCharacter3);

        //Set TextView
        tvCharacterName1 = (TextView) findViewById(R.id.tvCharacterName1);
        tvCharacterName2 = (TextView) findViewById(R.id.tvCharacterName2);
        tvCharacterName3 = (TextView) findViewById(R.id.tvCharacterName3);

        //Set ImageView
        ivCharacter1 = (ImageView) findViewById(R.id.ivCharacter1);
        ivCharacter2 = (ImageView) findViewById(R.id.ivCharacter2);
        ivCharacter3 = (ImageView) findViewById(R.id.ivCharacter3);

        //Set Seekbar
        sbCharacter1 = (SeekBar) findViewById(R.id.sbCharacter1);
        sbCharacter2 = (SeekBar) findViewById(R.id.sbCharacter2);
        sbCharacter3 = (SeekBar) findViewById(R.id.sbCharacter3);
        sbCharacter1.setEnabled(false);
        sbCharacter2.setEnabled(false);
        sbCharacter3.setEnabled(false);

        Character character = characters.get(0);
        setCharacterDetail(character, tvCharacterName1, ivCharacter1);
        character = characters.get(1);
        setCharacterDetail(character, tvCharacterName2, ivCharacter2);
        character = characters.get(2);
        setCharacterDetail(character, tvCharacterName3, ivCharacter3);
    }

    private void setCharacterDetail(Character character, TextView tvCharacterName, ImageView ivCharacter){
        tvCharacterName.setText(character.getName());
        ivCharacter.setImageDrawable(getResources().getDrawable(character.getImage()));
    }

    private void setEtGambleAndStartButton(boolean flag){
        etGambedCoins.setEnabled(flag);
        btnStart.setEnabled(flag);
        if (!flag){
            etGambedCoins.setText("");
        }
    }

    private void calculatePocket(int money, boolean isDesposited){
        int total = Integer.parseInt(tvCoins.getText().toString().substring(1));
        if (isDesposited){
            if (money >= total){
                total = 0;
            } else {
                total -= money;
            }
        } else {
            total += money;
        }
        tvCoins.setText("$"+total);
//        if (cbCharacter1.isChecked() || cbCharacter2.isChecked() || cbCharacter3.isChecked()){
//            btnStart.setEnabled(true);
//        } else {
//            btnStart.setEnabled(false);
//        }
    }

    private boolean isTheFirst(){
        String champion;
        int progress1 = sbCharacter1.getProgress();
        int progress2 = sbCharacter2.getProgress();
        int progress3 = sbCharacter3.getProgress();

        while (true)
        {
            progress1 += new Random().nextInt((50 - 10) + 1) + 10;
            if (progress1 >= 100)
            {
                progress1 = 100;
                champion = "cbCharacter1";
                break;
            }
            progress2 += new Random().nextInt((50 - 10) + 1) + 10;
            if (progress2 >= 100)
            {
                progress2 = 100;
                champion = "cbCharacter2";
                break;
            }
            progress3 += new Random().nextInt((50 - 10) + 1) + 10;
            if (progress3 >= 100){
                progress3 = 100;
                champion = "cbCharacter3";
                break;
            }
        }

        sbCharacter1.setProgress(progress1);
        sbCharacter2.setProgress(progress2);
        sbCharacter3.setProgress(progress3);
        new Handler().postDelayed(() -> {}, 10000);
        return cbName.equalsIgnoreCase(champion);
    }

    private void setDialogGamble(String title, String message){
            //Create builder
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(GambleActivity.this);
            //Set Title
            builder.setTitle(title);

            //Set Message
            builder.setMessage(message);

            //Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with OK name
            // OnClickListener method is use of
            // DialogInterface interface.

            builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            //Create the Alert Dialog
            AlertDialog alertDialog = builder.create();

            //Show the Alert Dialog box
            alertDialog.show();
    }


}