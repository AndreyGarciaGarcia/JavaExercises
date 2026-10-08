package com.curso.ejercicios.arrays.practicas;

public class A5_Pares {
    public static void main(String[] args) {

        int[] nums = {3,6,7,10,11};

        int contador = 0;
        int pares = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] % 2 == 0){
                contador++;
                System.out.println(nums[i]);
            }
        }
        pares = pares + contador;
        System.out.println("Pares " + pares);
    }
}
