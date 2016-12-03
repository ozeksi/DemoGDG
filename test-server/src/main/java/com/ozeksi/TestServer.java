/*
 * Copyright 2016-2017 arcelik.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 *
 */

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
