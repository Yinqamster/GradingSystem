/*
 * Created by JFormDesigner on Thu Nov 28 21:40:46 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import controller.StatisticsController;
import model.Breakdown;
import model.Course;
import model.GradingRule;
import service.ScoreService;
import utils.Config;

/**
 * @author Jun Li
 */
public class Statistics extends JFrame {
    private Course course;
    public Statistics(Course course) {
        initComponents();
        this.course = course;
        this.label_courseName.setText(course.getName());
        this.label_section.setText(course.getSection());

        loadBreakdownTree();

    }
    // test
    public Statistics(){
        initComponents();

    }

    private void loadBreakdownTree(){
        // test
        Breakdown breakdown = this.course.getBreakdown();
        Map<String, GradingRule> gradingRules = breakdown.getGradingRules(); // GradingRuleID, GradingRule
        List<GradingRule> grs= new ArrayList<>(gradingRules.values());
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(course.getName() + " 100%");

        // test
//        List<GradingRule> grs= new ArrayList<>();
//        for(int i=0; i<5; i++){
//            List<GradingRule> gs= new ArrayList<>();
//            List<GradingRule> gs0= new ArrayList<>();
//            gs0.add(new GradingRule("Homework3", 100, 0.33));
//            gs.add(new GradingRule("Homework1",0.21, gs0));
//            GradingRule gradingRule = new GradingRule("Homework2",0.55,gs);
//            grs.add(gradingRule);
//        }
//        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode( "CS 591 P1 100%");
        // -----------------------------

        DefaultTreeModel treeModel = new DefaultTreeModel(StatisticsController.initBreakdownTree(rootNode, StatisticsController.getGradeRuleOfDepth0(grs)));
        this.tree_breakdown.setModel(treeModel);
    }

    private void button_backMouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void tree_breakdownMouseReleased(MouseEvent e) {
        // left click on breakdown tree, get GradingRuleID for selected GradingRule and calculate statistics
        if (e.getButton() == MouseEvent.BUTTON1){
            refreshStats();
        }
    }

    private void comboBox_chooseStudentItemStateChanged(ItemEvent e) {
        refreshStats();
    }

    private void refreshStats(){
        if (tree_breakdown.getSelectionCount() == 0) return;

        // get GradingRuleID for selected GradingRule
        String itemText = Objects.requireNonNull(tree_breakdown.getSelectionPath()).getLastPathComponent().toString();
        String parentItemText = tree_breakdown.getSelectionPath().getParentPath().getLastPathComponent().toString();
        String[] items = itemText.split(" - ");
        String[] parentItems = parentItemText.split(" - ");
        String name = items[0];
        String parentName = parentItems[0];

        Breakdown breakdown = this.course.getBreakdown();
        Map<String, GradingRule> gradingRules = breakdown.getGradingRules(); // GradingRuleID, GradingRule
        List<GradingRule> grs= new ArrayList<>(gradingRules.values());

        String courseID = this.course.getCourseID();
        String gradingRuleID = StatisticsController.findGradingRuleID(grs,name,parentName,courseID);

        // calculate stats
        String[] stats = new String[0]; //{mean, median, sd};
        if(Objects.requireNonNull(comboBox_chooseStudent.getSelectedItem()).toString().equals("All Students")){
            // calculate for all students
            stats = ScoreService.getInstance().calculateStats(courseID,gradingRuleID, "All");
        }else if(Objects.requireNonNull(comboBox_chooseStudent.getSelectedItem()).toString().equals("Graduate Student")){
            // calculate for Graduate Students
            stats = ScoreService.getInstance().calculateStats(courseID,gradingRuleID, Config.GRADUATE);
        }else if(Objects.requireNonNull(comboBox_chooseStudent.getSelectedItem()).toString().equals("Undergraduate Student")){
            // calculate for Undergraduate Students
            stats = ScoreService.getInstance().calculateStats(courseID,gradingRuleID, Config.UNDERGRADUATE);
        }

        // set labels
        label_mean.setText(stats[0]);
        label_median.setText(stats[1]);
        label_stddev.setText(stats[2]);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        label_courseName = new JLabel();
        label3 = new JLabel();
        label_section = new JLabel();
        scrollPane_chooseCategories = new JScrollPane();
        tree_breakdown = new JTree();
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
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Course:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(40, 20), label1.getPreferredSize()));

        //---- label_courseName ----
        label_courseName.setText("course name");
        contentPane.add(label_courseName);
        label_courseName.setBounds(new Rectangle(new Point(90, 20), label_courseName.getPreferredSize()));

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

            //---- tree_breakdown ----
            tree_breakdown.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    tree_breakdownMouseReleased(e);
                }
            });
            scrollPane_chooseCategories.setViewportView(tree_breakdown);
        }
        contentPane.add(scrollPane_chooseCategories);
        scrollPane_chooseCategories.setBounds(40, 45, 465, 180);

        //---- label5 ----
        label5.setText("Median:");
        contentPane.add(label5);
        label5.setBounds(180, 260, 105, label5.getPreferredSize().height);

        //---- label6 ----
        label6.setText("Mean:");
        contentPane.add(label6);
        label6.setBounds(180, 235, 115, 15);

        //---- label7 ----
        label7.setText("Standard Deviation:");
        contentPane.add(label7);
        label7.setBounds(180, 280, 135, 25);

        //---- label_mean ----
        label_mean.setText("82");
        contentPane.add(label_mean);
        label_mean.setBounds(new Rectangle(new Point(330, 235), label_mean.getPreferredSize()));

        //---- label_median ----
        label_median.setText("82");
        contentPane.add(label_median);
        label_median.setBounds(new Rectangle(new Point(330, 260), label_median.getPreferredSize()));

        //---- label_stddev ----
        label_stddev.setText("12");
        contentPane.add(label_stddev);
        label_stddev.setBounds(new Rectangle(new Point(330, 285), label_stddev.getPreferredSize()));

        //---- comboBox_chooseStudent ----
        comboBox_chooseStudent.setModel(new DefaultComboBoxModel<>(new String[] {
            "All Students",
            "Graduate Student",
            "Undergraduate Student"
        }));
        comboBox_chooseStudent.addItemListener(e -> comboBox_chooseStudentItemStateChanged(e));
        contentPane.add(comboBox_chooseStudent);
        comboBox_chooseStudent.setBounds(180, 310, 175, comboBox_chooseStudent.getPreferredSize().height);

        //---- button_back ----
        button_back.setText("Back");
        button_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_backMouseReleased(e);
            }
        });
        contentPane.add(button_back);
        button_back.setBounds(new Rectangle(new Point(230, 340), button_back.getPreferredSize()));
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(235, 360, 55, 20);
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
    private JTree tree_breakdown;
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
