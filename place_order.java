/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pharmacy;
import javax.swing.*;
import java.sql.*;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author Janani
 */
public class place_order extends javax.swing.JFrame {
        Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
    start c;
    public place_order(){}
    /**
     * Creates new form place_order
     */
    //public place_order(start c) 
    String pharmacyID;
    public place_order(start c) {
        this.c = c;
        c.PharmacyID=this.pharmacyID;
        initComponents();
        try{
        Class.forName("oracle.jdbc.OracleDriver");
        try {
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","c##diya","admin");
        }
        catch (SQLException ex) {
        Logger.getLogger(start.class.getName()).log(Level.SEVERE,null, ex);
        JOptionPane.showMessageDialog(this,ex.getMessage());
        }
        }
        catch(ClassNotFoundException ex){
        Logger.getLogger(start.class.getName()).log(Level.SEVERE,null, ex);
        JOptionPane.showMessageDialog(this,ex.getMessage());

        } 
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Clear = new javax.swing.JButton();
        confirm_order = new javax.swing.JButton();
        back_to_home = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        s_id = new javax.swing.JTextField();
        s_id1 = new javax.swing.JTextField();
        p_name = new javax.swing.JTextField();
        dosage = new javax.swing.JTextField();
        confirm_order1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Product Name");

        jLabel3.setText("Dosage");

        jLabel4.setText("Quantity Ordered");

        Clear.setText("Clear");

        confirm_order.setText("Confirm Order");
        confirm_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirm_orderActionPerformed(evt);
            }
        });

        back_to_home.setText("Back");
        back_to_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_to_homeActionPerformed(evt);
            }
        });

        jLabel5.setText("Supplier  ID");

        s_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_idActionPerformed(evt);
            }
        });

        s_id1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_id1ActionPerformed(evt);
            }
        });

        p_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_nameActionPerformed(evt);
            }
        });

        dosage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosageActionPerformed(evt);
            }
        });

        confirm_order1.setText("Add Product");
        confirm_order1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirm_order1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("New Order");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(s_id, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(90, 90, 90)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(s_id1)
                                    .addComponent(p_name, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dosage))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 34, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Clear)
                            .addComponent(confirm_order))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(confirm_order1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(back_to_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(88, 88, 88))))
            .addGroup(layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(s_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(p_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dosage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(s_id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirm_order)
                    .addComponent(confirm_order1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Clear)
                    .addComponent(back_to_home))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private static int currentID = 0;
    private static final String PREFIX = "O";

    public static String generateID(int flag) {
        // Ensure the ID part is always two digits
        String id = String.format("%02d", currentID);
        if(flag==1){
        currentID++;}
        return PREFIX + id;
    }

    private void confirm_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirm_orderActionPerformed
        // TODO add your handling code here:
        try {   
                Date date = new Date();
                java.sql.Date sqldate= new java.sql.Date(date.getTime());
                String order_id = generateID(1);
            
                String sql = "insert into orders values(?,?,NULL,NULL,'P',NULL,?,?)";
        	ps = con.prepareStatement(sql);
                ps.setString(1, order_id);
        	ps.setDate(2,sqldate);
                ps.setString(3, pharmacyID);
                ps.setString(4, s_id.getText());
        	ps.execute();
                JOptionPane.showMessageDialog(null, "Order ID: " + order_id);
                String sql1 = "UPDATE orders o\n" +
"JOIN (\n" +
"    SELECT od.order_id, SUM(p.unit_price * od.qty_ordered) AS total_amount\n" +
"    FROM order_list od\n" +
"    JOIN product1 p ON p.product_id = od.product_id\n" +
"    GROUP BY od.order_id\n" +
") subquery ON o.order_id = subquery.order_id\n" +
"SET o.total_amount = subquery.total_amount";
        	ps = con.prepareStatement(sql1);
                ps.execute();
                

    	}
    	catch (SQLException ex) {
        	Logger.getLogger(order_details.class.getName()).log(Level.SEVERE,null, ex);
        	JOptionPane.showMessageDialog(this,ex.getMessage());
    	}
        transac_detail asi = new transac_detail(this);
        asi.show();
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_confirm_orderActionPerformed

    private void back_to_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_to_homeActionPerformed
        // TODO add your handling code here:
        c.setVisible(true);
        dispose();
    }//GEN-LAST:event_back_to_homeActionPerformed

    private void s_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_idActionPerformed

    private void s_id1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_id1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_id1ActionPerformed

    private void p_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_nameActionPerformed

    private void dosageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dosageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dosageActionPerformed

    public interface OrderIdListener {
    void onOrderIdGenerated(String orderId);
    }
    
    private void confirm_order1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirm_order1ActionPerformed
        // TODO add your handling code here:
        try{
        String sql2 = "insert into order_item values(?,?,?)";
        String order_id = generateID(0);
        ps = con.prepareStatement(sql2);
        ps.setString(1, order_id);
        ps.setString(2, p_name.getText());
        ps.setString(3, dosage.getText());
        ps.execute();
        JOptionPane.showMessageDialog(this,"Added to Order_List!");
        p_name.setText("");
        dosage.setText("");
        String sql1 = "update pharmacy_inventory ph,product p set qty_available=qty_available-? where p.product_id=ph.product_id AND p.product_name=? AND p.dosage=?";
        	ps = con.prepareStatement(sql1);
                ps.setString(1, order_id);
                ps.setString(2, p_name.getText());
                ps.setString(3, dosage.getText());
                ps.setString(3,pharmacyID);
                ps.execute();
        
        }

        catch (SQLException ex) {
        	Logger.getLogger(order_details.class.getName()).log(Level.SEVERE,null, ex);
        	JOptionPane.showMessageDialog(this,ex.getMessage());
    	}
    }//GEN-LAST:event_confirm_order1ActionPerformed
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(place_order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(place_order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(place_order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(place_order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new place_order().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Clear;
    private javax.swing.JButton back_to_home;
    private javax.swing.JButton confirm_order;
    private javax.swing.JButton confirm_order1;
    private javax.swing.JTextField dosage;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField p_name;
    private javax.swing.JTextField s_id;
    private javax.swing.JTextField s_id1;
    // End of variables declaration//GEN-END:variables
}
