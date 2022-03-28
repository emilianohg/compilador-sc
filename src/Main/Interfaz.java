package Main;

import Intermedio.Identifier;
import Intermedio.CodigoIntermedio;
import Intermedio.ModalCuadruplos;
import Intermedio.ModalIdentifiers;
import Lexico.AnalizadorLexico;
import Lexico.PaintTextBox;
import Semantico.AnalizadorSemantico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import Sintactico.AnalizadorSintactico;
import java.io.IOException;

import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Paths;
import Sintactico.Nodo;
import java.awt.Color;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.ArrayList;

public class Interfaz extends javax.swing.JFrame {

    int conLinea = 1;
    int linea = 0;
    Nodo arbolSintactico;
    enum TypeConsoleMessage {
        ERROR, INFO
    };
    private ArrayList<Identifier> tablaIdentificadores;

    public Interfaz() {
        initComponents();
        this.setLocationRelativeTo(null);
        PnlToken.setVisible(true);
        splitPaneEC.setDividerLocation(0.70);
        txtCode.requestFocus();
        btnAnalizadorLexico.setVisible(true);
        tablaIdentificadores = new ArrayList<>();

        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(attribs, Color.white);
        StyleConstants.setBold(attribs, true);
        txtNumLinea.setParagraphAttributes(attribs, true);

        ThreadNumberCode nC = new ThreadNumberCode();
        String text = "var pi = 3.1416;\n" +
                "var radio = 1;\n" +
                "\n" +
                "if(radio > pi) {\n" +
                "    radio = pi * 2;\n" +
                "} else {\n" +
                "    radio = pi + 1;\n" +
                "};";
        
        txtCode.setText(text);
        nC.start();
    }
    
