public class StarNorDot { 	
 	private boolean isLocked = false;
 	private String str_nor_dot = "";
 	public void setStarNorDot(String str_nor_dot) {
 		if(!isLocked)
 			this.str_nor_dot = str_nor_dot;
 	}
 	public void lock() {
 		isLocked = true;
 	}
 	public String getStarNorDot() {
 		return str_nor_dot;
 	}
 	
 	public boolean isEmpty() {
 		return str_nor_dot.equals("");
 	}
}
 