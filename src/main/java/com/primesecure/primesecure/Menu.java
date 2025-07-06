/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.primesecure.primesecure;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random; // Necesitamos esta clase para generar números aleatorios
import java.util.Scanner;

/**
 *
 * @author Andrea
 */
public class Menu {
     public static void iniciar() {
        PrimeList sharedPrimesList = new PrimeList();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- MENU PRIMESECURE ---");
            System.out.println("1. Agregar un 'Codigo Primo' (Manual)");
            System.out.println("2. Eliminar un 'Codigo Primo'");
            System.out.println("3. Listar todos los 'Codigos Primos'");
            System.out.println("4. Salir");
            System.out.println("5. Agregar 20 'Codigos Primos' aleatorios (1-100)"); // Nueva opción
            System.out.print("Seleccione una opcion: ");

            int opcion = -1;
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Error: Por favor, ingrese solo un numero.");
                scanner.next(); 
                continue; 
            }

            switch (opcion) {
                case 1:
                    // ... (el código para agregar manualmente se mantiene igual)
                    System.out.print("Ingrese el numero que desea agregar: ");
                    try {
                        int numeroParaAgregar = scanner.nextInt();
                        Thread adderThread = new PrimeAdderThread(sharedPrimesList, numeroParaAgregar);
                        adderThread.start(); 
                        adderThread.join(); 
                    } catch (InputMismatchException e) {
                        System.err.println("Error: Debe ingresar un numero entero valido.");
                        scanner.next();
                    } catch (InterruptedException e) {
                        System.err.println("El hilo de adicion fue interrumpido.");
                    }
                    break;
                
                case 2:
                    // ... (el código para eliminar se mantiene igual)
                    if (sharedPrimesList.isEmpty()) {
                        System.out.println("La lista está vacia, no hay nada que eliminar.");
                        break;
                    }
                    System.out.println("Lista actual: " + sharedPrimesList);
                    System.out.print("Ingrese el indice (posicion) del numero que desea eliminar: ");
                    try {
                        int indiceParaEliminar = scanner.nextInt();
                        Integer numeroEliminado = sharedPrimesList.remove(indiceParaEliminar);
                        System.out.println("Numero " + numeroEliminado + " eliminado correctamente.");
                    } catch (InputMismatchException | IndexOutOfBoundsException e) {
                        System.err.println("Error: Indice no valido.");
                        if (e instanceof InputMismatchException) scanner.next();
                    }
                    break;

                case 3:
                    // ... (el código para listar se mantiene igual)
                    if (sharedPrimesList.isEmpty()) {
                        System.out.println("La lista de 'Codigos Primos' esta actualmente vacia.");
                    } else {
                        System.out.println("\n--- Lista de Codigos Primos ---");
                        System.out.println(sharedPrimesList);
                        System.out.println("Total de codigos: " + sharedPrimesList.getPrimesCount());
                        System.out.println("----------------------------");
                    }
                    break;

                case 4:
                    System.out.println("Saliendo de la aplicacion. ¡Hasta luego!");
                    scanner.close(); 
                    return; 

                // =============== LÓGICA ===============
                case 5:
                    System.out.println("Iniciando la adicion de 20 numeros aleatorios...");
                    
                    // 1. Creamos un generador de aleatorios y una lista para guardar nuestros hilos.
                    Random random = new Random();
                    List<Thread> threads = new ArrayList<>();
                    
                    // 2. Generamos 20 números y creamos un hilo para cada uno.
                    for (int i = 0; i < 20; i++) {
                        // Genera un número entre 1 y 100 (incluidos).
                        int numeroAleatorio = random.nextInt(100) + 1; 
                        // Creamos un hilo trabajador para ESE número.
                        Thread t = new PrimeAdderThread(sharedPrimesList, numeroAleatorio);
                        threads.add(t); // Lo guardamos en nuestra lista de hilos.
                    }
                    
                    // 3. Iniciamos todos los hilos para que se ejecuten en paralelo.
                    System.out.println("Lanzando 20 hilos para verificar los numeros...");
                    for (Thread t : threads) {
                        t.start();
                    }
                    
                    // 4. Esperamos a que todos los hilos terminen su trabajo.
                    try {
                        for (Thread t : threads) {
                            t.join(); // El hilo principal espera a que cada hilo trabajador termine.
                        }
                    } catch (InterruptedException e) {
                        System.err.println("El proceso de espera fue interrumpido.");
                    }
                    
                    System.out.println("\nProceso completado. Todos los hilos han terminado.");
                    System.out.println("Revisa la lista actualizada con la opcion 3.");
                    break;
                
                default:
                    System.err.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        }
     }
}
