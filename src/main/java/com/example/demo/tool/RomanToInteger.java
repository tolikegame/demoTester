package com.example.demo.tool;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RomanToInteger {

    public int romanToInt(String s) {
        char[] chars = s.toCharArray();
        int length =chars.length;
        HashMap<Character,Integer> m1 =new HashMap<>();
        m1.put('I',1 );
        m1.put('V',5 );
        m1.put('X',10 );
        m1.put('L',50);
        m1.put('C',100);
        m1.put('D',500);
        m1.put('M',1000);
        int anwser=0;
        for(int i=0;i<length;i++) {
            if((i+1)<length) {
                char theOne =chars[i];
                char theTwo =chars[i+1];
                int temp =(m1.containsKey(theOne))?(int)m1.get(theOne):0;
                int temp1 =(m1.containsKey(theTwo))?(int)m1.get(theTwo):0;
                if(temp<temp1) {
                    anwser-=temp;
                }else {
                    anwser+=temp;
                }
            }else {
                char theOne =chars[i];
                int temp =(m1.containsKey(theOne))?(int)m1.get(theOne):0;
                anwser+=temp;
            }
        }
        return anwser;
    }
}
