/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;

/**
 *
 * @author Domukas
 */
public class ProcessorJPanel extends javax.swing.JPanel {

    Processor proc;
    RealMachineGUI owner;
    /**
     * Creates new form ProcessorJPanel
     */
    public ProcessorJPanel(Processor proc, RealMachineGUI owner) {
        this.owner = owner;
        this.proc = proc;
        initComponents();
    }
    
    public void updateFields()
    {
        repaint();
        PLRTextField.setText(proc.PLR.getValue());
        R1TextField.setText(proc.R1.getValue().getValue().replaceFirst("^0+(?!$)", ""));
        R2TextField.setText(proc.R2.getValue().getValue().replaceFirst("^0+(?!$)", ""));
        ICTextField.setText(Integer.toHexString(proc.IC.getValue()));
        CTextField.setText(Integer.toHexString(proc.C.getValue()));
        
        String tmp = Integer.toBinaryString(proc.S.getValue());
        while (tmp.length() < 16)
            tmp = "0"+tmp;
        tmp = tmp.substring(tmp.length()-16, tmp.length());
        STextField.setText(tmp);
        
        TimerTextField.setText(Integer.toHexString(proc.timer.getValue()));
        
        MODEToggleButton.setText(modeToString(proc.mode.isSupervisor()));
        MODEToggleButton.setSelected(proc.mode.isSupervisor());
        
        PITextField.setText(Integer.toHexString(proc.PI.getValue()));
        SITextField.setText(Integer.toString(proc.SI.getValue()));
        
        CH1ToggleButton.setText(CHStateToString(proc.CH1.isOpen()));
        CH1ToggleButton.setSelected(proc.CH1.isOpen());
        CH2ToggleButton.setText(CHStateToString(proc.CH2.isOpen()));
        CH2ToggleButton.setSelected(proc.CH2.isOpen());
        CH3ToggleButton.setText(CHStateToString(proc.CH3.isOpen()));
        CH3ToggleButton.setSelected(proc.CH3.isOpen());
        CH4ToggleButton.setText(CHStateToString(proc.CH4.isOpen()));
        CH4ToggleButton.setSelected(proc.CH4.isOpen());
        
        ZFCheckBox.setSelected(proc.C.isZeroFlagSet());
        SFCheckBox.setSelected(proc.C.isSignFlagSet());
        OFCheckBox.setSelected(proc.C.isOverflowFlagSet());
    }
    
    private String CHStateToString(boolean state)
    {
        if (state)
            return "Open";
        else 
            return "Closed";
    }

