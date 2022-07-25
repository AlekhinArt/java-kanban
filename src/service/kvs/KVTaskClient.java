package service.kvs;

import java.io.IOException;
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


    public KVTaskClient(URL url) {
        this.url = url;
        try {
            registration();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String makeGetRequest(URL url) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .GET()
                .uri(url.toURI())
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
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
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
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
        return response.body();
    }

    public void setAPI_TOKEN(String API_TOKEN) {
        this.API_TOKEN = API_TOKEN;
    }


}
