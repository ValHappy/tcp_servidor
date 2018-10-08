
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPConection {

	private Socket socket; // Variable para crear un socket
	private Receptor receptor; // Variable para crear un receptor
	private MessageObserver observer; // Variable para llamar la interfaz
	private Jugador jugador; // Variable para cargar atributos del jugador
	private Logica logica; // Variable para cargar atributos de logica
	private Paso hilo; // Variable para nombrar la clase hilo

	/**
	 * Constructor de la clase TCPConection
	 * 
	 * @param socket
	 * @param jugador
	 * @param logica
	 */
	public TCPConection(Socket socket, Jugador jugador, Logica logica) {
		this.socket = socket;
		this.receptor = new Receptor(socket);
		this.hilo = new Paso(socket, logica);
		hilo.start();
		this.jugador = jugador;
		this.logica = logica;
	}

	/**
	 * Metodo para enviar mensajes
	 * 
	 * @param mensaje
	 */
	public void send(String mensaje) {
		try {
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			os.writeUTF(mensaje);
		} catch (IOException e) {

		}
	}

	/**
	 * Interfaz observer
	 * 
	 * @author Happy
	 *
	 */
	public interface MessageObserver {
		void onDataReceiver(String mensaje);
	}

	public void setListener(MessageObserver observer) {
		this.observer = observer;
	}

	/**
	 * Metodo para activar el hilo del receptor ._."
	 */
	public void activarRecepcion() {
		receptor.start();
	}

	private class Receptor extends Thread {
		Socket socket; // Variable para crear un socket

		/**
		 * Constructor de la clase Receptor
		 * 
		 * @param socket
		 */
		public Receptor(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Metodo para correr el hilo de receptor
		 * 
		 */
		@Override
		public void run() {
			try {
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				while (true) {
					System.out.println("Esperando mensaje");
					String mensajeAndroid = dis.readUTF();
					jugador.move(mensajeAndroid);
					System.out.println("termina de mover");
				}
			} catch (IOException e) {
				System.out.println("Cliente desconectado: " + e.getMessage());
			}
		}
	}

	private class Paso extends Thread {
		Socket socket; // Variable para crear un socket
		Boolean parar; // Variable para detener el while
		Logica logica; // Variable para llamar atributos

		/**
		 * Constructor de la clase Paso para pasar la pantalla en Android
		 * 
		 * @param socket
		 * @param logica
		 */
		public Paso(Socket socket, Logica logica) {
			this.socket = socket;
			parar = false;
			this.logica = logica;
		}

		/**
		 * Metodo para correr el hilo
		 */
		@Override
		public void run() {
			while (!parar) {
				System.out.println("P----------------");
				if (logica.getPantallas() == 5 || logica.getPantallas() == 6 || logica.getPantallas() == 7) {
					System.out.println("Pasa de pantalla");
					parar = true;
					try {
						DataOutputStream os = new DataOutputStream(socket.getOutputStream());
						os.writeUTF("EndGame");
					} catch (IOException e) {

					}
				}
			}
		}
	}
}
