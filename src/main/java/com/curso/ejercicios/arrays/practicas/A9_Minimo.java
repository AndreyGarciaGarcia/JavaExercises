package com.curso.ejercicios.arrays.practicas;

public class A9_Minimo {
    public static void main(String[] args) {

        // Creamos el array
        int[] nums = {7,2,9,4};

        // Creamos variable de tipo entero buscando el menor del array
        int menor = nums[0];

        // Bucle for
        for(int i = 0; i < nums.length; i++){
            // Condicional if
            if(nums[i] < menor){
                menor = nums[i];
            }
        }
        System.out.println("Menor: " + menor);
    }
}
