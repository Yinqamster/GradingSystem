/*
 * Created by JFormDesigner on Sat Nov 30 20:32:30 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class ShowEditStudent extends JFrame {
    public ShowEditStudent() {
        initComponents();
    }

    private void button_saveMouseReleased(MouseEvent e) {
        // TODO check valid for every compulsory blanks, if valid , then save and refresh the table
    }

    private void button_cancelMouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        label2 = new JLabel();
        textField_firstName = new JTextField();
        label3 = new JLabel();
        textField_middleName = new JTextField();
        label4 = new JLabel();
        textField_lastName = new JTextField();
        label5 = new JLabel();
        textField_buid = new JTextField();
        label6 = new JLabel();
        comboBox_program = new JComboBox<>();
        label7 = new JLabel();
        comboBox_whichAssignment = new JComboBox();
        scrollPane1 = new JScrollPane();
        textArea_comment = new JTextArea();
        button_save = new JButton();
        button_cancel = new JButton();
        vSpacer1 = new JPanel(null);
        label_warning = new JLabel();
        hSpacer1 = new JPanel(null);

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Student");
        label1.setFont(new Font("Impact", Font.PLAIN, 30));
        contentPane.add(label1);
        label1.setBounds(125, 10, 105, label1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("First Name*:");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(45, 65), label2.getPreferredSize()));
        contentPane.add(textField_firstName);
        textField_firstName.setBounds(125, 60, 180, textField_firstName.getPreferredSize().height);

        //---- label3 ----
        label3.setText("Middle Name:");
        contentPane.add(label3);
        label3.setBounds(45, 95, 72, 15);
        contentPane.add(textField_middleName);
        textField_middleName.setBounds(125, 90, 180, 21);

        //---- label4 ----
        label4.setText("Last Name*:");
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(45, 125), label4.getPreferredSize()));
        contentPane.add(textField_lastName);
        textField_lastName.setBounds(125, 120, 180, textField_lastName.getPreferredSize().height);

        //---- label5 ----
        label5.setText("BUID*:");
        contentPane.add(label5);
        label5.setBounds(new Rectangle(new Point(45, 155), label5.getPreferredSize()));
        contentPane.add(textField_buid);
        textField_buid.setBounds(125, 150, 180, textField_buid.getPreferredSize().height);

        //---- label6 ----
        label6.setText("Program*:");
        contentPane.add(label6);
        label6.setBounds(45, 185, 78, 15);

        //---- comboBox_program ----
        comboBox_program.setModel(new DefaultComboBoxModel<>(new String[] {
            "Undergraduate",
            "Graduate"
        }));
        contentPane.add(comboBox_program);
        comboBox_program.setBounds(125, 180, 180, 21);

        //---- label7 ----
        label7.setText("Comment:");
        contentPane.add(label7);
        label7.setBounds(45, 215, 78, 15);
        contentPane.add(comboBox_whichAssignment);
        comboBox_whichAssignment.setBounds(125, 210, 180, 21);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea_comment);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(45, 245, 260, 100);

        //---- button_save ----
        button_save.setText("save");
        button_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_saveMouseReleased(e);
            }
        });
        contentPane.add(button_save);
        button_save.setBounds(new Rectangle(new Point(80, 370), button_save.getPreferredSize()));

        //---- button_cancel ----
        button_cancel.setText("cancel");
        button_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_cancelMouseReleased(e);
            }
        });
        contentPane.add(button_cancel);
        button_cancel.setBounds(205, 370, 70, button_cancel.getPreferredSize().height);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(160, 395, vSpacer1.getPreferredSize().width, 25);

        //---- label_warning ----
        label_warning.setForeground(Color.red);
        contentPane.add(label_warning);
        label_warning.setBounds(new Rectangle(new Point(80, 350), label_warning.getPreferredSize()));
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(325, 30, 25, hSpacer1.getPreferredSize().height);

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
    private JLabel label1;
    private JLabel label2;
    private JTextField textField_firstName;
    private JLabel label3;
    private JTextField textField_middleName;
    private JLabel label4;
    private JTextField textField_lastName;
    private JLabel label5;
    private JTextField textField_buid;
    private JLabel label6;
    private JComboBox<String> comboBox_program;
    private JLabel label7;
    private JComboBox comboBox_whichAssignment;
    private JScrollPane scrollPane1;
    private JTextArea textArea_comment;
    private JButton button_save;
    private JButton button_cancel;
    private JPanel vSpacer1;
    private JLabel label_warning;
    private JPanel hSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
