
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import chess.Board;

public class App extends JFrame{

	public static void main(String[] args) {
		App main = new App();
		main.init();
	}
	
	public void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.add(new Board(800,800));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setTitle("Chess Java");
		URL url = getClass().getResource("/sprites/Black/knight.gif");
		ImageIcon icon = new ImageIcon(url);
		this.setIconImage(icon.getImage());
	}

}
