package com.curso.ejercicios.arrays.practicas;

public class A16_TodoEnUno {
    public static void main(String[] args) {

        int[] nums = {3,6,4};

        int suma = 0;
        int contador = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] > 4){
                contador++;
            }
            suma = suma + nums[i];
        }
        System.out.println("Suma: " + suma);
        System.out.println("Mayores de 4: " + contador);

    }
}
