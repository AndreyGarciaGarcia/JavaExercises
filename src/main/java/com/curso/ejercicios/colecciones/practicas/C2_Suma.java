package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C2_Suma {
    public static void main(String[] args) {

        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(4);
        numeros.add(5);
        numeros.add(6);

        int suma = 0;

        for(int n : numeros){
            suma = suma + n;
        }
        System.out.println("Suma: " + suma);


    }
}
