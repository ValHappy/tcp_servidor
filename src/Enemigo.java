import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Enemigo {

	private PApplet app; // Variable para cargar la clase PApplet
	private PVector pos;// Variable dar una posición x y y
	private PVector vel;// Variable dar una velocidad
	private PImage[] enemigo;// Variable para cargar las imagenes de los enemigos
	private int enemy; // Variable para hacer un switch de enemigos 1:Fantasma 2: Hollow
	private int contador; // Variable para contar el tiempo de la animacion, subir y bajar
	private int dir; // Variable para pintar en cada vista el enemigo
	private float velA; // Variable para determinar la velocidad de la animación
	private boolean animOn; // Variable para activar la animación
	private boolean perseguir; // Variable para determinar si lo esta persiguiendo o no

	/**
	 * Constructor de la clase Enemigo
	 * 
	 * @param app
	 * @param x
	 * @param y
	 * @param enemy
	 */
	public Enemigo(PApplet app, float x, float y, int enemy) {
		this.app = app;
		pos = new PVector(app.random(-app.width + 165, app.width - 1035),
				app.random(-app.height + 150, app.height - 620));
		vel = new PVector(0, 0);
		this.enemy = enemy;
		enemigo = new PImage[4];
		dir = 2;
		contador = 0;
		velA = 0.5f;
		app.imageMode(app.CENTER);
		switch (enemy) {
		case 1:
			for (int i = 0; i < enemigo.length; i++) {
				enemigo[i] = app.loadImage("whi" + (i + 1) + ".png");
			}
			break;

		case 2:
			for (int i = 0; i < enemigo.length; i++) {
				enemigo[i] = app.loadImage("doku" + (i + 1) + ".png");
			}
			break;
		}
		perseguir = false;
	}

	/**
	 * Metodo para pintar los enemigos
	 * 
	 * Switch para pintar las vistas del enemigo cuando esta y no esta
	 * persiguiendo al jugador y creación de la animación de movimiento
	 * 
	 * @param j
	 *            Variable para obtener cam y lograr que los enemigos se queden
	 *            en un solo lugar en el lienzo
	 */
	public void pintar(Jugador j) {
		switch (dir) {
		case 1:
			// Arriba
			app.image(enemigo[0], j.getCamx() - pos.x, j.getCamy() - pos.y);
			break;

		case 2:
			// Abajo
			app.image(enemigo[1], j.getCamx() - pos.x, j.getCamy() - pos.y);
			break;

		case 3:
			// Izquierda
			app.image(enemigo[2], j.getCamx() - pos.x, j.getCamy() - pos.y);
			break;

		case 4:
			// Derecha
			app.image(enemigo[3], j.getCamx() - pos.x, j.getCamy() - pos.y);
			break;
		}

		if (animOn == true) {
			contador++;
			pos.y -= velA;
			if (contador == 25) {
				contador = 0;
				animOn = false;
			}
		} else {
			contador++;
			pos.y += velA;
			if (contador == 25) {
				contador = 0;
				animOn = true;
			}
		}

		if (perseguir == false) {
			if (app.frameCount % 120 == 0) {
				dir = (int) app.random(1, 4);
			}

			switch (dir) {
			case 1:
				if (j.getCamy() - pos.y < 620) {
					pos.y--;
				}
				break;

			case 2:
				if (j.getCamy() - pos.y > 100) {
					pos.y++;
				}
				break;

			case 3:
				if (j.getCamx() - pos.x < 1035) {
					pos.x--;
				}
				break;

			case 4:
				if (j.getCamx() - pos.x > 150) {
					pos.x++;
				}
				break;
			}

		} else {
			dir = j.getDir();
			vel.normalize();
			pos.sub(vel);
		}
	}

	public float getX() {
		return pos.x;
	}

	public void setX(float x) {
		pos.x = x;
	}

	public float getY() {
		return pos.y;
	}

	public void setY(float y) {
		pos.y = y;
	}

	public PVector getVel() {
		return vel;
	}

	public void setVel(float x, float y) {
		vel.x = x;
		vel.y = y;
	}

	public boolean isPerseguir() {
		return perseguir;
	}

	public void setPerseguir(boolean perseguir) {
		this.perseguir = perseguir;
	}
}
