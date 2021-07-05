package advancedTest.init;

import advancedTest.auth.AuthWeb;
import config.ResourcesConnector;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Init extends ResourcesConnector{

    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void getInitWeb(String wl) {
        JSONObject resultGetInitWeb;
        String method = getProperty(wl, "labelUrl") + "init?device=web";

        try {
            Request request = new Request.Builder()
                    .url(method)
                    .get()
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            resultGetInitWeb = new JSONObject(response.body().string());
            assertEquals(response.code(), 200);
            assertNotNull(resultGetInitWeb);
            response.close();
        }catch (IOException e){
            System.out.println("Error in execute() or string()");
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void getInitMobile(String wl) throws IOException {
        JSONObject resultGetInitMobile;
        String method = getProperty(wl, "labelUrl") + "init?device=mobile";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        resultGetInitMobile = new JSONObject(response.body().string());

        assertEquals(response.code(), 200);
        assertNotNull(resultGetInitMobile);
        response.close();
    }


    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void initCurrentTheme(String wl) throws IOException {

        AuthWeb postAuth = new AuthWeb();
        postAuth.authPost(wl);

        String inputDataFormat = "theme=dark";

        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(getProperty(wl, "labelUrl") + "init/current_theme")
                .addHeader("apitoken", postAuth.token)
                .put(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        assertNotNull(result);
        String message = result.getString("message");
        assertNotNull(message);
        response.close();
    }
}
