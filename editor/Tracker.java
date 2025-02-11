import javax.swing.JTextArea;
public class Tracker {
	public boolean isDeleted = false;
	public boolean isSelected = false;
	public JTextArea textarea;
	public String previous;
	public boolean hasFinished;
	public boolean wasHighlighted = false;
	public int start = -1;
	public Tracker(JTextArea textarea) {
		this.textarea = textarea;
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
		this.start = textarea.getSelectionStart();
		previous = textarea.getText().substring(start,textarea.getSelectionEnd());
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
		int cursor = textarea.getCaretPosition();
		if(cursor > start) {
			String wholetext = textarea.getText();
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
		return textarea.getText().substring(start,textarea.getCaretPosition());
	}
}
