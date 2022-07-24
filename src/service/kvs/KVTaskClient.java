package service.kvs;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class KVTaskClient {
    private String API_TOKEN;
    public static final int PORT = 8078;
    private URL url;
    Gson gson = new Gson();

    public KVTaskClient(URL url) {
        this.url = url;
    }

    private String makeGetRequest(URL url) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .GET()    // указываем HTTP-метод запроса
                .uri(url.toURI())// указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола HTTP
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") HTTP-запрос
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }


    public void registration() throws IOException, InterruptedException, URISyntaxException {
        String uri = "http://" + url.getHost() + ":" + PORT;
        url = new URL(uri);
        setAPI_TOKEN(makeGetRequest(new URL(uri + "/register")));

    }

    public void put(String key, String json) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        String uri = url + "/save/" + key + "?API_TOKEN=" + API_TOKEN;
        HttpRequest request = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(json)))    // указываем HTTP-метод запроса
                .uri(URI.create(uri)) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола HTTP
                .header("Content-Type", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") HTTP-запрос
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(" в клиенте " + response.statusCode());
    }

    public String load(String key) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        String uri = url + "/load/" + key + "?API_TOKEN=" + API_TOKEN;
        HttpRequest request = requestBuilder
                .GET()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return response.body();
    }


    public String getAPI_TOKEN() {
        return API_TOKEN;
    }

    public void setAPI_TOKEN(String API_TOKEN) {
        this.API_TOKEN = API_TOKEN;
    }


}
