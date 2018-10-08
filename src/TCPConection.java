
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
	private Socket socket;

	private Receptor receptor;

	private MessageObserver observer;

	private Jugador jugador;
	private Logica logica;
	private Muerte hilo;

	public TCPConection(Socket socket, Jugador jugador, Logica logica) {
		this.socket = socket;
		this.receptor = new Receptor(socket);
		this.hilo = new Muerte(socket, logica);
		hilo.start();
		this.jugador = jugador;
		this.logica = logica;
	}

	public void send(String mensaje) {
		try {
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			os.writeUTF(mensaje);
		} catch (IOException e) {

		}
	}

	public interface MessageObserver {
		void onDataReceiver(String mensaje);
	}

	public void setListener(MessageObserver observer) {
		this.observer = observer;
	}

	public void activarRecepcion() {
		receptor.start();
	}

	private class Receptor extends Thread {
		Socket socket;

		public Receptor(Socket socket) {
			this.socket = socket;
		}

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

	// private class Muerte extends Thread {
	// Socket socket;
	// Boolean parar;
	// Jugador jugador;
	//
	// public Muerte(Socket socket, Jugador jugador) {
	// this.socket = socket;
	// parar = false;
	// this.jugador = jugador;
	// }
	//
	// @Override
	// public void run() {
	// while (!parar) {
	// System.out.println("--------------------------");
	// System.out.println(jugador.getVida());
	// if (jugador.getVida() == 0) {
	// System.out.println("MUERTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
	// parar = true;
	// try {
	// DataOutputStream os = new DataOutputStream(socket.getOutputStream());
	// os.writeUTF("muerte");
	// } catch (IOException e) {
	//
	// }
	// }
	// }
	// }
	// }

	// here
	private class Muerte extends Thread {
		Socket socket;
		Boolean parar;
		Logica logica;

		public Muerte(Socket socket, Logica logica) {
			this.socket = socket;
			parar = false;
			this.logica = logica;
		}

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
