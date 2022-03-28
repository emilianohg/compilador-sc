package Lexico;

import java_cup.runtime.*;
import java.io.Reader;
import Sintactico.sym;
%%
%public
%class AnalizadorLexico
%cup



%line
%column
%char

Letras=[a-zA-Z_]
Digitos=[0-9]
Simbolo=[]
Operador=[*+/-]
Agrupacion=[( ) { } ,]
Simbolo=[ ? # $ % & ´ \[ \] \~ : = \( \) \{ \} \< \> \. \\ \/ \! ' \| - _]


ESPACIO=[ \t\r\n]

%{


public PaintTextBox estilo = new PaintTextBox();

public String nameToken = null;
public int numLinea(){
    return yyline + 1;
}

public String getCodigo(){
    return this.zzReader.toString();
}

%}
%%

{ESPACIO} {/* ignore */}
"//".* {/* ignore */}

"var"
{
    nameToken = "VARIABLE";
    return new Symbol(sym.VARIABLE, yychar, yyline,new String(yytext()));
}

"Grupo"
{
    nameToken = "PAL_GRUPO";
    return new Symbol(sym.PAL_GRUPO, yychar, yyline,new String(yytext()));
}

"leer"
{       
    nameToken = "LEER"; 
    return new Symbol(sym.LEER, yychar, yyline,new String(yytext()));
}

"imprimir"
{
    nameToken = "IMPRIMIR"; 
    return new Symbol(sym.IMPRIMIR, yychar, yyline,new String(yytext()));
}

"repetir"
{   
    nameToken = "CICLO_REPETIR"; 
    return new Symbol(sym.CICLO_REPETIR, yychar, yyline,new String(yytext()));
}

"hasta"
{   
    nameToken = "REPETIR_HASTA"; 
    return new Symbol(sym.REPETIR_HASTA, yychar, yyline,new String(yytext()));
}

"hacer"
{
    nameToken = "INICIO_CICLO"; 
    return new Symbol(sym.INICIO_CICLO, yychar, yyline,new String(yytext()));
}

"fin_repetir"
{
    nameToken = "REPETIR_FIN"; 
    return new Symbol(sym.REPETIR_FIN, yychar, yyline,new String(yytext()));
}




"mientras"
{
    nameToken = "CICLO_MIENTRAS";
    return new Symbol(sym.CICLO_MIENTRAS, yychar, yyline,new String(yytext()));  
}

"fin_mientras"
{
   nameToken = "FIN_MIENTRAS";
   return new Symbol(sym.FIN_MIENTRAS, yychar, yyline,new String(yytext()));   
}

"!="|"=="|"!"|">"|"<"|"<="|">="|"!=" 
{
 
    nameToken = "OPER_REL";
    return new Symbol(sym.OPER_REL, yychar, yyline,new String(yytext()));
}

"if"
{
    nameToken = "IF_STATEMENT";
    return new Symbol(sym.IF_STATEMENT, yychar, yyline,new String(yytext()));
}

"entonces"
{
    nameToken = "CONDICION_INICIO";
    return new Symbol(sym.CONDICION_INICIO, yychar, yyline,new String(yytext()));
}

"else"
{
    nameToken = "ELSE_STATEMENT";
    return new Symbol(sym.ELSE_STATEMENT, yychar, yyline,new String(yytext()));
}

"fin_si"
{
    nameToken = "CONDICION_FIN";
    return new Symbol(sym.CONDICION_FIN, yychar, yyline,new String(yytext()));
}


"+"
 {
    nameToken = "OPER_ART_SUMA";    
    return new Symbol(sym.OPER_ART_SUMA, yychar, yyline,new String(yytext()));
}

"-"
 {
    nameToken = "OPER_ART_RESTA";    
    return new Symbol(sym.OPER_ART_RESTA, yychar, yyline,new String(yytext()));
}


"*"
 {
    nameToken = "OPER_ART_MUL";    
    return new Symbol(sym.OPER_ART_MUL, yychar, yyline,new String(yytext()));
}


"/"
 {
    nameToken = "OPER_ART_DIV";    
    return new Symbol(sym.OPER_ART_DIV, yychar, yyline,new String(yytext()));
}

"="
{
    nameToken = "OPER_ASIG_IGUAL";
    return new Symbol(sym.OPER_ASIG_IGUAL, yychar, yyline,new String(yytext()));
}

"true"|"false"
{
    nameToken = "VALOR_LOG";
    return new Symbol(sym.VALOR_LOG, yychar, yyline,new String(yytext()));
}

"inc"|"dec"
{
    
    nameToken = "OPER_ASIG_InDe";
    return new Symbol(sym.OPER_ASIG_InDe, yychar, yyline,new String(yytext()));
}

"(" 
{    
    nameToken = "PAREN_OPEN";
    return new Symbol(sym.PAREN_OPEN, yychar, yyline,new String(yytext()));
}

")" 
{
    nameToken = "PAREN_CLOSE";
    return new Symbol(sym.PAREN_CLOSE, yychar, yyline,new String(yytext()));
}


"{" 
{   
    nameToken = "CURLY_BRACE_OPEN";
    return new Symbol(sym.CURLY_BRACE_OPEN, yychar, yyline,new String(yytext()));
}

"}" 
{
    nameToken = "CURLY_BRACE_CLOSE";
    return new Symbol(sym.CURLY_BRACE_CLOSE, yychar, yyline,new String(yytext()));
}


"[" 
{
    nameToken = "OPER_AGRUP_CI";
    return new Symbol(sym.OPER_AGRUP_CI, yychar, yyline,new String(yytext()));
}

"]" 
{
    nameToken = "OPER_AGRUP_CF";
    return new Symbol(sym.OPER_AGRUP_CF, yychar, yyline,new String(yytext()));
}

"," 
{
    nameToken = "OPER_AGRUP_COMA";
    return new Symbol(sym.OPER_AGRUP_COMA, yychar, yyline,new String(yytext()));
}



{Digitos}{Digitos}* {
    nameToken = "NUMBER_INTEGER";
    return new Symbol(sym.NUMBER_INTEGER, yychar,yyline,new String(yytext()));
}


{Digitos}{Digitos}*"."{Digitos}{Digitos}* 
{
    nameToken = "NUMBER_FLOAT";
    return new Symbol(sym.NUMBER_FLOAT, yychar,yyline,new String(yytext()));
}


"\"" ({Letras}|{Digitos}|{Operador}|{Agrupacion}|{Simbolo})* "\"" 
{
    nameToken = "VAL_TEXT";
    return new Symbol(sym.VAL_TEXT, yychar,yyline,new String(yytext()));
}


{Letras}({Letras}|{Digitos})*
{
    nameToken = "IDENTIFIER";
    return new Symbol(sym.IDENTIFIER, yychar,yyline,new String(yytext()));
}

";"
{
    nameToken = "SEMICOLON";
    return new Symbol(sym.SEMICOLON, yychar, yyline,new String(yytext()));
}

. {
    nameToken = "ERROR";
    return new Symbol(sym.ERROR, yychar, yyline,new String(yytext()));
}