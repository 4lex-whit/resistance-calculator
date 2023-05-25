package me.alex_whit.resistancecalculator;

import me.alex_whit.resistancecalculator.gui.GUI;

public class ResistanceCalculator {
	public static String getVersion() {
		return "v1.0.3";
	}
	
	public static void main(String[] args) {
		System.out.println(String.format("""
				 
				   Resistance Calculator | %s
				   By alex_whit
				 """, getVersion()));
		
		GUI.create();
	}
}
