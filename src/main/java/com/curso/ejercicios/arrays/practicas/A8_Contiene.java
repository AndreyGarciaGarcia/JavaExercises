package com.curso.ejercicios.arrays.practicas;

public class A8_Contiene {
    public static void main(String[] args) {

        String[] coches = {"Seat","BMW","Audi"};

        String buscado = "audi";
        boolean encontrado = false;

        for(int i = 0; i < coches.length; i++){
            if(coches[i].equalsIgnoreCase(buscado)){
                encontrado = true;
            }
        }

        if(encontrado){
            System.out.println("Sí existe");
        }else{
            System.out.println("No existe");
        }

    }
}
