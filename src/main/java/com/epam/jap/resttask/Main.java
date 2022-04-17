package com.epam.jap.resttask;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleServer simpleServer = new SimpleServer(8080, "/hello", 0);
        simpleServer.startServer();
        simpleServer.simpleService();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://reqres.in/api/users"))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

        // GET
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(URI.create("https://reqres.in/api/users?page=2"))
                .build();
        HttpResponse<String> answer = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        System.out.println(answer.statusCode());

        // POST
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{     \"name\": \"morpheus\",     \"job\": \"leader\" }");
        HttpRequest requestPost = HttpRequest.newBuilder(URI.create("https://reqres.in/api/users"))
                .POST(bodyPublisher).build();
        HttpResponse<String> postAnswer = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        System.out.println(postAnswer.statusCode());

        // PUT
        HttpRequest.BodyPublisher bodyPublisher2 = HttpRequest.BodyPublishers.ofString("{     \"name\": \"morpheus\",     \"job\": \"zion resident\" }");
        HttpRequest requestPut = HttpRequest.newBuilder(URI.create("https://reqres.in/api/users/2"))
                .PUT(bodyPublisher2).build();
        HttpResponse<String> putAnswer = client.send(requestPut, HttpResponse.BodyHandlers.ofString());
        System.out.println(putAnswer.statusCode());
    }
}
