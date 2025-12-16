package com.perky;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
/*
** This is a tracker for position of Control Z and Control Y.
*/
public class PositionTracker {
	public JTextArea textarea;
	public PositionTracker(JTextArea textarea) {
		this.textarea = textarea;
	}
	int position = -1;
	List<String> before = new LinkedList<String>();
	List<Integer> previouscursor = new LinkedList<Integer>();
	/*
	** This is start executed before first character because
	** after first undo doesn't record first character.
	*/
	public void startTracking() {
		if(position == -1 && before.size() == 0) {
			position = 0;
			before.add(textarea.getText());
			previouscursor.add(textarea.getCaretPosition());
			// Debug();
		}
	}
	public void add() {
		position++;
		before.add(position,textarea.getText());
		previouscursor.add(position,textarea.getCaretPosition());
		// Debug();
	}
	/*
	** This is for Control z
	*/
	public Selection previous() {
		position--;
		Selection selection = new Selection();
		selection.wholetext=before.get(position);
		selection.cursor=previouscursor.get(position);
		// Debug();
		return selection;
	}
	/*
	** This is for Control y.
	*/
	public Selection next() {
		position++;
		Selection selection = new Selection();
		selection.wholetext=before.get(position);
		selection.cursor=previouscursor.get(position);
		// Debug();
		return selection;
	}
	public void Debug() {
		System.out.println("position: "+position+" before.size(): "+before.size());
		for(String text:before) {
			JOptionPane.showMessageDialog(null,text);
		}
	}
}
