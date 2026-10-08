package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C3_Mayores {
    public static void main(String[] args) {

        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(2);
        numeros.add(8);
        numeros.add(4);
        numeros.add(9);

        int contador = 0;

        for(int n : numeros){
            if(n > 5){
                contador++;
            }
        }
        System.out.println("Mayores: " + contador);
    }
}
