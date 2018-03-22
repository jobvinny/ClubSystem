package dbconnector;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBConnector extends Component {

    String path = null;
    URL url5 = Main.class.getResource("/img/logo.png");
    URL url13 = Main.class.getResource("/img/dbconnection.png");
    URL url1 = Main.class.getResource("/img/connection.png");

    // final ImageIcon icon2 = new ImageIcon(url1);
    Image iconimage = new ImageIcon(url5).getImage();
    final ImageIcon iconcon = new ImageIcon(url13);
    final ImageIcon icon2 = new ImageIcon(url1);

    //connection variables
    private static final String USERNAME = "TechnoScience";
    private static final String PASSWORD = "TechnoScience";
    private static final String CONN = "jdbc:mysql://127.0.0.1:3306/technoscience";
    private static final String SQCONN = "jdbc:sqlite:dbpharmacy.sqlite";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
        //return DriverManager.getConnection(SQCONN);
    }
    //start xampp
    public void startXampp() {
        try {
            //Process runtimeprocess = Runtime.getRuntime().exec("cmd.exe " + "/c" + " start /B " + "C:/xampp/xampp_start.exe");
            //Process runtimeprocess = Runtime.getRuntime().exec("cmd.exe " + "/c" + " start /B " + urlxamppstart);
            Process runtimeprocess = Runtime.getRuntime().exec("C:/xampp/xampp_start.exe");
            int processcomplete = runtimeprocess.waitFor();
            if (processcomplete == 0) {
                //do nothing
            } else {
                stopXampp();
            }
        } catch (Exception bb) {
            JOptionPane.showMessageDialog(null, "Sorry Service Can't Be Started", "Database Connection", JOptionPane.INFORMATION_MESSAGE, icon2);
        }
    }

    //start xampp
    public void stopXampp() {
        try {
            //Process runtimeprocess = Runtime.getRuntime().exec("cmd.exe " + "/c" + " start /B " + "C:/xampp/xampp_stop.exe");
            //Process runtimeprocess = Runtime.getRuntime().exec("cmd.exe " + "/c" + " start /B " + urlxamppstop);
            Process runtimeprocess = Runtime.getRuntime().exec("C:/xampp/xampp_stop.exe");
            int processcomplete = runtimeprocess.waitFor();
            if (processcomplete == 0) {
                //System.out.println(processcomplete);
            } else {
                //System.out.println(processcomplete);
            }
        } catch (Exception bb) {
            JOptionPane.showMessageDialog(null, "Sorry Service Can't Be Started", "Database Connection", JOptionPane.INFORMATION_MESSAGE, icon2);
        }
    }

    //restart xampp
    public void restartXampp() {
        try {
            // Process runtimeprocess = Runtime.getRuntime().exec("cmd.exe " + "/c" + " start /B " + urlxamppstart);
            Process runtimeprocess = Runtime.getRuntime().exec("C:/xampp/xampp_start.exe");
            int processcomplete = runtimeprocess.waitFor();
            if (processcomplete == 0) {
                Connection connection = getConnection();
                if (connection != null) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Database Connection Established", "Connection Establishment", JOptionPane.INFORMATION_MESSAGE, iconcon);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Connection Failed\nPlease Exit the System and Start\nThe Database Application Manually", "Notification", JOptionPane.ERROR_MESSAGE, icon2);
                    stopXampp();
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed", "Database Connection", JOptionPane.INFORMATION_MESSAGE, icon2);
            }
        } catch (Exception bb) {
            restartXampp();
            //JOptionPane.showMessageDialog(null, "Sorry Service Can't Be Started", "Database Connection", JOptionPane.INFORMATION_MESSAGE, icon2);
        }
    }

    //code for restarting connection icase of failure during operation
    public void getCon() {
        String[] option = {"Retry", "Ok"};
        Toolkit.getDefaultToolkit().beep();
        int dbstate = JOptionPane.showOptionDialog(null, "Database Connection Failure\nRetry Starting The Connection\n\nAnd Try Again", "Notification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        if (dbstate == 0) {
            restartXampp();
        }
        if (dbstate == 1) {
            JOptionPane.showMessageDialog(null, "Please Establish Database Connection", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }
//        try {
//            Runtime rt = Runtime.getRuntime();
//            rt.exec("cmd.exe " + "/c" + " start " + urlxampp);
//            System.exit(0);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
}
