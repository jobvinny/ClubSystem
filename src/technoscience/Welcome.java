/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package technoscience;

import dbconnection.DBConnector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import sun.applet.Main;

public class Welcome {

    //getting images
    URL url4 = Main.class.getResource("/img/tech.jpg");
    URL url1 = Main.class.getResource("/img/connection.png");
    URL urllogo = Main.class.getResource("/img/logo.png");
    //setting images
    ImageIcon image4 = new ImageIcon(url4);

    //setting ma default image icon to my frames
    final ImageIcon icon2 = new ImageIcon(url1);
    Image iconimage = new ImageIcon(urllogo).getImage();

    //getting components;
    JProgressBar current;
    JLabel notify, notify2, notify3, notify4, splashimg, slogan, pwby;

    //setting panels
    JPanel panelbar = new JPanel(new GridBagLayout());

    //setting Frame
    JFrame Floadingbar;
    int num = 0;

    private void LoadingBar() {
        notify = new JLabel("Loading System Modules...");
        notify.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        notify.setForeground(Color.BLUE);
        notify2 = new JLabel("Establishing Database Connection...");
        notify2.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        notify2.setForeground(Color.BLUE);
        notify3 = new JLabel("Connection Established...");
        notify3.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        notify3.setForeground(Color.BLUE);
        notify4 = new JLabel("Connection Failure...");
        notify4.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        notify4.setForeground(Color.BLUE);
        slogan = new JLabel("Technologie Pour Change");
        slogan.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 10));
        slogan.setForeground(Color.BLACK);
        pwby = new JLabel("Powered By TechnoScience");
        pwby.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        pwby.setForeground(Color.BLACK);
        splashimg = new JLabel(image4);

        //progressbar
        current = new JProgressBar(0, 2000);
        current.setBorder(null);
        current.setBackground(Color.lightGray);
        current.setForeground(Color.black);
        current.setValue(0);
        current.setPreferredSize(new Dimension(500, 6));
        current.setStringPainted(false);
        //current.setForeground(Color.blue.darker());

        //adding components to panels pMain
        GridBagConstraints v = new GridBagConstraints();
        v.anchor = GridBagConstraints.CENTER;
        v.insets = new Insets(0, 0, 0, 0);
        v.ipadx = 0;
        v.ipady = 0;
        v.gridx = 0;
        v.gridy = 0;
        panelbar.add(splashimg, v);
//        v.insets = new Insets(10, 0, 0, 0);
//        v.gridy++;
//        panelbar.add(notify, v);
//        panelbar.add(notify2, v);
//        panelbar.add(notify3, v);
//        panelbar.add(notify4, v);
        v.insets = new Insets(0, 0, 0, 0);
        v.gridy++;
        v.anchor = GridBagConstraints.SOUTH;
        panelbar.add(current, v);
        v.anchor = GridBagConstraints.EAST;
        v.insets = new Insets(10, 0, 0, 10);
        v.gridy++;
        panelbar.add(slogan, v);
        v.insets = new Insets(0, 0, 2, 10);
        v.gridy++;
        panelbar.add(pwby, v);
        panelbar.setBackground(Color.lightGray);

        while (num < 2000) {
            current.setValue(num);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            num += 95;
            //System.out.println(num);
            if (num == 95) {
                notify.setVisible(true);
                notify2.setVisible(false);
                notify3.setVisible(false);
                notify4.setVisible(false);
                //frame code
                Floadingbar = new JFrame("Techno Science Club");
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.put("nimbusBase", Color.blue);
                } catch (Exception c) {
                }
                Floadingbar.setUndecorated(true);
                Floadingbar.setIconImage(iconimage);
                Floadingbar.add(panelbar);
                Floadingbar.setVisible(true);
                Floadingbar.setSize(500, 382);
                Floadingbar.setLocationRelativeTo(null);
                Floadingbar.revalidate();
                Floadingbar.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                Floadingbar.setBackground(Color.black);
                //end of frame code
            }
            if (num == 950) {
//                notify.setVisible(false);
//                notify2.setVisible(true);
//                notify3.setVisible(false);
//                notify4.setVisible(false);
            }
            if (num == 1425) {
                try {
                    Connection con = DBConnector.getConnection();
                } catch (SQLException e) {
//                    notify.setVisible(false);
//                    notify2.setVisible(false);
//                    notify3.setVisible(false);
//                    notify4.setVisible(true);
                    notify4.setForeground(Color.red);
                    current.setForeground(Color.red.darker());
                    JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + "Please Check If The Database Application Is Running", "Database Connection Notification", JOptionPane.ERROR_MESSAGE, icon2);
                    System.exit(0);
                }
            }
            if (num == 1615) {
//                notify.setVisible(false);
//                notify2.setVisible(false);
//                notify3.setVisible(true);
//                notify4.setVisible(false);
            }
            if (num == 2090) {
                try {
                    Connection con = DBConnector.getConnection();
                    if (con != null) {
                        Floadingbar.setVisible(false);
                        Login r2 = new Login();
                        r2.LoginSection();
                        con.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Connection Failure", "Error Message", JOptionPane.ERROR_MESSAGE, icon2);
                        System.exit(0);
                    }

                } catch (SQLException e) {
//                    notify.setVisible(false);
//                    notify2.setVisible(false);
//                    notify3.setVisible(false);
//                    notify4.setVisible(true);
                    notify4.setForeground(Color.red);
                    JOptionPane.showMessageDialog(null, "Connection Failure", "Error Message", JOptionPane.ERROR_MESSAGE, icon2);
                    System.exit(0);
                }
            }
        }

    }

    public static void main(String[] args) {
        Welcome wl = new Welcome();
        wl.LoadingBar();
    }
}
