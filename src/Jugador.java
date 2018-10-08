import java.awt.Color;
import java.util.*;

import processing.core.*;

public class Jugador {

	private PApplet app; // Variable para cargar la clase PApplet
	private PVector pos; // Variable para darle una posición en x y y
	private PVector vel; // Variable para darle una velocidad
	
	private PImage[] jugador; // Variable para cargar las imagenes del jugador
	private PImage escenarioneg; // Variable para cargar el escenario en negativo y delimitar
	
	private int player; // Variable para hacer un switch de enemigos 1:Mifu 2: Amayha
	private int contador; // Variable para contar el tiempo de la animacion, subir y bajar
	private int dir; // Variable para pintar en cada vista el jugador
	private int puntaje; // Variable para determinar el puntaje del jugador
	private int vida; // Variable para determinar las vidas del jugador
	private int camx; // Variable para mover la camara en x en el lienzo
	private int camy; // Variable para mover la camara en y en el lienzo
	private int camvel; // Variable para mover la camara a cierta velocidad en el lienzo
	private int tinv;
	
	private float velA; // Variable para determinar la velocidad de la animación
	
	private boolean animOn; // Variable para activar la animación
	private boolean arriba; // Variable para determinar si esta caminando hacia arriba
	private boolean abajo; // Variable para determinar si esta caminando hacia abajo
	private boolean izq; // Variable para determinar si esta caminando hacia la izquierda
	private boolean der; // Variable para determinar si esta caminando hacia la derecha
	private boolean inv;

	private ArrayList<Bala> balas; // Variable para cargar balas en el jugador
	
	private PFont october; // Variable para cargar una tipografia

	/**
	 * Consturctor de la clase Jugador
	 * @param app
	 * @param x
	 * @param y
	 * @param player
	 */
	public Jugador(PApplet app, float x, float y, int player) {
		this.app = app;
		this.player = player;
		
		pos = new PVector(x, y);
		vel = new PVector(5f, 5f);
		
		jugador = new PImage[4];
		
		balas = new ArrayList<Bala>();
		
		vida = 3;
		dir = 2;
		contador = 0;
		camx = app.width - 135;
		camy = app.height - 20;
		camvel = 5;
		velA = 0.5f;
		
		app.imageMode(app.CENTER);
		switch (player) {
		case 1:
			for (int i = 0; i < jugador.length; i++) {
				jugador[i] = app.loadImage("mifu" + (i + 1) + ".png");
			}
			break;

		case 2:
			for (int i = 0; i < jugador.length; i++) {
				jugador[i] = app.loadImage("amayha" + (i + 1) + ".png");
			}
			break;
		}

		arriba = false;
		abajo = false;
		izq = false;
		der = false;
		inv = false;

		escenarioneg = app.loadImage("escenarionegativo.png");
		
		october = app.createFont("october.ttf", 60);
		app.textFont(october);
	}

