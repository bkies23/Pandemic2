
package org.files;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.*;

// Author Brian Kies

public class JMonthlyData extends JFrame 
{
    Color royalBlue = new Color(0, 102, 153);
    String [] months = new String [] { "MARCH 2020", "APRIL 2020", "MAY 2020", "JUNE 2020", "JULY 2020", "AUGUST 2020", "SEPTEMBER 2020", "OCTOBER 2020", "NOVEMBER 2020", 
                                       "DECEMBER 2020", "JANUARY 2021", "FEBRUARY 2021", "MARCH 2021", "APRIL 2021", "MAY 2021", "JUNE 2021", "JULY 2021", "AUGUST 2021", 
                                       "SEPTEMBER 2021", "OCTOBER 2021", "NOVEMBER 2021", "DECEMBER 2021", "JANUARY 2022", "FEBRUARY 2022", "MARCH 2022", "APRIL 2022", 
                                       "MAY 2022", "JUNE 2022", "JULY 2022", "AUGUST 2022", "SEPTEMBER 2022" };
    ArrayList <Object> mnthAvgCases = new ArrayList <>(); 
    ArrayList <Object> mnthTtlCases = new ArrayList <>();
    ArrayList <Object> mnthAvgDeaths = new ArrayList <>(); 
    ArrayList <Object> mnthTtlDeaths = new ArrayList <>();
    String statistic, startMonth, endMonth;
    int finalMonth, startMonthIndex, endMonthIndex;
    
    LocalDate ld = LocalDate.now();
    
    public JMonthlyData( )
    {
           
    }
    
    public JMonthlyData(String stat) throws SQLException 
    {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(royalBlue);
        statistic = stat;
        
    //    JStartMonthCB.setMaximumRowCount(7);
    //    JEndMonthCB.setMaximumRowCount(7);
        
        JLowerPnlSP.setViewportView(JLowerPnlTA);
    //    JLowerPnl.add(JLowerPnlSP);
    //    JLowerPnlSP.setPreferredSize(new Dimension (890, 1000));
        
        ComputeMonthlyCases( );
        ComputeMonthlyDeaths( );
         // retrieve current month to set previous month as final month User can view 
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
           //     statistic = " AVERAGE CASES";
                break;
            case "TOTAL CASES":
                JTotalCasesRB.setSelected(true);
           //     statistic = " TOTAL CASES";
                break;
            case "AVERAGE DEATHS":
                JAvgDeathsRB.setSelected(true);
            //    statistic = " AVERAGE DEATHS";
                break;
            case "TOTAL DEATHS":
                JTotalDeathsRB.setSelected(true);
            //    statistic = " TOTAL DEATHS";
                break;  
        }
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
    
    public void ComputeMonthlyCases( ) throws SQLException
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
    
     public void ComputeMonthlyDeaths( ) throws SQLException
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
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // April 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 30));
        mnthTtlDeaths.add(sum);
        
        // May 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // June 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 30));
        mnthTtlDeaths.add(sum);
        
        // July 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // August 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // September 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 30));
        mnthTtlDeaths.add(sum);
        
