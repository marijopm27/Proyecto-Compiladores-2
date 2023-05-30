/*
 * @(#)Parser.java                        2.1 2003/10/07
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

import Triangle.ErrorReporter;
import Triangle.AbstractSyntaxTrees.ActualParameter;
import Triangle.AbstractSyntaxTrees.ActualParameterSequence;
import Triangle.AbstractSyntaxTrees.ArrayAggregate;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.Command;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DoCommand;
import Triangle.AbstractSyntaxTrees.DoWhileCommand;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.Expression;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecordAggregate;
import Triangle.AbstractSyntaxTrees.RepeatUntilAST;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileAST;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilAST;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import Triangle.AbstractSyntaxTrees.CasesCommand;
import Triangle.AbstractSyntaxTrees.CaseCommand;
import Triangle.AbstractSyntaxTrees.CaseLiteralCommand;
import Triangle.AbstractSyntaxTrees.MultipleCase;
import Triangle.AbstractSyntaxTrees.SingleCase;
import Triangle.AbstractSyntaxTrees.CaseLiterals;
import Triangle.AbstractSyntaxTrees.CaseRangeCommand;
import Triangle.AbstractSyntaxTrees.CompoundSingleDeclaration;
import Triangle.AbstractSyntaxTrees.SingleCaseRange;
import Triangle.AbstractSyntaxTrees.MultipleCaseRange;
import Triangle.AbstractSyntaxTrees.RepeatCommand;
import Triangle.AbstractSyntaxTrees.SelectCommand;
import Triangle.AbstractSyntaxTrees.UntilCommand;
import Triangle.AbstractSyntaxTrees.DoUntilCommand;
import Triangle.AbstractSyntaxTrees.VarDeclarationBecomes;
import Triangle.AbstractSyntaxTrees.ForBecomesAST;
import Triangle.AbstractSyntaxTrees.ForBecomesCommand;
import Triangle.AbstractSyntaxTrees.RepeatForUntil;
import Triangle.AbstractSyntaxTrees.RepeatForWhile;
import Triangle.AbstractSyntaxTrees.TimesCommand;
import Triangle.AbstractSyntaxTrees.RepeatTimesCommand;
import Triangle.AbstractSyntaxTrees.DotDCommand;
import Triangle.AbstractSyntaxTrees.DotDCommand2;
import Triangle.AbstractSyntaxTrees.PrivateDeclaration;
import Triangle.AbstractSyntaxTrees.DotDCommandLiteral;
import Triangle.AbstractSyntaxTrees.RecDeclaration;

public class Parser {

  private Scanner lexicalAnalyser;
  private ErrorReporter errorReporter;
  private Token currentToken;
  private SourcePosition previousTokenPosition;

  public Parser(Scanner lexer, ErrorReporter reporter) {
    lexicalAnalyser = lexer;
    errorReporter = reporter;
    previousTokenPosition = new SourcePosition();
  }

// accept checks whether the current token matches tokenExpected.
// If so, fetches the next token.
// If not, reports a syntactic error.

  void accept (int tokenExpected) throws SyntaxError {
    if (currentToken.kind == tokenExpected) {
      previousTokenPosition = currentToken.position;
      currentToken = lexicalAnalyser.scan();
    } else {
      syntacticError("\"%\" expected here", Token.spell(tokenExpected));
    }
  }

  void acceptIt() {
    previousTokenPosition = currentToken.position;
    currentToken = lexicalAnalyser.scan();
  }

// start records the position of the start of a phrase.
// This is defined to be the position of the first
// character of the first token of the phrase.

  void start(SourcePosition position) {
    position.start = currentToken.position.start;
  }

// finish records the position of the end of a phrase.
// This is defined to be the position of the last
// character of the last token of the phrase.

  void finish(SourcePosition position) {
    position.finish = previousTokenPosition.finish;
  }

  void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
    SourcePosition pos = currentToken.position;
    errorReporter.reportError(messageTemplate, tokenQuoted, pos);
    throw(new SyntaxError());
  }

///////////////////////////////////////////////////////////////////////////////
//
// PROGRAMS
//
///////////////////////////////////////////////////////////////////////////////

  public Program parseProgram() {

    Program programAST = null;

    previousTokenPosition.start = 0;
    previousTokenPosition.finish = 0;
    currentToken = lexicalAnalyser.scan();

    try {
      Command cAST = parseCommand();
      programAST = new Program(cAST, previousTokenPosition);
      if (currentToken.kind != Token.EOT) {
        syntacticError("\"%\" not expected after end of program",
          currentToken.spelling);
      }
    }
    catch (SyntaxError s) { return null; }
    return programAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// LITERALS
//
///////////////////////////////////////////////////////////////////////////////

// parseIntegerLiteral parses an integer-literal, and constructs
// a leaf AST to represent it.

  IntegerLiteral parseIntegerLiteral() throws SyntaxError {
    IntegerLiteral IL = null;

    if (currentToken.kind == Token.INTLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      IL = new IntegerLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      IL = null;
      syntacticError("integer literal expected here", "");
    }
    return IL;
  }

// parseCharacterLiteral parses a character-literal, and constructs a leaf
// AST to represent it.

  CharacterLiteral parseCharacterLiteral() throws SyntaxError {
    CharacterLiteral CL = null;

    if (currentToken.kind == Token.CHARLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      CL = new CharacterLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      CL = null;
      syntacticError("character literal expected here", "");
    }
    return CL;
  }

// parseIdentifier parses an identifier, and constructs a leaf AST to
// represent it.

  Identifier parseIdentifier() throws SyntaxError {
    Identifier I = null;

    if (currentToken.kind == Token.IDENTIFIER) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      I = new Identifier(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      I = null;
      syntacticError("identifier expected here", "");
    }
    return I;
  }
  
  Identifier parseIdentifierOpt() throws SyntaxError {
    Identifier I = null;

    if (currentToken.kind == Token.IDENTIFIER) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      I = new Identifier(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      I = null;
    }
    return I;
  }

// parseOperator parses an operator, and constructs a leaf AST to
// represent it.

  Operator parseOperator() throws SyntaxError {
    Operator O = null;

    if (currentToken.kind == Token.OPERATOR) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      O = new Operator(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      O = null;
      syntacticError("operator expected here", "");
    }
    return O;
  }
  
  CasesCommand parseCasesCommand() throws SyntaxError {
  CasesCommand commandAST = null; // in case there's a syntactic error
  SourcePosition position = new SourcePosition();
  start(position);
  CaseCommand cAST1 = parseCaseCommand();

  if (currentToken.kind == Token.WHEN) {
    MultipleCase mCase = new MultipleCase(cAST1, position);

    while (currentToken.kind == Token.WHEN) {
      CaseCommand cAST2 = parseCaseCommand();
      finish(position);
      mCase = new MultipleCase(mCase, cAST2, position);
    }

    commandAST = new CasesCommand(mCase, position);
  } else if (currentToken.kind == Token.END || currentToken.kind == Token.ELSE) {
    finish(position);
    SingleCase sCase = new SingleCase(cAST1, position);
    commandAST = new CasesCommand(sCase, position);
  } else {
    syntacticError("when or end expected here", "");
  }

  return commandAST;
}
  //Autores: Celina Madrigal Murillo, María José Porras Maroto y Gabriel Mora Estribí 
 CaseCommand parseCaseCommand() throws SyntaxError {
    CaseCommand caseCommandAST = null;
    SourcePosition actualsPos = new SourcePosition();
    start(actualsPos);

    if (currentToken.kind == Token.WHEN) {
        acceptIt();
        CaseLiterals caseLiteralsAST = parseCaseLiteralsCommand();
        accept(Token.THEN);
        Command commandAST = parseCommand();
        finish(actualsPos);
        caseCommandAST = new CaseCommand(caseLiteralsAST, commandAST, actualsPos);
    } else {
        syntacticError("A Case expected here", "");
    }

    return caseCommandAST;
}
 //Autores: Celina Madrigal Murillo, María José Porras Maroto y Gabriel Mora Estribí 
 CaseLiterals parseCaseLiteralsCommand() throws SyntaxError {
    CaseLiterals caseLiteralsAST = null;
    SourcePosition actualsPos = new SourcePosition();
    start(actualsPos);

    CaseRangeCommand caseRangeCommandAST = parseCaseRangeCommand();

    if (currentToken.kind == Token.THEN) {
        SingleCaseRange singleCaseRangeAST = new SingleCaseRange(caseRangeCommandAST, actualsPos);
        finish(actualsPos);
        caseLiteralsAST = new CaseLiterals(singleCaseRangeAST, actualsPos);
    } else if (currentToken.kind != Token.THEN && currentToken.kind != Token.BAR) {
        syntacticError("| or then expected here", "");
    } else {
        MultipleCaseRange multipleCaseRangeAST = new MultipleCaseRange(caseRangeCommandAST, actualsPos);

        while (currentToken.kind == Token.BAR) {
            acceptIt();
            CaseRangeCommand nextCaseRangeCommandAST = parseCaseRangeCommand();
            finish(actualsPos);
            multipleCaseRangeAST = new MultipleCaseRange(multipleCaseRangeAST, nextCaseRangeCommandAST, actualsPos);
        }

        caseLiteralsAST = new CaseLiterals(multipleCaseRangeAST, actualsPos);
    }

    return caseLiteralsAST;
}
  //Autores: Celina Madrigal Murillo, María José Porras Maroto y Gabriel Mora Estribí 
 CaseRangeCommand parseCaseRangeCommand() throws SyntaxError {
    CaseRangeCommand caseRangeCommandAST = null;
    SourcePosition commandPos = new SourcePosition();
    start(commandPos); 
    CaseLiteralCommand c2AST = parseCaseLiteral();
    
    if (currentToken.kind == Token.DOTDOT) {
        acceptIt();
        CaseLiteralCommand c3AST = parseCaseLiteral();
        DotDCommandLiteral DotDCommandLiteralAST = new DotDCommandLiteral(c3AST, commandPos);
        finish(commandPos);
        caseRangeCommandAST = new CaseRangeCommand(c2AST, DotDCommandLiteralAST, commandPos);
    } else if (currentToken.kind != Token.THEN && currentToken.kind != Token.BAR) {
        caseRangeCommandAST = null; 
        syntacticError("character literal or integer literal expected here", "");  
    } else {
        finish(commandPos);
        caseRangeCommandAST = new CaseRangeCommand(c2AST, commandPos);
    }
    
    return caseRangeCommandAST;
}
 //Autores: Celina Madrigal Murillo, María José Porras Maroto y Gabriel Mora Estribí 
CaseLiteralCommand parseCaseLiteral() throws SyntaxError{
    CaseLiteralCommand caseLiteralAST = null;
    SourcePosition commandPos = new SourcePosition();
    start(commandPos);
    if(currentToken.kind == Token.INTLITERAL){
        IntegerLiteral c2AST = parseIntegerLiteral();
        finish(commandPos);
        caseLiteralAST = new CaseLiteralCommand(c2AST, commandPos);
    } 
    else if(currentToken.kind == Token.CHARLITERAL){
        CharacterLiteral c2AST = parseCharacterLiteral();
        finish(commandPos);
        caseLiteralAST = new CaseLiteralCommand(c2AST, commandPos);
    }
    else{
       caseLiteralAST = null; 
       syntacticError("character literal or integer literal expected here", "");
    }
    return caseLiteralAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// COMMANDS
//
///////////////////////////////////////////////////////////////////////////////

// parseCommand parses the command, and constructs an AST
// to represent its phrase structure.

  Command parseCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();

    start(commandPos);
    commandAST = parseSingleCommand();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Command c2AST = parseSingleCommand();
      finish(commandPos);
      commandAST = new SequentialCommand(commandAST, c2AST, commandPos);
    }
    return commandAST;
  }

  Command parseSingleCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();
    start(commandPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(commandPos);
          commandAST = new CallCommand(iAST, apsAST, commandPos);

        } else {

          Vname vAST = parseRestOfVname(iAST);
         if(currentToken.kind == Token.BECOMES){
            acceptIt();
            Expression eAST = parseExpression();
            finish(commandPos);
            commandAST = new AssignCommand(vAST, eAST, commandPos);
          }
          else{
              syntacticError(":= expected after a variable name", "");
          }
        }
        
      }
      break;
      
    //Eliminamos begin

   /* case Token.BEGIN:
      acceptIt();
      commandAST = parseCommand();
      accept(Token.END);
      break;*/
      
    //Eliminamos let

    /*case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
      }
      break;*/
      
    //Eliminamos if

    /*case Token.IF:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.THEN);
        Command c1AST = parseSingleCommand();
        accept(Token.ELSE);
        Command c2AST = parseSingleCommand();
        finish(commandPos);
        commandAST = new IfCommand(eAST, c1AST, c2AST, commandPos);
      }
      break;*/

    /*case Token.WHILE:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.DO);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new WhileCommand(eAST, cAST, commandPos);
      }
      break;

    case Token.SEMICOLON:
    case Token.END:
    case Token.ELSE:
    case Token.IN:
    case Token.EOT:

      finish(commandPos);
      commandAST = new EmptyCommand(commandPos);
      break;*/
      
      
    //Añadimos skip
    //Autor: Celina Madrigal Murillo
      
    case Token.SKIP:
      {
        acceptIt();
        finish(commandPos);
        commandAST = new EmptyCommand(commandPos);
      }
      break;
      
    //Añadimos let
    //Autor: Celina Madrigal Murillo
    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseCommand();
        accept(Token.END);         
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
       
      }
      break;  
      
     //Añadimos if
     //Autor: Celina Madrigal Murillo
    case Token.IF: {
        acceptIt(); //Se acepta el if
        Expression eAST = parseExpression(); //Se acepta el expression.
        accept(Token.THEN);
        Command c1AST = parseCommand();
        Command c2AST = parseBarThen(); //
        finish(commandPos);
        commandAST = new IfCommand(eAST, c1AST, c2AST, commandPos);
      }
      break;

     //Autor:María José Porras Maroto 
    case Token.REPEAT:{
        acceptIt();
        Expression expressionAST = null;
        SourcePosition expressionPos = new SourcePosition();
        start(expressionPos);
        
        switch(currentToken.kind){
     
        case Token.WHILE: {
            acceptIt();
            WhileCommand While = whileDo(commandPos);
            commandAST = new RepeatCommand( While, commandPos);
            break;
        }
        case Token.UNTIL: {
            acceptIt();
            UntilCommand UntilAST = UntilDo(commandPos);
            commandAST = new RepeatUntilAST( UntilAST, commandPos);
            break;
        }

        case Token.DO: {
            acceptIt();
            Command cAST = parseCommand();

            DoCommand DoAST;
            DoAST = new DoCommand(cAST, commandPos);

            
            switch (currentToken.kind) {
                
                case Token.WHILE:
                    acceptIt();
                    DoWhileCommand WhileAST = DoWhile(commandPos); 
                    commandAST = new RepeatDoWhileAST(DoAST, WhileAST, commandPos);
                    break;
                case Token.UNTIL:
                    acceptIt();
                    DoUntilCommand UntilAST = DoUntil(commandPos);
                    commandAST = new RepeatDoUntilAST( DoAST, UntilAST, commandPos);
                    break;
                default:
                    syntacticError("Expected 'while' or 'until' after the command", currentToken.spelling);
                    break;
        }
            break;
        }
        
        

       //Autor: Gabriel Mora Estribí 
        
        case Token.INTLITERAL:
      {
        IntegerLiteral ilAST = parseIntegerLiteral();
        finish(expressionPos);
        expressionAST = new IntegerExpression(ilAST, expressionPos);
        
        if(currentToken.kind == Token.TIMES){
            acceptIt();
            
            TimesCommand times = TimesDo(commandPos);

            commandAST = new RepeatTimesCommand(expressionAST, times, commandPos);

            
            }
         break;
      }
     
        case Token.CHARLITERAL:
          {
            CharacterLiteral clAST= parseCharacterLiteral();
            finish(expressionPos);
            expressionAST = new CharacterExpression(clAST, expressionPos);
            if(currentToken.kind == Token.TIMES){
                acceptIt();
                TimesCommand times = TimesDo(commandPos);

                commandAST = new RepeatTimesCommand( expressionAST,times, commandPos);

            }
           break;
          }
          

        case Token.LBRACKET:
          {
            acceptIt();
            ArrayAggregate aaAST = parseArrayAggregate();
            accept(Token.RBRACKET);
            finish(expressionPos);
            expressionAST = new ArrayExpression(aaAST, expressionPos);
            if(currentToken.kind == Token.TIMES){
                acceptIt();
                TimesCommand times = TimesDo(commandPos);
     
                commandAST = new RepeatTimesCommand( expressionAST,times, commandPos);

            }
             break;
          }
          

        case Token.LCURLY:
          {
            acceptIt();
            RecordAggregate raAST = parseRecordAggregate();
            accept(Token.RCURLY);
            finish(expressionPos);
            expressionAST = new RecordExpression(raAST, expressionPos);
            if(currentToken.kind == Token.TIMES){
                acceptIt();

                TimesCommand times = TimesDo(commandPos);

                commandAST = new RepeatTimesCommand( expressionAST,times, commandPos);
            }
             break;
          }

        case Token.OPERATOR:
          {
            Operator opAST = parseOperator();
            Expression eAST = parsePrimaryExpression();
            finish(expressionPos);
            expressionAST = new UnaryExpression(opAST, eAST, expressionPos);
            if(currentToken.kind == Token.TIMES){
                acceptIt();
                TimesCommand times = TimesDo(commandPos);
                commandAST = new RepeatTimesCommand( expressionAST,times, commandPos);

            }
             break;
          }
          
        case Token.LPAREN:{
          acceptIt();
          expressionAST = parseExpression();
          accept(Token.RPAREN);
          if(currentToken.kind == Token.TIMES){
              acceptIt();
              TimesCommand times = TimesDo(commandPos);
              commandAST = new RepeatTimesCommand( expressionAST,times, commandPos);

            }
           break;
        }
   
        default:
            syntacticError("Expected 'while', 'do', 'until' or an expression after repeat", currentToken.spelling);
            break;
    }
    
    }
    
    break;
   
    //Autor: María José Porras Maroto
    case Token.FOR: {
        acceptIt();
        Identifier iASTF = parseIdentifier();
        if (currentToken.kind == Token.BECOMES){
            acceptIt();
            ForBecomesCommand ForBecomesAST = ParseForBecomesCommand(commandPos, iASTF);

            accept(Token.DOTDOT);

            Expression eASTF = parseExpression();

            DotDCommand DDoAST ;
            DDoAST = new DotDCommand(eASTF, commandPos); 
            
            if(currentToken.kind == Token.DO){
                acceptIt();
                DoCommand ForDoAST = ParseDoCommand(commandPos);
                commandAST = new ForBecomesAST( ForBecomesAST, DDoAST, ForDoAST, commandPos);
            }
            else if (currentToken.kind == Token.WHILE){
                acceptIt();
                
                WhileCommand WhileAST = whileDo(commandPos);
                
                commandAST = new RepeatForWhile( ForBecomesAST,DDoAST, WhileAST, commandPos);
            }
            else if (currentToken.kind == Token.UNTIL) {
                acceptIt();
            
                UntilCommand UntilAST = UntilDo(commandPos);

                commandAST = new RepeatForUntil(ForBecomesAST,DDoAST, UntilAST, commandPos);
            }
            else{
                syntacticError("Expected 'do', 'while' or 'until' ",currentToken.spelling);
            }
            break;
        }
        else if (currentToken.kind == Token.IN){
            
        }
        else{
     
            syntacticError("Expected ':=' ",
                    "");
        }
        break;
    }
    
    default:
      syntacticError("\"%\" cannot start a command",
        currentToken.spelling);
      break;
    }

    return commandAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// EXPRESSIONS
