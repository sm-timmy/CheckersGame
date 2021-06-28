/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.checkersgame;

/**
 *
 * @author Аленка
 */
public class CriticalResources {
    public double curr_point;
    public double result;
    
    CriticalResources(double curr_point) {
        this.result = 0;
        this.curr_point = curr_point;
    }
    
    public double returnResult() {
        return Math.round(result * 1000.0) / 1000.0;
    }
}
