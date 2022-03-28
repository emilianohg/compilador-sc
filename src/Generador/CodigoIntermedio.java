package Generador;

import Sintactico.Nodo;
import java.util.ArrayList;
import java.util.Stack;
import Posfija.Posfija;

public class CodigoIntermedio {

    private ArrayList<String> codigo;
    private ArrayList<Cuadruplo> pila;
    private int gvtemporal;
    private Nodo condicion_alta;
    private String nodoAnterior;
    private int getemporal;
    private Stack<Integer> etiquetas;
    private Stack<String> tempIncCiclo;
    private Stack<String> tempVarCiclo;

    public CodigoIntermedio() {
        this.codigo = new ArrayList<>();
        this.pila = new ArrayList<>();
        this.gvtemporal = -1;
        this.getemporal = -1;
        this.condicion_alta = new Nodo("", "", 0);
        this.nodoAnterior = "";
        this.etiquetas = new Stack<>();
        this.tempIncCiclo = new Stack<>();
        this.tempVarCiclo = new Stack<>();
    }
    
    
    private String parseOperator(String op) {
        if(op.equals("*")) return "MULT";
        if(op.equals("/")) return "DIV";
        if(op.equals("+")) return "ADD";
        if(op.equals("-")) return "MIN";
        return "NULL";
    }
    