	/**
	 * Metodo para pintar el jugador
	 * Imprimir puntaje de ambos jugadores, definir direccion, crear la animación, pintar las balas
	 */
	public void pintar() {

		if (player == 1) {
			app.textSize(60);
			app.text("Vida: " + vida, 100, 60);
			app.text("Puntaje: " + puntaje, 300, 60);
		}

		if (player == 2) {
			app.text("Vida: " + vida, app.width - 100, 60);
			app.text("Puntaje: " + puntaje, app.width - 300, 60);
		}

		switch (dir) {
		case 1:
			// Arriba
			app.image(jugador[0], pos.x, pos.y);
			break;

		case 2:
			// Abajo
			app.image(jugador[1], pos.x, pos.y);
			break;

		case 3:
			// Izquierda
			app.image(jugador[2], pos.x, pos.y);
			break;

		case 4:
			// Derecha
			app.image(jugador[3], pos.x, pos.y);
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

		for (int i = 0; i < balas.size(); i++) {
			balas.get(i).pintar();
			balas.get(i).mover();

			if (balas.get(i).getX() <= -1200 || balas.get(i).getX() >= 2000 || balas.get(i).getY() <= -700
					|| balas.get(i).getY() >= 1400) {
				balas.remove(i);
				// Se borra la bala
			}
		}

//		if (inv == true) {
//			tinv++;
//			if (tinv == 120) {
//				inv = false;
//				tinv = 0;
//			}
//		}
	}

	/**
	 * Metodo para mover los jugadores en android
	 * @param dirr Variable string para el intercambio de datos
	 */
	public void move(String dirr) {
		System.out.println("cuantas veces se inicia");
		if (dirr.equals("arriba")) {
			dir = 1;
			arriba = true;
			abajo = false;
			izq = false;
			der = false;
		} else if (dirr.equals("abajo")) {
			dir = 2;
			abajo = true;
			arriba = false;
			izq = false;
			der = false;
		} else if (dirr.equals("izquierda")) {
			dir = 3;
			izq = true;
			der = false;
			arriba = false;
			abajo = false;
		} else if (dirr.equals("derecha")) {
			dir = 4;
			der = true;
			izq = false;
			abajo = false;
			arriba = false;
		} else if (dirr.equals("disparar")) {
			disparar();
		}
	}

	/**
	 * Metodo para delimitar el borde del mapa y caminar
	 */
	public void caminando() {
		if (arriba == true) {
			if (pos.y >= 150) {
				pos.y -= vel.y;
			} else {
				if (camy < 620) {
					if (camy <= escenarioneg.height + camy)
						camy += camvel;
				}
			}
		}

		if (abajo == true) {
			if (pos.y <= app.height - 150) {
				pos.y += vel.y;
			} else {
				if (camy > 100) {
					if (camy >= camy - escenarioneg.height)
						camy -= camvel;
				}
			}
		}

		if (izq == true) {
			if (pos.x >= 150) {
				pos.x -= vel.x;
			} else {
				if (camx < 1035) {
					if (camx <= escenarioneg.width + camx)
						camx += camvel;
				}
			}
		}

		if (der == true) {
			if (pos.x <= app.width - 150) {
				pos.x += vel.x;
			} else {
				if (camx > 165) {
					if (camx >= camx - escenarioneg.width)
						camx -= camvel;
				}
			}
		}
	}

	/**
	 * Metodo para disparar
	 */
	public void disparar() {
		balas.add(new Bala(app, pos.x, pos.y, dir));
	}

	/**
	 * Metodo para que el enemigo siga el jugador y al tocarlo pierda vidas
	 * @param enemigo
	 */
	public void validarDistanciaEnemigo(Enemigo enemigo) {
		if (app.dist(pos.x, pos.y, camx - enemigo.getX(), camy - enemigo.getY()) < 400) {
			enemigo.setVel((pos.x - (camx - enemigo.getX())), (pos.y - (camy - enemigo.getY())));
			enemigo.setPerseguir(true);
		} else {
			enemigo.setPerseguir(false);
		}
		if (inv == false) {
			if (app.dist(pos.x, pos.y, camx - enemigo.getX(), camy - enemigo.getY()) < 100) {
				vida -= 1;
				inv = true;
			}
		}
	}

	/**
	 * Metodo para recoger objetos y sumar puntos
	 * @param recuerdos
	 */
	public void validarDistanciaObjeto(ArrayList<Recuerdo> recuerdos) {
		for (int i = 0; i < recuerdos.size(); i++) {
			if (app.dist(pos.x, pos.y, camx - recuerdos.get(i).getX(), camy - recuerdos.get(i).getY()) < 60) {
				puntaje += 50;
				recuerdos.remove(i);
				System.out.println("Recogio un recuerdo");
			}
		}
	}

	/**
	 * Metodo para que al disparar desaparezca el enemigo y la bala
	 * @param enemigos
	 */
	public void matarEnemigos(ArrayList<Enemigo> enemigos) {
		for (int i = 0; i < enemigos.size(); i++) {
			Enemigo enemigo = enemigos.get(i);
			if (enemigo != null) {
				for (int j = 0; j < balas.size(); j++) {
					if (app.dist(balas.get(j).getX(), balas.get(j).getY(), camx - enemigo.getX(),
							camy - enemigo.getY()) < 100) {
						enemigos.remove(i);
						System.out.println("Se remueve el enemigo");
						balas.remove(j);
						System.out.println("remuevo la bala");
					}
				}
			}
		}
	}

	public int getCamx() {
		return camx;
	}

	public void setCamx(int camx) {
		this.camx = camx;
	}

	public int getCamy() {
		return camy;
	}

	public void setCamy(int camy) {
		this.camy = camy;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public void mover() {
		if (player == 2) {
			if (app.key == 'W' || app.key == 'w') {
				dir = 1;
				arriba = true;
			}
			if (app.key == 'S' || app.key == 's') {
				dir = 2;
				abajo = true;
			}
			if (app.key == 'A' || app.key == 'a') {
				dir = 3;
				izq = true;
			}
			if (app.key == 'D' || app.key == 'd') {
				dir = 4;
				der = true;
			}
		}

		if (player == 1) {
			if (app.keyCode == app.UP) {
				dir = 1;
				arriba = true;
			}
			if (app.keyCode == app.DOWN) {
				dir = 2;
				abajo = true;
			}
			if (app.keyCode == app.LEFT) {
				dir = 3;
				izq = true;
			}
			if (app.keyCode == app.RIGHT) {
				dir = 4;
				der = true;
			}
		}
	}
	
	public void soltar() {
		arriba = false;
		abajo = false;
		izq = false;
		der = false;
	}
}
