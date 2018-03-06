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
 * Activity that allows the user to add a new book to the storage or modify an already stored book
 * Last modified date : 05/03/2018.
 */

public class AddModifyBook extends AppCompatActivity
{
    InternalStorage storage;
    Book selectedBook;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storage = InternalStorage.getInstance(this);
        Button modifyBtn = findViewById(R.id.modifyBtn);
        Button addBtn = findViewById(R.id.addBtn);
        //Get selectedBook
        Intent i = getIntent();
        selectedBook = (Book)i.getSerializableExtra("selectedBook");
        //Activity behaviour depends on the selectedBook value
        if(selectedBook == null)
        {
            modifyBtn.setEnabled(false);
        }
        else
        {
            addBtn.setEnabled(false);
            //Set the widgets according to the selectedBook properties
            EditText nameText = findViewById(R.id.nameEditText);
            EditText chapterText = findViewById(R.id.chapterEditText);
            EditText pageText = findViewById(R.id.pageEditText);
            //A book must have at least the name
            nameText.setText(selectedBook.getName());
            nameText.setFocusable(false);
            //Make sure that the attributes are not null
            if(selectedBook.getChapter() != null)
            {
                chapterText.setText(selectedBook.getChapter());
            }
            if(selectedBook.getPage() != null)
            {
                pageText.setText(selectedBook.getPage());
            }
            if(selectedBook.getStatus() != null)
            {
                if(selectedBook.getStatus().equals("Completed"))
                {
                    CheckBox completedCheckBox = findViewById(R.id.completedCheckBox);
                    completedCheckBox.setChecked(true);
                }
                else if(selectedBook.getStatus().equals("On Going"))
                {
                    CheckBox onGoingCheckBox = findViewById(R.id.onGoingCheckBox);
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
                    try
                    {
                        Book book = new Book();
                        book.setName(nameText.getText().toString());
                        book.setChapter(chapterText.getText().toString());
                        book.setPage(pageText.getText().toString());
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
                        storage.addBook(book);
                        //Reset interface elements
                        nameText.setText("");
                        chapterText.setText("");
                        pageText.setText("");
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
                    try
                    {
                        Book book = new Book();
                        book.setName(nameText.getText().toString());
                        book.setChapter(chapterText.getText().toString());
                        book.setPage(pageText.getText().toString());
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
                        //Remove old book(selected book)
                        storage.modifyBook(selectedBook, book);
                        String message = "Book successfully modified";
                        Toast.makeText(getApplicationContext(),message, LENGTH_SHORT).show();
                    }
                    catch(IllegalArgumentException e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_SHORT).show();
                    }
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
