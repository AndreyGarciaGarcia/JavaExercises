package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C7_SumaImpares {
    public static void main(String[] args) {

        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(1);
        numeros.add(2);
        numeros.add(3);
        numeros.add(4);

        int suma = 0;

        for(int n : numeros){
            if(n % 2 != 0){
                suma = suma + n;
            }
        }
        System.out.println("Suma impares: " + suma);


    }
}
