/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import mitosv0.RealMachine;
import mitosv0.VirtualMachine;
import mitosv0.Word;

/**
 *
 * @author Tomas
 */
public class RealMachineGUI extends javax.swing.JFrame {

    /**
     * Creates new form RealMachineGUI
     */
    
    RealMachine RM;
    JTable vm1MemoryTable;
    
    //Reikalinga nusakyti lenteles reiksmiu tipui
    TableDataTypes tableDataType = TableDataTypes.Hex;
    public enum TableDataTypes {
        Int,
        Hex,
        Char
    }
    public TableDataTypes getTableDataType(){
        return tableDataType;
    }
    
    public RealMachineGUI(RealMachine RM) {
        super("Real machine");
        initComponents();
        this.RM = RM;
        this.setVisible(true);
       // STextField.setFont(STextField.new);
        
        updateRegisterFields();  
        updateFlagInfo();
        //Norim, kad adreso stulpelio dydis nesikeistu - vietos butu tiek, kiek uztenka
        memoryTable.getColumnModel().getColumn(0).setMaxWidth(32);
        memoryTable.getColumnModel().getColumn(0).setMinWidth(32);  

        MemoryTableRenderer cr=new MemoryTableRenderer();
        MemoryTableFirstColumnRenderer cfr = new MemoryTableFirstColumnRenderer(RM);
        memoryTable.getColumn(memoryTable.getColumnName(0)).setCellRenderer(cfr);
        for (int i=1;i < memoryTable.getColumnCount(); i++)
        {
            memoryTable.getColumn(memoryTable.getColumnName(i)).setCellRenderer(cr);
        }
    }
    
    private void createVMTab()
    {
        vm1MemoryTable = new JTable();
        vm1MemoryTable.setModel(new VirtualMemoryTableModel(RM,this));
        vm1MemoryTable.setFillsViewportHeight(true);
        memoryTabbedPane.add("VM1", new JScrollPane(vm1MemoryTable));
        vm1MemoryTable.setColumnSelectionAllowed(true);
        vm1MemoryTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        vm1MemoryTable.getColumnModel().getColumn(0).setMaxWidth(32);
        vm1MemoryTable.getColumnModel().getColumn(0).setMinWidth(32);
        
        MemoryTableRenderer cr=new MemoryTableRenderer();
        MemoryTableFirstColumnRenderer cfr = new MemoryTableFirstColumnRenderer(RM);
        memoryTable.getColumn(memoryTable.getColumnName(0)).setCellRenderer(cfr);
        for (int i=1;i < memoryTable.getColumnCount(); i++)
        {
            vm1MemoryTable.getColumn(memoryTable.getColumnName(i)).setCellRenderer(cr);
        }
    }
    
    public void updateAll()
    {
        updateRegisterFields();
        updateFlagInfo();
        this.paint(this.getGraphics());
        memoryTable.repaint();
        if (RealMachine.VM != null)
            vm1MemoryTable.repaint();
    }
        
    private void updateRegisterFields()
    {
        repaint();
        PLRTextField.setText(RM.PLR.getValue());
        R1TextField.setText(RM.R1.getValue().getValue().replaceFirst("^0+(?!$)", ""));
        R2TextField.setText(RM.R2.getValue().getValue().replaceFirst("^0+(?!$)", ""));
        ICTextField.setText(Integer.toHexString(RM.IC.getValue()));
        CTextField.setText(Integer.toHexString(RM.C.getValue()));
        
        String tmp = Integer.toBinaryString(RM.S.getValue());
        while (tmp.length() < 16)
            tmp = "0"+tmp;
        tmp = tmp.substring(tmp.length()-16, tmp.length());
        STextField.setText(tmp);
        
        TimerTextField.setText(Integer.toHexString(RM.timer.getValue()));
        
        MODEToggleButton.setText(modeToString(RealMachine.mode.isSupervisor()));
        MODEToggleButton.setSelected(RealMachine.mode.isSupervisor());
        
        PITextField.setText(Integer.toHexString(RM.PI.getValue()));
        SITextField.setText(Integer.toString(RM.SI.getValue()));
        
        CH1ToggleButton.setText(CHStateToString(RM.CH1.isOpen()));
        CH1ToggleButton.setSelected(RM.CH1.isOpen());
        CH2ToggleButton.setText(CHStateToString(RM.CH2.isOpen()));
        CH2ToggleButton.setSelected(RM.CH2.isOpen());
        CH3ToggleButton.setText(CHStateToString(RM.CH3.isOpen()));
        CH3ToggleButton.setSelected(RM.CH3.isOpen());
        CH4ToggleButton.setText(CHStateToString(RM.CH4.isOpen()));
        CH4ToggleButton.setSelected(RM.CH4.isOpen());
    }
    
    
    private void updateFlagInfo()
    {
        ZFCheckBox.setSelected(RM.C.isZeroFlagSet());
        SFCheckBox.setSelected(RM.C.isSignFlagSet());
        OFCheckBox.setSelected(RM.C.isOverflowFlagSet());
    }
    
