package pl.kelooystore.healthdiagnostics;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityBaza activityBaza = new ActivityBaza();
        database = activityBaza.getBase();
        database = openOrCreateDatabase("PATIENTS",MODE_PRIVATE,null);
        String sqlDiagnostics = "CREATE TABLE IF NOT EXISTS PATIENTS(Id INTEGER, Imie VARCHAR, Nazwisko VARCHAR, Wyniki VARCHAR)";
        database.execSQL(sqlDiagnostics);
    }


    public void onClickShow(View view) {

        ArrayList<String> records = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Id, Imie, Nazwisko, Wyniki FROM PATIENTS", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("Id"));
                String imie = cursor.getString(cursor.getColumnIndex("Imie"));
                String nazwisko = cursor.getString(cursor.getColumnIndex("Nazwisko"));
                String wyniki = cursor.getString(cursor.getColumnIndex("Wyniki"));

                records.add("ID: " + id + ", Imię: " + imie + ", Nazwisko: " + nazwisko + ", Wyniki: " + wyniki);

            } while (cursor.moveToNext());
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(adapter);
        cursor.close();
    }


    public void onClickAdd(View view) {
        Intent intent = new Intent(this, ActivityBaza.class);
        startActivity(intent);
    }


    public void onClickDelete(View view) {
        dialogDelete();
    }


    public void dialogDelete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Please confirm");
        builder.setMessage("Are you sure that you want to delete database?");

        builder.setPositiveButton("    NO    ", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("    YES    ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteData();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void deleteData()
    {
        String sqlDoesNotExist = "DELETE FROM PATIENTS WHERE id IS NOT NULL";
        database.execSQL(sqlDoesNotExist);
    }
}
