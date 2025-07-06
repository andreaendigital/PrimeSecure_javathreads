/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.primesecure.primesecure;

import java.util.ArrayList;

/**
 * @author Andrea
 * Representa una lista que solo puede contener números primos.
 * Esta clase es "thread-safe" (segura para hilos) gracias al uso de 'synchronized'.
 */

// Heredamos toda la funcionalidad básica de una lista de enteros.
public class PrimeList extends ArrayList<Integer> {
       /**
     * Verifica si un número es primo.
     * Un número es primo si solo es divisible por 1 y por sí mismo.
     * @param number El número a verificar.
     * @return true si es primo, false en caso contrario.
     */
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false; // 1 y los números negativos no son primos.
        }
        // Optimizamos el bucle: solo necesitamos comprobar hasta la raíz cuadrada del número.
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false; // Si encontramos un divisor, no es primo.
            }
        }
        return true; // Si no encontramos divisores, es primo.
    }

    /**
     * Sobrescribe el método 'add' para asegurar que solo se agreguen primos.
     * La palabra clave 'synchronized' es CRUCIAL aquí.
     * 'synchronized' actúa como un candado. Solo un hilo puede ejecutar este método a la vez.
     * Si el Hilo A está agregando un número, el Hilo B debe esperar a que el Hilo A termine
     * antes de poder intentar agregar su propio número. Esto evita el caos.
     *
     * @param number El número que se intenta agregar.
     * @return true si se agregó con éxito.
     * @throws IllegalArgumentException si el número no es primo.
     */
    
    @Override
    public synchronized boolean add(Integer number) {
        if (isPrime(number)) {
            // Si es primo, llamamos al método 'add' original de ArrayList.
            return super.add(number);
        } else {
            // Si no es primo, lanzamos una excepción para señalar el error.
            throw new IllegalArgumentException("Error: El numero " + number + " no es primo y no puede ser agregado.");
        }
    }

    /**
     * Sobrescribe el método 'remove' para que también sea sincronizado.
     * Esto asegura que un hilo no intente eliminar un elemento mientras otro
     * hilo está, por ejemplo, contando los elementos. Mantiene la consistencia.
     */
    
    @Override
    public synchronized Integer remove(int index) {
        return super.remove(index);
    }
    
    /**
     * Devuelve la cantidad de primos en la lista de forma segura.
     * @return el tamaño de la lista.
     */
    public synchronized int getPrimesCount() {
        return this.size();
    }
}
