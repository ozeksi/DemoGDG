package com.ozeksi;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class TestServer {

    private Server jettyServer;
    private Thread serverThread;
    private static final int port = 8080;

    public void start() throws Exception {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                    context.setContextPath("/");

                    jettyServer = new Server(port);
                    jettyServer.setHandler(context);
                    ServletHolder jerseyServlet = context.addServlet(
                            ServletContainer.class, "/*");
                    jerseyServlet.setInitOrder(0);
                    jerseyServlet.setInitParameter(
                            "jersey.config.server.provider.classnames",
                            MockApi.class.getCanonicalName());

                    try {
                        jettyServer.start();
                        jettyServer.join();
                    } finally {
                        jettyServer.destroy();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
    }

    public void stop() {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jettyServer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
    }

    public static void main(String[] args) throws Exception {
        ResourceManager.RESOURCE_PATH = "./test-server/src/main/resources/";//TODO: ozeksi -> fix this hack
        new TestServer().start();
    }
}
