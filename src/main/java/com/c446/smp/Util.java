package com.c446.smp;

public class Util {
    public class ParticleUtil{
        public static int rgbToInt (int red, int green, int blue){
            return ((red << 16) | (green << 8) | blue);
        }
    }
}
