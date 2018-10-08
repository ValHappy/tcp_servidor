import processing.core.PApplet;
import processing.core.PImage;

public class Bala {

	private PApplet app; // Variable para cargar la clase PApplet
	private float x; // Variable para darle una posición x
	private float y; // Variable para darle una posición y
	private PImage bala; // Variable para cargar la imagen de la bala
	private int dir; // Variable para darle una dirección a la bala
	private int vel; // Variable dar una velocidad a la bala

	/**
	 * Constructor para la clase Bala
	 * 
	 * @param app
	 * @param x
	 * @param y
	 * @param dir
	 */
	public Bala(PApplet app, float x, float y, int dir) {
		this.app = app;
		this.x = x;
		this.y = y;
		this.dir = dir;
		vel = 7;
		bala = app.loadImage("bala.png");
	}

	/**
	 * Metodo para pintar la bala
	 */
	public void pintar() {
		app.image(bala, x, y);
	}

	/**
	 * Metodo para mover la bala en cierta dirección
	 */
	public void mover() {
		switch (dir) {
		case 1:
			// Arriba
			y -= vel;
			break;

		case 2:
			// Abajo
			y += vel;
			break;

		case 3:
			// Izquierda
			x -= vel;
			break;

		case 4:
			// Derecha
			x += vel;
			break;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
