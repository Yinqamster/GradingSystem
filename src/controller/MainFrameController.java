package controller;

import model.Breakdown;
import model.Course;
import model.GradingRule;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFrameController {
    public static DefaultMutableTreeNode initBreakdownTree(DefaultMutableTreeNode rootNode, List<GradingRule> gradingRules){
        for(int i=0; i<gradingRules.size(); i++){
            String name = gradingRules.get(i).getName();
            String proportion = (int)(gradingRules.get(i).getProportion() * 100) + "%";
            String nodeText = "";
            if(gradingRules.get(i).getFullScore() == 0) {
                // not leaf node
               nodeText  = name + " " + proportion;
            }
            else{
                // leaf node
                String fullScore = String.valueOf((int)gradingRules.get(i).getFullScore());
                nodeText = name + " " + proportion + " " + fullScore;
            }
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeText);
            rootNode.add(child);

            // add this child's child
            if(gradingRules.get(i).getChildren() != null) {
                if (gradingRules.get(i).getChildren().size() != 0) {
                    initBreakdownTree(child, gradingRules.get(i).getChildren());
                }
            }
        }
        return rootNode;
    }

    public static List<GradingRule> getGradeRuleOfDepth0(List<GradingRule> gradingRules){
        List<GradingRule> grs = new ArrayList<>();
        for(GradingRule gr : gradingRules){
            if(gr.getParentID() == null) grs.add(gr);
        }
        return grs;
    }
}
