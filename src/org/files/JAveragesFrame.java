/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.files;

import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JAveragesFrame extends javax.swing.JFrame 
{
    boolean TotalCases, TotalDeaths, NewCases, NewDeaths = false;
    String startDateStr = null, endDateStr = null;
    int selectedColumn;
    Date startDate, endDate = null;
    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
    GetConnection gc = new GetConnection();
    GetResultSet grs = new GetResultSet();
    ResultSet rs; 
    PreparedStatement stmt;
    ButtonGroup bGroup = new ButtonGroup();
    
    MainFrame mainFrame;
    
    public JAveragesFrame ( )
    {
        
    }
    
    public JAveragesFrame(int selectedRB, MainFrame mf) 
    {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
        setVisible(false);
        mainFrame = mf;
        
       switch (selectedRB)
       {
           case 1:  // average Cases
               JAvgCasesRB.setSelected(true);
               selectedColumn = selectedRB;
               break;
           case 2: // total Cases 
               JTotalCasesRB.setSelected(true);
               selectedColumn = selectedRB;
               break;
           case 3: // average Deaths
               JAvgDeathsRB.setSelected(true);
               selectedColumn = selectedRB;
               break;
           case 4: // total Deaths
               JTotalDeathsRB.setSelected(true);
               selectedColumn = selectedRB;
               break;
           default:
               break;
       }
          
        bGroup.add(JAvgCasesRB);
        bGroup.add(JTotalCasesRB);
        bGroup.add(JAvgDeathsRB);
        bGroup.add(JTotalDeathsRB);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JAveragesPnl = new javax.swing.JPanel();
        controlsPanel = new javax.swing.JPanel();
        JStartDateChooser = new com.toedter.calendar.JDateChooser();
        JEndDateChooser = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        JAvgCasesRB = new javax.swing.JRadioButton();
        JAvgDeathsRB = new javax.swing.JRadioButton();
        JTotalCasesRB = new javax.swing.JRadioButton();
        JTotalDeathsRB = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        submitBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        startDateLbl = new javax.swing.JLabel();
        endDateLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ResultsTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 800));

        JAveragesPnl.setBackground(new java.awt.Color(0, 102, 153));
        JAveragesPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TimeFrame", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        JAveragesPnl.setPreferredSize(new java.awt.Dimension(975, 700));

        controlsPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(255, 255, 255)));

        JStartDateChooser.setDateFormatString("yyyy-MM-dd");

        JEndDateChooser.setDateFormatString("yyyy-MM-dd");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Category", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(310, 240));

        JAvgCasesRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAvgCasesRB.setText("Average Cases");
        JAvgCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAvgCasesRBActionPerformed(evt);
            }
        });

        JAvgDeathsRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAvgDeathsRB.setText("Average Deaths");
        JAvgDeathsRB.setToolTipText("");
        JAvgDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAvgDeathsRBActionPerformed(evt);
            }
        });

        JTotalCasesRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTotalCasesRB.setText("Total Cases");
        JTotalCasesRB.setToolTipText("");
        JTotalCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalCasesRBActionPerformed(evt);
            }
        });

        JTotalDeathsRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTotalDeathsRB.setText("Total Deaths");
        JTotalDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalDeathsRBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JAvgDeathsRB)
                    .addComponent(JTotalDeathsRB)
                    .addComponent(JTotalCasesRB)
                    .addComponent(JAvgCasesRB))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(JAvgCasesRB)
                .addGap(18, 18, 18)
                .addComponent(JTotalCasesRB)
                .addGap(18, 18, 18)
                .addComponent(JAvgDeathsRB)
                .addGap(18, 18, 18)
                .addComponent(JTotalDeathsRB)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(102, 102, 102), new java.awt.Color(204, 204, 204), null, null));
        jPanel2.setPreferredSize(new java.awt.Dimension(310, 240));

        submitBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        submitBtn.setText("Submit");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        clearBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        clearBtn.setText("Clear");
        clearBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        closeBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        closeBtn.setText("Close");
        closeBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submitBtn))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(submitBtn)
                .addGap(37, 37, 37)
                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        startDateLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        startDateLbl.setText("Start Date:");

        endDateLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        endDateLbl.setText("End Date:");

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startDateLbl, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(endDateLbl, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JStartDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JEndDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(controlsPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(controlsPanelLayout.createSequentialGroup()
                                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(JStartDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(startDateLbl))
                                .addGap(39, 39, 39)
                                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(endDateLbl)
                                    .addComponent(JEndDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        ResultsTextArea.setEditable(false);
        ResultsTextArea.setColumns(1);
        ResultsTextArea.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        ResultsTextArea.setRows(5);
        ResultsTextArea.setText(" ");
        ResultsTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Results", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jScrollPane1.setViewportView(ResultsTextArea);

        javax.swing.GroupLayout JAveragesPnlLayout = new javax.swing.GroupLayout(JAveragesPnl);
        JAveragesPnl.setLayout(JAveragesPnlLayout);
        JAveragesPnlLayout.setHorizontalGroup(
            JAveragesPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JAveragesPnlLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(JAveragesPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        JAveragesPnlLayout.setVerticalGroup(
            JAveragesPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JAveragesPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JAveragesPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JAveragesPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JAvgCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAvgCasesRBActionPerformed
      
        NewCases = true;
        NewDeaths = false;
        selectedColumn = 1;
    }//GEN-LAST:event_JAvgCasesRBActionPerformed

    private void JAvgDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAvgDeathsRBActionPerformed
       
        NewCases = true;
        NewDeaths = false;
        selectedColumn = 3;
    }//GEN-LAST:event_JAvgDeathsRBActionPerformed

    private void JTotalCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalCasesRBActionPerformed
      
        NewCases = false;
        NewDeaths = true;
        selectedColumn = 2;
    }//GEN-LAST:event_JTotalCasesRBActionPerformed

    private void JTotalDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalDeathsRBActionPerformed
        
        NewCases = false;
        NewDeaths = true;
        selectedColumn = 4;
    }//GEN-LAST:event_JTotalDeathsRBActionPerformed

     public boolean CheckInputData (Date firstDate, Date lastDate, JDateChooser startDateChooser, JDateChooser endDateChooser) throws ParseException
    {
        JFrame messageFrame = new JFrame( );
        java.sql.Date startDate, endDate;
        String message1 = "Start Date must be on or after " + firstDate + " and before " + lastDate + ".";
        String message2 = "End Date must be greater than Start Date.";
        String message3 = "End Date cannot be greater than: " + lastDate + ".";
        
    //    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
       
        if ( startDateChooser.getDate() == null && endDateChooser.getDate() == null)
        {
            JOptionPane.showMessageDialog(messageFrame, "Enter Dates.");
            return false;
        }
        else if (startDateChooser.getDate() == null) 
        {
            JOptionPane.showMessageDialog(messageFrame, "Enter Start Date.");
            return false;
        }
        else if ( endDateChooser.getDate() == null)
        {
            JOptionPane.showMessageDialog(messageFrame, "Enter End Date.");
            return false;
        }
        else
        {
            startDate = new java.sql.Date (startDateChooser.getDate().getTime());
            startDateStr = startDate.toString();
            endDate = new java.sql.Date (endDateChooser.getDate().getTime());
            endDateStr = endDate.toString();
            if (startDate.compareTo(firstDate) <  0 || startDate.compareTo(lastDate) >= 0 )
            {
                JOptionPane.showMessageDialog(messageFrame, message1);
                startDateChooser.setCalendar(null);
                return false;
            }
            else if (endDate.compareTo(startDate) <=  0   ) 
            {
                JOptionPane.showMessageDialog(messageFrame, message2);
                endDateChooser.setCalendar(null);
                return false;
            }
            else if (endDateStr.compareTo(lastDate.toString()) >  0 )  //|| startDate.compareTo(lastDate)== 0)
            {
                JOptionPane.showMessageDialog(messageFrame, message3);
                endDateChooser.setCalendar(null);
                return false;
            }
        }
        
        return true;
    } 
     
    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed

        Date firstDate = null, lastDate = null;
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        final String newLine = "\n\n";
        String formattedAvg = "";
        String resultText;
        Font myFont = new Font ("Times New Roman", Font.PLAIN, 22);
       

        grs = new GetResultSet();

        try
        {
            rs = grs.getResultSet();
            rs.first();
            firstDate = rs.getDate(1);
            rs.last();
            lastDate = rs.getDate(1);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(JAveragesFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        startDate = JStartDateChooser.getDate();
        endDate = JEndDateChooser.getDate();

        try 
        {
            if (CheckInputData(firstDate, lastDate, JStartDateChooser, JEndDateChooser))
            {
                BigDecimal avg, total  = null;

                // convert java.util.Dates to java.sql.Dates
                java.sql.Date sqlStartDate = new java.sql.Date (startDate.getTime());
                java.sql.Date sqlEndDate = new java.sql.Date (endDate.getTime());
                
              

                switch (selectedColumn)
                {
                    case 1:  // average of new cases
                    try
                    {
                        avg =  mainFrame.GetAverage(sqlStartDate, sqlEndDate, 1);
                        formattedAvg = decimalFormat.format(avg);
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(JAveragesFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    resultText = String.format("The average number of cases between %s and %s is %s.", startDateStr, endDateStr, formattedAvg);

                    ResultsTextArea.append(newLine);
                    ResultsTextArea.append(resultText);
                    break;

                    case 2:  // average of new deaths
                    try
                    {
                        avg = mainFrame.GetAverage(sqlStartDate, sqlEndDate, 2);
                        formattedAvg = decimalFormat.format(avg);
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(JAveragesFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    resultText = String.format("The total number of cases between %s and %s is %s.", startDateStr, endDateStr, formattedAvg);

                    ResultsTextArea.append(newLine);
                    ResultsTextArea.append(resultText);
                    break;

                    case 3:  // total of new cases
                    try
                    {
                        total =   mainFrame.GetAverage(sqlStartDate, sqlEndDate, 3);
                        formattedAvg = decimalFormat.format(total);
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(JAveragesFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    resultText = String.format("The average number of deaths between %s and %s is %s.", startDateStr, endDateStr, formattedAvg);

                    ResultsTextArea.append(newLine);
                    ResultsTextArea.append(resultText);
                    break;

                    case 4:  // total of new deaths
                    try
                    {
                        total =   mainFrame.GetAverage(sqlStartDate, sqlEndDate, 4);
                        formattedAvg = decimalFormat.format(total);
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(JAveragesFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    resultText = String.format("The total number of deaths between %s and %s is %s.", startDateStr, endDateStr, formattedAvg);

                    ResultsTextArea.append(newLine);
                    ResultsTextArea.append(resultText);
                    break;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(JAveragesFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed

        JStartDateChooser.setCalendar(null);
        JEndDateChooser.setCalendar(null);
        bGroup.clearSelection();
        ResultsTextArea.setText("");
    }//GEN-LAST:event_clearBtnActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed

        this.dispose();
    }//GEN-LAST:event_closeBtnActionPerformed

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
            java.util.logging.Logger.getLogger(JAveragesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JAveragesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JAveragesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JAveragesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JAveragesFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JAveragesPnl;
    private javax.swing.JRadioButton JAvgCasesRB;
    private javax.swing.JRadioButton JAvgDeathsRB;
    private com.toedter.calendar.JDateChooser JEndDateChooser;
    private com.toedter.calendar.JDateChooser JStartDateChooser;
    private javax.swing.JRadioButton JTotalCasesRB;
    private javax.swing.JRadioButton JTotalDeathsRB;
    private javax.swing.JTextArea ResultsTextArea;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton closeBtn;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JLabel endDateLbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel startDateLbl;
    private javax.swing.JButton submitBtn;
    // End of variables declaration//GEN-END:variables
}
