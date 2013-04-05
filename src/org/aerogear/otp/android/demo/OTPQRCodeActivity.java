package org.aerogear.otp.android.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OTPQRCodeActivity extends Activity {

    private final int requestCode = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanBarcode();
    }

    private void scanBarcode() {
        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {
                String otpauth = data.getStringExtra("SCAN_RESULT");
                Intent intent = new Intent(this, OTPDisplay.class);
                intent.putExtra("otpauth", otpauth);
                startActivity(intent);
            } else {
                showAlertDialog();
            }
        }
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Aviso")
            .setMessage("An error occurred while trying to read the QRCode. Do you want to try again?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface di, int arg) {
                    scanBarcode();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface di, int arg) {
                    finish();
                }
            }).show();
    }

}
