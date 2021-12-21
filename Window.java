package com.mar.main2048;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

    //размер окна
    private int width = 375;
    private int height = 450;

    public Window(String title) {
        super(title);
        //внешний вид окна
        this.setLayout( new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setFocusable(true);
        getContentPane().add( new Grid() );
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 760));
        this.setVisible(true);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
