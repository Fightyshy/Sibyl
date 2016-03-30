import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class HelpWindow extends JFrame {
	public HelpWindow() {
		setTitle("\"Sibyl\" - Intelligent Policing System - Help Documentation");
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel titleLabel = new JLabel("\"Sibyl\" - Intelligent Policing System - Dissertation Project");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JTextPane helpTextPane = new JTextPane();
		helpTextPane.setFont(new Font("Arial", Font.PLAIN, 14));
		helpTextPane.setBackground(UIManager.getColor("Button.background"));
		helpTextPane.setEditable(false);
		helpTextPane.setText("Program Information:\r\nVersion 0.1 Alpha\r\nCreated by Tharon Nithiroongrujakorn\r\n\r\nHelp:\r\n- File:\r\n\t- Help: Display this window, contains operation documentation.\r\n\t- Exit: Quits program, which can also be done by normal close button.\r\n- Enviroment:\r\n\t- Generate: Randomly generate a new simulated enviroment. Must be done prior to adding criminals and drones. Will wipe existing progress.\r\n\t- Execute: Starts the simulation, allowing criminals to randomly spawn and drones to move, among other processes.\r\n- Drones:\r\n\t- Add Drone: Adds a drone that starts at the location of the central entity.\r\n\t- Remove Drone: immediately removes drones from order of first added.\r\n- Criminal:\r\n\t- Add Criminal: Force-adds a Criminal at a random location to the enviroment.\r\n\t- Delete Criminal: Force-removes the last added Criminal on the map.");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(helpTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helpTextPane, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		pack(); //resizes window so label component is shown size of helpTextPane
	}
}
