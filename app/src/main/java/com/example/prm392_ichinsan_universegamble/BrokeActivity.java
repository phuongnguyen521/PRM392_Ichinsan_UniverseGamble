package com.example.prm392_ichinsan_universegamble;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;

public class BrokeActivity extends AppCompatActivity {

    //Variables
    private String username;
    private Button btnPlayAgain;
    private Button btnExitBroke;
    private ArrayList<Character> characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broke);

        setUsername();
        if (checkUsername()){
            setDialog();
        }
        //Anh xa
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnExitBroke = (Button) findViewById(R.id.btnExitBroke);

        btnExitBroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrokeActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPlayAgain();
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
                new AlertDialog.Builder(BrokeActivity.this);
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
                Intent intent = new Intent(BrokeActivity.this, SignUpActivity.class);
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

    private void setDialogPlayAgain(){
        //Create builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(BrokeActivity.this);
        //Set Title
        builder.setTitle("Keep Heroes?");

        //Set Message
        builder.setMessage("Do you want to keep 3 heroes again?");

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
                getCharacters();
                Intent intent = new Intent(BrokeActivity.this, GambleActivity.class);
                int counter = 1;
                intent.putExtra("username",username);
                for (Character character : characters) {
                    intent.putExtra("character" + counter, character);
                    counter++;
                }
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BrokeActivity.this, CharactersActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        //Create the Alert Dialog
        AlertDialog alertDialog = builder.create();

        //Show the Alert Dialog box
        alertDialog.show();

    }
}