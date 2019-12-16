package controller;

import model.GradingRule;
import service.GradingRuleService;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class StatisticsController {
    public static DefaultMutableTreeNode initBreakdownTree(DefaultMutableTreeNode rootNode, List<GradingRule> gradingRules){
        for(int i=0; i<gradingRules.size(); i++){
            String name = gradingRules.get(i).getName();
            String proportion = (int)(gradingRules.get(i).getProportion() * 100) + "%";
            String nodeText = "";
            if(gradingRules.get(i).getFullScore() == 0) {
                // not leaf node
                nodeText  = name + " - " + proportion;
            }
            else{
                // leaf node
                String fullScore = String.valueOf((int)gradingRules.get(i).getFullScore());
                nodeText = name + " - " + proportion + " - " + fullScore;
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
            if(gr.getParentID() == null || gr.getParentID().isEmpty()) grs.add(gr);
        }
        return grs;
    }

    public static String findGradingRuleID(List<GradingRule> grs,  String name, String parentName, String courseID){
        for(GradingRule gradingRule: grs){
            if(gradingRule.getName().equals(name) && GradingRuleService.getInstance().getGradingRuleByID(courseID, gradingRule.getParentID()).getName().equals(parentName)){
                return gradingRule.getId();
            }
        }
        return "";
    }
}
