/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
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
