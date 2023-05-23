/*
 * @(#)Token.java                        2.1 2003/10/07
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


public final class Token extends Object {

  public int kind;
  public String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if ((kind == Token.IDENTIFIER)){
      int currentKind = firstReservedWord;
      boolean searching = true;

      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    LONG_IDENTIFIER = 3,
    OPERATOR	= 4,
          
//Autores: María José Porras Maroto, Celina Madrigal Murillo y Gabriel Mora Estribí
    // reserved words - must be in alphabetical order...
    ARRAY		= 5,
    //BEGIN		= 5,  Eliminamos begin
    CONST		= 6,
    DO			= 7,
    ELSE		= 8,
    END			= 9,
    FOR                 = 10,   //Agregamos FOR  
    FROM                = 11,   //Agregamos FROM          
    FUNC		= 12,
    IF			= 13,
    IN			= 14,
    LET			= 15,
    OF			= 16,
    PACKAGE             = 17,   //Agregamos PACKAGE
    PRIVATE             = 18,   //Agregamos PRIVATE          
    PROC		= 19,
    REC                 = 20,   //Agregamos REC
    RECORD		= 21,
    REPEAT              = 22,    //Agregamos REPEAT 
    SELECT              = 23,   //Agregamos SELECT    
    SKIP                = 24,   //Agregamos SKIP          
    THEN		= 25,
    TIMES               = 26,  //Agregamos TIMES
    TO                  = 27, //Agregamos TO
    TYPE		= 28,
    UNTIL               = 29,  //Agregamos UNTIL    
    VAR			= 30,
    WHEN                = 31, //Agregamos WHEN
    WHILE		= 32,
          
      
    // punctuation...
    DOT			= 33,
    COLON		= 34,
    SEMICOLON           = 35,
    COMMA		= 36,
    BECOMES		= 37,
    IS			= 38,
    BAR                 = 39, //Agregamos BAR "|"
    DOLLAR              = 40, //Agregamos DOLLAR
    DOTDOT              = 41, //Agregamos DOTDOT
          
    // brackets...
    LPAREN		= 42,
    RPAREN		= 43,
    LBRACKET            = 44,
    RBRACKET            = 45,
    LCURLY		= 46,
    RCURLY		= 47,

    // special tokens...
    EOT			= 48,
    ERROR		= 49;

  private static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<long_identifier>",
    "<operator>",
    "array",
    //"begin",
    "const",
    "do",
    "else",
    "end",
    "for",          //Agregamos FOR
    "from",         //Agregamos FROM
    "func",
    "if",
    "in",
    "let",
    "of",
    "package",      //Agregamos PACKAGE
    "private",      //Agregamos PRIVATE
    "proc",
    "rec",          ////Agregamos REC
    "record",
    "repeat",       //Agregamos REPEAT
    "select",       //Agregamos SELECT
    "skip",
    "then",
    "times",        //Agregamos TIMES
    "to",           //Agregamos TO
    "type",
    "until",        //Agregamos UNTIL
    "var",
    "when",         //Agregamos WHEN
    "while",
    ".",
    ":",
    ";",
    ",",
    ":=",
    "~",
    "|",  //Agregamos BAR "|"
    "$", //Agregamos DOLLAR
    "..", //Agregamos DOTDOT
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "",
    "<error>"
  };

  private final static int	firstReservedWord = Token.ARRAY,
  				lastReservedWord  = Token.WHILE;

}
