package me.alex_whit.resistancecalculator.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import me.alex_whit.resistancecalculator.ResistanceCalculator;

public class GUI {
	private static JFrame frame;
	private static JLayeredPane pane;
	private static JLabel resistanceLabel;
	private static JLabel resistanceResultLabel;
	private static JLabel toleranceLabel;
	private static JLabel toleranceResultLabel;
	private static JLabel temperatureCoefficientLabel;
	private static JLabel temperatureCoefficientResultLabel;
	private static JLabel resistorLabel;
	private static JLabel band1Label;
	private static JLabel band2Label;
	private static JLabel band3Label;
	private static JLabel band4Label;
	private static JLabel band5Label;
	private static JLabel band6Label;
	private static JComboBox<String> band1ComboBox;
	private static JComboBox<String> band2ComboBox;
	private static JComboBox<String> band3ComboBox;
	private static JComboBox<String> band4ComboBox;
	private static JComboBox<String> band5ComboBox;
	private static JComboBox<String> band6ComboBox;
	private static JButton calculateButton;
	
	private static JFrame getFrame() {
		return frame;
	}
	
	private static JLayeredPane getPane() {
		return pane;
	}
	
	private static JLabel getResistanceLabel() {
		return resistanceLabel;
	}
	
	private static JLabel getResistanceResultLabel() {
		return resistanceResultLabel;
	}
	
	private static JLabel getToleranceLabel() {
		return toleranceLabel;
	}
	
	private static JLabel getToleranceResultLabel() {
		return toleranceResultLabel;
	}
	
	private static JLabel getTemperatureCoefficientLabel() {
		return temperatureCoefficientLabel;
	}
	
	private static JLabel getTemperatureCoefficientResultLabel() {
		return temperatureCoefficientResultLabel;
	}
	
	private static JLabel getResistorLabel() {
		return resistorLabel;
	}
	
	private static JLabel getBandLabel(int band) {
		switch (band) {
		case 1: return band1Label;
		case 2: return band2Label;
		case 3: return band3Label;
		case 4: return band4Label;
		case 5: return band5Label;
		case 6: return band6Label;
		}
		
		return null;
	}
	
	private static JComboBox<String> getBandComboBox(int band) {
		switch (band) {
		case 1: return band1ComboBox;
		case 2: return band2ComboBox;
		case 3: return band3ComboBox;
		case 4: return band4ComboBox;
		case 5: return band5ComboBox;
		case 6: return band6ComboBox;
		}
		
		return null;
	}
	
	private static JButton getCalculateButton() {
		return calculateButton;
	}
	
