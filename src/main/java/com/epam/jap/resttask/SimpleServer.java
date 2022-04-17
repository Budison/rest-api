package com.epam.jap.resttask;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author Kevin Nowak
 */
class SimpleServer {
    private final int port;
    private final String path;
    private HttpServer httpServer;

    public SimpleServer(int port, String path, int backlog) {
        this.port = port;
        this.path = path;
        httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), backlog);
        } catch (IOException e) {
            System.err.println("Unable to create the server!");
        }
    }

    void startServer() {
        this.httpServer.setExecutor(null);
        this.httpServer.start();
    }

    void simpleService() {
        this.httpServer.createContext(this.path, (exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String response = "Hey there!\n";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.flush();
            }
            exchange.close();
            exchange.close();
        }));
        System.out.println("Get service started on port " + port);
    }
}
