package Objeto;

import Intermedio.Cuadruplo;
import Intermedio.Identifier;
import Utils.NumberUtils;

public final class ModalObjeto extends javax.swing.JFrame {
    
    private String binary;
    private String instruction;
    private String hexadecimal;
    private Identifier[] identifiers;
    private Cuadruplo[] cuadruplos;

    /**
     * Creates new form ModalObjeto
     * @param cuadruplos
     * @param identifiers
     */
    public ModalObjeto(Cuadruplo[] cuadruplos, Identifier[] identifiers) {
        initComponents();
        
        this.binary = "";
        this.instruction = "";
        this.hexadecimal = "";
        
        this.identifiers = identifiers;
        this.cuadruplos = cuadruplos;
        
        for(Cuadruplo cuadruplo: this.cuadruplos) {
            if(cuadruplo.getOperator().equals("ADD")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());

                // ADD EAX, EBX*
                // ADD reg, reg
                // 000000dw oorrrmmm
                this.addInstruction("ADD EAX, EBX");
                this.addBinary("00000011 11000011");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.endInstruction();
                this.endBinary();
            }
            
            // RESTA
            if (cuadruplo.getOperator().equals("MIN")) {
                
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());
                
                // SUB EAX, EBX
                // 000101dw oorrrmmm
                this.addInstruction("SUB EAX, EBX");
                this.addBinary("00010111 11000011");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.endInstruction();
                this.endBinary();
            }
            
            
            if (cuadruplo.getOperator().equals("MULT")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());
                
