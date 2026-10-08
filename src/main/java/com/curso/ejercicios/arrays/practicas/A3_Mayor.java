package com.curso.ejercicios.arrays.practicas;

public class A3_Mayor {
    public static void main(String[] args) {

        int[] notas = {10,5,9};

        int mayor = notas[0];

        for(int i = 0; i < notas.length; i++){

            if(notas[i] > mayor){
                mayor = notas[i];
            }
        }
        System.out.println(mayor);
    }
}
