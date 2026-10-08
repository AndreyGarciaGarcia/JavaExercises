package com.curso.ejercicios.arrays.practicas;

public class A7_SumaPares {
    public static void main(String[] args) {

        int[] nums = {3,6,7,10,11};

        int sumaPares = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] % 2 == 0){
                sumaPares = sumaPares + nums[i];
            }
        }
        System.out.println("Suma pares: " + sumaPares);
    }
}
