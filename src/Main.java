import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Main extends PApplet {

	private Servidor servidor;	
	private Minim minim;
	private AudioPlayer musica;

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	public void settings() {
		size(1200, 700);
	}

	public void setup() {
		servidor = new Servidor(this);
		servidor.start();

		minim = new Minim(this);
		musica = minim.loadFile("Ghost.mp3");
	}

	public void draw() {
		background(255);
		if (!musica.isPlaying()) {
			musica.rewind();
			//musica.play();
		}

		servidor.getLogica().pintar();
	}

	public void keyPressed() {
		servidor.getLogica().teclado();
	}

	public void keyReleased() {
		servidor.getLogica().tecladoSoltado();
	}

}
