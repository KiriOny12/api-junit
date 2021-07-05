package advancedTest.init;

import config.ResourcesConnector;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

public class Languages extends ResourcesConnector {

    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void getCurrentLanguage(String wl) throws IOException  {
        String method = getProperty(wl, "labelUrl") + "current-language";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        assertEquals(response.code(), 200);
        String iso = result.getString("iso");
        assertNotNull(iso);
        assertNotNull(result);
        response.close();
    }
}
