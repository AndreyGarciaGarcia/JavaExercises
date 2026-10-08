package com.curso.ejercicios.arrays.practicas;

public class A15_MediaPares {
    public static void main(String[] args) {

        int[] nums = {2,4,5};

        int suma = 0;
        int contador = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] % 2 == 0){
                suma = suma + nums[i];
                contador++;
            }
        }
        System.out.println("Suma pares: " + suma);
        System.out.println("Cuenta pares: " + contador);

    }
}
