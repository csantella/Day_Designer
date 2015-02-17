package com.muamone.sunrise.daydesigner;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class DayView extends ActionBarActivity {

    CalendarView calendar;

    Calendar c = Calendar.getInstance();

    private static int selectedDay;
    private static int selectedMonth;
    private static int selectedYear;
    private static long date;
    private static boolean dayMoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        Globals.today = new int[3]; //initialize today array

        Globals.today[0] = c.get(Calendar.DAY_OF_MONTH); Globals.today[1] = c.get(Calendar.MONTH) + 1; Globals.today[2] = c.get(Calendar.YEAR);

        c.set(Calendar.DATE,Calendar.getInstance().getActualMinimum(Calendar.DATE));
/*
        //The below snippet converts today's date into milliseconds so the calendar can default to today


        c.set(Calendar.YEAR, Globals.today[2]);
        c.set(Calendar.MONTH, Globals.today[1]);
        c.set(Calendar.DAY_OF_MONTH, Globals.today[0]);

        calendar.setDate(c.getTimeInMillis(),true,true); //convert data to millis

        selectedDay = Globals.today[0];
        selectedMonth = Globals.today[1]-1;
        selectedYear = Globals.today[2];

        date = c.getTime().getTime();
        calendar.setMinDate(date);
*/
        calendar.setMaxDate(2524543200000l);    //Day is 12/31/2059
        calendar.setMinDate(946706400000l);     //Day is 01/01/2000

/*
        calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // calendar clicked
                Toast.makeText(getApplicationContext(),"calendarClicked",Toast.LENGTH_SHORT).show();
                if (!dayMoved)
                {
                    Toast.makeText(getApplicationContext(),"dayMoved=false\nStarting Day activity.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Day.class);
                    startActivity(i);
                }
            }
        });
*/
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                boolean launchActivity=true;
                dayMoved = true;

                Globals.day = day;
                Globals.month = month + 1;  //January is 0 for some reason
                Globals.year = year;

                if (selectedDay==0)
                {
                    launchActivity = false;
                }

                if (selectedDay==day)
                {
                    if (selectedMonth==month)
                    {
                        if (selectedYear==year)
                        {
                            //dates are the same
                            launchActivity = false;
                        }
                    }
                }

                selectedDay = day;
                selectedMonth = month;
                selectedYear = year;

                if (launchActivity)
                {
                    Intent i = new Intent(getApplicationContext(), Day.class);
                    dayMoved = false;
                    startActivity(i);
                    //onStop();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_view, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("selectedDay", selectedDay);
        savedInstanceState.putInt("selectedMonth",selectedMonth);
        savedInstanceState.putInt("selectedYear",selectedYear);

        // etc.
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        selectedDay = savedInstanceState.getInt("selectedDay");
        selectedMonth = savedInstanceState.getInt("selectedMonth");
        selectedYear = savedInstanceState.getInt("selectedYear");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
