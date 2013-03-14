package by.gsu.epamlab.Implements.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import by.gsu.epamlab.Implements.FrameImpl;
import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.beans.State;
import by.gsu.epamlab.run.configuration.ConfigBuilding;

public class WindowAplication extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	
	private BuildingUI buildingUI;
	private JTextArea logs;
	private JButton startButton;

	public WindowAplication(final Building building) {
		super("Elevator");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					new Thread (new Runnable() {
						
						@Override
						public void run() {
							building.cancel();
						}
					}).start();
				}
		});
		
		setBounds(50, 50, 970, 600);
		setResizable(false);
		setLayout(new BorderLayout());
// top
		JPanel topPanel = new JPanel(new GridBagLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
// center
		buildingUI = new BuildingUI(building);
		getContentPane().add(buildingUI, BorderLayout.CENTER);
// bottom
		logs = new JTextArea(7, 50);
		logs.setEditable(false);
		JScrollPane bottomPanel = new JScrollPane(logs);
		
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		AppenderJtext append = new AppenderJtext(logs) ;
		append.setScrollPane(bottomPanel);
		Logger logger = Logger.getLogger(FrameImpl.class);
		logger.addAppender(append);
	
// adds
		ConfigBuilding cf = building.getConfigBuilding();
		topPanel.add(new JLabel("passengers number:"), new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 1, 1));
		final JTextField passengerNumber = new JTextField(String.valueOf(cf.getPassengersNumber()),5);
		topPanel.add(passengerNumber,new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,  new Insets(3, 3, 3, 3), 1, 1));
		
		topPanel.add(new JLabel("storeys number:"), new GridBagConstraints(0, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,  new Insets(3, 3, 3, 3), 1, 1));
		JTextField storeysNumber = new JTextField(String.valueOf(cf.getStoreysNumber()),5);
		topPanel.add(storeysNumber,new GridBagConstraints(1, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,  new Insets(3, 3, 3, 3), 1, 1));

		topPanel.add(new JLabel("elevator capacity:"), new GridBagConstraints(0, 2, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 1, 1));
		JTextField elevatorCapacity = new JTextField(String.valueOf(cf.getElevatorCapacity()),5);
		topPanel.add(elevatorCapacity,new GridBagConstraints(1, 2, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 1, 1));

		topPanel.add(new JLabel("Animation:"), new GridBagConstraints(2,1,1,1,0,0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5,5,0,5),0,0));
		final JSlider slider = new JSlider(0,30, cf.getAnimationBoost());
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (building != null) {
					int value = ((JSlider)e.getSource()).getValue();
						building.setAnimation(value);
				}
			}
		});

		topPanel.add(slider, new GridBagConstraints(3,1,1,1,1,0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,0,5),0,0));
	
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("Start".equals(startButton.getText())) {
					startButton.setText("abort");
					building.start();
				} else {
					if (building.getState()==State.IN_PROGRESS){
						building.cancel();
					}
				}
			}
		});
		
		topPanel.add(startButton, new GridBagConstraints(2, 2, 2, 1, 1, 1,
		GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 1, 1));
		
		passengerNumber.setEditable(false);
		storeysNumber.setEditable(false);
		elevatorCapacity.setEditable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public BuildingUI getBuildingUI() {
		return buildingUI;
	}
	
}
