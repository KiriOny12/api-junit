package advancedTest.auth;


import config.ResourcesConnector;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AuthWeb extends ResourcesConnector {

    public String token;

    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void authPost(String wl) throws IOException {

        String route = "auth";
        String inputDataFormat =
                getProperty(wl, "labelUrl") + "&"
                        + getProperty(wl, "email") + "&"
                        + getProperty(wl, "password")+ "&"
                        + getProperty(wl, "rememberMeEmpty")+ "&"
                        + getProperty(wl, "urlOfAuthRequester")+ "&"
                        + getProperty(wl, "sourcePlatform");

        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(getProperty(wl, "labelUrl") + route)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        token = result.getString("token");
        assertNotNull(token);
        assertEquals(response.code(), 200);
        response.close();
    }
}