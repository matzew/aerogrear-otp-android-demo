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

import java.util.List;

public class OTPLoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final OTPApplication application = (OTPApplication) getApplication();

        final TextView username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(OTPLoginActivity.this, "Wait...", "Loging", true, true);

                application.login(user, pass, new Callback<HeaderAndBody>() {
                    @Override
                    public void onSuccess(HeaderAndBody data) {
                        try {
                            application.retrieveOTPPath(new Callback<List<OTPUser>>() {
                                @Override
                                public void onSuccess(List<OTPUser> data) {
                                    Intent intent = new Intent(OTPLoginActivity.this, OTPDisplay.class);
                                    intent.putExtra("otpauth", data.get(0).getUri());
                                    startActivity(intent);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    displayErrorMessage(e, dialog);
                                }
                            });
                        } catch (Exception e) {
                            displayErrorMessage(e, dialog);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        displayErrorMessage(e, dialog);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void displayErrorMessage(Exception e, ProgressDialog dialog) {
        Log.e("Login", "An error occurrence", e);
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

}
