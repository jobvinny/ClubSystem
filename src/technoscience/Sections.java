package technoscience;

import com.toedter.calendar.JDateChooser;
import dbconnection.DBConnector;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class Sections {

    //dimension setting
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //getting images

    URL url1 = Main.class.getResource("/img/logo.png");
    URL url2 = Main.class.getResource("/img/selllogo2.png");
    URL url3 = Main.class.getResource("/img/search.png");
    URL url4 = Main.class.getResource("/img/sellbutton.png");
    URL url5 = Main.class.getResource("/img/delete.png");
    URL url6 = Main.class.getResource("/img/update2.png");
    URL url7 = Main.class.getResource("/img/cancel5.png");
    //setting images
    ImageIcon imageselllogo = new ImageIcon(url2);
    ImageIcon imagesearch = new ImageIcon(url3);
    ImageIcon imagesellbtn = new ImageIcon(url4);
    ImageIcon imageupdate = new ImageIcon(url6);
    ImageIcon imagecancel = new ImageIcon(url7);
    ImageIcon imagedelete = new ImageIcon(url5);

    //setting ma default image icon to my frames
    Image iconimage = new ImageIcon(url1).getImage();
    final ImageIcon icon = new ImageIcon(url5);
    final ImageIcon icon2 = new ImageIcon(url6);

    //images for joptionpanes
    Image imagetechguy = new ImageIcon(url1).getImage();

    //setting components
    JLabel lsname, lrnumber, lcost, lquantity, ltcost, lselllogo, lsellpoint, lpaid, lchange, lprovider, dcost, upcost, linvoice;
    JTextField tsname, trnumber, tcost, tquantity, ttcost, tsearch, tpaid, tchange, tprovider, tdcost, tupcost, tlinvoice;
    JButton bsearch;
    public JButton bsell, bdelete, bupdate;
    public JButton bselluser;
    JButton bcancel;
    public JButton bback, bedit;
    public JButton bbacksell;
    public JButton brefresh;
    public JButton brefreshadmin;
    JComboBox<String> boxdrugname;
    JComboBox<String> ltype;
    JTextArea treport;
    JTable table;

    //panel
    JPanel panelmain = new JPanel(new BorderLayout(0, 0));
    JPanel paneltable = new JPanel(new GridBagLayout());
    JPanel sellpanel = new JPanel(new BorderLayout(0, 0));
    JPanel panelprint = new JPanel(new GridBagLayout());
    JPanel panelproduct = new JPanel(new GridBagLayout());
    JPanel panelsell = new JPanel(new GridBagLayout());
    JPanel panelcost = new JPanel(new GridBagLayout());

    //frame
    JFrame Fsell = new JFrame();
    JFrame Fprint = new JFrame();

    //database connectors
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement prs = null;

    //variables
    String[] values = new String[]{"Student Name", "Registration Number", "Service Type", "Cost [KSH]", "Quantity", "Total Cost[KSH]", "Service Provider", "Cash Paid", "Change Given", "Provided On"};
    String cost, quantity, drugname, namefound, type2;
    double cost2, quaty, total, costfound, change, dcash;
    StringBuilder sb;

    //date
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
    private static final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("EEE,dd MMM yyyy,hh:mm a");
    LocalDateTime now = LocalDateTime.now();
    String fileeditedlast = dtf.format(now);
    String fileeditedlast2 = dtf2.format(now);
    java.util.Date today = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    String format = formatter.format(today);
    double costage;

    //unique serial number
    Random randserial = new Random();
    int numserial = randserial.nextInt();

    //method for cancelling
    private void canacelaction() {
        tsname.setText("");
        trnumber.setText("");
        tcost.setText("");
        tquantity.setText("");
        ttcost.setText("");
        tprovider.setText("");
        tpaid.setText("");
        tchange.setText("");

        analyzingdata();
        uplaod();
    }

    //method for getting data names from store
    public void DrugNameFound() {

        try {
            Connection con = DBConnector.getConnection();
            String sqldrugname = "SELECT * FROM servicetable";
            prs = con.prepareStatement(sqldrugname);
            rs = prs.executeQuery();
            Vector vnames = new Vector();
            vnames.add("Choose Student Reg_NO To View Service Type");
            while (rs.next()) {
                namefound = rs.getString("rnumber");
                vnames.add(namefound);
            }
            //String[] optionsnames = {""};
            boxdrugname = new JComboBox<String>(vnames);
            rs.close();
            prs.close();
            con.close();
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + x, "Error Message", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        } finally {

            Connection con = null;
            try {
                con = DBConnector.getConnection();
                rs.close();
                prs.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //method for searching2
    private void searchengine2() {
        DefaultTableModel pharmacymodel = new DefaultTableModel();
        //dm.addColumn("Medicine Name");
        pharmacymodel.setColumnIdentifiers(values);
        String fetchrecord = "SELECT * FROM servicetable WHERE  rnumber  = '" + drugname + "'";
        try {
            Connection con = DBConnector.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(fetchrecord);
            if (rs.next()) {
                do {
                    String name = rs.getString("sname");
                    String reg = rs.getString("rnumber");
                    String service = rs.getString("stype");
                    double cost = rs.getDouble("cost");
                    String scost = String.format("%.2f", cost);
                    double q = rs.getDouble("Tquantity");
                    String tq = String.format("%.2f", q);
                    double tc = rs.getDouble("Tcost");
                    String ttc = String.format("%.2f", tc);
                    String provider = rs.getString("sprovider");
                    double paid = rs.getDouble("CashPaid");
                    String cpaid = String.format("%.2f", paid);
                    double change = rs.getDouble("ChangePaid");
                    String schange = String.format("%.2f", change);
                    String providedon = rs.getString("LastEdited");

                    pharmacymodel.addRow(new String[]{name, reg, service, scost, tq, ttc, provider, cpaid, schange, providedon});

                } while (rs.next());
                table.setModel(pharmacymodel);
                table.setAutoCreateRowSorter(true);
                table.setFillsViewportHeight(true);
                table.revalidate();

                rs.close();
                stmt.close();
                con.close();
            } else {
                table.setModel(pharmacymodel);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "No Details Found For " + " " + drugname, "Store Message", JOptionPane.ERROR_MESSAGE);
                rs.close();
                stmt.close();
                con.close();
            }
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + x, "Database Message", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

    //method for uploadin data in system
    private void uplaod() {
        DefaultTableModel pharmacymodel = new DefaultTableModel();
        //dm.addColumn("Medicine Name");
        pharmacymodel.setColumnIdentifiers(values);
        String fetchrecord = "SELECT * FROM servicetable";
        try {
            Connection con = DBConnector.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(fetchrecord);
            if (rs.next()) {
                do {
                    String name = rs.getString("sname");
                    String reg = rs.getString("rnumber");
                    String service = rs.getString("stype");
                    double cost = rs.getDouble("cost");
                    String scost = String.format("%.2f", cost);
                    double q = rs.getDouble("Tquantity");
                    String tq = String.format("%.2f", q);
                    double tc = rs.getDouble("Tcost");
                    String ttc = String.format("%.2f", tc);
                    String provider = rs.getString("sprovider");
                    double paid = rs.getDouble("CashPaid");
                    String cpaid = String.format("%.2f", paid);
                    double change = rs.getDouble("ChangePaid");
                    String schange = String.format("%.2f", change);
                    String providedon = rs.getString("LastEdited");

                    pharmacymodel.addRow(new String[]{name, reg, service, scost, tq, ttc, provider, cpaid, schange, providedon});

                } while (rs.next());
                table.setModel(pharmacymodel);
                table.setAutoCreateRowSorter(true);
                table.setFillsViewportHeight(true);
                table.revalidate();

                rs.close();
                stmt.close();
                con.close();
            } else {
                table.setModel(pharmacymodel);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "No Service", "Store Message", JOptionPane.ERROR_MESSAGE);
                rs.close();
                stmt.close();
                con.close();
            }
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + x, "Database Message", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

    //method for searching
    private void searchengine() {
        String valuesearch = tsearch.getText();
        String serialgot = String.valueOf(tsearch.getText());
        if (valuesearch.equalsIgnoreCase("") || valuesearch.equalsIgnoreCase(null)) {
            JOptionPane.showMessageDialog(null, "Please Enter The Student Name/Reg_No/Techno Service Number For Search", "Search Message", JOptionPane.INFORMATION_MESSAGE);
        } else {
            DefaultTableModel pharmacymodel = new DefaultTableModel();
            //dm.addColumn("Medicine Name");
            pharmacymodel.setColumnIdentifiers(values);
            String fetchrecord = "SELECT * FROM servicetable WHERE  rnumber  = '" + tsearch.getText() + "' || invoice  = '" + tsearch.getText() + "' || sname  = '" + tsearch.getText() + "'";
            try {
                Connection con = DBConnector.getConnection();
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(fetchrecord);
                if (rs.next()) {
                    do {
                        String name = rs.getString("sname");
                        String reg = rs.getString("rnumber");
                        String service = rs.getString("stype");
                        double cost = rs.getDouble("cost");
                        String scost = String.format("%.2f", cost);
                        double q = rs.getDouble("Tquantity");
                        String tq = String.format("%.2f", q);
                        double tc = rs.getDouble("Tcost");
                        String ttc = String.format("%.2f", tc);
                        String provider = rs.getString("sprovider");
                        double paid = rs.getDouble("CashPaid");
                        String cpaid = String.format("%.2f", paid);
                        double change = rs.getDouble("ChangePaid");
                        String schange = String.format("%.2f", change);
                        String providedon = rs.getString("LastEdited");

                        pharmacymodel.addRow(new String[]{name, reg, service, scost, tq, ttc, provider, cpaid, schange, providedon});

                    } while (rs.next());
                    table.setModel(pharmacymodel);
                    table.setAutoCreateRowSorter(true);
                    table.setFillsViewportHeight(true);
                    table.revalidate();

                    rs.close();
                    stmt.close();
                    con.close();
                } else {
                    table.setModel(pharmacymodel);
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "No Details Found For " + " " + tsearch.getText(), "Store Message", JOptionPane.ERROR_MESSAGE);
                    rs.close();
                    stmt.close();
                    con.close();
                }
            } catch (SQLException x) {
                JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + x, "Database Message", JOptionPane.ERROR_MESSAGE);
                //System.exit(0);
            }
        }
    }

    //method for providing service
    private void provideservice() {
        if (type2.equalsIgnoreCase("") || type2.equalsIgnoreCase("Choose Techno Club Service")) {
            JOptionPane.showMessageDialog(null, "Please Select The Techno Service Type", "Alert", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (tsname.getText().equalsIgnoreCase("") && trnumber.getText().equalsIgnoreCase("")
                    && tpaid.getText().equalsIgnoreCase("") && tprovider.getText().equalsIgnoreCase("")
                    && tchange.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Please Fill In All The Spaces", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String finalcost = ttcost.getText();
                String getpayed = tpaid.getText();
                double dpayed = Double.parseDouble(getpayed);
                //double costfinal = Double.parseDouble(finalcost);
                if (dpayed < total) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Please Enter Extra Cash", "Cash Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Image newimg = imagetechguy.getScaledInstance(200, 230, Image.SCALE_SMOOTH);
                    ImageIcon newIcon = new ImageIcon(newimg);
                    Toolkit.getDefaultToolkit().beep();
                    String[] option = {"Yes", "No"};
                    int selloption = JOptionPane.showOptionDialog(null, "Proceed With the Service", "Techno Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, newIcon, option, option[1]);
                    if (selloption == 0) {
                        try {
                            con = DBConnector.getConnection();
                            String sqladd = "INSERT INTO servicetable(sname,rnumber,stype,cost,Tquantity,Tcost,sprovider,CashPaid,ChangePaid,LastEdited,Invoice) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                            prs = con.prepareStatement(sqladd);

                            //setting to database
                            prs.setString(1, tsname.getText());
                            prs.setString(2, trnumber.getText());
                            prs.setString(3, type2);
                            prs.setDouble(4, cost2);
                            prs.setDouble(5, quaty);
                            prs.setDouble(6, total);
                            prs.setString(7, tprovider.getText());
                            prs.setDouble(8, Double.parseDouble(tpaid.getText()));
                            prs.setDouble(9, Double.parseDouble(tchange.getText()));
                            prs.setString(10, fileeditedlast);
                            prs.setString(11, String.valueOf(numserial));

                            prs.execute();

                            rs.close();
                            prs.close();
                            con.close();

                            JOptionPane.showMessageDialog(null, "<---TECHNO SCIENCE CLUB--->" + "\n" + "\b" + "The following is the techno service number for" + "\n" + "\t"
                                    + tsname.getText() + "\n" + "\t" + trnumber.getText() + "\n" + "\b" + "Techno Expert " + " " + tprovider.getText() + "\n" + "\b" + "Techno Service Number is [" + " " + numserial + " " + "]", "Techno Science Club Verification", JOptionPane.INFORMATION_MESSAGE, newIcon);

                            analyzingdata();
                            uplaod();
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + e, "Database Message", JOptionPane.ERROR_MESSAGE);
                            //System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Service Cancelled Successfully", "Techno Confirmation", JOptionPane.INFORMATION_MESSAGE, newIcon);
                        canacelaction();
                    }
                }
            }
        }
    }

    //analyze data
    private void analyzingdata() {
        try {
            con = DBConnector.getConnection();
            String stockcostadmin = "SELECT SUM(Tcost) TCOST FROM  servicetable";
            prs = con.prepareStatement(stockcostadmin);
            rs = prs.executeQuery();
            rs.next();
            double costfound = rs.getDouble("TCOST");
            tdcost.setText(String.format("%.2f", costfound));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + e, "Database Message", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

    public void repairmode() {
        table = new JTable();
        // this enables horizontal scroll bar
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        //table.setAutoCreateRowSorter(true);
        table.setIntercellSpacing(new Dimension(20, 20));
        // table.setPreferredScrollableViewportSize(new Dimension(935, 570));
        table.setPreferredScrollableViewportSize(new Dimension((int) (screenSize.width / 1.25), (int) (screenSize.height / 2.70)));
        table.revalidate();
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.revalidate();

        lselllogo = new JLabel(imageselllogo);

        lsellpoint = new JLabel("SERVICE DESK");
        lsellpoint.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lsname = new JLabel("Client Name");
        lsname.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lrnumber = new JLabel("Registration Number");
        lrnumber.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lcost = new JLabel("Cost [KSH]");
        lcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lquantity = new JLabel("Quantity");
        lquantity.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        ltcost = new JLabel("Total Cost");
        ltcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lpaid = new JLabel("Cash Paid");
        lpaid.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lchange = new JLabel("Change");
        lchange.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        lprovider = new JLabel("Techno Expert");
        lprovider.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        dcost = new JLabel("Total Cost [KSH]");
        dcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        linvoice = new JLabel("Enter Invoice");
        linvoice.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        upcost = new JLabel("Enter Cost");
        upcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));

        tsname = new JTextField(20);
        tsname.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tlinvoice = new JTextField(20);
        tlinvoice.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tupcost = new JTextField(20);
        tupcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        trnumber = new JTextField(20);
        trnumber.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tprovider = new JTextField(20);
        tprovider.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tcost = new JTextField(20);
        tcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tquantity = new JTextField(20);
        tquantity.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        ttcost = new JTextField(20);
        ttcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        ttcost.setEditable(false);
        ttcost.setBackground(Color.LIGHT_GRAY);
        tpaid = new JTextField(20);
        tpaid.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tchange = new JTextField(20);
        tchange.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tchange.setEditable(false);
        tchange.setBackground(Color.LIGHT_GRAY);
        tdcost = new JTextField(20);
        tdcost.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tdcost.setEditable(false);
        tdcost.setBackground(Color.LIGHT_GRAY);
        tsearch = new JTextField(21);
        tsearch.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        tsearch.setToolTipText("Search");

        //combobox for names
        boxdrugname.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boxdrugname.setToolTipText("Search Product By Name");
        //method
        boxdrugname.addActionListener(event -> {
            JComboBox<String> boxdrugname = (JComboBox<String>) event.getSource();
            drugname = (String) boxdrugname.getSelectedItem();
            searchengine2();
        });
        //end of combobox

        String[] options = {"Choose Techno Club Service", "Taking Setups", "Hardware Check", "Network Card", "OS Installation", "Drivers", "Windows Activation", "Antivirus", "Blowing[Open/Full]", "Games Installation", "Mobile Service", "Network Configuration", "Blowing[Closed/Half]", "Windows Activation", "Application Sofware"};
        ltype = new JComboBox<String>(options);
        ltype.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ltype.addActionListener(event -> {
            JComboBox<String> ltype = (JComboBox<String>) event.getSource();
            type2 = (String) ltype.getSelectedItem();
            if (type2.equalsIgnoreCase("OS Installation")) {
                tcost.setText("200");
            } else if (type2.equalsIgnoreCase("Blowing[Closed/Half]")) {
                tcost.setText("50");
            } else if (type2.equalsIgnoreCase("Blowing[Open/Full]")) {
                tcost.setText("200");
            } else if (type2.equalsIgnoreCase("Games Installation")) {
                tcost.setText("150");
            } else if (type2.equalsIgnoreCase("Mobile Service")) {
                tcost.setText("200");
            } else if (type2.equalsIgnoreCase("Network Configuration")) {
                tcost.setText("50");
            } else if (type2.equalsIgnoreCase("Antivirus")) {
                tcost.setText("150");
            } else if (type2.equalsIgnoreCase("Windows Activation")) {
                tcost.setText("100");
            } else if (type2.equalsIgnoreCase("Application Sofware")) {
                tcost.setText("100");
            } else if (type2.equalsIgnoreCase("Drivers")) {
                tcost.setText("100");
            } else if (type2.equalsIgnoreCase("Windows Activation")) {
                tcost.setText("100");
            } else if (type2.equalsIgnoreCase("Hardware Check")) {
                tcost.setText("200");
            } else if (type2.equalsIgnoreCase("Network Card")) {
                tcost.setText("150");
            } else if (type2.equalsIgnoreCase("Taking Setups")) {
                tcost.setText("200");
            }
        });
        bedit = new JButton("VIEW/EDIT");
        bedit.setBackground(Color.LIGHT_GRAY);
        bedit.setToolTipText("Edit Data");
        bedit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bedit.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        bsearch = new JButton(imagesearch);
        bsearch.setBackground(Color.LIGHT_GRAY);
        bsearch.setToolTipText("Search Client");
        bsearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bsell = new JButton(imagesellbtn);
        bsell.setBackground(Color.GREEN);
        bsell.setToolTipText("Sell Service");
        bsell.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bselluser = new JButton(imagesellbtn);
        bselluser.setBackground(Color.GREEN);
        bselluser.setToolTipText("Sell Service");
        bselluser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bcancel = new JButton(imagecancel);
        bcancel.setBackground(Color.red);
        bcancel.setToolTipText("Cancel/Reload");
        bcancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bback = new JButton("BACK");
        bback.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
        bback.setBackground(Color.LIGHT_GRAY);
        bback.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bbacksell = new JButton("LOGOUT");
        bbacksell.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        bbacksell.setBackground(Color.LIGHT_GRAY);
        bbacksell.setCursor(new Cursor(Cursor.HAND_CURSOR));
        brefresh = new JButton("REFRESH");
        brefresh.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        brefresh.setBackground(Color.GREEN);
        brefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        brefreshadmin = new JButton("REFRESH");
        brefreshadmin.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        brefreshadmin.setBackground(Color.GREEN);
        brefreshadmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bdelete = new JButton(imagedelete);
        bdelete.setBackground(Color.RED.brighter());
        bdelete.setToolTipText("Remove");
        bdelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bupdate = new JButton(imageupdate);
        bupdate.setToolTipText("Update");
        bupdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bupdate.setBackground(Color.green.brighter());

        //adding components to paneltable
        GridBagConstraints v = new GridBagConstraints();
        v.anchor = GridBagConstraints.WEST;
        v.insets = new Insets(0, 0, 0, 0);
        v.ipadx = 0;
        v.ipady = 0;
        v.gridx = 0;
        v.gridy = 0;
        paneltable.add(bbacksell, v);
        v.anchor = GridBagConstraints.EAST;
        v.insets = new Insets(0, 0, 0, 320);
        paneltable.add(boxdrugname, v);
        v.insets = new Insets(0, 110, 0, 53);
        paneltable.add(tsearch, v);
        v.insets = new Insets(0, 0, 0, 0);
        paneltable.add(bsearch, v);
        v.anchor = GridBagConstraints.CENTER;
        v.insets = new Insets(0, 0, 0, 0);
        v.gridy++;
        paneltable.add(scrollPane, v);
        paneltable.setBorder(new TitledBorder("Techno Science Club Clinic Details"));
        paneltable.revalidate();

        //sellpanel
        v.anchor = GridBagConstraints.CENTER;
        v.insets = new Insets(0, 0, 0, 0);
        v.ipadx = 0;
        v.ipady = 0;
        v.gridx = 0;
        v.gridy = 0;
        panelproduct.add(lsname, v);
        panelproduct.add(upcost, v);
        v.gridy++;
        panelproduct.add(tsname, v);
        panelproduct.add(tupcost, v);
        v.gridy++;
        panelproduct.add(lrnumber, v);
        v.gridy++;
        panelproduct.add(trnumber, v);
        panelproduct.add(linvoice, v);
        v.gridy++;
        panelproduct.add(lprovider, v);
        panelproduct.add(tlinvoice, v);
        v.insets = new Insets(0, 0, 15, 0);
        v.gridy++;
        panelproduct.add(tprovider, v);
        v.insets = new Insets(10, 0, 0, 200);
        panelproduct.add(bupdate, v);
        v.insets = new Insets(10, 200, 0, 0);
        panelproduct.add(bdelete, v);
        v.insets = new Insets(15, 0, 0, 0);
        v.gridy++;
        panelproduct.add(dcost, v);
        v.insets = new Insets(0, 0, 0, 0);
        v.gridy++;
        panelproduct.add(bedit, v);
        v.gridy++;
        panelproduct.add(tdcost, v);
        v.gridy++;
        panelproduct.add(bback, v);
        panelproduct.setBorder(new TitledBorder("Student Details Details"));

        v.anchor = GridBagConstraints.CENTER;
        v.insets = new Insets(0, 0, 0, 0);
        v.ipadx = 0;
        v.ipady = 0;
        v.gridx = 3;
        v.gridy = 0;
        panelcost.add(ltype, v);
        v.gridy++;
        panelcost.add(lcost, v);
        v.gridy++;
        panelcost.add(tcost, v);
        v.gridy++;
        panelcost.add(lquantity, v);
        v.gridy++;
        panelcost.add(tquantity, v);
        v.gridy++;
        panelcost.add(ltcost, v);
        v.gridy++;
        panelcost.add(ttcost, v);
        v.gridy++;
        panelcost.add(lpaid, v);
        v.gridy++;
        panelcost.add(tpaid, v);
        v.gridy++;
        panelcost.add(lchange, v);
        v.gridy++;
        panelcost.add(tchange, v);
        panelcost.setBorder(new TitledBorder("Service Details"));

        v.anchor = GridBagConstraints.CENTER;
        v.insets = new Insets(0, 0, 50, 0);
        v.ipadx = 0;
        v.ipady = 0;
        v.gridx = 5;
        v.gridy = 0;
        panelsell.add(lselllogo, v);
        v.insets = new Insets(0, 0, 10, 0);
        v.gridy++;
        panelsell.add(lsellpoint, v);
        v.insets = new Insets(0, 0, 0, 150);
        v.anchor = GridBagConstraints.WEST;
        v.gridy++;
        panelsell.add(bsell, v);
        panelsell.add(bselluser, v);
        v.anchor = GridBagConstraints.EAST;
        v.insets = new Insets(0, 150, 0, 0);
        panelsell.add(bcancel, v);
        panelsell.setBorder(new TitledBorder("Check Service"));

        //panel sellpanel
        sellpanel.add("West", panelproduct);
        sellpanel.add("Center", panelcost);
        sellpanel.add("East", panelsell);
        sellpanel.setBorder(new TitledBorder(""));

        panelmain.add("Center", paneltable);
        panelmain.add("South", sellpanel);
        panelmain.setBorder(new TitledBorder(""));
        panelmain.setBackground(Color.blue.brighter());
        panelmain.revalidate();

        //actions
        tquantity.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                cost = tcost.getText();
                quantity = tquantity.getText();
                if (cost.equalsIgnoreCase("") && quantity.equalsIgnoreCase("")) {
                    //JOptionPane.showMessageDialog(null, "No Cost or Quantity given", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    cost2 = Double.parseDouble(cost);
                    quaty = Double.parseDouble(quantity);
                    total = quaty * cost2;
                    //String costresult = Double.toString(total);
                    ttcost.setText(String.format("%.2f", total));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cost = tcost.getText();
                quantity = tquantity.getText();
                if (cost.equalsIgnoreCase("") && quantity.equalsIgnoreCase("")) {
                    //JOptionPane.showMessageDialog(null, "No Cost or Quantity given", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    cost2 = Double.parseDouble(cost);
                    quaty = Double.parseDouble(quantity);
                    total = quaty * cost2;
                    //String costresult = Double.toString(total);
                    ttcost.setText(String.format("%.2f", total));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        tpaid.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String cashpaid = tpaid.getText();
                dcash = Double.parseDouble(cashpaid);
                String tamount = ttcost.getText();
                double damount = Double.parseDouble(tamount);
                change = dcash - damount;

                tchange.setText(String.format("%.2f", change));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String cashpaid = tpaid.getText();
                dcash = Double.parseDouble(cashpaid);
                String tamount = ttcost.getText();
                double damount = Double.parseDouble(tamount);
                change = dcash - damount;

                tchange.setText(String.format("%.2f", change));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        //action for table to select a specific column
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //getting values from table
                double storeoutcost = Double.parseDouble(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));

                //setting values got from table
                tupcost.setText(String.format("%.2f", storeoutcost));

            }

            @Override
            public void mousePressed(MouseEvent e) {

                //getting values from table
                double storeoutcost = Double.parseDouble(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));

                //setting values got from table
                tupcost.setText(String.format("%.2f", storeoutcost));
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        dcost.setVisible(false);
        tdcost.setVisible(false);
        linvoice.setVisible(false);
        tlinvoice.setVisible(false);
        bdelete.setVisible(false);
        upcost.setVisible(false);
        tupcost.setVisible(false);
        bupdate.setVisible(false);
        bback.setVisible(false);
        bedit.setVisible(false);
        //action event end
        analyzingdata();
        uplaod();
        //setting frame
        Fsell = new JFrame("Pharmacy System");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", Color.blue);
        } catch (Exception ignored) {
        }
        Fsell.setUndecorated(true);
        Fsell.setIconImage(iconimage);
        Fsell.add(panelmain);
        Fsell.setVisible(true);
        // Fsell.setSize(950, 600);
        Fsell.setSize(screenSize.width, screenSize.height);
        Fsell.revalidate();
        Fsell.pack();
        Fsell.revalidate();
        Fsell.setLocationRelativeTo(null);
        Fsell.setResizable(true);
        Fsell.revalidate();
        Fsell.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Fsell.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                // minimized
                if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
                    Fsell.revalidate();
                    Fsell.pack();
                    Fsell.revalidate();
                    Fsell.setLocationRelativeTo(null);
                } // maximized
                else if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    Fsell.revalidate();
                    Fsell.pack();
                    Fsell.revalidate();
                    Fsell.setLocationRelativeTo(null);
                }
            }
        });
        //action events start
        bsearch.addActionListener(e -> {
            searchengine();
        });
        bselluser.addActionListener(e -> {
            provideservice();

            Fsell.setVisible(false);
            Sections msell = new Sections();
            msell.DrugNameFound();
            msell.repairmode();
            msell.dcost.setVisible(false);
            msell.tdcost.setVisible(false);
            msell.linvoice.setVisible(false);
            msell.tlinvoice.setVisible(false);
            msell.bdelete.setVisible(false);
            msell.upcost.setVisible(false);
            msell.tupcost.setVisible(false);
            msell.bupdate.setVisible(false);
            msell.bback.setVisible(false);
            msell.bedit.setVisible(false);
            msell.bsell.setVisible(false);
        });
        bsell.addActionListener(e -> {
            provideservice();

            Fsell.setVisible(false);
            Sections msell = new Sections();
            msell.DrugNameFound();
            msell.repairmode();
            msell.lsname.setVisible(true);
            msell.tsname.setVisible(true);
            msell.lrnumber.setVisible(true);
            msell.trnumber.setVisible(true);
            msell.lprovider.setVisible(true);
            msell.tprovider.setVisible(true);
            msell.bedit.setVisible(true);
            msell.bselluser.setVisible(false);
        });
        bedit.addActionListener(e -> {
            dcost.setVisible(true);
            tdcost.setVisible(true);
            linvoice.setVisible(true);
            tlinvoice.setVisible(true);
            bdelete.setVisible(true);
            upcost.setVisible(true);
            tupcost.setVisible(true);
            bupdate.setVisible(true);
            bback.setVisible(true);

            bedit.setVisible(false);
            lsname.setVisible(false);
            tsname.setVisible(false);
            lrnumber.setVisible(false);
            trnumber.setVisible(false);
            lprovider.setVisible(false);
            tprovider.setVisible(false);
        });
        bback.addActionListener(e -> {
            dcost.setVisible(false);
            tdcost.setVisible(false);
            linvoice.setVisible(false);
            tlinvoice.setVisible(false);
            bdelete.setVisible(false);
            upcost.setVisible(false);
            tupcost.setVisible(false);
            bupdate.setVisible(false);
            bback.setVisible(false);

            lsname.setVisible(true);
            tsname.setVisible(true);
            lrnumber.setVisible(true);
            trnumber.setVisible(true);
            lprovider.setVisible(true);
            tprovider.setVisible(true);
            bedit.setVisible(true);
        });
        bcancel.addActionListener(e -> {
            canacelaction();
        });

        bbacksell.addActionListener(e -> {
            String[] option = {"Yes", "No"};
            Toolkit.getDefaultToolkit().beep();
            int selloption = JOptionPane.showOptionDialog(null, "Are you Sure you want to logout", "Logout Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
            if (selloption == 0) {
                Fsell.setVisible(false);
                Login r3 = new Login();
                r3.LoginSection();
            } else {
                JOptionPane.showMessageDialog(null, "Still Logged In Continue With your Work", "Logout Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bupdate.addActionListener(e -> {
            String h = tupcost.getText();
            double f = Double.parseDouble(h);
            try {
                if (tlinvoice.getText().equalsIgnoreCase("") || tupcost.getText().equalsIgnoreCase("") || tlinvoice.getText().equalsIgnoreCase(null)) {
                    JOptionPane.showMessageDialog(null, "Please Enter Invoice", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    Connection con = DBConnector.getConnection();
                    String[] option = {"Yes", "No"};
                    int selloption = JOptionPane.showOptionDialog(null, "Proceed in Updating" + " " + tlinvoice.getText(), "Deletion Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon2, option, option[1]);
                    if (selloption == 0) {
                        String sqlupdate = "UPDATE servicetable set Tcost = '" + f + "' WHERE Invoice ='" + tlinvoice.getText() + "'";
                        prs = con.prepareStatement(sqlupdate);
                        prs.execute();
                        if (prs != null) {
                            JOptionPane.showMessageDialog(null, tlinvoice.getText() + " " + "Updated Successful", "Deletion Confirmation", JOptionPane.INFORMATION_MESSAGE, icon2);
                            rs.close();
                            prs.close();
                            con.close();

                            //method for reloading details
                            analyzingdata();
                            uplaod();
                        } else {
                            JOptionPane.showMessageDialog(null, tlinvoice.getText() + " " + "Update Failed", "Error Message", JOptionPane.ERROR_MESSAGE);
                            rs.close();
                            prs.close();
                            con.close();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Update Cancelled", "Error Message", JOptionPane.ERROR_MESSAGE);
                        rs.close();
                        prs.close();
                        con.close();
                    }
                }
            } catch (SQLException x) {
                JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + x, "Error Message", JOptionPane.ERROR_MESSAGE);
                //System.exit(0);
            }

        });
        bdelete.addActionListener(e -> {
            try {
                if (tlinvoice.getText().equalsIgnoreCase("") || tlinvoice.getText().equalsIgnoreCase(null)) {
                    JOptionPane.showMessageDialog(null, "Please Enter Invoice", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    Connection con = DBConnector.getConnection();
                    String[] option = {"Yes", "No"};
                    int selloption = JOptionPane.showOptionDialog(null, "Proceed in Deleting" + " " + tlinvoice.getText(), "Deletion Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon, option, option[1]);
                    if (selloption == 0) {
                        String sqldelete = "DELETE FROM servicetable WHERE Invoice ='" + tlinvoice.getText() + "'";
                        prs = con.prepareStatement(sqldelete);
                        prs.execute();
                        if (prs != null) {
                            JOptionPane.showMessageDialog(null, tlinvoice.getText() + " " + "Deleted Successful", "Deletion Confirmation", JOptionPane.INFORMATION_MESSAGE, icon);
                            rs.close();
                            prs.close();
                            con.close();

                            //method for reloading details
                            analyzingdata();
                            uplaod();
                        } else {
                            JOptionPane.showMessageDialog(null, tlinvoice.getText() + " " + "Deletion Failed", "Error Message", JOptionPane.ERROR_MESSAGE);
                            rs.close();
                            prs.close();
                            con.close();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Deletion Cancelled", "Error Message", JOptionPane.ERROR_MESSAGE);
                        rs.close();
                        prs.close();
                        con.close();
                    }
                }
            } catch (SQLException x) {
                JOptionPane.showMessageDialog(null, "Connection Failure" + "\n" + x, "Error Message", JOptionPane.ERROR_MESSAGE);
                //System.exit(0);
            }

        });
    }

//    public static void main(String[] args) {
//        Sections msell = new Sections();
//        msell.DrugNameFound();
//        msell.repairmode();
//    }
}
