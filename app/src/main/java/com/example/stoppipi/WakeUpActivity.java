package com.byanhor.stoppipi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class WakeUpActivity extends AppCompatActivity {

    HorizontalCalendar horizontalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        // start 2 months ago from now
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -10);
        // End after 2 months from now
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.horizontalCalendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .configure()
                    .formatTopText("EEE")
                    .formatMiddleText("d")
                    .formatBottomText("MMMM")
                    .showTopText(false)
                    .showBottomText(false)
                    .textColor(Color.LTGRAY, Color.LTGRAY)
                    .selectedDateBackground(new ColorDrawable(getResources().getColor(R.color.colorAccent)))
                    .selectorColor(getResources().getColor(R.color.colorWhite))
                    .sizeTopText(getResources().getDimension(R.dimen.text_small))
                    .sizeMiddleText(getResources().getDimension(R.dimen.text_medium))
                    .sizeBottomText(getResources().getDimension(R.dimen.text_small))
                    .colorTextTop(Color.LTGRAY, Color.LTGRAY)
                    .colorTextMiddle(Color.LTGRAY, Color.LTGRAY)
                    .colorTextBottom(Color.LTGRAY, Color.LTGRAY)
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();

        setSelectedDateSimpleDateFormatFR(defaultSelectedDate);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                setSelectedDateSimpleDateFormatFR(date);
            }
        });

        TextView textViewDateFR = findViewById(R.id.textViewDateFR);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Signatra.ttf");
        textViewDateFR.setTypeface(face);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalCalendar.goToday(false);
            }
        });

        //myCalendar = (CalendarView)findViewById(R.id.calendarView);
        /*int daysInMonth = .getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.setMaxDate(daysInMonth);
        /*myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int y, int m, int d) {

            }
        });*/

    }

    public void setSelectedDateSimpleDateFormatFR(Calendar date) {
        String selectedDateCalendarStr = DateFormat.format("EEEE-dd-MMMM-yyyy", date).toString();
        SimpleDateFormat smfEN = new SimpleDateFormat("EEEE-dd-MMMM-yyyy", Locale.ENGLISH);
        Date selectedDateSimpleDateFormatEN = null;
        try {
            selectedDateSimpleDateFormatEN = smfEN.parse(selectedDateCalendarStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat smfFR = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH);
        String selectedDateSimpleDateFormatFR = smfFR.format(selectedDateSimpleDateFormatEN);
        TextView textViewDateFR = findViewById(R.id.textViewDateFR);
        selectedDateSimpleDateFormatFR = selectedDateSimpleDateFormatFR.substring(0,1).toUpperCase() + selectedDateSimpleDateFormatFR.substring(1);
        textViewDateFR.setText(selectedDateSimpleDateFormatFR);
        AsynchReadWriteURL mTask = new AsynchReadWriteURL(this, findViewById(android.R.id.content), selectedDateSimpleDateFormatFR);
        mTask.execute("abc","10","Hello world");
    }
}


