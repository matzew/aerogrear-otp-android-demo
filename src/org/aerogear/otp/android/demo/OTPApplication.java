package org.aerogear.otp.android.demo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.JsonObject;
import org.aerogear.android.Callback;
import org.aerogear.android.http.HeaderAndBody;
import org.aerogear.android.impl.http.HttpRestProvider;

import java.net.URL;

public class OTPApplication extends Application {

    public void login(final String username, final String password, final Callback<HeaderAndBody> callback) {
        new AsyncTask<Void, Void, Void>() {
            HeaderAndBody result = null;
            Exception exception = null;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL authenticationURL = new URL("http://controller-aerogear.rhcloud.com/aerogear-controller-demo/login");

                    JsonObject loginInformations = new JsonObject();
                    loginInformations.addProperty("aeroGearUser.id", username);
                    loginInformations.addProperty("aeroGearUser.password", password);

                    new HttpRestProvider(authenticationURL).post(loginInformations.toString());
                } catch (Exception e) {
                    Log.e("OTP", "error login", e);
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void ignore) {
                super.onPostExecute(ignore);
                if (exception == null) {
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(exception);
                }
            }

        }.execute();
    }

}
