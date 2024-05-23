package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.safe_keep.services.MessageService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private FirebaseAuth auth;
    private TextView textView;
    private FirebaseUser user;
    private Button buttonLogout;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private Map<Integer, Class<?>> activityMap = new HashMap<>();
    private TextView dateView;
    private Button buttonTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.monthYearTV);
        buttonLogout = findViewById(R.id.logout);
        dateView = findViewById(R.id.date);

        user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        buttonLogout.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });


        activityMap.put(R.id.bottom_home, MainActivity.class);
        activityMap.put(R.id.bottom_search, SearchActivity.class);
        activityMap.put(R.id.bottom_settings, SettingsActivity.class);
        activityMap.put(R.id.bottom_profile, ProfileActivity.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Class<?> activityClass = activityMap.get(item.getItemId());
            if (activityClass != null) {
                startActivity(new Intent(getApplicationContext(), activityClass));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        dateView.setOnClickListener(view -> {
            showNewDateDialog();
        });

        // Start the service
        startService(new Intent(getApplicationContext(), MessageService.class));
    }

    private void showNewDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.new_date, null);
        builder.setView(dialogView);

        EditText datePicker = dialogView.findViewById(R.id.location);
        CheckBox checkBox1 = dialogView.findViewById(R.id.checkBox1);
        CheckBox checkBox2 = dialogView.findViewById(R.id.checkBox2);
        Button btnResetRadius = dialogView.findViewById(R.id.btnResetRadius); // Locate the btnResetRadius button
        Button btnCallMe = dialogView.findViewById(R.id.btnCallMe); // Locate the btnResetRadius button

        // Automatically check the CheckBox
        checkBox1.setChecked(true);
        checkBox2.setChecked(true);

        // Set OnClickListener for the btnResetRadius button
        btnResetRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate the user to the MapsActivity
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity if needed
            }
        });

        btnCallMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate the user to the MapsActivity
                Intent intent = new Intent(getApplicationContext(), DoYouWantFakeCall.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity if needed
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            String selectedDateStr = datePicker.getText().toString();
            // Convert the selected date string to LocalDate format
            LocalDate selectedDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // Update the selectedDate and refresh the calendar view
            this.selectedDate = selectedDate;
            setMonthView();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }





    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null);
            } else {
                daysInMonthArray.add(firstOfMonth.plusDays(i - dayOfWeek - 1));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            String message = "Selected Date " + date.getDayOfMonth() + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
}
