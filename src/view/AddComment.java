/*
 * Created by JFormDesigner on Wed Dec 11 21:59:09 EST 2019
 */

package view;

import controller.MainFrameController;
import model.Course;
import model.Grade;
import model.GradingRule;
import model.Student;
import service.ScoreService;
import sun.applet.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jun Li
 */
public class AddComment extends JFrame {
    private MainFrame mainFrame;
    private Course course;
    private GradingRule gr;
    private String BUID;
    public AddComment(MainFrame mainFrame, Course course, GradingRule gr, String BUID) {
        initComponents();
        this.mainFrame = mainFrame;
        this.course = course;
        this.gr = gr;
        this.BUID = BUID;

        this.label_ruleName.setText(gr.getName());
        this.label_studentName.setText(MainFrameController.getStudent(BUID, course.getCourseID()).getName().getFullName());

        Student stu = MainFrameController.getStudent(BUID,course.getCourseID());
        Grade g = stu.getGrades().get(gr.getId());
        if(g==null) {}
        else this.textArea_comment.setText(g.getComment());
    }

    private void button2MouseReleased(MouseEvent e) {
        this.dispose();
    }

    private void button1MouseReleased(MouseEvent e) {
        String comment = textArea_comment.getText();
        ScoreService.getInstance().updateGradeComment(course.getCourseID(),BUID,gr.getId(),comment);
        JOptionPane.showMessageDialog(this,"Comment updated");
        mainFrame.refreshAll();
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        label_ruleName = new JLabel();
        label2 = new JLabel();
        label_studentName = new JLabel();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea_comment = new JTextArea();
        button1 = new JButton();
        button2 = new JButton();
        hSpacer1 = new JPanel(null);
        vSpacer1 = new JPanel(null);

        //======== this ========
        setTitle("Add/Edit Comment");
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Rule:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(35, 15), label1.getPreferredSize()));

        //---- label_ruleName ----
        label_ruleName.setText("text");
        contentPane.add(label_ruleName);
        label_ruleName.setBounds(95, 15, 130, label_ruleName.getPreferredSize().height);

        //---- label2 ----
        label2.setText("Student:");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(35, 35), label2.getPreferredSize()));

        //---- label_studentName ----
        label_studentName.setText("text");
        contentPane.add(label_studentName);
        label_studentName.setBounds(95, 35, 150, label_studentName.getPreferredSize().height);

        //---- label3 ----
        label3.setText("Comment:");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(35, 55), label3.getPreferredSize()));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea_comment);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(35, 75, 205, 90);

        //---- button1 ----
        button1.setText("Save");
        button1.setIcon(new ImageIcon(getClass().getResource("/images/floppy-disk.png")));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button1MouseReleased(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(35, 180), button1.getPreferredSize()));

        //---- button2 ----
        button2.setText("Cancel");
        button2.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button2MouseReleased(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(150, 180), button2.getPreferredSize()));
        contentPane.add(hSpacer1);
        hSpacer1.setBounds(255, 60, 25, hSpacer1.getPreferredSize().height);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(125, 200, 20, 25);

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
    private JLabel label_ruleName;
    private JLabel label2;
    private JLabel label_studentName;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JTextArea textArea_comment;
    private JButton button1;
    private JButton button2;
    private JPanel hSpacer1;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
