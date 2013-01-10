package org.aerogear.otp.android.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import org.aerogear.android.Callback;
import org.aerogear.android.http.HeaderAndBody;

public class OTPLoginActivity extends SherlockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                OTPApplication application = (OTPApplication) getApplication();
                application.login(user, pass, new Callback<HeaderAndBody>() {
                    @Override
                    public void onSuccess(HeaderAndBody data) {
                        Toast.makeText(OTPLoginActivity.this, "Ok. You are logged :)", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("Login", "An error occurrence", e);
                        Toast.makeText(OTPLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



}
