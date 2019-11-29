/*
 * Created by JFormDesigner on Thu Nov 28 15:41:42 EST 2019
 */

package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class saveAsTemplate extends JFrame {
    public saveAsTemplate() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        frame1 = new JFrame();
        label1 = new JLabel();
        textField1 = new JTextField();
        label_isValid = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        vSpacer1 = new JPanel(null);

        //======== frame1 ========
        {
            frame1.setTitle("Save as Template");
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(null);

            //---- label1 ----
            label1.setText("Template Name:");
            frame1ContentPane.add(label1);
            label1.setBounds(35, 25, 105, 30);
            frame1ContentPane.add(textField1);
            textField1.setBounds(125, 30, 160, textField1.getPreferredSize().height);

            //---- label_isValid ----
            label_isValid.setText("\u221a");
            frame1ContentPane.add(label_isValid);
            label_isValid.setBounds(290, 30, 35, 20);

            //---- button1 ----
            button1.setText("save");
            frame1ContentPane.add(button1);
            button1.setBounds(new Rectangle(new Point(80, 65), button1.getPreferredSize()));

            //---- button2 ----
            button2.setText("cancel");
            frame1ContentPane.add(button2);
            button2.setBounds(new Rectangle(new Point(205, 65), button2.getPreferredSize()));
            frame1ContentPane.add(vSpacer1);
            vSpacer1.setBounds(5, 95, 330, 15);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < frame1ContentPane.getComponentCount(); i++) {
                    Rectangle bounds = frame1ContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = frame1ContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                frame1ContentPane.setMinimumSize(preferredSize);
                frame1ContentPane.setPreferredSize(preferredSize);
            }
            frame1.pack();
            frame1.setLocationRelativeTo(null);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JFrame frame1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label_isValid;
    private JButton button1;
    private JButton button2;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
