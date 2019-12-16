/*
 * Created by JFormDesigner on Sat Nov 30 19:28:42 EST 2019
 */

package view;

import controller.ShowEditCourseController;
import model.Course;
import utils.ErrCode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jun Li
 */
public class ShowEditCourse extends JFrame {
    private Course course;
    MainFrame parent;
    public ShowEditCourse(MainFrame parent, String courseID) {
        initComponents();
        this.parent = parent;
        this.course = ShowEditCourseController.getCourseByID(courseID);

        loadInfo();
    }

    //test
    public ShowEditCourse() {
        initComponents();
    }
    // -------------------------

    private void loadInfo(){
        this.textField_courseName.setText(course.getName());
        this.textField_section.setText(course.getSection());
        this.textArea_description.setText(course.getDescription());
        String[] semester = course.getSemester().split(" ");
        String season = semester[0];
        String year = semester[1];
        for(int i=0;i<comboBox_season.getItemCount();i++){
            if(comboBox_season.getItemAt(i).equals(season)){
                comboBox_season.setSelectedIndex(i);
                break;
            }
        }
        for(int i=0;i<comboBox_year.getItemCount();i++){
            if(comboBox_year.getItemAt(i).equals(year)){
                comboBox_year.setSelectedIndex(i);
                break;
            }
        }
    }

    private void button_saveMouseReleased(MouseEvent e) {
        if(checkValid()) {
            // if all textfileds with * has text
            String name = textField_courseName.getText();
            String section = textField_section.getText();
            String semester = comboBox_season.getSelectedItem().toString() + " " + comboBox_year.getSelectedItem().toString();
            String description = textArea_description.getText();

            ShowEditCourseController.updateCourse(name,section,semester,description,course.getCourseID());

            // show message box
            JOptionPane.showMessageDialog(this, "Course updated.");
            // refresh course information in mainframe
            parent.refreshCourseNameAndSection(this.course);

            this.dispose();
        }else{
            label_warning.setText(ErrCode.TEXTFIELDEMPTY.getDescription());
        }
    }

    private boolean checkValid(){
        if(textField_courseName.getText().equals("")) return false;
        else if(textField_section.getText().equals("")) return false;
        else return true;
    }

    private void button_cancelMouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        label2 = new JLabel();
        textField_courseName = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        textField_section = new JTextField();
        label5 = new JLabel();
        comboBox_season = new JComboBox<>();
        comboBox_year = new JComboBox<>();
        hSpacer1 = new JPanel(null);
        label6 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea_description = new JTextArea();
        button_save = new JButton();
        button_cancel = new JButton();
        vSpacer1 = new JPanel(null);
        label_warning = new JLabel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        setTitle("Show/Edit Course");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Show/Edit Course");
        label1.setFont(new Font("Impact", Font.PLAIN, 30));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(85, 15), label1.getPreferredSize()));

        //---- label2 ----
        label2.setText("Course Name*:");
        contentPane.add(label2);
        label2.setBounds(45, 75, 95, label2.getPreferredSize().height);
        contentPane.add(textField_courseName);
        textField_courseName.setBounds(145, 70, 190, textField_courseName.getPreferredSize().height);
        contentPane.add(label3);
        label3.setBounds(45, 100, 75, label3.getPreferredSize().height);

        //---- label4 ----
        label4.setText("Section*:");
        contentPane.add(label4);
        label4.setBounds(45, 105, 78, 15);
        contentPane.add(textField_section);
        textField_section.setBounds(145, 100, 190, 21);

        //---- label5 ----
        label5.setText("Semester*:");
        contentPane.add(label5);
        label5.setBounds(45, 130, 78, 15);

        //---- comboBox_season ----
        comboBox_season.setModel(new DefaultComboBoxModel<>(new String[] {
            "Fall",
            "Spring",
            "Summer"
        }));
        contentPane.add(comboBox_season);
        comboBox_season.setBounds(145, 130, 90, comboBox_season.getPreferredSize().height);

        //---- comboBox_year ----
        comboBox_year.setModel(new DefaultComboBoxModel<>(new String[] {
            "2019",
            "2020",
            "2021",
            "2022",
            "2023",
            "2024",
            "2025"
        }));
        contentPane.add(comboBox_year);
        comboBox_year.setBounds(245, 130, 90, comboBox_year.getPreferredSize().height);
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(350, 70, 25, 30);

        //---- label6 ----
        label6.setText("Description:");
        contentPane.add(label6);
        label6.setBounds(45, 160, 90, label6.getPreferredSize().height);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea_description);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(145, 160, 190, 95);

        //---- button_save ----
        button_save.setText("save");
        button_save.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
        button_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_saveMouseReleased(e);
            }
        });
        contentPane.add(button_save);
        button_save.setBounds(80, 280, 95, button_save.getPreferredSize().height);

        //---- button_cancel ----
        button_cancel.setText("cancel");
        button_cancel.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        button_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_cancelMouseReleased(e);
            }
        });
        contentPane.add(button_cancel);
        button_cancel.setBounds(205, 280, 95, button_cancel.getPreferredSize().height);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(180, 305, vSpacer1.getPreferredSize().width, 25);

        //---- label_warning ----
        label_warning.setForeground(Color.red);
        contentPane.add(label_warning);
        label_warning.setBounds(110, 260, 180, 15);

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
    private JTextField textField_courseName;
    private JLabel label3;
    private JLabel label4;
    private JTextField textField_section;
    private JLabel label5;
    private JComboBox<String> comboBox_season;
    private JComboBox<String> comboBox_year;
    private JPanel hSpacer1;
    private JLabel label6;
    private JScrollPane scrollPane1;
    private JTextArea textArea_description;
    private JButton button_save;
    private JButton button_cancel;
    private JPanel vSpacer1;
    private JLabel label_warning;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
