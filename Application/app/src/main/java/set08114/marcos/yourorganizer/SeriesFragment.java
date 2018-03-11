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
 * Activity that shows the list of series stored and allows the user to perform different types of operations with them.
 * Last modified date : 10/03/2018.
 */

public class SeriesFragment extends Fragment
{

    InternalStorage storage;
    Series selectedSeries;
    Boolean selection = false;
    TableRow selectedRow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.series_fragment,container,false);
        storage = InternalStorage.getInstance(this.getContext());
        final Button modifyBtn = view.findViewById(R.id.modifyBtnSeries);
        final Button deleteBtn = view.findViewById(R.id.deleteBtnSeries);
        //Disable modify and delete buttons
        modifyBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        this.initializeSeriesTable(storage.getSeriesList(),view, modifyBtn, deleteBtn);
        Button addBtn = view.findViewById(R.id.addBtnSeries);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddModifySeries.class);
                startActivity(intent);
            }
        });
        modifyBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddModifySeries.class);
                //Passing the selected series to the AddModifySeries activity
                intent.putExtra("selectedSeries",selectedSeries);
                startActivity(intent);
                selectedSeries = null;
                selection = false;
            }
        });
        final TableLayout table = view.findViewById(R.id.series_tableLayout);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.removeSeries(selectedSeries);
                selectedSeries = null;
                selection = false;
                modifyBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                table.removeView(selectedRow);
                table.setVisibility(View.GONE);
                table.setVisibility(View.VISIBLE);
            }
        });
        if(!this.isVisible())
        {
            selectedSeries = null;
            selection = false;
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        storage.storeLists();
    }

    private void initializeSeriesTable(final List<Series> seriesList, View view, final Button modifyBtn, final Button deleteBtn)
    {
        //Get screen width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        TableLayout table =  view.findViewById(R.id.series_tableLayout);
        //Background for the name text view
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(getResources().getColor(R.color.colorTeal));
        gd.setCornerRadius(2);
        gd.setStroke(2, 0xFF000000);
        //Background for season, chapter and status text views
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
        headerName.setText(getResources().getString(R.string.series_NAME));
        headerName.setBackground(gd);
        headerName.setPadding(20,0,20,0);
        headerName.setTypeface(font);
        headerName.setTextSize(12.5f);
        headerName.setTextColor(Color.BLACK);
        headerName.setMinWidth(screenWidth/4);
        headerName.setLayoutParams(textViewParams);
        headerRow.addView(headerName);
        //Header season text view
        TextView headerSeason = new TextView(this.getContext());
        headerSeason.setText(getResources().getString(R.string.series_SEASON));
        headerSeason.setBackground(gd2);
        headerSeason.setPadding(20,0,20,0);
        headerSeason.setTypeface(font);
        headerSeason.setTextSize(12.5f);
        headerSeason.setTextColor(Color.BLACK);
        headerSeason.setMinWidth(screenWidth/4);
        headerSeason.setLayoutParams(textViewParams);
        headerRow.addView(headerSeason);
        //Header episode text view
        TextView headerEpisode = new TextView(this.getContext());
        headerEpisode.setText(getResources().getString(R.string.series_EPISODE));
        headerEpisode.setBackground(gd2);
        headerEpisode.setPadding(20,0,20,0);
        headerEpisode.setTypeface(font);
        headerEpisode.setTextSize(12.5f);
        headerEpisode.setTextColor(Color.BLACK);
        headerEpisode.setMinWidth(screenWidth/4);
        headerEpisode.setLayoutParams(textViewParams);
        headerRow.addView(headerEpisode);
        //Header status text view
        TextView headerStatus = new TextView(this.getContext());
        headerStatus.setText(getResources().getString(R.string.series_STATUS));
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
        for(int i = 0; i < seriesList.size(); i++)
        {
            //New table row
            TableRow row = new TableRow(this.getContext());
            //Set text views properties
            TextView name = new TextView(this.getContext());
            name.setText(seriesList.get(i).getName());
            name.setPadding(20,0,20,0);
            name.setLayoutParams(textViewParams);
            name.setBackground(gd);
            TextView season = new TextView(this.getContext());
            season.setText(seriesList.get(i).getSeason());
            season.setPadding(20,0,20,0);
            season.setLayoutParams(textViewParams);
            season.setBackground(gd2);
            TextView episode = new TextView(this.getContext());
            episode.setText(seriesList.get(i).getEpisode());
            episode.setPadding(20,0,20,0);
            episode.setLayoutParams(textViewParams);
            episode.setBackground(gd2);
            TextView status = new TextView(this.getContext());
            status.setText(seriesList.get(i).getStatus());
            status.setPadding(20,0,20,0);
            status.setLayoutParams(textViewParams);
            status.setBackground(gd2);
            //Add text views to the row
            row.addView(name);
            row.addView(season);
            row.addView(episode);
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
                    if(selection && textViewCheck.getText().toString().equals(selectedSeries.getName()))
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
                        selectedSeries = null;
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
                            selectedSeries = storage.findSeries(nameView.getText().toString());
                            selection = true;
                        }
                    }
                }
            });
            table.addView(row);
        }
    }
}
