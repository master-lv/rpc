package com.metro.bi.sys;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

import java.io.IOException;


/** Simple example of native library declaration and usage. */

public class HelloTest {



    public interface CLibrary extends Library {

        CLibrary INSTANCE = (CLibrary)

                Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),

                        CLibrary.class);



        void printf(String format, Object... args);

    }

    /**
     * functionality:invoke local command from jvm
     *
     *     process.exitValue();
     *     process.getErrorStream();
     * 　　process.getInputStream();
     * 　　process.getOutputStream();
     *
     * @param filePath
     * @param fileName
     */

    public void cmdInvoke(String filePath,String fileName){
        Process process=null;
        String cmd="\"C:\\Windows\\System32\\regsvr32.exe\"";
        try {
            process = Runtime.getRuntime().exec(cmd+" \""+filePath+"\""+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

        CLibrary.INSTANCE.printf("Hello, World\n");

        for (int i=0;i < args.length;i++) {

            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);

        }
        System.out.println();
        System.out.println((int)((Math.random()*9+1)*100000));
        System.out.println((int)((Math.random()*9+1)*100000));
        System.out.println((int)((Math.random()*9+1)*100000));
        System.out.println((int)((Math.random()*9+1)*100000));
        System.out.println((int)((Math.random()*9+1)*100000));



    }

}
