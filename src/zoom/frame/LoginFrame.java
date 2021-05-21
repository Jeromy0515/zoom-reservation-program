package zoom.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends BaseFrame{
    private String id = "smc206";
    private JPasswordField tfId  = new JPasswordField(15);

    public LoginFrame(){
        super("로그인",300,120);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(tfId);
        centerPanel.add(createButton("확인",e -> login()));

        tfId.addActionListener(e->login());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                stack.push(LoginFrame.this);
            }
        });

        add(createLabel(new JLabel("Login",JLabel.CENTER),new Font("맑은 고딕",1,20)),BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(createLabel(new JLabel("코드를 입력해주세요.",JLabel.CENTER)),BorderLayout.SOUTH);
    }

    private void login(){
        if(!isCorrect()){
            eMsg("경고","틀렸습니다. 다시입력해주세요.");
            tfId.setText("");
            return;
        }

        iMsg("정보","환영합니다.");
        openFrame(new MainFrame());

    }

    private boolean isCorrect(){
        return tfId.getText().equals(id);
    }

    @Override
    public void closingAction() {
        System.exit(0);
    }

}
