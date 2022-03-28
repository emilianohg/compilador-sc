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


public Pintar estilo = new Pintar();

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
    nameToken = "PAL_VARIABLE";
    return new Symbol(sym.PAL_VARIABLE, yychar, yyline,new String(yytext()));
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
    nameToken = "CONDICION_SI";
    return new Symbol(sym.CONDICION_SI, yychar, yyline,new String(yytext()));
}

"entonces"
{
    nameToken = "CONDICION_INICIO";
    return new Symbol(sym.CONDICION_INICIO, yychar, yyline,new String(yytext()));
}

"else"
{
    nameToken = "CONDICION_SINO";
    return new Symbol(sym.CONDICION_SINO, yychar, yyline,new String(yytext()));
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
    nameToken = "OPER_AGRUP_PI";
    return new Symbol(sym.OPER_AGRUP_PI, yychar, yyline,new String(yytext()));
}

")" 
{
    nameToken = "OPER_AGRUP_PF";
    return new Symbol(sym.OPER_AGRUP_PF, yychar, yyline,new String(yytext()));
}


"{" 
{   
    nameToken = "OPER_AGRUP_LLI";
    return new Symbol(sym.OPER_AGRUP_LLI, yychar, yyline,new String(yytext()));
}

"}" 
{
    nameToken = "OPER_AGRUP_LLF";
    return new Symbol(sym.OPER_AGRUP_LLF, yychar, yyline,new String(yytext()));
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
    nameToken = "VALOR_ENT";
    return new Symbol(sym.VALOR_ENT, yychar,yyline,new String(yytext()));
}


{Digitos}{Digitos}*"."{Digitos}{Digitos}* 
{
    nameToken = "VALOR_FLO";
    return new Symbol(sym.VALOR_FLO, yychar,yyline,new String(yytext()));
}


"\"" ({Letras}|{Digitos}|{Operador}|{Agrupacion}|{Simbolo})* "\"" 
{
    nameToken = "VALOR_STRING";
    return new Symbol(sym.VALOR_STRING, yychar,yyline,new String(yytext()));
}


{Letras}({Letras}|{Digitos})*
{
    nameToken = "NOMBRE_VAR";
    return new Symbol(sym.NOMBRE_VAR, yychar,yyline,new String(yytext()));
}

";"
{
    nameToken = "PUNTOYCOMA";
    return new Symbol(sym.PUNTOYCOMA, yychar, yyline,new String(yytext()));
}

. {
    nameToken = "ERROR";
    return new Symbol(sym.ERROR, yychar, yyline,new String(yytext()));
}