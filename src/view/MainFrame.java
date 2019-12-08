/*
 * Created by JFormDesigner on Fri Nov 29 12:47:12 EST 2019
 */

package view;

import controller.MainFrameController;
import model.Course;
import model.GradingRule;
import service.CourseService;
import utils.Config;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.*;
import javax.swing.tree.*;

/**
 * @author Jun Li
 */
public class MainFrame extends JFrame {
    private CourseList parent;
    private Course course;
    public MainFrame(CourseList courseList, String courseID) {
        initComponents();
        this.parent = courseList;
        this.course = MainFrameController.getCourseByID(courseID);

        // frozen table
//        scrollPane_table = new FrozenTablePane(table_grades, 2);

        // right click on table header, show popupMenu to choose score expression
        table_grades.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // select the column
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

        // test
        // load course name and section
//        refreshCourseNameAndSection(this.course);

        loadBreakdownTree();
    }

    public void refreshTable(){
        // TODO set column header, load info row by row, set highLight for those grades who have comments, and make comment show when mouse anchored in such cell

        // disable first two columns
        DefaultTableModel dcbm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
                if(column < 2){
                    return false;
                }else{
                    return true;
                }

            }
        };

        List<GradingRule> gradingRuleList = MainFrameController.getAllGradingRule(course);
    }

    public void refreshCourseNameAndSection(Course course){
        this.label_courseName.setText(course.getName());
        this.label_section.setText(course.getSection());
    }

    private void button_showEditMouseReleased(MouseEvent e) {
        // test
        ShowEditCourse showEditCourse = new ShowEditCourse();
        //ShowEditCourse showEditCourse = new ShowEditCourse(courseID);
        showEditCourse.setVisible(true);
    }

    private void tree_breakdownMouseReleased(MouseEvent e) {
        // left click on breakdown tree, show information
        if (e.getButton() == MouseEvent.BUTTON1){
            if (tree_breakdown.getSelectionCount() == 0) return;
            String itemText = Objects.requireNonNull(tree_breakdown.getSelectionPath()).getLastPathComponent().toString();
            String[] items = itemText.split(" - ");
            String name = items[0];
            String proportion = items[1].replace("%", "");
            textField_name.setText(name);
            spinner_percentage.setValue(Integer.parseInt(proportion));

            if(items.length == 3) {// not leaf node
                panel_fullScore.setVisible(true);
                String fullScore = items[2];
                spinner_fullScore.setValue(Integer.parseInt(fullScore));
            }else {
                panel_fullScore.setVisible(false);
            }
        }
        // right button on breakdown tree
        if (e.getButton() == MouseEvent.BUTTON3) {
            //popupMenu show in JTree
            if (tree_breakdown.getSelectionCount() == 0) return;
            int i = tree_breakdown.getClosestRowForLocation(e.getX(), e.getY());
            popupMenu_breakdownTree.show(tree_breakdown, e.getX(), e.getY());
        }
    }

    // add a student
    private void button_addStudentMouseReleased(MouseEvent e) {
        ShowEditStudent showEditStudent = new ShowEditStudent(this.course, Config.ADDNEWSTUDENT, this);
        showEditStudent.setVisible(true);
    }

    private void button_backMouseReleased(MouseEvent e) {
        parent.setVisible(true);
        this.dispose();
    }

    private void button_statisticsMouseReleased(MouseEvent e) {
        Statistics statistics = new Statistics();
        statistics.setVisible(true);
    }

    private void loadBreakdownTree(){
        // test
//        Breakdown breakdown = this.course.getBreakdown();
//        Map<String, GradingRule> gradingRules = breakdown.getGradingRules(); // GradingRuleID, GradingRule
//        List<GradingRule> grs= new ArrayList<>(gradingRules.values());
//        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(course.getName() + " - 100%");

        // test
        List<GradingRule> grs= new ArrayList<>();
        for(int i=0; i<5; i++){
            List<GradingRule> gs= new ArrayList<>();
            List<GradingRule> gs0= new ArrayList<>();
            gs0.add(new GradingRule("Homework3", 100, 0.33));
            gs.add(new GradingRule("Homework1",0.21, gs0));
            GradingRule gradingRule = new GradingRule("Homework2",0.55,gs);
            grs.add(gradingRule);
        }
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode( "CS 591 P1 - 100%");

        DefaultTreeModel treeModel = new DefaultTreeModel(MainFrameController.initBreakdownTree(rootNode, MainFrameController.getGradeRuleOfDepth0(grs)));
        this.tree_breakdown.setModel(treeModel);
    }

    private void list_letterGradeRuleValueChanged(ListSelectionEvent e) {
        String item = list_letterGradeRule.getSelectedValue();
        String[] ss = item.split("  ");
        String letterName = ss[0];
        String Bound = ss[1].replace(" ","").replace("%","");
        String[] bounds = Bound.split("-");
        String lowerBound = bounds[0];
        String upperBound = bounds[1];

        // set textField;
        label_letterGrade.setText(letterName);
        spinner_lowerBound.setValue(Integer.parseInt(lowerBound));
        spinner_upperBound.setValue(Integer.parseInt(upperBound));
    }

    private void button_saveAsTemplateMouseReleased(MouseEvent e) {
        SaveAsTemplate saveAsTemplate = new SaveAsTemplate(course.getCourseID());
        saveAsTemplate.setVisible(true);
    }

    private void menuItem_showEditStudentMouseReleased(MouseEvent e) {
        ShowEditStudent showEditStudent = new ShowEditStudent(course,Config.EDITSTUDENT,this);
        showEditStudent.setVisible(true);
    }

    private void menuItem_addEditCommentMouseReleased(MouseEvent e) {
        // TODO get info for selected cell, and show ShowEditStudent
        if (e.getButton() == MouseEvent.BUTTON3) {
            // get point position
            int row = table_grades.getSelectedRow();
            int col = table_grades.getSelectedColumn();

            String GradingRuleName = table_grades.getColumnName(col);
            String BUID = table_grades.getValueAt(row,0).toString();
        }
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
        vSpacer1 = new JPanel(null);
        vSpacer2 = new JPanel(null);
        panel_whole = new JPanel();
        scrollPane_breakdown = new JScrollPane();
        tree_breakdown = new JTree();
        scrollPane_letterGrade = new JScrollPane();
        list_letterGradeRule = new JList<>();
        button_saveBreakdown = new JButton();
        button_saveAsTemplate = new JButton();
        label5 = new JLabel();
        textField_name = new JTextField();
        label6 = new JLabel();
        spinner_percentage = new JSpinner();
        label8 = new JLabel();
        label_letterGrade = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        button_saveLetterGradeRule = new JButton();
        spinner_lowerBound = new JSpinner();
        spinner_upperBound = new JSpinner();
        label4 = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        vSpacer4 = new JPanel(null);
        panel_fullScore = new JPanel();
        label7 = new JLabel();
        spinner_fullScore = new JSpinner();
        button_back = new JButton();
        hSpacer1 = new JPanel(null);
        popupMenu_breakdownTree = new JPopupMenu();
        menuItem_addChildren = new JMenuItem();
        menuItem_deleteThis = new JMenuItem();
        popupMenu_student = new JPopupMenu();
        menuItem_showEditStudent = new JMenuItem();
        menuItem_studentComment = new JMenuItem();
        menuItem_freezeStudent = new JMenuItem();
        menuItem_deleteStudent = new JMenuItem();
        popupMenu_ScoreExpression = new JPopupMenu();
        menuItem_percentage = new JMenuItem();
        menuItem_absScore = new JMenuItem();
        menuItem_lostScore = new JMenuItem();
        popupMenu_gradeComment = new JPopupMenu();
        menuItem_addEditComment = new JMenuItem();

        //======== this ========
        setTitle("Grading System");
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Course:");
        contentPane.add(label1);
        label1.setBounds(25, 10, 70, 15);

        //---- label_courseName ----
        label_courseName.setText("course name");
        contentPane.add(label_courseName);
        label_courseName.setBounds(95, 10, 100, 15);

        //---- label3 ----
        label3.setText("Section");
        contentPane.add(label3);
        label3.setBounds(200, 10, 65, 15);

        //---- label_section ----
        label_section.setText("which section");
        contentPane.add(label_section);
        label_section.setBounds(270, 10, 110, 15);

        //---- button_showEdit ----
        button_showEdit.setText("Show/Edit");
        button_showEdit.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
        button_showEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_showEditMouseReleased(e);
            }
        });
        contentPane.add(button_showEdit);
        button_showEdit.setBounds(new Rectangle(new Point(390, 5), button_showEdit.getPreferredSize()));

        //======== tabbedPane_gradingTable ========
        {

            //======== panel_GradesTab ========
            {
                panel_GradesTab.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
                javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax
                .swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java
                .awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt
                .Color.red),panel_GradesTab. getBorder()));panel_GradesTab. addPropertyChangeListener(new java.beans.
                PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".
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
                    table_grades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table_grades.setToolTipText("Double click a cell to edit");
                    table_grades.setColumnSelectionAllowed(true);
                    scrollPane_table.setViewportView(table_grades);
                }
                panel_GradesTab.add(scrollPane_table);
                scrollPane_table.setBounds(0, 0, 1065, 415);

                //---- button_addStudent ----
                button_addStudent.setText("Add Student");
                button_addStudent.setIcon(new ImageIcon(getClass().getResource("/images/plus.png")));
                button_addStudent.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_addStudentMouseReleased(e);
                    }
                });
                panel_GradesTab.add(button_addStudent);
                button_addStudent.setBounds(new Rectangle(new Point(260, 425), button_addStudent.getPreferredSize()));

                //---- button_saveGrades ----
                button_saveGrades.setText("Save");
                button_saveGrades.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
                panel_GradesTab.add(button_saveGrades);
                button_saveGrades.setBounds(new Rectangle(new Point(410, 425), button_saveGrades.getPreferredSize()));

                //---- button_calculate ----
                button_calculate.setText("Calculate Grade");
                button_calculate.setIcon(new ImageIcon(getClass().getResource("/images/calculator.png")));
                panel_GradesTab.add(button_calculate);
                button_calculate.setBounds(new Rectangle(new Point(520, 425), button_calculate.getPreferredSize()));

                //---- button_statistics ----
                button_statistics.setText("Statistics");
                button_statistics.setIcon(new ImageIcon(getClass().getResource("/images/bar-chart.png")));
                button_statistics.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_statisticsMouseReleased(e);
                    }
                });
                panel_GradesTab.add(button_statistics);
                button_statistics.setBounds(new Rectangle(new Point(690, 425), button_statistics.getPreferredSize()));
                panel_GradesTab.add(vSpacer1);
                vSpacer1.setBounds(new Rectangle(new Point(430, 460), vSpacer1.getPreferredSize()));
                panel_GradesTab.add(vSpacer2);
                vSpacer2.setBounds(815, 445, 45, 15);

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

            //======== panel_whole ========
            {
                panel_whole.setLayout(null);

                //======== scrollPane_breakdown ========
                {

                    //---- tree_breakdown ----
                    tree_breakdown.setModel(new DefaultTreeModel(
                        new DefaultMutableTreeNode("CS591 P1 100%") {
                            {
                                DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Homework 50%");
                                    node1.add(new DefaultMutableTreeNode("TicTacToe 25% 100"));
                                    node1.add(new DefaultMutableTreeNode("MyFancyBank 25% 100"));
                                add(node1);
                                node1 = new DefaultMutableTreeNode("Exam 50%");
                                    DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Midterm 35%");
                                        node2.add(new DefaultMutableTreeNode("Written 20% 50"));
                                        node2.add(new DefaultMutableTreeNode("Practicum 15% 133"));
                                    node1.add(node2);
                                    node1.add(new DefaultMutableTreeNode("Final 15% 100"));
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
                panel_whole.add(scrollPane_breakdown);
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
                    list_letterGradeRule.setToolTipText("Select a Row to Edit ");
                    list_letterGradeRule.addListSelectionListener(e -> list_letterGradeRuleValueChanged(e));
                    scrollPane_letterGrade.setViewportView(list_letterGradeRule);
                }
                panel_whole.add(scrollPane_letterGrade);
                scrollPane_letterGrade.setBounds(530, 10, 530, 340);

                //---- button_saveBreakdown ----
                button_saveBreakdown.setText("Save");
                button_saveBreakdown.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
                panel_whole.add(button_saveBreakdown);
                button_saveBreakdown.setBounds(new Rectangle(new Point(380, 405), button_saveBreakdown.getPreferredSize()));

                //---- button_saveAsTemplate ----
                button_saveAsTemplate.setText("Save as Template");
                button_saveAsTemplate.setIcon(new ImageIcon(getClass().getResource("/images/template.png")));
                button_saveAsTemplate.setToolTipText("Click to save breakdown as a template");
                button_saveAsTemplate.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_saveAsTemplateMouseReleased(e);
                    }
                });
                panel_whole.add(button_saveAsTemplate);
                button_saveAsTemplate.setBounds(new Rectangle(new Point(915, 405), button_saveAsTemplate.getPreferredSize()));

                //---- label5 ----
                label5.setText("Name:");
                panel_whole.add(label5);
                label5.setBounds(145, 360, 60, label5.getPreferredSize().height);
                panel_whole.add(textField_name);
                textField_name.setBounds(225, 360, 135, textField_name.getPreferredSize().height);

                //---- label6 ----
                label6.setText("Percentage:");
                panel_whole.add(label6);
                label6.setBounds(145, 385, 80, label6.getPreferredSize().height);

                //---- spinner_percentage ----
                spinner_percentage.setModel(new SpinnerNumberModel(0, 0, 100, 1));
                panel_whole.add(spinner_percentage);
                spinner_percentage.setBounds(225, 385, 135, spinner_percentage.getPreferredSize().height);

                //---- label8 ----
                label8.setText("Letter Grade:");
                panel_whole.add(label8);
                label8.setBounds(new Rectangle(new Point(530, 365), label8.getPreferredSize()));

                //---- label_letterGrade ----
                label_letterGrade.setText("Choose One Letter Grade Above");
                panel_whole.add(label_letterGrade);
                label_letterGrade.setBounds(615, 365, label_letterGrade.getPreferredSize().width, 16);

                //---- label9 ----
                label9.setText("Lower Bound:");
                panel_whole.add(label9);
                label9.setBounds(530, 390, 85, 15);

                //---- label10 ----
                label10.setText("Upper Bound:");
                panel_whole.add(label10);
                label10.setBounds(530, 415, 85, 15);

                //---- button_saveLetterGradeRule ----
                button_saveLetterGradeRule.setText("Save");
                button_saveLetterGradeRule.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
                panel_whole.add(button_saveLetterGradeRule);
                button_saveLetterGradeRule.setBounds(new Rectangle(new Point(745, 405), button_saveLetterGradeRule.getPreferredSize()));

                //---- spinner_lowerBound ----
                spinner_lowerBound.setModel(new SpinnerNumberModel(0, 0, 100, 1));
                panel_whole.add(spinner_lowerBound);
                spinner_lowerBound.setBounds(615, 385, 110, spinner_lowerBound.getPreferredSize().height);

                //---- spinner_upperBound ----
                spinner_upperBound.setModel(new SpinnerNumberModel(0, 0, 100, 1));
                panel_whole.add(spinner_upperBound);
                spinner_upperBound.setBounds(615, 410, 110, 22);

                //---- label4 ----
                label4.setText("%");
                panel_whole.add(label4);
                label4.setBounds(new Rectangle(new Point(365, 390), label4.getPreferredSize()));

                //---- label11 ----
                label11.setText("%");
                panel_whole.add(label11);
                label11.setBounds(730, 390, 25, 15);

                //---- label12 ----
                label12.setText("%");
                panel_whole.add(label12);
                label12.setBounds(730, 415, 25, 15);
                panel_whole.add(vSpacer4);
                vSpacer4.setBounds(500, 440, 10, 20);

                //======== panel_fullScore ========
                {
                    panel_fullScore.setLayout(null);

                    //---- label7 ----
                    label7.setText("Full Score:");
                    panel_fullScore.add(label7);
                    label7.setBounds(5, 5, 70, label7.getPreferredSize().height);

                    //---- spinner_fullScore ----
                    spinner_fullScore.setModel(new SpinnerNumberModel(0, 0, null, 1));
                    panel_fullScore.add(spinner_fullScore);
                    spinner_fullScore.setBounds(85, 5, 135, spinner_fullScore.getPreferredSize().height);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < panel_fullScore.getComponentCount(); i++) {
                            Rectangle bounds = panel_fullScore.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = panel_fullScore.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        panel_fullScore.setMinimumSize(preferredSize);
                        panel_fullScore.setPreferredSize(preferredSize);
                    }
                }
                panel_whole.add(panel_fullScore);
                panel_fullScore.setBounds(140, 410, 220, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel_whole.getComponentCount(); i++) {
                        Rectangle bounds = panel_whole.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel_whole.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel_whole.setMinimumSize(preferredSize);
                    panel_whole.setPreferredSize(preferredSize);
                }
            }
            tabbedPane_gradingTable.addTab("Breakdown", panel_whole);
        }
        contentPane.add(tabbedPane_gradingTable);
        tabbedPane_gradingTable.setBounds(20, 35, 1075, 490);

        //---- button_back ----
        button_back.setText("Back");
        button_back.setIcon(new ImageIcon(getClass().getResource("/images/left-arrow.png")));
        button_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_backMouseReleased(e);
            }
        });
        contentPane.add(button_back);
        button_back.setBounds(new Rectangle(new Point(1010, 5), button_back.getPreferredSize()));
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(1090, 5, 20, 30);

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
            menuItem_addChildren.setIcon(new ImageIcon(getClass().getResource("/images/plus.png")));
            popupMenu_breakdownTree.add(menuItem_addChildren);

            //---- menuItem_deleteThis ----
            menuItem_deleteThis.setText("Delete");
            menuItem_deleteThis.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
            popupMenu_breakdownTree.add(menuItem_deleteThis);
        }

        //======== popupMenu_student ========
        {

            //---- menuItem_showEditStudent ----
            menuItem_showEditStudent.setText("Show/Edit");
            menuItem_showEditStudent.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
            menuItem_showEditStudent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    menuItem_showEditStudentMouseReleased(e);
                }
            });
            popupMenu_student.add(menuItem_showEditStudent);

            //---- menuItem_studentComment ----
            menuItem_studentComment.setText("Student Comment");
            menuItem_studentComment.setIcon(new ImageIcon(getClass().getResource("/images/comment.png")));
            popupMenu_student.add(menuItem_studentComment);

            //---- menuItem_freezeStudent ----
            menuItem_freezeStudent.setText("Freeze");
            menuItem_freezeStudent.setIcon(new ImageIcon(getClass().getResource("/images/freeze.png")));
            popupMenu_student.add(menuItem_freezeStudent);

            //---- menuItem_deleteStudent ----
            menuItem_deleteStudent.setText("Delete");
            menuItem_deleteStudent.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
            popupMenu_student.add(menuItem_deleteStudent);
        }

        //======== popupMenu_ScoreExpression ========
        {

            //---- menuItem_percentage ----
            menuItem_percentage.setText("Percentage");
            menuItem_percentage.setIcon(new ImageIcon(getClass().getResource("/images/percentage.png")));
            popupMenu_ScoreExpression.add(menuItem_percentage);

            //---- menuItem_absScore ----
            menuItem_absScore.setText("Absolute Scores");
            menuItem_absScore.setIcon(new ImageIcon(getClass().getResource("/images/score.png")));
            popupMenu_ScoreExpression.add(menuItem_absScore);

            //---- menuItem_lostScore ----
            menuItem_lostScore.setText("Lost Scores");
            menuItem_lostScore.setIcon(new ImageIcon(getClass().getResource("/images/lost-score.png")));
            popupMenu_ScoreExpression.add(menuItem_lostScore);
        }

        //======== popupMenu_gradeComment ========
        {

            //---- menuItem_addEditComment ----
            menuItem_addEditComment.setText("Add/Edit Comment");
            menuItem_addEditComment.setSelectedIcon(null);
            menuItem_addEditComment.setIcon(new ImageIcon(getClass().getResource("/images/comment.png")));
            menuItem_addEditComment.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    menuItem_addEditCommentMouseReleased(e);
                }
            });
            popupMenu_gradeComment.add(menuItem_addEditComment);
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
    private JPanel vSpacer1;
    private JPanel vSpacer2;
    private JPanel panel_whole;
    private JScrollPane scrollPane_breakdown;
    private JTree tree_breakdown;
    private JScrollPane scrollPane_letterGrade;
    private JList<String> list_letterGradeRule;
    private JButton button_saveBreakdown;
    private JButton button_saveAsTemplate;
    private JLabel label5;
    private JTextField textField_name;
    private JLabel label6;
    private JSpinner spinner_percentage;
    private JLabel label8;
    private JLabel label_letterGrade;
    private JLabel label9;
    private JLabel label10;
    private JButton button_saveLetterGradeRule;
    private JSpinner spinner_lowerBound;
    private JSpinner spinner_upperBound;
    private JLabel label4;
    private JLabel label11;
    private JLabel label12;
    private JPanel vSpacer4;
    private JPanel panel_fullScore;
    private JLabel label7;
    private JSpinner spinner_fullScore;
    private JButton button_back;
    private JPanel hSpacer1;
    private JPopupMenu popupMenu_breakdownTree;
    private JMenuItem menuItem_addChildren;
    private JMenuItem menuItem_deleteThis;
    private JPopupMenu popupMenu_student;
    private JMenuItem menuItem_showEditStudent;
    private JMenuItem menuItem_studentComment;
    private JMenuItem menuItem_freezeStudent;
    private JMenuItem menuItem_deleteStudent;
    private JPopupMenu popupMenu_ScoreExpression;
    private JMenuItem menuItem_percentage;
    private JMenuItem menuItem_absScore;
    private JMenuItem menuItem_lostScore;
    private JPopupMenu popupMenu_gradeComment;
    private JMenuItem menuItem_addEditComment;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
