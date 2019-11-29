/*
 * Created by JFormDesigner on Wed Nov 27 16:40:50 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class CourseList extends JFrame{
    public CourseList() {
        initComponents();
    }

    private void button_addMouseReleased(MouseEvent e) {
        this.setVisible(false);
        AddCourse addCourse = new AddCourse(this);
        addCourse.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label_title = new JLabel();
        scrollPane_courses = new JScrollPane();
        list_courseList = new JList();
        panel_buttons = new JPanel();
        button_open = new JButton();
        button_delete = new JButton();
        button_add = new JButton();
        button_history_current = new JButton();
        label_which_semester = new JLabel();
        vSpacer1 = new JPanel(null);

        //======== this ========
        setTitle("Course List");
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
            scrollPane_courses.setViewportView(list_courseList);
        }
        contentPane.add(scrollPane_courses);
        scrollPane_courses.setBounds(30, 50, 199, 128);

        //======== panel_buttons ========
        {
            panel_buttons.setBackground(new Color(238, 238, 238));
            panel_buttons.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
            swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border
            . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog"
            ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel_buttons. getBorder
            ( )) ); panel_buttons. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
            .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException
            ( ); }} );
            panel_buttons.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- button_open ----
            button_open.setText("open");
            button_open.setForeground(Color.black);
            button_open.setBackground(new Color(204, 204, 204));
            panel_buttons.add(button_open, "cell 0 0");

            //---- button_delete ----
            button_delete.setText("delete");
            button_delete.setBackground(new Color(204, 204, 204));
            button_delete.setForeground(Color.black);
            panel_buttons.add(button_delete, "cell 0 1");

            //---- button_add ----
            button_add.setText("add");
            button_add.setForeground(Color.black);
            button_add.setBackground(new Color(204, 204, 204));
            button_add.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button_addMouseReleased(e);
                }
            });
            panel_buttons.add(button_add, "cell 0 2");

            //---- button_history_current ----
            button_history_current.setText("history");
            button_history_current.setForeground(Color.black);
            button_history_current.setBackground(new Color(204, 204, 204));
            panel_buttons.add(button_history_current, "cell 0 3");
        }
        contentPane.add(panel_buttons);
        panel_buttons.setBounds(240, 50, 90, 128);

        //---- label_which_semester ----
        label_which_semester.setText("Fall 2019");
        label_which_semester.setForeground(Color.black);
        contentPane.add(label_which_semester);
        label_which_semester.setBounds(30, 185, 55, 16);
        contentPane.add(vSpacer1);
        vSpacer1.setBounds(0, 200, 340, 25);

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
    private JList list_courseList;
    private JPanel panel_buttons;
    private JButton button_open;
    private JButton button_delete;
    private JButton button_add;
    private JButton button_history_current;
    private JLabel label_which_semester;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
