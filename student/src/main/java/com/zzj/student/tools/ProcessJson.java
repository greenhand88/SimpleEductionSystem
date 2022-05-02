package com.zzj.student.tools;

public class ProcessJson {
    public static String processJson(String token){
        int start=token.indexOf("\"",token.indexOf(":"))+1,end=token.indexOf("\"",start+1);
        return token.substring(start,end);
    }
}
