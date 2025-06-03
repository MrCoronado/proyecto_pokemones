package vista;


import controlador.Batalla;
import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
//import models.pokemones.Pokemon.TipoPokemon;
import models.ataques.Ataque;
import models.pokemones.CreacionPokemones;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Random;
//import java.awt.event.ActionListener;
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
        JPanel panelsuperior= new JPanel();
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
            // Redimensionar la imagen
            Image imagenEscaladaJugador = imagenIconJugador.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            Image imagenEscaladaRival = imagenIconRival.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            // Crear un nuevo ImageIcon con la imagen escalada
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
        barraSaludJugador.setValue(100); // Valor inicial de salud
        barraSaludJugador.setStringPainted(true);
        barraSaludJugador.setForeground(Color.GREEN);
        panelsuperior.add(barraSaludJugador);
        barraSaludRival = new JProgressBar(0, 100);
        barraSaludRival.setValue(100); // Valor inicial de salud
        barraSaludRival.setStringPainted(true);
        barraSaludRival.setForeground(Color.GREEN);
        panelsuperior.add(barraSaludRival);

        add(panelsuperior, BorderLayout.NORTH);


        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        
        botonAtaque = new JButton("Atacar");
        
            botonAtaque.addActionListener((ActionEvent _) -> ejecutarTurno(0));
            panelBotones.add(botonAtaque);
        add(panelBotones, BorderLayout.SOUTH);

        inicializarJuego();
        actualizarBotonesAtaque();
    }

    

    


private void inicializarJuego(){
    String nombrejugador = JOptionPane.showInputDialog(this, "Ingresa el nombre del primer entrenador:");
    String nombreRival = JOptionPane.showInputDialog(this, "Ingresa el nombre del segundo entrenador:");
    jugador = new Entrenador(nombrejugador);
    rival = new Entrenador(nombreRival);
    Pokemon[] disponibles = CreacionPokemones.obtenerPokemonesDisponibles();
    for ( int i= 0;i<3;i++){
        jugador.agregarPokemon(CreacionPokemones.crearNuevoPokemon(disponibles[i]));
        rival.agregarPokemon(CreacionPokemones.crearNuevoPokemon(disponibles[i+3]));
    }
    batalla = new Batalla(jugador, rival, this);
    imagenJugador.setText(jugador.getNombre());  // Actualiza el texto del JLabel con el nombre del jugador
    imagenRival.setText(rival.getNombre());


    areaTexto.setText("¡-----------------Bienvenido a la batalla Pokémon!--------------------\n");
    areaTexto.append("Entrenador " + jugador.getNombre() + " vs Entrenador " + rival.getNombre() + "\n");
    areaTexto.append(nombrejugador + " Recibio su equipo: \n");
    for (Pokemon pokemon : jugador.getEquipo()) {
        areaTexto.append(pokemon.toString() + "\n");
    }
    areaTexto.append(nombreRival + " Recibio su equipo: \n");
    for (Pokemon pokemon : rival.getEquipo()) {
        areaTexto.append(pokemon.toString() + "\n");
    }



    areaTexto.append("¡⚔️La batalla comienza⚔️!\n");
    batalla = new Batalla(jugador, rival, this);
    batalla.iniciar();

}

private void ejecutarTurno(int indiceAtaque){
    if(jugador.equipoDerrotado() || rival.equipoDerrotado()){
        areaTexto.append("❌ La batalla ha terminado.\n");
        return;
    }
    Pokemon atacante = jugador.obtenerPokemonActivo();
    Pokemon defensor = rival.obtenerPokemonActivo();

    String mensajeJugador = atacante.atacar(defensor, indiceAtaque);
    areaTexto.append(mensajeJugador + "\n");

    // Actualizar la barra de salud
    barraSaludJugador.setValue((defensor.getPuntos_de_salud() ));

    if(!rival.equipoDerrotado()){
        Ataque ataqueRival = defensor.getAtaques().get(0); // Ataque por defecto
        String mensajeRival = ataqueRival.aplicarAtaque(defensor, atacante);
        areaTexto.append(mensajeRival + "\n");
    }

    // Actualizar la barra de salud
    barraSaludRival.setValue((atacante.getPuntos_de_salud() ));
    
    if(jugador.equipoDerrotado()){
        areaTexto.append("¡" + rival.getNombre() + " gana la batalla!\n");
    } else if(rival.equipoDerrotado()){
        areaTexto.append("¡" + jugador.getNombre() + " gana la batalla!\n");
    }
    actualizarBotonesAtaque();
}

private void actualizarBotonesAtaque(){
    Pokemon pokemonActivo = jugador.obtenerPokemonActivo();
    if(pokemonActivo == null || pokemonActivo.getAtaques().isEmpty()){
        botonAtaque.setEnabled(false);
        botonAtaque.setText("Sin ataque");
        
    }else{
        Ataque ataque = pokemonActivo.getAtaques().get(0);
        botonAtaque.setEnabled(true);
        //botonAtaque.setText("Atacar");
        botonAtaque.setText(ataque.getNombre());
        botonAtaque.setBackground(Color.RED);
        botonAtaque.setForeground(Color.WHITE);
        botonAtaque.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 16));
        botonAtaque.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        botonAtaque.setPreferredSize(new Dimension(150, 50));
        botonAtaque.setFocusPainted(false);
        botonAtaque.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }

    
}

public void mostrarMensaje(String mensaje) {
    areaTexto.append(mensaje + "\n");
}

public int pedirOpcionAtaque(Pokemon atacante) {
    // aca se cambia para q funcione como GUI
    return JOptionPane.showOptionDialog(this, 
    "Selecciona un ataque para " + atacante.getNombre(),
    "Seleccionar Ataque", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            atacante.getAtaques().toArray(), atacante.getAtaques().get(0));
}

public void mostrarEstadoPokemon(String nombreEntrenador, Pokemon pokemon) {
    areaTexto.append(nombreEntrenador + ": " + pokemon.getNombre() + " (HP: " + pokemon.getPuntos_de_salud() + ")\n");
}






public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        VistaGUI vista = new VistaGUI();
        vista.setVisible(true);
    });
}

}