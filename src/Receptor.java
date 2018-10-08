import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Receptor extends Thread {

	Socket socket;

	public Receptor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// Siempre quiero que este en funcionamiento
		
		try {
			InputStream is = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			//lee siempre y en android para escribir deberia ser write UTF o las demas opciones
			while (true) {
				String line = reader.readLine();
				System.out.println(line);
				out.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
