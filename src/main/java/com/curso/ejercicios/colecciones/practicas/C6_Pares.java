package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C6_Pares {
    public static void main(String[] args) {

        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(3);
        numeros.add(6);
        numeros.add(7);
        numeros.add(10);

        int contador = 0;

        for(int i = 0; i < numeros.size(); i++){
            if(numeros.get(i) % 2 == 0){
                contador++;
            }
        }
        System.out.println("Pares: " + contador);

        for(int i = 0; i < numeros.size(); i++){
            if(numeros.get(i) % 2 == 0){
                System.out.println(numeros.get(i));
            }
        }

    }
}
