package com.curso.ejercicios.arrays.practicas;

public class A4_Buscar {
    public static void main(String[] args) {

        String[] coches = {"Seat", "BMW", "Audi"};

        boolean encontrado = false;

        for(int i = 0; i < coches.length; i++){
            if(coches[i].equalsIgnoreCase("bmw")){
                System.out.println("Encontrado en " + i);
                encontrado = true;
            }
        }

       if(!encontrado){
           System.out.println("No existe");
       }

    }
}
