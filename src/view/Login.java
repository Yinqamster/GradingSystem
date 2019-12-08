/*
 * Created by JFormDesigner on Thu Nov 28 22:15:49 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class Login extends JFrame {
    public Login() {
        initComponents();
    }

    private void button_loginMouseReleased(MouseEvent e) {
        CourseList courseList = new CourseList();
        courseList.setVisible(true);
        this.dispose();
    }

    private void button_exitMouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void thisWindowClosing(WindowEvent e) {
        System.exit(0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        button_login = new JButton();
        button_exit = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        hSpacer4 = new JPanel(null);
        vSpacer1 = new JPanel(null);

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        setTitle("Grading System");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button_login ----
        button_login.setText("Login");
        button_login.setIcon(new ImageIcon(getClass().getResource("/images/login.png")));
        button_login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_loginMouseReleased(e);
            }
        });
        contentPane.add(button_login);
        button_login.setBounds(60, 115, 85, button_login.getPreferredSize().height);

        //---- button_exit ----
        button_exit.setText("Exit");
        button_exit.setIcon(new ImageIcon(getClass().getResource("/images/exit.png")));
        button_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_exitMouseReleased(e);
            }
        });
        contentPane.add(button_exit);
        button_exit.setBounds(180, 115, 85, button_exit.getPreferredSize().height);

        //---- label1 ----
        label1.setText("Grading System");
        label1.setFont(new Font("Impact", Font.PLAIN, 30));
        contentPane.add(label1);
        label1.setBounds(65, 5, 205, label1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("Username:");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(30, 60), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("Password:");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(30, 85), label3.getPreferredSize()));
        contentPane.add(textField1);
        textField1.setBounds(105, 55, 185, textField1.getPreferredSize().height);
        contentPane.add(textField2);
        textField2.setBounds(105, 80, 185, textField2.getPreferredSize().height);
        contentPane.add(hSpacer4);
        hSpacer4.setBounds(new Rectangle(new Point(305, 10), hSpacer4.getPreferredSize()));
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(155, 145, 35, 10);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JButton button_login;
    private JButton button_exit;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel hSpacer4;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
