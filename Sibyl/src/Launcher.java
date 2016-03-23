import java.awt.EventQueue;

public class Launcher {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				SimulationEnviroment simEnviro = new SimulationEnviroment();
				simEnviro.setVisible(true);
			}
		});
	}
}
