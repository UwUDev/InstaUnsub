package me.uwu.instaunsub;

import me.uwu.instaunsub.utils.WhiteList;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUnfollowRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static String user = "";
    public static String pass = "";

    public static int baseValue = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter username : ");
        user = sc.nextLine();
        System.out.println("Enter password : ");
        pass = sc.nextLine();

        System.out.println("Loaded whitelist (" + WhiteList.getQuantity() + " accounts) : " + WhiteList.getWhiteList());

        Instagram4j instagram = Instagram4j.builder().username(user).password(pass).build();
        instagram.setup();
        instagram.login();

        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));

        baseValue = userResult.getUser().following_count - WhiteList.getQuantity();

        System.out.println(baseValue + "accounts to unfollow.");

        InstagramGetUserFollowersResult followers = instagram.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));
        List<InstagramUserSummary> users = followers.getUsers();
        for (InstagramUserSummary user : users) {
            if (!WhiteList.getWhiteList().contains("@" + user.getUsername())) {
                System.out.println("Thying to unfollow @" + user.getUsername() + " ...");
                instagram.sendRequest(new InstagramUnfollowRequest(user.getPk()));
                Thread.sleep(32000);
                InstagramSearchUsernameResult count = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));
                float percent = 100 - (((float) count.getUser().following_count / (float) baseValue) * 100);
                System.out.println(count.getUser().following_count + " remaining (" + percent + "%)    about " + (count.getUser().following_count * 32.5) + "s left.");
            } else {
              System.out.println("Not unfollowing @" + user.getUsername() + " beacause he is whitelisted, lucky day for him :)");
            }
        }
    }

}
