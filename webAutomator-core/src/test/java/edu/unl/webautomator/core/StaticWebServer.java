package edu.unl.webautomator.core;

import com.google.common.collect.Maps;
import edu.unl.webautomator.core.util.StringHelper;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by gigony on 12/12/14.
 */
public class StaticWebServer {
  private Vertx vertx;
  private HttpServer server;
  private String startPath = "";

  private static Map<Integer, HttpServer> portMap = Maps.newHashMap();

  public StaticWebServer(final String path) {
    try {
      this.startPath = new File(ClassLoader.getSystemResource("").toURI().getPath(), path).getPath();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    this.vertx = VertxFactory.newVertx();
    this.server = this.vertx.createHttpServer();
  }


  public final void start() {


    final String rootPath = this.startPath;

    this.server.requestHandler(
      new Handler<HttpServerRequest>() {
        public void handle(final HttpServerRequest req) {
          String file = "";
          if (req.path().equals("/")) {
            file = rootPath + "index.html";
          } else if (!req.path().contains("..")) {
            file = rootPath + req.path();
          }
          req.response().sendFile(file);
        }
      }
    ).listen(8080, "localhost");
  }

  public final Vertx getVertx() {
    return this.vertx;
  }

  public final HttpServer getServer() {
    return this.server;
  }

  public static void start(final String path) {
    int port = StringHelper.getPortNumFromUrl(path);
    if (portMap.containsKey(port)) {
      HttpServer httpServer = portMap.get(port);
      httpServer.close(); // close server if duplicate
      portMap.remove(port);
    }

    StaticWebServer server = new StaticWebServer(path);
    server.start();

    portMap.put(port, server.getServer());
  }

}
