package com.dictiographer.controller;



/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/6/14
 */
public class Launcher {


    public Launcher(){
        ViewController.getInstance();
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