        // October 2020
         sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // November 2020
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 30));
        mnthTtlDeaths.add(sum);
                
       // December 2020
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // January 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // February 2021
        sum = 0;
        for ( int i = 1; i <= 28; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 28));
        mnthTtlDeaths.add(sum);
        
        // March 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // April 2021
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 30));
        mnthTtlDeaths.add(sum);
        
        // May 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // June 2021
        sum = 0;
        for ( int i = 1; i <= 30; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 30));
        mnthTtlDeaths.add(sum);
        
        // July 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
        // August 2021
        sum = 0;
        for ( int i = 1; i <= 31; i++)
        {
            sum += rs.getInt(5);
            rs.next();
        }
        
        mnthAvgDeaths.add(df.format((float)sum / 31));
        mnthTtlDeaths.add(sum);
        
    }
    
    public void Draw ( )
    { 
        Graphics g = JLowerPnl.getGraphics();
        Font myFont = new Font ("Times New Roman", Font.BOLD, 18);
        g.setFont(myFont);
        g.setColor(Color.white);
        
        System.out.println("Been In Draw( )");
         
        String monthsStr = "MONTH(S)"; 
        AttributedString as = new AttributedString(monthsStr);
        as.addAttribute(TextAttribute.FONT, myFont);
        as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 8);
        g.drawString(as.getIterator(), 180, 25);
        
        as = new AttributedString(statistic);
        as.addAttribute(TextAttribute.FONT, myFont);
        switch(statistic)
        {
            case "AVERAGE CASES":
                JAvgCasesRB.setSelected(true);
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 13);
                break;
            case "TOTAL CASES":
                JTotalCasesRB.setSelected(true);
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 11);
                break;
            case "AVERAGE DEATHS":
                JAvgDeathsRB.setSelected(true);
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 14);
                break;
            case "TOTAL DEATHS":
                JTotalDeathsRB.setSelected(true);
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, 12);
                break;        
        }
        
        g.drawString(as.getIterator(), 500, 25);                  
    }
    
    public void DrawLowerPnl ( )
    {
        Draw( );
        PrintStats( );
    }
    
    public void PrintStats ()
    {
        Graphics g1 = JLowerPnlTA.getGraphics();
        Graphics g2 = JLowerPnlTA.getGraphics();
   
        Font myFont = new Font ("Times New Roman", Font.PLAIN, 18);
        Font lineFont = new Font("Times New Roman", Font.PLAIN, 10);
        
        int width = JLowerPnlTA.getWidth();
        
        g1.setFont(myFont);
        g2.setColor(royalBlue);
        g2.setFont(lineFont);
 
        int x1 = 150;
        int x2 = 500;
        int y = 40;
        int verticalSpacing = 30;
       
        if ( startMonthIndex > 0)
        {
            switch (statistic)
            {
                case "AVERAGE CASES":
                     for (int i = startMonthIndex - 1; i < endMonthIndex; i++)
                     {
                        g1.drawString(months[i], x1, y);
                        g1.drawString(mnthAvgCases.get(i).toString(), x2, y);
                        g2.drawLine(0, y, width, y);
                        y += verticalSpacing;
                      }
                break;
                    
                case "TOTAL CASES":
                     for (int i = startMonthIndex - 1; i < endMonthIndex; i++)
                     {
                        g1.drawString(months[i], x1, y);
                        g1.drawString(mnthTtlCases.get(i).toString(), x2, y);
                        g2.drawLine(0, y, width, y);
                        y += verticalSpacing;
                      }
                break;
                    
                case "AVERAGE DEATHS":
                     for (int i = startMonthIndex - 1; i < endMonthIndex; i++)
                     {
                        g1.drawString(months[i], x1, y);
                        g1.drawString(mnthAvgDeaths.get(i).toString(), x2, y);
                        g2.drawLine(0, y, width, y);
                        y += verticalSpacing;
                     }
                     break;
                    
                case "TOTAL DEATHS":
                     for (int i = startMonthIndex - 1; i < endMonthIndex; i++)
                     {
                        g1.drawString(months[i], x1, y);
                        g1.drawString(mnthTtlDeaths.get(i).toString(), x2, y);
                        g2.drawLine(0, y, width, y);
                        y += verticalSpacing;
                    }   
                     break;      
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        JUpperPnl = new javax.swing.JPanel();
        JStatisticPnl = new javax.swing.JPanel();
        JTotalDeathsRB = new javax.swing.JRadioButton();
        JAvgCasesRB = new javax.swing.JRadioButton();
        JTotalCasesRB = new javax.swing.JRadioButton();
        JAvgDeathsRB = new javax.swing.JRadioButton();
        JMonthPnl = new javax.swing.JPanel();
        JStartMonthCB = new javax.swing.JComboBox();
        JEndMonthCB = new javax.swing.JComboBox();
        JSelectedMonthsLbl = new javax.swing.JLabel();
        JMonthlyReportSubmitBtn = new javax.swing.JButton();
        JMonthlyReportResetBtn = new javax.swing.JButton();
        JLowerPnl = new javax.swing.JPanel();
        JLowerPnlSP = new javax.swing.JScrollPane();
        JLowerPnlTA = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Monthly Data");
        setPreferredSize(new java.awt.Dimension(860, 800));

        JUpperPnl.setBackground(new java.awt.Color(255, 204, 153));
        JUpperPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Monthly Statistics", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        JUpperPnl.setPreferredSize(new java.awt.Dimension(870, 220));

        JStatisticPnl.setBackground(new java.awt.Color(204, 204, 204));
        JStatisticPnl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        JTotalDeathsRB.setBackground(new java.awt.Color(204, 204, 204));
        buttonGroup1.add(JTotalDeathsRB);
        JTotalDeathsRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTotalDeathsRB.setText("Total Deaths");
        JTotalDeathsRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JTotalDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalDeathsRBActionPerformed(evt);
            }
        });

        JAvgCasesRB.setBackground(new java.awt.Color(204, 204, 204));
        buttonGroup1.add(JAvgCasesRB);
        JAvgCasesRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAvgCasesRB.setText("Average Cases");
        JAvgCasesRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JAvgCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAvgCasesRBActionPerformed(evt);
            }
        });

        JTotalCasesRB.setBackground(new java.awt.Color(204, 204, 204));
        buttonGroup1.add(JTotalCasesRB);
        JTotalCasesRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JTotalCasesRB.setText("Total Cases");
        JTotalCasesRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JTotalCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalCasesRBActionPerformed(evt);
            }
        });

        JAvgDeathsRB.setBackground(new java.awt.Color(204, 204, 204));
        buttonGroup1.add(JAvgDeathsRB);
        JAvgDeathsRB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAvgDeathsRB.setText("Average Deaths");
        JAvgDeathsRB.setPreferredSize(new java.awt.Dimension(140, 25));
        JAvgDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAvgDeathsRBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JStatisticPnlLayout = new javax.swing.GroupLayout(JStatisticPnl);
        JStatisticPnl.setLayout(JStatisticPnlLayout);
        JStatisticPnlLayout.setHorizontalGroup(
            JStatisticPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JStatisticPnlLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(JStatisticPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JStatisticPnlLayout.createSequentialGroup()
                        .addComponent(JTotalCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JAvgDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JStatisticPnlLayout.createSequentialGroup()
                        .addComponent(JAvgCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JTotalDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JStatisticPnlLayout.setVerticalGroup(
            JStatisticPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JStatisticPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JStatisticPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTotalDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JAvgCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(JStatisticPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTotalCasesRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JAvgDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        JMonthPnl.setBackground(new java.awt.Color(204, 204, 204));
        JMonthPnl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        JStartMonthCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JStartMonthCB.setMaximumRowCount(12);
        JStartMonthCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Starting Month" }));
        JStartMonthCB.setPreferredSize(new java.awt.Dimension(160, 25));
        JStartMonthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JStartMonthCBActionPerformed(evt);
            }
        });

        JEndMonthCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JEndMonthCB.setMaximumRowCount(12);
        JEndMonthCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Ending Month" }));
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

        javax.swing.GroupLayout JMonthPnlLayout = new javax.swing.GroupLayout(JMonthPnl);
        JMonthPnl.setLayout(JMonthPnlLayout);
        JMonthPnlLayout.setHorizontalGroup(
            JMonthPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JMonthPnlLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(JStartMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JEndMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JMonthPnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JSelectedMonthsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        JMonthPnlLayout.setVerticalGroup(
            JMonthPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JMonthPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JMonthPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JStartMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JEndMonthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(JSelectedMonthsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout JUpperPnlLayout = new javax.swing.GroupLayout(JUpperPnl);
        JUpperPnl.setLayout(JUpperPnlLayout);
        JUpperPnlLayout.setHorizontalGroup(
            JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JUpperPnlLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(JMonthPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(JUpperPnlLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JMonthlyReportSubmitBtn)
                        .addGap(102, 102, 102)
                        .addComponent(JMonthlyReportResetBtn)
                        .addGap(29, 29, 29))
                    .addGroup(JUpperPnlLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JStatisticPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        JUpperPnlLayout.setVerticalGroup(
            JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JUpperPnlLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JMonthPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JStatisticPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JUpperPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JMonthlyReportSubmitBtn)
                    .addComponent(JMonthlyReportResetBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JLowerPnl.setBackground(new java.awt.Color(0, 102, 153));
        JLowerPnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 153), 5));
        JLowerPnl.setPreferredSize(new java.awt.Dimension(700, 400));

        JLowerPnlSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLowerPnlTA.setEditable(false);
        JLowerPnlTA.setColumns(20);
        JLowerPnlTA.setRows(20);
        JLowerPnlSP.setViewportView(JLowerPnlTA);

        javax.swing.GroupLayout JLowerPnlLayout = new javax.swing.GroupLayout(JLowerPnl);
        JLowerPnl.setLayout(JLowerPnlLayout);
        JLowerPnlLayout.setHorizontalGroup(
            JLowerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JLowerPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JLowerPnlSP, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addContainerGap())
        );
        JLowerPnlLayout.setVerticalGroup(
            JLowerPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JLowerPnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JLowerPnlSP, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JUpperPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLowerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JUpperPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JLowerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTotalDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalDeathsRBActionPerformed

        JLowerPnl.revalidate();
        JLowerPnl.repaint();
        setEnabled(true);
        statistic = "TOTAL DEATHS";
        Draw( );
        PrintStats( );
    }//GEN-LAST:event_JTotalDeathsRBActionPerformed

    private void JAvgCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAvgCasesRBActionPerformed

        JLowerPnl.revalidate();
        JLowerPnl.repaint();
        setEnabled(true);
        statistic = "AVERAGE CASES";
        Draw( );
        PrintStats( );
    }//GEN-LAST:event_JAvgCasesRBActionPerformed

    private void JTotalCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalCasesRBActionPerformed

        JLowerPnl.revalidate();
        JLowerPnl.repaint();
        setEnabled(true);
        statistic = "TOTAL CASES";
        Draw( );
        PrintStats( );
    }//GEN-LAST:event_JTotalCasesRBActionPerformed

    private void JAvgDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAvgDeathsRBActionPerformed

        JLowerPnl.repaint();
        setEnabled(true);
        statistic = "AVERAGE DEATHS";
        DrawLowerPnl( );
        Draw( );
        PrintStats( );
    }//GEN-LAST:event_JAvgDeathsRBActionPerformed

    private void JStartMonthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JStartMonthCBActionPerformed

        startMonth = JStartMonthCB.getSelectedItem().toString();
        startMonthIndex = JStartMonthCB.getSelectedIndex();

        if (startMonthIndex > 0)
        {
            for (int j = 1 ; j < JEndMonthCB.getItemCount(); j++)
                JEndMonthCB.remove(j);
            for (int j = startMonthIndex - 1; j < finalMonth; j++)
                JEndMonthCB.addItem(months[j]);
        }
    }//GEN-LAST:event_JStartMonthCBActionPerformed

    private void JEndMonthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JEndMonthCBActionPerformed

        endMonth = JEndMonthCB.getSelectedItem().toString();
        endMonthIndex = JEndMonthCB.getSelectedIndex();

        JSelectedMonthsLbl.setText(startMonth + "  THROUGH  " + endMonth);
        JEndMonthCB.removeAllItems();

        for (int j = endMonthIndex - 1; j < finalMonth; j++)
            JEndMonthCB.addItem(months[j]);
    }//GEN-LAST:event_JEndMonthCBActionPerformed

    private void JMonthlyReportSubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMonthlyReportSubmitBtnActionPerformed

        Draw();
        PrintStats( );
    }//GEN-LAST:event_JMonthlyReportSubmitBtnActionPerformed

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
            java.util.logging.Logger.getLogger(JMonthlyData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JMonthlyData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JMonthlyData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMonthlyData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JMonthlyData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton JAvgCasesRB;
    private javax.swing.JRadioButton JAvgDeathsRB;
    private javax.swing.JComboBox JEndMonthCB;
    private javax.swing.JPanel JLowerPnl;
    private javax.swing.JScrollPane JLowerPnlSP;
    private javax.swing.JTextArea JLowerPnlTA;
    private javax.swing.JPanel JMonthPnl;
    private javax.swing.JButton JMonthlyReportResetBtn;
    private javax.swing.JButton JMonthlyReportSubmitBtn;
    private javax.swing.JLabel JSelectedMonthsLbl;
    private javax.swing.JComboBox JStartMonthCB;
    private javax.swing.JPanel JStatisticPnl;
    private javax.swing.JRadioButton JTotalCasesRB;
    private javax.swing.JRadioButton JTotalDeathsRB;
    private javax.swing.JPanel JUpperPnl;
    private javax.swing.ButtonGroup buttonGroup1;
    // End of variables declaration//GEN-END:variables
}
