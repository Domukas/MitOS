/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.table.DefaultTableModel;
import mitosv0.VirtualMachine;

/**
 *
 * @author Tomas
 */
public class VirtualMachineGUI extends javax.swing.JFrame {

    /**
     * Creates new form RealMachineGUI
     */
    
    VirtualMachine VM;
    RealMachineGUI RMgui;
    int focusGained;
    
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
    
    public VirtualMachineGUI(VirtualMachine VM, RealMachineGUI RMgui) {
        super("Virtual machine");
        this.VM = VM;
        this.RMgui = RMgui;
        
        initComponents();
        this.setVisible(true);
        
        updateRegisterFields();  
        updateFlagInfo();
        //Norim, kad adreso stulpelio dydis nesikeistu - vietos butu tiek, kiek uztenka
        memoryTable.getColumnModel().getColumn(0).setMaxWidth(32);
        memoryTable.getColumnModel().getColumn(0).setMinWidth(32);
        MemoryTableRenderer cr=new MemoryTableRenderer();
        VirtualMemoryTableFirstColumnRenderer cfr = new VirtualMemoryTableFirstColumnRenderer(VM);
        memoryTable.getColumn(memoryTable.getColumnName(0)).setCellRenderer(cfr);
        for (int i=1;i < memoryTable.getColumnCount(); i++)
        {
            memoryTable.getColumn(memoryTable.getColumnName(i)).setCellRenderer(cr);
        }
    }
    
    public void updateAll()
    {
        updateRegisterFields();
        updateFlagInfo();
        memoryTable.repaint();
    }
        
    private void updateRegisterFields()
    {
        R1TextField.setText(Integer.toHexString(VM.R1.getValue()));
        R2TextField.setText(Integer.toHexString(VM.R2.getValue()));
        ICTextField.setText(Integer.toHexString(VM.IC.getValue()));
        CTextField.setText(Integer.toHexString(VM.C.getValue()));
    }
    
