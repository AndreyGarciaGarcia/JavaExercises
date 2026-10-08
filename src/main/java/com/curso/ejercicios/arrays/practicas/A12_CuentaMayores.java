package com.curso.ejercicios.arrays.practicas;

public class A12_CuentaMayores {
    public static void main(String[] args) {

        int[] nums = {2,8,4,9};

        int contador = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] > 5){
                contador = contador + 1;
            }
        }
        System.out.println("Mayores: " + contador);

    }
}
