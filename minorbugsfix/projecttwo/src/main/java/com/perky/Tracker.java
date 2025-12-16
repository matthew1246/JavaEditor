package com.perky;

import javax.swing.JTextArea;
public class Tracker {
	public boolean isDeleted = false;
	public boolean isSelected = false;
	public Main main;
	public String previous;
	public boolean hasFinished;
	public boolean wasHighlighted = false;
	public int start = -1;
	public Tracker(Main main) {
		this.main = main;
	}
	
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public boolean getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean getIsSelected() {
		return this.isSelected;
	}
	
	public void isHighlighted() {
		this.start = main.textarea.getSelectionStart();
		previous = main.textarea.getText().substring(start,main.textarea.getSelectionEnd());
		hasFinished = false;
		wasHighlighted = true;
	}
	
	public void Save(int start,String previous) {
		this.start = start;
		this.previous = previous;
	}
	
	public boolean isVariableStillSelected() {
		if(hasFinished || start == -1) {
			return false;
		}
		int cursor = main.textarea.getCaretPosition();
		if(cursor > start) {
			String wholetext = main.textarea.getText();
			String line = wholetext.substring(start,wholetext.length());
			int index = start+line.indexOf("=");
			if(cursor < index) {
				return true;
			}
			else {
				hasFinished = true;
				return false;
			}	
		}
		else {
			hasFinished = true;
			return false;
		}
	}
	
	public String getVariable() {
		return main.textarea.getText().substring(start,main.textarea.getCaretPosition());
	}
}
