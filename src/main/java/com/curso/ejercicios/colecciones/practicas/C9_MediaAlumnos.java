package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C9_MediaAlumnos {
    public static void main(String[] args) {

        ArrayList<Integer> notas = new ArrayList<>();
        notas.add(7);
        notas.add(5);
        notas.add(9);

        int suma = 0;
        double media = 0;

       for(int i = 0; i < notas.size(); i++){
           suma = suma + notas.get(i);
       }
        media = (double) suma / notas.size();

        System.out.println("Total: " + notas.size());
        System.out.println("Media: " + media);

    }
}