    private String CHStateToString(boolean state)
    {
        if (state)
            return new String ("Open");
        else 
            return new String ("Closed");
    }

    private String modeToString(boolean mode)
    {
        if (mode)
            return new String ("Superv.");
        else 
            return new String ("User");
    }
    
    public void showMessage(String text)
    {
        JOptionPane.showMessageDialog(this, text);
    }
    
    public String showInputMessageBox(String message)
    {
        return JOptionPane.showInputDialog(this, message);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableDataTypeButtonGroup = new javax.swing.ButtonGroup();
        operationsPanel = new javax.swing.JPanel();
        registerPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PLRTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        R1TextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        R2TextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ICTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        STextField = new javax.swing.JTextField();
        registerPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        TimerTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        MODEToggleButton = new javax.swing.JToggleButton();
        jLabel9 = new javax.swing.JLabel();
        PITextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        SITextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        CH1ToggleButton = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();
        CH2ToggleButton = new javax.swing.JToggleButton();
        jLabel13 = new javax.swing.JLabel();
        CH3ToggleButton = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        CH4ToggleButton = new javax.swing.JToggleButton();
        statusFlagPanel = new javax.swing.JPanel();
        ZFCheckBox = new javax.swing.JCheckBox();
        SFCheckBox = new javax.swing.JCheckBox();
        OFCheckBox = new javax.swing.JCheckBox();
        buttonPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        TaskNameField = new javax.swing.JTextField();
        TaskButton = new javax.swing.JButton();
        memoryTabbedPane = new javax.swing.JTabbedPane();
        memoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoryTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        operationsPanel.setMaximumSize(new java.awt.Dimension(160, 450));
        operationsPanel.setPreferredSize(new java.awt.Dimension(160, 400));
        operationsPanel.setLayout(new javax.swing.BoxLayout(operationsPanel, javax.swing.BoxLayout.PAGE_AXIS));

        registerPanel1.setMaximumSize(new java.awt.Dimension(160, 420));
        registerPanel1.setPreferredSize(new java.awt.Dimension(160, 400));
        registerPanel1.setLayout(new java.awt.GridLayout(0, 2, 2, 2));

        jLabel1.setText("PLR");
        registerPanel1.add(jLabel1);

        PLRTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLRTextFieldActionPerformed(evt);
            }
        });
        registerPanel1.add(PLRTextField);

        jLabel2.setText("R1");
        registerPanel1.add(jLabel2);

        R1TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R1TextFieldActionPerformed(evt);
            }
        });
        registerPanel1.add(R1TextField);

        jLabel3.setText("R2");
        registerPanel1.add(jLabel3);

        R2TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R2TextFieldActionPerformed(evt);
            }
        });
        registerPanel1.add(R2TextField);

        jLabel4.setText("IC");
        registerPanel1.add(jLabel4);

        ICTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICTextFieldActionPerformed(evt);
            }
        });
        registerPanel1.add(ICTextField);

        jLabel5.setText("C");
        registerPanel1.add(jLabel5);

        CTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTextFieldActionPerformed(evt);
            }
        });
        registerPanel1.add(CTextField);

        jLabel6.setText("S");
        registerPanel1.add(jLabel6);

        STextField.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        STextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                STextFieldActionPerformed(evt);
            }
        });
        registerPanel1.add(STextField);

        operationsPanel.add(registerPanel1);

        registerPanel2.setMaximumSize(new java.awt.Dimension(160, 420));
        registerPanel2.setPreferredSize(new java.awt.Dimension(160, 400));
        registerPanel2.setLayout(new java.awt.GridLayout(0, 2, 2, 2));

        jLabel7.setText("TIMER");
        registerPanel2.add(jLabel7);

        TimerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimerTextFieldActionPerformed(evt);
            }
        });
        registerPanel2.add(TimerTextField);

        jLabel8.setText("MODE");
        registerPanel2.add(jLabel8);

        MODEToggleButton.setText("User");
        MODEToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MODEToggleButtonActionPerformed(evt);
            }
        });
        registerPanel2.add(MODEToggleButton);

        jLabel9.setText("PI");
        registerPanel2.add(jLabel9);

        PITextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PITextFieldActionPerformed(evt);
            }
        });
        registerPanel2.add(PITextField);

        jLabel10.setText("SI");
        registerPanel2.add(jLabel10);

        SITextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SITextFieldActionPerformed(evt);
            }
        });
        registerPanel2.add(SITextField);

        jLabel11.setText("CH1");
        registerPanel2.add(jLabel11);

        CH1ToggleButton.setText("Open");
        CH1ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH1ToggleButtonActionPerformed(evt);
            }
        });
        registerPanel2.add(CH1ToggleButton);

        jLabel12.setText("CH2");
        registerPanel2.add(jLabel12);

        CH2ToggleButton.setText("Open");
        CH2ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH2ToggleButtonActionPerformed(evt);
            }
        });
        registerPanel2.add(CH2ToggleButton);

        jLabel13.setText("CH3");
        registerPanel2.add(jLabel13);

        CH3ToggleButton.setText("Open");
        CH3ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH3ToggleButtonActionPerformed(evt);
            }
        });
        registerPanel2.add(CH3ToggleButton);

        jLabel14.setText("CH4");
        registerPanel2.add(jLabel14);

        CH4ToggleButton.setText("Open");
        CH4ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH4ToggleButtonActionPerformed(evt);
            }
        });
        registerPanel2.add(CH4ToggleButton);

        operationsPanel.add(registerPanel2);

        statusFlagPanel.setMaximumSize(new java.awt.Dimension(160, 420));
        statusFlagPanel.setPreferredSize(new java.awt.Dimension(160, 400));
        statusFlagPanel.setLayout(new javax.swing.BoxLayout(statusFlagPanel, javax.swing.BoxLayout.LINE_AXIS));

        ZFCheckBox.setText("ZF");
        ZFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZFCheckBoxActionPerformed(evt);
            }
        });
        statusFlagPanel.add(ZFCheckBox);

        SFCheckBox.setText("SF");
        SFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SFCheckBoxActionPerformed(evt);
            }
        });
        statusFlagPanel.add(SFCheckBox);

        OFCheckBox.setText("OF");
        OFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OFCheckBoxActionPerformed(evt);
            }
        });
        statusFlagPanel.add(OFCheckBox);

        operationsPanel.add(statusFlagPanel);

        buttonPanel.setMaximumSize(new java.awt.Dimension(160, 420));
        buttonPanel.setPreferredSize(new java.awt.Dimension(160, 400));
        buttonPanel.setLayout(new java.awt.GridLayout(1, 0));

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(runButton);

        stepButton.setText("Step");
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(stepButton);

        operationsPanel.add(buttonPanel);

        jPanel1.setLayout(new java.awt.BorderLayout());

        TaskNameField.setText("Name of Task");
        jPanel1.add(TaskNameField, java.awt.BorderLayout.CENTER);

        TaskButton.setText("New Task");
        TaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TaskButtonActionPerformed(evt);
            }
        });
        jPanel1.add(TaskButton, java.awt.BorderLayout.PAGE_END);

        operationsPanel.add(jPanel1);

        getContentPane().add(operationsPanel);

        memoryPanel.setLayout(new javax.swing.BoxLayout(memoryPanel, javax.swing.BoxLayout.LINE_AXIS));

        memoryTable.setModel(new MemoryTableModel(RM, this));
        memoryTable.setColumnSelectionAllowed(true);
        memoryTable.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                memoryTableInputMethodTextChanged(evt);
            }
        });
        jScrollPane1.setViewportView(memoryTable);
        memoryTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        memoryPanel.add(jScrollPane1);

        memoryTabbedPane.addTab("RM", memoryPanel);

        getContentPane().add(memoryTabbedPane);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
        RM.VM.step();
        updateAll();
    }//GEN-LAST:event_stepButtonActionPerformed

    private void memoryTableInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_memoryTableInputMethodTextChanged

    }//GEN-LAST:event_memoryTableInputMethodTextChanged

    private void CH2ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH2ToggleButtonActionPerformed
        if (CH2ToggleButton.isSelected())
            RM.CH2.setOpen();
        else
            RM.CH2.setClosed();
        CH2ToggleButton.setText(CHStateToString(RM.CH2.isOpen()));
    }//GEN-LAST:event_CH2ToggleButtonActionPerformed

    private void CH3ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH3ToggleButtonActionPerformed
        if (CH3ToggleButton.isSelected())
            RM.CH3.setOpen();
        else
            RM.CH3.setClosed();
        CH3ToggleButton.setText(CHStateToString(RM.CH3.isOpen()));
    }//GEN-LAST:event_CH3ToggleButtonActionPerformed

    private void CH1ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH1ToggleButtonActionPerformed
        if (CH1ToggleButton.isSelected())
            RM.CH1.setOpen();
        else
            RM.CH1.setClosed();
        CH1ToggleButton.setText(CHStateToString(RM.CH1.isOpen()));
    }//GEN-LAST:event_CH1ToggleButtonActionPerformed

    private void CH4ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH4ToggleButtonActionPerformed
        if (CH4ToggleButton.isSelected())
            RM.CH4.setOpen();
        else
            RM.CH4.setClosed();
        CH4ToggleButton.setText(CHStateToString(RM.CH4.isOpen()));
    }//GEN-LAST:event_CH4ToggleButtonActionPerformed

    private void MODEToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MODEToggleButtonActionPerformed
        if (MODEToggleButton.isSelected())
            RM.mode.SetSupervisor();
        else
            RM.mode.setUser();
        MODEToggleButton.setText(modeToString(RM.mode.isSupervisor()));
    }//GEN-LAST:event_MODEToggleButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        RM.VM.run();
        updateAll();
    }//GEN-LAST:event_runButtonActionPerformed

    private void R2TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R2TextFieldActionPerformed
        RM.R2.setValue(new Word(Integer.parseInt(R2TextField.getText(), 16)));
        updateAll();
    }//GEN-LAST:event_R2TextFieldActionPerformed

    private void R1TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R1TextFieldActionPerformed
        RM.R1.setValue(new Word(Integer.parseInt(R1TextField.getText(), 16)));
        updateAll();
    }//GEN-LAST:event_R1TextFieldActionPerformed

    private void ICTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICTextFieldActionPerformed
        RM.IC.setValue(Integer.parseInt(ICTextField.getText(), 16));
        updateAll();
    }//GEN-LAST:event_ICTextFieldActionPerformed

    private void CTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTextFieldActionPerformed
        RM.C.setValue(Integer.parseInt(CTextField.getText(), 16));
        updateAll();        
    }//GEN-LAST:event_CTextFieldActionPerformed

    private void STextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_STextFieldActionPerformed
        RM.S.setValue(Integer.parseInt(STextField.getText(),2));
        updateAll();
    }//GEN-LAST:event_STextFieldActionPerformed

    private void TimerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimerTextFieldActionPerformed
        RM.timer.setValue(Integer.parseInt(TimerTextField.getText(), 16));
        updateAll();
    }//GEN-LAST:event_TimerTextFieldActionPerformed

    private void PITextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PITextFieldActionPerformed
        RM.PI.setValue(Integer.parseInt(PITextField.getText(), 16));
        updateAll();
    }//GEN-LAST:event_PITextFieldActionPerformed

    private void SITextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SITextFieldActionPerformed
        RM.SI.setValue(Integer.parseInt(SITextField.getText(), 16));
        updateAll();
    }//GEN-LAST:event_SITextFieldActionPerformed

    private void ZFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZFCheckBoxActionPerformed
        if (ZFCheckBox.isSelected())
            RM.C.setZeroFlag();
        else 
            RM.C.unsetZeroFlag();
        updateAll();
    }//GEN-LAST:event_ZFCheckBoxActionPerformed

    private void SFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SFCheckBoxActionPerformed
        if (SFCheckBox.isSelected())
            RM.C.setSignFlag();
        else 
            RM.C.unsetSignFlag();
        updateAll();
    }//GEN-LAST:event_SFCheckBoxActionPerformed

    private void OFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OFCheckBoxActionPerformed
        if (OFCheckBox.isSelected())
            RM.C.setOverflowFlag();
        else 
            RM.C.unsetOverflowFlag();
        updateAll();
    }//GEN-LAST:event_OFCheckBoxActionPerformed

    private void TaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TaskButtonActionPerformed
            String taskName;
            taskName = TaskNameField.getText();
            File f = new File("src/mitosv0/"+taskName+".mit");
            
            if(f.exists()){
                if (RealMachine.VM == null)
                {
                    if (RM.CreateVirtualMachine(taskName))
                            createVMTab();
                        else
                            showMessage("Number of blocks, assigned to virtual machine, is invalid");
                }
                else
                    RM.CreateVirtualMachine(taskName);
                
                updateAll();       
                }else{
                    showMessage("File not found.");
            }
    }//GEN-LAST:event_TaskButtonActionPerformed

    private void PLRTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLRTextFieldActionPerformed
        try {
            int intValue = Integer.parseInt(PLRTextField.getText(), 16);
            RM.PLR.setValue(intValue);
            updateAll();
        } 
            catch (NumberFormatException e) {}
    }//GEN-LAST:event_PLRTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton CH1ToggleButton;
    private javax.swing.JToggleButton CH2ToggleButton;
    private javax.swing.JToggleButton CH3ToggleButton;
    private javax.swing.JToggleButton CH4ToggleButton;
    private javax.swing.JTextField CTextField;
    private javax.swing.JTextField ICTextField;
    private javax.swing.JToggleButton MODEToggleButton;
    private javax.swing.JCheckBox OFCheckBox;
    private javax.swing.JTextField PITextField;
    private javax.swing.JTextField PLRTextField;
    private javax.swing.JTextField R1TextField;
    private javax.swing.JTextField R2TextField;
    private javax.swing.JCheckBox SFCheckBox;
    private javax.swing.JTextField SITextField;
    private javax.swing.JTextField STextField;
    private javax.swing.JButton TaskButton;
    private javax.swing.JTextField TaskNameField;
    private javax.swing.JTextField TimerTextField;
    private javax.swing.JCheckBox ZFCheckBox;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel memoryPanel;
    private javax.swing.JTabbedPane memoryTabbedPane;
    private javax.swing.JTable memoryTable;
    private javax.swing.JPanel operationsPanel;
    private javax.swing.JPanel registerPanel1;
    private javax.swing.JPanel registerPanel2;
    private javax.swing.JButton runButton;
    private javax.swing.JPanel statusFlagPanel;
    private javax.swing.JButton stepButton;
    private javax.swing.ButtonGroup tableDataTypeButtonGroup;
    // End of variables declaration//GEN-END:variables
}