    private void writeMessageInConsole(String text, TypeConsoleMessage type) {
        PaintTextBox mensaje = new PaintTextBox();
        mensaje.insertarCodigo(text);
        if (type == TypeConsoleMessage.ERROR) {
            mensaje.pintaRojoFuerte(0, mensaje.getTexto().length());
        } else {
            mensaje.pintaAzulBold(0, mensaje.getTexto().length());
        }
        txtErrores.setDocument(mensaje.componente.getDocument());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPaneCentral = new javax.swing.JSplitPane();
        panelEditor = new javax.swing.JPanel();
        splitPaneEC = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        txtCode = new javax.swing.JTextPane();
        txtNumLinea = new javax.swing.JTextPane();
        PnlToken = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblAnalisis = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        consola = new javax.swing.JPanel();
        PanelControlesSintactico = new javax.swing.JPanel();
        btnAnalizadorLexico = new javax.swing.JButton();
        btnAnalizadorSintactico = new javax.swing.JButton();
        btnAnalizadorSemantico = new javax.swing.JButton();
        btnGeneradorCodigoIntermedio = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtErrores = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lenguaje SC");
        setPreferredSize(new java.awt.Dimension(1024, 700));
        setSize(new java.awt.Dimension(800, 600));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        splitPaneCentral.setDividerSize(2);
        splitPaneCentral.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelEditor.setAutoscrolls(true);
        panelEditor.setPreferredSize(new java.awt.Dimension(458, 500));
        panelEditor.setLayout(new java.awt.BorderLayout());

        splitPaneEC.setDividerLocation(200);
        splitPaneEC.setDividerSize(2);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(400, 19));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 52));

        jPanel2.setLayout(new java.awt.BorderLayout());

        txtCode.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        txtCode.setSelectionColor(new java.awt.Color(0, 51, 255));
        txtCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodeKeyTyped(evt);
            }
        });
        jPanel2.add(txtCode, java.awt.BorderLayout.CENTER);

        txtNumLinea.setEditable(false);
        txtNumLinea.setBackground(new java.awt.Color(204, 204, 204));
        txtNumLinea.setFont(new java.awt.Font("Consolas", 3, 14)); // NOI18N
        txtNumLinea.setText("1");
        txtNumLinea.setMinimumSize(new java.awt.Dimension(10, 25));
        txtNumLinea.setPreferredSize(new java.awt.Dimension(20, 50));
        jPanel2.add(txtNumLinea, java.awt.BorderLayout.LINE_START);
        txtNumLinea.getAccessibleContext().setAccessibleName("");

        jScrollPane2.setViewportView(jPanel2);

        splitPaneEC.setLeftComponent(jScrollPane2);

        PnlToken.setPreferredSize(new java.awt.Dimension(400, 100));
        PnlToken.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tokens", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 12))); // NOI18N
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 436));

        TblAnalisis.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        TblAnalisis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Token", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblAnalisis.setMinimumSize(new java.awt.Dimension(100, 0));
        jScrollPane1.setViewportView(TblAnalisis);
        if (TblAnalisis.getColumnModel().getColumnCount() > 0) {
            TblAnalisis.getColumnModel().getColumn(0).setResizable(false);
            TblAnalisis.getColumnModel().getColumn(1).setResizable(false);
        }

        PnlToken.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getAccessibleContext().setAccessibleDescription("Tabla de simbolos");

        jPanel1.setBackground(new java.awt.Color(247, 247, 247));
        jPanel1.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel1.setLayout(new java.awt.GridLayout(20, 0, 0, 2));
        PnlToken.add(jPanel1, java.awt.BorderLayout.LINE_START);

        splitPaneEC.setRightComponent(PnlToken);

        panelEditor.add(splitPaneEC, java.awt.BorderLayout.CENTER);

        splitPaneCentral.setTopComponent(panelEditor);

        consola.setPreferredSize(new java.awt.Dimension(10, 100));
        consola.setLayout(new java.awt.BorderLayout());

        PanelControlesSintactico.setMinimumSize(new java.awt.Dimension(0, 24));
        PanelControlesSintactico.setPreferredSize(new java.awt.Dimension(711, 44));

        btnAnalizadorLexico.setText("Analizador léxico");
        btnAnalizadorLexico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizadorLexicoActionPerformed(evt);
            }
        });

        btnAnalizadorSintactico.setText("Analizador sintactico");
        btnAnalizadorSintactico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizadorSintacticoActionPerformed(evt);
            }
        });

        btnAnalizadorSemantico.setText("Analizador semantico");
        btnAnalizadorSemantico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizadorSemanticoActionPerformed(evt);
            }
        });

        btnGeneradorCodigoIntermedio.setText("Generar código intermedio");
        btnGeneradorCodigoIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneradorCodigoIntermedioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelControlesSintacticoLayout = new javax.swing.GroupLayout(PanelControlesSintactico);
        PanelControlesSintactico.setLayout(PanelControlesSintacticoLayout);
        PanelControlesSintacticoLayout.setHorizontalGroup(
            PanelControlesSintacticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelControlesSintacticoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnalizadorLexico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnalizadorSintactico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnalizadorSemantico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGeneradorCodigoIntermedio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelControlesSintacticoLayout.setVerticalGroup(
            PanelControlesSintacticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelControlesSintacticoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelControlesSintacticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizadorSintactico)
                    .addComponent(btnAnalizadorSemantico)
                    .addComponent(btnGeneradorCodigoIntermedio)
                    .addComponent(btnAnalizadorLexico))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        consola.add(PanelControlesSintactico, java.awt.BorderLayout.PAGE_START);

        txtErrores.setEditable(false);
        txtErrores.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtErrores.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jScrollPane3.setViewportView(txtErrores);

        consola.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        splitPaneCentral.setBottomComponent(consola);

        getContentPane().add(splitPaneCentral, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 51));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jMenu1.setBackground(new java.awt.Color(0, 0, 51));
        jMenu1.setText("Archivo");

        jMenuItem1.setText("Abrir archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String aux = "";
        String texto = "";
        boolean existe;
        String direccion;
        try {
            // llamamos el metodo que permite cargar la ventana
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(".txt", "txt");
            fc.setFileFilter(filtro);
            fc.showOpenDialog(fc);
            File abre = fc.getSelectedFile();
            fc.addChoosableFileFilter(filtro);

            // recorremos el archivo, lo leemos para plasmarlo en el area de
            // texto
            int status = 0;
            if (abre.getName().endsWith(".txt")) {
                if (fc != null) {

                    FileReader archivos = new FileReader(abre);

                    BufferedReader lee = new BufferedReader(archivos);
                    while ((aux = lee.readLine()) != null) {
                        texto += aux + "\n";
                        existe = true;
                    }
                    lee.close();
                    archivos.close();
                }

                txtCode.setText(new String(texto.getBytes("UTF-8")));
            } else {
                JOptionPane.showMessageDialog(null, "Solo se acepta formato .txt", "ERROR", 0);
            }

            if (status == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "No eligio ningun archivo", "Error", 0);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nNo se ha encontrado el archivo", "ADVERTENCIA!!!",
                    JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException ex) {
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void generarArbolSintaxis(AnalizadorSintactico analisis) {
        Nodo raiz = analisis.nodoPrincipal;
        String datos = mapearArbolIni(raiz);
        String datos1 = mapearUnionesArbolIni(raiz);
        Graficar(datos + "\n" + datos1, "Arbol");

    }

    //inicializa los nodos para poder unir sin dupliciadad
    private String mapearArbolIni(Nodo raiz) {
        String datos = "";
        //es raiz?
        if (!raiz.getHojas().isEmpty()) {
            for (Nodo hoja : raiz.getHojas()) {
                datos += mapearArbolIni(hoja);
            }
            //raiz
            datos += inicializarNodo(raiz) + "\n";
        } else {
            //hojas
            datos = inicializarNodo(raiz) + "\n";
        }
        return datos;
    }

    //pone el formato para incializar
    private String inicializarNodo(Nodo n) {
        String datos = "";
        if (n.getValor().isEmpty()) {
            //Raiz
            datos = "node" + n.getNumeroGuia() + "[label=\"" + n.getNombreRaiz() + "\"]";
        } else {
            //Hoja
            if (n.getNombreRaiz().equals("TEXT")) {
                String valor = n.getValor().replace("\"", "");
                datos += "node" + n.getNumeroGuia() + "[label=\"" + n.getNombreRaiz() + "\n<" + valor + "> \"]";
            } else {
                datos += "node" + n.getNumeroGuia() + "[label=\"" + n.getNombreRaiz() + "\n<" + n.getValor() + ">\"]";
            }

        }
        return datos;
    }

    //Crea las uniones para graficar el arbol
    private String mapearUnionesArbolIni(Nodo raiz) {
        String datos = "";
        //es raiz?
        if (!raiz.getHojas().isEmpty()) {
            for (Nodo hoja : raiz.getHojas()) {
                datos += "node" + raiz.getNumeroGuia() + "->" + mapearUnionesArbolIni(hoja) + "\n";
            }
        } else {
            //hojas
            datos = "node" + raiz.getNumeroGuia();
        }
        return datos;
    }

    private void Graficar(String cadena, String cad) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        String nombre = cad;
        String archivo = nombre + ".dot";
        String url = Paths.get(".").toAbsolutePath().normalize().toString();
        String path = url + "\\src\\img\\";
        File img = new File(url + "Arbol.png");
        try {
            fichero = new FileWriter(img);
            fichero = new FileWriter(path + archivo);
            pw = new PrintWriter(fichero);
            pw.println("digraph G {node[shape=box, style=filled, color=Gray95]; edge[color=blue];rankdir=UD \n");
            pw.println(cadena);
            pw.println("\n}");
            fichero.close();
            try {
                String cmd = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe -Tpng " + path + nombre + ".dot -o " + (path + cad) + ".png"; //Comando de apagado en linux
                Runtime.getRuntime().exec(cmd);
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static boolean moverArch(String archNombre) {
        boolean efectuado = false;

        File arch = new File(archNombre);
        if (arch.exists()) {
            System.out.println("\n*** Moviendo " + arch + " \n***");
            Path currentRelativePath = Paths.get("");
            String nuevoDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "Sintactico" + File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                System.out.println("\n*** Generado " + archNombre + "***\n");
                efectuado = true;

            } else {
                System.out.println("\n*** No movido " + archNombre + " ***\n");
            }

        } else {
            System.out.println("\n*** Codigo no existente ***\n");
        }
        return efectuado;
    }


    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange

    }//GEN-LAST:event_formPropertyChange

    private void btnAnalizadorLexicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizadorLexicoActionPerformed
        
        if (this.errorLexico()) {
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) TblAnalisis.getModel();
        modelo.setRowCount(0);
        try {
            String codigo = txtCode.getText();
            String tok = "";
            int lin = 0;
            String lex = "";
            Reader reader = new BufferedReader(new BufferedReader(new StringReader(codigo)));
            AnalizadorLexico lexico = new AnalizadorLexico(reader);
            lexico.estilo.insertarCodigoPane(txtCode);
            while (true) {
                lexico.next_token();
                if (lexico.yytext().equals("")) {
                    break;
                }
                lin = lexico.numLinea() + 1;
                tok = lexico.nameToken;
                lex = lexico.yytext();

                Object[] row1 = {lin, tok, lex};
                modelo.addRow(row1);

            }
            txtCode = lexico.estilo.componente;

        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        TblAnalisis.setModel(modelo);
        this.writeMessageInConsole(
            "Analisis léxico funcionando correctamente.",
            TypeConsoleMessage.INFO
        );
    }//GEN-LAST:event_btnAnalizadorLexicoActionPerformed

    private void txtCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyTyped

    }//GEN-LAST:event_txtCodeKeyTyped

    private void btnAnalizadorSintacticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizadorSintacticoActionPerformed
        
        txtErrores.setText("");
        String codigo = txtCode.getText();
        Reader reader = new BufferedReader(new BufferedReader(new StringReader(codigo)));
        AnalizadorLexico lexico;
        AnalizadorSintactico sintactico;

        if (errorLexico()) {
            return;    
        }
        
        lexico = new AnalizadorLexico(reader);
        lexico.estilo.insertarCodigoPane(txtCode);
        sintactico = new AnalizadorSintactico(lexico);

        try {

            sintactico.parse();
            arbolSintactico = sintactico.nodoPrincipal;
            generarArbolSintaxis(sintactico);

            this.writeMessageInConsole(
                "Analisis sintáctico finalizado con exito",
                TypeConsoleMessage.INFO
            );

        } catch (Exception ex) {
            if (sintactico.charErrorDetec() != -1 && sintactico.charErrorDetec() != 0) {
                int diezPorciento = (int) (codigo.substring(0, sintactico.charErrorDetec()).length() * 0.10);
                
                int tamTextoFinal = sintactico.estilo.getTexto().length();
                String textoCargado = "";
                try {
                    textoCargado = sintactico.estilo.doc.getText(0, tamTextoFinal);
                } catch (BadLocationException ex1) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex1);
                }
                String errores = "Error sintactico en :\n\t..." + codigo.substring(diezPorciento, sintactico.charErrorDetec());
                this.writeMessageInConsole(
                    errores,
                    TypeConsoleMessage.ERROR
                );
            } else {
                this.writeMessageInConsole(
                    "Error sintactico",
                    TypeConsoleMessage.ERROR
                );
            }

        }

        

    }//GEN-LAST:event_btnAnalizadorSintacticoActionPerformed

    private void btnAnalizadorSemanticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizadorSemanticoActionPerformed
        txtErrores.setText("");
        AnalizadorSintactico sintactico_a = null;
        if (errorLexico()) {
            this.writeMessageInConsole(
                "Se detectaron errores lexicos. No se puede continuar",
                TypeConsoleMessage.ERROR
            );
        } else {
            String codigo = txtCode.getText();
            Reader reader_a = new BufferedReader(new BufferedReader(new StringReader(codigo)));
            AnalizadorLexico lex = new AnalizadorLexico(reader_a);
            lex.estilo.insertarCodigoPane(txtCode);
            sintactico_a = new AnalizadorSintactico(lex);

            try {
                sintactico_a.parse();
            } catch (Exception ex) {
                this.writeMessageInConsole(
                    "Se detectaron errores sintácticos. No se puede continuar",
                    TypeConsoleMessage.ERROR
                );
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            AnalizadorSemantico semantico = new AnalizadorSemantico();

            semantico.rastrearProduccion("DECLARACION", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("ASIGNACION", sintactico_a.nodoPrincipal, "IDENTIFIER");

            semantico.rastrearProduccion("DECL_ASIG_CICLO", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("ASIGNACION_CICLO", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("VARIABLE_DA", sintactico_a.nodoPrincipal, "IDENTIFIER");

            semantico.rastrearProduccion("TIPO_VALOR", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("LEER_DATO", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("ASIGNACION_INC_DEC", sintactico_a.nodoPrincipal, "IDENTIFIER");

            semantico.rastrearProduccion("CONDICION_BAJA", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("CONDICION_MEDIA", sintactico_a.nodoPrincipal, "IDENTIFIER");
            semantico.rastrearProduccion("CONDICION_ALTA", sintactico_a.nodoPrincipal, "IDENTIFIER");

            if (!semantico.listaErrores.isEmpty()) {
                splitPaneCentral.getBottomComponent().setVisible(true);
                splitPaneCentral.setDividerLocation(0.60);
                String errores = "";
                for (int i = 0; i < semantico.listaErrores.size(); i++) {
                    errores = semantico.listaErrores.get(i) + "\n";
                }
                this.writeMessageInConsole(errores, TypeConsoleMessage.ERROR);
            } else {
                this.writeMessageInConsole("Análisis semántico finalizado correctamente", TypeConsoleMessage.INFO);
            }

        }
    }//GEN-LAST:event_btnAnalizadorSemanticoActionPerformed

    private void btnGeneradorCodigoIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneradorCodigoIntermedioActionPerformed
        
        if (this.arbolSintactico == null) {
            this.writeMessageInConsole("Ejecuta el analizador sintactico primero", TypeConsoleMessage.ERROR);
            return;
        }
        
        if (this.arbolSintactico != null) {
            CodigoIntermedio ci = new CodigoIntermedio();
            ci.recorrerArbolSintactico(this.arbolSintactico);
            ci.imprimirCodigo();
            ModalCuadruplos vCodigo = new ModalCuadruplos(ci.getCodigo());
            
            Identifier[] identifiers = ci.getIdentifiers();
            for(Identifier ident : identifiers) {
                System.out.println(ident);
            }
            ModalIdentifiers vIdentifiers = new ModalIdentifiers(identifiers);
            
            vIdentifiers.show();
            vCodigo.show();
        }
    }//GEN-LAST:event_btnGeneradorCodigoIntermedioActionPerformed

//    private String mapearArbol(Nodo raiz) {
//        String datos = "";
//        //es raiz?
//        if (!raiz.getHojas().isEmpty()) {
//            for (Nodo hoja : raiz.getHojas()) {
//                String nodoHoja = mapearArbol(hoja);
//                String  nodoRaiz = tipoNodo(raiz) + " -> " + tipoNodo(hoja) + ";";
//                datos += nodoRaiz+"\n";
//                datos += nodoHoja+"\n";
//            }
//        }
//        return datos;
//    }
//    
//    private String tipoNodo(Nodo n) {
//        String datos = "";
//        if (n.getValor().isEmpty()) {
//            //Raiz
//            datos = "\""+ n.getNombreRaiz() + "\"";
//        } else {
//            //Hoja
//            datos += "\"" + n.getNombreRaiz() + "\n<" + n.getValor() + "> \"";
//        }
//        return datos;
//    }
    private boolean errorLexico() {
        String codigo = txtCode.getText();

        Reader reader = new BufferedReader(new BufferedReader(new StringReader(codigo)));

        AnalizadorLexico lexico = new AnalizadorLexico(reader);
        lexico.estilo.insertarCodigoPane(txtCode);
        while (true) {
            try {
                lexico.next_token();
                if (lexico.yytext().equals("")) {
                    break;
                }
                if (lexico.nameToken.equals("ERROR")) {
                    System.out.println(lexico.numLinea());
                    this.writeMessageInConsole(
                        "Token no identificado.\n\tLinea: " + lexico.numLinea() + "\n\tValor: " + lexico.yytext(),
                        TypeConsoleMessage.ERROR
                    );
                    return true;
                }
            } catch (IOException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return false;
    }

    class ThreadNumberCode extends Thread {

        private String salto = System.getProperty("line.separator");

        @Override
        public void run() {
            while (true) {
                try {
                    String lineas = txtCode.getText();
                    int l = recorrerCaracteres(lineas);
                    String numeros = "";
                    for (int i = 1; i <= l; i++) {
                        numeros += i + "\n";
                    }
                    txtNumLinea.setText(numeros);
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private int recorrerCaracteres(String codigo) {
            int s = 1;
            for (int i = 0; i < codigo.length(); i++) {
                int c = codigo.charAt(i);
                if (c == 10) {
                    s++;
                }
            }
            return s;
        }
    }

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
                //System.out.println(info.getName());
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelControlesSintactico;
    private javax.swing.JPanel PnlToken;
    private javax.swing.JTable TblAnalisis;
    private javax.swing.JButton btnAnalizadorLexico;
    private javax.swing.JButton btnAnalizadorSemantico;
    private javax.swing.JButton btnAnalizadorSintactico;
    private javax.swing.JButton btnGeneradorCodigoIntermedio;
    private javax.swing.JPanel consola;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelEditor;
    private javax.swing.JSplitPane splitPaneCentral;
    private javax.swing.JSplitPane splitPaneEC;
    private javax.swing.JTextPane txtCode;
    private javax.swing.JTextPane txtErrores;
    private javax.swing.JTextPane txtNumLinea;
    // End of variables declaration//GEN-END:variables
}
