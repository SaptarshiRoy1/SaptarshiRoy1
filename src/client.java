import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;

public class client implements ActionListener {

    static JFrame f = new JFrame();
    JTextField text;
    static JPanel pp2;
    static DataOutputStream dos;
    static Box vertical = Box.createVerticalBox();
    client(){
        f.setSize(450,750);
        f.setLocation(800, 40);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLayout(null);

        JPanel pp = new JPanel();
        f.add(pp);
        pp.setLayout(null);
        pp.setBackground(new Color(2, 63, 45));
        pp.setBounds(0,0,450,50);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel backbtn = new JLabel(i3);
        backbtn.setBounds(5,12,25,25);
        pp.add(backbtn);

        backbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
                super.mouseClicked(e);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(43,43,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,5,43,43);
        pp.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel videobtn = new JLabel(i9);
        videobtn.setBounds(300,12,25,25);
        pp.add(videobtn);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phonebtn = new JLabel(i12);
        phonebtn.setBounds(345,12,25,25);
        pp.add(phonebtn);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(12,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel threedot = new JLabel(i15);
        threedot.setBounds(390,12,12,25);
        pp.add(threedot);

        JLabel name = new JLabel("Bablu");
        name.setBounds(99,8,100,28);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        pp.add(name);

        JLabel status = new JLabel("Online");
        status.setBounds(99,24,90,28);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        pp.add(status);


        pp2 =new JPanel();
        pp2.setBounds(18,65,412,600);
        f.add(pp2);

        text =new JTextField();
        text.setBounds(20,690,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
        f.add(text);
        text.setText(null);

        JButton send = new JButton("Send");
        send.setBounds(340,690,80,40);
        send.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        send.setBackground(new Color(2, 63, 45));
        send.setForeground(Color.white);
        f.add(send);
        send.addActionListener(this);


        f.setUndecorated(true);
        f.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            String msg = text.getText();
            System.out.println(msg);

            JPanel msg3 = formatLebel(msg);

            pp2.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(msg3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            pp2.add(vertical, BorderLayout.PAGE_START);

            //msg3.setBounds(340,690,80,40);
            //msg3.setFont(new Font("SAN_SERIF",Font.BOLD,18));
            //msg3.setBackground(new Color(2, 63, 45));

            dos.writeUTF(msg);

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLebel(String msg){
        JLabel msg2 = new JLabel(msg);
        JPanel msg3 = new JPanel();
        msg3.add(msg2);

        msg2.setFont(new Font("Tahoma",Font.PLAIN,17));
        msg3.setBackground(new Color(127, 241, 146));
        //msg2.setForeground(Color.white);
        msg2.setBorder(new EmptyBorder(2,5,5,10));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        time.setForeground(Color.LIGHT_GRAY);

        msg3.add(time);

        msg3.setLayout(new BoxLayout(msg3, BoxLayout.Y_AXIS));
        return msg3;
    }

    public static void main(String[] args) {
        new client();

        try {
            Socket s = new Socket("127.0.0.1",4001);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

                while (true){
                    pp2.setLayout(new BorderLayout());
                    String msg = dis.readUTF();
                    JPanel msg3 = formatLebel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(msg3, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                    vertical.add(Box.createVerticalStrut(15));
                    pp2.add(vertical, BorderLayout.PAGE_START);
                    msg3.setBackground(new Color(240, 253, 242));
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
