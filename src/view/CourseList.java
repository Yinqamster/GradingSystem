/*
 * Created by JFormDesigner on Wed Nov 27 16:40:50 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;

import service.CourseService;

import model.Course;
import net.miginfocom.swing.*;
import utils.Config;

/**
 * @author Jun Li
 */
public class CourseList extends JFrame{
    private Vector<Course> coursesVector = new Vector<>();
    private String currentSemester = Config.CURRENT_SEMETER;

    public CourseList() {
        initComponents();

        // initialize semester label
        this.label_which_semester.setText(currentSemester);

        // add courses to list_courseList
        refreshList();
    }

    private void button_addMouseReleased(MouseEvent e) {
        AddCourse addCourse = new AddCourse(this);
        addCourse.setVisible(true);
        this.setEnabled(false);
    }

    private void button_openMouseReleased(MouseEvent e) {
        if(list_courseList.getSelectedIndex() == -1){
            // select nothing
            return;
        }else{
            // todo 1. get selected course's courseID 2. open a new MainFrame with this courseID 3. make this frame invisible
        }
        this.setVisible(false);
    }

    private void button_deleteMouseReleased(MouseEvent e) {
        if(list_courseList.getSelectedIndex() == -1){
            // select nothing
            return;
        }
        // alert window
        int n = JOptionPane.showConfirmDialog(null, "Delete this course?", "Warning",JOptionPane.YES_NO_OPTION);//n=0/1
        if(n == 0){
            // delete this course
            int selectedIndex = list_courseList.getSelectedIndex();
//            Course selectedCourse = courses.get(selectedIndex);
//            CourseService.deleteCourse(selectedCourse.getCourseID());
//            Course selectedCourse = this.coursesVector.get(selectedIndex);
//            CourseService.deleteCourse(selectedCourse.getCourseID());
            refreshList();
        }
    }

    // refresh list_courseList, show courses this semester
    private void refreshList() {
        this.coursesVector.clear();
        ArrayList<Course> courses_this_semester = new ArrayList<Course>(CourseService.getCourseListBySemester(currentSemester));
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (Course course : courses_this_semester) {
            this.coursesVector.add(course);
            String item = course.getName() + " Section: " + course.getSection();
            dlm.addElement(item);
        }

//        //test
//        coursesVector.add(new Course("CS591 P1", "Fall 2019",""));
//        coursesVector.add(new Course("CS591 P1", "Fall 2019",""));
//        coursesVector.add(new Course("CS591 P1", "Fall 2019",""));
//        for(Course course:coursesVector){
//            String item = course.getName() + " Section: " + course.getSemester();
//            dlm.addElement(item);
//        }

        this.list_courseList.setModel(dlm);
    }

    private void button_history_currentMouseReleased(MouseEvent e) {
        if (button_history_current.getText().equals("History")) {
            // todo: show all courses, change button text to "Current", reset courses Vector
            button_history_current.setText("Current");
            label_which_semester.setText("All Courses");
        } else {
            button_history_current.setText("History");
            label_which_semester.setText(currentSemester);
            refreshList();
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        System.exit(0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label_title = new JLabel();
        scrollPane_courses = new JScrollPane();
        list_courseList = new JList<>();
        label_which_semester = new JLabel();
        vSpacer1 = new JPanel(null);
        button_open = new JButton();
        button_delete = new JButton();
        button_add = new JButton();
        button_history_current = new JButton();

        //======== this ========
        setTitle("Course List");
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label_title ----
        label_title.setText("Courses");
        label_title.setFont(new Font("Impact", Font.PLAIN, 30));
        label_title.setForeground(Color.black);
        contentPane.add(label_title);
        label_title.setBounds(125, 5, 103, 39);

        //======== scrollPane_courses ========
        {

            //---- list_courseList ----
            list_courseList.setBackground(Color.white);
            list_courseList.setForeground(Color.black);
            list_courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list_courseList.setModel(new AbstractListModel<String>() {
                String[] values = {
                    "CS591 P1 Section A1",
                    "CS112 Section A1",
                    "CS112 Section A2"
                };
                @Override
                public int getSize() { return values.length; }
                @Override
                public String getElementAt(int i) { return values[i]; }
            });
            scrollPane_courses.setViewportView(list_courseList);
        }
        contentPane.add(scrollPane_courses);
        scrollPane_courses.setBounds(30, 50, 199, 128);

        //---- label_which_semester ----
        label_which_semester.setText("Fall 2019");
        label_which_semester.setForeground(Color.black);
        contentPane.add(label_which_semester);
        label_which_semester.setBounds(30, 185, 105, 16);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(5, 200, 340, 25);

        //---- button_open ----
        button_open.setText("Open");
        button_open.setForeground(Color.black);
        button_open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_openMouseReleased(e);
            }
        });
        contentPane.add(button_open);
        button_open.setBounds(250, 55, 85, 24);

        //---- button_delete ----
        button_delete.setText("Delete");
        button_delete.setForeground(Color.black);
        button_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_deleteMouseReleased(e);
            }
        });
        contentPane.add(button_delete);
        button_delete.setBounds(250, 85, 85, 24);

        //---- button_add ----
        button_add.setText("Add");
        button_add.setForeground(Color.black);
        button_add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_addMouseReleased(e);
            }
        });
        contentPane.add(button_add);
        button_add.setBounds(250, 115, 85, 24);

        //---- button_history_current ----
        button_history_current.setText("History");
        button_history_current.setForeground(Color.black);
        button_history_current.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button_history_currentMouseReleased(e);
            }
        });
        contentPane.add(button_history_current);
        button_history_current.setBounds(250, 145, 85, button_history_current.getPreferredSize().height);

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
    private JLabel label_title;
    private JScrollPane scrollPane_courses;
    private JList<String> list_courseList;
    private JLabel label_which_semester;
    private JPanel vSpacer1;
    private JButton button_open;
    private JButton button_delete;
    private JButton button_add;
    private JButton button_history_current;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
