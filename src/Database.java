import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement; 
import java.swing.*;
import java.swing.event.ChangeEvent;
import java.swing.event.ChangeListener; 
import java.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.ArrayList;

public class Database {
    public static void main(String[] args){ 
        (new Database()).go();        
    }
    private void go() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            String cmd = "CREATE TABLE IF NOT EXISTS user (" + 
                "username STRING PRIMARY KEY," + 
                "password STRING);";
           conn.createStatement().executeUpdate(cmd);
           conn.close(); 
        } catch (SQLEXception e) {
            e.printStackTrace();
        }
    }
    listModel = new DefaultListModel<String>();
    JFrame = jFrame = new JFrame();
    JTabbedPane jTabs = new JTabbedPane();
    jTabs.add("CREATE ACCOUNT", makeCreateTab());
    jTabs.add("LOGIN", makeLoginTab());

    jTabs.addChangeListener(new ChangeListener() {
        @Override 
        public void stateChanged(ChangeEvent e) {
            ArrayList<String> = arrayList = new ArrayList<String>();
            try {
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }); 

    jFrame.add(jTabs);
    jFrame.setSize(600, 600);
    jFrame.setVisible(true);
}

private JPanel makeLoginTab() {
    JPanel jPanel = new JPanel();
    
}
