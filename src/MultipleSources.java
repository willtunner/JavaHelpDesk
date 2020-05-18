import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ButtonListener implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    JButton o = (JButton) e.getSource();
    String label = o.getText();
    System.out.println(label + " button clicked");
  }
}

public class MultipleSources {
  public static void main(String[] args) {
    JPanel panel = new JPanel();

    JButton close = new JButton("Close");
    close.addActionListener(new ButtonListener());

    JButton open = new JButton("Open");
    open.addActionListener(new ButtonListener());

    JButton find = new JButton("Find");
    find.addActionListener(new ButtonListener());

    JButton save = new JButton("Save");
    save.addActionListener(new ButtonListener());

    panel.add(close);
    panel.add(open);
    panel.add(find);
    panel.add(save);
    JFrame f = new JFrame();
    f.add(panel);
    f.setSize(400, 300);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }
}
