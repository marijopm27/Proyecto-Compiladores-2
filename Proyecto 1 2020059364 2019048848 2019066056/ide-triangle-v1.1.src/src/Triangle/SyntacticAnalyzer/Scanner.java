/*
 * @(#)Scanner.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;

import ArchivosSalida.ArchivoHTML;
public final class Scanner {

  private SourceFile sourceFile;
  private boolean debug;

  private char currentChar;
  private StringBuffer currentSpelling;
  private boolean currentlyScanningToken;
  private ArchivoHTML archivoHTML;

  private boolean isLetter(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean isDigit(char c) {
    return (c >= '0' && c <= '9');
  }

// isOperator returns true iff the given character is an operator character.

  private boolean isOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/' ||
	    c == '=' || c == '<' || c == '>' || c == '\\' ||
	    c == '&' || c == '@' || c == '%' || c == '^' ||
	    c == '?');
  }


///////////////////////////////////////////////////////////////////////////////
  public Scanner(SourceFile source) {
    sourceFile = source;
    currentChar = sourceFile.getSource();
    debug = false;
  }

  public Scanner(SourceFile source, ArchivoHTML archivo) {
    sourceFile = source;
    currentChar = sourceFile.getSource();
    this.archivoHTML = archivo;    //Autor: María José Porras Maroto
    debug = false;
  }
  public void enableDebugging() {
    debug = true;
  }

  // takeIt appends the current character to the current token, and gets
  // the next character from the source program.

  private void takeIt() {
    if (currentlyScanningToken)
      currentSpelling.append(currentChar);
    currentChar = sourceFile.getSource();
  }

  // scanSeparator skips a single separator.

  private void scanSeparator() {
    String comentario = " <font style='padding-left:1em' color=\"#50C878\"><tt> ! ";
    switch (currentChar) {
    case '!':
      {
        takeIt();
        while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT)){
            comentario += currentChar;
            takeIt();  
        }
        if (currentChar == SourceFile.EOL){
          comentario += "</tt></font><br></br>";
          archivoHTML.escribir(comentario);
          takeIt();
          break;
        }
        comentario += "</tt></font><br></br>";
        archivoHTML.escribir(comentario);
      }
      break;

    case ' ': case '\r': 
      takeIt();
      archivoHTML.escribir("&nbsp;");
      break;
      
    case '\n':
        takeIt();
        archivoHTML.escribir("<br></br>");
        break;
        
    case '\t':
        takeIt();
        archivoHTML.escribir("&nbsp;&nbsp;&nbsp;&nbsp;");
    }
  }

  private int scanToken() {
    String linea;
    switch (currentChar) {

    case 'a':  case 'b':  case 'c':  case 'd':  case 'e':
    case 'f':  case 'g':  case 'h':  case 'i':  case 'j':
    case 'k':  case 'l':  case 'm':  case 'n':  case 'o':
    case 'p':  case 'q':  case 'r':  case 's':  case 't':
    case 'u':  case 'v':  case 'w':  case 'x':  case 'y':
    case 'z':
    case 'A':  case 'B':  case 'C':  case 'D':  case 'E':
    case 'F':  case 'G':  case 'H':  case 'I':  case 'J':
    case 'K':  case 'L':  case 'M':  case 'N':  case 'O':
    case 'P':  case 'Q':  case 'R':  case 'S':  case 'T':
    case 'U':  case 'V':  case 'W':  case 'X':  case 'Y':
    case 'Z':
      takeIt();
      while (isLetter(currentChar) || isDigit(currentChar))
        takeIt();

      return Token.IDENTIFIER;

    case '0':  case '1':  case '2':  case '3':  case '4':
    case '5':  case '6':  case '7':  case '8':  case '9':
      takeIt();
      while (isDigit(currentChar))
        takeIt();

      return Token.INTLITERAL;

    case '+':  case '-':  case '*': case '/':  case '=':
    case '<':  case '>':  case '\\':  case '&':  case '@':
    case '%':  case '^':  case '?':
      takeIt();
      while (isOperator(currentChar))
        takeIt();

      return Token.OPERATOR;

    case '\'':
      takeIt();
      takeIt(); // the quoted character
      if (currentChar == '\'') {
      	takeIt();

        return Token.CHARLITERAL;
      } else
        return Token.ERROR;

    case '.':
      takeIt();
      if (currentChar == '.') {   //Autor: María José Porras Maroto
        takeIt();
        archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ ".." +" </tt></font> ");
        return Token.DOTDOT; //Se agrego el ..
      } else
        archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "." +" </tt></font> ");
        return Token.DOT;

    case ':':
      takeIt();
      if (currentChar == '=') {    //Autor: María José Porras Maroto
        takeIt();
        archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ ":=" +" </tt></font> ");
        return Token.BECOMES; //Se agregó :=
      } else
        archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ ":" +" </tt></font> ");
        return Token.COLON;

    case ';':
      takeIt();   //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ ";" +" </tt></font> ");
      return Token.SEMICOLON;

    case ',':
      takeIt();   //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "," +" </tt></font> ");
      return Token.COMMA;

    case '~':
      takeIt();   //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "~" +" </tt></font> ");
      return Token.IS;
      
      //Se agregó BAR 
    case '|':    //Autor: María José Porras Maroto
      takeIt();
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "|" +" </tt></font> ");
      return Token.BAR;  
      
      //Se agregó DOLLAR
    case '$':    //Autor: María José Porras Maroto
      takeIt();
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "$" +" </tt></font> ");
      return Token.DOLLAR; 
      
    case '(':
      takeIt();   //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "(" +" </tt></font> ");
      return Token.LPAREN;

    case ')':
      takeIt();   //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ ")" +" </tt></font> ");
      return Token.RPAREN;

    case '[':
      takeIt();    //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "[" +" </tt></font> ");
      return Token.LBRACKET;

    case ']':
      takeIt();    //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "]" +" </tt></font> ");
      return Token.RBRACKET;

    case '{':
      takeIt();   //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "{" +" </tt></font> ");
      return Token.LCURLY;

    case '}':
      takeIt(); //Autor: María José Porras Maroto
      archivoHTML.escribir(" <font style='padding-left:1em' color:'black'><tt> "+ "}" +" </tt></font> ");
      return Token.RCURLY;

    case SourceFile.EOT:
      return Token.EOT;

    default:
      takeIt();
      return Token.ERROR;
    }
  }

  public Token scan () {
    Token tok;
    SourcePosition pos;
    int kind;
    currentlyScanningToken = false;
    while (currentChar == '!'
           || currentChar == ' '
           || currentChar == '\n'
           || currentChar == '\r'
           || currentChar == '\t')
      scanSeparator();

    currentlyScanningToken = true;
    currentSpelling = new StringBuffer("");
    pos = new SourcePosition();
    pos.start = sourceFile.getCurrentLine();

    kind = scanToken();

    pos.finish = sourceFile.getCurrentLine();
    tok = new Token(kind, currentSpelling.toString(), pos); 
    archivoHTML.HTML(tok); //Autor: María José Porras Maroto
    if (debug)
      System.out.println(tok);
    return tok;
  }

}
