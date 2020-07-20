package com.wutong.lddw.utils;

public class Test {

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println(pow(4, 3));
        System.out.println("end----" + (System.currentTimeMillis() - start));

        long start1 = System.currentTimeMillis();
        System.out.println(pow1(4, 3));
        System.out.println("end----" + (System.currentTimeMillis() - start1));

    }

    public static int pow(int b, int n){
        if (n == 1) {
            return b;
        }
        int half = pow(b, n/2);
        if (n % 2 == 0) {
            return  half * half;
        } else {
            return half * half * b;
        }
    }

    public static int pow1(int b, int n){
        int temp = 1;
        for (int i = 0;i<n;i++) {
            temp = temp * b;
        }
        return temp;
    }

}
