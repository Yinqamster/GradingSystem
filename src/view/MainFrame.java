/*
 * Created by JFormDesigner on Fri Nov 29 12:47:12 EST 2019
 */

package view;

import model.Course;
import service.CourseService;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.tree.*;

/**
 * @author Jun Li
 */
public class MainFrame extends JFrame {
    private CourseList parent;
    private String courseID;
    public MainFrame(CourseList courseList, String courseID) {
        initComponents();
        this.parent = courseList;
        this.courseID = courseID;

        // frozen table
//        scrollPane_table = new FrozenTablePane(table_grades, 2);

        // right click on table header, show popupMenu to choose score expression
        table_grades.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!e.isShiftDown())
                        table_grades.clearSelection();
                    // get point position
                    int pick = table_grades.getTableHeader().columnAtPoint(e.getPoint());
                    // set pick selected
                    table_grades.addColumnSelectionInterval(pick, pick);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // get point position
                    int pick = table_grades.getTableHeader().columnAtPoint(e.getPoint());
                    // if not first two columns, then show popupMenu
                    if (pick != 0 && pick != 1) {
                        popupMenu_ScoreExpression.show(table_grades.getTableHeader(), e.getX(), e.getY());
                    }
                }
            }
        });

        // load course name and section
        Course thisCourse = CourseService.getInstance().getCourse(courseID);
        this.label_courseName.setText(thisCourse.getName());
        this.label_section.setText(thisCourse.getSection());
    }

    private void button_showEditMouseReleased(MouseEvent e) {
        ShowEditCourse showEditCourse = new ShowEditCourse(courseID);
        showEditCourse.setVisible(true);
    }

    private void tree_breakdownMouseReleased(MouseEvent e) {
        // right button on breakdown tree
        if (e.getButton() == MouseEvent.BUTTON3) {
            //show in JTree
            if (tree_breakdown.getSelectionCount() == 0) return;
            int i = tree_breakdown.getClosestRowForLocation(e.getX(), e.getY());
            popupMenu_breakdownTree.show(tree_breakdown, e.getX(), e.getY());
        }
    }

    // add a student
    private void button_addStudentMouseReleased(MouseEvent e) {
        ShowEditStudent showEditStudent = new ShowEditStudent();
        showEditStudent.setVisible(true);
    }

    private void button_backMouseReleased(MouseEvent e) {
        parent.setVisible(true);
        this.dispose();
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
        tree_breakdown = new JTree();
        scrollPane_letterGrade = new JScrollPane();
        list_letterGradeRule = new JList<>();
        button_saveBreakdown = new JButton();
        button_saveAsTemplate = new JButton();
        label2 = new JLabel();
        label_whichCategory = new JLabel();
        label5 = new JLabel();
        textField_name = new JTextField();
        label6 = new JLabel();
        spinner_percentage = new JSpinner();
        label7 = new JLabel();
        textField_fullScore = new JTextField();
        label8 = new JLabel();
        label_letterGrade = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        button_saveLetterGradeRule = new JButton();
        spinner_lowerBound = new JSpinner();
        spinner_upperBound = new JSpinner();
        button_back = new JButton();
        hSpacer1 = new JPanel(null);
        vSpacer1 = new JPanel(null);
        popupMenu_breakdownTree = new JPopupMenu();
        menuItem_addChildren = new JMenuItem();
        menuItem_editThis = new JMenuItem();
        menuItem_deleteThis = new JMenuItem();
        popupMenu_student = new JPopupMenu();
        menuItem_showStudent = new JMenuItem();
        menuItem_edit = new JMenuItem();
        menuItem_addComment = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        popupMenu_ScoreExpression = new JPopupMenu();
        menuItem_percentage = new JMenuItem();
        menuItem_absScore = new JMenuItem();
        menuItem_lostScore = new JMenuItem();

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
                panel_GradesTab.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
                javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e",javax
                .swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java
                .awt.Font("D\u0069al\u006fg",java.awt.Font.BOLD,12),java.awt
                .Color.red),panel_GradesTab. getBorder()));panel_GradesTab. addPropertyChangeListener(new java.beans.
                PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062or\u0064er".
                equals(e.getPropertyName()))throw new RuntimeException();}});
                panel_GradesTab.setLayout(null);

                //======== scrollPane_table ========
                {

                    //---- table_grades ----
                    table_grades.setModel(new DefaultTableModel(
                        new Object[][] {
                            {"U73344054", "Jun Li", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {"U42186937", "Jiatong Hao", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {"U48621793", "Jiaqian Sun", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                            {"U31787103", "Qi Yin", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
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
                    table_grades.setBorder(new MatteBorder(1, 0, 0, 0, Color.black));
                    table_grades.setCellSelectionEnabled(true);
                    table_grades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table_grades.setToolTipText("Double click a cell to edit");
                    scrollPane_table.setViewportView(table_grades);
                }
                panel_GradesTab.add(scrollPane_table);
                scrollPane_table.setBounds(0, 0, 1065, 415);

                //---- button_addStudent ----
                button_addStudent.setText("Add Student");
                button_addStudent.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_addStudentMouseReleased(e);
                    }
                });
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

                    //---- tree_breakdown ----
                    tree_breakdown.setModel(new DefaultTreeModel(
                        new DefaultMutableTreeNode("CS591 P1 100%") {
                            {
                                DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Homework 50%");
                                    node1.add(new DefaultMutableTreeNode("TicTacToe 25%"));
                                    node1.add(new DefaultMutableTreeNode("MyFancyBank 25%"));
                                add(node1);
                                node1 = new DefaultMutableTreeNode("Exam 50%");
                                    DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Midterm 35%");
                                        node2.add(new DefaultMutableTreeNode("Written 20%"));
                                        node2.add(new DefaultMutableTreeNode("Practicum 15%"));
                                    node1.add(node2);
                                    node1.add(new DefaultMutableTreeNode("Final 15%"));
                                add(node1);
                            }
                        }));
                    tree_breakdown.setToolTipText("select a node and right click on it");
                    tree_breakdown.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            tree_breakdownMouseReleased(e);
                        }
                    });
                    scrollPane_breakdown.setViewportView(tree_breakdown);
                }
                panel1.add(scrollPane_breakdown);
                scrollPane_breakdown.setBounds(10, 10, 510, 340);

                //======== scrollPane_letterGrade ========
                {

                    //---- list_letterGradeRule ----
                    list_letterGradeRule.setModel(new AbstractListModel<String>() {
                        String[] values = {
                            "A   93% - 100%",
                            "A-  90% - 92%",
                            "B+  87% - 89%",
                            "B   83% - 86%",
                            "B-  80% - 82%",
                            "C+  77% - 79%",
                            "C   73% - 76%",
                            "C-  70% - 72%",
                            "D+  67% - 69%",
                            "D   63% - 66%",
                            "D-  60% - 62%",
                            "F   0% - 69%"
                        };
                        @Override
                        public int getSize() { return values.length; }
                        @Override
                        public String getElementAt(int i) { return values[i]; }
                    });
                    scrollPane_letterGrade.setViewportView(list_letterGradeRule);
                }
                panel1.add(scrollPane_letterGrade);
                scrollPane_letterGrade.setBounds(530, 10, 530, 340);

                //---- button_saveBreakdown ----
                button_saveBreakdown.setText("Save");
                panel1.add(button_saveBreakdown);
                button_saveBreakdown.setBounds(new Rectangle(new Point(390, 430), button_saveBreakdown.getPreferredSize()));

                //---- button_saveAsTemplate ----
                button_saveAsTemplate.setText("Save as Template");
                panel1.add(button_saveAsTemplate);
                button_saveAsTemplate.setBounds(new Rectangle(new Point(935, 440), button_saveAsTemplate.getPreferredSize()));

                //---- label2 ----
                label2.setText("Category:");
                panel1.add(label2);
                label2.setBounds(145, 360, 60, label2.getPreferredSize().height);

                //---- label_whichCategory ----
                label_whichCategory.setText("which category");
                panel1.add(label_whichCategory);
                label_whichCategory.setBounds(225, 360, 145, label_whichCategory.getPreferredSize().height);

                //---- label5 ----
                label5.setText("Name:");
                panel1.add(label5);
                label5.setBounds(145, 380, 60, label5.getPreferredSize().height);
                panel1.add(textField_name);
                textField_name.setBounds(225, 375, 155, textField_name.getPreferredSize().height);

                //---- label6 ----
                label6.setText("Percentage:");
                panel1.add(label6);
                label6.setBounds(145, 405, 80, label6.getPreferredSize().height);

                //---- spinner_percentage ----
                spinner_percentage.setModel(new SpinnerNumberModel(0, 0, 100, 1));
                panel1.add(spinner_percentage);
                spinner_percentage.setBounds(225, 400, 155, spinner_percentage.getPreferredSize().height);

                //---- label7 ----
                label7.setText("Full Score:");
                panel1.add(label7);
                label7.setBounds(145, 430, 70, label7.getPreferredSize().height);
                panel1.add(textField_fullScore);
                textField_fullScore.setBounds(225, 430, 155, textField_fullScore.getPreferredSize().height);

                //---- label8 ----
                label8.setText("Letter Grade:");
                panel1.add(label8);
                label8.setBounds(new Rectangle(new Point(535, 385), label8.getPreferredSize()));

                //---- label_letterGrade ----
                label_letterGrade.setText("which letter grade");
                panel1.add(label_letterGrade);
                label_letterGrade.setBounds(new Rectangle(new Point(620, 385), label_letterGrade.getPreferredSize()));

                //---- label9 ----
                label9.setText("Lower Bound:");
                panel1.add(label9);
                label9.setBounds(535, 410, label9.getPreferredSize().width, 15);

                //---- label10 ----
                label10.setText("Upper Bound:");
                panel1.add(label10);
                label10.setBounds(535, 435, 72, 15);

                //---- button_saveLetterGradeRule ----
                button_saveLetterGradeRule.setText("Save");
                panel1.add(button_saveLetterGradeRule);
                button_saveLetterGradeRule.setBounds(785, 430, 57, 23);
                panel1.add(spinner_lowerBound);
                spinner_lowerBound.setBounds(620, 405, 155, spinner_lowerBound.getPreferredSize().height);
                panel1.add(spinner_upperBound);
                spinner_upperBound.setBounds(620, 430, 155, 22);

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
        button_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_backMouseReleased(e);
            }
        });
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
        setLocationRelativeTo(null);

        //======== popupMenu_breakdownTree ========
        {

            //---- menuItem_addChildren ----
            menuItem_addChildren.setText("Add");
            popupMenu_breakdownTree.add(menuItem_addChildren);

            //---- menuItem_editThis ----
            menuItem_editThis.setText("Edit");
            popupMenu_breakdownTree.add(menuItem_editThis);

            //---- menuItem_deleteThis ----
            menuItem_deleteThis.setText("Delete");
            popupMenu_breakdownTree.add(menuItem_deleteThis);
        }

        //======== popupMenu_student ========
        {

            //---- menuItem_showStudent ----
            menuItem_showStudent.setText("Show");
            popupMenu_student.add(menuItem_showStudent);

            //---- menuItem_edit ----
            menuItem_edit.setText("Edit");
            popupMenu_student.add(menuItem_edit);

            //---- menuItem_addComment ----
            menuItem_addComment.setText("Add comment");
            popupMenu_student.add(menuItem_addComment);
            popupMenu_student.addSeparator();

            //---- menuItem2 ----
            menuItem2.setText("Freeze");
            popupMenu_student.add(menuItem2);

            //---- menuItem3 ----
            menuItem3.setText("Delete");
            popupMenu_student.add(menuItem3);
        }

        //======== popupMenu_ScoreExpression ========
        {

            //---- menuItem_percentage ----
            menuItem_percentage.setText("Percentage");
            popupMenu_ScoreExpression.add(menuItem_percentage);

            //---- menuItem_absScore ----
            menuItem_absScore.setText("Absolute Scores");
            popupMenu_ScoreExpression.add(menuItem_absScore);

            //---- menuItem_lostScore ----
            menuItem_lostScore.setText("Lost Scores");
            popupMenu_ScoreExpression.add(menuItem_lostScore);
        }
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
    private JTree tree_breakdown;
    private JScrollPane scrollPane_letterGrade;
    private JList<String> list_letterGradeRule;
    private JButton button_saveBreakdown;
    private JButton button_saveAsTemplate;
    private JLabel label2;
    private JLabel label_whichCategory;
    private JLabel label5;
    private JTextField textField_name;
    private JLabel label6;
    private JSpinner spinner_percentage;
    private JLabel label7;
    private JTextField textField_fullScore;
    private JLabel label8;
    private JLabel label_letterGrade;
    private JLabel label9;
    private JLabel label10;
    private JButton button_saveLetterGradeRule;
    private JSpinner spinner_lowerBound;
    private JSpinner spinner_upperBound;
    private JButton button_back;
    private JPanel hSpacer1;
    private JPanel vSpacer1;
    private JPopupMenu popupMenu_breakdownTree;
    private JMenuItem menuItem_addChildren;
    private JMenuItem menuItem_editThis;
    private JMenuItem menuItem_deleteThis;
    private JPopupMenu popupMenu_student;
    private JMenuItem menuItem_showStudent;
    private JMenuItem menuItem_edit;
    private JMenuItem menuItem_addComment;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JPopupMenu popupMenu_ScoreExpression;
    private JMenuItem menuItem_percentage;
    private JMenuItem menuItem_absScore;
    private JMenuItem menuItem_lostScore;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
