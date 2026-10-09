package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C10_Aprobados {
    public static void main(String[] args) {

        ArrayList<Integer> notas = new ArrayList<>();
        notas.add(7);
        notas.add(4);
        notas.add(9);
        notas.add(3);

        int contador = 0;

        for(int n : notas){
            if(n >= 5){
                contador++;
            }
        }
        System.out.println("Aprobados: " + contador);

    }
}
