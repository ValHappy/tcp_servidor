import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

import processing.core.PApplet;

public class Servidor extends Thread {

	private ServerSocket server; // Variable para montar el servidor
	private PApplet app; // Variable para cargar la clase PApplet
	private Logica logica; // Variable para pedir atributos
	private ArrayList<TCPConection> conexiones; // Variable para almacenar las conexiones
	private TCPConection.MessageObserver observer; // Implementacion de la interfaz observable
	private int conectados; // Variable para el conteo de clientes actuales

	/**
	 * Constructor del Servidor
	 * 
	 * @param app
	 *            processing
	 */
	public Servidor(PApplet app) {
		this.app = app;
		logica = new Logica(app);
		conectados = 0;
		try {
			conexiones = new ArrayList<>();
			server = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Corre el hilo del servidor Primero espera una conexión, luego en el orden
	 * en el que entran las conexiones las cuenta (2) para pasar de pantalla y
	 * asigna los jugadores en el orden en el que se contectaron
	 * 
	 */
	@Override
	public void run() {
		System.out.println("Esperando conexión...");
		try {
			Stack<Jugador> pila = new Stack<Jugador>();
			pila.push(logica.getJugador2());
			pila.push(logica.getJugador1());

			while (conectados < 2) {
				Socket socket = server.accept();

				System.out.println("Conexion aceptada!");
				conectados++;
				logica.setConectados(conectados);

				Jugador jugador = pila.pop();

				TCPConection conection = new TCPConection(socket, jugador, logica);
				conection.setListener(observer);
				conection.activarRecepcion();
				conexiones.add(conection);
			}

			for (TCPConection conexion : conexiones) {
				conexion.send("Pasamos a la siguiente pantalla :3");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Logica getLogica() {
		return logica;
	}
}
