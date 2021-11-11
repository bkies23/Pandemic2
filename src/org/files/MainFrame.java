
package org.files;

import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.JFreeChart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// Author Brian Kies

public class MainFrame extends javax.swing.JFrame 
{
    GetConnection newConnection;
    GetResultSet grs;
    ResultSet rs;
    PreparedStatement stmt;
    Date newDate, dateToModify, dateToDelete, dateToInsert;
    String newCases, newDeaths, casesToModify, deathsToModify, statistic;
    Date startDate, endDate;
    JFrame messageFrame = new JFrame();
    JCharts myChart;
    
    public MainFrame() throws SQLException, IOException 
    {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("U.S. Covid-19 Statistics");
        FetchVariables( );
        
        JChartButtonGroup.add(JTotalCasesRB);
        JChartButtonGroup.add(JNewCasesRB);
        JChartButtonGroup.add(JTotalDeathsRB);
        JChartButtonGroup.add(JNewDeathsRB);
        JChartButtonGroup.add(JChangeInCasesRB);
        JChartButtonGroup.add(JChangeInDeathsRB);
        JChartButtonGroup.add(JDeathPercentRB);
     
        try 
        {
            SaveOriginalTable();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addWindowListener(new WindowAdapter ()
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                try 
                {
                    HandleClosing();
                    
                } 
                catch (SQLException ex)
                {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        RefreshTable( );
    }
    
    private void StoreVariables ()
    {
        try 
        {
            File myObj = new File("C:\\Users\\Brian Kies\\NetBeansProjects\\Pandemic2\\src\\org\\resources\\variables.txt");
            if (myObj.createNewFile()) 
            {
                System.out.println("File created: " + myObj.getName());
            } 
            else 
            {
                System.out.println("File already exists.");
            }
            
            FileWriter myWriter = new FileWriter("C:\\Users\\Brian Kies\\NetBeansProjects\\Pandemic2\\src\\org\\resources\\variables.txt");
 
            String rowDeleted = String.valueOf(Reference.ROW_DELETED);
            myWriter.write(rowDeleted);
            myWriter.write("\n");
            myWriter.close();
        } 
        catch (IOException e)
        {
            System.out.println("An error occurred.");
        }
    }
    
    private void FetchVariables ( ) throws FileNotFoundException, IOException
    {
         File file = new File ("C:\\Users\\Brian Kies\\NetBeansProjects\\Pandemic2\\src\\org\\resources\\variables.txt");
         FileReader myReader = new FileReader(file);
         BufferedReader br = new BufferedReader(myReader);
         String line;
         int i;
         
         line = br.readLine();
         Reference.ROW_DELETED = Boolean.parseBoolean(line);
    }
    
    private void HandleClosing() throws SQLException
    {
        if (HasUnsavedData())
        {
            int answer = ShowWarningMessage();
            
            switch (answer)
            {
                case JOptionPane.YES_OPTION:
                    StoreVariables( );
                    System.exit(0);
                    break;
                   
                case JOptionPane.NO_OPTION:
                    MaintainOriginalTable();
                    System.exit(0);
                    break;
                    
                case JOptionPane.CANCEL_OPTION:
                    break;
            }   
        }
        else
            dispose();    
    }
    
    private boolean HasUnsavedData ()
    {
        if (Reference.IMPORT_FILE == false && Reference.UPDATE_MADE == false)
            return false;
        else
            return true;
    }
    
    private int ShowWarningMessage()
    {
        return  JOptionPane.showConfirmDialog(messageFrame, "The table has been modified. Do you want to save your changes?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION );    
    }
    
    public BigDecimal GetAverage(java.sql.Date startDate, java.sql.Date endDate, int selectedIndex) throws SQLException, ParseException
    {
        Long sum = 0L;
        Integer totalRows, firstRow;
        BigDecimal bdSum = null, bdTotalRows, bdAvg = null;
        
        totalRows = Reference.DifferenceInDates(startDate, endDate);
        firstRow = Reference.FindCurrentRow(startDate);

        firstRow -= 1;
        
        switch(selectedIndex)
        {
            case 1:
                 for ( int i = firstRow; i < firstRow + totalRows; i++)
                 {
                    sum = sum + Integer.parseInt(Covid19table.getValueAt(i, selectedIndex + 2).toString());
                    bdSum = new BigDecimal(sum);
                 }
                    bdTotalRows = new BigDecimal(totalRows);
                    bdAvg = bdSum.divide(bdTotalRows, 2, RoundingMode.UP);
                    return bdAvg;
            case 2:
                 for ( int i = firstRow; i < firstRow + totalRows; i++)
                 {
                    sum = sum + Integer.parseInt(Covid19table.getValueAt(i, selectedIndex + 1).toString());
                    bdSum = new BigDecimal(sum);
                 }
                    return bdSum;
            case 3:
                 for ( int i = firstRow; i < firstRow + totalRows; i++)
                 {
                    sum = sum + Integer.parseInt(Covid19table.getValueAt(i, selectedIndex + 1).toString());
                    bdSum = new BigDecimal(sum);
                 }
                    bdTotalRows = new BigDecimal(totalRows);
                    bdAvg = bdSum.divide(bdTotalRows, 2, RoundingMode.UP);
                    return bdAvg;
            case 4:
                 for ( int i = firstRow; i < firstRow + totalRows; i++)
                 {
                    sum = sum + Integer.parseInt(Covid19table.getValueAt(i, selectedIndex).toString());
                    bdSum = new BigDecimal(sum);
                 }
                    return bdSum;   
        }
       
        return bdAvg;
    }                               
    
    public void RefreshTable ()
    {
        grs = new GetResultSet( );
        DefaultTableCellRenderer renderer;
        // use renderer to center coulumn headers
        renderer = (DefaultTableCellRenderer) Covid19table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.BOTTOM);
        
        try 
        {
            rs = grs.getResultSet();
            Covid19table.setModel(DbUtils.resultSetToTableModel (rs)); 
            rs.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        // use dtcr to center data in each column 
        dtcr.setHorizontalAlignment(SwingConstants.CENTER); 
        dtcr.setVerticalAlignment(SwingConstants.BOTTOM);
        Covid19table.setShowGrid(true);
        TableColumn col;   
        // center the data in each column
        for ( int i = 0; i <Covid19table.getColumnCount(); i++)     
        {                  
            col = Covid19table.getColumnModel().getColumn(i);
            col.setCellRenderer(dtcr);
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        JChartButtonGroup = new javax.swing.ButtonGroup();
        JBackgroundPanel = new javax.swing.JPanel();
        JAddNewDatePnl = new javax.swing.JPanel();
        JNewDateLbl = new javax.swing.JLabel();
        JNewDateChooser = new com.toedter.calendar.JDateChooser();
        JNewCasesLbl = new javax.swing.JLabel();
        JNewCasesLbl1 = new javax.swing.JLabel();
        JNewCasesTF = new javax.swing.JTextField();
        JNewDeathsTF = new javax.swing.JTextField();
        JAddDateBtn = new javax.swing.JButton();
        JInsertDateBtn = new javax.swing.JButton();
        JResetAddBtn = new javax.swing.JButton();
        JTableScrollPane = new javax.swing.JScrollPane();
        Covid19table = new javax.swing.JTable();
        JModifyNewDatePnl = new javax.swing.JPanel();
        JModifyDateLbl = new javax.swing.JLabel();
        JModifyDateChooser = new com.toedter.calendar.JDateChooser();
        JModifyCasesLbl = new javax.swing.JLabel();
        JModifyDeathsLbl = new javax.swing.JLabel();
        JModifyCasesTF = new javax.swing.JTextField();
        JModifyDeathsTF = new javax.swing.JTextField();
        JModifyNewDateBtn = new javax.swing.JButton();
        JDeleteDateBtn = new javax.swing.JButton();
        JResertModifyBtn = new javax.swing.JButton();
        JChartPnl = new javax.swing.JPanel();
        JChartStartDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        JChartEndDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        JTotalCasesRB = new javax.swing.JRadioButton();
        JNewCasesRB = new javax.swing.JRadioButton();
        JChangeInCasesRB = new javax.swing.JRadioButton();
        JTotalDeathsRB = new javax.swing.JRadioButton();
        JNewDeathsRB = new javax.swing.JRadioButton();
        JChangeInDeathsRB = new javax.swing.JRadioButton();
        JDeathPercentRB = new javax.swing.JRadioButton();
        JDrawChartBtn = new javax.swing.JButton();
        JResetChartBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        JMenuFile = new javax.swing.JMenu();
        JMenuItemProgramNotes = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        JMenuItemExportTable = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        JMenuItemImportTable = new javax.swing.JMenuItem();
        JMenuStatistics = new javax.swing.JMenu();
        JMenuSelectedTimeframe = new javax.swing.JMenu();
        JMenuItemAvgCases = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        JMenuItemTotalCases = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        JMenuItemAvgDeaths = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        JMenuItemTotalDeaths = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        JMenuMonthlyData = new javax.swing.JMenu();
        JMenuItemMonthlyAvgCases = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        JMenuItemTotalMonthlyCases = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        JMenuItemAvgMonthlyDeaths = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        JMenuItemMonthlyTotalDeaths = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JBackgroundPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        JAddNewDatePnl.setBackground(new java.awt.Color(153, 153, 153));
        JAddNewDatePnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add or Insert Date", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        JAddNewDatePnl.setForeground(new java.awt.Color(255, 255, 255));
        JAddNewDatePnl.setNextFocusableComponent(JNewDateChooser);
        JAddNewDatePnl.setPreferredSize(new java.awt.Dimension(375, 235));

        JNewDateLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JNewDateLbl.setForeground(new java.awt.Color(255, 255, 255));
        JNewDateLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JNewDateLbl.setText("Date:");
        JNewDateLbl.setPreferredSize(new java.awt.Dimension(35, 20));

        JNewDateChooser.setNextFocusableComponent(JNewCasesTF);
        JNewDateChooser.setPreferredSize(new java.awt.Dimension(135, 25));

        JNewCasesLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JNewCasesLbl.setForeground(new java.awt.Color(255, 255, 255));
        JNewCasesLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JNewCasesLbl.setText("New Cases:");
        JNewCasesLbl.setPreferredSize(new java.awt.Dimension(35, 20));

        JNewCasesLbl1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JNewCasesLbl1.setForeground(new java.awt.Color(255, 255, 255));
        JNewCasesLbl1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JNewCasesLbl1.setText("New Deaths:");
        JNewCasesLbl1.setPreferredSize(new java.awt.Dimension(35, 20));

        JNewCasesTF.setText(" ");
        JNewCasesTF.setNextFocusableComponent(JNewDeathsTF);
        JNewCasesTF.setPreferredSize(new java.awt.Dimension(135, 25));

        JNewDeathsTF.setText(" ");
        JNewDeathsTF.setNextFocusableComponent(JAddDateBtn);
        JNewDeathsTF.setPreferredSize(new java.awt.Dimension(135, 25));

        JAddDateBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JAddDateBtn.setText("Add");
        JAddDateBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JAddDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JAddDateBtnActionPerformed(evt);
            }
        });

        JInsertDateBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JInsertDateBtn.setText("Insert");
        JInsertDateBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JInsertDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JInsertDateBtnActionPerformed(evt);
            }
        });

        JResetAddBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JResetAddBtn.setText("Reset");
        JResetAddBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JResetAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JResetAddBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JAddNewDatePnlLayout = new javax.swing.GroupLayout(JAddNewDatePnl);
        JAddNewDatePnl.setLayout(JAddNewDatePnlLayout);
        JAddNewDatePnlLayout.setHorizontalGroup(
            JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JAddNewDatePnlLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JAddNewDatePnlLayout.createSequentialGroup()
                        .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(JNewCasesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JNewCasesLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(JNewCasesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JNewDeathsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(JAddNewDatePnlLayout.createSequentialGroup()
                        .addComponent(JNewDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JNewDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(JAddNewDatePnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JAddDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(JInsertDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(JResetAddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        JAddNewDatePnlLayout.setVerticalGroup(
            JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JAddNewDatePnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JNewDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JNewDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JNewCasesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JNewCasesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JNewDeathsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JNewCasesLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(JAddNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JInsertDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JResetAddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JAddDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JTableScrollPane.setBackground(new java.awt.Color(0, 153, 153));

        Covid19table.setBackground(new java.awt.Color(0, 153, 153));
        Covid19table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Covid19table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        JTableScrollPane.setViewportView(Covid19table);

        JModifyNewDatePnl.setBackground(new java.awt.Color(102, 102, 102));
        JModifyNewDatePnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modify or Delete Date", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        JModifyNewDatePnl.setForeground(new java.awt.Color(255, 255, 255));
        JModifyNewDatePnl.setPreferredSize(new java.awt.Dimension(375, 235));

        JModifyDateLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JModifyDateLbl.setForeground(new java.awt.Color(255, 255, 255));
        JModifyDateLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JModifyDateLbl.setText("Date:");
        JModifyDateLbl.setPreferredSize(new java.awt.Dimension(80, 20));

        JModifyDateChooser.setPreferredSize(new java.awt.Dimension(135, 25));

        JModifyCasesLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JModifyCasesLbl.setForeground(new java.awt.Color(255, 255, 255));
        JModifyCasesLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JModifyCasesLbl.setText("New Cases:");
        JModifyCasesLbl.setPreferredSize(new java.awt.Dimension(35, 20));

        JModifyDeathsLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JModifyDeathsLbl.setForeground(new java.awt.Color(255, 255, 255));
        JModifyDeathsLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JModifyDeathsLbl.setText("New Deaths:");
        JModifyDeathsLbl.setPreferredSize(new java.awt.Dimension(35, 20));

        JModifyCasesTF.setText(" ");
        JModifyCasesTF.setPreferredSize(new java.awt.Dimension(135, 25));

        JModifyDeathsTF.setText(" ");
        JModifyDeathsTF.setPreferredSize(new java.awt.Dimension(135, 25));

        JModifyNewDateBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JModifyNewDateBtn.setText("Modify");
        JModifyNewDateBtn.setPreferredSize(new java.awt.Dimension(60, 25));
        JModifyNewDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JModifyNewDateBtnActionPerformed(evt);
            }
        });

        JDeleteDateBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JDeleteDateBtn.setText("Delete");
        JDeleteDateBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JDeleteDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JDeleteDateBtnActionPerformed(evt);
            }
        });

        JResertModifyBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JResertModifyBtn.setText("Reset");
        JResertModifyBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JResertModifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JResertModifyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JModifyNewDatePnlLayout = new javax.swing.GroupLayout(JModifyNewDatePnl);
        JModifyNewDatePnl.setLayout(JModifyNewDatePnlLayout);
        JModifyNewDatePnlLayout.setHorizontalGroup(
            JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JModifyNewDatePnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JModifyNewDatePnlLayout.createSequentialGroup()
                        .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JModifyDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JModifyCasesLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JModifyDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JModifyCasesTF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JModifyNewDatePnlLayout.createSequentialGroup()
                        .addComponent(JModifyDeathsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JModifyDeathsTF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(149, 149, 149))
            .addGroup(JModifyNewDatePnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JModifyNewDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(JDeleteDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(JResertModifyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JModifyNewDatePnlLayout.setVerticalGroup(
            JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JModifyNewDatePnlLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JModifyDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JModifyDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JModifyCasesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JModifyCasesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JModifyDeathsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JModifyDeathsLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(JModifyNewDatePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JModifyNewDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JDeleteDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JResertModifyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        JChartPnl.setBackground(new java.awt.Color(153, 153, 153));
        JChartPnl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Charts", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        JChartPnl.setPreferredSize(new java.awt.Dimension(700, 270));

        JChartStartDate.setPreferredSize(new java.awt.Dimension(135, 25));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Start Date:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("End Date:");

        JChartEndDate.setPreferredSize(new java.awt.Dimension(135, 25));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("<html>X-Axis Dates Will Not Be Legible For <br>Longer Timeframes. See Program Notes</html>");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        JChartButtonGroup.add(JTotalCasesRB);
        JTotalCasesRB.setText("Total Cases");
        JTotalCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalCasesRBActionPerformed(evt);
            }
        });

        JChartButtonGroup.add(JNewCasesRB);
        JNewCasesRB.setText("New Cases");
        JNewCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JNewCasesRBActionPerformed(evt);
            }
        });

        JChartButtonGroup.add(JChangeInCasesRB);
        JChangeInCasesRB.setText("Change In Cases");
        JChangeInCasesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JChangeInCasesRBActionPerformed(evt);
            }
        });

        JChartButtonGroup.add(JTotalDeathsRB);
        JTotalDeathsRB.setText("Total Deaths");
        JTotalDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTotalDeathsRBActionPerformed(evt);
            }
        });

        JNewDeathsRB.setText("New Deaths");
        JNewDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JNewDeathsRBActionPerformed(evt);
            }
        });

        JChartButtonGroup.add(JChangeInDeathsRB);
        JChangeInDeathsRB.setText("Change In Deaths");
        JChangeInDeathsRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JChangeInDeathsRBActionPerformed(evt);
            }
        });

        JChartButtonGroup.add(JDeathPercentRB);
        JDeathPercentRB.setText("Death Percent");
        JDeathPercentRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JDeathPercentRBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JChangeInCasesRB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JNewCasesRB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JTotalCasesRB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JDeathPercentRB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JNewDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTotalDeathsRB, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JChangeInDeathsRB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTotalCasesRB)
                    .addComponent(JTotalDeathsRB))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JNewDeathsRB)
                    .addComponent(JNewCasesRB))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JChangeInDeathsRB)
                    .addComponent(JChangeInCasesRB))
                .addGap(18, 18, 18)
                .addComponent(JDeathPercentRB)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JDrawChartBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JDrawChartBtn.setText("Draw Chart");
        JDrawChartBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JDrawChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JDrawChartBtnActionPerformed(evt);
            }
        });

        JResetChartBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JResetChartBtn.setText("Reset");
        JResetChartBtn.setPreferredSize(new java.awt.Dimension(75, 25));
        JResetChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JResetChartBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JChartPnlLayout = new javax.swing.GroupLayout(JChartPnl);
        JChartPnl.setLayout(JChartPnlLayout);
        JChartPnlLayout.setHorizontalGroup(
            JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JChartPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JChartPnlLayout.createSequentialGroup()
                        .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JChartEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JChartStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(JChartPnlLayout.createSequentialGroup()
                        .addComponent(JDrawChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JResetChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        JChartPnlLayout.setVerticalGroup(
            JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JChartPnlLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(JChartStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(JChartEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(JChartPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(JChartPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JDrawChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JResetChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JBackgroundPanelLayout = new javax.swing.GroupLayout(JBackgroundPanel);
        JBackgroundPanel.setLayout(JBackgroundPanelLayout);
        JBackgroundPanelLayout.setHorizontalGroup(
            JBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JTableScrollPane)
                    .addGroup(JBackgroundPanelLayout.createSequentialGroup()
                        .addComponent(JAddNewDatePnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JModifyNewDatePnl, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JChartPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JBackgroundPanelLayout.setVerticalGroup(
            JBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JModifyNewDatePnl, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addComponent(JAddNewDatePnl, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                    .addComponent(JChartPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JMenuFile.setText("File");

        JMenuItemProgramNotes.setText("Program Notes");
        JMenuItemProgramNotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemProgramNotesActionPerformed(evt);
            }
        });
        JMenuFile.add(JMenuItemProgramNotes);
        JMenuFile.add(jSeparator1);

        JMenuItemExportTable.setText("Export Table");
        JMenuItemExportTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemExportTableActionPerformed(evt);
            }
        });
        JMenuFile.add(JMenuItemExportTable);
        JMenuFile.add(jSeparator2);

        JMenuItemImportTable.setText("Import Table");
        JMenuItemImportTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemImportTableActionPerformed(evt);
            }
        });
        JMenuFile.add(JMenuItemImportTable);

        jMenuBar1.add(JMenuFile);

        JMenuStatistics.setText("Statistics");

        JMenuSelectedTimeframe.setText("Select Timeframe");

        JMenuItemAvgCases.setText("Average Cases");
        JMenuItemAvgCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemAvgCasesActionPerformed(evt);
            }
        });
        JMenuSelectedTimeframe.add(JMenuItemAvgCases);
        JMenuSelectedTimeframe.add(jSeparator3);

        JMenuItemTotalCases.setText("Total Cases");
        JMenuItemTotalCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemTotalCasesActionPerformed(evt);
            }
        });
        JMenuSelectedTimeframe.add(JMenuItemTotalCases);
        JMenuSelectedTimeframe.add(jSeparator4);

        JMenuItemAvgDeaths.setText("Average Deaths");
        JMenuItemAvgDeaths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemAvgDeathsActionPerformed(evt);
            }
        });
        JMenuSelectedTimeframe.add(JMenuItemAvgDeaths);
        JMenuSelectedTimeframe.add(jSeparator5);

        JMenuItemTotalDeaths.setText("Total Deaths");
        JMenuItemTotalDeaths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemTotalDeathsActionPerformed(evt);
            }
        });
        JMenuSelectedTimeframe.add(JMenuItemTotalDeaths);

        JMenuStatistics.add(JMenuSelectedTimeframe);
        JMenuStatistics.add(jSeparator6);

        JMenuMonthlyData.setText("View Monthly Data");

        JMenuItemMonthlyAvgCases.setText("Average Cases");
        JMenuItemMonthlyAvgCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemMonthlyAvgCasesActionPerformed(evt);
            }
        });
        JMenuMonthlyData.add(JMenuItemMonthlyAvgCases);
        JMenuMonthlyData.add(jSeparator7);

        JMenuItemTotalMonthlyCases.setText("Total Cases");
        JMenuItemTotalMonthlyCases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemTotalMonthlyCasesActionPerformed(evt);
            }
        });
        JMenuMonthlyData.add(JMenuItemTotalMonthlyCases);
        JMenuMonthlyData.add(jSeparator8);

        JMenuItemAvgMonthlyDeaths.setText("Average Deaths");
        JMenuItemAvgMonthlyDeaths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemAvgMonthlyDeathsActionPerformed(evt);
            }
        });
        JMenuMonthlyData.add(JMenuItemAvgMonthlyDeaths);
        JMenuMonthlyData.add(jSeparator9);

        JMenuItemMonthlyTotalDeaths.setText("Total Deaths");
        JMenuItemMonthlyTotalDeaths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemMonthlyTotalDeathsActionPerformed(evt);
            }
        });
        JMenuMonthlyData.add(JMenuItemMonthlyTotalDeaths);

        JMenuStatistics.add(JMenuMonthlyData);

        jMenuBar1.add(JMenuStatistics);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JBackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JBackgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JAddDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JAddDateBtnActionPerformed
        
        // The AddRow method recieves next succesive date with case and death totals.
        // If not next successive date, User recieves popup dialog stating date cannot be entered.
        // If input valid, method returns TRUE so that RefreshTable() method executes to show updates.
        
        AddRow addRow = new AddRow( );
        
        newDate = JNewDateChooser.getDate();
        newCases = JNewCasesTF.getText().trim();
        newDeaths = JNewDeathsTF.getText().trim();
        
        try 
        {
            if (addRow.AddRow(newDate, newCases, newDeaths, JNewDateChooser))
            {
                RefreshTable( );
                JNewDateChooser.setDate(null);
                JNewCasesTF.setText("");
                JNewDeathsTF.setText("");
            }
        }
        catch (SQLException | ParseException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_JAddDateBtnActionPerformed

    private void JInsertDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JInsertDateBtnActionPerformed
        
        InsertRow insertRow = new InsertRow( );
        Date lastDate = null;
        grs = new GetResultSet( );
        
        try 
        {
            rs = grs.getResultSet();
            rs.last();
            lastDate = rs.getDate(1);
        } 
         catch (SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        dateToInsert = JNewDateChooser.getDate();
        newCases = JNewCasesTF.getText().trim();
        newDeaths = JNewDeathsTF.getText().trim();
   
        try 
        {
            if (insertRow.InsertRow(newCases, newDeaths, JNewDateChooser))
            {
                RefreshTable( );
                Reference.ROW_DELETED = false;
                JNewDateChooser.setDate(null);
                JNewCasesTF.setText("");
                JNewDeathsTF.setText("");
                if (Reference.ROW_DELETED == true && dateToInsert != lastDate)
                    Reference.ROW_DELETED = false;
            }
        } 
        catch (SQLException | ParseException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JInsertDateBtnActionPerformed

    private void JModifyNewDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JModifyNewDateBtnActionPerformed
        
        ModifyRow modifyRow = new ModifyRow();
        
        dateToModify = JModifyDateChooser.getDate();
        casesToModify = JModifyCasesTF.getText().trim();
        deathsToModify = JModifyDeathsTF.getText().trim();
        
        try 
        {
            if (modifyRow.ModifyRow(casesToModify, deathsToModify, JModifyDateChooser, JModifyCasesTF, JModifyDeathsTF))
                RefreshTable( );
        } 
        catch (ParseException | SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_JModifyNewDateBtnActionPerformed

    private void JDeleteDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JDeleteDateBtnActionPerformed
        
        DeleteRow deleteRow = new DeleteRow( );
        dateToDelete = JModifyDateChooser.getDate();
        
        try 
        {
            if (deleteRow.DeleteRow(dateToDelete, JModifyDateChooser))
            {
                RefreshTable( );
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JDeleteDateBtnActionPerformed

    private void JResetAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JResetAddBtnActionPerformed
        
        JNewDateChooser.setDate(null);
        JNewCasesTF.setText(null);
        JNewDeathsTF.setText(null);
    }//GEN-LAST:event_JResetAddBtnActionPerformed

    private void JResertModifyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JResertModifyBtnActionPerformed
        
        JModifyDateChooser.setDate(null);
        JModifyCasesTF.setText(null);
        JModifyDeathsTF.setText(null);
    }//GEN-LAST:event_JResertModifyBtnActionPerformed

    private void JMenuItemImportTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemImportTableActionPerformed
        
        Connection myConn;
        JFileChooser fileChooser = new JFileChooser( );
        String filePath = "";
        String extension = "", sqlOne, sqlTwo;
        BufferedReader lineReader = null;
            
        sqlOne = "DELETE FROM covid19table";
        // set size of dialog box
        Dimension dialogSize = new Dimension(300, 400);
        
        fileChooser.setDialogTitle("Specify File To Import");
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Brian Kies\\NetBeansProjects\\Pandemic2\\src\\org\\resources\\COVID19_DATA.csv"));
        fileChooser.setPreferredSize(dialogSize);
        
        int userSelection = fileChooser.showOpenDialog(this);
        
        if (userSelection == 1)
            return;
        else if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            File importFile = fileChooser.getSelectedFile();
            filePath = importFile.getAbsolutePath();
            try 
            {
                lineReader = new BufferedReader(new FileReader(filePath));
            } 
            catch (FileNotFoundException ex) 
            {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            extension = GetFileExtension(importFile);
            filePath = filePath.replaceAll("\\\\", "//");
            }
            
            if ( ! extension.equals("csv"))
            {
                JOptionPane.showMessageDialog(messageFrame, "Import file must have .csv extension.");
            }
            else
            {
                try 
                {
                    myConn = newConnection.init();
                   // delete data from covid19table
                    stmt = myConn.prepareStatement(sqlOne);
                    stmt.execute();
                    
                    sqlTwo = "LOAD DATA INFILE '" + filePath + "' into table covid19table fields terminated by ',' ignore 1 lines;";
                    stmt = myConn.prepareStatement(sqlTwo);
                    stmt.execute();
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      
                Reference.IMPORT_FILE = true;
                RefreshTable();   
            }                          
    }//GEN-LAST:event_JMenuItemImportTableActionPerformed

    private void JMenuItemProgramNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemProgramNotesActionPerformed
        
        JNotesFrame jNotesFrame = new JNotesFrame( );
        
        jNotesFrame.setVisible(true);
    }//GEN-LAST:event_JMenuItemProgramNotesActionPerformed

    private void JMenuItemAvgCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemAvgCasesActionPerformed
        
        JAveragesFrame avgWindow = new JAveragesFrame(1, this);

        avgWindow.setVisible(true);
    }//GEN-LAST:event_JMenuItemAvgCasesActionPerformed

    private void JMenuItemTotalCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemTotalCasesActionPerformed
        
         JAveragesFrame avgWindow = new JAveragesFrame(2, this);
         
         avgWindow.setVisible(true); 
    }//GEN-LAST:event_JMenuItemTotalCasesActionPerformed

    private void JMenuItemAvgDeathsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemAvgDeathsActionPerformed
        
         JAveragesFrame avgWindow = new JAveragesFrame(3, this);
         
         avgWindow.setVisible(true); 
    }//GEN-LAST:event_JMenuItemAvgDeathsActionPerformed

    private void JMenuItemTotalDeathsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemTotalDeathsActionPerformed
        
        JAveragesFrame avgWindow = new JAveragesFrame(4, this);

        avgWindow.setVisible(true);
    }//GEN-LAST:event_JMenuItemTotalDeathsActionPerformed

    private void JMenuItemMonthlyAvgCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemMonthlyAvgCasesActionPerformed
        
            JMonthlyData monthlyData = null;
            
            try 
            {
                monthlyData = new JMonthlyData("AVERAGE CASES");
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            monthlyData.setVisible(true);  
    }//GEN-LAST:event_JMenuItemMonthlyAvgCasesActionPerformed

    private void JMenuItemTotalMonthlyCasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemTotalMonthlyCasesActionPerformed
        
        JMonthlyData monthlyData = null;
        
        try 
        {
             monthlyData = new JMonthlyData("TOTAL CASES");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        monthlyData.setVisible(true);
    }//GEN-LAST:event_JMenuItemTotalMonthlyCasesActionPerformed

    private void JMenuItemAvgMonthlyDeathsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemAvgMonthlyDeathsActionPerformed
        
        statistic = "AVERAGE DEATHS";
        
        try 
        {
            JMonthlyReports monthlyReports = new JMonthlyReports(statistic);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JMenuItemAvgMonthlyDeathsActionPerformed

    private void JMenuItemMonthlyTotalDeathsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemMonthlyTotalDeathsActionPerformed
        
        statistic = "TOTAL DEATHS";
        try {
            JMonthlyReports monthlyReports = new JMonthlyReports(statistic);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_JMenuItemMonthlyTotalDeathsActionPerformed

    private void JTotalCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalCasesRBActionPerformed
        
        statistic = "TOTAL CASES";
    }//GEN-LAST:event_JTotalCasesRBActionPerformed

    private void JNewCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JNewCasesRBActionPerformed
        
        statistic = "NEW CASES";
    }//GEN-LAST:event_JNewCasesRBActionPerformed

    private void JChangeInCasesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JChangeInCasesRBActionPerformed
        
        statistic = "CHANGE IN CASES";
    }//GEN-LAST:event_JChangeInCasesRBActionPerformed

    private void JTotalDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTotalDeathsRBActionPerformed
        
         statistic = "TOTAL DEATHS";
    }//GEN-LAST:event_JTotalDeathsRBActionPerformed

    private void JNewDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JNewDeathsRBActionPerformed
        
         statistic = "NEW DEATHS";
    }//GEN-LAST:event_JNewDeathsRBActionPerformed

    private void JChangeInDeathsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JChangeInDeathsRBActionPerformed
        
         statistic = "CHANGE IN DEATHS";
    }//GEN-LAST:event_JChangeInDeathsRBActionPerformed

    private void JDeathPercentRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JDeathPercentRBActionPerformed
        
        statistic = "DEATH PERCENT"; 
    }//GEN-LAST:event_JDeathPercentRBActionPerformed

    private void JDrawChartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JDrawChartBtnActionPerformed
       
   
        java.sql.Date startDate = null, endDate = null;
        JFrame messageFrame = new JFrame();
        ResultSet rs = null;
        java.sql.Date firstDate = null, lastDate = null;

        JFreeChart chart = null;

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
        try 
        {
            if (CheckInputData(firstDate, lastDate, JChartStartDate, JChartEndDate))
            {
                if (! JTotalCasesRB.isSelected() && ! JTotalDeathsRB.isSelected() && ! JNewCasesRB.isSelected() && ! JNewDeathsRB.isSelected()
                    && ! JChangeInCasesRB.isSelected() && ! JChangeInDeathsRB.isSelected()  && ! JDeathPercentRB.isSelected() )
                        JOptionPane.showMessageDialog(messageFrame, "Select Category.");
                else
                {
                     startDate = new java.sql.Date (JChartStartDate.getDate().getTime());
                     endDate = new java.sql.Date (JChartEndDate.getDate().getTime());
                     java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                     java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
                     int currentRow = FindCurrentRow(startDate);
                     int numOfRows = DifferenceInDates(startDate, endDate);
                     myChart = new JCharts(sqlStartDate, sqlEndDate);
                     myChart.Draw(currentRow, numOfRows, statistic);
                }
            }
        } 
        catch (ParseException | SQLException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_JDrawChartBtnActionPerformed

    private void JResetChartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JResetChartBtnActionPerformed
        
        JFrame messageFrame = new JFrame ();
        String message1 = "Are you sure you want to reset the data?";
        int  dialogResult = JOptionPane.showConfirmDialog(messageFrame, message1, "Reset Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION)
        {
            JChartStartDate.setCalendar(null);
            JChartEndDate.setCalendar(null);
            JChartButtonGroup.clearSelection();
        }
    }//GEN-LAST:event_JResetChartBtnActionPerformed

    public JTable getJTable ()
    {
        System.out.println("Been in getJTable");
        return this.Covid19table;
    }
    
    
    
    public void exportToExcel(JTable table, String filePath) throws IOException 
    {
        TableModel model = table.getModel();
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        Row row;
        Cell cell;
        Object value;
        Short height;
        String dateStr;
        
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        
        // set height of cells for column headers and row cells
        height = 600;
        // write the column headers
        row = sheet.createRow(0);
        
        // set column header's height
        row.setHeight(height);
       
        for (int c = 0; c < model.getColumnCount(); c++) 
        {
            cell = row.createCell(c);
            cell.setCellStyle(style);
            cell.setCellValue(model.getColumnName(c));  
        }

        height = 500;
        row.setHeight(height);
        // write the data rows
        for (int r = 0; r < model.getRowCount(); r++) 
        {
            row = sheet.createRow(r+1);
            // set height for each row
        //    row.setHeight(height);
            for (int c = 0; c < model.getColumnCount(); c++) 
            {
                cell = row.createCell(c);
                cell.setCellStyle(style); 
          
                value = model.getValueAt(r, c);
                
                if (value instanceof Date)
                {
                    dateStr = dFormat.format(value);
                    cell.setCellValue(dateStr);
                }
                if (value instanceof String) 
                {
                    cell.setCellValue((String)value);
                }
                else if (value instanceof Integer)
                {
                    cell.setCellValue((Integer)value);
                }
            }
        }

        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();
        workbook.close();
 }
    private void JMenuItemExportTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemExportTableActionPerformed
        
        JFileChooser fileChooser = new JFileChooser();
        String filePath = "";
        
        // dialog size
        Dimension dialogSize = new Dimension(300, 400);
        
        fileChooser.setDialogTitle("Specify File To Save"); 
        fileChooser.setPreferredSize(dialogSize);
 
        int userSelection = fileChooser.showSaveDialog(this);
 
        if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            File fileToSave = fileChooser.getSelectedFile();
            filePath = fileToSave.getAbsolutePath();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
        
        JTable table = getJTable();
        
        try
        {
            exportToExcel(table, filePath);
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }//GEN-LAST:event_JMenuItemExportTableActionPerformed

    private String GetFileExtension(File file) 
    {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else 
            return "";
    }
    
    public void MaintainOriginalTable() throws SQLException
    {
        Connection myConn = newConnection.init();
        
        stmt = myConn.prepareStatement("DELETE FROM covid19table");
        stmt.execute();
        stmt = myConn.prepareStatement("INSERT INTO covid19table  SELECT * FROM tempcovidtable");
        stmt.execute();
        myConn.close();
    }
    
    public void SaveOriginalTable() throws SQLException
    {
        try (Connection myConn = newConnection.init()) {
            stmt = myConn.prepareStatement("DELETE FROM tempcovidtable");
            stmt.execute();
            stmt = myConn.prepareStatement("INSERT INTO tempcovidtable SELECT * FROM covid19table");
            stmt.execute();
        }
    }
    
    public boolean CheckInputData (Date firstDate, Date lastDate, JDateChooser startDateChooser, JDateChooser endDateChooser) throws ParseException, SQLException
    {
     //   java.sql.Date startDate, endDate;
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
        else if (! JTotalCasesRB.isSelected() && ! JTotalDeathsRB.isSelected() && ! JNewCasesRB.isSelected() && ! JNewDeathsRB.isSelected()
                 && ! JChangeInCasesRB.isSelected() && ! JChangeInDeathsRB.isSelected()  && ! JDeathPercentRB.isSelected() )
            JOptionPane.showMessageDialog(messageFrame, "Select Category"); 
                      
                    
        else
        {
            startDate = new java.sql.Date (startDateChooser.getDate().getTime());
            String startDateStr = startDate.toString();
            endDate = new java.sql.Date (endDateChooser.getDate().getTime());
            String endDateStr = endDate.toString();
      
        //    if ( numOfRows > 365)   
         //   {
         //       JOptionPane.showMessageDialog(messageFrame, "Timeframe cannot exceed 365 days.");
          //      return false;
        //    }
   
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
            //    endDateChooser.setCalendar(null);
                return false;
            }
        }
        
        return true;
    }
    
      public int FindCurrentRow (Date date) throws ParseException, SQLException
      {
        int row = 1;
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date tempDate;
        PreparedStatement ps;
        Connection myConn = newConnection.init();
        
        java.sql.Date modifiedDate = (java.sql.Date) date;
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateStr = formatter.format(date);
        java.util.Date formattedDate = formatter.parse(currentDateStr);
        
        String dateStr = dFormat.format(date);
        
        if (dateStr.equals("2020-03-01"))
            return row; 
        else 
            
        try 
        {
            ResultSet rs = grs.getResultSet();
      
            rs.first();
            do
            {
                row++;
                rs.next();
                tempDate = rs.getDate(1);
            }
            while (!tempDate.equals(formattedDate));
            
            rs.close();      
        } 
        catch (SQLException ex) 
        {
        }
      
        return row;
    }
      
    public int DifferenceInDates (Date startDate, Date endDate) throws SQLException, ParseException
    {
        ResultSet rs = grs.getResultSet();
        int numOfRows = 1;
        
        int currentRow = FindCurrentRow (startDate);
        rs.absolute(currentRow);
        
        while (startDate.toString().compareTo(endDate.toString()) < 0)
        {
            numOfRows++;
            rs.next();
            startDate = rs.getDate(1);   
        }
         
        rs.close();       
        return numOfRows;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws SQLException, IOException {
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
       {
           MainFrame mf = new MainFrame();
           
            public void run() 
            {
                mf.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Covid19table;
    private javax.swing.JButton JAddDateBtn;
    private javax.swing.JPanel JAddNewDatePnl;
    private javax.swing.JPanel JBackgroundPanel;
    private javax.swing.JRadioButton JChangeInCasesRB;
    private javax.swing.JRadioButton JChangeInDeathsRB;
    private javax.swing.ButtonGroup JChartButtonGroup;
    private com.toedter.calendar.JDateChooser JChartEndDate;
    private javax.swing.JPanel JChartPnl;
    private com.toedter.calendar.JDateChooser JChartStartDate;
    private javax.swing.JRadioButton JDeathPercentRB;
    private javax.swing.JButton JDeleteDateBtn;
    private javax.swing.JButton JDrawChartBtn;
    private javax.swing.JButton JInsertDateBtn;
    private javax.swing.JMenu JMenuFile;
    private javax.swing.JMenuItem JMenuItemAvgCases;
    private javax.swing.JMenuItem JMenuItemAvgDeaths;
    private javax.swing.JMenuItem JMenuItemAvgMonthlyDeaths;
    private javax.swing.JMenuItem JMenuItemExportTable;
    private javax.swing.JMenuItem JMenuItemImportTable;
    private javax.swing.JMenuItem JMenuItemMonthlyAvgCases;
    private javax.swing.JMenuItem JMenuItemMonthlyTotalDeaths;
    private javax.swing.JMenuItem JMenuItemProgramNotes;
    private javax.swing.JMenuItem JMenuItemTotalCases;
    private javax.swing.JMenuItem JMenuItemTotalDeaths;
    private javax.swing.JMenuItem JMenuItemTotalMonthlyCases;
    private javax.swing.JMenu JMenuMonthlyData;
    private javax.swing.JMenu JMenuSelectedTimeframe;
    private javax.swing.JMenu JMenuStatistics;
    private javax.swing.JLabel JModifyCasesLbl;
    private javax.swing.JTextField JModifyCasesTF;
    private com.toedter.calendar.JDateChooser JModifyDateChooser;
    private javax.swing.JLabel JModifyDateLbl;
    private javax.swing.JLabel JModifyDeathsLbl;
    private javax.swing.JTextField JModifyDeathsTF;
    private javax.swing.JButton JModifyNewDateBtn;
    private javax.swing.JPanel JModifyNewDatePnl;
    private javax.swing.JLabel JNewCasesLbl;
    private javax.swing.JLabel JNewCasesLbl1;
    private javax.swing.JRadioButton JNewCasesRB;
    private javax.swing.JTextField JNewCasesTF;
    private com.toedter.calendar.JDateChooser JNewDateChooser;
    private javax.swing.JLabel JNewDateLbl;
    private javax.swing.JRadioButton JNewDeathsRB;
    private javax.swing.JTextField JNewDeathsTF;
    private javax.swing.JButton JResertModifyBtn;
    private javax.swing.JButton JResetAddBtn;
    private javax.swing.JButton JResetChartBtn;
    private javax.swing.JScrollPane JTableScrollPane;
    private javax.swing.JRadioButton JTotalCasesRB;
    private javax.swing.JRadioButton JTotalDeathsRB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    // End of variables declaration//GEN-END:variables
}
