package com.example.simplealarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAlarm;
    Button alarmkapat;
    int yilim;
    int ayim;
    int gunum;
    int saatim;
    int dakikam;
    int i=0;
    TextView alarm;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    final static int islem_kodu = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmkapat = findViewById(R.id.kapat);
        btnAlarm = findViewById(R.id.btnAlarmAyarla);
        alarm = findViewById(R.id.alarm);
        btnAlarm.setOnClickListener(this);
    }

    public void onClick(View view) {
        openPickerDialog(false);
    }

    private void openPickerDialog(boolean tumgunsaat) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(MainActivity.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), tumgunsaat);
        timePickerDialog.setTitle("Alarm Ayarla");
        timePickerDialog.show();
        datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Gun Ayarla");
        datePickerDialog.show();

    }

    DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, ay, gun) -> {
        yilim = year;
        ayim = ay;
        gunum = gun;

        System.out.println(gunum);
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, saat, dakika) -> {
        saatim = saat;
        dakikam = dakika;
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.YEAR, yilim);
        calSet.set(Calendar.MONTH, ayim);
        calSet.set(Calendar.DAY_OF_MONTH, gunum);
        calSet.set(Calendar.HOUR_OF_DAY, saatim);
        calSet.set(Calendar.MINUTE, dakikam);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }
        setAlarm(calSet);
    };


    private void setAlarm(Calendar alarmCalendar) {
        String alarmsaat = gunum + "." + ayim + "." + yilim + " | " + saatim + ":" + dakikam;
        alarm.setText(alarmsaat);
        System.out.println(gunum);
        Toast.makeText(this, "Alarm ayarlandı!", Toast.LENGTH_SHORT).show();
        Intent intenttt = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), i, intenttt, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
        intentArray.add(pendingIntent);
        i++;
        System.out.println(i);
        System.out.println(intentArray);
    }


    public void alarmikapat(View view) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intenttt = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), i, intenttt, 0);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        alarmManager.cancel(intentArray.get(i));
    }
}
