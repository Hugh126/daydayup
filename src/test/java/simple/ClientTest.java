package simple;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ClientTest {

    @Test
    public void tooManyClientTest() throws InterruptedException {
        while(true){
            new Thread( () -> {
                int size = 1000;
                while (size-- > 0) {
                    try{
                        String url = "https://developer.mozilla.org/en-US/docs/Web/API/EventSource/bcd.json";
                        if (size%31 == 0) {
                            url = url + "12345";
                        }
                        reqAndResp(url);
                    }catch (Exception e){
                        System.err.println("[size]=" + size);
                        log.error("", e);
                    }
                }
            }
            ).start();
            TimeUnit.SECONDS.sleep(20);
        }

    }

    private void reqAndResp(String url) {
        OkHttpClient client =  new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resp = response.body().string();
            log.error(resp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }




    @Test
    public void test4() {
        Stream<Double> list = Stream.of(Double.valueOf(1.1), Double.valueOf(2.2), null, Double.valueOf(3.3));
//        DoubleSummaryStatistics collect = list.filter(item -> item != null).collect(Collectors.summarizingDouble(d -> d));
        System.out.println(list.filter(item -> item != null).collect(Collectors.summingDouble(d -> d)));

    }
}
