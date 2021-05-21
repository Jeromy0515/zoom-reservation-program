package zoom.frame;


import zoom.classes.Bigdata;
import zoom.model.Class;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ReservationFrame extends BaseFrame{

    LocalDateTime now = LocalDateTime.now();

    JPanel contentsPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER,0,0)),370,350);
    int cntComp;

    JScrollPane scrollPane = new JScrollPane(contentsPanel);

    public ReservationFrame(){
        super("예약",500,400);

        setLayout(new BorderLayout());

        JPanel westPanel = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(0,1,0,5));
        buttonPanel.add(createButton("예약하기",e -> openFrame(new ReservingFrame())));
        buttonPanel.add(createButton("뒤로가기",e->closingFrame()));

        westPanel.add(buttonPanel);
        add(westPanel,BorderLayout.WEST);
        add(scrollPane,BorderLayout.CENTER);
    }

    class ReservationPanel extends JPanel{
        JButton cancelBtn = createButton("예약취소",e->{});

        Class cls;

        ReservationPanel(String clsName,String time){
            super(new BorderLayout());

            cls = linearSearchOfClass(clsName);

            setBorder(new LineBorder(Color.black));

            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            southPanel.add(createLabel(new JLabel(time)));
            southPanel.add(cancelBtn);

            add(createLabel(new JLabel(cls.name)), BorderLayout.CENTER);
            add(southPanel, BorderLayout.SOUTH);

            startReservation(this,time);
        }
        
        private void startReservation(JComponent comp,String time){

            boolean flag = false;

            new Thread(()->{

                while(true){
                    now = LocalDateTime.now();

                    String nowString = String.format("%04d-%02d-%02d %02d:%02d",now.getYear(),now.getMonthValue(),now.getDayOfMonth(),now.getHour(),now.getMinute());
//                    System.out.println("Reserve time:"+time);
//                    System.out.println("Now time:"+nowString);

                    if(time.equals(nowString)){
                        openBrowser(cls.url);
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                contentsPanel.remove(comp);
                contentsPanel.revalidate();
                contentsPanel.repaint();
            }).start();
        }

        private Class linearSearchOfClass(String clsName){
            for (int i=0;i<classes.length;i++){
                if(clsName.equals(classes[i].name))
                    return classes[i];
            }
            return null;
        }
    }

    class ReservingFrame extends BaseFrame{
        JTextField tfDate = new JTextField(7);
        JComboBox<String> classCb = new JComboBox<>();
        JComboBox<String> cbs[] = new JComboBox[2]; // 0:hour 1:minute
        ReservingFrame(){
            super("시간 예약",360,110);
            setLayout(new BorderLayout());

            setLocationRelativeTo(null);

            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


            for (int i=0;i<classes.length;i++){
                classCb.addItem(classes[i].name);
            }

            centerPanel.add(classCb);
            centerPanel.add(tfDate);

            tfDate.setText("월 일 선택");

            tfDate.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    new CalendarFrame(tfDate).setVisible(true);
                    dispose();
                }
            });

            for (int i=0;i<cbs.length;i++){
                cbs[i] = new JComboBox<>();
                centerPanel.add(cbs[i]);
            }

            cbs[0].addItem("시 선택");
            cbs[1].addItem("분 선택");

            setComboBox(cbs[0],0,23);
            setComboBox(cbs[1],0,59);

            southPanel.add(createButton("예약",e -> reserve()));
            southPanel.add(createButton("취소",e -> {
                dispose();
                stack.pop();
                stack.peek().setVisible(true);
            }));

            add(centerPanel,BorderLayout.CENTER);
            add(southPanel, BorderLayout.SOUTH);

            
        }

        private void reserve(){
            if(tfDate.getText().equals("월 일 선택") || cbs[0].getSelectedIndex() == 0 || cbs[1].getSelectedIndex() == 0){
                eMsg("경고","날짜를 혹은 시간을 선택해주세요.");
                return;
            }

            contentsPanel.add(createComponent(new ReservationPanel(classCb.getSelectedItem().toString(),tfDate.getText()+" "+
                    String.format("%02d:%02d",Integer.parseInt(cbs[0].getSelectedItem().toString())%24,Integer.parseInt(cbs[1].getSelectedItem().toString())%60)),395,80));
            contentsPanel.setPreferredSize(new Dimension(350,110 * ++cntComp));
            closingFrame();
        }

        private JComboBox setComboBox(JComboBox cb,int start,int end){
            for (int i=start;i<=end;i++){
                cb.addItem(i+"");
            }
            return cb;
        }

        @Override
        public void closingAction() {
            closingFrame();
        }
    }


    @Override
    public void closingAction() {
        closingFrame();
    }
}
