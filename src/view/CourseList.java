/*
 * Created by JFormDesigner on Wed Nov 27 16:40:50 EST 2019
 */

package view;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class CourseList extends JPanel {
    public CourseList() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label_title = new JLabel();
        scrollPane1 = new JScrollPane();
        list_courseList = new JList();
        panel1 = new JPanel();
        button_open = new JButton();
        button_delete = new JButton();
        button_add = new JButton();
        button_history_current = new JButton();
        label_which_semester = new JLabel();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dialo\u0067"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("borde\u0072" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label_title ----
        label_title.setText("Courses");
        label_title.setFont(new Font("Impact", Font.PLAIN, 30));
        add(label_title, "cell 8 0 3 1");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(list_courseList);
        }
        add(scrollPane1, "cell 2 2 9 1");

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
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
            panel1.add(button_open, "cell 0 0");

            //---- button_delete ----
            button_delete.setText("delete");
            panel1.add(button_delete, "cell 0 1");

            //---- button_add ----
            button_add.setText("add");
            panel1.add(button_add, "cell 0 2");

            //---- button_history_current ----
            button_history_current.setText("history");
            panel1.add(button_history_current, "cell 0 3");
        }
        add(panel1, "cell 12 2");

        //---- label_which_semester ----
        label_which_semester.setText("Fall 2019");
        add(label_which_semester, "cell 2 4");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label_title;
    private JScrollPane scrollPane1;
    private JList list_courseList;
    private JPanel panel1;
    private JButton button_open;
    private JButton button_delete;
    private JButton button_add;
    private JButton button_history_current;
    private JLabel label_which_semester;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
