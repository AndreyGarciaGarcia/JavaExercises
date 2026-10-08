package com.curso.ejercicios.arrays.practicas;

public class A10_Existe {
    public static void main(String[] args) {

        int[] nums = {5,8,2};

        int buscado = 8;

        boolean encontrado = false;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] == buscado){
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
