
package Intermedio;

public class Cuadruplo {
    private final String operator;
    private final String arg0;
    private final String arg1;
    private final String res;
    
    public Cuadruplo(String operator, String arg0, String arg1, String res) {
        this.operator = operator;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.res = res;
        System.out.println(this.toString());
    }

    public String getOperator() {
        return operator;
    }

    public String getArg0() {
        return arg0;
    }

    public String getArg1() {
        return arg1;
    }

    public String getRes() {
        return res;
    }

    @Override
    public String toString() {
        return operator + "\t" + arg0 + "\t" + arg1 + "\t" + res;
    }
    
    
}
