package advancedTest.webSockets;

import advancedTest.auth.AuthWeb;
import config.ResourcesConnector;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;


public class WebSockets extends ResourcesConnector {

    @ParameterizedTest
    @ValueSource(strings = {"kievphp"})
    public void getWebSockets(String wl) throws IOException {
        Date dateNow = new Date();
        long nowDateTimestamp = dateNow.toInstant().getEpochSecond();
        long nowDateTimestampMinusTenSec = nowDateTimestamp - 10;

        AuthWeb postAuth = new AuthWeb();
        postAuth.authPost(wl);

        Request request = new Request.Builder()
                .url(getProperty(wl, "labelUrl") + "web-sockets")
                .addHeader("apitoken", postAuth.token)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        assertEquals(response.code(), 200);

        JSONObject result = new JSONObject(response.body().string());
        assertNotNull(result);
        JSONObject params = result.getJSONObject("params");
        String data =params.getString("data");
        assertNotNull(data);
        assertEquals(64, data.length());
        String hash =params.getString("hash");
        assertNotNull(hash);
        assertEquals(32, hash.length());
        int platform =params.getInt("platform");
        assertTrue(platform>0);
        int timestamp = params.getInt("timestamp");
        assertTrue(timestamp>nowDateTimestampMinusTenSec);
        assertEquals(response.code(), 200);
        response.close();
    }
}
