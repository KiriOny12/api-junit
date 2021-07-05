package advancedTest.links;

import advancedTest.auth.AuthWeb;
import config.ResourcesConnector;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DepositLink extends ResourcesConnector {


    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void GetLinksDeposit(String wl) throws IOException  {
        AuthWeb postAuth = new AuthWeb();
        postAuth.authPost(wl);
        Request request = new Request.Builder()
                .url(getProperty(wl, "labelUrl") + "links/deposit")
                .addHeader("apitoken", postAuth.token)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        String link = result.getString("link");
        assertNotNull(link);
        assertEquals(response.code(), 200);
        response.close();
    }
}
