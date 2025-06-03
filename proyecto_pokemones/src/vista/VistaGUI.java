package vista;

import controlador.Batalla;
import models.bases_de_datos.GestionDePartidas;
import models.bases_de_datos.HashPokemones;
import models.bases_de_datos.ListaTurnos;
import models.bases_de_datos.Persistencia;
import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
import models.ataques.Ataque;
import models.pokemones.CreacionPokemones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;



public class VistaGUI extends JFrame implements Vista {
    private JTextArea areaTexto;
    private JButton botonAtaque;
    private Batalla batalla;
    private Entrenador jugador;
    private Entrenador rival;
    private JProgressBar barraSaludJugador;
    private JProgressBar barraSaludRival;
    private JLabel imagenJugador;
    private JLabel imagenRival;
    private HashPokemones hashPokemones = new HashPokemones();
    private Persistencia persistencia = new Persistencia();

    public VistaGUI() {
        setTitle("Batalla Pokémon");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.LIGHT_GRAY);
        areaTexto.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);
        areaTexto.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 16));

        JPanel panelsuperior = new JPanel();
        panelsuperior.setLayout(new GridLayout(2, 2));

        imagenJugador = new JLabel("Entrenador 1");
        imagenRival = new JLabel("Entrenador 2");

        imagenJugador.setHorizontalAlignment(SwingConstants.CENTER);
        imagenRival.setHorizontalAlignment(SwingConstants.CENTER);
        imagenJugador.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 19));
        imagenRival.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 19));
        imagenJugador.setForeground(Color.BLUE);
        imagenRival.setForeground(Color.RED);

        try {
            ImageIcon imagenIconJugador = new ImageIcon(getClass().getResource("/imagenes/Entrenador1.png"));
            ImageIcon imagenIconRival = new ImageIcon(getClass().getResource("/imagenes/Entrenador2.png"));
            Image imagenEscaladaJugador = imagenIconJugador.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            Image imagenEscaladaRival = imagenIconRival.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagenIconJugador = new ImageIcon(imagenEscaladaJugador);
            imagenIconRival = new ImageIcon(imagenEscaladaRival);
            imagenJugador.setIcon(imagenIconJugador);
            imagenRival.setIcon(imagenIconRival);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen del jugador: " + e.getMessage());
        }

        panelsuperior.add(imagenJugador);
        panelsuperior.add(imagenRival);

        barraSaludJugador = new JProgressBar(0, 100);
        barraSaludJugador.setValue(100);
        barraSaludJugador.setStringPainted(true);
        barraSaludJugador.setForeground(Color.GREEN);
        panelsuperior.add(barraSaludJugador);

        barraSaludRival = new JProgressBar(0, 100);
        barraSaludRival.setValue(100);
        barraSaludRival.setStringPainted(true);
        barraSaludRival.setForeground(Color.GREEN);
        panelsuperior.add(barraSaludRival);

        add(panelsuperior, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        botonAtaque = new JButton("Atacar");
        botonAtaque.addActionListener((ActionEvent _) -> batalla.realizarTurno());
        panelBotones.add(botonAtaque);

        //boton para guardar partida
        JButton botonGuardar = new JButton("Guardar Partida");
        botonGuardar.addActionListener((ActionEvent _) -> {
        if(batalla != null && jugador != null && rival != null) {
            boolean exito = GestionDePartidas.guardarPartida(jugador, rival);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Partida guardada correctamente.", "Guardar Partida", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la partida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay partida para guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        });
        panelBotones.add(botonGuardar);
        botonGuardar.setBackground(Color.GREEN);
        botonGuardar.setForeground(Color.BLACK);
        botonGuardar.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 16));
        botonGuardar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        botonGuardar.setPreferredSize(new Dimension(150, 50));
    

        //boton para salir del juego
        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener((ActionEvent _) -> {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres salir del juego?", "Salir", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panelBotones.add(botonSalir);


        add(panelBotones, BorderLayout.SOUTH);

        inicializarJuego();
        actualizarBotonesAtaque();
    }
    private void inicializarJuego() {

        int opcion = JOptionPane.showConfirmDialog(this, "¿Quieres cargar una partida guardada?", "Cargar Partida", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            Entrenador[] entrenadores = GestionDePartidas.cargarPartida();
            if (entrenadores != null && entrenadores.length == 2) {
                jugador = entrenadores[0];
                rival = entrenadores[1];
                mostrarMensaje("Partida cargada correctamente.");
            } else {
                mostrarMensaje("No se pudo cargar la partida. Se iniciará una nueva batalla.");
                pedirNombresYCrearEntrenadores();
            }
        } else {
            pedirNombresYCrearEntrenadores();
        }
        



        /*String nombrejugador = JOptionPane.showInputDialog(this, "Ingresa el nombre del primer entrenador:");
        String nombreRival = JOptionPane.showInputDialog(this, "Ingresa el nombre del segundo entrenador:");
        jugador = new Entrenador(nombrejugador);
        rival = new Entrenador(nombreRival);*/

        //implementacion de base de datos

        List<Pokemon> disponibles = persistencia.leerPokemones();
        for (Pokemon pokemon : disponibles) {
            hashPokemones.agregarPokemon(pokemon);
        }
        if (disponibles.isEmpty()) {
            mostrarMensaje("⚠️ No se encontraron Pokémon en la base de datos.");
            JOptionPane.showMessageDialog(this, "No hay Pokémon disponibles para la batalla.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if(opcion != JOptionPane.YES_OPTION) {
            // Si no se carga una partida, se asignan equipos aleatorios
            asignarEquipoAleatorio(jugador, new ArrayList<>(disponibles));
            asignarEquipoAleatorio(rival, new ArrayList<>(disponibles));
       
        }
        

        //Pokemon[] disponibles = CreacionPokemones.obtenerPokemonesDisponibles();
        //jugador.asignarEquipoAleatorio(disponibles);
        //rival.asignarEquipoAleatorio(disponibles);
           
        batalla = new Batalla(jugador, rival, this);
        imagenJugador.setText(jugador.getNombre());
        imagenRival.setText(rival.getNombre());

    areaTexto.setText("¡-----------------Bienvenido a la batalla Pokémon!--------------------\n");
    areaTexto.append("Entrenador " + jugador.getNombre() + " 🆚 Entrenador " + rival.getNombre() + "\n");
    areaTexto.append(jugador.getNombre() + " Recibio su equipo: \n");
    for (Pokemon pokemon : jugador.getEquipo()) {
        areaTexto.append(pokemon.toString() + "\n");
    }

    
    areaTexto.append(rival.getNombre() + " Recibio su equipo: \n");
    for (Pokemon pokemon : rival.getEquipo()) {
        areaTexto.append(pokemon.toString() + "\n");
    }
        areaTexto.append("¡⚔️La batalla comienza⚔️!\n");
        batalla.iniciar();
    }

    private void pedirNombresYCrearEntrenadores() {
        String nombreJugador = JOptionPane.showInputDialog(this, "Ingresa el nombre del primer entrenador:");
        String nombreRival = JOptionPane.showInputDialog(this, "Ingresa el nombre del segundo entrenador:");
        jugador = new Entrenador(nombreJugador != null && !nombreJugador.isEmpty() ? nombreJugador : "Jugador");
        rival = new Entrenador(nombreRival != null && !nombreRival.isEmpty() ? nombreRival : "Rival");
    }


    private void asignarEquipoAleatorio(Entrenador entrenador, List<Pokemon> disponibles){
        Collections.shuffle(disponibles);
        for (int i= 0; i<3 && i < disponibles.size(); i++) {
            Pokemon original = disponibles.get(i);
            Pokemon copia = new Pokemon(original.getNombre(), original.getPuntos_de_salud(), original.getTipo(), new ArrayList<>(original.getAtaques()), original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(), original.getDefensaEspecial(), original.getVelocidad());

            entrenador.agregarPokemon(copia);
        }
    }





    public void mostrarVictoria(String nombreGanador) {
    areaTexto.append("¡" + nombreGanador + " gana la batalla!\n");
    JOptionPane.showMessageDialog(this,
            "🎉 ¡" + nombreGanador + " ha ganado la batalla! 🎉",
            "¡Victoria!",
            JOptionPane.INFORMATION_MESSAGE);

            //guardar partida
    GestionDePartidas.guardarPartida(jugador, rival);
    preguntarReinicio();
}

    private void preguntarReinicio() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Quieres jugar otra vez?",
                "Reiniciar juego",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra la ventana actual
            VistaGUI nuevaVentana = new VistaGUI();
            nuevaVentana.setVisible(true);
        } else {
            System.exit(0); // Cierra la aplicación
        }
    }

    private void actualizarBotonesAtaque() {
    botonAtaque.setEnabled(true);
    botonAtaque.setText("Atacar");
    botonAtaque.setBackground(Color.RED);
    botonAtaque.setForeground(Color.WHITE);
    botonAtaque.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 16));
    botonAtaque.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    botonAtaque.setPreferredSize(new Dimension(150, 50));
    botonAtaque.setFocusPainted(false);
    botonAtaque.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
}

    public void mostrarMensaje(String mensaje) {
        areaTexto.append(mensaje + "\n");
    }

    public int pedirOpcionAtaque(Pokemon atacante) {
        return JOptionPane.showOptionDialog(this,
                "Selecciona un ataque para " + atacante.getNombre(),
                "Seleccionar Ataque",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                atacante.getAtaques().toArray(),
                atacante.getAtaques().get(0));
    }

    public void mostrarEstadoPokemon(String nombreEntrenador, Pokemon pokemon) {
        if (pokemon == null) return; 
        
        areaTexto.append(nombreEntrenador + ": " + pokemon.getNombre() + " (HP: " + pokemon.getPuntos_de_salud() + ")\n");

        if (nombreEntrenador.equals(jugador.getNombre())) {
            barraSaludJugador.setValue(pokemon.getPuntos_de_salud());
        } else {
            barraSaludRival.setValue(pokemon.getPuntos_de_salud());
        }

        actualizarBotonesAtaque();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaGUI vista = new VistaGUI();
            vista.setVisible(true);
        });
    }
}
