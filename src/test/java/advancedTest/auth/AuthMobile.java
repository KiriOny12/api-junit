package advancedTest.auth;


import config.ResourcesConnector;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AuthMobile  extends ResourcesConnector  {

    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void PostAuthMobile(String wl) throws IOException {

        String route = "auth-mobile";
        String inputDataFormat =
                getProperty(wl, "email") + "&"
                + getProperty(wl, "password");

        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(getProperty(wl, "labelUrl") + route)
                .addHeader("Platform", "IOS")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        String hash = result.getString("hash");
        assertEquals(32, hash.length());
        String token = result.getString("token");
        assertEquals(32, token.length());
        assertEquals(response.code(), 200);
        response.close();

    }
}