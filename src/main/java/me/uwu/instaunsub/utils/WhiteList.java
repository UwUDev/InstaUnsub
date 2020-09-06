package me.uwu.instaunsub.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WhiteList {

    public static String getWhiteList() throws IOException {
        BufferedReader brTest = new BufferedReader(new FileReader("whitelist.txt"));
        return brTest.readLine();
    }

}