	private static void updateBandColours() {
		System.out.println("Updating band colours...");
		
		for (int i = 1; i <= 6; i++) {
			try {
				String colour = getBandComboBox(i).getSelectedItem().toString().toLowerCase();
				
				switch (colour) {
				case "none":
					getBandLabel(i).setIcon(null);
					break;
				default:
					getBandLabel(i).setIcon(new ImageIcon(ImageIO.read(GUI.class.getResource(String.format("/band%s/resistor_band%s_%s.png",
							i, i, colour)))));
				}
					
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	private static void calculate() {
		System.out.println("Calculating results...");
		
		boolean invalid = false;
		int bandAmount = 3;
		
		String band1Colour = getBandComboBox(1).getSelectedItem().toString();
		String band2Colour = getBandComboBox(2).getSelectedItem().toString();
		String band3Colour = getBandComboBox(3).getSelectedItem().toString();
		String band4Colour = getBandComboBox(4).getSelectedItem().toString();
		String band5Colour = getBandComboBox(5).getSelectedItem().toString();
		String band6Colour = getBandComboBox(6).getSelectedItem().toString();
		
		if (!band5Colour.matches("None")) bandAmount = 4; // 4, 5 or 6 bands
		
		if (!band4Colour.matches("None")) { // 5 or 6 bands
			if (band5Colour.matches("None")) invalid = true;
			else bandAmount = 5;
		}
		
		if (!band6Colour.matches("None")) { // 6 bands
			if (band4Colour.matches("None") || band5Colour.matches("None")) invalid = true;
			else bandAmount = 6;
		}
		
		if (invalid) {
			System.out.println("Invalid colours");
			
			JOptionPane.showMessageDialog(getFrame(), "Band colours are invalid!", "Calculation Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		double resistance = 0;
		double tolerance = 0;
		double temperatureCoefficient = 0;
		
		switch (band1Colour) {
		case "Brown": resistance += 10; break;
		case "Red": resistance += 20; break;
		case "Orange": resistance += 30; break;
		case "Yellow": resistance += 40; break;
		case "Green": resistance += 50; break;
		case "Blue": resistance += 60; break;
		case "Purple": resistance += 70; break;
		case "Grey": resistance += 80; break;
		case "White": resistance += 90;
		}
		
		switch (band2Colour) {
		case "Brown": resistance += 1; break;
		case "Red": resistance += 2; break;
		case "Orange": resistance += 3; break;
		case "Yellow": resistance += 4; break;
		case "Green": resistance += 5; break;
		case "Blue": resistance += 6; break;
		case "Purple": resistance += 7; break;
		case "Grey": resistance += 8; break;
		case "White": resistance += 9;
		}
		
		if (bandAmount <= 4) { // has 2 digits
			switch (band3Colour) {
			case "Brown": resistance = resistance * 10; break;
			case "Red": resistance = resistance * 100; break;
			case "Orange": resistance = resistance * 1000; break;
			case "Yellow": resistance = resistance * 10000; break;
			case "Green": resistance = resistance * 100000; break;
			case "Blue": resistance = resistance * 1000000; break;
			case "Purple": resistance = resistance * 10000000; break;
			case "Grey": resistance = resistance * 100000000; break;
			case "White": resistance = resistance * 1000000000; break;
			case "Gold": resistance = resistance * 0.1; break;
			case "Silver": resistance = resistance * 0.01;
			}
		}
		
		if (bandAmount >= 4) { // has tolerance band
			switch (band5Colour) {
			case "Brown": tolerance = 1; break;
			case "Red": tolerance = 2; break;
			case "Orange": tolerance = 0.05; break;
			case "Yellow": tolerance = 0.02; break;
			case "Green": tolerance = 0.5; break;
			case "Blue": tolerance = 0.25; break;
			case "Purple": tolerance = 0.1; break;
			case "Grey": tolerance = 0.01; break;
			case "Gold": tolerance = 5; break;
			case "Silver": tolerance = 10;
			}
		}
		
		if (bandAmount >= 5) { // has 3 digits
			resistance = resistance * 10;
			
			switch (band3Colour) {
			case "Brown": resistance += 1; break;
			case "Red": resistance += 2; break;
			case "Orange": resistance += 3; break;
			case "Yellow": resistance += 4; break;
			case "Green": resistance += 5; break;
			case "Blue": resistance += 6; break;
			case "Purple": resistance += 7; break;
			case "Grey": resistance += 8; break;
			case "White": resistance += 9;
			}
			
			switch (band4Colour) {
			case "Brown": resistance = resistance * 10; break;
			case "Red": resistance = resistance * 100; break;
			case "Orange": resistance = resistance * 1000; break;
			case "Yellow": resistance = resistance * 10000; break;
			case "Green": resistance = resistance * 100000; break;
			case "Blue": resistance = resistance * 1000000; break;
			case "Purple": resistance = resistance * 10000000; break;
			case "Grey": resistance = resistance * 100000000; break;
			case "White": resistance = resistance * 1000000000; break;
			case "Gold": resistance = resistance * 0.1; break;
			case "Silver": resistance = resistance * 0.01;
			}
		}
		
		if (bandAmount == 6) { // has temperature coefficient band
			switch (band6Colour) {
			case "Black": temperatureCoefficient = 250; break;
			case "Brown": temperatureCoefficient = 100; break;
			case "Red": temperatureCoefficient = 50; break;
			case "Orange": temperatureCoefficient = 15; break;
			case "Yellow": temperatureCoefficient = 25; break;
			case "Green": temperatureCoefficient = 20; break;
			case "Blue": temperatureCoefficient = 10; break;
			case "Purple": temperatureCoefficient = 5; break;
			case "Grey": temperatureCoefficient = 1;
			}
		}
		
		String resistanceRounded = new BigDecimal(resistance).setScale(5, RoundingMode.HALF_UP).toPlainString();
		
		System.out.println(String.format("""
				Resistance: %s Ohm(s)
				Tolerance: +/-%s %%
				Temperature Coefficient: %s ppm/K""", resistanceRounded, tolerance, temperatureCoefficient));
		
		getResistanceResultLabel().setText(String.format("%s Ω", resistanceRounded));
		getToleranceResultLabel().setText(String.format("±%s %%", tolerance));
		getTemperatureCoefficientResultLabel().setText(String.format("%s ppm/K", temperatureCoefficient));
	}
	
	public static void setupComponents() {
		System.out.println("Setting up components...");
		
		// pane
		pane = new JLayeredPane();
		getPane().setBounds(0,0,500,350);
		
		Font font = new Font("Arial", Font.BOLD, 18);
		
		// resistance label
		resistanceLabel = new JLabel("Resistance:");
		getResistanceLabel().setBounds(130,15,105,30);
		getResistanceLabel().setFont(font);
		
		// resistance result label
		resistanceResultLabel = new JLabel("0.00000 Ω");
		getResistanceResultLabel().setBounds(250,15,250,30);
		getResistanceResultLabel().setFont(font);
		
		// tolerance label
		toleranceLabel = new JLabel("Tolerance:");
		getToleranceLabel().setBounds(139,50,95,30);
		getToleranceLabel().setFont(font);
		
		// tolerance result label
		toleranceResultLabel = new JLabel("±0.0 %");
		getToleranceResultLabel().setBounds(250,50,250,30);
		getToleranceResultLabel().setFont(font);
		
		// temperature coefficient label
		temperatureCoefficientLabel = new JLabel("Temperature Coefficient:");
		getTemperatureCoefficientLabel().setBounds(14,85,220,30);
		getTemperatureCoefficientLabel().setFont(font);
		
		// temperature coefficient result label
		temperatureCoefficientResultLabel = new JLabel("0.0 ppm/K");
		getTemperatureCoefficientResultLabel().setBounds(250,85,250,30);
		getTemperatureCoefficientResultLabel().setFont(font);
		
		// bands
		band1Label = new JLabel();
		band2Label = new JLabel();
		band3Label = new JLabel();
		band4Label = new JLabel();
		band5Label = new JLabel();
		band6Label = new JLabel();
		
		band1ComboBox = new JComboBox<String>();
		band2ComboBox = new JComboBox<String>();
		band3ComboBox = new JComboBox<String>();
		band4ComboBox = new JComboBox<String>();
		band5ComboBox = new JComboBox<String>();
		band6ComboBox = new JComboBox<String>();
		
		for (int i = 1; i <= 6; i++) {
			// band labels
			
			getBandLabel(i).setBounds(132,135,235,61);
			getBandLabel(i).setFont(font);
			getPane().add(getBandLabel(i),0+i);
			
			// band combo boxes
			getBandComboBox(i).setBounds(80*i-70,225,70,30);
			
			getBandComboBox(i).addItem("None");
			getBandComboBox(i).addItem("Black");
			getBandComboBox(i).addItem("Brown");
			getBandComboBox(i).addItem("Red");
			getBandComboBox(i).addItem("Orange");
			getBandComboBox(i).addItem("Yellow");
			getBandComboBox(i).addItem("Green");
			getBandComboBox(i).addItem("Blue");
			getBandComboBox(i).addItem("Purple");
			getBandComboBox(i).addItem("Grey");
			getBandComboBox(i).addItem("White");
			getBandComboBox(i).addItem("Gold");
			getBandComboBox(i).addItem("Silver");
			
			switch (i) {
			case 1,2:
				getBandComboBox(i).removeItem("Gold");
				getBandComboBox(i).removeItem("Silver");
				getBandComboBox(i).removeItem("None");
				break;
			case 3:
				getBandComboBox(i).removeItem("None");
				break;
			case 5:
				getBandComboBox(i).removeItem("Black");
				getBandComboBox(i).removeItem("White");
				break;
			case 6:
				getBandComboBox(i).removeItem("White");
				getBandComboBox(i).removeItem("Gold");
				getBandComboBox(i).removeItem("Silver");
			}
			
			getBandComboBox(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					System.out.println("Band colour selected");
					
					updateBandColours();
				}
			});
		}
		
		// resistor label
		resistorLabel = new JLabel();
		getResistorLabel().setBounds(132,135,235,61);
		try {
			getResistorLabel().setIcon(new ImageIcon(ImageIO.read(GUI.class.getResource("/resistor.png"))));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		getPane().add(getResistorLabel(), -1);
		
		// calculate button
		calculateButton = new JButton("Calculate");
		getCalculateButton().setBounds(192,265,115,30);
		getCalculateButton().setFont(font);
		getCalculateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("Calculate button clicked");
				
				calculate();
			}
		});
	}
	
	public static void create() {
		System.out.println("Creating GUI...");
		
		frame = new JFrame();
		
		// frame setup
		getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getFrame().setTitle(String.format("Resistance Calculator │ %s", ResistanceCalculator.getVersion()));
		getFrame().setResizable(false);
		getFrame().setSize(500,350);
		getFrame().setLocationRelativeTo(null);
		getFrame().setLayout(null);
		
		// setup and add components
		setupComponents();
		getFrame().add(getPane());
		getFrame().add(getResistanceLabel());
		getFrame().add(getResistanceResultLabel());
		getFrame().add(getToleranceLabel());
		getFrame().add(getToleranceResultLabel());
		getFrame().add(getTemperatureCoefficientLabel());
		getFrame().add(getTemperatureCoefficientResultLabel());
		for (int i = 1; i <= 6; i++) getFrame().add(getBandComboBox(i));
		getFrame().add(getCalculateButton());
		
		updateBandColours();
		
		getFrame().setVisible(true);
	}
}
