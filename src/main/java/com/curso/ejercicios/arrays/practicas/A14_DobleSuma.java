package com.curso.ejercicios.arrays.practicas;

public class A14_DobleSuma {
    public static void main(String[] args) {

        // Generar un array de numeros
        int[] nums = {1,2,3};

        int suma = 0;

        for(int i = 0; i < nums.length; i++){
            suma = suma + nums[i];
        }
        System.out.println("Doble suma: " + suma * 2);

    }
}
