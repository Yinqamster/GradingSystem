/*
 * Created by JFormDesigner on Thu Nov 28 15:41:42 EST 2019
 */

package view;

import controller.SaveAsTemplateController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jun Li
 */
public class SaveAsTemplate extends JFrame {
    private String courseID;
    public SaveAsTemplate(String courseID) {
        initComponents();
        this.courseID = courseID;
    }

    private void textField1KeyTyped(KeyEvent e) {
        // check if templateName is unique
        String name = textField_name.getText();
        if(SaveAsTemplateController.isTemplateNameUnique(name)){
            label_isValid.setText("\u2713"); //√
            button_save.setEnabled(true);
        }else{
            label_isValid.setText("\u2613"); //×
            button_save.setEnabled(false);
        }
    }

    private void button_cancelMouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void button_saveMouseReleased(MouseEvent e) {
        String templateName = textField_name.getText();
        SaveAsTemplateController.saveTemplate(courseID,templateName);

        // show message box
        JOptionPane.showMessageDialog(this, "Template saved.");
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        frame1 = new JFrame();
        label1 = new JLabel();
        textField_name = new JTextField();
        label_isValid = new JLabel();
        button_save = new JButton();
        button_cancel = new JButton();
        vSpacer1 = new JPanel(null);

        //======== frame1 ========
        {
            frame1.setTitle("Save as Template");
            frame1.setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(null);

            //---- label1 ----
            label1.setText("Template Name:");
            frame1ContentPane.add(label1);
            label1.setBounds(35, 25, label1.getPreferredSize().width, 30);

            //---- textField_name ----
            textField_name.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    textField1KeyTyped(e);
                }
            });
            frame1ContentPane.add(textField_name);
            textField_name.setBounds(125, 30, 160, textField_name.getPreferredSize().height);
            frame1ContentPane.add(label_isValid);
            label_isValid.setBounds(new Rectangle(new Point(290, 35), label_isValid.getPreferredSize()));

            //---- button_save ----
            button_save.setText("save");
            button_save.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
            button_save.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button_saveMouseReleased(e);
                }
            });
            frame1ContentPane.add(button_save);
            button_save.setBounds(60, 65, 90, button_save.getPreferredSize().height);

            //---- button_cancel ----
            button_cancel.setText("cancel");
            button_cancel.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
            button_cancel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button_cancelMouseReleased(e);
                }
            });
            frame1ContentPane.add(button_cancel);
            button_cancel.setBounds(200, 65, 90, button_cancel.getPreferredSize().height);
            frame1ContentPane.add(vSpacer1);
            vSpacer1.setBounds(5, 95, 330, 15);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < frame1ContentPane.getComponentCount(); i++) {
                    Rectangle bounds = frame1ContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = frame1ContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                frame1ContentPane.setMinimumSize(preferredSize);
                frame1ContentPane.setPreferredSize(preferredSize);
            }
            frame1.pack();
            frame1.setLocationRelativeTo(null);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JFrame frame1;
    private JLabel label1;
    private JTextField textField_name;
    private JLabel label_isValid;
    private JButton button_save;
    private JButton button_cancel;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
