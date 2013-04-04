package org.aerogear.otp.android.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OTPMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button withLogin = (Button) findViewById(R.id.withLogin);
        withLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPMainActivity.this, OTPLoginActivity.class));
            }
        });

        Button withQRCode = (Button) findViewById(R.id.withQRCode);
        withQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPMainActivity.this, OTPQRCodeActivity.class));
            }
        });

    }

}
