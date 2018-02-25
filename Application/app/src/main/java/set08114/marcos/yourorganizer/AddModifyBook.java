package set08114.marcos.yourorganizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class AddModifyBook extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText nameText = findViewById(R.id.nameEditText);
                EditText chapterText = findViewById(R.id.chapterEditText);
                EditText pageText = findViewById(R.id.pageEditText);
                //The user must enter at least the name
                if(nameText.getText().toString().isEmpty())
                {
                    String message = "Please enter a name";
                    Toast.makeText(getApplicationContext(), message, LENGTH_SHORT).show();
                }
                else
                {
                    Book book = new Book();
                    book.setName(nameText.getText().toString());
                    book.setChapter(Integer.parseInt(chapterText.getText().toString()));
                    book.setPage(Integer.parseInt(pageText.getText().toString()));
                    //Check if one of the check boxes is checked
                    CheckBox completedCheckBox = findViewById(R.id.completedCheckBox);
                    CheckBox onGoingCheckBox = findViewById(R.id.onGoingCheckBox);
                    if(completedCheckBox.isChecked())
                    {
                        book.setStatus(completedCheckBox.getText().toString());
                    }
                    else if(onGoingCheckBox.isChecked())
                    {
                        book.setStatus(onGoingCheckBox.getText().toString());
                    }
                    //Add book to storage

                }
            }
        });
    }

    public void onCheckboxClicked(View view)
    {

        CheckBox completedCheckBox = findViewById(R.id.completedCheckBox);
        CheckBox onGoingCheckBox = findViewById(R.id.onGoingCheckBox);

        // Check which checkbox was clicked
        switch(view.getId())
        {
            case R.id.completedCheckBox:
                completedCheckBox.setChecked(true);
                onGoingCheckBox.setChecked(false);
                break;
            case R.id.onGoingCheckBox:
                onGoingCheckBox.setChecked(true);
                completedCheckBox.setChecked(false);
                break;
        }
    }
}
