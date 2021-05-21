package zoom.frame;

import zoom.classes.*;
import zoom.classes.Math;
import zoom.model.Class;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Stack;

public abstract class BaseFrame extends JFrame {

    static Stack<JFrame> stack = new Stack<>();
    static Class[] classes = {new Bigdata(),new Database(),new DataStructure(),new English(),new History(),
            new Jpanese(),new Literature(),new Math(),new PE(),new Social(),new GoogleMeet()};

    public BaseFrame(String title,int width,int height){
        super(title);
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                closingAction();
            }
        });
    }

    public static<T extends JComponent> T createComponent (T comp, int width, int height){
        comp.setPreferredSize(new Dimension(width,height));
        return comp;
    }

    public static<T extends JComponent> T createComponent (T comp, int x,int y,int width, int height){
        comp.setBounds(x,y,width,height);
        return comp;
    }

    public static JButton createButton(String text, ActionListener act){
        JButton button = new JButton(text);
        button.addActionListener(act);
        return button;
    }

    public static JButton createButtonWithoutMargin(String text, ActionListener act){
        JButton button = new JButton(text);
        button.addActionListener(act);
        button.setMargin(new Insets(0,0,0,0));
        return button;
    }

    public static JLabel createLabel(JLabel label){
        label.setFont(new Font("굴림",1,12));
        return label;
    }

    public static JLabel createLabel(JLabel label,Font font){
        label.setFont(font);
        return label;
    }

    public static ImageIcon getImage(String url,int width,int height){
       return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(width,height,Image.SCALE_SMOOTH));
    }

    public static void iMsg(String title,String caption){
        JOptionPane.showMessageDialog(null,caption,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void eMsg(String title,String caption){
        JOptionPane.showMessageDialog(null,caption,title,JOptionPane.ERROR_MESSAGE);
    }

    public void openFrame(JFrame frame){
        dispose();
        stack.push(frame);
        stack.peek().setVisible(true);
    }

    public void closingFrame(){
        dispose();
        stack.pop();
        stack.peek().setVisible(true);
    }

    public static void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void closingAction();

}
