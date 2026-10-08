package com.curso.ejercicios.arrays.practicas;

public class A2_Media {
    public static void main(String[] args) {

        int[] notas = {7,5,9};

        // Calcular la media
        int suma = 0;
        for(int i = 0; i < notas.length; i++){
            suma = suma + notas[i];
        }
        System.out.println("La suma total del array es: " + suma);

        double media = (double)suma / notas.length;
        System.out.println("La media del array es: " + media);

    }
}
