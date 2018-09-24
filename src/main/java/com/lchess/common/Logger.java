package com.lchess.common;

import org.springframework.stereotype.Component;

/**
 * Created by liad on 31/08/2018.
 */
public class Logger {

     public static void errorMsg(String methodName, String msg){
         System.out.println(String.format("[%s():] ERROR: [%s]", methodName, msg));
     }

     public static void printTestPass(String testName){
         System.out.println(String.format("Test pass:: [%s()]", testName));
     }

    public static void printTestFailed(String testName, String reason){
        System.out.println(String.format("Test filed:: [%s()] :: [%s]", testName, reason));
    }
}
