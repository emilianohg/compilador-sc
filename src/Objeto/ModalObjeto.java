package Objeto;

import Intermedio.Cuadruplo;
import Intermedio.Identifier;
import Utils.NumberUtils;

public final class ModalObjeto extends javax.swing.JFrame {
    
    private String binary;
    private String instruction;
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
        this.identifiers = identifiers;
        this.cuadruplos = cuadruplos;
        
        for(Cuadruplo cuadruplo: this.cuadruplos) {
            if(cuadruplo.getOperator().equals("ADD")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());

                // ADD EAX, EBX*
                // ADD reg, reg
                // 000000dw oorrrmmm
                this.addBinary("ADD EAX, EBX\n");
                this.addBinary("00000011 11000011\n");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.addBinary("\n");
            }
            
            // RESTA
            if (cuadruplo.getOperator().equals("MIN")) {
                
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());
                
                // SUB EAX, EBX
                // 000101dw oorrrmmm
                this.addBinary("SUB EAX, EBX\n");
                this.addBinary("00010111 11000011\n");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.addBinary("\n");
            }
            
            
            if (cuadruplo.getOperator().equals("MULT")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());
                
                // IMUL reg, reg
                // 00001111 10101111 oorrrmmm
                this.addBinary("IMUL EAX, EBX\n");
                this.addBinary("00001111 10101111 11000011\n");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("DIV")) {
                this.setValueOn("EAX", cuadruplo.getArg0());
                
                this.setValueOn("EBX", cuadruplo.getArg1());
                
                // http://www.c-jump.com/CIS77/MLabs/M11arithmetic/M11_0120_idiv_instruction.htm
                // CDQ
                // 11010100 00001010
                this.addBinary("CDQ\n");
                this.addBinary("11010100 00001010\n");
                
                // IDIV EBX
                // 1111011w oo111mmm
                this.addBinary("IDIV EBX\n");
                this.addBinary("11110111 11111011\n");
                
                this.saveValueOnMemory("EAX", cuadruplo.getRes());
                
                this.addBinary("\n");
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
                
                this.addBinary("\n");

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
                this.addBinary("CMP EAX, 0\n");
                this.addBinary("11110111 11111011 00000000\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("JE")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JE
                // 01110100
                this.addBinary("JE "+cuadruplo.getArg0()+"\n");
                this.addBinary("01110100 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("JL")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JL
                // 01111100
                this.addBinary("JL "+cuadruplo.getArg0()+"\n");
                this.addBinary("01111100 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("JL")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JL
                // 01111100
                this.addBinary("JL "+cuadruplo.getArg0()+"\n");
                this.addBinary("01111100 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("JLE")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JLE
                // 01111110
                this.addBinary("JLE "+cuadruplo.getArg0()+"\n");
                this.addBinary("01111110 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("JG")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JG
                // 01111111
                this.addBinary("JG "+cuadruplo.getArg0()+"\n");
                this.addBinary("01111111 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("JGE")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JGE
                // 01111101
                this.addBinary("JGE "+cuadruplo.getArg0()+"\n");
                this.addBinary("01111101 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
            
            if (cuadruplo.getOperator().equals("GOTO")) {
                int position = this.findLabel(cuadruplo.getArg0());
                // JE
                // 01110100
                this.addBinary("JUMP "+cuadruplo.getArg0()+"\n");
                this.addBinary("11101001 "+this.parseDecimalToBinaryNotation(position)+"\n");
                
                this.addBinary("\n");
            }
                
        }
        
        textObjeto.setText(this.getBinary());
        
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
        this.addBinary("MOV EAX, 0\n");
        this.addBinary("10111000 00000000 00000000 00000000 00000000\n");
    }
    
    public void setDataOn(String ref, String value) {
        String dato = this.parseDecimalToBinaryNotation(value);
        // MOV reg, imm
        // 1011wrrr datos
        this.addBinary("MOV " +ref+", "+dato+"\n");
        this.addBinary("10111"+ this.getRegister(ref) +" "+dato+"\n");
    }
    
    public void setValueOn(String ref, String value) {
        if (NumberUtils.isNumeric(value)) {
                    
            this.setDataOn(ref, value);

        } else {
            this.setRefVariableIn("ECX", value);

            // MOV EAX, [ECX]
            // 100010dw oorrrmmm
            this.addBinary("MOV "+ref+", [ECX]\n");
            this.addBinary("10001011 11"+this.getRegister(ref)+"001\n");
        }
    }
    
    public void saveValueOnMemory(String ref, String momery) {
        this.setRefVariableIn("ECX", momery);
                
        // MOV [ECX], rrr
        // 100010dw oorrrmmm
        this.addBinary("MOV [ECX], "+ref+"\n");
        this.addBinary("10001001 11"+this.getRegister(ref)+"001\n");
    }
    
    public void sumOnEAX(String variableOrNumber) {
        
        if (NumberUtils.isNumeric(variableOrNumber)) {
            String dato = this.parseDecimalToBinaryNotation(variableOrNumber);

            // ADD EAX, arg1*
            // ADD reg, imm
            // 100000sw oo000mmm datos
            this.addBinary("ADD EAX, imm\n");
            this.addBinary("10000001 11000000 " + dato + "\n");

        } else {

            setValorVariableOnEBX(variableOrNumber);

            // ADD EAX, EBX*
            // ADD reg, reg
            // 000000dw oorrrmmm
            this.addBinary("ADD EAX, EBX\n");
            this.addBinary("00000011 11000011\n");
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
        this.addBinary("MOV "+ ref +", pos("+position+")\n");
        this.addBinary("10111"+ this.getRegister(ref) +" "+ this.parseDecimalToBinaryNotation(position) +"\n");
        
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
        this.addBinary("MOV EBX, [EDX]\n");
        this.addBinary("10001011 11011010\n");
    }
    
    public void addInstruction(String instruction) {
        this.instruction += instruction;
    }
    
    public void addBinary(String binary) {
        this.binary += binary;
    }
    
    public String getBinary() {
        /*
        String parseBinary = this.binary.replaceAll("\\s", "");
        String finalBinary = "";
        for (int i = 0; i < parseBinary.length(); i += 8) {
            finalBinary += parseBinary.substring(i, 8) + " ";
        }
        */
        return this.binary;
    }
    
    public String parseDecimalToBinaryNotation(String integer) {
        int numberDecimal = Integer.parseInt(integer);
        return this.parseDecimalToBinaryNotation(numberDecimal);
    }
    
    public String parseDecimalToBinaryNotation(int integer) {
        
        String hexString = Integer.toString(integer,16);
        
        String binary = "";
        
        // HACERLO AL REVES
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

        jScrollPane1.setViewportView(textObjeto);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
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
    private javax.swing.JTextPane textObjeto;
    // End of variables declaration//GEN-END:variables
}
