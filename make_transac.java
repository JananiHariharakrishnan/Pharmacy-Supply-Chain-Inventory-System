/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacy;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.util.Date;
import oracle.jdbc.OracleTypes;

public class make_transac extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookingTable;
    Connection con = null;
    CallableStatement callableStatement = null;
    ResultSet resultSet;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
        start c;
        
    String pharmacyID = "";
        
    public make_transac() 
    {}
    public make_transac(start c) 
    {   this.c = c;
        c.PharmacyID=this.pharmacyID;
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
                        "c##janani","Jayaram98");
            }
            catch (SQLException ex) {
                Logger.getLogger(start.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
        catch(ClassNotFoundException ex){
            Logger.getLogger(start.class.getName()).log(Level.SEVERE,null, ex);
        } 
        
        setTitle("Pharmacy - Managing Transaction");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add "Back" button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start sp = new start(pharmacyID);
                sp.show();                
                dispose(); 

            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 4) ? JButton.class : Object.class;
            }
        };

        bookingTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);

        tableModel.addColumn("Order ID");
        tableModel.addColumn("Transaction_ID");
        tableModel.addColumn("Transaction_type");
        tableModel.addColumn("Transaction_date");
        tableModel.addColumn("Transaction_status");
        tableModel.addColumn("Transaction_amount");
        
        bookingTable.getTableHeader().getColumnModel().getColumn(4).setHeaderValue(null);
        int rowHeight = bookingTable.getRowHeight();
        int newHeight = (int) (rowHeight * 1.1);
        bookingTable.setRowHeight(newHeight);

        DisplayPendingOrders();

        bookingTable.getColumnModel().getColumn(6).setCellRenderer((TableCellRenderer) new ButtonRenderer());
        bookingTable.getColumnModel().getColumn(6).setCellEditor(new CustomButtonEditor(new JCheckBox(), true, tableModel, this.pharmacyID));

        setVisible(true);
    }

    private void UpdatePendingOrders(String orderId) 
    {
        PreparedStatement pstmt = null;
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
            "c##janani","Jayaram98");
            String sql = "UPDATE transactions,orders SET transaction_date = SYSDATE WHERE order_id = ? AND o.total_amount<=t.transaction_amount";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, orderId);
            int rowsUpdated = pstmt.executeUpdate();
            
            String sql1 = "UPDATE transactions t JOIN (\n" +
"    SELECT o.order_id, SUM(p.unit_price * o.qty_ordered) AS total_amount\n" +
"    FROM order_items o\n" +
"    JOIN product1 p ON p.product_id = o.product_id\n" +
"    WHERE o.order_id = ?\n" +
"    GROUP BY o.order_id\n" +
") subquery ON t.order_id = subquery.order_id\n" +
"SET t.transaction_amount = subquery.total_amount\n" +
"WHERE t.order_id = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, orderId);
            rowsUpdated = pstmt.executeUpdate();
            String sql2 = "UPDATE transactions t,order_items o,product1 p SET transaction_type = 'Complete' WHERE t.order_id = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, orderId);
            rowsUpdated = pstmt.executeUpdate();
            //con.commit();
            //con.setAutoCommit(true);

            // Print the number of rows updated for debugging purposes
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void DisplayPendingOrders() 
    {
        try {
            String sql = "SELECT orders.order_id,transaction_type,transaction_date,transaction_status, transaction_amount " +
                    "FROM orders JOIN transactions t ON orders.order_id = transactions.order_id " +
                    "WHERE t.transaction_date IS NULL";

            // Create a prepared statement with parameter placeholder
            PreparedStatement pstmt = con.prepareStatement(sql);
            //pstmt.setString(1, "S001"); //COMMENT THIS AND UNCOMMENT THE NEXT LINE AFTER DEBUGGING
            //pstmt.setString(1, this.SupplierID);   

            // Print the SQL query for debugging purposes
            String sql1 = pstmt.toString();
            System.out.println("Executing query: " + this.pharmacyID + sql1);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No data found for the given criteria.");
            }

            while (rs.next()) {
                String orderId1 = rs.getString("order_id");
                String transaction_type = rs.getString("transaction_type");
                Date orderDate = rs.getDate("transaction_date");
                String transaction_status = rs.getString("transaction_status");
                float totalAmount = rs.getFloat("transaction_amount");

                // Create a JButton for the "Deliver" column
                JButton deliverButton = new JButton("Complete Transaction");

                // Create an object array to represent the row data
                Object[] row = {orderId1, transaction_type, orderDate,transaction_status, totalAmount, deliverButton};
                tableModel.addRow(row); // Add the row to the table model
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                // Close resources
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static void logCallableStatement(String sql, Object[] params) 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Executing SQL: ").append(sql).append("\nWith parameters: ");
        for (Object param : params) {
            sb.append(param).append(" ");
        }
        System.out.println(sb.toString());
    }
    
    private class ButtonRenderer extends JButton implements TableCellRenderer 
    {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Ensure that the value is a JButton
            if (value instanceof JButton) {
                JButton button = (JButton) value;
                // Return the button as the cell renderer component
                return button;
            } else {
                // If the value is not a JButton, return this default renderer
                setText((value == null) ? "" : value.toString());
                return this;
            }
        }
    }

    private class CustomButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener 
    {
        private JButton button;
        private boolean isEditButton;
        private DefaultTableModel tableModel;
        private int selectedRow;
        String SupID = "";

        public CustomButtonEditor(JCheckBox checkBox, boolean isEditButton, DefaultTableModel tableModel, String SupID) {
            this.tableModel = tableModel;
            this.button = new JButton("Complete Transaction");
            this.button.setPreferredSize(new Dimension(this.button.getPreferredSize().width, 40)); // Adjust height as needed
            this.button.setOpaque(true);
            this.button.addActionListener(this);
            this.isEditButton = isEditButton;
            this.SupID = SupID;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            selectedRow = row;
            return this.button;
        }

        @Override
        public Object getCellEditorValue() {
            return this.button;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Perform action when the button is clicked
            String orderId = tableModel.getValueAt(selectedRow, 0).toString();
            UpdatePendingOrders(orderId);

            // Show confirmation dialog
            JOptionPane.showMessageDialog(null, "Transaction ID " + orderId + " done!");

            // Update button text to "Delivered"
            button.setText("Transaction Made");

            // Stop editing mode
            stopCellEditing();
        }
    }

}