                // IMUL reg, reg
                // 00001111 10101111 oorrrmmm
                this.addInstruction("IMUL EAX, EBX");
                this.addBinary("00001111 10101111 11000011");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("DIV")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());
                
                // http://www.c-jump.com/CIS77/MLabs/M11arithmetic/M11_0120_idiv_instruction.htm
                // CDQ
                // 11010100 00001010
                this.addInstruction("CDQ");
                this.addBinary("11010100 00001010");
                
                // IDIV EBX
                // 1111011w oo111mmm
                this.addInstruction("IDIV EBX");
                this.addBinary("11110111 11111011");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.endInstruction();
                this.endBinary();
            }


            // VAR1 = VAR2
            if (cuadruplo.getOperator().equals("MOV")) {

                // setValorVariableOnEBX(cuadruplo.getArg1());
                
                this.setValueOn("EAX", cuadruplo.getArg1());

                // MOV EAX, EBX
                // 100010dw oorrrmmm
                // this.addBinary("MOV EAX, EBX\n");
                // this.addBinary("10001011 11000011\n");
                
                this.saveValueOnMemory("EAX", cuadruplo.getArg0());
                
                this.endInstruction();
                this.endBinary();

                /*
                this.setRefVariableIn("EDX", cuadruplo.getArg0());

                // MOV [EDX], EAX
                // 100010dw oorrrmmm
                this.addBinary("MOV [EDX], EAX\n");
                this.addBinary("10001001 11000010\n");                
                */
            }
            
            if (cuadruplo.getOperator().equals("CMP")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                // CMP
                // 100000sw oo111mmm
                this.addInstruction("CMP EAX, 0");
                this.addBinary("11110111 11111011 00000000");
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("JE")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JE
                // 01110100
                this.addInstruction("JE "+cuadruplo.getArg0());
                this.addBinary("01110100 "+this.parseDecimalToBinaryNotation(position));
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("JL")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JL
                // 01111100
                this.addInstruction("JL "+cuadruplo.getArg0());
                this.addBinary("01111100 "+this.parseDecimalToBinaryNotation(position));
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("JLE")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JLE
                // 01111110
                this.addInstruction("JLE "+cuadruplo.getArg0());
                this.addBinary("01111110 "+this.parseDecimalToBinaryNotation(position));
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("JG")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JG
                // 01111111
                this.addInstruction("JG "+cuadruplo.getArg0());
                this.addBinary("01111111 "+this.parseDecimalToBinaryNotation(position));
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("JGE")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JGE
                // 01111101
                this.addInstruction("JGE "+cuadruplo.getArg0());
                this.addBinary("01111101 "+this.parseDecimalToBinaryNotation(position));
                
                this.endInstruction();
                this.endBinary();
            }
            
            if (cuadruplo.getOperator().equals("GOTO")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JE
                // 01110100
                this.addInstruction("JUMP "+cuadruplo.getArg0());
                this.addBinary("11101001 "+this.parseDecimalToBinaryNotation(position));
                
                this.endInstruction();
                this.endBinary();
            }
        }
        
        textInstruccion.setText(this.getInstruction());
        textObjeto.setText(this.getBinary());
        textHexadecimal.setText(this.getHexadecimal());
        
    }
    
    public int findLabel(String label) {
        int position = 0;
                
        for(int i = 0; i < this.cuadruplos.length; i++) {
            if(
                this.cuadruplos[i].getOperator().equals("LABEL") 
                && this.cuadruplos[i].getArg0().equals(this.cuadruplos[i].getArg0())
            ) {
                position = i;
            }
        }

        return position;
    }
    
    // SUSTITUIR POR SET DATA ON
    public void resetEAX() {
        // MOV EAX, 0
        // MOV reg, imm
        // 1011wrrr datos
        this.addInstruction("MOV EAX, 0");
        this.addBinary("10111000 00000000 00000000 00000000 00000000");
    }
    
    public void setDataOn(String ref, String value) {
        String dato = this.parseDecimalToBinaryNotation(value);
        // MOV reg, imm
        // 1011wrrr datos
        this.addInstruction("MOV " +ref+", "+dato);
        this.addBinary("10111"+ this.getRegister(ref) +" "+dato);
    }
    
    public void setValueOn(String ref, String value) {
        if (NumberUtils.isNumeric(value)) {
                    
            this.setDataOn(ref, value);

        } else {
            this.setRefVariableIn("ECX", value);

            // MOV EAX, [ECX]
            // 100010dw oorrrmmm
            this.addInstruction("MOV "+ref+", [ECX]");
            this.addBinary("10001011 11"+this.getRegister(ref)+"001");
        }
    }
    
    public void saveValueOnMemory(String ref, String momery) {
        this.setRefVariableIn("ECX", momery);
                
        // MOV [ECX], rrr
        // 100010dw oorrrmmm
        this.addInstruction("MOV [ECX], "+ref);
        this.addBinary("10001001 11"+this.getRegister(ref)+"001");
    }
    
    public void sumOnEAX(String variableOrNumber) {
        
        if (NumberUtils.isNumeric(variableOrNumber)) {
            String dato = this.parseDecimalToBinaryNotation(variableOrNumber);

            // ADD EAX, arg1*
            // ADD reg, imm
            // 100000sw oo000mmm datos
            this.addInstruction("ADD EAX, imm");
            this.addBinary("10000001 11000000 " + dato);

        } else {

            setValorVariableOnEBX(variableOrNumber);

            // ADD EAX, EBX*
            // ADD reg, reg
            // 000000dw oorrrmmm
            this.addInstruction("ADD EAX, EBX");
            this.addBinary("00000011 11000011");
        }
        
    }
    
    public void setRefVariableIn(String ref, String variable) {
        int position = 0;
                        
        for(Identifier identifier: this.identifiers) {
            if(identifier.getName().equals(variable)) {
                position = identifier.getPos();
            }
        }
        
        // MOV EDX, 
        // MOV reg, imm
        // 1011wrrr datos
        this.addInstruction("MOV "+ ref +", pos("+position+")");
        this.addBinary("10111"+ this.getRegister(ref) +" "+ this.parseDecimalToBinaryNotation(position));
        
    }
    
    public String getRegister(String reg) {
        if (reg.equals("EAX")) {
            return "000";
        }
        if (reg.equals("ECX")) {
            return "001";
        }
        if (reg.equals("EDX")) {
            return "010";
        }
        if (reg.equals("EBX")) {
            return "011";
        }
        return null;
    }
    

    
    public void setValorVariableOnEBX(String variable) {
        this.setRefVariableIn("EDX", variable);
        
        // MOV EBX, [EDX]
        // MOV reg, mem
        // 100010dw oorrrmmm
        this.addInstruction("MOV EBX, [EDX]");
        this.addBinary("10001011 11011010");
    }
    
    public void endInstruction() {
        this.addInstruction("");
    }
    
    public void endBinary() {
        this.addBinary("");
    }
    
    public void addInstruction(String instruction) {
        this.instruction += instruction + "\n";
    }
    
    public void addBinary(String binary) {
        this.binary += binary + "\n";
        
        String[] bytesString = binary.split(" ");
        
        for(String byteString: bytesString) {
            
            if (!Utils.NumberUtils.isNumeric(byteString)) {
                continue;
            }

            int decimal = Integer.parseInt(byteString, 2);
            String hexStr = Integer.toString(decimal, 16);
            
            if (hexStr.length() % 2 != 0) {
                hexStr = "0" + hexStr;
            }
            
            for(int i = 0; i < hexStr.length(); i += 2) {
                this.hexadecimal += hexStr.substring(i,2) + " ";
            }
        }
        
        this.hexadecimal += "\n";
    }
    
    public String getHexadecimal() {
        return this.hexadecimal;
    }
   
    public String getInstruction() {
        return this.instruction;
    }
    
    public String getBinary() {
        return this.binary;
    }
    
    public String parseDecimalToBinaryNotation(String integer) {
        int numberDecimal = Integer.parseInt(integer);
        return this.parseDecimalToBinaryNotation(numberDecimal);
    }
    
    public String parseDecimalToBinaryNotation(int integer) {
        
        String hexString = Integer.toString(integer,16);
        
        String binary = "";
        
        // REVES
        for (int i=0; i < hexString.length(); i += 2){
            String byteString;
            if (i + 2 >= hexString.length()) {
                byteString =  hexString.substring(i,1);
            } else {
                byteString =  hexString.substring(i,2);
            }
            
            binary += Integer.toBinaryString(Integer.parseInt(byteString, 16));
        }
        
        return this.padLeftZeros(binary, 8);
    }
    
    
    
    public String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textObjeto = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        textHexadecimal = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        textInstruccion = new javax.swing.JTextPane();

        textObjeto.setEditable(false);
        jScrollPane1.setViewportView(textObjeto);

        textHexadecimal.setEditable(false);
        jScrollPane2.setViewportView(textHexadecimal);

        textInstruccion.setEditable(false);
        jScrollPane4.setViewportView(textInstruccion);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
            .addComponent(jScrollPane2)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane textHexadecimal;
    private javax.swing.JTextPane textInstruccion;
    private javax.swing.JTextPane textObjeto;
    // End of variables declaration//GEN-END:variables
}
