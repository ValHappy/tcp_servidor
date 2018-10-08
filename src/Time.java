import processing.core.*;

public class Time {

	private PApplet app; // Variable para cargar la clase PApplet
	private int seg_mostrados = 45; // Variable que determina cuantos segundos tiene el jugador
	private int min_mostrados = 0; // Variable que determina cuantos minutos tiene el jugador

	/**
	 * Constructor de la clase Time
	 * 
	 * @param app
	 */
	public Time(PApplet app) {
		this.app = app;
	}

	/**
	 * Metodo para pintar el tiempo
	 * se hace un frameCount de un seg, se le va restando a la variable seg_mostrados y se pinta en la pantalla
	 */
	void draw() {
		if (app.frameCount % 60 == 0) {
			seg_mostrados--;
		}
		app.fill(255);
		app.textSize(50);
		app.text(min_mostrados + ":" + seg_mostrados, app.width / 2, 60);
	}

	public int getSeg_mostrados() {
		return seg_mostrados;
	}

	public void setSeg_mostrados(int seg_mostrados) {
		this.seg_mostrados = seg_mostrados;
	}

}
