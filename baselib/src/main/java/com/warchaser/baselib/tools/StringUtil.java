package com.warchaser.baselib.tools;

public class StringUtil {

    public static String getRealValue(String str){
        String result = str;
        if(str == null){
            result = "NULL";
        } else if (str.length() == 0) {
            result = "EMPTY_STRING";
        } else if(isBlankString(str)){
            result = "BLANK_STRING";
        }

        return result;
    }

    public static boolean isBlankString(String str){
        boolean result = true;
        for (int i = 0; i < str.length(); i++){
            final char c = str.charAt(i);
            if(!Character.isWhitespace(c)){
                result = false;
                break;
            }
        }
        return result;
    }

}
