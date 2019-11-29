/*
 * Created by JFormDesigner on Thu Nov 28 15:11:57 EST 2019
 */

package view;

import java.awt.*;
import javax.swing.*;
import com.intellij.uiDesigner.core.*;

/**
 * @author unknown
 */
public class AddCourse extends JFrame {
    public AddCourse(CourseList courseList) {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label3 = new JLabel();
        textField_name = new JTextField();
        label4 = new JLabel();
        textField_section = new JTextField();
        label5 = new JLabel();
        comboBox_season = new JComboBox<>();
        comboBox_year = new JComboBox<>();
        label6 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea_description = new JTextArea();
        label7 = new JLabel();
        comboBox_chooseTemplate = new JComboBox<>();
        label8 = new JLabel();
        button_selectfile = new JButton();
        label_filename = new JLabel();
        button_save = new JButton();
        button_cancel = new JButton();
        label_title = new JLabel();
        hSpacer2 = new JPanel(null);

        //======== this ========
        setTitle("Add a Course");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label3 ----
        label3.setText("Course Name*:");
        label3.setForeground(Color.black);
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(40, 55), label3.getPreferredSize()));

        //---- textField_name ----
        textField_name.setForeground(Color.black);
        textField_name.setBackground(Color.white);
        contentPane.add(textField_name);
        textField_name.setBounds(165, 50, 180, textField_name.getPreferredSize().height);

        //---- label4 ----
        label4.setText("Section*:");
        label4.setForeground(Color.black);
        contentPane.add(label4);
        label4.setBounds(40, 90, 88, label4.getPreferredSize().height);

        //---- textField_section ----
        textField_section.setForeground(Color.black);
        textField_section.setBackground(Color.white);
        contentPane.add(textField_section);
        textField_section.setBounds(165, 85, 180, 20);

        //---- label5 ----
        label5.setText("Semester*:");
        label5.setForeground(Color.black);
        contentPane.add(label5);
        label5.setBounds(40, 125, 88, label5.getPreferredSize().height);

        //---- comboBox_season ----
        comboBox_season.setBackground(Color.white);
        comboBox_season.setForeground(Color.black);
        comboBox_season.setModel(new DefaultComboBoxModel<>(new String[] {
            "Fall",
            "Spring",
            "Summer"
        }));
        contentPane.add(comboBox_season);
        comboBox_season.setBounds(165, 120, 80, comboBox_season.getPreferredSize().height);

        //---- comboBox_year ----
        comboBox_year.setBackground(Color.white);
        comboBox_year.setForeground(Color.black);
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
        comboBox_year.setBounds(255, 120, 90, comboBox_year.getPreferredSize().height);

        //---- label6 ----
        label6.setText("Description:");
        label6.setForeground(Color.black);
        contentPane.add(label6);
        label6.setBounds(40, 155, 88, label6.getPreferredSize().height);

        //======== scrollPane1 ========
        {

            //---- textArea_description ----
            textArea_description.setForeground(Color.black);
            textArea_description.setBackground(Color.white);
            scrollPane1.setViewportView(textArea_description);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(165, 160, 180, 60);

        //---- label7 ----
        label7.setText("Grade Breakdown:*");
        label7.setForeground(Color.black);
        contentPane.add(label7);
        label7.setBounds(40, 255, 120, label7.getPreferredSize().height);

        //---- comboBox_chooseTemplate ----
        comboBox_chooseTemplate.setBackground(Color.white);
        comboBox_chooseTemplate.setForeground(Color.black);
        comboBox_chooseTemplate.setModel(new DefaultComboBoxModel<>(new String[] {
            "Add new later"
        }));
        contentPane.add(comboBox_chooseTemplate);
        comboBox_chooseTemplate.setBounds(165, 250, 180, 25);

        //---- label8 ----
        label8.setText("Import Students:");
        label8.setForeground(Color.black);
        contentPane.add(label8);
        label8.setBounds(40, 295, 120, label8.getPreferredSize().height);

        //---- button_selectfile ----
        button_selectfile.setText("select file");
        button_selectfile.setForeground(Color.black);
        button_selectfile.setBackground(new Color(204, 204, 204));
        contentPane.add(button_selectfile);
        button_selectfile.setBounds(new Rectangle(new Point(165, 290), button_selectfile.getPreferredSize()));

        //---- label_filename ----
        label_filename.setText("filename.xlsx");
        label_filename.setForeground(Color.black);
        contentPane.add(label_filename);
        label_filename.setBounds(new Rectangle(new Point(265, 295), label_filename.getPreferredSize()));

        //---- button_save ----
        button_save.setText("save");
        button_save.setForeground(Color.black);
        button_save.setBackground(new Color(204, 204, 204));
        contentPane.add(button_save);
        button_save.setBounds(75, 330, 88, 25);

        //---- button_cancel ----
        button_cancel.setText("cancel");
        button_cancel.setForeground(Color.black);
        button_cancel.setBackground(new Color(204, 204, 204));
        contentPane.add(button_cancel);
        button_cancel.setBounds(240, 330, 88, 25);

        //---- label_title ----
        label_title.setText("Add a Course");
        label_title.setFont(new Font("Impact", Font.PLAIN, 30));
        label_title.setForeground(Color.black);
        contentPane.add(label_title);
        label_title.setBounds(115, 5, label_title.getPreferredSize().width, 45);
        contentPane.add(hSpacer2);
        hSpacer2.setBounds(355, 0, 40, 375);

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
    private JLabel label3;
    private JTextField textField_name;
    private JLabel label4;
    private JTextField textField_section;
    private JLabel label5;
    private JComboBox<String> comboBox_season;
    private JComboBox<String> comboBox_year;
    private JLabel label6;
    private JScrollPane scrollPane1;
    private JTextArea textArea_description;
    private JLabel label7;
    private JComboBox<String> comboBox_chooseTemplate;
    private JLabel label8;
    private JButton button_selectfile;
    private JLabel label_filename;
    private JButton button_save;
    private JButton button_cancel;
    private JLabel label_title;
    private JPanel hSpacer2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
