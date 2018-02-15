package set08114.marcos.yourorganizer;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marcos on 11/02/2018.
 */

public class BookFragment extends Fragment{

    List<String> listExample = Arrays.asList("Hello","my","name","is","marcosSSSSSSSSSSSSS");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.books_fragment,container,false);
        this.initializeBookTable(listExample,view);
        return view;
    }

    private void initializeBookTable(List<String> listExample,View view)
    {
        TableLayout table =  view.findViewById(R.id.book_tableLayout);
        //Border for the text views
        GradientDrawable gd = new GradientDrawable();
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
        headerRow.addView(headerName);
        //Header chapter text view
        TextView headerChapter = new TextView(this.getContext());
        headerChapter.setText("CHAPTER");
        headerChapter.setBackground(gd);
        headerChapter.setPadding(20,0,20,0);
        headerChapter.setTypeface(font);
        headerChapter.setTextColor(Color.BLACK);
        headerRow.addView(headerChapter);
        //Header page text view
        TextView headerPage = new TextView(this.getContext());
        headerPage.setText("PAGE");
        headerPage.setBackground(gd);
        headerPage.setPadding(20,0,20,0);
        headerPage.setTypeface(font);
        headerPage.setTextColor(Color.BLACK);
        headerRow.addView(headerPage);
        //Header name text view
        TextView headerStatus = new TextView(this.getContext());
        headerStatus.setText("STATUS");
        headerStatus.setBackground(gd);
        headerStatus.setPadding(20,0,20,0);
        headerStatus.setTypeface(font);
        headerStatus.setTextColor(Color.BLACK);
        headerRow.addView(headerStatus);
        //Add row to table
        table.addView(headerRow);
        for(int i = 0; i < listExample.size();i++)
        {
            TableRow row = new TableRow(this.getContext());
            //Set row parameters
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParams.setMargins(0,1,0,1);
            row.setLayoutParams(tableRowParams);
            //Set text views parameters
            TextView name = new TextView(this.getContext());
            name.setText(listExample.get(i));
            name.setBackground(gd);
            name.setPadding(20,0,20,0);
            TextView chapter = new TextView(this.getContext());
            chapter.setText(listExample.get(i));
            chapter.setBackground(gd);
            chapter.setPadding(20,0,20,0);
            TextView page = new TextView(this.getContext());
            page.setText(listExample.get(i));
            page.setBackground(gd);
            page.setPadding(20,0,20,0);
            TextView status = new TextView(this.getContext());
            status.setText(listExample.get(i));
            status.setBackground(gd);
            status.setPadding(20,0,20,0);
            //Add text views to the row
            row.addView(name);
            row.addView(chapter);
            row.addView(page);
            row.addView(status);
            table.addView(row);
        }
    }
}
