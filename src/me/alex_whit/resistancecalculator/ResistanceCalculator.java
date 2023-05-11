package me.alex_whit.resistancecalculator;

import me.alex_whit.resistancecalculator.gui.Gui;

public class ResistanceCalculator {
	public static String getVersion() {
		return "v1.0.0";
	}
	
	public static void main(String[] args) {
		System.out.println(String.format("""
				 
				   Resistance Calculator | %s
				   By alex_whit
				 """, getVersion()));
		
		Gui.create();
	}
}
