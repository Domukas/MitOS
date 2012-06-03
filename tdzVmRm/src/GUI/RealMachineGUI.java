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
import tdzOS.OS.ProcName;
import tdzOS.ProcessDescriptor;
import tdzOS.ResourceDescriptor;
import tdzOS.VirtualMachine;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;

/**
 *
 * @author Tomas
 */
public class RealMachineGUI extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form RealMachineGUI
     */
    
    RealMachine RM;
    OS os;
    JTable vm1MemoryTable;
    public boolean updateTables = false;

    @Override
    public void run() {
        while (true)
        {
            if (updateTables)
                updateAll();
            updateTables = false;
            if (os.makeRun)
                runJToggleButton.setText("Running");
            else
                runJToggleButton.setText("Run");
            runJToggleButton.setSelected(os.makeRun);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(RealMachineGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
    
    public RealMachineGUI(RealMachine RM, OS os) {
        super("Real machine");
        RM.addGui(this);
        initComponents();
        this.RM = RM;
        this.os = os;
        this.setVisible(true);
        for (int i = 0; i < RM.proc.length; i++)
        {
            ProcessorJPanel procPanel = new ProcessorJPanel(RM.proc[i],this);
            processorsTabbedPane.add("P"+i, procPanel);
        }
        
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
        
        procJTable.setModel(new ProcessesTableModel(os));
        
        ListSelectionListener procJTableListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e) {
                procTableSelected();
            }
        };
        procJTable.getSelectionModel().addListSelectionListener(procJTableListener);
        
        resJTable.setModel(new ResourcesTableModel(os));

        ResourcesTableRenderer rr =new ResourcesTableRenderer();
        for (int i=0;i < resJTable.getColumnCount(); i++)
        {
            resJTable.getColumn(resJTable.getColumnName(i)).setCellRenderer(rr);
        }
        
        updateAll();
    }
    
    private void procTableSelected() {
        tdzOS.Process proc;
        try 
        {
            proc = os.processes.get(procJTable.getSelectedRow());
            if (proc.pd.externalID == ProcName.VirtualMachine)
            {
                for (int i = 1; i < memoryTabbedPane.getTabCount(); i++)
                {
                    if (Integer.parseInt(memoryTabbedPane.getTitleAt(i).split("#")[1])==proc.pd.internalID)
                        memoryTabbedPane.setSelectedIndex(i);
                }
                for (int i = 0; i< processorsTabbedPane.getTabCount(); i++)
                {
                    if (proc.pd.processor != null && processorsTabbedPane.getTitleAt(i).equals("P"+proc.pd.processor.pd.number))
                        processorsTabbedPane.setSelectedIndex(i);
                }
            }
            resJTable.revalidate();
            resJTable.repaint();
        } catch (IndexOutOfBoundsException e) {}
    }
    
    public void dropVMTab(VirtualMachine vm)
    {
        for (int i = 1; i < memoryTabbedPane.getTabCount(); i++)
        {
            if (Integer.parseInt(memoryTabbedPane.getTitleAt(i).split("#")[1])==vm.pd.internalID)
            {
                memoryTabbedPane.removeTabAt(i);
                break;
            }    
        }
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
        ((ProcessorJPanel) processorsTabbedPane.getComponentAt(processorsTabbedPane.getSelectedIndex()) ).updateFields();
        memoryTabbedPane.getComponentAt(memoryTabbedPane.getSelectedIndex()).repaint();
        procJTable.revalidate();
        resJTable.revalidate();
        procJTable.repaint();
        resJTable.repaint();
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
        procJPanel = new javax.swing.JPanel();
        procTableJScrollPane = new javax.swing.JScrollPane();
        procJTable = new javax.swing.JTable();
        resJPanel = new javax.swing.JPanel();
        resTableScrollPane = new javax.swing.JScrollPane();
        resJTable = new javax.swing.JTable();
        rightSidePanel = new javax.swing.JPanel();
        memoryTabbedPane = new javax.swing.JTabbedPane();
        memoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        memoryTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        runJToggleButton = new javax.swing.JToggleButton();
        stepButton = new javax.swing.JButton();
        osStepButton = new javax.swing.JButton();
        taskNameField = new javax.swing.JTextField();
        taskButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(956, 600));
        setPreferredSize(new java.awt.Dimension(922, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        operationsPanel.setMinimumSize(new java.awt.Dimension(256, 128));
        operationsPanel.setLayout(new javax.swing.BoxLayout(operationsPanel, javax.swing.BoxLayout.PAGE_AXIS));

        processorsTabbedPane.setDoubleBuffered(true);
        operationsPanel.add(processorsTabbedPane);

        procJPanel.setLayout(new java.awt.GridLayout(1, 0));

        procJTable.setModel(new javax.swing.table.DefaultTableModel(
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
        procJTable.setDoubleBuffered(true);
        procJTable.setFillsViewportHeight(true);
        procJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                procJTableMouseClicked(evt);
            }
        });
        procTableJScrollPane.setViewportView(procJTable);

        procJPanel.add(procTableJScrollPane);

        operationsPanel.add(procJPanel);

        resJPanel.setLayout(new java.awt.GridLayout(1, 0));

        resJTable.setModel(new javax.swing.table.DefaultTableModel(
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
        resJTable.setDoubleBuffered(true);
        resJTable.setFillsViewportHeight(true);
        resTableScrollPane.setViewportView(resJTable);

        resJPanel.add(resTableScrollPane);

        operationsPanel.add(resJPanel);

        getContentPane().add(operationsPanel);

        rightSidePanel.setLayout(new javax.swing.BoxLayout(rightSidePanel, javax.swing.BoxLayout.Y_AXIS));

        memoryPanel.setMaximumSize(new java.awt.Dimension(620, 32767));
        memoryPanel.setMinimumSize(new java.awt.Dimension(620, 23));
        memoryPanel.setPreferredSize(new java.awt.Dimension(620, 402));
        memoryPanel.setLayout(new javax.swing.BoxLayout(memoryPanel, javax.swing.BoxLayout.Y_AXIS));

        memoryTable.setModel(new MemoryTableModel(RM, this));
        memoryTable.setColumnSelectionAllowed(true);
        memoryTable.setDoubleBuffered(true);
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

        runJToggleButton.setText("Run");
        runJToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runJToggleButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(runJToggleButton);

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
        os.makeStepToVM = true;
        updateAll();
    }//GEN-LAST:event_stepButtonActionPerformed

    private void memoryTableInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_memoryTableInputMethodTextChanged

    }//GEN-LAST:event_memoryTableInputMethodTextChanged

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
        }
    }//GEN-LAST:event_taskButtonActionPerformed

    private void osStepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_osStepButtonActionPerformed
        os.makeStep = true;
        updateAll();
    }//GEN-LAST:event_osStepButtonActionPerformed

    private void taskNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskNameFieldActionPerformed

    }//GEN-LAST:event_taskNameFieldActionPerformed

    private void taskNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskNameFieldMouseClicked
        taskNameField.setText("");
    }//GEN-LAST:event_taskNameFieldMouseClicked

    private void procJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_procJTableMouseClicked
        procTableSelected();
    }//GEN-LAST:event_procJTableMouseClicked

    private void runJToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runJToggleButtonActionPerformed
        if (runJToggleButton.isSelected())
        {
            os.makeRun = true;
            runJToggleButton.setText("Running");
            taskButton.setEnabled(false);
        } else 
        {
            os.makeRun = false;
            runJToggleButton.setText("Run");
            taskButton.setEnabled(true);
        }
    }//GEN-LAST:event_runJToggleButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel memoryPanel;
    private javax.swing.JTabbedPane memoryTabbedPane;
    private javax.swing.JTable memoryTable;
    private javax.swing.JPanel operationsPanel;
    private javax.swing.JButton osStepButton;
    private javax.swing.JPanel procJPanel;
    public javax.swing.JTable procJTable;
    private javax.swing.JScrollPane procTableJScrollPane;
    private javax.swing.JTabbedPane processorsTabbedPane;
    private javax.swing.JPanel resJPanel;
    private javax.swing.JTable resJTable;
    private javax.swing.JScrollPane resTableScrollPane;
    private javax.swing.JPanel rightSidePanel;
    private javax.swing.JToggleButton runJToggleButton;
    private javax.swing.JButton stepButton;
    private javax.swing.ButtonGroup tableDataTypeButtonGroup;
    private javax.swing.JButton taskButton;
    private javax.swing.JTextField taskNameField;
    // End of variables declaration//GEN-END:variables
}
