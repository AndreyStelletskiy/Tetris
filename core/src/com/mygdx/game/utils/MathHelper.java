package com.mygdx.game.utils;

public class MathHelper {
    static int percent(int a, int b){
        if(a >= 0 ){
            return a % b;
        }
        return a % b + b;
    }
}
