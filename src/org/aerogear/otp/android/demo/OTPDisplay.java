package org.aerogear.otp.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.jboss.aerogear.android.Callback;

import static android.view.View.OnClickListener;

public class OTPDisplay extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        final OTPApplication application = (OTPApplication) getApplication();

//        application.retrieveSecretKey();

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                application.logout(new Callback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        finish();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("Logout", "An error occurrence", e);
                        Toast.makeText(OTPDisplay.this, "Logout failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
    }
}
