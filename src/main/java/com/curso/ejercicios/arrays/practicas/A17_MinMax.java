package com.curso.ejercicios.arrays.practicas;

public class A17_MinMax {
    public static void main(String[] args) {

        int[] nums = {5,1,8};

        int menor = nums[0];
        int mayor = nums[0];

        for(int i = 0; i < nums.length; i++){
            if(nums[i] < menor){
                menor = nums[i];
            }

            if(nums[i] > mayor){
                mayor = nums[i];
            }
        }
        System.out.println("Menor: " + menor);
        System.out.println("Mayor: " + mayor);
    }
}
