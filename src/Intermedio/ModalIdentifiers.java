package Intermedio;

import javax.swing.table.DefaultTableModel;

public class ModalIdentifiers extends javax.swing.JFrame {


    public ModalIdentifiers(Identifier[] identifiers) {
        initComponents();
        DefaultTableModel modelo = (DefaultTableModel) jTableIdentifiers.getModel();
        for(Identifier ident: identifiers){
            Object[] row1 = {
                ident.getName(),
                ident.getType(),
                ident.getValue(),
                ident.getSize(),
                ident.getPos()
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
        jTableIdentifiers = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vizualizador de Identificadores");
        setType(java.awt.Window.Type.POPUP);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(550, 402));

        jTableIdentifiers.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTableIdentifiers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Tipo", "Valor", "Tamaño", "Posicion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableIdentifiers.setUpdateSelectionOnSort(false);
        jScrollPane2.setViewportView(jTableIdentifiers);
        if (jTableIdentifiers.getColumnModel().getColumnCount() > 0) {
            jTableIdentifiers.getColumnModel().getColumn(0).setResizable(false);
            jTableIdentifiers.getColumnModel().getColumn(1).setResizable(false);
            jTableIdentifiers.getColumnModel().getColumn(2).setResizable(false);
            jTableIdentifiers.getColumnModel().getColumn(3).setResizable(false);
            jTableIdentifiers.getColumnModel().getColumn(4).setResizable(false);
        }

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableIdentifiers;
    // End of variables declaration//GEN-END:variables
}
