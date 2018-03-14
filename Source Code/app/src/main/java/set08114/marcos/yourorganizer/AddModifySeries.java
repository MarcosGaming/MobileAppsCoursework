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
 * Activity that allows the user to add a new series to the storage or modify an already stored series
 * Last modified date : 11/03/2018.
 */

public class AddModifySeries extends AppCompatActivity
{
    InternalStorage storage;
    Series selectedSeries;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_series);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storage = InternalStorage.getInstance(this);
        Button modifyBtn = findViewById(R.id.finishModifyBtnSeries);
        Button addBtn = findViewById(R.id.finishAddBtnSeries);
        //Get selectedSeries
        Intent i = getIntent();
        selectedSeries = (Series)i.getSerializableExtra("selectedSeries");
        //Activity behaviour depends on the selectedSeries value
        if(selectedSeries == null)
        {
            modifyBtn.setEnabled(false);
        }
        else
        {
            addBtn.setEnabled(false);
            //Set the widgets according to the selectedSeries properties
            EditText nameText = findViewById(R.id.seriesNameEditText);
            EditText seasonText = findViewById(R.id.seriesSeasonEditText);
            EditText episodeText = findViewById(R.id.seriesEpisodeEditText);
            //The series name can not be modified
            nameText.setText(selectedSeries.getName());
            nameText.setFocusable(false);
            //Make sure that the attributes are not null
            if(selectedSeries.getSeason() != null)
            {
                seasonText.setText(selectedSeries.getSeason());
            }
            if(selectedSeries.getEpisode() != null)
            {
                episodeText.setText(selectedSeries.getEpisode());
            }
            if(selectedSeries.getStatus() != null)
            {
                if(selectedSeries.getStatus().equals(getResources().getString(R.string.status_completed)))
                {
                    CheckBox completedCheckBox = findViewById(R.id.seriesCompletedCheckBox);
                    completedCheckBox.setChecked(true);
                }
                else if(selectedSeries.getStatus().equals(getResources().getString(R.string.status_onGoing)))
                {
                    CheckBox onGoingCheckBox = findViewById(R.id.seriesOnGoingCheckBox);
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
                EditText nameText = findViewById(R.id.seriesNameEditText);
                EditText seasonText = findViewById(R.id.seriesSeasonEditText);
                EditText episodeText = findViewById(R.id.seriesEpisodeEditText);
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
                        Series series = new Series();
                        series.setName(nameText.getText().toString());
                        series.setSeason(seasonText.getText().toString());
                        series.setEpisode(episodeText.getText().toString());
                        //Check if one of the check boxes is checked
                        CheckBox completedCheckBox = findViewById(R.id.seriesCompletedCheckBox);
                        CheckBox onGoingCheckBox = findViewById(R.id.seriesOnGoingCheckBox);
                        if(completedCheckBox.isChecked())
                        {
                            series.setStatus(completedCheckBox.getText().toString());
                        }
                        else if(onGoingCheckBox.isChecked())
                        {
                            series.setStatus(onGoingCheckBox.getText().toString());
                        }
                        //Add series to storage
                        storage.addSeries(series);
                        //Reset interface elements
                        nameText.setText("");
                        seasonText.setText("");
                        episodeText.setText("");
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
                EditText nameText = findViewById(R.id.seriesNameEditText);
                EditText seasonText = findViewById(R.id.seriesSeasonEditText);
                EditText episodeText = findViewById(R.id.seriesEpisodeEditText);
                try
                {
                    Series series = new Series();
                    series.setName(nameText.getText().toString());
                    series.setSeason(seasonText.getText().toString());
                    series.setEpisode(episodeText.getText().toString());
                    //Check if one of the check boxes is checked
                    CheckBox completedCheckBox = findViewById(R.id.seriesCompletedCheckBox);
                    CheckBox onGoingCheckBox = findViewById(R.id.seriesOnGoingCheckBox);
                    if(completedCheckBox.isChecked())
                    {
                        series.setStatus(completedCheckBox.getText().toString());
                    }
                    else if(onGoingCheckBox.isChecked())
                    {
                        series.setStatus(onGoingCheckBox.getText().toString());
                    }
                    //Modify old series(selected series)
                    storage.modifySeries(selectedSeries, series);
                    String message = "Book successfully modified";
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
        CheckBox completedCheckBox = findViewById(R.id.seriesCompletedCheckBox);
        CheckBox onGoingCheckBox = findViewById(R.id.seriesOnGoingCheckBox);
        // Check which checkbox was clicked
        switch(view.getId())
        {
            case R.id.seriesCompletedCheckBox:
                completedCheckBox.setChecked(true);
                onGoingCheckBox.setChecked(false);
                break;
            case R.id.seriesOnGoingCheckBox:
                onGoingCheckBox.setChecked(true);
                completedCheckBox.setChecked(false);
                break;
        }
    }
}
