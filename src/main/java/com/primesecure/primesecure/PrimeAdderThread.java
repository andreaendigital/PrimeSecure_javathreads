/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.primesecure.primesecure;

/**
 * @author Andrea
 * Esta clase es un Hilo (Thread). Su única misión es tomar un número
 * y tratar de agregarlo a nuestra lista de primos compartida.
 * Heredar de 'Thread' nos da toda la capacidad de ser un hilo de ejecución.
 */
public class PrimeAdderThread extends Thread {
    // Cada hilo necesita saber a qué lista debe agregar el número.
    private final PrimeList primesList;
    // Y cada hilo necesita saber qué número debe intentar agregar.
    private final int numberToAdd;

    /**
     * Constructor para crear nuestro hilo trabajador.
     * @param primesList La lista compartida donde se agregarán los primos.
     * @param numberToAdd El número que este hilo específico intentará agregar.
     */
    public PrimeAdderThread(PrimeList primesList, int numberToAdd) {
        this.primesList = primesList;
        this.numberToAdd = numberToAdd;
    }

    /**
     * El método run() es el corazón del hilo.
     * Cuando llamemos a .start() en nuestro hilo, el código que está aquí adentro
     * se ejecutará en un nuevo flujo de control, en paralelo con el programa principal y otros hilos.
     */
    @Override
    public void run() {
        try {
            // El hilo intenta realizar su trabajo: agregar el número a la lista.
            // La lógica de si es primo o no está dentro de PrimesList.add().
            primesList.add(numberToAdd);
            System.out.println("Hilo [" + getName() + "]: El numero " + numberToAdd + " fue agregado con exito.");
            
        } catch (IllegalArgumentException e) {
            // Si PrimesList.add() lanza la excepción (porque no era primo), la atrapamos aquí.
            // Esto evita que el programa se caiga y nos permite informar del error de forma controlada.
            System.err.println("Hilo [" + getName() + "]: " + e.getMessage());
        }
    }
}
