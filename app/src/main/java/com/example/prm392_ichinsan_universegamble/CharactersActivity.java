package com.example.prm392_ichinsan_universegamble;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;

public class CharactersActivity extends AppCompatActivity {

    //Variables
    private String username;
    private TextView txtSum;
    private ListView lvCharacters;
    private CustomCharactersAdapter adapter;
    private ArrayList<Character> tempCharacters;
    private ArrayList<Character> characters;
    private Button btnNext;
    private Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        setUsername();
        if (checkUsername()){
            setDialog();
        }

        populateCharactersList();

        //Anh xa
        txtSum = (TextView) findViewById(R.id.txtSum);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnExit = (Button) findViewById(R.id.btnExit);
//        https://www.geeksforgeeks.org/how-to-change-background-color-of-listview-items-in-android/
        lvCharacters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(CollectionUtils.isEmpty(tempCharacters))
                {
                    tempCharacters = new ArrayList<>();
                }
                Character character = characters.get(position);
                setTextViewSum(!character.isClicked());
                if (character.isClicked()){
                    character.setClicked(false);
                    characters.set(position, character);
                    tempCharacters.remove(character);
                    view.setBackgroundColor(Color.WHITE);
                } else {
                    character.setClicked(true);
                    characters.set(position, character);
                    tempCharacters.add(character);
                    view.setBackgroundColor(Color.rgb(240,30,20));
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharactersActivity.this, GambleActivity.class);
                int counter = 1;
                intent.putExtra("username",username);
                for (Character character : tempCharacters) {
                    intent.putExtra("character" + counter, character);
                    counter++;
                }
                startActivity(intent);
                finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharactersActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
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
                new AlertDialog.Builder(CharactersActivity.this);
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
                Intent intent = new Intent(CharactersActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Create the Alert Dialog
        AlertDialog alertDialog = builder.create();

        //Show the Alert Dialog box
        alertDialog.show();
    }

    private void populateCharactersList(){
        // Construct the data source
        characters = Character.getCharacters();
        // Create the adapter to convert the array to views
        adapter = new CustomCharactersAdapter (this, characters);
        // Attach the adapter to a ListView
        lvCharacters = (ListView) findViewById(R.id.lvCharacters);
        lvCharacters.setAdapter(adapter);
    }

    private void setTextViewSum(boolean isAdded){
        txtSum = (TextView) findViewById(R.id.txtSum);
        int position = txtSum.getText().toString().indexOf("/");
        int number = Integer.parseInt(txtSum.getText().toString().substring(0, position));
        if (isAdded){
            number += 1;
        } else {
            number -= 1;
        }
        String result = number + "/3";
        txtSum.setText(result);
        btnNext.setEnabled(number == 3);
    }
}