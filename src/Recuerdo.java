import java.util.concurrent.RecursiveAction;

import javafx.scene.shape.Ellipse;
import processing.core.PApplet;
import processing.core.PImage;

public class Recuerdo {

	private PApplet app; // Variable para cargar la clase PApplet
	private int x; // Variable para dar una posición x al objeto
	private int y; // Variable para dar una posición y al objeto
	private int recuerdo; // Variable para hacer un switch de objetos
	private PImage[] recuerdos; // Variable para cargar cada img de cada objeto

	/**
	 * Constructor de la clase Recuerdo (los Objetos a recoger)
	 * 
	 * @param app
	 * @param recuerdo
	 *            Variable para pintar cada objeto
	 */
	public Recuerdo(PApplet app, int recuerdo) {
		this.app = app;
		this.recuerdo = recuerdo;
		x = (int) app.random(-app.width + 165, app.width - 1035);
		y = (int) app.random(-app.height + 150, app.height - 620);

		recuerdos = new PImage[5];
		for (int i = 0; i < recuerdos.length; i++) {
			recuerdos[i] = app.loadImage("recuerdo" + (i + 1) + ".png");
		}
	}

	/**
	 * Metodo para pintar los objetos
	 * 
	 * @param j
	 *            Variable para obtener cam y lograr que los objetos se queden
	 *            en un solo lugar en el lienzo
	 */
	public void pintar(Jugador j) {
		switch (recuerdo) {
		case 1:
			app.image(recuerdos[0], j.getCamx() - x, j.getCamy() - y);
			break;

		case 2:
			app.image(recuerdos[1], j.getCamx() - x, j.getCamy() - y);
			break;

		case 3:
			app.image(recuerdos[2], j.getCamx() - x, j.getCamy() - y);
			break;

		case 4:
			app.image(recuerdos[3], j.getCamx() - x, j.getCamy() - y);
			break;

		case 5:
			app.image(recuerdos[4], j.getCamx() - x, j.getCamy() - y);
			break;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
