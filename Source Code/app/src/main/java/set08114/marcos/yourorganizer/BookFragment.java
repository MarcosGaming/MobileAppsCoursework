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
        final Button modifyBtn = view.findViewById(R.id.modifyBtnBook);
        final Button deleteBtn = view.findViewById(R.id.deleteBtnBook);
        //Disable modify and delete buttons
        modifyBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        this.initializeBookTable(storage.getBookList(),view, modifyBtn, deleteBtn);
        Button addBtn = view.findViewById(R.id.addBtnBook);
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
        if(!this.isVisible())
        {
            selectedBook = null;
            selection = false;
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final TableLayout table = getView().findViewById(R.id.book_tableLayout);
        table.removeAllViewsInLayout();
        final Button modifyBtn = getView().findViewById(R.id.modifyBtnBook);
        final Button deleteBtn = getView().findViewById(R.id.deleteBtnBook);
        initializeBookTable(storage.getBookList(),getView(),modifyBtn,deleteBtn);
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
        //Background for the name text view
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(getResources().getColor(R.color.colorTeal));
        gd.setCornerRadius(2);
        gd.setStroke(2, 0xFF000000);
        //Background for chapter, page and status text views
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setColor(getResources().getColor(R.color.colorTeal));
        gd2.setCornerRadius(2);
        gd2.setStroke(2, 0xFF000000);
        //Font for the text views
        Typeface font = Typeface.create("casual",Typeface.BOLD);
        //Text view parameters
        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(1,1,1,1);
        //Header row
        TableRow headerRow = new TableRow(this.getContext());
        //Header name text view
        TextView headerName = new TextView(this.getContext());
        headerName.setText(getResources().getString(R.string.book_NAME));
        headerName.setBackground(gd);
        headerName.setPadding(20,0,20,0);
        headerName.setTypeface(font);
        headerName.setTextSize(12.5f);
        headerName.setTextColor(Color.BLACK);
        headerName.setMinWidth(screenWidth/4);
        headerName.setLayoutParams(textViewParams);
        headerRow.addView(headerName);
        //Header chapter text view
        TextView headerChapter = new TextView(this.getContext());
        headerChapter.setText(getResources().getString(R.string.book_CHAPTER));
        headerChapter.setBackground(gd2);
        headerChapter.setPadding(20,0,20,0);
        headerChapter.setTypeface(font);
        headerChapter.setTextSize(12.5f);
        headerChapter.setTextColor(Color.BLACK);
        headerChapter.setMinWidth(screenWidth/4);
        headerChapter.setLayoutParams(textViewParams);
        headerRow.addView(headerChapter);
        //Header page text view
        TextView headerPage = new TextView(this.getContext());
        headerPage.setText(getResources().getString(R.string.book_PAGE));
        headerPage.setBackground(gd2);
        headerPage.setPadding(20,0,20,0);
        headerPage.setTypeface(font);
        headerPage.setTextSize(12.5f);
        headerPage.setTextColor(Color.BLACK);
        headerPage.setMinWidth(screenWidth/4);
        headerPage.setLayoutParams(textViewParams);
        headerRow.addView(headerPage);
        //Header name text view
        TextView headerStatus = new TextView(this.getContext());
        headerStatus.setText(getResources().getString(R.string.book_STATUS));
        headerStatus.setBackground(gd2);
        headerStatus.setPadding(20,0,20,0);
        headerStatus.setTypeface(font);
        headerStatus.setTextSize(12.5f);
        headerStatus.setTextColor(Color.BLACK);
        headerStatus.setMinWidth(screenWidth/4);
        headerStatus.setLayoutParams(textViewParams);
        headerRow.addView(headerStatus);
        //Add row to table
        table.addView(headerRow);
        for(int i = 0; i < bookList.size(); i++)
        {
            //New table row
            TableRow row = new TableRow(this.getContext());
            //Set text views properties
            TextView name = new TextView(this.getContext());
            name.setText(bookList.get(i).getName());
            name.setPadding(20,0,20,0);
            name.setLayoutParams(textViewParams);
            name.setBackground(gd);
            TextView chapter = new TextView(this.getContext());
            chapter.setText(bookList.get(i).getChapter());
            chapter.setPadding(20,0,20,0);
            chapter.setLayoutParams(textViewParams);
            chapter.setBackground(gd2);
            TextView page = new TextView(this.getContext());
            page.setText(bookList.get(i).getPage());
            page.setPadding(20,0,20,0);
            page.setLayoutParams(textViewParams);
            page.setBackground(gd2);
            TextView status = new TextView(this.getContext());
            status.setText(bookList.get(i).getStatus());
            status.setPadding(20,0,20,0);
            status.setLayoutParams(textViewParams);
            status.setBackground(gd2);
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
