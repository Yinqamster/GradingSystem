/*
 * Created by JFormDesigner on Sun Dec 08 17:02:24 EST 2019
 */

package view;

import controller.AddChildController;
import model.Course;
import model.GradingRule;
import utils.ErrCode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jun li
 */
public class AddChild extends JFrame {
    private MainFrame mainFrame;
    private GradingRule parent;
    private int depth;
    private Course course;

    public AddChild(MainFrame mainFrame, Course course, String parentID, int depth) {
        initComponents();
        this.mainFrame = mainFrame;
        this.depth = depth;
        this.course = course;

        if(parentID != null) {
            this.parent = AddChildController.getGradingRuleByID(course.getCourseID(),parentID);
            this.label_parentName.setText(parent.getName());
        }else {
            this.parent = null;
            this.label_parentName.setText(course.getName() + " 100%");
        }
    }

    private void button_saveBreakdownMouseReleased(MouseEvent e) {
        if(isTextFieldValid()){
            String name = textField_name.getText();
            double fullScore = Double.parseDouble(spinner_fullScore.getValue().toString());
            double proportion = Double.parseDouble(spinner_percentage.getValue().toString())/100;
            String parentID = parent==null ? null : parent.getId();
            // add a child
            AddChildController.addGradingRule(course.getCourseID(),name,fullScore,proportion,parentID,depth);
            JOptionPane.showMessageDialog(this,"Rule added");
            mainFrame.refreshAll();
            this.dispose();
        }
    }

    private boolean isTextFieldValid(){
        if(textField_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, ErrCode.TEXTFIELDEMPTY);
            return false;
        }else if(!AddChildController.isRuleNameUnique(textField_name.getText(),course)){
            JOptionPane.showMessageDialog(this,ErrCode.NAMENOTUNIQUE);
            return false;
        }else return true;
    }

    private void button1MouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jun Li
        button_saveBreakdown = new JButton();
        label5 = new JLabel();
        textField_name = new JTextField();
        label6 = new JLabel();
        spinner_percentage = new JSpinner();
        label4 = new JLabel();
        label7 = new JLabel();
        spinner_fullScore = new JSpinner();
        label1 = new JLabel();
        label_parentName = new JLabel();
        button1 = new JButton();
        hSpacer1 = new JPanel(null);
        vSpacer1 = new JPanel(null);

        //======== this ========
        setTitle("Add");
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button_saveBreakdown ----
        button_saveBreakdown.setText("Save");
        button_saveBreakdown.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
        button_saveBreakdown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_saveBreakdownMouseReleased(e);
            }
        });
        contentPane.add(button_saveBreakdown);
        button_saveBreakdown.setBounds(50, 140, 95, 25);

        //---- label5 ----
        label5.setText("Name*:");
        contentPane.add(label5);
        label5.setBounds(40, 40, 60, 15);
        contentPane.add(textField_name);
        textField_name.setBounds(125, 40, 135, 21);

        //---- label6 ----
        label6.setText("Proportion:");
        contentPane.add(label6);
        label6.setBounds(40, 70, 80, 15);

        //---- spinner_percentage ----
        spinner_percentage.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        contentPane.add(spinner_percentage);
        spinner_percentage.setBounds(125, 70, 135, 22);

        //---- label4 ----
        label4.setText("%");
        contentPane.add(label4);
        label4.setBounds(265, 75, 20, 15);

        //---- label7 ----
        label7.setText("Full Score:");
        contentPane.add(label7);
        label7.setBounds(40, 100, 70, label7.getPreferredSize().height);

        //---- spinner_fullScore ----
        spinner_fullScore.setModel(new SpinnerNumberModel(0, 0, null, 1));
        contentPane.add(spinner_fullScore);
        spinner_fullScore.setBounds(125, 100, 135, spinner_fullScore.getPreferredSize().height);

        //---- label1 ----
        label1.setText("Category:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(40, 15), label1.getPreferredSize()));

        //---- label_parentName ----
        label_parentName.setText("parent name");
        contentPane.add(label_parentName);
        label_parentName.setBounds(125, 15, 135, label_parentName.getPreferredSize().height);

        //---- button1 ----
        button1.setText("Cancel");
        button1.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button1MouseReleased(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(170, 140, 95, button1.getPreferredSize().height);
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(280, 10, 25, 35);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(275, 155, 30, 30);

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
    // Generated using JFormDesigner Evaluation license - Jun Li
    private JButton button_saveBreakdown;
    private JLabel label5;
    private JTextField textField_name;
    private JLabel label6;
    private JSpinner spinner_percentage;
    private JLabel label4;
    private JLabel label7;
    private JSpinner spinner_fullScore;
    private JLabel label1;
    private JLabel label_parentName;
    private JButton button1;
    private JPanel hSpacer1;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
