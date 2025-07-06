/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.primesecure.primesecure;

import java.util.Scanner;

/**
 *
 * @author Andrea
 */
public class Menu {
     public static void iniciar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Iniciando la aplicacion de PrimeSecure...");

        // --- PASO 1: Crear el RECURSO COMPARTIDO ---
        // Creamos UNA SOLA instancia de PrimesList. Esta es la lista que
        // TODOS los hilos compartirán. Es fundamental que sea la misma instancia.
        PrimeList sharedPrimesList = new PrimeList();
        System.out.println("Lista de primos compartida ha sido creada.\n");

        // --- PASO 2: Crear los HILOS TRABAJADORES ---
        // Vamos a crear varios hilos. Cada uno intentará agregar un número diferente
        // a la MISMA lista 'sharedPrimesList'.
        // Mezclamos números primos y no primos para ver cómo funciona la validación.
        System.out.println("Creando hilos para agregar 'Codigos Primos'...");
        Thread t1 = new PrimeAdderThread(sharedPrimesList, 17);
        Thread t2 = new PrimeAdderThread(sharedPrimesList, 20); // No es primo
        Thread t3 = new PrimeAdderThread(sharedPrimesList, 29);
        Thread t4 = new PrimeAdderThread(sharedPrimesList, 30); // No es primo
        Thread t5 = new PrimeAdderThread(sharedPrimesList, 41);

        // --- PASO 3: Iniciar los HILOS ---
        // Este es el momento clave. llamar a .start() le dice a la JVM:
        // "Crea un nuevo hilo de ejecución y ejecuta el método run() de este objeto en él".
        // NO llames a .run() directamente, porque eso ejecutaría el código en el hilo principal,
        // uno tras otro (secuencialmente), y perderíamos toda la concurrencia.
        System.out.println("Iniciando hilos para que trabajen en paralelo...\n");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // --- PASO 4: Esperar a que TODOS los hilos terminen ---
        // El hilo principal (main) no debe continuar hasta que todos los trabajadores hayan terminado.
        // El método .join() pausa el hilo actual (main) y espera a que el hilo sobre el que se llama
        // (por ej. t1) complete su ejecución.
        // Lo ponemos en un try-catch porque join() puede lanzar una InterruptedException.
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido mientras esperaba.");
            Thread.currentThread().interrupt(); // Buena práctica
        }

        // --- PASO 5: Mostrar los resultados FINALES ---
        // Este código solo se ejecutará después de que todos los hilos hayan terminado su trabajo.
        System.out.println("\nTodos los hilos han terminado su ejecucion.");
        System.out.println("=============================================");
        System.out.println("Resultado Final de la Base de Datos de Codigos Primos:");
        System.out.println("Codigos Primos en la lista: " + sharedPrimesList);
        System.out.println("Cantidad total de Codigos Primos: " + sharedPrimesList.getPrimesCount());
        System.out.println("=============================================");

    }
}
