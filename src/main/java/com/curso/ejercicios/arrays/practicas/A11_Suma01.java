package com.curso.ejercicios.arrays.practicas;

public class A11_Suma01 {
    public static void main(String[] args) {

        int[] nums = {4,5,6};

        int suma = 0;

        for(int i = 0; i < nums.length; i++){
            suma = suma + nums[i];
        }
        System.out.println("Suma: " + suma);

    }
}
