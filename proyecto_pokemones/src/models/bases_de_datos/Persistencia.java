package models.bases_de_datos;

import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;

import java.io.*;
import java.util.List;

public class Persistencia {

    public static void guardarEntrenador(Entrenador entrenador, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(entrenador.getNombre());
            writer.newLine();
            for (Pokemon p : entrenador.getEquipo()) {
                writer.write(p.serializar());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }

    public static Entrenador cargarEntrenador(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String nombreEntrenador = reader.readLine();
            Entrenador entrenador = new Entrenador(nombreEntrenador);

            String linea;
            while ((linea = reader.readLine()) != null) {
                Pokemon p = Pokemon.deserializar(linea);
                entrenador.agregarPokemon(p);
            }

            return entrenador;
        } catch (IOException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }
        return null;
    }
}