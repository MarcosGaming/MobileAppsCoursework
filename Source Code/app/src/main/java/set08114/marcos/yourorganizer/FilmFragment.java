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
 * Activity that shows the list of films stored and allows the user to perform different types of operations with them.
 * Last modified date : 10/03/2018.
 */

public class FilmFragment extends Fragment{

    InternalStorage storage;
    Film selectedFilm;
    Boolean selection;
    TableRow selectedRow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.films_fragment,container,false);
        storage = InternalStorage.getInstance(this.getContext());
        final Button modifyBtn = view.findViewById(R.id.modifyBtnFilm);
        final Button deleteBtn = view.findViewById(R.id.deleteBtnFilm);
        //Disable modify and delete buttons
        modifyBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        //Default values
        selection = false;
        selectedRow = null;
        final TableLayout table = view.findViewById(R.id.film_tableLayout);
        this.initializeFilmTable(storage.getFilmList(),view, modifyBtn, deleteBtn);
        Button addBtn = view.findViewById(R.id.addBtnFilm);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddModifyFilm.class);
                startActivity(intent);
            }
        });
        modifyBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddModifyFilm.class);
                //Passing the selected film to the AddModifyFilm activity
                intent.putExtra("selectedFilm",selectedFilm);
                startActivity(intent);
                selectedFilm = null;
                selection = false;
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.removeFilm(selectedFilm);
                selectedFilm = null;
                selection = false;
                modifyBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                table.removeView(selectedRow);
            }
        });
        if(!this.isVisible())
        {
            selectedFilm = null;
            selection = false;
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final TableLayout table = getView().findViewById(R.id.film_tableLayout);
        table.removeAllViewsInLayout();
        final Button modifyBtn = getView().findViewById(R.id.modifyBtnFilm);
        final Button deleteBtn = getView().findViewById(R.id.deleteBtnFilm);
        initializeFilmTable(storage.getFilmList(),getView(),modifyBtn,deleteBtn);
    }
    @Override
    public void onPause() {
        super.onPause();
        storage.storeLists();
    }

    private void initializeFilmTable(final List<Film> filmList, View view, final Button modifyBtn, final Button deleteBtn)
    {
        //Get screen width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        TableLayout table =  view.findViewById(R.id.film_tableLayout);
        //Background for the name text view
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(getResources().getColor(R.color.colorTeal));
        gd.setCornerRadius(2);
        gd.setStroke(2, 0xFF000000);
        //Background for release date and status text views
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
        headerName.setText(getResources().getString(R.string.film_NAME));
        headerName.setBackground(gd);
        headerName.setPadding(20,0,20,0);
        headerName.setTypeface(font);
        headerName.setTextSize(12.5f);
        headerName.setTextColor(Color.BLACK);
        headerName.setMinWidth(screenWidth/3);
        headerName.setLayoutParams(textViewParams);
        headerRow.addView(headerName);
        //Header release date text view
        TextView headerReleaseDate = new TextView(this.getContext());
        headerReleaseDate.setText(getResources().getString(R.string.film_RELEASE));
        headerReleaseDate.setBackground(gd2);
        headerReleaseDate.setPadding(20,0,20,0);
        headerReleaseDate.setTypeface(font);
        headerReleaseDate.setTextSize(12.5f);
        headerReleaseDate.setTextColor(Color.BLACK);
        headerReleaseDate.setMinWidth(screenWidth/3);
        headerReleaseDate.setLayoutParams(textViewParams);
        headerRow.addView(headerReleaseDate);
        //Header status text view
        TextView headerStatus = new TextView(this.getContext());
        headerStatus.setText(getResources().getString(R.string.film_STATUS));
        headerStatus.setBackground(gd2);
        headerStatus.setPadding(20,0,20,0);
        headerStatus.setTypeface(font);
        headerStatus.setTextSize(12.5f);
        headerStatus.setTextColor(Color.BLACK);
        headerStatus.setMinWidth(screenWidth/3);
        headerStatus.setLayoutParams(textViewParams);
        headerRow.addView(headerStatus);
        //Add row to table
        table.addView(headerRow);
        for(int i = 0; i < filmList.size(); i++)
        {
            //New table row
            TableRow row = new TableRow(this.getContext());
            //Set text views properties
            TextView name = new TextView(this.getContext());
            name.setText(filmList.get(i).getName());
            name.setPadding(20,0,20,0);
            name.setLayoutParams(textViewParams);
            name.setBackground(gd);
            TextView releaseDate = new TextView(this.getContext());
            releaseDate.setText(filmList.get(i).getReleaseDate());
            releaseDate.setPadding(20,0,20,0);
            releaseDate.setLayoutParams(textViewParams);
            releaseDate.setBackground(gd2);
            TextView status = new TextView(this.getContext());
            status.setText(filmList.get(i).getStatus());
            status.setPadding(20,0,20,0);
            status.setLayoutParams(textViewParams);
            status.setBackground(gd2);
            //Add text views to the row
            row.addView(name);
            row.addView(releaseDate);
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
                    if(selection && textViewCheck.getText().toString().equals(selectedFilm.getName()))
                    {
                        for(int i = 0; i < 3; i++)
                        {
                            TextView textView = (TextView) tableRow.getChildAt(i);
                            textView.setBackground(gd);
                        }
                        //Disable add and modify buttons when row is deselected
                        modifyBtn.setEnabled(false);
                        deleteBtn.setEnabled(false);
                        selection = false;
                        selectedFilm = null;
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
                            for(int i = 0; i < 3; i++)
                            {
                                TextView textView = (TextView) tableRow.getChildAt(i);
                                textView.setBackgroundColor(Color.RED);
                            }
                            //Enable add and modify buttons when row is selected
                            modifyBtn.setEnabled(true);
                            deleteBtn.setEnabled(true);
                            TextView nameView = (TextView) tableRow.getChildAt(0);
                            selectedFilm = storage.findFilm(nameView.getText().toString());
                            selection = true;
                        }
                    }
                }
            });
            table.addView(row);
        }
    }
}
