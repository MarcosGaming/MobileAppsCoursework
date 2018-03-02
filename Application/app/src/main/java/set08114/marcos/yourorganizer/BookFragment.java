package set08114.marcos.yourorganizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
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
 */

public class BookFragment extends Fragment{

    InternalStorage storage;
    Book selectedBook;
    Boolean selection = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.books_fragment,container,false);
        storage = InternalStorage.getInstance(this.getContext());
        this.initializeBookTable(storage.getBookList(),view);
        Button addBtn = view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddModifyBook.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initializeBookTable(final List<Book> bookList, View view)
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
        headerName.setText("NAME");
        headerName.setBackground(gd);
        headerName.setPadding(20,0,20,0);
        headerName.setTypeface(font);
        headerName.setTextColor(Color.BLACK);
        headerName.setWidth(screenWidth/4);
        headerRow.addView(headerName);
        //Header chapter text view
        TextView headerChapter = new TextView(this.getContext());
        headerChapter.setText("CHAPTER");
        headerChapter.setBackground(gd);
        headerChapter.setPadding(20,0,20,0);
        headerChapter.setTypeface(font);
        headerChapter.setTextColor(Color.BLACK);
        headerChapter.setWidth(screenWidth/4);
        headerRow.addView(headerChapter);
        //Header page text view
        TextView headerPage = new TextView(this.getContext());
        headerPage.setText("PAGE");
        headerPage.setBackground(gd);
        headerPage.setPadding(20,0,20,0);
        headerPage.setTypeface(font);
        headerPage.setTextColor(Color.BLACK);
        headerPage.setWidth(screenWidth/4);
        headerRow.addView(headerPage);
        //Header name text view
        TextView headerStatus = new TextView(this.getContext());
        headerStatus.setText("STATUS");
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
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
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
                    //If the row is already selected (in red) deselect it
                    TextView textViewCheck = (TextView) tableRow.getChildAt(0);
                    GradientDrawable cd = (GradientDrawable) textViewCheck.getBackground();
                    Log.i("check color","ok");
                    if(cd.getColor().getDefaultColor() == Color.RED)
                    {
                        Log.i("check color","ok2");
                        for(int i = 0; i < 4; i++)
                        {
                            TextView textView = (TextView) tableRow.getChildAt(i);
                            textView.setBackground(gd);
                            Log.i("check color","ok3");
                        }
                        selection = false;
                        selectedBook = null;
                        Log.i("check color","ok4");
                    }
                    //If the row is not in red check if there is already a selected row
                    else
                    {
                        if(selection == true)
                        {
                            String message = "Please select only one";
                            Toast.makeText(getContext(), message, LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            for(int i = 0; i < 4; i++)
                            {
                                TextView textView = (TextView) tableRow.getChildAt(i);
                                textView.setBackgroundColor(Color.RED);
                            }
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
