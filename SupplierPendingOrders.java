/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newproject;
import javax.swing.JFrame;
/**
 *
 * @author Diya Seshan
 */
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

public class SupplierPendingOrders extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookingTable;
    Connection con = null;
    CallableStatement callableStatement = null;
    ResultSet resultSet;
    
    String SupplierID = "";
    
    public SupplierPendingOrders(String SupplierID) 
    {
        this.SupplierID = SupplierID;
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
                        "c##diya","admin");
            }
            catch (SQLException ex) {
                Logger.getLogger(RegistrationPage.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
        catch(ClassNotFoundException ex){
            Logger.getLogger(RegistrationPage.class.getName()).log(Level.SEVERE,null, ex);
        } 
        
        setTitle("Supplier - Pending Orders");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add "Back" button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SupplierHomePage sp = new SupplierHomePage(SupplierID);
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
        tableModel.addColumn("Pharmacy Name");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Total Amount");
        tableModel.addColumn("Deliver");
        
        bookingTable.getTableHeader().getColumnModel().getColumn(4).setHeaderValue(null);
        int rowHeight = bookingTable.getRowHeight();
        int newHeight = (int) (rowHeight * 1.1);
        bookingTable.setRowHeight(newHeight);

        DisplayPendingOrders();

        bookingTable.getColumnModel().getColumn(4).setCellRenderer((TableCellRenderer) new ButtonRenderer());
        bookingTable.getColumnModel().getColumn(4).setCellEditor(new CustomButtonEditor(new JCheckBox(), true, tableModel, this.SupplierID));

        setVisible(true);
    }

    private void UpdatePendingOrders(String orderId, String supplierId) 
    {
        PreparedStatement pstmt = null;
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
            "c##diya","admin");
            String sql = "UPDATE orders SET delivery_date = SYSDATE WHERE order_id = ? AND supplier_id = ? AND delivery_date IS NULL";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, orderId);
            pstmt.setString(2, supplierId);
               
            //con.setAutoCommit(false);
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
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
            String sql = "SELECT orders.order_id, pharmacy2A.pharmacy_name, orders.order_date, " +
                    "orders.total_amount FROM orders JOIN pharmacy1 ON orders.pharmacy_id = pharmacy1.pharmacy_id " +
                    " JOIN pharmacy2A ON pharmacy1.pharmacy_phno = pharmacy2A.pharmacy_phno WHERE " + 
                    " orders.supplier_id = ? AND orders.delivery_date IS NULL";

            // Create a prepared statement with parameter placeholder
            PreparedStatement pstmt = con.prepareStatement(sql);
            //pstmt.setString(1, "S001"); //COMMENT THIS AND UNCOMMENT THE NEXT LINE AFTER DEBUGGING
            pstmt.setString(1, this.SupplierID);   

            // Print the SQL query for debugging purposes
            String sql1 = pstmt.toString();
            System.out.println("Executing query: " + this.SupplierID + sql1);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No data found for the given criteria.");
            }

            while (rs.next()) {
                String orderId = rs.getString("order_id");
                String pharmacyName = rs.getString("pharmacy_name");
                Date orderDate = rs.getDate("order_date");
                float totalAmount = rs.getFloat("total_amount");

                // Create a JButton for the "Deliver" column
                JButton deliverButton = new JButton("Deliver");

                // Create an object array to represent the row data
                Object[] row = {orderId, pharmacyName, orderDate, totalAmount, deliverButton};
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
            this.button = new JButton("Deliver");
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
            UpdatePendingOrders(orderId, this.SupID);

            // Show confirmation dialog
            JOptionPane.showMessageDialog(null, "Order ID " + orderId + " delivered!");

            // Update button text to "Delivered"
            button.setText("Delivered");

            // Stop editing mode
            stopCellEditing();
        }
    }

}