    public void recorrerArbolSintactico(Nodo arbol) {
        Buscador buscador = new Buscador();
        System.out.println(arbol.getNombreRaiz());

        switch (arbol.getNombreRaiz()) {
            
            case "VARIABLE_DA":
                buscador.cargarPilaConHojas(arbol);
                if(buscador.getPila().size() == 4){
                    buscador.getPila().remove(0);
                }
                this.tempVarCiclo.push(((Nodo)buscador.getPila().get(0)).getValor());
                String iterador = buscador.getPilaString();
                this.codigo.add(iterador+";");
                break;
            case "R_INCDEC":
                buscador.cargarPilaConHojas(arbol);
                String inc_dec = ((Nodo)buscador.getPila().get(0)).getValor();
                buscador.getPila().remove(0);
                if(inc_dec.equals("inc")){
                    inc_dec = "+";
                }else{
                    inc_dec = "-";
                }
                this.gvtemporal++;
                String var = this.tempVarCiclo.pop();
                String codigoTemp = "t"+this.gvtemporal+"="+var+inc_dec+buscador.getPilaString()+";";
                String remVar = var+"="+"t"+this.gvtemporal+";";
                this.tempIncCiclo.push(codigoTemp+"\n"+remVar);
                
                break;            
            case "INICIO_CICLO":                
                this.getemporal++;
                String etiqueta_0 = "E" + this.getemporal + ":";                
                this.etiquetas.push(this.getemporal);
                this.getemporal++;                
                String condicion = "if false " + this.obtenerResultadoAnterior() + " goto E" + this.getemporal + ";";
                this.etiquetas.push(this.getemporal);
                this.codigo.add(etiqueta_0);
                this.codigo.add(condicion);                
                break;
            case "FIN_MIENTRAS":
            case "REPETIR_FIN":
                String etiqueta_fin = "E" + this.etiquetas.pop() + ":";
                String salto_fin_ciclo = "goto E" + this.etiquetas.pop() + ";";
                if(arbol.getNombreRaiz().equals("REPETIR_FIN")){
                    this.codigo.add(this.tempIncCiclo.pop());
                }
                this.codigo.add(salto_fin_ciclo);
                this.codigo.add(etiqueta_fin);

                break;
            case "ASIGNACION":
                Nodo listaVar = buscador.rastrearNodo(arbol, "LISTA_VARIABLES");
                //se localizan las variables y se guardan en una pila
                buscador.cargarPilaConHojaEspecifica(listaVar, "IDENTIFIER");
                //se obtine la pila con los nombres de variables
                Stack<Object> variables = buscador.getPila();

                //se resetea el buscador
                buscador = new Buscador();
                Nodo exp_asig_basica = buscador.rastrearNodo(arbol, "EXP_ASIG_BASICA");
                if (exp_asig_basica.getNombreRaiz().equals("")) {
                    //SE TRATA DE UNA ASIGNACION SIMPLE EJEMPLO: g = 0;
                    Nodo tipo_valor = buscador.rastrearNodo(arbol, "TIPO_VALOR");
                    //carga la hoja o valor
                    buscador.cargarPilaConHojas(tipo_valor);
                } else {
                    //carga las hojas u operandos a la pila del buscador
                    buscador.cargarPilaConHojas(exp_asig_basica);
                }

                //ASIGNACION
                String codigo = "";

                //Se convierte la exp a estring
                //buscador.imprimirPila();
                String exp = buscador.getPilaString();
                Posfija pos = new Posfija();
                //Se obtiene la exp en notacion posfija
                ArrayList<Object> expCov = pos.posfija(exp);
                if (expCov.size() == 1) {
                    String var1 = ((Nodo) variables.pop()).getValor();
                    this.pila.add(
                        new Cuadruplo(
                            "MOV",
                            var1,
                            expCov.get(0).toString(),
                            "NULL"
                        )
                    );
                    this.codigo.add("MOV \t" + var1 + "\t" + expCov.get(0).toString() + "\tNULL");

                } else {
                    Stack<Object> auxpila = new Stack<>();
                    for (int i = 0; i < expCov.size(); i++) {
                        if (esOperador(expCov.get(i).toString())) {
                            //operando operador operando
                            this.gvtemporal++;
                            String val2 = auxpila.pop().toString();
                            String val1 = auxpila.pop().toString();
                            //codigo += "t" + this.gvtemporal + " = " + val1 + expCov.get(i).toString() + val2 + ";\n";
                            this.pila.add(
                                new Cuadruplo(
                                    this.parseOperator(expCov.get(i).toString()),
                                    ""+val1,
                                    ""+val2,
                                    ""+this.gvtemporal
                                )
                            );
                            this.codigo.add(expCov.get(i).toString() + "\t " + val1 + "\t " + val2 + "\t t" + this.gvtemporal);
                            auxpila.push("t" + this.gvtemporal);

                        } else {
                            auxpila.push(expCov.get(i).toString());
                        }
                    }
                    for (int i = 0; i < variables.size(); i++) {
                        //codigo += ((Nodo) variables.get(i)).getValor() + " = t" + this.gvtemporal + ";\n";
                        this.pila.add(
                            new Cuadruplo(
                                "MOV",
                                ((Nodo) variables.get(i)).getValor(),
                                "t" + this.gvtemporal,
                                "NULL"
                            )
                        );
                        this.codigo.add("MOV \t " + ((Nodo) variables.get(i)).getValor() + "\t t" + this.gvtemporal + "\t NULL");
                    }
                }

                break;
            case "CONDICION_ALTA":
                this.condicion_alta = arbol;
                break;

            case "CONDICION_BAJA":
                Nodo condicion_baja = arbol;
                buscador.cargarPilaConHojas(condicion_baja);
                Stack<Object> parametros = buscador.getPila();
                this.gvtemporal++;
                String operando2 = ((Nodo) parametros.pop()).getValor();
                String operador = simOperRel(((Nodo) parametros.pop()).getValor());
                String operando1 = ((Nodo) parametros.pop()).getValor();
                
                this.pila.add(
                    new Cuadruplo(
                        "MIN",
                        ""+operando1,
                        ""+operando2,
                        "t" + this.gvtemporal
                    )
                );
                
                this.codigo.add("t" + this.gvtemporal + "=" + operando1 + operador + operando2 + ";");
                

//                if(this.condicion_alta.getNombreRaiz().equals("")){
//                    int aux = this.gvtemporal;
//                    this.gvtemporal++;
//                    this.codigo.add("t"+this.gvtemporal+"=t"+(aux-1)+(this.condicion_alta.getHojas().get(1).getValor())+"t"+(aux));
//                    this.condicion_alta = null;
//                }
                break;
            case "BEGIN_IF":
                this.getemporal++;
                String var1 = this.obtenerResultadoAnterior();
                String if_entonces = "if false " + var1 + " goto E" + this.getemporal + ";";
                this.codigo.add(if_entonces);
                this.pila.add(
                    new Cuadruplo(
                        "CMP",
                        "" + var1,
                        "0",
                        "NULL"
                    )
                );
                this.pila.add(
                    new Cuadruplo(
                        "JE",
                        "L" + this.getemporal,
                        "NULL",
                        "NULL"
                    )
                );
                this.etiquetas.push(this.getemporal);
                break;
            case "BEGIN_ELSE":
                int label = this.etiquetas.pop();
                String inicio = "LABEL L" + label + "\t NULL \t NULL";
                
                this.getemporal++;
                this.etiquetas.push(this.getemporal);
                
                String salto = "GOTO \t L" + this.getemporal + "\t NULL \t NULL";
                
                
                this.pila.add(
                    new Cuadruplo(
                        "GOTO",
                        "L" + this.getemporal,
                        "NULL",
                        "NULL"
                    )
                );
                
                this.codigo.add(salto);
                
                this.pila.add(
                    new Cuadruplo(
                        "LABEL",
                        "L" + label,
                        "NULL",
                        "NULL"
                    )
                );
                
                this.codigo.add(inicio);
                
                break;
            case "END_ELSE":
                int label2 = this.etiquetas.pop();
                String fin_si = "LABEL \t L" + label2 + "\t NULL \t NULL";
                this.pila.add(
                    new Cuadruplo(
                        "LABEL",
                        "L" + label2,
                        "NULL",
                        "NULL"
                    )
                );
                this.codigo.add(fin_si);
                break;
            case "LISTA_IMPRECIONES":
                buscador.cargarPilaConHojas(arbol);
                for (int i = 0; i < buscador.getPila().size(); i++) {
                    Nodo objTexto = (Nodo) buscador.getPila().get(i);
                    if (!objTexto.getNombreRaiz().equals("OPER_AGRUP_COMA")) {
                        this.codigo.add("write " + objTexto.getValor() + ";");
                    }

                }
                buscador.getPila().clear();
                return;
            case "ASIGNACION_INC_DEC":
                buscador.cargarPilaConHojas(arbol);
                buscador.getPila().pop();
                String variable = ((Nodo) buscador.getPila().pop()).getValor();
                String oper = ((Nodo) buscador.getPila().pop()).getValor();
                if (oper.equals("inc")) {
                    oper = "+";
                } else {
                    oper = "-";
                }
                this.gvtemporal++;
                this.codigo.add("t" + this.gvtemporal + "=" + variable + oper + "1;");
                this.codigo.add(variable + "=t" + this.gvtemporal + ";");
                break;
        }
        
        if (!arbol.getHojas().isEmpty()) {
            this.nodoAnterior = arbol.getNombreRaiz();
            if (arbol.getNombreRaiz().equals("CONDICION_ALTA")) {
                System.out.println("-------------------------------------------------");
            }
            for (Nodo hoja : arbol.getHojas()) {
                System.out.println(hoja.getValor());
                recorrerArbolSintactico(hoja);
            }
        }

    }

    private String obtenerResultadoAnterior() {
        Cuadruplo linea = this.pila.get(this.pila.size() - 1);        
        return linea.getRes();
    }

    private String simOperRel(String operador) {
        switch (operador) {
            case ">":
                return ">";
            case "<":
                return "<";
            case "==":
                return "==";
            case "!=":
                return "!=";
            case ">=":
                return ">=";
            case "<=":
                return "==";
            case "!":
                return "!";
        }
        return null;
    }

    private boolean esOperador(String valor) {
        if (valor.equals("+") || valor.equals("-")
                || valor.equals("*") || valor.equals("/")
                || valor.equals("^")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean esCaracterValido(String valor) {
        if (valor.equals("(") || valor.equals(")") || valor.equals("+")
                || valor.equals("-") || valor.equals("*") || valor.equals("/")
                || valor.equals("^")) {
            return false;
        }
        return true;
    }

    public void imprimirCodigo() {
        this.pila.forEach(System.out::println);
    }

    public Cuadruplo[] getCodigo() {
        return this.pila.toArray(new Cuadruplo[this.pila.size()]);
        /*
        for (Cuadruplo cuadruplo: this.pila) {
            aux += linea.toString() + "\n";
        }
        return aux;
        */
    }
}
