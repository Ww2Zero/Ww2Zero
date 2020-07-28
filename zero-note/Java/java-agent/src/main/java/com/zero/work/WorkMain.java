package com.zero.work;

import java.util.Random;

public class WorkMain {

    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            int x = new Random().nextInt();
            new WorkMain().test(x);
        }
    }


    public void test(int x) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("i'm working " + x);
    }
}
