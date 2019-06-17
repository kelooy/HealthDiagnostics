package pl.kelooystore.healthdiagnostics;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

public class ActivityBaza extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dpd;
    SQLiteDatabase base;
    int i=0; // ID iterator


    public SQLiteDatabase getBase() {
        return base;
    }


    public void onClickReturn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void onClickAdd(View view) {
        Random iter = new Random();
        i=iter.nextInt(100000); // Nadawanie ID
        addUser();
    }


    public void addUser() {
        base = openOrCreateDatabase("PATIENTS", MODE_PRIVATE, null);
        try {
            String sqlDiagnostics = "CREATE TABLE IF NOT EXISTS PATIENTS (Id INTEGER, Imie VARCHAR, Nazwisko VARCHAR, Wyniki VARCHAR)";
            base.execSQL(sqlDiagnostics);

            String sqlCount = "SELECT count(*) FROM PATIENTS";
            Cursor cursor = base.rawQuery(sqlCount, null);
            cursor.moveToLast();
            cursor.close();

            EditText name = (EditText) findViewById(R.id.imie);
            EditText surname = (EditText) findViewById(R.id.nazwisko);
            TextView wyniki = (TextView) findViewById(R.id.WYNIK);

            String sqlPacjent = "INSERT INTO PATIENTS VALUES(?,?,?,?)";
            SQLiteStatement statement = base.compileStatement(sqlPacjent);
            statement.bindLong(1, i); // ID
            statement.bindString(2, name.getText().toString());
            statement.bindString(3, surname.getText().toString());
            statement.bindString(4, wyniki.getText().toString());
            statement.executeInsert();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            base.close();
        }
    }


    public void onClickCount(View view) {
        TextView WYNIKI = (TextView) findViewById(R.id.WYNIK);

        Spinner spinner01 = (Spinner) findViewById(R.id.spinnerWzrost);
        Spinner spinner02 = (Spinner) findViewById(R.id.spinnerPPM);
        Spinner spinner03 = (Spinner) findViewById(R.id.spinnerPlec);

        String jednostkaMiary = String.valueOf(spinner01.getSelectedItem());
        String rodzajPPM = String.valueOf(spinner02.getSelectedItem());
        String plec = String.valueOf(spinner03.getSelectedItem());

        EditText poleWzrost = (EditText) findViewById(R.id.wzrost);
        EditText poleMasa = (EditText) findViewById(R.id.masa);
        EditText poleTalia = (EditText) findViewById(R.id.talia);


        try {   // EKSPERTYZA // DIAGNOZA //
            double wzrost = Double.valueOf(poleWzrost.getText().toString());
            double talia = Double.valueOf(poleTalia.getText().toString());
            double masa = Double.valueOf(poleMasa.getText().toString());
            double BMI = 0;
            double PPM = 0;
            double FAT = 0;

            //BMI
            if (jednostkaMiary.equals("m")) {
                BMI = pl.kelooystore.healthdiagnostics.BMI.bodyMassIndex(masa, wzrost);
            } else {
                BMI = pl.kelooystore.healthdiagnostics.BMI.bodyMassIndex(masa, (wzrost / 100));
            }

            //FAT-level
            if (jednostkaMiary.equals("m")){
                FAT = pl.kelooystore.healthdiagnostics.FAT.bodyFat(masa, (talia*100), plec);
            } else {
                FAT = pl.kelooystore.healthdiagnostics.FAT.bodyFat(masa, talia, plec);
            }

            //PPM
            if (rodzajPPM.equals("Benedicta-Harrisa")) {
                if (jednostkaMiary.equals("m")) {
                   wzrost *= 100;
                }
               PPM = pl.kelooystore.healthdiagnostics.PPM.BenedictaHarrisa(masa, wzrost, (2019 - dpd.getDatePicker().getYear()), plec);
            } else {
                if (jednostkaMiary.equals("m")) {
                    wzrost *= 100; }
                PPM = pl.kelooystore.healthdiagnostics.PPM.Mifflina(masa, wzrost, (2019 - dpd.getDatePicker().getYear()), plec);
            }

            DecimalFormat dF = new DecimalFormat("#.#");
            WYNIKI.setText("BMI = " +String.valueOf(dF.format(BMI)) +"  PPM = " +String.valueOf(dF.format(PPM)) +"kcal" +"  FAT = " +String.valueOf(dF.format(FAT)) +"%");

        } catch (NumberFormatException e) {
            WYNIKI.setText("Co najmniej jedno pole jest niepoprawnie wypełnione!");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza);

        Button btnPickDate = (Button) findViewById(R.id.buttonPickDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(ActivityBaza.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                    }
                }, day, month, year);
                dpd.show();
            }
        });
    }
}