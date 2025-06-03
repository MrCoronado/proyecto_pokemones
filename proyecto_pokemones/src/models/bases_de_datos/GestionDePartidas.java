package models.bases_de_datos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.entrenadores.Entrenador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class GestionDePartidas {
    private static final String ARCHIVO = "partida_guardada.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static boolean guardarPartida(Entrenador jugador, Entrenador rival) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(new Entrenador[]{jugador, rival}, writer);
            System.out.println("Partida guardada correctamente." + ARCHIVO);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Entrenador[] cargarPartida() {
        try (FileReader reader = new FileReader(ARCHIVO)) {
            return gson.fromJson(reader, Entrenador[].class);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        
    }
    
}
