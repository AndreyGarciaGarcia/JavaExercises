package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C8_MaxMin {
    public static void main(String[] args) {

        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(5);
        numeros.add(1);
        numeros.add(8);

        int mayor = numeros.get(0);
        int menor = numeros.get(0);

        for(int n : numeros){
            if(n < menor){
                menor = n;
            }

            if(n > mayor){
                mayor = n;
            }
        }
        System.out.println("Menor: " + menor);
        System.out.println("Mayor: " + mayor);


    }
}
