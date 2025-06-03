public class ChatGPT {
	public static void main(String[] args) {
		ChatGPT chatgpt = new ChatGPT(args);
	}
	public ChatGPT() {
		this(new String[0]);
	}
	public ChatGPT(String[] args) {
	   	SimpleBrowser.launch(SimpleBrowser.class, args);
	}
}