//
///////////////////////////////////////////////////////////////////////////////

  Expression parseExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error
 
    SourcePosition expressionPos = new SourcePosition();

    start (expressionPos);

    switch (currentToken.kind) {

    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Expression eAST = parseExpression();
        finish(expressionPos);
        expressionAST = new LetExpression(dAST, eAST, expressionPos);
      }
      break;

    case Token.IF:
      {
        acceptIt();
        Expression e1AST = parseExpression();
        accept(Token.THEN);
        Expression e2AST = parseExpression();
        accept(Token.ELSE);
        Expression e3AST = parseExpression();
        finish(expressionPos);
        expressionAST = new IfExpression(e1AST, e2AST, e3AST, expressionPos);
      }
      break;

    default:
      expressionAST = parseSecondaryExpression();
      break;
    }
    return expressionAST;
  }
//  Expression parseExpressionOpt() throws SyntaxError {
//    Expression E = null;
//    SourcePosition expressionPos = new SourcePosition();
//
//    start (expressionPos);
//    if (currentToken.kind != Token.) {
//      previousTokenPosition = currentToken.position;
//      String spelling = currentToken.spelling;
//      E = new Expression(spelling, previousTokenPosition);
//      currentToken = lexicalAnalyser.scan();
//    } else {
//      E = null;
//    }
//    return E;
//  }
  
  Expression parseSecondaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    expressionAST = parsePrimaryExpression();
    while (currentToken.kind == Token.OPERATOR) {
      Operator opAST = parseOperator();
      Expression e2AST = parsePrimaryExpression();
      expressionAST = new BinaryExpression (expressionAST, opAST, e2AST,
        expressionPos);
    }
    return expressionAST;
  }

  Expression parsePrimaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    switch (currentToken.kind) {

    case Token.INTLITERAL:
      {
        IntegerLiteral ilAST = parseIntegerLiteral();
        finish(expressionPos);
        expressionAST = new IntegerExpression(ilAST, expressionPos);
      }
      break;

    case Token.CHARLITERAL:
      {
        CharacterLiteral clAST= parseCharacterLiteral();
        finish(expressionPos);
        expressionAST = new CharacterExpression(clAST, expressionPos);
      }
      break;

    case Token.LBRACKET:
      {
        acceptIt();
        ArrayAggregate aaAST = parseArrayAggregate();
        accept(Token.RBRACKET);
        finish(expressionPos);
        expressionAST = new ArrayExpression(aaAST, expressionPos);
      }
      break;

    case Token.LCURLY:
      {
        acceptIt();
        RecordAggregate raAST = parseRecordAggregate();
        accept(Token.RCURLY);
        finish(expressionPos);
        expressionAST = new RecordExpression(raAST, expressionPos);
      }
      break;

    case Token.IDENTIFIER:
      {
        Identifier iAST= parseIdentifier();
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(expressionPos);
          expressionAST = new CallExpression(iAST, apsAST, expressionPos);

        } else {
          Vname vAST = parseRestOfVname(iAST);
          finish(expressionPos);
          expressionAST = new VnameExpression(vAST, expressionPos);
        }
      }
      break;

    case Token.OPERATOR:
      {
        Operator opAST = parseOperator();
        Expression eAST = parsePrimaryExpression();
        finish(expressionPos);
        expressionAST = new UnaryExpression(opAST, eAST, expressionPos);
      }
      break;

    case Token.LPAREN:
    {
      acceptIt();
      expressionAST = parseExpression();
      accept(Token.RPAREN);
    }
    break;
    default:
      syntacticError("\"%\" cannot start an expression",
        currentToken.spelling);
      break;

    }
    return expressionAST;
  }

  RecordAggregate parseRecordAggregate() throws SyntaxError {
    RecordAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Identifier iAST = parseIdentifier();
    accept(Token.IS);
    Expression eAST = parseExpression();

    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      RecordAggregate aAST = parseRecordAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleRecordAggregate(iAST, eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleRecordAggregate(iAST, eAST, aggregatePos);
    }
    return aggregateAST;
  }

  ArrayAggregate parseArrayAggregate() throws SyntaxError {
    ArrayAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Expression eAST = parseExpression();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ArrayAggregate aAST = parseArrayAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleArrayAggregate(eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleArrayAggregate(eAST, aggregatePos);
    }
    return aggregateAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// VALUE-OR-VARIABLE NAMES
//
///////////////////////////////////////////////////////////////////////////////

  Vname parseVname () throws SyntaxError {
    Vname vnameAST = null; // in case there's a syntactic error
    Identifier iAST = parseIdentifier();
    vnameAST = parseRestOfVname(iAST);
    return vnameAST;
  }

  Vname parseRestOfVname(Identifier identifierAST) throws SyntaxError {
    SourcePosition vnamePos = new SourcePosition();
    vnamePos = identifierAST.position;
    Vname vAST = new SimpleVname(identifierAST, vnamePos);

    while (currentToken.kind == Token.DOT ||
           currentToken.kind == Token.LBRACKET) {

      if (currentToken.kind == Token.DOT) {
        acceptIt();
        Identifier iAST = parseIdentifier();
        vAST = new DotVname(vAST, iAST, vnamePos);
      } else {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.RBRACKET);
        finish(vnamePos);
        vAST = new SubscriptVname(vAST, eAST, vnamePos);
      }
    }
    return vAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// DECLARATIONS
//
///////////////////////////////////////////////////////////////////////////////

  //Autor: Celina Madrigal Murillo
  Declaration parseDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    declarationAST = parseCompoundDeclaration(); 
    
    //declarationAST = parseSingleDeclaration();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Declaration d2AST = parseSingleDeclaration();
      finish(declarationPos);
      declarationAST = new SequentialDeclaration(declarationAST, d2AST,
        declarationPos);
    }
    return declarationAST;
  }
   
  //Autor: María José Porras Maroto
  Declaration parseCompoundDeclaration() throws SyntaxError{

      Declaration declarationAST = null;
      SourcePosition position = new SourcePosition();
      start(position);
      switch(currentToken.kind){
          case Token.CONST:
          case Token.VAR:
          case Token.PROC:
          case Token.FUNC:
          case Token.TYPE:
              Declaration dAST = parseSingleDeclaration();
              finish(position);
              declarationAST = new CompoundSingleDeclaration(dAST, position);
              break;
          case Token.REC:
              acceptIt();
              declarationAST = parseProcFuncs();
              accept(Token.END);
              finish(position);
              declarationAST = new RecDeclaration(declarationAST, position);
              break;
          case Token.PRIVATE:
              acceptIt();
              Declaration dAST1 = parseDeclaration();
              accept(Token.IN);
              Declaration dAST2 = parseDeclaration();
              accept(Token.END);
              finish(position);
              declarationAST = new PrivateDeclaration(dAST1, dAST2, position);
              break;
          default:
              syntacticError("\"%\" cannot start a declaration.",
                             currentToken.spelling);
              break;
      }
      return declarationAST;
   }
  //Autor: Celina Madrigal Murillo
  
  
  Declaration parseProcFunc() throws SyntaxError {
    Declaration procFuncAST = null;
    SourcePosition position = new SourcePosition();
    start(position);

    switch (currentToken.kind) {
        case Token.PROC:
            acceptIt();
            Identifier iAST = parseIdentifier();
            accept(Token.LPAREN);
            FormalParameterSequence formalAST = parseFormalParameterSequence();
            accept(Token.RPAREN);
            accept(Token.IS);
            Command cAST = parseCommand();
            accept(Token.END);
            finish(position);
            procFuncAST = new ProcDeclaration(iAST, formalAST, cAST, position);
            break;

        case Token.FUNC:
            acceptIt();
            Identifier identifierAST = parseIdentifier();
            accept(Token.LPAREN);
            FormalParameterSequence fpsAST = parseFormalParameterSequence();
            accept(Token.RPAREN);
            accept(Token.COLON);
            TypeDenoter tAST = parseTypeDenoter();
            accept(Token.IS);
            Expression eAST = parseExpression();
            finish(position);
            procFuncAST = new FuncDeclaration(identifierAST, fpsAST, tAST, eAST, position);
            break;

        default:
            syntacticError("Expected here a proc or func", currentToken.spelling);
            break;
    }

    return procFuncAST;
}
  //Autor: Gabriel Mora
  
  Declaration parseProcFuncs() throws SyntaxError {
    Declaration declarationAST = null;
    SourcePosition position = new SourcePosition();
    start(position);
    
    declarationAST = parseProcFunc();
    
    if(currentToken.kind == Token.BAR){
          while(currentToken.kind == Token.BAR){
              acceptIt();
              Declaration dAST2 = parseProcFunc();
              finish(position);
              declarationAST = new SequentialDeclaration(declarationAST,
                               dAST2, position);
          }
      }else{
          syntacticError("| expected here.","");
      }
    return declarationAST;
}

  
  Declaration parseSingleDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);

    switch (currentToken.kind) {

    case Token.CONST:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new ConstDeclaration(iAST, eAST, declarationPos);
      }
      break;

     //Autor: Celina Madrigal Murillo
    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        switch(currentToken.kind){
            case Token.COLON: {
                accept(Token.COLON);
                TypeDenoter tAST = parseTypeDenoter();
                finish(declarationPos);
                declarationAST = new VarDeclaration(iAST, tAST, declarationPos);
                break;
            }
            case Token.BECOMES:{
                accept(Token.BECOMES);
                Expression eAST = parseExpression();
                finish(declarationPos);
                declarationAST = new VarDeclarationBecomes(iAST, eAST, declarationPos);
                break;
            }
            default:{
                syntacticError("Expected ':' or 'init' after the identifier", currentToken.spelling);
            }
        }
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.IS);
        Command cAST = parseSingleCommand();
        
        if (currentToken.kind == Token.END) {
            acceptIt();
            finish(declarationPos);
            declarationAST = new ProcDeclaration(iAST, fpsAST, cAST, declarationPos);
        } 
        else {
        syntacticError("Expected end after the command", currentToken.spelling);
        }
       
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new FuncDeclaration(iAST, fpsAST, tAST, eAST,
          declarationPos);
      }
      break;

    case Token.TYPE:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        TypeDenoter tAST = parseTypeDenoter();
        finish(declarationPos);
        declarationAST = new TypeDeclaration(iAST, tAST, declarationPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a declaration",
        currentToken.spelling);
      break;
    }
    return declarationAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// PARAMETERS
