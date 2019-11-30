/*
 * Created by JFormDesigner on Thu Nov 28 21:40:46 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import info.clearthought.layout.*;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class Statistics extends JFrame {
    public Statistics() {
        initComponents();
    }

    private void checkBox_isAllMouseReleased(MouseEvent e) {
        if(checkBox_isAll.isSelected()){
            // todo select all checkboxes
        }
        else {
            // todo undo select all checkboxes
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        label_courseName = new JLabel();
        label3 = new JLabel();
        label_section = new JLabel();
        scrollPane_chooseCategories = new JScrollPane();
        panel_categories = new JPanel();
        checkBox1 = new JCheckBox();
        label2 = new JLabel();
        label9 = new JLabel();
        checkBox2 = new JCheckBox();
        label4 = new JLabel();
        label10 = new JLabel();
        checkBox3 = new JCheckBox();
        label8 = new JLabel();
        label11 = new JLabel();
        checkBox_isAll = new JCheckBox();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label_mean = new JLabel();
        label_median = new JLabel();
        label_stddev = new JLabel();
        comboBox_chooseStudent = new JComboBox<>();
        button_back = new JButton();
        vSpacer1 = new JPanel(null);
        hSpacer1 = new JPanel(null);

        //======== this ========
        setTitle("Statistics");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Course:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(40, 20), label1.getPreferredSize()));

        //---- label_courseName ----
        label_courseName.setText("course name");
        contentPane.add(label_courseName);
        label_courseName.setBounds(new Rectangle(new Point(85, 20), label_courseName.getPreferredSize()));

        //---- label3 ----
        label3.setText("Section");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(210, 20), label3.getPreferredSize()));

        //---- label_section ----
        label_section.setText("which section");
        contentPane.add(label_section);
        label_section.setBounds(new Rectangle(new Point(260, 20), label_section.getPreferredSize()));

        //======== scrollPane_chooseCategories ========
        {
            scrollPane_chooseCategories.setBackground(Color.white);

            //======== panel_categories ========
            {
                panel_categories.setBackground(Color.white);
                panel_categories.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder ( 0
                , 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
                , new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) ,
                panel_categories. getBorder () ) ); panel_categories. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
                ) { if( "\u0062order" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
                panel_categories.setLayout(new TableLayout(new double[][] {
                    {TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED},
                    {TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED}}));
                ((TableLayout)panel_categories.getLayout()).setHGap(10);

                //---- checkBox1 ----
                checkBox1.setText("asfdasfafa");
                checkBox1.setBackground(Color.white);
                panel_categories.add(checkBox1, new TableLayoutConstraints(0, 0, 0, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- label2 ----
                label2.setText("text");
                panel_categories.add(label2, new TableLayoutConstraints(1, 0, 1, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- label9 ----
                label9.setText("text");
                panel_categories.add(label9, new TableLayoutConstraints(2, 0, 2, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- checkBox2 ----
                checkBox2.setText("text");
                checkBox2.setBackground(Color.white);
                panel_categories.add(checkBox2, new TableLayoutConstraints(0, 1, 0, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- label4 ----
                label4.setText("text");
                panel_categories.add(label4, new TableLayoutConstraints(1, 1, 1, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- label10 ----
                label10.setText("text");
                panel_categories.add(label10, new TableLayoutConstraints(2, 1, 2, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- checkBox3 ----
                checkBox3.setText("text");
                checkBox3.setBackground(Color.white);
                panel_categories.add(checkBox3, new TableLayoutConstraints(0, 2, 0, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- label8 ----
                label8.setText("text");
                panel_categories.add(label8, new TableLayoutConstraints(1, 2, 1, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- label11 ----
                label11.setText("text");
                panel_categories.add(label11, new TableLayoutConstraints(2, 2, 2, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
            }
            scrollPane_chooseCategories.setViewportView(panel_categories);
        }
        contentPane.add(scrollPane_chooseCategories);
        scrollPane_chooseCategories.setBounds(40, 70, 465, 180);

        //---- checkBox_isAll ----
        checkBox_isAll.setText("all");
        checkBox_isAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                checkBox_isAllMouseReleased(e);
            }
        });
        contentPane.add(checkBox_isAll);
        checkBox_isAll.setBounds(new Rectangle(new Point(40, 40), checkBox_isAll.getPreferredSize()));

        //---- label5 ----
        label5.setText("Median:");
        contentPane.add(label5);
        label5.setBounds(new Rectangle(new Point(180, 280), label5.getPreferredSize()));

        //---- label6 ----
        label6.setText("Mean:");
        contentPane.add(label6);
        label6.setBounds(180, 255, 42, 15);

        //---- label7 ----
        label7.setText("Standard Deviation:");
        contentPane.add(label7);
        label7.setBounds(180, 300, 135, 25);

        //---- label_mean ----
        label_mean.setText("82%");
        contentPane.add(label_mean);
        label_mean.setBounds(new Rectangle(new Point(330, 255), label_mean.getPreferredSize()));

        //---- label_median ----
        label_median.setText("82%");
        contentPane.add(label_median);
        label_median.setBounds(330, 280, 18, 15);

        //---- label_stddev ----
        label_stddev.setText("12");
        contentPane.add(label_stddev);
        label_stddev.setBounds(330, 305, 18, 15);

        //---- comboBox_chooseStudent ----
        comboBox_chooseStudent.setModel(new DefaultComboBoxModel<>(new String[] {
            "Graduate Student",
            "Undergraduate Student"
        }));
        contentPane.add(comboBox_chooseStudent);
        comboBox_chooseStudent.setBounds(180, 330, 175, comboBox_chooseStudent.getPreferredSize().height);

        //---- button_back ----
        button_back.setText("Back");
        contentPane.add(button_back);
        button_back.setBounds(new Rectangle(new Point(230, 360), button_back.getPreferredSize()));
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(235, 385, 55, 20);
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(515, 45, 30, 265);

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
    private JLabel label_courseName;
    private JLabel label3;
    private JLabel label_section;
    private JScrollPane scrollPane_chooseCategories;
    private JPanel panel_categories;
    private JCheckBox checkBox1;
    private JLabel label2;
    private JLabel label9;
    private JCheckBox checkBox2;
    private JLabel label4;
    private JLabel label10;
    private JCheckBox checkBox3;
    private JLabel label8;
    private JLabel label11;
    private JCheckBox checkBox_isAll;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label_mean;
    private JLabel label_median;
    private JLabel label_stddev;
    private JComboBox<String> comboBox_chooseStudent;
    private JButton button_back;
    private JPanel vSpacer1;
    private JPanel hSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
