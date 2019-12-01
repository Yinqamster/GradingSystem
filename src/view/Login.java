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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        panel1 = new JPanel();
        button_login = new JButton();
        button_exit = new JButton();
        dialogPane = new JPanel();
        hSpacer3 = new JPanel(null);
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        hSpacer4 = new JPanel(null);
        vSpacer1 = new JPanel(null);

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder (
            0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder
            . BOTTOM, new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .
            red ) ,panel1. getBorder () ) ); panel1. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java .
            beans. PropertyChangeEvent e) { if( "\u0062order" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            panel1.setLayout(null);

            //---- button_login ----
            button_login.setText("Login");
            button_login.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button_loginMouseReleased(e);
                }
            });
            panel1.add(button_login);
            button_login.setBounds(new Rectangle(new Point(90, 5), button_login.getPreferredSize()));

            //---- button_exit ----
            button_exit.setText("Exit");
            button_exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button_exitMouseReleased(e);
                }
            });
            panel1.add(button_exit);
            button_exit.setBounds(175, 5, 63, 23);

            //======== dialogPane ========
            {
                dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
                dialogPane.setLayout(new BorderLayout());
                dialogPane.add(hSpacer3, BorderLayout.EAST);
            }
            panel1.add(dialogPane);
            dialogPane.setBounds(-20, -150, 353, 173);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(10, 125, 329, panel1.getPreferredSize().height);

        //======== contentPanel ========
        {
            contentPanel.setLayout(null);

            //---- label1 ----
            label1.setText("Grading System");
            label1.setFont(new Font("Impact", Font.PLAIN, 30));
            contentPanel.add(label1);
            label1.setBounds(80, 0, 205, label1.getPreferredSize().height);

            //---- label2 ----
            label2.setText("Username:");
            contentPanel.add(label2);
            label2.setBounds(new Rectangle(new Point(35, 65), label2.getPreferredSize()));

            //---- label3 ----
            label3.setText("Password:");
            contentPanel.add(label3);
            label3.setBounds(35, 90, 54, 15);
            contentPanel.add(textField1);
            textField1.setBounds(95, 60, 195, textField1.getPreferredSize().height);
            contentPanel.add(textField2);
            textField2.setBounds(95, 90, 195, 21);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                    Rectangle bounds = contentPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = contentPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                contentPanel.setMinimumSize(preferredSize);
                contentPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(contentPanel);
        contentPanel.setBounds(10, 5, 329, 125);
        contentPane.add(hSpacer4);
        hSpacer4.setBounds(new Rectangle(new Point(335, 0), hSpacer4.getPreferredSize()));
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(155, 155, 35, 15);

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
    private JPanel panel1;
    private JButton button_login;
    private JButton button_exit;
    private JPanel dialogPane;
    private JPanel hSpacer3;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel hSpacer4;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
