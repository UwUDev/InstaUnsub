package me.uwu.instaunsub.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WhiteList {

    public static String getWhiteList() throws IOException {
        BufferedReader brTest = new BufferedReader(new FileReader("whitelist.txt"));
        return brTest.readLine();
    }

    public static int getQuantity() throws IOException {
        String input = WhiteList.getWhiteList();

        int index = input.indexOf("@");
        int count = 0;
        while (index != -1) {
            count++;
            input = input.substring(index + 1);
            index = input.indexOf("@");
        }
       return count;
    }

}
