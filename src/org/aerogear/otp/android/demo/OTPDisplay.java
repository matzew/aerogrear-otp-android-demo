package org.aerogear.otp.android.demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.jboss.aerogear.security.otp.Totp;

public class OTPDisplay extends Activity {

    private TextView totpDisplay;
    private TextView nameDisplay;
    private ProgressBar progressBar;

    private String name = "";
    private String secret;

    private Totp totp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        parseOtpPath();

        findComponents();

        updateOTP();

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                updateOTP();
                this.start();
            }
        }.start();

    }

    private void parseOtpPath() {
        String otpauth = getIntent().getStringExtra("otpauth");
        Uri otpUri = Uri.parse(otpauth);

        name   = otpUri.getQueryParameter("");
        secret = otpUri.getQueryParameter("secret");

        totp = new Totp(secret);
    }

    private void findComponents() {
        totpDisplay = (TextView)    findViewById(R.id.totp);
        nameDisplay = (TextView)    findViewById(R.id.name);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    private void updateOTP() {
        nameDisplay.setText(name);
        totpDisplay.setText(totp.now());
    }

}
