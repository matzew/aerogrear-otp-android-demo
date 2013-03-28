package org.aerogear.otp.android.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.http.HeaderAndBody;

public class OTPLoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                OTPApplication application = (OTPApplication) getApplication();
                final ProgressDialog dialog = ProgressDialog.show(OTPLoginActivity.this, "Wait...", "Loging", true, true);
                application.login(user, pass, new Callback<HeaderAndBody>() {
                    @Override
                    public void onSuccess(HeaderAndBody data) {
                        startActivity(new Intent(OTPLoginActivity.this, OTPDisplay.class));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("Login", "An error occurrence", e);
                        Toast.makeText(OTPLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

}
