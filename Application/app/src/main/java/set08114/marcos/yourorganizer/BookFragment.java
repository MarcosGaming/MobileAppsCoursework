package set08114.marcos.yourorganizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Marcos on 11/02/2018.
 * Activity that shows the list of books stored and allows the user to perform different types of operations with them.
 * Last modified date : 05/03/2018.
 */

public class BookFragment extends Fragment{

    InternalStorage storage;
    Book selectedBook;
    Boolean selection = false;
    TableRow selectedRow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.books_fragment,container,false);
        storage = InternalStorage.getInstance(this.getContext());
        final Button modifyBtn = view.findViewById(R.id.modifyBtn);
        final Button deleteBtn = view.findViewById(R.id.deleteBtn);
        //Disable modify and delete buttons
        modifyBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        this.initializeBookTable(storage.getBookList(),view, modifyBtn, deleteBtn);
        Button addBtn = view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddModifyBook.class);
                startActivity(intent);
            }
        });
        modifyBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddModifyBook.class);
                //Passing the selected book to the AddModifyBook activity
                intent.putExtra("selectedBook",selectedBook);
                startActivity(intent);
                selectedBook = null;
                selection = false;
            }
        });
        final TableLayout table = view.findViewById(R.id.book_tableLayout);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.removeBook(selectedBook);
                selectedBook = null;
                selection = false;
                modifyBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                table.removeView(selectedRow);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        storage.storeLists();
    }

    private void initializeBookTable(final List<Book> bookList, View view, final Button modifyBtn, final Button deleteBtn)
    {
        //Get screen width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        TableLayout table =  view.findViewById(R.id.book_tableLayout);
        //Border for the text views
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(getResources().getColor(R.color.colorTeal));
        gd.setCornerRadius(2);
        gd.setStroke(1, 0xFF000000);
        //Font for the text views
        Typeface font = Typeface.create("casual",Typeface.BOLD);
        //Header row
        TableRow headerRow = new TableRow(this.getContext());
        TableLayout.LayoutParams headerRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        headerRowParams.setMargins(0,1,0,0);
        headerRow.setLayoutParams(headerRowParams);
        //Header name text view
        TextView headerName = new TextView(this.getContext());
        headerName.setText(getResources().getString(R.string.book_NAME));
        headerName.setBackground(gd);
        headerName.setPadding(20,0,20,0);
        headerName.setTypeface(font);
        headerName.setTextColor(Color.BLACK);
        headerName.setWidth(screenWidth/4);
        headerRow.addView(headerName);
        //Header chapter text view
        TextView headerChapter = new TextView(this.getContext());
        headerChapter.setText(getResources().getString(R.string.book_CHAPTER));
        headerChapter.setBackground(gd);
        headerChapter.setPadding(20,0,20,0);
        headerChapter.setTypeface(font);
        headerChapter.setTextColor(Color.BLACK);
        headerChapter.setWidth(screenWidth/4);
        headerRow.addView(headerChapter);
        //Header page text view
        TextView headerPage = new TextView(this.getContext());
        headerPage.setText(getResources().getString(R.string.book_PAGE));
        headerPage.setBackground(gd);
        headerPage.setPadding(20,0,20,0);
        headerPage.setTypeface(font);
        headerPage.setTextColor(Color.BLACK);
        headerPage.setWidth(screenWidth/4);
        headerRow.addView(headerPage);
        //Header name text view
        TextView headerStatus = new TextView(this.getContext());
        headerStatus.setText(getResources().getString(R.string.book_STATUS));
        headerStatus.setBackground(gd);
        headerStatus.setPadding(20,0,20,0);
        headerStatus.setTypeface(font);
        headerStatus.setTextColor(Color.BLACK);
        headerStatus.setWidth(screenWidth/4);
        headerRow.addView(headerStatus);
        //Add row to table
        table.addView(headerRow);
        for(int i = 0; i < bookList.size();i++)
        {
            TableRow row = new TableRow(this.getContext());
            //Set row parameters
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParams.setMargins(0,1,0,1);
            row.setLayoutParams(tableRowParams);
            //Set text views parameters
            TextView name = new TextView(this.getContext());
            name.setText(bookList.get(i).getName());
            name.setBackground(gd);
            name.setPadding(20,0,20,0);
            TextView chapter = new TextView(this.getContext());
            chapter.setText(String.valueOf(bookList.get(i).getChapter()));
            chapter.setBackground(gd);
            chapter.setPadding(20,0,20,0);
            TextView page = new TextView(this.getContext());
            page.setText(String.valueOf(bookList.get(i).getPage()));
            page.setBackground(gd);
            page.setPadding(20,0,20,0);
            TextView status = new TextView(this.getContext());
            status.setText(bookList.get(i).getStatus());
            status.setBackground(gd);
            status.setPadding(20,0,20,0);
            //Add text views to the row
            row.addView(name);
            row.addView(chapter);
            row.addView(page);
            row.addView(status);
            row.setClickable(true);
            //Click listener for each row
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tableRow = (TableRow) view;
                    selectedRow = tableRow;
                    //If the row is already selected (in red) deselect it
                    TextView textViewCheck = (TextView) tableRow.getChildAt(0);
                    if(selection && textViewCheck.getText().toString().equals(selectedBook.getName()))
                    {
                        for(int i = 0; i < 4; i++)
                        {
                            TextView textView = (TextView) tableRow.getChildAt(i);
                            textView.setBackground(gd);
                        }
                        //Disable add and modify buttons when row is deselected
                        modifyBtn.setEnabled(false);
                        deleteBtn.setEnabled(false);
                        selection = false;
                        selectedBook = null;
                    }
                    //If the row is not in red check if there is already a selected row
                    else
                    {
                        if(selection)
                        {
                            String message = "Please select only one";
                            Toast.makeText(getContext(), message, LENGTH_SHORT).show();
                        }
                        else
                        {
                            for(int i = 0; i < 4; i++)
                            {
                                TextView textView = (TextView) tableRow.getChildAt(i);
                                textView.setBackgroundColor(Color.RED);
                            }
                            //Enable add and modify buttons when row is selected
                            modifyBtn.setEnabled(true);
                            deleteBtn.setEnabled(true);
                            TextView nameView = (TextView) tableRow.getChildAt(0);
                            selectedBook = storage.findBook(nameView.getText().toString());
                            selection = true;
                        }
                    }
                }
            });
            table.addView(row);
        }
    }
}
