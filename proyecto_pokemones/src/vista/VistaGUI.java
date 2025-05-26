package vista;

import controlador.Batalla;
import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
import models.ataques.Ataque;
import models.pokemones.CreacionPokemones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


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
        add(panelBotones, BorderLayout.SOUTH);

        inicializarJuego();
        actualizarBotonesAtaque();
    }

    private void inicializarJuego() {
        String nombrejugador = JOptionPane.showInputDialog(this, "Ingresa el nombre del primer entrenador:");
        String nombreRival = JOptionPane.showInputDialog(this, "Ingresa el nombre del segundo entrenador:");
        jugador = new Entrenador(nombrejugador);
        rival = new Entrenador(nombreRival);
        Pokemon[] disponibles = CreacionPokemones.obtenerPokemonesDisponibles();
        jugador.asignarEquipoAleatorio(disponibles);
        rival.asignarEquipoAleatorio(disponibles);
           
        batalla = new Batalla(jugador, rival, this);
        imagenJugador.setText(jugador.getNombre());
        imagenRival.setText(rival.getNombre());

        areaTexto.setText("¡-----------------Bienvenido a la batalla Pokémon!--------------------\n");
        areaTexto.append("Entrenador " + jugador.getNombre() + " 🆚 Entrenador " + rival.getNombre() + "\n");
        areaTexto.append(nombrejugador + " Recibió su equipo: \n");
        for (Pokemon pokemon : jugador.getEquipo()) {
            areaTexto.append(pokemon.toString() + "\n");
        }
        areaTexto.append(nombreRival + " Recibió su equipo: \n");
        for (Pokemon pokemon : rival.getEquipo()) {
            areaTexto.append(pokemon.toString() + "\n");
        }

        areaTexto.append("¡⚔️La batalla comienza⚔️!\n");
        batalla.iniciar();
    }

    public void mostrarVictoria(String nombreGanador) {
    areaTexto.append("¡" + nombreGanador + " gana la batalla!\n");
    JOptionPane.showMessageDialog(this,
            "🎉 ¡" + nombreGanador + " ha ganado la batalla! 🎉",
            "¡Victoria!",
            JOptionPane.INFORMATION_MESSAGE);
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
