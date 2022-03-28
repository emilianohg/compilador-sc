package Intermedio;

import javax.swing.table.DefaultTableModel;

public class ModalCuadruplos extends javax.swing.JFrame {


    public ModalCuadruplos(Cuadruplo[] cuadruplos) {
        initComponents();
        DefaultTableModel modelo = (DefaultTableModel) jTableCuadruplos.getModel();
        for(Cuadruplo cuadruplo: cuadruplos){
            Object[] row1 = {
                cuadruplo.getOperator(),
                cuadruplo.getArg0(),
                cuadruplo.getArg1(),
                cuadruplo.getRes()
            };
            modelo.addRow(row1);
        }
        
        this.setLocationRelativeTo(null);
        this.setSize(300, 400);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCuadruplos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vizualizador de código intermedio");
        setPreferredSize(new java.awt.Dimension(552, 402));
        setType(java.awt.Window.Type.POPUP);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(550, 402));

        jTableCuadruplos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTableCuadruplos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Operador", "Argumento 1", "Argumento 2", "Resultado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCuadruplos.setUpdateSelectionOnSort(false);
        jScrollPane2.setViewportView(jTableCuadruplos);
        if (jTableCuadruplos.getColumnModel().getColumnCount() > 0) {
            jTableCuadruplos.getColumnModel().getColumn(0).setResizable(false);
            jTableCuadruplos.getColumnModel().getColumn(1).setResizable(false);
            jTableCuadruplos.getColumnModel().getColumn(2).setResizable(false);
            jTableCuadruplos.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCuadruplos;
    // End of variables declaration//GEN-END:variables
}