    private String modeToString(boolean mode)
    {
        if (mode)
            return "Superv.";
        else 
            return "User";
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
        R1TextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        PLRTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        R2TextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ICTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TimerTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        MODEToggleButton = new javax.swing.JToggleButton();
        jLabel9 = new javax.swing.JLabel();
        PITextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        SITextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        STextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        CH1ToggleButton = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();
        CH2ToggleButton = new javax.swing.JToggleButton();
        jLabel13 = new javax.swing.JLabel();
        CH3ToggleButton = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        CH4ToggleButton = new javax.swing.JToggleButton();
        OFCheckBox = new javax.swing.JCheckBox();
        SFCheckBox = new javax.swing.JCheckBox();
        ZFCheckBox = new javax.swing.JCheckBox();

        setMaximumSize(new java.awt.Dimension(256, 128));
        setMinimumSize(new java.awt.Dimension(256, 128));
        setPreferredSize(new java.awt.Dimension(256, 128));

        jLabel2.setText("R1");
        add(jLabel2);

        R1TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R1TextFieldActionPerformed(evt);
            }
        });
        add(R1TextField);

        jLabel1.setText("PLR");
        add(jLabel1);

        PLRTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLRTextFieldActionPerformed(evt);
            }
        });
        add(PLRTextField);

        jLabel3.setText("R2");
        add(jLabel3);

        R2TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R2TextFieldActionPerformed(evt);
            }
        });
        add(R2TextField);

        jLabel4.setText("IC");
        add(jLabel4);

        ICTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICTextFieldActionPerformed(evt);
            }
        });
        add(ICTextField);

        jLabel5.setText("C");
        add(jLabel5);

        CTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTextFieldActionPerformed(evt);
            }
        });
        add(CTextField);

        jLabel7.setText("TIMER");
        add(jLabel7);

        TimerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimerTextFieldActionPerformed(evt);
            }
        });
        add(TimerTextField);

        jLabel8.setText("MODE");
        add(jLabel8);

        MODEToggleButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        MODEToggleButton.setText("User");
        MODEToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MODEToggleButtonActionPerformed(evt);
            }
        });
        add(MODEToggleButton);

        jLabel9.setText("PI");
        add(jLabel9);

        PITextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PITextFieldActionPerformed(evt);
            }
        });
        add(PITextField);

        jLabel10.setText("SI");
        add(jLabel10);

        SITextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SITextFieldActionPerformed(evt);
            }
        });
        add(SITextField);

        jLabel6.setText("S");
        add(jLabel6);

        STextField.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        STextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                STextFieldActionPerformed(evt);
            }
        });
        add(STextField);

        jLabel11.setText("CH1");
        add(jLabel11);

        CH1ToggleButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        CH1ToggleButton.setText("Open");
        CH1ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH1ToggleButtonActionPerformed(evt);
            }
        });
        add(CH1ToggleButton);

        jLabel12.setText("CH2");
        add(jLabel12);

        CH2ToggleButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        CH2ToggleButton.setText("Open");
        CH2ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH2ToggleButtonActionPerformed(evt);
            }
        });
        add(CH2ToggleButton);

        jLabel13.setText("CH3");
        add(jLabel13);

        CH3ToggleButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        CH3ToggleButton.setText("Open");
        CH3ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH3ToggleButtonActionPerformed(evt);
            }
        });
        add(CH3ToggleButton);

        jLabel14.setText("CH4");
        add(jLabel14);

        CH4ToggleButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        CH4ToggleButton.setText("Open");
        CH4ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH4ToggleButtonActionPerformed(evt);
            }
        });
        add(CH4ToggleButton);

        OFCheckBox.setText("OF");
        OFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OFCheckBoxActionPerformed(evt);
            }
        });
        add(OFCheckBox);

        SFCheckBox.setText("SF");
        SFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SFCheckBoxActionPerformed(evt);
            }
        });
        add(SFCheckBox);

        ZFCheckBox.setText("ZF");
        ZFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZFCheckBoxActionPerformed(evt);
            }
        });
        add(ZFCheckBox);
    }// </editor-fold>//GEN-END:initComponents

    private void R2TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R2TextFieldActionPerformed
        proc.R2.setValue(new Word(Integer.parseInt(R2TextField.getText(), 16)));
        owner.updateAll();
    }//GEN-LAST:event_R2TextFieldActionPerformed

    private void ICTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICTextFieldActionPerformed
        proc.IC.setValue(Integer.parseInt(ICTextField.getText(), 16));
        owner.updateAll();
    }//GEN-LAST:event_ICTextFieldActionPerformed

    private void CTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTextFieldActionPerformed
        proc.C.setValue(Integer.parseInt(CTextField.getText(), 16));
        owner.updateAll();
    }//GEN-LAST:event_CTextFieldActionPerformed

    private void STextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_STextFieldActionPerformed
        proc.S.setValue(Integer.parseInt(STextField.getText(), 2));
        owner.updateAll();
    }//GEN-LAST:event_STextFieldActionPerformed

    private void TimerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimerTextFieldActionPerformed
        proc.timer.setValue(Integer.parseInt(TimerTextField.getText(), 16));
        owner.updateAll();
    }//GEN-LAST:event_TimerTextFieldActionPerformed

    private void MODEToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MODEToggleButtonActionPerformed
        if (MODEToggleButton.isSelected()) {
            proc.mode.SetSupervisor();
        } else {
            proc.mode.setUser();
        }
        MODEToggleButton.setText(modeToString(proc.mode.isSupervisor()));
    }//GEN-LAST:event_MODEToggleButtonActionPerformed

    private void PITextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PITextFieldActionPerformed
        proc.PI.setValue(Integer.parseInt(PITextField.getText(), 16));
        owner.updateAll();
    }//GEN-LAST:event_PITextFieldActionPerformed

    private void SITextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SITextFieldActionPerformed
        proc.SI.setValue(Integer.parseInt(SITextField.getText(), 16));
        owner.updateAll();
    }//GEN-LAST:event_SITextFieldActionPerformed

    private void CH1ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH1ToggleButtonActionPerformed
        if (CH1ToggleButton.isSelected()) {
            proc.CH1.setOpen();
        } else {
            proc.CH1.setClosed();
        }
        CH1ToggleButton.setText(CHStateToString(proc.CH1.isOpen()));
    }//GEN-LAST:event_CH1ToggleButtonActionPerformed

    private void CH2ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH2ToggleButtonActionPerformed
        if (CH2ToggleButton.isSelected()) {
            proc.CH2.setOpen();
        } else {
            proc.CH2.setClosed();
        }
        CH2ToggleButton.setText(CHStateToString(proc.CH2.isOpen()));
    }//GEN-LAST:event_CH2ToggleButtonActionPerformed

    private void CH3ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH3ToggleButtonActionPerformed
        if (CH3ToggleButton.isSelected()) {
            proc.CH3.setOpen();
        } else {
            proc.CH3.setClosed();
        }
        CH3ToggleButton.setText(CHStateToString(proc.CH3.isOpen()));
    }//GEN-LAST:event_CH3ToggleButtonActionPerformed

    private void CH4ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH4ToggleButtonActionPerformed
        if (CH4ToggleButton.isSelected()) {
            proc.CH4.setOpen();
        } else {
            proc.CH4.setClosed();
        }
        CH4ToggleButton.setText(CHStateToString(proc.CH4.isOpen()));
    }//GEN-LAST:event_CH4ToggleButtonActionPerformed

    private void OFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OFCheckBoxActionPerformed
        if (OFCheckBox.isSelected()) {
            proc.C.setOverflowFlag();
        } else {
            proc.C.unsetOverflowFlag();
        }
        owner.updateAll();
    }//GEN-LAST:event_OFCheckBoxActionPerformed

    private void SFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SFCheckBoxActionPerformed
        if (SFCheckBox.isSelected()) {
            proc.C.setSignFlag();
        } else {
            proc.C.unsetSignFlag();
        }
        owner.updateAll();
    }//GEN-LAST:event_SFCheckBoxActionPerformed

    private void ZFCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZFCheckBoxActionPerformed
        if (ZFCheckBox.isSelected()) {
            proc.C.setZeroFlag();
        } else {
            proc.C.unsetZeroFlag();
        }
        owner.updateAll();
    }//GEN-LAST:event_ZFCheckBoxActionPerformed

    private void R1TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R1TextFieldActionPerformed
        proc.R1.setValue(new Word(Integer.parseInt(R1TextField.getText(), 16)));
        owner.updateAll();
    }//GEN-LAST:event_R1TextFieldActionPerformed

    private void PLRTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLRTextFieldActionPerformed
        try {
            int intValue = Integer.parseInt(PLRTextField.getText(), 16);
            proc.PLR.setValue(intValue);
            owner.updateAll();
        } catch (NumberFormatException e) {
        }
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
    private javax.swing.JTextField TimerTextField;
    private javax.swing.JCheckBox ZFCheckBox;
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
    // End of variables declaration//GEN-END:variables
}
