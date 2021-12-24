import javax.swing.JFrame;

public class SnakeFrame extends JFrame {
    
    /**
     * 
    **/
    private static final long serialVersionUID = 1L;

    SnakeFrame() {
        this.add(new SnakePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
}
