import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Main extends PApplet {

	private Servidor servidor; // Variable para cargar el servidor
	private Minim minim; // Variable para poner el audio
	private AudioPlayer musica; // Variable para definirlo

	/**
	 * Constructor de la clase Main
	 */
	public static void main(String[] args) {
		PApplet.main("Main");
	}

	/**
	 * Metodo para ajustes
	 */
	public void settings() {
		size(1200, 700);
	}

	/**
	 * Metodo para instanciar
	 */
	public void setup() {
		servidor = new Servidor(this);
		servidor.start();

		minim = new Minim(this);
		musica = minim.loadFile("Ghost.mp3");
	}

	/**
	 * Metodo para pintar
	 */
	public void draw() {
		background(255);
		if (!musica.isPlaying()) {
			musica.rewind();
			musica.play();
		}
		servidor.getLogica().pintar();
	}

	/**
	 * Metodo para uso del teclado
	 */
	public void keyPressed() {
		servidor.getLogica().teclado();
	}

	/**
	 * Metodo para uso del teclado
	 */
	public void keyReleased() {
		servidor.getLogica().tecladoSoltado();
	}

}
