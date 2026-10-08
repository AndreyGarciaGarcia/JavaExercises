package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;

public class C4_Buscar {
    public static void main(String[] args) {

        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Ana");
        nombres.add("Luis");
        nombres.add("Marta");

        String buscado = "luis";

        boolean encontrado = false;

        for(String n : nombres){
            if(n.equalsIgnoreCase(buscado)){
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
