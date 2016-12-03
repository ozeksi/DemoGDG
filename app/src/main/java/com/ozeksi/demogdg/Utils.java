package com.ozeksi.demogdg;

import java.io.IOException;

public class Utils {
    public static boolean isInternetAvailable() {//Can misguide if you work on localhost
        String command = "ping -c 1 google.com";
        try {
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
