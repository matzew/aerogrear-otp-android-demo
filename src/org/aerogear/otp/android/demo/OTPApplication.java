package org.aerogear.otp.android.demo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.authentication.AuthenticationConfig;
import org.jboss.aerogear.android.authentication.AuthenticationModule;
import org.jboss.aerogear.android.authentication.impl.Authenticator;
import org.jboss.aerogear.android.http.HeaderAndBody;
import org.jboss.aerogear.android.impl.http.HttpRestProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class OTPApplication extends Application {

    private AuthenticationModule authModule;

    @Override
    public void onCreate() {
        super.onCreate();

        try {

            URL baseURL = new URL("http://controller-aerogear.rhcloud.com/aerogear-controller-demo");
            Authenticator auth = new Authenticator(baseURL);
            AuthenticationConfig config = new AuthenticationConfig();
            authModule = auth.auth("login", config);

        } catch (MalformedURLException e) {
            Log.e("AeroGear-OTP-Android-Demo", e.getMessage(), e);
        }
    }

    public void login(String username, String password, Callback<HeaderAndBody> callback) {
        authModule.login(username, password, callback);
    }

}
