/*
 * Created by JFormDesigner on Thu Nov 28 21:40:46 EST 2019
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import javax.swing.event.*;
import info.clearthought.layout.*;
import model.GradingRule;
import net.miginfocom.swing.*;
import service.GradingRuleService;

/**
 * @author unknown
 */
public class Statistics extends JFrame {
    public Statistics() {
        initComponents();
        loadCheckList();
    }

    private void loadCheckList(){
        // get a list of GradingRule whose depth is 0
        List<GradingRule> gradingRuleList = GradingRuleService.getAllCategories(0);
        DefaultListModel listModel = new DefaultListModel();
        for(GradingRule g : gradingRuleList){
            String item = g.getName() + " (" + g.getProportion() + "%)";
            listModel.addElement(new CheckListItem(item));
        }
//        test
        listModel.addElement(new CheckListItem("item1"));
        listModel.addElement(new CheckListItem("item2"));
        listModel.addElement(new CheckListItem("item3"));

        list_checkList.setCellRenderer(new CheckListRenderer());
        list_checkList.setModel(listModel);
    }

    private void checkBox_isAllMouseReleased(MouseEvent e) {
        if(checkBox_isAll.isSelected()){
            // todo select all checkboxes
            int count = list_checkList.getModel().getSize();
            while(count-->0){
                CheckListItem item = (CheckListItem) list_checkList.getModel().getElementAt(count);
                item.setSelected(true); // set true
                list_checkList.repaint();// Repaint cell
            }
        }
        else {
            // todo undo select all checkboxes
            int count = list_checkList.getModel().getSize();
            while(count-->0){
                CheckListItem item = (CheckListItem) list_checkList.getModel().getElementAt(count);
                item.setSelected(false); // set true
                list_checkList.repaint();// Repaint cell
            }
        }
    }

    private void list_checkListMouseReleased(MouseEvent e) {
        int index = list_checkList.locationToIndex(e.getPoint());// Get index of item
        // clicked
        CheckListItem item = (CheckListItem) list_checkList.getModel()
                .getElementAt(index);
        item.setSelected(!item.isSelected()); // Toggle selected state
        list_checkList.repaint(list_checkList.getCellBounds(index, index));// Repaint cell
    }

    private void list_checkListValueChanged(ListSelectionEvent e) {
        // TODO recalculate mean, median, and standard deviation, refresh labels
    }

    class CheckListItem {

        private String label;
        private boolean isSelected = false;

        public CheckListItem(String label) {
            this.label = label;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    class CheckListRenderer extends JCheckBox implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
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
        list_checkList = new JList();
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

            //---- list_checkList ----
            list_checkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list_checkList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    list_checkListMouseReleased(e);
                }
            });
            list_checkList.addListSelectionListener(e -> list_checkListValueChanged(e));
            scrollPane_chooseCategories.setViewportView(list_checkList);
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
        label_mean.setBounds(330, 255, 40, label_mean.getPreferredSize().height);

        //---- label_median ----
        label_median.setText("82%");
        contentPane.add(label_median);
        label_median.setBounds(330, 280, 40, 15);

        //---- label_stddev ----
        label_stddev.setText("12");
        contentPane.add(label_stddev);
        label_stddev.setBounds(330, 305, 45, 15);

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
    private JList list_checkList;
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