    private void updateFlagInfo()
    {
        ZFCheckBox.setSelected(VM.C.isZeroFlagSet());
        SFCheckBox.setSelected(VM.C.isSignFlagSet());
        OFCheckBox.setSelected(VM.C.isOverflowFlagSet());
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
        jLabel2 = new javax.swing.JLabel();
        R1TextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        R2TextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ICTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CTextField = new javax.swing.JTextField();
        statusFlagPanel = new javax.swing.JPanel();
        ZFCheckBox = new javax.swing.JCheckBox();
        SFCheckBox = new javax.swing.JCheckBox();
        OFCheckBox = new javax.swing.JCheckBox();
        buttonPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        memoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoryTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        tableDataTypeIntegerToggleButton = new javax.swing.JToggleButton();
        tableDataTypeHexToggleButton = new javax.swing.JToggleButton();
        tableDataTypeCharToggleButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        operationsPanel.setMaximumSize(new java.awt.Dimension(160, 420));
        operationsPanel.setPreferredSize(new java.awt.Dimension(160, 400));
        operationsPanel.setLayout(new javax.swing.BoxLayout(operationsPanel, javax.swing.BoxLayout.PAGE_AXIS));

        registerPanel1.setMaximumSize(new java.awt.Dimension(160, 420));
        registerPanel1.setPreferredSize(new java.awt.Dimension(160, 400));
        registerPanel1.setLayout(new java.awt.GridLayout(0, 2, 2, 2));

        jLabel2.setText("R1");
        registerPanel1.add(jLabel2);

        R1TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                R1TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                R1TextFieldFocusLost(evt);
            }
        });
        registerPanel1.add(R1TextField);

        jLabel3.setText("R2");
        registerPanel1.add(jLabel3);

        R2TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                R2TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                R2TextFieldFocusLost(evt);
            }
        });
        registerPanel1.add(R2TextField);

        jLabel4.setText("IC");
        registerPanel1.add(jLabel4);

        ICTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ICTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ICTextFieldFocusLost(evt);
            }
        });
        registerPanel1.add(ICTextField);

        jLabel5.setText("C");
        registerPanel1.add(jLabel5);

        CTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                CTextFieldFocusLost(evt);
            }
        });
        registerPanel1.add(CTextField);

        operationsPanel.add(registerPanel1);

        statusFlagPanel.setMaximumSize(new java.awt.Dimension(160, 420));
        statusFlagPanel.setPreferredSize(new java.awt.Dimension(160, 400));
        statusFlagPanel.setLayout(new javax.swing.BoxLayout(statusFlagPanel, javax.swing.BoxLayout.LINE_AXIS));

        ZFCheckBox.setText("ZF");
        statusFlagPanel.add(ZFCheckBox);

        SFCheckBox.setText("SF");
        statusFlagPanel.add(SFCheckBox);

        OFCheckBox.setText("OF");
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

        getContentPane().add(operationsPanel);

        memoryPanel.setLayout(new javax.swing.BoxLayout(memoryPanel, javax.swing.BoxLayout.PAGE_AXIS));

        memoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        tableDataTypeButtonGroup.add(tableDataTypeIntegerToggleButton);
        tableDataTypeIntegerToggleButton.setText("Integer");
        tableDataTypeIntegerToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableDataTypeIntegerToggleButtonActionPerformed(evt);
            }
        });
        jPanel2.add(tableDataTypeIntegerToggleButton);

        tableDataTypeButtonGroup.add(tableDataTypeHexToggleButton);
        tableDataTypeHexToggleButton.setSelected(true);
        tableDataTypeHexToggleButton.setText("Hex");
        tableDataTypeHexToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableDataTypeHexToggleButtonActionPerformed(evt);
            }
        });
        jPanel2.add(tableDataTypeHexToggleButton);

        tableDataTypeButtonGroup.add(tableDataTypeCharToggleButton);
        tableDataTypeCharToggleButton.setText("Char");
        tableDataTypeCharToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableDataTypeCharToggleButtonActionPerformed(evt);
            }
        });
        jPanel2.add(tableDataTypeCharToggleButton);

        memoryPanel.add(jPanel2);

        getContentPane().add(memoryPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
        VM.step();
        updateAll();
        RMgui.updateAll();
    }//GEN-LAST:event_stepButtonActionPerformed

    private void memoryTableInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_memoryTableInputMethodTextChanged

    }//GEN-LAST:event_memoryTableInputMethodTextChanged

    private void tableDataTypeIntegerToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableDataTypeIntegerToggleButtonActionPerformed
        tableDataType = TableDataTypes.Int;
        memoryTable.repaint();
    }//GEN-LAST:event_tableDataTypeIntegerToggleButtonActionPerformed

    private void tableDataTypeCharToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableDataTypeCharToggleButtonActionPerformed
        tableDataType = TableDataTypes.Char;
        memoryTable.repaint();
    }//GEN-LAST:event_tableDataTypeCharToggleButtonActionPerformed

    private void tableDataTypeHexToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableDataTypeHexToggleButtonActionPerformed
        tableDataType = TableDataTypes.Hex;
        memoryTable.repaint();
        
    }//GEN-LAST:event_tableDataTypeHexToggleButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        VM.run();
        updateAll();
        RMgui.updateAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_runButtonActionPerformed

    private void R1TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_R1TextFieldFocusGained
        focusGained = Integer.valueOf(R1TextField.getText(), 16).intValue();
    }//GEN-LAST:event_R1TextFieldFocusGained

    private void R2TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_R2TextFieldFocusGained
        focusGained = Integer.valueOf(R2TextField.getText(), 16).intValue();
    }//GEN-LAST:event_R2TextFieldFocusGained

    private void ICTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICTextFieldFocusGained
        focusGained = Integer.valueOf(ICTextField.getText(), 16).intValue();
    }//GEN-LAST:event_ICTextFieldFocusGained

    private void CTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CTextFieldFocusGained
        focusGained = Integer.valueOf(CTextField.getText(), 16).intValue();
    }//GEN-LAST:event_CTextFieldFocusGained

    private void R1TextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_R1TextFieldFocusLost
        try
        {
            int newValue = Integer.valueOf(R1TextField.getText(), 16).intValue();
            VM.R1.setValue(newValue);
            RMgui.updateAll();
        }catch(Exception e)
        {
            R1TextField.setText(Integer.toHexString(focusGained));
        }
    }//GEN-LAST:event_R1TextFieldFocusLost

    private void R2TextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_R2TextFieldFocusLost
        try
        {
            int newValue = Integer.valueOf(R2TextField.getText(), 16).intValue();
            VM.R2.setValue(newValue);
            RMgui.updateAll();
        }catch(Exception e)
        {
            R2TextField.setText(Integer.toHexString(focusGained));
        }
    }//GEN-LAST:event_R2TextFieldFocusLost

    private void ICTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICTextFieldFocusLost
        try
        {
            int newValue = Integer.valueOf(ICTextField.getText(), 16).intValue();
            VM.IC.setValue(newValue);
            RMgui.updateAll();
        }catch(Exception e)
        {
            ICTextField.setText(Integer.toHexString(focusGained));
        }
    }//GEN-LAST:event_ICTextFieldFocusLost

    private void CTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CTextFieldFocusLost
        try
        {
            int newValue = Integer.valueOf(CTextField.getText(), 16).intValue();
            VM.C.setValue(newValue);
            RMgui.updateAll();
        }catch(Exception e)
        {
            CTextField.setText(Integer.toHexString(focusGained));
        }
    }//GEN-LAST:event_CTextFieldFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CTextField;
    private javax.swing.JTextField ICTextField;
    private javax.swing.JCheckBox OFCheckBox;
    private javax.swing.JTextField R1TextField;
    private javax.swing.JTextField R2TextField;
    private javax.swing.JCheckBox SFCheckBox;
    private javax.swing.JCheckBox ZFCheckBox;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel memoryPanel;
    private javax.swing.JTable memoryTable;
    private javax.swing.JPanel operationsPanel;
    private javax.swing.JPanel registerPanel1;
    private javax.swing.JButton runButton;
    private javax.swing.JPanel statusFlagPanel;
    private javax.swing.JButton stepButton;
    private javax.swing.ButtonGroup tableDataTypeButtonGroup;
    private javax.swing.JToggleButton tableDataTypeCharToggleButton;
    private javax.swing.JToggleButton tableDataTypeHexToggleButton;
    private javax.swing.JToggleButton tableDataTypeIntegerToggleButton;
    // End of variables declaration//GEN-END:variables
}
