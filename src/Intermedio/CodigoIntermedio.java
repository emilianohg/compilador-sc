package Intermedio;

import Sintactico.Nodo;
import java.util.ArrayList;
import java.util.Stack;
import Posfija.Posfija;
import java.util.Optional;

public class CodigoIntermedio {

    private ArrayList<Cuadruplo> pila;
    private ArrayList<Identifier> identifiers;
    private int memoryStack;

    private int gvtemporal;
    private Nodo condicion_alta;
    private String nodoAnterior;
    private int getemporal;
    private Stack<Integer> labels;
    private Stack<String> tempIncCiclo;
    private Stack<String> tempVarCiclo;

    public CodigoIntermedio() {
        this.pila = new ArrayList<>();
        this.identifiers = new ArrayList<>();
        this.gvtemporal = -1;
        this.getemporal = -1;
        this.condicion_alta = new Nodo("", "", 0);
        this.nodoAnterior = "";
        this.labels = new Stack<>();
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
    
    private Identifier getIdentifier(String valor, String name) {
                
        if (valor.equals("true") || valor.equals("false")) {
            this.memoryStack += 1;
            return new Identifier("boolean", name, valor, 1, this.memoryStack - 1);
        }
        
        try { 
            Integer.parseInt(valor);
            this.memoryStack += 2;
            return new Identifier("int", name, valor, 2, this.memoryStack - 2);
        } catch(NumberFormatException e) {}
        
        try { 
            Double.parseDouble(valor);
            this.memoryStack += 4;
            return new Identifier("double", name, valor, 4, this.memoryStack - 4);
        } catch(NumberFormatException e) {}
        
        this.memoryStack += valor.length();
        
        return new Identifier("string", name, valor, valor.length(), this.memoryStack - valor.length());
    }
    
    private void saveIdentifier(String var, String valor) {
        Optional<Identifier> optIdent = this.identifiers.stream()
            .filter(p -> p.getName().equals(var))
            .findFirst();

        if (optIdent.isPresent()) {
            Identifier updated = this.getIdentifier(valor, var);
            Identifier ident = optIdent.get();
            ident.setValue(updated.getValue());
            ident.setSize(updated.getSize());
            ident.setPos(updated.getPos());
        } else {
            this.identifiers.add(
                this.getIdentifier(valor, var)
            );
        }
    }
    

    public void recorrerArbolSintactico(Nodo arbol) {
        Finder finder = new Finder();
        System.out.println(arbol.getNombreRaiz());

        switch (arbol.getNombreRaiz()) {
            
            case "ASIGNACION":
                Nodo listaVar = finder.rastrearNodo(arbol, "LISTA_VARIABLES");
                finder.cargarPilaConHojaEspecifica(listaVar, "IDENTIFIER");
                Stack<Object> variables = finder.getPila();

                finder = new Finder();
                Nodo exp_asig_basica = finder.rastrearNodo(arbol, "EXP_ASIG_BASICA");
                if (exp_asig_basica.getNombreRaiz().equals("")) {
                    Nodo tipo_valor = finder.rastrearNodo(arbol, "TIPO_VALOR");
                    finder.cargarPilaConHojas(tipo_valor);
                } else {
                    finder.cargarPilaConHojas(exp_asig_basica);
                }

                String exp = finder.getPilaString();
                
                Posfija pos = new Posfija();
                ArrayList<Object> expCov = pos.posfija(exp);
                
                if (expCov.size() == 1) {
                    
                    String var1 = ((Nodo) variables.pop()).getValor();
                    String valor = expCov.get(0).toString();
                    
                    this.saveIdentifier(var1, valor);
                    
                    this.pila.add(
                        new Cuadruplo(
                            "MOV",
                            var1,
                            valor,
                            "NULL"
                        )
                    );

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
                                    "t"+this.gvtemporal
                                )
                            );
                            auxpila.push("t" + this.gvtemporal);

                        } else {
                            auxpila.push(expCov.get(i).toString());
                        }
                    }
                    for (int i = 0; i < variables.size(); i++) {
                        String var1 = "t" + this.gvtemporal;
                        String valor = ((Nodo) variables.get(i)).getValor();
                        this.saveIdentifier(var1, "0.0");
                        this.pila.add(
                            new Cuadruplo(
                                "MOV",
                                "t" + this.gvtemporal,
                                valor,
                                "NULL"
                            )
                        );
                    }
                }

                break;
            case "CONDICION_ALTA":
                this.condicion_alta = arbol;
                break;

            case "CONDICION_BAJA":
                Nodo condicion_baja = arbol;
                finder.cargarPilaConHojas(condicion_baja);
                Stack<Object> parametros = finder.getPila();
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
                
                break;
            case "BEGIN_IF":
                this.getemporal++;
                String var1 = this.obtenerResultadoAnterior();
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
                this.labels.push(this.getemporal);
                break;
            case "BEGIN_ELSE":
                int label = this.labels.pop();
                
                this.getemporal++;
                this.labels.push(this.getemporal);
                
                
                this.pila.add(
                    new Cuadruplo(
                        "GOTO",
                        "L" + this.getemporal,
                        "NULL",
                        "NULL"
                    )
                );
                
                this.pila.add(
                    new Cuadruplo(
                        "LABEL",
                        "L" + label,
                        "NULL",
                        "NULL"
                    )
                );
                
                break;
            case "END_ELSE":
                int label2 = this.labels.pop();
                String fin_si = "LABEL \t L" + label2 + "\t NULL \t NULL";
                this.pila.add(
                    new Cuadruplo(
                        "LABEL",
                        "L" + label2,
                        "NULL",
                        "NULL"
                    )
                );
                break;
            case "LISTA_IMPRECIONES":
                finder.cargarPilaConHojas(arbol);
                for (int i = 0; i < finder.getPila().size(); i++) {
                    Nodo objTexto = (Nodo) finder.getPila().get(i);
                    if (!objTexto.getNombreRaiz().equals("OPER_AGRUP_COMA")) {
                        this.pila.add(
                            new Cuadruplo(
                                "PRINT",
                                "" + objTexto.getValor(),
                                "NULL",
                                "NULL"
                            )
                        );
                    }
                }
                finder.getPila().clear();
                return;
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
        /*
        this.identifiers.forEach((ident)-> {
            this.pila.add(
                0,
                new Cuadruplo(
                   "DECLARE",
                   ""+ident.getSize(),
                   ""+ident.getValue(),
                   ident.getName()
                )
            );
        });
        */
        return this.pila.toArray(new Cuadruplo[this.pila.size()]);
    }
    
    public Identifier[] getIdentifiers() {
        return this.identifiers.toArray(new Identifier[this.identifiers.size()]);
    }
}
