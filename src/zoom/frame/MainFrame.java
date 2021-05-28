package zoom.frame;

import zoom.model.*;
import zoom.model.Class;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainFrame extends BaseFrame{

    public MainFrame(){
        super("2-6 zoom 수업",800,600);

        setLayout(null);

        JPanel linkPanels = createComponent(new JPanel(new GridLayout(0,4)),250,50,400,300);

        JPanel buttonPanel = createComponent(new JPanel(new GridLayout(0,1)),10,50,200,100);
        buttonPanel.add(createButton("예약하기",e -> openFrame(new ReservationFrame())));
        buttonPanel.add(createButton("종료",e -> System.exit(0)));

        for (int i=0;i<classes.length;i++){
            linkPanels.add(createComponent(new LinkPanel(classes[i],
                     classes[i].name.equals("구글미트")? "./image/googlemeetImage.png" : "./image/zoomImage.png"),0,0,100,100));
        }

        add(buttonPanel);
        add(linkPanels);
    }

    class LinkPanel extends JPanel{
        LinkPanel(Class cls, String imageURL){
            super(new FlowLayout(FlowLayout.LEFT));

            JLabel image = createLabel(new JLabel(getImage(imageURL,70,70)));

            JLabel nameLabel = createLabel(new JLabel(cls.name));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2){
                        openBrowser(cls.url);
                    }
                }
            });

            add(image);
            add(nameLabel);

        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }

    @Override
    public void closingAction() {
        closingFrame();
    }
}
