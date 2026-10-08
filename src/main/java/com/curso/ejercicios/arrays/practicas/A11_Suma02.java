package com.curso.ejercicios.arrays.practicas;

public class A11_Suma02 {
    public static void main(String[] args) {

        int[] nums = {1,2,3};

        int suma = 0;

        for(int i = 0; i < nums.length; i++){
            suma = suma + nums[i];
        }
        System.out.println("Suma: " + suma);
    }
}
