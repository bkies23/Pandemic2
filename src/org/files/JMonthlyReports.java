/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.files;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.time.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class JMonthlyReports extends JFrame 
{
    String [] months = new String [] { "MARCH 2020", "APRIL 2020", "MAY 2020", "JUNE 2020", "JULY 2020", "AUGUST 2020", "SEPTEMBER 2020", "OCTOBER 2020", "NOVEMBER 2020", 
                                       "DECEMBER 2020", "JANUARY 2021", "FEBRUARY 2021", "MARCH 2021", "APRIL 2021", "MAY 2021", "JUNE 2021", "JULY 2021", "AUGUST 2021", 
                                       "SEPTEMBER 2021", "OCTOBER 2021", "NOVEMBER 2021", "DECEMBER 2021", "JANUARY 2022", "FEBRUARY 2022", "MARCH 2022", "APRIL 2022", 
                                       "MAY 2022", "JUNE 2022", "JULY 2022", "AUGUST 2022" };
    ArrayList <Object> mnthAvgCases = new ArrayList <>(); 
    ArrayList <Object> mnthTtlCases = new ArrayList <>();
    String statistic, startMonth, endMonth;
    int finalMonth, startMonthIndex, endMonthIndex;
    JTextArea lowerPnlTA = new JTextArea( );
    JScrollPane lowerPnlSP = new JScrollPane( );
    
    LocalDate ld = LocalDate.now();
   
 //   lowerPnlSP = new JScrollPane(lowerPnlTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   // JLowerPnlSP.setPreferredSize(new Dimension(width, height * 2));
    //scrollPane.setViewportView(textArea);
    
    public JMonthlyReports( ) 
    {
        initComponents();
        setVisible(true);
    }
    
    public int RetrieveLastMonth (String yearMonth)
    {
        int lastMonth = 0;
        
        do
        {
            lastMonth++;
        } 
        while (! yearMonth.trim().equals(months[lastMonth].trim()));
        
        return lastMonth;
    }
   
    public JMonthlyReports (String stat) throws SQLException
    {
        initComponents();
        statistic = stat;
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Lower Panel Text Area
        lowerPnlTA.setPreferredSize(new Dimension(564, 963));
        lowerPnlTA.setBackground(Color.white);
        lowerPnlTA.setForeground(new Color(0, 128 ,128));
        lowerPnlTA.setOpaque(true);
        lowerPnlTA.setBorder(new EmptyBorder (15, 15, 15, 15));
        Border insideBorder = BorderFactory.createLineBorder(Color.blue, 1, false);
        lowerPnlTA.setBorder(BorderFactory.createCompoundBorder(insideBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        lowerPnlTA.setVisible(true);
        // Lower Panel Scrollpane
        lowerPnlSP = new JScrollPane(lowerPnlTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lowerPnlSP.getViewport().setBackground(Color.WHITE);
        lowerPnlSP.setPreferredSize(new Dimension(860, 660));
        lowerPnlSP.setViewportView(lowerPnlTA);
        lowerPnlSP.setOpaque(true);
        lowerPnlSP.setVisible(true);
    //    lowerPnlSP.add(lowerPnlTA);
        add(lowerPnlSP);
        
        JStartMonthCB.setMaximumRowCount(9);
        JEndMonthCB.setMaximumRowCount(9);
       
        ComputeMonthAvgTotalCases( );
        
        // retrieve current month in order to determine final complete month 
        Month currentMonth = ld.getMonth();
        // retrieve current year
        int year = ld.getYear();
        String yearStr = String.valueOf(year);
        
        String currentMonthYearStr = currentMonth.toString() + " " + yearStr;
        
        finalMonth = RetrieveLastMonth(currentMonthYearStr);
        
        for ( int i = 0; i < finalMonth; i++)
            JStartMonthCB.addItem(months[i]);
        
        switch (statistic)
        {
            case "AVERAGE CASES":
                JAvgCasesRB.setSelected(true);
             //   statistic = " AVERAGE CASES";
                break;
            case "TOTAL CASES":
                JTotalCasesRB.setSelected(true);
            //    statistic = " TOTAL CASES";
                break;
            case "AVERAGE DEATHS":
                JAvgDeathsRB.setSelected(true);
             //   statistic = " AVERAGE DEATHS";
                break;
            case "TOTAL DEATHS":
                JTotalDeathsRB.setSelected(true);
            //    statistic = " TOTAL DEATHS";
                break;           
        }
    
        setVisible(true);  
    }
    
    public void Draw ( )
    { 
        Graphics g = JLowerPnl.getGraphics();
        Font myFont = new Font ("Times New Roman", Font.BOLD, 18);
        g.setFont(myFont);
        g.setColor(Color.black);
        String monthsStr = "MONTH(S)"; 
        AttributedString as = new AttributedString(monthsStr);
        as.addAttribute(TextAttribute.FONT, myFont);
        as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 8);
        g.drawString(as.getIterator(), 180, 240);
        
       // statistic = " AVERAGE CASES";
        
      //  String statisticStr = statistic;
        as = new AttributedString(statistic);
        as.addAttribute(TextAttribute.FONT, myFont);
        switch(statistic)
        {
            case "AVERAGE CASES":
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 13);
                break;
            case "TOTAL CASES":
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 10);
                break;
            case "AVERAGE DEATHS":
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 14);
                break;
            case "TOTAL DEATHS":
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 12);
                break;        
        }
        
        g.drawString(as.getIterator(), 570, 40);                  
    }
    
 //   public void paint(Graphics g)
  //  {
     //   super.paint(g);
    //   Graphics2D g2 = (Graphics2D) g;
     //   Draw (g2); 
     //   PrintStats();    
  //  }
    
    public int GetMonth (int index)
    {
        int monthIndex = 0;
        
        do 
        {
            monthIndex++;
        }
        while (monthIndex != index);
        
        return monthIndex - 1;
    }
    
    public void ComputeMonthAvgTotalCases( ) throws SQLException
    {
        GetResultSet grs = new GetResultSet( );
        ResultSet rs = grs.getResultSet();
        int sum = 0;
        float avg = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        
        // March 2020
        rs.first();
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // April 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 30));
        mnthTtlCases.add(sum);
        
        // May 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // June 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 30));
        mnthTtlCases.add(sum);
        
        // July 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // August 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // September 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 30));
        mnthTtlCases.add(sum);
        
        // October 2020
         sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // November 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 30));
        mnthTtlCases.add(sum);
                
       // December 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // January 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // February 2021
        sum = 0;
        for ( int i = 1; i <= 28; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 28));
        mnthTtlCases.add(sum);
        
        // March 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // April 2021
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 30));
        mnthTtlCases.add(sum);
        
        // May 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // June 2021
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 30));
        mnthTtlCases.add(sum);
        
        // July 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
        // August 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(4);
            rs.next();
        }
        
        mnthAvgCases.add(df.format((float)sum / 31));
        mnthTtlCases.add(sum);
        
    }
    
    public void PrintStats ()
    {
        Graphics g = lowerPnlTA.getGraphics();
    //    Graphics g2 = JBackgroundPnl.getGraphics();
 
        Font myFont = new Font ("Times New Roman", Font.BOLD, 22);
        
        g.setFont(myFont);
        g.setColor(Color.blue);
        int x1 = 80;
        int x2 = 470;
        int y = 40;
        int verticalSpacing = 30;
       
        if ( startMonthIndex > 0)
            for (int i = startMonthIndex - 1; i < endMonthIndex; i++)
            {
                g.drawString(months[i], x1, y);
                g.drawString(mnthAvgCases.get(i).toString(), x2, y);
                g.drawLine(50, y, 800, y);
                y += verticalSpacing;
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

        JButtonGroup = new javax.swing.ButtonGroup();
        JBackgroundPnl = new javax.swing.JPanel();
        JUpperPnl = new javax.swing.JPanel();
        JMonthsPnl = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        JTotalDeathsRB = new javax.swing.JRadioButton();
        JAvgCasesRB = new javax.swing.JRadioButton();
        JTotalCasesRB = new javax.swing.JRadioButton();
        JAvgDeathsRB = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        JStartMonthCB = new javax.swing.JComboBox();
        JEndMonthCB = new javax.swing.JComboBox();
        JSelectedMonthsLbl = new javax.swing.JLabel();
        JMonthlyReportSubmitBtn = new javax.swing.JButton();
        JMonthlyReportResetBtn = new javax.swing.JButton();
        JLowerPnl = new javax.swing.JPanel();
        JLowerPnlSP = new javax.swing.JScrollPane();
        JLowerPnlTA = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JBackgroundPnl.setBackground(new java.awt.Color(0, 102, 153));
        JBackgroundPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monthly Data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        JBackgroundPnl.setPreferredSize(new java.awt.Dimension(830, 970));

        JUpperPnl.setBackground(new java.awt.Color(255, 204, 153));
        JUpperPnl.setPreferredSize(new java.awt.Dimension(950, 285));

        JMonthsPnl.setBackground(new java.awt.Color(204, 204, 204));
        JMonthsPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Month(s) and Statistic To View", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        JMonthsPnl.setPreferredSize(new java.awt.Dimension(410, 130));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        JTotalDeathsRB.setBackground(new java.awt.Color(204, 204, 204));
        JButtonGroup.add(JTotalDeathsRB);
        JTotalDeathsRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTotalDeathsRB.setText("Total Deaths");
        JTotalDeathsRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JTotalDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalDeathsRBActionPerformed(evt);
            }
        });

        JAvgCasesRB.setBackground(new java.awt.Color(204, 204, 204));
        JButtonGroup.add(JAvgCasesRB);
        JAvgCasesRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAvgCasesRB.setText("Average Cases");
        JAvgCasesRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JAvgCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAvgCasesRBActionPerformed(evt);
            }
        });

        JTotalCasesRB.setBackground(new java.awt.Color(204, 204, 204));
        JButtonGroup.add(JTotalCasesRB);
        JTotalCasesRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTotalCasesRB.setText("Total Cases");
        JTotalCasesRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JTotalCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalCasesRBActionPerformed(evt);
            }
        });

        JAvgDeathsRB.setBackground(new java.awt.Color(204, 204, 204));
        JButtonGroup.add(JAvgDeathsRB);
        JAvgDeathsRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAvgDeathsRB.setText("Average Deaths");
        JAvgDeathsRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JAvgDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAvgDeathsRBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(JTotalCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JAvgDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(JAvgCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JTotalDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTotalDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JAvgCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTotalCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JAvgDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        JStartMonthCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JStartMonthCB.setMaximumRowCount(12);
        JStartMonthCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Starting Month" }));
        JStartMonthCB.setPreferredSize(new java.awt.Dimension(160, 25));
        JStartMonthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JStartMonthCBActionPerformed(evt);
            }
        });

        JEndMonthCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JEndMonthCB.setMaximumRowCount(12);
        JEndMonthCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ending Month" }));
        JEndMonthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JEndMonthCBActionPerformed(evt);
            }
        });

        JSelectedMonthsLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JSelectedMonthsLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JSelectedMonthsLbl.setText(" ");
        JSelectedMonthsLbl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        JSelectedMonthsLbl.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(JStartMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JEndMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JSelectedMonthsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JStartMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JEndMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JSelectedMonthsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        JMonthlyReportSubmitBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JMonthlyReportSubmitBtn.setText("Submit");
        JMonthlyReportSubmitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMonthlyReportSubmitBtnActionPerformed(evt);
            }
        });

        JMonthlyReportResetBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JMonthlyReportResetBtn.setText("Reset");

        javax.swing.GroupLayout JMonthsPnlLayout = new javax.swing.GroupLayout(JMonthsPnl);
        JMonthsPnl.setLayout(JMonthsPnlLayout);
        JMonthsPnlLayout.setHorizontalGroup(
            JMonthsPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JMonthsPnlLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JMonthsPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(JMonthsPnlLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JMonthlyReportSubmitBtn)
                        .addGap(102, 102, 102)
                        .addComponent(JMonthlyReportResetBtn)
                        .addGap(30, 30, 30))
                    .addGroup(JMonthsPnlLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        JMonthsPnlLayout.setVerticalGroup(
            JMonthsPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JMonthsPnlLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(JMonthsPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JMonthsPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JMonthlyReportResetBtn)
                    .addComponent(JMonthlyReportSubmitBtn))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JUpperPnlLayout = new javax.swing.GroupLayout(JUpperPnl);
        JUpperPnl.setLayout(JUpperPnlLayout);
        JUpperPnlLayout.setHorizontalGroup(
            JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JUpperPnlLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(JMonthsPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        JUpperPnlLayout.setVerticalGroup(
            JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JUpperPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JMonthsPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JLowerPnl.setBackground(new java.awt.Color(204, 204, 204));

        JLowerPnlSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JLowerPnlSP.setPreferredSize(new java.awt.Dimension(564, 963));

        JLowerPnlTA.setColumns(20);
        JLowerPnlTA.setLineWrap(true);
        JLowerPnlTA.setRows(5);
        JLowerPnlTA.setWrapStyleWord(true);
        JLowerPnlTA.setPreferredSize(new java.awt.Dimension(564, 963));
        JLowerPnlSP.setViewportView(JLowerPnlTA);

        javax.swing.GroupLayout JLowerPnlLayout = new javax.swing.GroupLayout(JLowerPnl);
        JLowerPnl.setLayout(JLowerPnlLayout);
        JLowerPnlLayout.setHorizontalGroup(
            JLowerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JLowerPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JLowerPnlSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        JLowerPnlLayout.setVerticalGroup(
            JLowerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JLowerPnlLayout.createSequentialGroup()
                .addComponent(JLowerPnlSP, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout JBackgroundPnlLayout = new javax.swing.GroupLayout(JBackgroundPnl);
        JBackgroundPnl.setLayout(JBackgroundPnlLayout);
        JBackgroundPnlLayout.setHorizontalGroup(
            JBackgroundPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JBackgroundPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JUpperPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(JBackgroundPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(JBackgroundPnlLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(JLowerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        JBackgroundPnlLayout.setVerticalGroup(
            JBackgroundPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JBackgroundPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JUpperPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(JBackgroundPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JBackgroundPnlLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JLowerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(235, 235, 235)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JBackgroundPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JBackgroundPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JMonthlyReportSubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMonthlyReportSubmitBtnActionPerformed

        switch (statistic)
        {
            case "AVERAGE CASES":
                int n = 1;
                break;
        }
        
        Draw( );
        PrintStats( );
    }//GEN-LAST:event_JMonthlyReportSubmitBtnActionPerformed

    private void JEndMonthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JEndMonthCBActionPerformed

        endMonth = JEndMonthCB.getSelectedItem().toString();
        endMonthIndex = JEndMonthCB.getSelectedIndex();

        JSelectedMonthsLbl.setText(startMonth + "  THROUGH  " + endMonth);
        JEndMonthCB.removeAllItems();

        for (int j = startMonthIndex; j < finalMonth; j++)
        JEndMonthCB.addItem(months[j]);
    }//GEN-LAST:event_JEndMonthCBActionPerformed

    private void JStartMonthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JStartMonthCBActionPerformed

        startMonth = JStartMonthCB.getSelectedItem().toString();
        startMonthIndex = JStartMonthCB.getSelectedIndex();

        if (startMonthIndex > 0)
        {
            for (int j = 1 ; j < JEndMonthCB.getItemCount(); j++)
            JEndMonthCB.removeAllItems();
            for (int j = startMonthIndex - 1 ; j < finalMonth; j++)
            JEndMonthCB.addItem(months[j]);
        }

    }//GEN-LAST:event_JStartMonthCBActionPerformed

    private void JAvgDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAvgDeathsRBActionPerformed

        setEnabled(true);
        statistic = "AVERAGE DEATHS";
        repaint();
    }//GEN-LAST:event_JAvgDeathsRBActionPerformed

    private void JTotalCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalCasesRBActionPerformed

        setEnabled(true);
        statistic = "TOTAL CASES";
        repaint();
    }//GEN-LAST:event_JTotalCasesRBActionPerformed

    private void JAvgCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAvgCasesRBActionPerformed

        setEnabled(true);
        statistic = "AVERAGE CASES";
        repaint();
    }//GEN-LAST:event_JAvgCasesRBActionPerformed

    private void JTotalDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalDeathsRBActionPerformed

        setEnabled(true);
        statistic = "TOTAL DEATHS";
        repaint();
    }//GEN-LAST:event_JTotalDeathsRBActionPerformed

    
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
            java.util.logging.Logger.getLogger(JMonthlyReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JMonthlyReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JMonthlyReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMonthlyReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            JMonthlyReports monthlyReports = new JMonthlyReports( );
            public void run()
                    
            {
                monthlyReports.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton JAvgCasesRB;
    private javax.swing.JRadioButton JAvgDeathsRB;
    private javax.swing.JPanel JBackgroundPnl;
    private javax.swing.ButtonGroup JButtonGroup;
    private javax.swing.JComboBox JEndMonthCB;
    private javax.swing.JPanel JLowerPnl;
    private javax.swing.JScrollPane JLowerPnlSP;
    private javax.swing.JTextArea JLowerPnlTA;
    private javax.swing.JButton JMonthlyReportResetBtn;
    private javax.swing.JButton JMonthlyReportSubmitBtn;
    private javax.swing.JPanel JMonthsPnl;
    private javax.swing.JLabel JSelectedMonthsLbl;
    private javax.swing.JComboBox JStartMonthCB;
    private javax.swing.JRadioButton JTotalCasesRB;
    private javax.swing.JRadioButton JTotalDeathsRB;
    private javax.swing.JPanel JUpperPnl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
