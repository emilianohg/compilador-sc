package Generadores;

import java.io.File;

public class GenerarAnalizadorLexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "C:\\dev\\KidCode\\src\\Lexico\\Lexer.flex";
        generarLexer(path);
    }

    public static void generarLexer(String path) {
        File file = new File(path);

        jflex.Main.generate(file);
    }

    
}
