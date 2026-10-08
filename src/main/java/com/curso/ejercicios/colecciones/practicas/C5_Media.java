package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C5_Media {
    public static void main(String[] args) {

        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(6);
        numeros.add(4);
        numeros.add(8);

        double suma = 0;

        for(int i = 0; i < numeros.size(); i++){
            suma = suma + numeros.get(i);
        }
        double media = (double)suma / numeros.size();
        System.out.println("Media: " + media);
    }
}
