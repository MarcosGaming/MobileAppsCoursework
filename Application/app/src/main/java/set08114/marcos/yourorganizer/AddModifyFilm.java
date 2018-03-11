package set08114.marcos.yourorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Marcos on 11/02/2018.
 * Activity that allows the user to add a new film to the storage or modify an already stored film
 * Last modified date : 11/03/2018.
 */

public class AddModifyFilm extends AppCompatActivity
{
    InternalStorage storage;
    Film selectedFilm;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_film);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storage = InternalStorage.getInstance(this);
        Button modifyBtn = findViewById(R.id.finishModifyBtnFilm);
        Button addBtn = findViewById(R.id.finishAddBtnFilm);
        //Get selectedFilm
        Intent i = getIntent();
        selectedFilm = (Film) i.getSerializableExtra("selectedFilm");
        //Activity behaviour depends on the selectedFilm value
        if(selectedFilm == null)
        {
            modifyBtn.setEnabled(false);
        }
        else
        {
            addBtn.setEnabled(false);
            //Set the widgets according to the selectedFilm properties
            EditText nameText = findViewById(R.id.filmNameEditText);
            EditText dateText = findViewById(R.id.filmDateEditText);
            //The film name can not be modified
            nameText.setText(selectedFilm.getName());
            nameText.setFocusable(false);
            //Make sure that the attributes are not null
            if(selectedFilm.getReleaseDate() != null)
            {
                dateText.setText(selectedFilm.getReleaseDate());
            }
            if(selectedFilm.getStatus() != null)
            {
                if(selectedFilm.getStatus().equals(getResources().getString(R.string.status_seen)))
                {
                    CheckBox completedCheckBox = findViewById(R.id.filmCompletedCheckBox);
                    completedCheckBox.setChecked(true);
                }
                else if(selectedFilm.getStatus().equals(getResources().getString(R.string.status_notSeen)))
                {
                    CheckBox onGoingCheckBox = findViewById(R.id.filmOnGoingCheckBox);
                    onGoingCheckBox.setChecked(true);
                }
            }
        }
        //Add button functionality
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText nameText = findViewById(R.id.filmNameEditText);
                EditText dateText = findViewById(R.id.filmDateEditText);
                //The user must enter at least the name
                if(nameText.getText().toString().isEmpty())
                {
                    String message = "Please enter a name";
                    Toast.makeText(getApplicationContext(), message, LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        Film film = new Film();
                        film.setName(nameText.getText().toString());
                        film.setReleaseDate(dateText.getText().toString());
                        //Check if one of the check boxes is checked
                        CheckBox completedCheckBox = findViewById(R.id.filmCompletedCheckBox);
                        CheckBox onGoingCheckBox = findViewById(R.id.filmOnGoingCheckBox);
                        if(completedCheckBox.isChecked())
                        {
                            film.setStatus(completedCheckBox.getText().toString());
                        }
                        else if(onGoingCheckBox.isChecked())
                        {
                            film.setStatus(onGoingCheckBox.getText().toString());
                        }
                        //Add film to storage
                        storage.addFilm(film);
                        //Reset interface elements
                        nameText.setText("");
                        dateText.setText("");
                        completedCheckBox.setChecked(false);
                        onGoingCheckBox.setChecked(false);
                    }
                    catch(IllegalArgumentException e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_SHORT).show();
                    }
                }
            }
        });
        //Modify button functionality
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameText = findViewById(R.id.filmNameEditText);
                EditText dateText = findViewById(R.id.filmDateEditText);
                try
                {
                    Film film = new Film();
                    film.setName(nameText.getText().toString());
                    film.setReleaseDate(dateText.getText().toString());
                    //Check if one of the check boxes is checked
                    CheckBox completedCheckBox = findViewById(R.id.filmCompletedCheckBox);
                    CheckBox onGoingCheckBox = findViewById(R.id.filmOnGoingCheckBox);
                    if(completedCheckBox.isChecked())
                    {
                        film.setStatus(completedCheckBox.getText().toString());
                    }
                    else if(onGoingCheckBox.isChecked())
                    {
                        film.setStatus(onGoingCheckBox.getText().toString());
                    }
                    //Modify old film(selected film)
                    storage.modifyFilm(selectedFilm, film);
                    String message = "Film successfully modified";
                    Toast.makeText(getApplicationContext(),message, LENGTH_SHORT).show();
                }
                catch(IllegalArgumentException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCheckboxClicked(View view)
    {
        CheckBox completedCheckBox = findViewById(R.id.filmCompletedCheckBox);
        CheckBox onGoingCheckBox = findViewById(R.id.filmOnGoingCheckBox);
        // Check which checkbox was clicked
        switch(view.getId())
        {
            case R.id.filmCompletedCheckBox:
                completedCheckBox.setChecked(true);
                onGoingCheckBox.setChecked(false);
                break;
            case R.id.filmOnGoingCheckBox:
                onGoingCheckBox.setChecked(true);
                completedCheckBox.setChecked(false);
                break;
        }
    }
}
