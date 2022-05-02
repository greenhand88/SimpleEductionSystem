package com.zzj.teacher.tools;

public class ProcessJson {
    public static String processJson(String token){
        int start=token.indexOf("\"",token.indexOf(":"))+1,end=token.indexOf("\"",start+1);
        if(start<0||end<0)
            return token;
        return token.substring(start,end);
    }
}
