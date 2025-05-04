package gui;

import models.entrenadores.Entrenador;
import models.ataques.Ataque;
import models.pokemones.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class BattleGUI extends JFrame {
    private JTextField entrenador1Field;
    private JTextField entrenador2Field;
    private int turnoActual= 0;
    private int pokemonTurnoIndex= 0;
    private JButton [] attackButtons;
    private JButton iniciarButton;
    private JTextArea logArea;
    private JPanel ataquePanel;
    private Pokemon atacante;
    private Pokemon defensor;

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    
    private Pokemon[] disponibles;

    public BattleGUI() {
        try {
            setTitle("Batalla Pokémon");
            setSize(800, 500);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            System.out.println("Inicializando la interfaz gráfica...");

            initUI();
        

            inicializarPokemonesDisponibles();
            
            setVisible(true);//para las ventanas Swing 

            System.out.println("BattleGUI se ha inicializado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al inicializar la interfaz: " + e.getMessage());
        }
    }



    private void initUI() {
        setLayout(new BorderLayout());

        // Panel superior para ingresar nombres
        JPanel topPanel = new JPanel(new FlowLayout());//

        entrenador1Field = new JTextField(10);
        entrenador2Field = new JTextField(10);
        iniciarButton = new JButton("Iniciar Batalla");
        iniciarButton.setBackground(Color.GREEN);//color boton para iniciar batalla






        topPanel.add(new JLabel("Entrenador 1:"));
        topPanel.add(entrenador1Field);
        topPanel.add(new JLabel("Entrenador 2:")); 
        topPanel.add(entrenador2Field);
        topPanel.add(iniciarButton);
        topPanel.setBackground(Color.YELLOW);//color panel entrenadores 
        topPanel.setBorder(BorderFactory.createTitledBorder("Bienvenidos a la Batalla Pokémon"));//titulo






        add(topPanel, BorderLayout.NORTH);

        // Área de batalla
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior de ataques
        JPanel ataquePanel = new JPanel(new FlowLayout());
        attackButtons = new JButton[4];
        for(int i= 0; i<4; i++){
            attackButtons[i] = new JButton("Ataque " + (i+1));
            attackButtons[i].setVisible(false);
            ataquePanel.add(attackButtons[i]);
        }
        add(ataquePanel, BorderLayout.SOUTH);

        iniciarButton.addActionListener(e -> iniciarBatalla());
    }

    private void inicializarPokemonesDisponibles() {
        try {
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

            System.out.println("Pokémon disponibles inicializados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al inicializar los Pokémon: " + e.getMessage());
        }
    }

    private void iniciarBatalla() {
        try {
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

            turnoActual= 0;
            pokemonTurnoIndex= 0;
            mostrarEstadoActual();


            mostrarOpcionesDeAtaque();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al iniciar la batalla: " + e.getMessage());
        }

    }

    private void mostrarEstadoActual(){
        atacante = turnoActual % 2 == 0
                ? entrenador1.getEquipo().get(pokemonTurnoIndex)
                : entrenador2.getEquipo().get(pokemonTurnoIndex);

        defensor = turnoActual % 2 == 0
                ? entrenador2.getEquipo().get(pokemonTurnoIndex)
                : entrenador1.getEquipo().get(pokemonTurnoIndex);
    
        log("\n---------------- Estado actual -----------------\n");

        log(entrenador1.getNombre() + ": " + entrenador1.getEquipo().get(pokemonTurnoIndex));
        log("\n" + entrenador2.getNombre() + ": " + entrenador2.getEquipo().get(pokemonTurnoIndex));
        log("\nTurno de " + (turnoActual % 2 == 0 ? entrenador1.getNombre() : entrenador2.getNombre()) + "\n");


    }
    private void mostrarOpcionesDeAtaque() {

        // lo comentado es la implementacion anterior pero que no concordaba con la logica definida
        //borrarlo cuando sea necesario
        /* try {
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
                ataqueBtn.addActionListener(_ -> {
                    p1.atacar(p2, index);
                    if (p2.getPuntos_de_salud() <= 0) {
                        log(p2.getNombre() + " ha sido derrotado.\n");
                        entrenador2.getEquipo().remove(p2);
                    }
                    if (!entrenador2.equipoDerrotado()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al mostrar las opciones de ataque: " + e.getMessage());
        }*/
        List<Ataque> ataques = atacante.getAtaques();
        for (int i = 0; i < attackButtons.length; i++) {
            attackButtons[i].removeActionListener(attackButtons[i].getActionListeners().length > 0 ? attackButtons[i].getActionListeners()[0] : null);
            if (i < ataques.size()) {
                Ataque atk = ataques.get(i);
                attackButtons[i].setText(atk.getNombre());
                attackButtons[i].setVisible(true);
                attackButtons[i].addActionListener(e -> ejecutarAtaque(atk));
            } else {
                attackButtons[i].setVisible(false);
            }
        }  

       
    }


    private void ejecutarAtaque(Ataque ataque) {

        String resultado = atacante.atacarConAtaque(defensor, ataque);
    log(resultado);  // <--- esto muestra el mensaje en la interfaz

    if (defensor.getPuntos_de_salud() <= 0) {
        log(defensor.getNombre() + " ha sido derrotado.\n");
        if (turnoActual % 2 == 0) {
            entrenador2.getEquipo().remove(pokemonTurnoIndex);
        } else {
            entrenador1.getEquipo().remove(pokemonTurnoIndex);
        }
    }

    if (entrenador1.equipoDerrotado() || entrenador2.equipoDerrotado()) {
        String ganador = entrenador1.equipoDerrotado() ? entrenador2.getNombre() : entrenador1.getNombre();
        log("\n¡La batalla ha terminado! Ganador: " + ganador);

        int opcion = JOptionPane.showOptionDialog(
            this,
            "Ganador: " + ganador + "\n¿Deseas jugar otra vez?",
            "Fin de la batalla",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Reiniciar", "Salir"},
            "Reiniciar"
        );

        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarBatalla();
        } else {
            System.exit(0);
        }

        return;
    }
        turnoActual++;
        mostrarEstadoActual();
        mostrarOpcionesDeAtaque();
    }


    private void reiniciarBatalla() {
        entrenador1.getEquipo().clear();
        entrenador2.getEquipo().clear();
        turnoActual = 0;
        pokemonTurnoIndex = 0;
        logArea.setText("");
        for (JButton btn : attackButtons) {
            btn.setVisible(false);
        }
        entrenador1Field.setText("");
        entrenador2Field.setText("");
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
        SwingUtilities.invokeLater(() -> {
            BattleGUI gui = new BattleGUI();
            gui.setVisible(true);
        });
    }
}