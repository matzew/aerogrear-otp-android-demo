package org.aerogear.otp.android.demo;

import android.app.Application;
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.ReadFilter;
import org.jboss.aerogear.android.authentication.AuthenticationConfig;
import org.jboss.aerogear.android.authentication.AuthenticationModule;
import org.jboss.aerogear.android.authentication.impl.Authenticator;
import org.jboss.aerogear.android.http.HeaderAndBody;
import org.jboss.aerogear.android.impl.pipeline.PipeConfig;
import org.jboss.aerogear.android.pipeline.Pipe;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class OTPApplication extends Application {

    private AuthenticationModule authModule;
    private String baseURL = "http://controller-aerogear.rhcloud.com/aerogear-controller-demo";

    @Override
    public void onCreate() {
        super.onCreate();

        Authenticator auth = new Authenticator(baseURL);
        AuthenticationConfig config = new AuthenticationConfig();
        config.setLoginEndpoint("/login");
        authModule = auth.auth("login", config);
    }

    public void login(String username, String password, Callback<HeaderAndBody> callback) {
        authModule.login(username, password, callback);
    }

    public void retrieveOTPPath(Callback<List<OTPUser>> callback) throws Exception {
        URL url = new URL(baseURL);

        Pipeline pipeline = new Pipeline(url);

        PipeConfig pipeConfig = new PipeConfig(url, OTPUser.class);
        pipeConfig.setAuthModule(authModule);

        Pipe<OTPUser> pipe = pipeline.pipe(OTPUser.class, pipeConfig);

        ReadFilter filter = new ReadFilter();
        filter.setLinkUri(new URI("/aerogear-controller-demo/auth/otp/secret"));
        pipe.readWithFilter(filter, callback);
    }

    public void logout(Callback<Void> callback) {
        authModule.logout(callback);
    }

}
