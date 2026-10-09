package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C11_ExisteNum {
    public static void main(String[] args) {

        ArrayList<Integer> notas = new ArrayList<>();
        notas.add(5);
        notas.add(8);
        notas.add(2);

        int buscado = 8;

        boolean encontrado = false;

        for(int n : notas){
            if(n == buscado){
                encontrado = true;
            }
        }

        if(encontrado){
            System.out.println("Existe");
        }else{
            System.out.println("No existe");
        }


    }
}
