
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;
import processing.core.PApplet;
import processing.core.PImage;

public class Logica extends Thread {

	private PApplet app; // Variable para cargar la clase PApplet
	private Time tiempo; // Variable para cargar la clase Tiempo

	private Jugador jugador1; // Variable para crear el jugador 1
	private Jugador jugador2; // Variable para crear el jugador 2

	private ArrayList<Enemigo> enemigos; // Variable crear varios enemigos
	private ArrayList<Recuerdo> recuerdos; // Variable crear varios recuerdos

	// Variables para cargar las imagenes de pantallas
	private PImage p1;
	private PImage p2;
	private PImage p3;
	private PImage p5;
	private PImage p6;
	private PImage p7;
	private PImage escenario; // Variable para cargar el escenario

	private int usuariosConectados; // Variable para saber cuantos usuarios hay conectados
	private int pantallas = 1; // Variable para cambiar de pantalla

	/**
	 * Constructor de la clase Logica
	 * @param app
	 */
	public Logica(PApplet app) {
		this.app = app;
		tiempo = new Time(app);

		jugador1 = new Jugador(app, app.width / 2 - 50, app.height / 2, 1);
		jugador2 = new Jugador(app, app.width / 2 + 50, app.height / 2, 2);

		enemigos = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			enemigos.add(new Enemigo(app, app.random(-app.width, app.width), app.random(-app.height, app.height),
					(int) app.random(1, 3)));
		}

		usuariosConectados = 0;
		recuerdos = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			recuerdos.add(new Recuerdo(app, (i + 1)));
		}

		p1 = app.loadImage("ippc.png");
		p2 = app.loadImage("iniciopc.png");
		p3 = app.loadImage("instru.png");
		p5 = app.loadImage("hap.png");
		p6 = app.loadImage("sad.png");
		p7 = app.loadImage("op.png");
		escenario = app.loadImage("escenario.png");
	}

	/**
	 * Metodo para pintar cada pantalla
	 */
	public void pintar() {
		app.background(0);
		switch (pantallas) {
		case 1:
			// Inicio
			pintarPantalla1();
			break;

		case 2:
			// Ip
			pintarPantalla2();
			break;

		case 3:
			// Instrucciones
			pintarPantalla3();
			break;

		case 4:
			// Juego
			pintarPantalla4();
			break;

		case 5:
			// Puntaje
			pintarPantalla5();
			break;

		case 6:
			// Game Over los dos
			pintarPantalla6();
			break;

		case 7:
			// Se acabo el tiempo
			pintarPantalla7();
			break;
		}
	}

	/**
	 * Metodo para jugar con los personajes en el teclado
	 */
	public void teclado() {
		if (app.key == ' ') {
			jugador1.disparar();
		}

		if (app.key == 'X' || app.key == 'x') {
			jugador2.disparar();
		}

		jugador1.mover();
		jugador2.mover();
	}

	/**
	 * Metodo para dejar de jugar con los personajes con el teclado
	 */
	public void tecladoSoltado() {
		jugador1.soltar();
		jugador2.soltar();
	}

	/**
	 * Metodo para pintar la pantalla1 y pasar a la siguiente
	 */
	public void pintarPantalla1() {
		// Inicio
		app.image(p2, app.width / 2, app.height / 2);
		if (app.frameCount == 200) {
			pantallas++;
		}
	}

	/**
	 * Metodo para pintar la pantalla2, mostrar la IP, cuantos usuarios hay y pasar a la siguiente
	 */
	public void pintarPantalla2() {
		// IP
		app.image(p1, app.width / 2, app.height / 2);
		try {
			InetAddress ip = InetAddress.getLocalHost();
			// System.out.println("Mi direccion ip: " +
			// ip.toString().split("/")[1]);
			app.textAlign(app.CENTER);
			app.fill(255);
			app.textSize(80);
			app.text("Mi dirección IP: \n" + ip.toString().split("/")[1], app.width / 2, app.height / 2 - 50);
			app.textSize(50);
			app.text("Usuarios conectados: " + usuariosConectados, app.width / 2, app.height / 2 + 150);
			app.text("Se necesita que dos usuarios se conecten", app.width / 2, app.height / 2 + 300);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para pintar la pantalla3 las intrucciones
	 */
	public void pintarPantalla3() {
		// Instrucciones
		app.image(p3, app.width / 2, app.height / 2);
	}

	/**
	 * Metodo para pintar la pantalla4 y desarrollar todo el juego
	 */
	public void pintarPantalla4() {
		if (pantallas == 4) {
		// Juego
		app.image(escenario, jugador1.getCamx(), jugador1.getCamy());
		tiempo.draw();

		//Jugador 1
		if (jugador1.getVida() > 0) {
			jugador1.pintar();
			jugador1.caminando();
			jugador1.validarDistanciaObjeto(recuerdos);
			jugador1.matarEnemigos(enemigos);
			for (int i = 0; i < enemigos.size(); i++) {
				jugador1.validarDistanciaEnemigo(enemigos.get(i));
			}
		}

		//Jugador 2
		if (jugador2.getVida() > 0) {
			jugador2.setCamx(jugador1.getCamx());
			jugador2.setCamy(jugador1.getCamy());
			jugador2.pintar();
			jugador2.caminando();
			jugador2.validarDistanciaObjeto(recuerdos);
			jugador2.matarEnemigos(enemigos);
			for (int i = 0; i < enemigos.size(); i++) {
				jugador2.validarDistanciaEnemigo(enemigos.get(i));
			}
		}

		for (int i = 0; i < enemigos.size(); i++) {
			enemigos.get(i).pintar(jugador1);
		}

		for (int i = 0; i < recuerdos.size(); i++) {
			recuerdos.get(i).pintar(jugador1);
		}

		if (tiempo.getSeg_mostrados() <= 0) {
			pantallas = 7;
		}

		if (jugador1.getVida() == 0 && jugador2.getVida() == 0) {
			pantallas = 6;
		}

		if (jugador1.getPuntaje() + jugador2.getPuntaje() == 250) {
			pantallas = 5;
			}
		}
	}

	/**
	 * Metodo para pintar la pantalla5 y mostrar puntajes
	 */
	public void pintarPantalla5() {
		// Final
		app.image(p5, app.width / 2, app.height / 2);
		app.textAlign(app.CENTER);
		app.fill(255);
		app.textSize(90);
		app.text("Puntaje P1: " + jugador1.getPuntaje(), 600, app.height / 2 - 50);
		app.text("Puntaje P2: " + jugador2.getPuntaje(), 600, app.height / 2 + 100);
	}

	/**
	 * Metodo para pintar la pantalla6 y decir que perdió
	 */
	public void pintarPantalla6() {
		// GameOver
		app.image(p6, app.width / 2, app.height / 2);
		// System.out.println("GAME OVER GATITOS");
	}

	/**
	 * Metodo para pintar la pantalla7 y decir que se acabo el tiempo
	 */
	public void pintarPantalla7() {
		// Time
		app.image(p7, app.width / 2, app.height / 2);
		// System.out.println("JAKE MATE GATITO, SE TE ACABO EL TIEMPO");
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public int getPantallas() {
		return pantallas;
	}

	public void setPantallas(int pantallas) {
		this.pantallas = pantallas;
	}

	public void setConectados(int conectados) {
		this.usuariosConectados = conectados;
		if (usuariosConectados == 2) {
			setPantallas(4);
		}
	}

}
