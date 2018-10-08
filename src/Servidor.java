import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

import processing.core.PApplet;

public class Servidor extends Thread {

	private ServerSocket server;	//
	private PApplet app;	//Cargar
	private Logica logica;	//Cargar logica  para pedir
	private ArrayList<TCPConection> conexiones;
	private TCPConection.MessageObserver observer;
	
	private int nopantalla;
	private int conectados;

	/**
	 * Constructor del Servidor
	 * @param app processing
	 */
	public Servidor(PApplet app) {
		this.app = app;
		logica = new Logica(app);
		nopantalla = 0;
		conectados = 0;
		try {
			conexiones = new ArrayList<>();
			server = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Corre el hilo del servidor
	 * 
	 * 
	 */
	@Override
	public void run() {
		System.out.println("Esperando conexión");
		try {
			Stack<Jugador> pila = new Stack<Jugador>();
			pila.push(logica.getJugador2());
			pila.push(logica.getJugador1());
			while (conectados < 2) {
				Socket socket = server.accept();
				System.out.println("Conexion aceptada");
				conectados++;
				logica.setConectados(conectados);
				Jugador jugador = pila.pop();
				TCPConection conection = new TCPConection(socket, jugador, logica);
				conection.setListener(observer);
				conection.activarRecepcion();
				conexiones.add(conection);
			}	
			for (TCPConection conexion : conexiones) {
				conexion.send("Pasamos a la siguiente pantalla");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Logica getLogica() {
		return logica;
	}
}
