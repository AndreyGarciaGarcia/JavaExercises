package com.curso.ejercicios.arrays.practicas;

public class A13_SumaImpares {
    public static void main(String[] args) {

        int[] nums = {1,2,3,4};

        int suma = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] % 2 != 0){
                suma = suma + nums[i];
            }
        }
        System.out.println("Suma impares: " + suma);
    }
}
