package com.curso.ejercicios.poo.practicas;

public class Producto {
    private String nombre;
    private double precio;

    public Producto() {
    }

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        setPrecio(precio);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if(precio >= 0){
            this.precio = precio;
        }else{
            System.out.println("Precio invalido");
        }
    }

    @Override
    public String toString(){
        return nombre + " -> " + precio;
    }
}
