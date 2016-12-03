package com.ozeksi.demogdg;

import com.ozeksi.TestServer;
import com.ozeksi.demogdg.network.ResponseHandler;
import com.ozeksi.demogdg.network.ServiceProvider;
import com.ozeksi.demogdg.network.api.ApiService;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Timer;
import java.util.concurrent.CountDownLatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiServiceIntegrationTest {
    private static final int serverStartDelay = 3000;
    private static TestServer testServer;
    private static ApiService apiService;

    @BeforeClass
    public static void start_server() throws Exception {
        apiService = new ServiceProvider().provideApiService();
        testServer = new TestServer();
        testServer.start();
        Thread.sleep(serverStartDelay);
    }

    @Test
    public void test_001_test() throws InterruptedException {
        TestCallback testResult = new TestCallback();
        apiService.test(testResult);
        Assert.assertEquals(true, testResult.isSuccess());
    }

    @Test
    public void test_002_getUser() throws InterruptedException {
        TestCallback testResult = new TestCallback();
        apiService.getUser(testResult);
        Assert.assertEquals(true, testResult.isSuccess());
    }

    @AfterClass
    public static void stop_server() {
        if (testServer != null) {
            testServer.stop();
        }
    }

    private static class TestCallback implements ResponseHandler {
        private static final int requestTimeout = 2000;
        private final CountDownLatch latch;
        private final Timer timer;
        private boolean testPassed;

        TestCallback() {
            this.latch = new CountDownLatch(1);
            this.testPassed = true;
            this.timer = new Timer();
            this.timer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            testPassed = false;
                            latch.countDown();
                        }
                    },
                    requestTimeout
            );
        }

        @Override
        public void success(Object data) {
            latch.countDown();
        }

        @Override
        public void fail(String code, String description) {
            testPassed = false;
            latch.countDown();
        }

        boolean isSuccess() throws InterruptedException {
            this.latch.await();
            timer.cancel();
            return testPassed;
        }
    }
}