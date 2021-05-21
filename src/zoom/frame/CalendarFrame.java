package zoom.frame;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarFrame extends BaseFrame{
    LocalDate now = LocalDate.now();
    LocalDate date = LocalDate.of(now.getYear(), now.getMonthValue(), 1);
    JPanel centerPanel = new JPanel(new GridLayout(0,7));
    JLabel dateLabel = createLabel(new JLabel(date.format(DateTimeFormatter.ofPattern("yyyy 년 MM 월"))));
    JButton previousBtn = createButton("◁", e->previousBtnAct());
    JButton nextBtn = createButton("▷",e->nextBtnAct());

    JTextField tf;

    public CalendarFrame(JTextField tf){
        super("월-일 선택",350,420);

        this.tf = tf;

        setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        JPanel datePanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER,35,0)),350,20);
        JLabel dateLabels[] = new JLabel[7];
        String str[] = "일월화수목금토".split("");
        for(int i=0;i<dateLabels.length;i++) {
            dateLabels[i] = new JLabel(str[i]);
            datePanel.add(dateLabels[i]);
        }
        dateLabels[0].setForeground(Color.red);
        dateLabels[6].setForeground(Color.blue);

        JPanel btnPanel = createComponent(new JPanel(new FlowLayout(FlowLayout.CENTER)),350,32);
        previousBtn.setEnabled(false);
        btnPanel.add(previousBtn);
        btnPanel.add(dateLabel);
        btnPanel.add(nextBtn);

        setCalendar();
        add(btnPanel);
        add(datePanel);
        add(centerPanel);
    }

    private void setCalendar(){
        centerPanel.removeAll();
        boolean start = false;
        String dayOfWeek[] = {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        int k=0;
        for(int i=0;i<date.lengthOfMonth();i++) {
            if (!start) {
                if (dayOfWeek[i % 7].equals(String.valueOf(date.getDayOfWeek()))) {
                    i = 0;
                    start = true;
                }
            }
            if(start){
                JButton btn = createComponent(createButtonWithoutMargin((i+1)+"",e -> calBtnAct(e.getActionCommand())),48,48);
                if(k%7==0)
                    btn.setForeground(Color.red);
                if(k%7==6)
                    btn.setForeground(Color.blue);
                if(date.getMonthValue() == now.getMonthValue() && i+1<=now.getDayOfMonth()-1 && date.getYear() == now.getYear()) //현재날짜 +1의 날짜부터 활성화
                    btn.setEnabled(false);

                centerPanel.add(btn);

            }else{
                centerPanel.add(new JPanel());
            }
            k++;
        }
    }

    private void calBtnAct(String text){
        closingAction();
        tf.setText(String.format("%d-%02d-%02d", date.getYear(),date.getMonthValue(),Integer.parseInt(text)));
    }

    private void nextBtnAct(){
        date = date.plusMonths(1);
        setCalendar();
        dateLabel.setText(date.format(DateTimeFormatter.ofPattern("yyyy 년 MM 월")));
        previousBtn.setEnabled(true);
        revalidate();
    }

    private void previousBtnAct(){
        date = date.minusMonths(1);
        setCalendar();
        dateLabel.setText(date.format(DateTimeFormatter.ofPattern("yyyy 년 MM 월")));
        if(date.getMonthValue() == now.getMonthValue() && date.getYear() == now.getYear())
            previousBtn.setEnabled(false);
        revalidate();
    }

    @Override
    public void closingAction() {
        dispose();
        stack.peek().setVisible(true);
    }
}
