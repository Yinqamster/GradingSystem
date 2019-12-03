package view;

public class ViewTest {
    public static void main(String[] arg){
//        CourseList courseList = new CourseList();
//        courseList.setVisible(true);
//        Login login = new Login();
//        login.setVisible(true);
//        Statistics statistics = new Statistics();
//        statistics.setVisible(true);
        MainFrame mainFrame = new MainFrame(new CourseList(),"");
        mainFrame.setVisible(true);
    }
}
