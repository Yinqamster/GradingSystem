/*
 * Created by JFormDesigner on Fri Nov 29 12:47:12 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.tree.*;

/**
 * @author unknown
 */
public class MainFrame extends JFrame {
    private CourseList parent;
    private String courseID;
    public MainFrame(CourseList courseList, String courseID) {
        initComponents();
        parent = courseList;
        this.courseID = courseID;
    }

    private void button_showEditMouseReleased(MouseEvent e) {
        ShowEditCourse showEditCourse = new ShowEditCourse(courseID);
        showEditCourse.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        label_courseName = new JLabel();
        label3 = new JLabel();
        label_section = new JLabel();
        button_showEdit = new JButton();
        tabbedPane_gradingTable = new JTabbedPane();
        panel_GradesTab = new JPanel();
        scrollPane_table = new JScrollPane();
        table_grades = new JTable();
        button_addStudent = new JButton();
        button_saveGrades = new JButton();
        button_calculate = new JButton();
        button_statistics = new JButton();
        panel1 = new JPanel();
        scrollPane_breakdown = new JScrollPane();
        tree1 = new JTree();
        scrollPane_letterGrade = new JScrollPane();
        button_saveBreakdown = new JButton();
        button_saveAsTemplate = new JButton();
        button_back = new JButton();
        hSpacer1 = new JPanel(null);
        vSpacer1 = new JPanel(null);

        //======== this ========
        setTitle("Grading System");
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Course:");
        contentPane.add(label1);
        label1.setBounds(20, 10, 42, 15);

        //---- label_courseName ----
        label_courseName.setText("course name");
        contentPane.add(label_courseName);
        label_courseName.setBounds(65, 10, 66, 15);

        //---- label3 ----
        label3.setText("Section");
        contentPane.add(label3);
        label3.setBounds(195, 10, 42, 15);

        //---- label_section ----
        label_section.setText("which section");
        contentPane.add(label_section);
        label_section.setBounds(245, 10, 78, 15);

        //---- button_showEdit ----
        button_showEdit.setText("Show/Edit");
        button_showEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_showEditMouseReleased(e);
            }
        });
        contentPane.add(button_showEdit);
        button_showEdit.setBounds(new Rectangle(new Point(340, 5), button_showEdit.getPreferredSize()));

        //======== tabbedPane_gradingTable ========
        {

            //======== panel_GradesTab ========
            {
                panel_GradesTab.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing
                . border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder
                . CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .
                awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel_GradesTab. getBorder( )) )
                ; panel_GradesTab. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
                ) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} )
                ;
                panel_GradesTab.setLayout(null);

                //======== scrollPane_table ========
                {

                    //---- table_grades ----
                    table_grades.setModel(new DefaultTableModel(
                        new Object[][] {
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {"hello", "asda", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {"dfs", "asda", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                        },
                        new String[] {
                            "BUID", "Name", null, null, null, null, null, null, null, null, null, null, null, null, null, null
                        }
                    ));
                    table_grades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    table_grades.setBorder(LineBorder.createBlackLineBorder());
                    scrollPane_table.setViewportView(table_grades);
                }
                panel_GradesTab.add(scrollPane_table);
                scrollPane_table.setBounds(0, 0, 1065, 415);

                //---- button_addStudent ----
                button_addStudent.setText("Add Student");
                panel_GradesTab.add(button_addStudent);
                button_addStudent.setBounds(new Rectangle(new Point(340, 430), button_addStudent.getPreferredSize()));

                //---- button_saveGrades ----
                button_saveGrades.setText("Save");
                panel_GradesTab.add(button_saveGrades);
                button_saveGrades.setBounds(new Rectangle(new Point(460, 430), button_saveGrades.getPreferredSize()));

                //---- button_calculate ----
                button_calculate.setText("Calculate Grade");
                panel_GradesTab.add(button_calculate);
                button_calculate.setBounds(new Rectangle(new Point(530, 430), button_calculate.getPreferredSize()));

                //---- button_statistics ----
                button_statistics.setText("Statistics");
                panel_GradesTab.add(button_statistics);
                button_statistics.setBounds(new Rectangle(new Point(665, 430), button_statistics.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_GradesTab.getComponentCount(); i++) {
                        Rectangle bounds = panel_GradesTab.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_GradesTab.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_GradesTab.setMinimumSize(preferredSize);
                    panel_GradesTab.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_gradingTable.addTab("Grades", panel_GradesTab);

            //======== panel1 ========
            {
                panel1.setLayout(null);

                //======== scrollPane_breakdown ========
                {

                    //---- tree1 ----
                    tree1.setModel(new DefaultTreeModel(
                        new DefaultMutableTreeNode("CS591 P1") {
                            {
                                DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Homework");
                                    node1.add(new DefaultMutableTreeNode("TicTacToe"));
                                    node1.add(new DefaultMutableTreeNode("MyFancyBank"));
                                add(node1);
                                node1 = new DefaultMutableTreeNode("Exam");
                                    DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Midterm");
                                        node2.add(new DefaultMutableTreeNode("Written"));
                                        node2.add(new DefaultMutableTreeNode("Practicum"));
                                    node1.add(node2);
                                    node1.add(new DefaultMutableTreeNode("Final"));
                                add(node1);
                            }
                        }));
                    scrollPane_breakdown.setViewportView(tree1);
                }
                panel1.add(scrollPane_breakdown);
                scrollPane_breakdown.setBounds(10, 10, 510, 415);
                panel1.add(scrollPane_letterGrade);
                scrollPane_letterGrade.setBounds(530, 10, 530, 415);

                //---- button_saveBreakdown ----
                button_saveBreakdown.setText("Save");
                panel1.add(button_saveBreakdown);
                button_saveBreakdown.setBounds(new Rectangle(new Point(860, 440), button_saveBreakdown.getPreferredSize()));

                //---- button_saveAsTemplate ----
                button_saveAsTemplate.setText("Save as Template");
                panel1.add(button_saveAsTemplate);
                button_saveAsTemplate.setBounds(new Rectangle(new Point(930, 440), button_saveAsTemplate.getPreferredSize()));

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
            tabbedPane_gradingTable.addTab("Breakdown", panel1);
        }
        contentPane.add(tabbedPane_gradingTable);
        tabbedPane_gradingTable.setBounds(20, 35, 1075, 500);

        //---- button_back ----
        button_back.setText("Back");
        contentPane.add(button_back);
        button_back.setBounds(new Rectangle(new Point(1035, 10), button_back.getPreferredSize()));
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(1090, 5, 20, 30);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(new Rectangle(new Point(435, 540), vSpacer1.getPreferredSize()));

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
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label1;
    private JLabel label_courseName;
    private JLabel label3;
    private JLabel label_section;
    private JButton button_showEdit;
    private JTabbedPane tabbedPane_gradingTable;
    private JPanel panel_GradesTab;
    private JScrollPane scrollPane_table;
    private JTable table_grades;
    private JButton button_addStudent;
    private JButton button_saveGrades;
    private JButton button_calculate;
    private JButton button_statistics;
    private JPanel panel1;
    private JScrollPane scrollPane_breakdown;
    private JTree tree1;
    private JScrollPane scrollPane_letterGrade;
    private JButton button_saveBreakdown;
    private JButton button_saveAsTemplate;
    private JButton button_back;
    private JPanel hSpacer1;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