//
///////////////////////////////////////////////////////////////////////////////

  FormalParameterSequence parseFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST;

    SourcePosition formalsPos = new SourcePosition();

    start(formalsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(formalsPos);
      formalsAST = new EmptyFormalParameterSequence(formalsPos);

    } else {
      formalsAST = parseProperFormalParameterSequence();
    }
    return formalsAST;
  }

  FormalParameterSequence parseProperFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST = null; // in case there's a syntactic error;

    SourcePosition formalsPos = new SourcePosition();
    start(formalsPos);
    FormalParameter fpAST = parseFormalParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FormalParameterSequence fpsAST = parseProperFormalParameterSequence();
      finish(formalsPos);
      formalsAST = new MultipleFormalParameterSequence(fpAST, fpsAST,
        formalsPos);

    } else {
      finish(formalsPos);
      formalsAST = new SingleFormalParameterSequence(fpAST, formalsPos);
    }
    return formalsAST;
  }

  FormalParameter parseFormalParameter() throws SyntaxError {
    FormalParameter formalAST = null; // in case there's a syntactic error;

    SourcePosition formalPos = new SourcePosition();
    start(formalPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new ConstFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new VarFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        finish(formalPos);
        formalAST = new ProcFormalParameter(iAST, fpsAST, formalPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new FuncFormalParameter(iAST, fpsAST, tAST, formalPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a formal parameter",
        currentToken.spelling);
      break;

    }
    return formalAST;
  }


  ActualParameterSequence parseActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST;

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(actualsPos);
      actualsAST = new EmptyActualParameterSequence(actualsPos);

    } else {
      actualsAST = parseProperActualParameterSequence();
    }
    return actualsAST;
  }

  ActualParameterSequence parseProperActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST = null; // in case there's a syntactic error

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    ActualParameter apAST = parseActualParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ActualParameterSequence apsAST = parseProperActualParameterSequence();
      finish(actualsPos);
      actualsAST = new MultipleActualParameterSequence(apAST, apsAST,
        actualsPos);
    } else {
      finish(actualsPos);
      actualsAST = new SingleActualParameterSequence(apAST, actualsPos);
    }
    return actualsAST;
  }

  ActualParameter parseActualParameter() throws SyntaxError {
    ActualParameter actualAST = null; // in case there's a syntactic error

    SourcePosition actualPos = new SourcePosition();

    start(actualPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
    case Token.INTLITERAL:
    case Token.CHARLITERAL:
    case Token.OPERATOR:
    case Token.LET:
    case Token.IF:
    case Token.LPAREN:
    case Token.LBRACKET:
    case Token.LCURLY:
      {
        Expression eAST = parseExpression();
        finish(actualPos);
        actualAST = new ConstActualParameter(eAST, actualPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Vname vAST = parseVname();
        finish(actualPos);
        actualAST = new VarActualParameter(vAST, actualPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new ProcActualParameter(iAST, actualPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new FuncActualParameter(iAST, actualPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start an actual parameter",
        currentToken.spelling);
      break;

    }
    return actualAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// TYPE-DENOTERS
//
///////////////////////////////////////////////////////////////////////////////

  TypeDenoter parseTypeDenoter() throws SyntaxError {
    TypeDenoter typeAST = null; // in case there's a syntactic error
    SourcePosition typePos = new SourcePosition();

    start(typePos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        finish(typePos);
        typeAST = new SimpleTypeDenoter(iAST, typePos);
      }
      break;

    case Token.ARRAY:
      {
        acceptIt();
        IntegerLiteral ilAST = parseIntegerLiteral();
        accept(Token.OF);
        TypeDenoter tAST = parseTypeDenoter();
        finish(typePos);
        typeAST = new ArrayTypeDenoter(ilAST, tAST, typePos);
      }
      break;

    case Token.RECORD:
      {
        acceptIt();
        FieldTypeDenoter fAST = parseFieldTypeDenoter();
        accept(Token.END);
        finish(typePos);
        typeAST = new RecordTypeDenoter(fAST, typePos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a type denoter",
        currentToken.spelling);
      break;

    }
    return typeAST;
  }

  FieldTypeDenoter parseFieldTypeDenoter() throws SyntaxError {
    FieldTypeDenoter fieldAST = null; // in case there's a syntactic error

    SourcePosition fieldPos = new SourcePosition();

    start(fieldPos);
    Identifier iAST = parseIdentifier();
    accept(Token.COLON);
    TypeDenoter tAST = parseTypeDenoter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FieldTypeDenoter fAST = parseFieldTypeDenoter();
      finish(fieldPos);
      fieldAST = new MultipleFieldTypeDenoter(iAST, tAST, fAST, fieldPos);
    } else {
      finish(fieldPos);
      fieldAST = new SingleFieldTypeDenoter(iAST, tAST, fieldPos);
    }
    return fieldAST;
  }
  
  //Funciones extra
  
  //Autor: María José Porras Maroto
  private WhileCommand whileDo(SourcePosition commandPos) throws SyntaxError{
        
        start(commandPos);
        WhileCommand commandAST = null;       
        Expression eAST = parseExpression();
        accept(Token.DO);
        Command cAST = parseCommand();
        DoCommand DoAST;
        DoAST = new DoCommand(cAST, commandPos);
        
        if(currentToken.kind == Token.END){
            acceptIt();
            finish(commandPos);
            commandAST = new WhileCommand (eAST, DoAST, commandPos);
        }
      
        else{
            syntacticError("Expected END here", currentToken.spelling);
        }

        return commandAST;
    }
  
   //Autor: María José Porras Maroto
  private DoWhileCommand DoWhile(SourcePosition commandPos) throws SyntaxError {
        start(commandPos);
        DoWhileCommand commandAST = null;
        Expression eAST = parseExpression();

        if(currentToken.kind == Token.END){
            acceptIt();
            finish(commandPos);
            commandAST = new DoWhileCommand (eAST, commandPos);
        }

        else{
            syntacticError("Expected END here", currentToken.spelling);
        }
        
        // Devuelve el arbol
        return commandAST;
    }  
  
   //Autor: María José Porras Maroto
  private UntilCommand UntilDo(SourcePosition commandPos) throws SyntaxError {
        start(commandPos);
        UntilCommand commandAST = null;
        Expression eAST = parseExpression();
        accept(Token.DO);
        Command cAST = parseCommand();
        DoCommand DoAST;
        DoAST = new DoCommand(cAST, commandPos);       

        if(currentToken.kind == Token.END){
            acceptIt();
            finish(commandPos);
            commandAST = new UntilCommand(eAST, DoAST, commandPos);
        }
        
        else{
            syntacticError("Expected END here", currentToken.spelling);
        }

        return commandAST;
    }
   //Autor: María José Porras Maroto
  private DoUntilCommand DoUntil(SourcePosition commandPos) throws SyntaxError {
        start(commandPos);
        DoUntilCommand commandAST = null;
        Expression eAST = parseExpression();
        if(currentToken.kind == Token.END){
            acceptIt();
            finish(commandPos);
            commandAST = new DoUntilCommand (eAST, commandPos);
        }

        else{
            syntacticError("Expected END here", currentToken.spelling);
        }
        
        return commandAST;}
  
   //Autor: María José Porras Maroto
  private ForBecomesCommand ParseForBecomesCommand(SourcePosition commandPos, Identifier iASTF) throws SyntaxError {
        start(commandPos);
        ForBecomesCommand commandAST = null;
        Expression eAST = parseExpression();
        finish(commandPos);
        commandAST = new ForBecomesCommand(iASTF, eAST, commandPos);
        return commandAST;
    }
  
   //Autor: María José Porras Maroto
  private DoCommand ParseDoCommand(SourcePosition commandPos) throws SyntaxError {
        start(commandPos);
        DoCommand commandAST = null;
        Command cAST = parseCommand();
        accept(Token.END);
        finish(commandPos);
        commandAST = new DoCommand(cAST, commandPos);
        return commandAST;
    }
  
   //Autor: Gabriel Mora Estribí
  private TimesCommand TimesDo(SourcePosition commandPos) throws SyntaxError{
        
        start(commandPos);
        TimesCommand TcommandAST = null;
        accept(Token.DO);
        Command cAST = parseCommand();
        DoCommand DoAST;
        DoAST = new DoCommand(cAST, commandPos);

        if(currentToken.kind == Token.END){
            
            acceptIt();
            finish(commandPos);
            TcommandAST = new TimesCommand ( DoAST, commandPos);
            
        }

        else{
            syntacticError("Expected END here", currentToken.spelling);
        }
        
        return TcommandAST;
    }  
  
   //Autor: Celina Madrigal Murillo
  Command parseBarThen() throws SyntaxError {

  Command commandAST = null; 
  SourcePosition commandPos = new SourcePosition();
  start(commandPos);

  if (currentToken.kind == Token.ELSE) {
    acceptIt();
    Command cAST = parseCommand();
    accept(Token.END);
    commandAST = cAST;
  } else if (currentToken.kind == Token.BAR) {
    acceptIt();
    Expression eAST = parseExpression();
    accept(Token.THEN);
    Command cAST = parseCommand();
    Command c2AST = parseBarThen();
    finish(commandPos);
    commandAST = new IfCommand(eAST, cAST, c2AST, commandPos); 
  } else {
    syntacticError("| or else expected here", "");
  }

  return commandAST;
}
  
}
