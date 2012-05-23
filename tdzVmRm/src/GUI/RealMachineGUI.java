/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import tdzOS.OS;
import tdzOS.ProcessDescriptor;
import tdzOS.ResourceDescriptor;
import tdzOS.VirtualMachine;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;

/**
 *
 * @author Tomas
 */
public class RealMachineGUI extends javax.swing.JFrame {

    /**
     * Creates new form RealMachineGUI
     */
    
    RealMachine RM;
    OS os;
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
    
    public RealMachineGUI(RealMachine RM, OS os) {
        super("Real machine");
        initComponents();
        this.RM = RM;
        this.os = os;
        this.setVisible(true);
        for (int i = 0; i < RM.proc.length; i++)
        {
            ProcessorJPanel procPanel = new ProcessorJPanel(RM.proc[i],this);
            processorsTabbedPane.add("P"+i, procPanel);
        }
        
        updateProcessorJPanels();
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
        
        infoJTable.setModel(new InfoTableModel(os));
        
        ListSelectionListener infoJTableListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e) {
                updateInfoJTable();
            }
        };
        infoJTable.getSelectionModel().addListSelectionListener(infoJTableListener);
         
    }
    
    public void createVMTab(VirtualMachine vm)
    {
        vm1MemoryTable = new JTable();
        vm1MemoryTable.setModel(new VirtualMemoryTableModel(RM,vm,this));
        vm1MemoryTable.setFillsViewportHeight(true);
        memoryTabbedPane.add("VM#"+vm.pd.internalID, new JScrollPane(vm1MemoryTable));
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
    
    public void updateAll(){
        updateProcessorJPanels();
        this.paint(this.getGraphics());
        memoryTable.repaint();
        /*
        if (RealMachine.VM != null)
            vm1MemoryTable.repaint();
            * */
        updateInfoJTable();
    }
    
    private void processSelected(){
        int index = infoJTable.getSelectedRow(); 
        if (index >= 0 && index < os.processes.size())
        {
            ProcessDescriptor pd = os.processes.get(index).pd;
            String text = "";
            text += "Process name: "+pd.externalID;
            text += "\nID: "+pd.internalID;
            if (pd.processor != null)
                text += "\nProcessor: "+pd.processor.pd.number;
            else
                text += "\nProcessor: null";
            text += "\nState: "+pd.state;
            text += "\nPriority: "+pd.priority;
            if (pd.parent != null)
                text += "\nParent: "+pd.parent.pd.externalID+"#"+pd.parent.pd.internalID;
            text += "\nInstruction: "+os.processes.get(index).nextInstruction;
            infoJTextArea.setText(text);
        }
    }
    
    private void resourceSelected(){
        int index = infoJTable.getSelectedRow();
        if (index >= 0 && index < os.resources.size())
        {
            ResourceDescriptor rd = os.resources.get(index).rd;
            String text = "";
            text += "Resource name: "+rd.externalID;
            text += "\nID: "+rd.internalID;
            text += "\nCreator: "+rd.creator.pd.externalID+"#"+rd.creator.pd.internalID;
            text += "\nComponent count: "+rd.components.size();
            infoJTextArea.setText(text);
        }
    }
    
    private void updateInfoJTable() {
        if (infoJTable.getSelectedColumn()==0)
                    processSelected();
                else
                    resourceSelected();
    }
    
    private void updateProcessorJPanels(){
        for (int i = 0; i < RM.proc.length; i++)
            ((ProcessorJPanel) processorsTabbedPane.getComponentAt(i)).updateFields();
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
        processorsTabbedPane = new javax.swing.JTabbedPane();
        procResJPanel = new javax.swing.JPanel();
        infoTableJScrollPane = new javax.swing.JScrollPane();
        infoJTable = new javax.swing.JTable();
        infoJPanel = new javax.swing.JPanel();
        infoLabel = new javax.swing.JLabel();
        infoJScrollPane = new javax.swing.JScrollPane();
        infoJTextArea = new javax.swing.JTextArea();
        rightSidePanel = new javax.swing.JPanel();
        memoryTabbedPane = new javax.swing.JTabbedPane();
        memoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoryTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        osStepButton = new javax.swing.JButton();
        taskNameField = new javax.swing.JTextField();
        taskButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(860, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        operationsPanel.setMaximumSize(new java.awt.Dimension(160, 550));
        operationsPanel.setMinimumSize(new java.awt.Dimension(256, 128));
        operationsPanel.setPreferredSize(new java.awt.Dimension(256, 128));
        operationsPanel.setLayout(new java.awt.GridLayout(0, 1));
        operationsPanel.add(processorsTabbedPane);

        procResJPanel.setLayout(new java.awt.GridLayout(1, 0));

        infoTableJScrollPane.setMinimumSize(new java.awt.Dimension(128, 128));
        infoTableJScrollPane.setPreferredSize(new java.awt.Dimension(128, 400));

        infoJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        infoJTable.setColumnSelectionAllowed(true);
        infoJTable.setFillsViewportHeight(true);
        infoJTable.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        infoJTable.setPreferredSize(new java.awt.Dimension(128, 100));
        infoJTable.getTableHeader().setReorderingAllowed(false);
        infoJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoJTableMouseClicked(evt);
            }
        });
        infoTableJScrollPane.setViewportView(infoJTable);
        infoJTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        procResJPanel.add(infoTableJScrollPane);

        operationsPanel.add(procResJPanel);

        infoJPanel.setLayout(new javax.swing.BoxLayout(infoJPanel, javax.swing.BoxLayout.Y_AXIS));

        infoLabel.setText("Information");
        infoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        infoLabel.setMaximumSize(new java.awt.Dimension(256, 14));
        infoLabel.setMinimumSize(new java.awt.Dimension(256, 14));
        infoLabel.setPreferredSize(new java.awt.Dimension(256, 14));
        infoJPanel.add(infoLabel);

        infoJScrollPane.setMinimumSize(new java.awt.Dimension(256, 128));
        infoJScrollPane.setPreferredSize(new java.awt.Dimension(256, 128));

        infoJTextArea.setColumns(20);
        infoJTextArea.setEditable(false);
        infoJTextArea.setRows(5);
        infoJTextArea.setMinimumSize(new java.awt.Dimension(128, 128));
        infoJScrollPane.setViewportView(infoJTextArea);

        infoJPanel.add(infoJScrollPane);

        operationsPanel.add(infoJPanel);

        getContentPane().add(operationsPanel);

        rightSidePanel.setLayout(new javax.swing.BoxLayout(rightSidePanel, javax.swing.BoxLayout.Y_AXIS));

        memoryPanel.setLayout(new javax.swing.BoxLayout(memoryPanel, javax.swing.BoxLayout.Y_AXIS));

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

        rightSidePanel.add(memoryTabbedPane);

        buttonPanel.setMaximumSize(new java.awt.Dimension(30000, 16));
        buttonPanel.setMinimumSize(new java.awt.Dimension(128, 16));
        buttonPanel.setPreferredSize(new java.awt.Dimension(128, 16));
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.LINE_AXIS));

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(runButton);

        stepButton.setText("Step to VM");
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(stepButton);

        osStepButton.setText("OS step");
        osStepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                osStepButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(osStepButton);

        taskNameField.setText("Task name");
        taskNameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskNameFieldMouseClicked(evt);
            }
        });
        taskNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskNameFieldActionPerformed(evt);
            }
        });
        buttonPanel.add(taskNameField);

        taskButton.setText("New Task");
        taskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(taskButton);

        rightSidePanel.add(buttonPanel);

        getContentPane().add(rightSidePanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
        os.stepToVM();
        //RM.VM.step();
        updateAll();
    }//GEN-LAST:event_stepButtonActionPerformed

    private void memoryTableInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_memoryTableInputMethodTextChanged

    }//GEN-LAST:event_memoryTableInputMethodTextChanged

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        os.run();
        updateAll();
    }//GEN-LAST:event_runButtonActionPerformed

    private void taskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskButtonActionPerformed
            String taskName;
            taskName = taskNameField.getText();
            File f = new File("src/tdzVmRm/"+taskName+".tdz");
            
            if(f.exists())
            {
                //Cia jau kuriam resursa IvedimoSrautas
                //Paimam is OS'o startStop tik tam, kad kuriant resursa galetume paduot jo teva teisingai
                
                LinkedList<Object> elements = new LinkedList<>();
                
                FileInputStream input = null;
                try {
                    input = new FileInputStream(f);
    
                    elements.add(input);
                    
                    os.createResource(os.getMainproc(), OS.ResName.IvedimoSrautas, elements);
                } catch (IOException ex) {
                    Logger.getLogger(RealMachineGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                /*
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
                    * 
                    */
            }
    }//GEN-LAST:event_taskButtonActionPerformed

    private void osStepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_osStepButtonActionPerformed
        os.step();
        updateAll();
    }//GEN-LAST:event_osStepButtonActionPerformed

    private void taskNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taskNameFieldActionPerformed

    private void taskNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskNameFieldMouseClicked
        taskNameField.setText("");
    }//GEN-LAST:event_taskNameFieldMouseClicked

    private void infoJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoJTableMouseClicked
        updateInfoJTable();
    }//GEN-LAST:event_infoJTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel infoJPanel;
    private javax.swing.JScrollPane infoJScrollPane;
    private javax.swing.JTable infoJTable;
    private javax.swing.JTextArea infoJTextArea;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JScrollPane infoTableJScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel memoryPanel;
    private javax.swing.JTabbedPane memoryTabbedPane;
    private javax.swing.JTable memoryTable;
    private javax.swing.JPanel operationsPanel;
    private javax.swing.JButton osStepButton;
    private javax.swing.JPanel procResJPanel;
    private javax.swing.JTabbedPane processorsTabbedPane;
    private javax.swing.JPanel rightSidePanel;
    private javax.swing.JButton runButton;
    private javax.swing.JButton stepButton;
    private javax.swing.ButtonGroup tableDataTypeButtonGroup;
    private javax.swing.JButton taskButton;
    private javax.swing.JTextField taskNameField;
    // End of variables declaration//GEN-END:variables
}
