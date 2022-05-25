package com.example.prm392_ichinsan_universegamble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomCharactersAdapter extends ArrayAdapter<Character> {

    public CustomCharactersAdapter(@NonNull Context context, @NonNull List<Character> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the data item for this position
        Character character = getItem(position);

        // Lookup view for data population
        ImageView ivCharacter = (ImageView) convertView.findViewById(R.id.ivCharacter);
        TextView tvCharacter = (TextView) convertView.findViewById(R.id.tvCharacter);
        // Populate the data into the template view using the data object
        ivCharacter.setImageDrawable(getContext().getResources().getDrawable(character.getImage()));
        tvCharacter.setText(character.getName());
        // Return the completed view to render on screen
        return convertView;
    }

}
