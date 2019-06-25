package pl.kelooystore.healthdiagnostics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity
{
    final String truePIN = "1234";
    int attempt = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onClickConfirm(View view) {
        EditText polePIN = (EditText) findViewById(R.id.haslo);
        TextView poleLicznik = (TextView) findViewById(R.id.licznik);
        String enteredPIN = String.valueOf(polePIN.getText().toString());

        if(enteredPIN.equals(truePIN)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            polePIN.setText("");
            loginToast();
            finish();
        } else {
            --attempt;
            polePIN.setText("");
            poleLicznik.setText(attempt + " attempts left...");
            if(attempt==0) finish();
        }
    }


     private void loginToast()
     {
         CharSequence text = "Logged in!";
         int duration = Toast.LENGTH_LONG;
         Context context = getApplicationContext();

         Toast toast = Toast.makeText(context, text, duration);
         toast.show();
     }
}
