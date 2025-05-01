package gui;

import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
import models.ataques.Ataque;
import models.pokemones.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class BattleGUI extends JFrame {
    private JTextField entrenador1Field;
    private JTextField entrenador2Field;
    private JButton iniciarButton;
    private JTextArea logArea;
    private JPanel ataquePanel;

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon[] disponibles;

    public BattleGUI() {
        setTitle("Batalla Pokémon");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        inicializarPokemonesDisponibles();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel superior para ingresar nombres
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Entrenador 1:"));
        entrenador1Field = new JTextField(10);
        topPanel.add(entrenador1Field);

        topPanel.add(new JLabel("Entrenador 2:"));
        entrenador2Field = new JTextField(10);
        topPanel.add(entrenador2Field);

        iniciarButton = new JButton("Iniciar Batalla");
        topPanel.add(iniciarButton);

        add(topPanel, BorderLayout.NORTH);

        // Área de batalla
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior de ataques
        ataquePanel = new JPanel();
        add(ataquePanel, BorderLayout.SOUTH);

        iniciarButton.addActionListener(e -> iniciarBatalla());
    }

    private void inicializarPokemonesDisponibles() {
        Ataque latigoCepa = new Ataque("Látigo Cepa", 10, "Especial", "Planta");
        Ataque lanzallamas = new Ataque("Lanzallamas", 10, "Especial", "Fuego");
        Ataque trueno = new Ataque("Trueno", 10, "Especial", "Electrico");
        Ataque hidrobomba = new Ataque("Hidrobomba", 10, "Especial", "Agua");
        Ataque rayoHielo = new Ataque("Rayo Hielo", 10, "Especial", "Hielo");
        Ataque terremoto = new Ataque("Terremoto", 10, "Especial", "Tierra");

        disponibles = new Pokemon[]{
            new PokemonPlanta("Roselia", 100, List.of(latigoCepa), 50, 45, 65, 50, 60),
            new PokemonFuego("Charmander", 100, List.of(lanzallamas), 52, 43, 60, 50, 65),
            new PokemonElectrico("Pikachu", 100, List.of(trueno), 55, 40, 50, 50, 90),
            new PokemonAgua("Squirtle", 100, List.of(hidrobomba), 48, 65, 50, 64, 43),
            new PokemonHielo("Articuno", 100, List.of(rayoHielo), 85, 100, 95, 125, 85),
            new PokemonTierra("Garchomp", 100, List.of(terremoto), 130, 95, 80, 85, 102)
        };
    }

    private void iniciarBatalla() {
        String nombre1 = entrenador1Field.getText().trim();
        String nombre2 = entrenador2Field.getText().trim();

        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa los nombres de ambos entrenadores.");
            return;
        }

        entrenador1 = new Entrenador(nombre1);
        entrenador2 = new Entrenador(nombre2);

        seleccionarEquipoAleatorio(entrenador1);
        seleccionarEquipoAleatorio(entrenador2);

        logArea.setText("");
        log("¡Comienza la batalla Pokémon entre " + nombre1 + " y " + nombre2 + "!\n");

        mostrarOpcionesDeAtaque();
    }

    private void mostrarOpcionesDeAtaque() {
        ataquePanel.removeAll();
        ataquePanel.setLayout(new FlowLayout());

        if (entrenador1.equipoDerrotado() || entrenador2.equipoDerrotado()) {
            log("\n¡La batalla ha terminado!\n");
            String ganador = entrenador1.equipoDerrotado() ? entrenador2.getNombre() : entrenador1.getNombre();
            log("Ganador: " + ganador);
            return;
        }

        Pokemon p1 = entrenador1.obtenerPokemonActivo();
        Pokemon p2 = entrenador2.obtenerPokemonActivo();

        log("Turno de " + entrenador1.getNombre() + " con " + p1.getNombre() + " (HP: " + p1.getPuntos_de_salud() + ")\n");
        log("Rival: " + p2.getNombre() + " (HP: " + p2.getPuntos_de_salud() + ")\n");

        for (int i = 0; i < p1.getAtaques().size(); i++) {
            int index = i;
            JButton ataqueBtn = new JButton(p1.getAtaques().get(i).getNombre());
            ataqueBtn.addActionListener(e -> {
                p1.atacar(p2, index);
                if (p2.getPuntos_de_salud() <= 0) {
                    log(p2.getNombre() + " ha sido derrotado.\n");
                    entrenador2.getEquipo().remove(p2);
                }
                if (!entrenador2.equipoDerrotado()) {
                    // Turno rival automático (elige primer ataque)
                    Pokemon rival = entrenador2.obtenerPokemonActivo();
                    Pokemon objetivo = entrenador1.obtenerPokemonActivo();
                    rival.atacar(objetivo, 0);
                    if (objetivo.getPuntos_de_salud() <= 0) {
                        log(objetivo.getNombre() + " ha sido derrotado.\n");
                        entrenador1.getEquipo().remove(objetivo);
                    }
                }
                mostrarOpcionesDeAtaque();
            });
            ataquePanel.add(ataqueBtn);
        }

        ataquePanel.revalidate();
        ataquePanel.repaint();
    }

    private void seleccionarEquipoAleatorio(Entrenador entrenador) {
        List<Integer> usados = new ArrayList<>();
        Random rand = new Random();
        while (entrenador.getEquipo().size() < 3) {
            int idx;
            do {
                idx = rand.nextInt(disponibles.length);
            } while (usados.contains(idx));
            usados.add(idx);
            entrenador.agregarPokemon(clonarPokemon(disponibles[idx]));
        }
    }

    private Pokemon clonarPokemon(Pokemon p) {
        return switch (p.getTipo()) {
            case PLANTA -> new PokemonPlanta(p.getNombre(), 100, p.getAtaques(), p.getAtaque(), p.getDefensa(), p.getAtaqueEspecial(), p.getDefensaEspecial(), p.getVelocidad());
            case FUEGO -> new PokemonFuego(p.getNombre(), 100, p.getAtaques(), p.getAtaque(), p.getDefensa(), p.getAtaqueEspecial(), p.getDefensaEspecial(), p.getVelocidad());
            case ELECTRICO -> new PokemonElectrico(p.getNombre(), 100, p.getAtaques(), p.getAtaque(), p.getDefensa(), p.getAtaqueEspecial(), p.getDefensaEspecial(), p.getVelocidad());
            case AGUA -> new PokemonAgua(p.getNombre(), 100, p.getAtaques(), p.getAtaque(), p.getDefensa(), p.getAtaqueEspecial(), p.getDefensaEspecial(), p.getVelocidad());
            case HIELO -> new PokemonHielo(p.getNombre(), 100, p.getAtaques(), p.getAtaque(), p.getDefensa(), p.getAtaqueEspecial(), p.getDefensaEspecial(), p.getVelocidad());
            case TIERRA -> new PokemonTierra(p.getNombre(), 100, p.getAtaques(), p.getAtaque(), p.getDefensa(), p.getAtaqueEspecial(), p.getDefensaEspecial(), p.getVelocidad());
        };
    }

    private void log(String mensaje) {
        logArea.append(mensaje);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BattleGUI().setVisible(true));
    }
}
