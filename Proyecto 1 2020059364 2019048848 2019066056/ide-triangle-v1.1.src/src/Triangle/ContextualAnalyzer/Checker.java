/*
 * @(#)Checker.java                        2.1 2003/10/07
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

package Triangle.ContextualAnalyzer;

import Triangle.ErrorReporter;
import Triangle.StdEnvironment;
import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BarCommandCaseRange;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CaseCommand;
import Triangle.AbstractSyntaxTrees.CaseLiteralCommand;
import Triangle.AbstractSyntaxTrees.CaseLiterals;
import Triangle.AbstractSyntaxTrees.CaseRangeCommand;
import Triangle.AbstractSyntaxTrees.CasesCommand;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DoCommand;
import Triangle.AbstractSyntaxTrees.DoWhileCommand;
import Triangle.AbstractSyntaxTrees.DoUntilCommand;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleCase;
import Triangle.AbstractSyntaxTrees.MultipleCaseRange;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.MultipleThen;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileAST;
import Triangle.AbstractSyntaxTrees.RepeatUntilAST;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilAST;
import Triangle.AbstractSyntaxTrees.SelectCommand;
import Triangle.AbstractSyntaxTrees.SequentialCases;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleCase;
import Triangle.AbstractSyntaxTrees.SingleCaseRange;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SingleThen;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.Terminal;
import Triangle.AbstractSyntaxTrees.ThenCommand;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import Triangle.AbstractSyntaxTrees.UntilCommand;
import Triangle.AbstractSyntaxTrees.VarDeclarationBecomes;
import Triangle.AbstractSyntaxTrees.ForBecomesAST;
import Triangle.AbstractSyntaxTrees.ForBecomesCommand;
import Triangle.AbstractSyntaxTrees.RepeatForUntil;
import Triangle.AbstractSyntaxTrees.RepeatForWhile;
import Triangle.AbstractSyntaxTrees.TimesCommand;
import Triangle.AbstractSyntaxTrees.RepeatTimesCommand;
import Triangle.AbstractSyntaxTrees.DotDCommand;
import Triangle.AbstractSyntaxTrees.DotDCommand2;
import Triangle.AbstractSyntaxTrees.DotDCommandLiteral;
import Triangle.AbstractSyntaxTrees.PrivateDeclaration;
import Triangle.AbstractSyntaxTrees.RepeatDeclaration;
import Triangle.AbstractSyntaxTrees.WhileEndCommand;
import Triangle.SyntacticAnalyzer.SourcePosition;
import Triangle.ErrorReporter;
import Triangle.StdEnvironment;
import Triangle.AbstractSyntaxTrees.*;
import Triangle.SyntacticAnalyzer.SourcePosition;

public final class Checker implements Visitor {

  // Commands

  // Always returns null. Does not use the given object.
    
 //Autores: Celina Madrigal Murillo, María José Porras Maroto y Gabriel Mora Estribi
  @Override
  public Object visitCaseLiteralCommand(CaseLiteralCommand ast, Object o) {
    TypeDenoter cType;
    if (ast.CL != null) {
      cType = (TypeDenoter) ast.CL.visit(this, null);
      idTable.enter((String) ast.CL.spelling, ast);
      if (ast.duplicated)
        reporter.reportError("Character literal \"%\" already used", ast.CL.spelling, ast.position);
    } else {
      cType = (TypeDenoter) ast.IL.visit(this, null);
      idTable.enter((String) ast.IL.spelling, ast);
      if (ast.duplicated)
        reporter.reportError("Integer literal \"%\" already used", ast.IL.spelling, ast.position);
    }
    if (!cType.equals(StdEnvironment.integerType) && !cType.equals(StdEnvironment.charType))
      reporter.reportError("Integer Literal or Character Literal expected here",
          "", ast.position);
    if (cType.equals(o) == false)
      reporter.reportError("All literals must be the same kind", "", ast.position);
    return null;
  }

public Object visitCaseRangeCommand(CaseRangeCommand ast, Object o) {
     if (ast.CLC != null && ast.DCL != null) {
      ast.CLC.visit(this, o);
      ast.DCL.visit(this, o);
    } else if (ast.CLC != null && ast.DCL == null) {
      ast.CLC.visit(this, o);
    }
    return null;
  }

public Object visitDotDCommand2(DotDCommand2 ast, Object obj){
   if (ast.CLCT != null) {
      ast.CLCT.visit(this, obj);
    }
    return null;
}  
public Object visitBarCommandCaseRange(BarCommandCaseRange ast, Object obj){
    return null;
}

public Object visitCaseCommand(CaseCommand ast, Object obj){
    if (ast.CL != null) {
      ast.CL.visit(this, obj);
      ast.C.visit(this, null);
    }
    return null;
}

public Object visitCasesCommand(CasesCommand ast, Object obj){
    if (ast.MC == null) {
      ast.SC.visit(this, obj);
    } else {
      ast.MC.visit(this, obj);
    }
    return null;
}
//------------------------------------------------------------------------------
public Object visitSelectCommand(SelectCommand ast, Object obj){
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (eType.equals(StdEnvironment.integerType) || eType.equals(StdEnvironment.charType)) {
      idTable.openScope();
      ast.CC.visit(this, eType);
      idTable.closeScope();
    } else {
      reporter.reportError("Integer or Character Expression expected here",
          "", ast.E.position);
    }

    if (ast.C != null) {
      ast.C.visit(this, null);
    }
    return null;

}


public Object visitCaseLiterals(CaseLiterals ast, Object obj){
    if (ast.SCR != null) {
      ast.SCR.visit(this, obj);
    } else if (ast.MCR != null) {
      ast.MCR.visit(this, obj);
    }
    return null;
}

public Object visitSingleCaseRange(SingleCaseRange ast, Object obj){
    TypeDenoter Type = (TypeDenoter) ast.CRC.visit(this,obj);
    return null;
}

public Object visitSingleThen(SingleThen ast, Object obj){
    
    return null;
}

public Object visitSequentialCases(SequentialCases ast, Object obj){
    return null;
}

public Object visitSingleCase(SingleCase ast, Object obj){
     ast.CC.visit(this, obj);
    return null;
}

public Object visitMultipleCaseRange(MultipleCaseRange ast, Object obj){
    
    if (ast.CRC2!= null) {
      ast.CRC2.visit(this, obj);
    } else {
      ast.CRC1.visit(this, obj);
      ast.CRC2.visit(this, obj);
    }
    return null;
}

public Object visitMultipleThen(MultipleThen ast, Object obj){
    return null;
}

public Object visitMultipleCase(MultipleCase ast, Object obj){
    if (ast.CC2 == null) {
      ast.CC1.visit(this, obj);
    } else {
      ast.CC1.visit(this, obj);
      ast.CC2.visit(this, obj);
    }
    return null;
}

  public Object visitAssignCommand(AssignCommand ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (!ast.V.variable)
      reporter.reportError ("LHS of assignment is not a variable", "", ast.V.position);
    if (! eType.equals(vType))
      reporter.reportError ("assignment incompatibilty", "", ast.position);
    return null;
  }


  public Object visitCallCommand(CallCommand ast, Object o) {

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null){
        System.out.println(1);
      reportUndeclared(ast.I);
    }
    else if (binding instanceof ProcDeclaration) {
      ast.APS.visit(this, ((ProcDeclaration) binding).FPS);
    } else if (binding instanceof ProcFormalParameter) {
      ast.APS.visit(this, ((ProcFormalParameter) binding).FPS);
    } else
      reporter.reportError("\"%\" is not a procedure identifier",
                           ast.I.spelling, ast.I.position);
    return null;
  }

  public Object visitEmptyCommand(EmptyCommand ast, Object o) {
    return null;
  }

  public Object visitIfCommand(IfCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }

  public Object visitLetCommand(LetCommand ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialCommand(SequentialCommand ast, Object o) {
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }
  //Repeat while Exp do Com end
  public Object visitWhileCommand(WhileCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (!eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);

    if(o != null){
      RepeatDeclaration repeat = new RepeatDeclaration(dummyPos);
      idTable.openScope();
      if(((RepeatCommand) o).I != null)
        idTable.enter(((RepeatCommand) o).I.spelling, repeat);
      else
        idTable.enter("", repeat);

      if(repeat.duplicated)
        reporter.reportError("identifier \"%\" already declared", 
          ((RepeatCommand) o).I.spelling, ((RepeatCommand) o).position);
      ast.C.visit(this, null);
      idTable.closeScope();
    }
    else
      ast.C.visit(this, null);

    return null;
  }

    public Object visitTimesCommand(TimesCommand ast, Object o) {
    
    ast.C.visit(this, null);
    return null;
  }

  // Expressions

  // Returns the TypeDenoter denoting the type of the expression. Does
  // not use the given object.

  public Object visitArrayExpression(ArrayExpression ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    IntegerLiteral il = new IntegerLiteral(new Integer(ast.AA.elemCount).toString(),
        ast.position);
    if (!(elemType instanceof IntTypeDenoter) && !(elemType instanceof CharTypeDenoter)) {
      reporter.reportError("Int or char Array expected here", "", ast.position);
    }
    ast.type = new ArrayTypeDenoter(il, elemType, ast.position);
    StdEnvironment.arrayTypeDenoter = new ArrayTypeDenoter(il, elemType, ast.position);
    return ast.type;
  }

  public Object visitBinaryExpression(BinaryExpression ast, Object o) {

    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);

    if (binding == null){
      System.out.println(2);  
      reportUndeclared(ast.O);
    }
    else {
      if (! (binding instanceof BinaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a binary operator",
                              ast.O.spelling, ast.O.position);
      BinaryOperatorDeclaration bbinding = (BinaryOperatorDeclaration) binding;
      if (bbinding.ARG1 == StdEnvironment.anyType) {
        // this operator must be "=" or "\="
        if (! e1Type.equals(e2Type))
          reporter.reportError ("incompatible argument types for \"%\"",
                                ast.O.spelling, ast.position);
      } else if (! e1Type.equals(bbinding.ARG1))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E1.position);
      else if (! e2Type.equals(bbinding.ARG2))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E2.position);
      ast.type = bbinding.RES;
    }
    return ast.type;
  }

  public Object visitCallExpression(CallExpression ast, Object o) {
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
        System.out.println(3);
      reportUndeclared(ast.I);
      ast.type = StdEnvironment.errorType;
    } else if (binding instanceof FuncDeclaration) {
      ast.APS.visit(this, ((FuncDeclaration) binding).FPS);
      ast.type = ((FuncDeclaration) binding).T;
      
    } else if (binding instanceof FuncFormalParameter) {
      ast.APS.visit(this, ((FuncFormalParameter) binding).FPS);
      ast.type = ((FuncFormalParameter) binding).T;
    } else
      reporter.reportError("\"%\" is not a function identifier",
                           ast.I.spelling, ast.I.position);
    return ast.type;
  }

  public Object visitCharacterExpression(CharacterExpression ast, Object o) {
    ast.type = StdEnvironment.charType;
    return ast.type;
  }

  public Object visitEmptyExpression(EmptyExpression ast, Object o) {
    ast.type = null;
    return ast.type;
  }

  public Object visitIfExpression(IfExpression ast, Object o) {
    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    if (! e1Type.equals(StdEnvironment.booleanType))
      reporter.reportError ("Boolean expression expected here", "",
                            ast.E1.position);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    TypeDenoter e3Type = (TypeDenoter) ast.E3.visit(this, null);
    if (! e2Type.equals(e3Type))
      reporter.reportError ("incompatible limbs in if-expression", "", ast.position);
    ast.type = e2Type;
    return ast.type;
  }

  public Object visitIntegerExpression(IntegerExpression ast, Object o) {
    ast.type = StdEnvironment.integerType;
    return ast.type;
  }

  public Object visitLetExpression(LetExpression ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.type = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    return ast.type;
  }

  public Object visitRecordExpression(RecordExpression ast, Object o) {
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    ast.type = new RecordTypeDenoter(rType, ast.position);
    return ast.type;
  }

  public Object visitUnaryExpression(UnaryExpression ast, Object o) {

    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);
    if (binding == null) {
        System.out.println(4);
      reportUndeclared(ast.O);
      ast.type = StdEnvironment.errorType;
    } else if (! (binding instanceof UnaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a unary operator",
                              ast.O.spelling, ast.O.position);
    else {
      UnaryOperatorDeclaration ubinding = (UnaryOperatorDeclaration) binding;
      if (! eType.equals(ubinding.ARG))
        reporter.reportError ("wrong argument type for \"%\"",
                              ast.O.spelling, ast.O.position);
      ast.type = ubinding.RES;
    }
    return ast.type;
  }

  public Object visitVnameExpression(VnameExpression ast, Object o) {
    ast.type = (TypeDenoter) ast.V.visit(this, null);
    return ast.type;
  }

  // Declarations

  // Always returns null. Does not use the given object.
  public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    if (! ast.T.equals(eType))
      reporter.reportError ("body of function \"%\" has wrong type",
                            ast.I.spelling, ast.E.position);
    return null;
  }

  public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
    ast.D1.visit(this, null);
    ast.D2.visit(this, null);
    return null;
  }

  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitVarDeclaration(VarDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);

    return null;
  }

  public Object visitPrivateDeclaration(PrivateDeclaration ast, Object o) {
    // Se agrega a la pila publica
    idTable.pushPublic();
    ast.D1.visit(this, null);

    // Se agrega unicamente a la pila privado
    idTable.pushPrivate();
    ast.D2.visit(this, null);

    // Se cierra la pila privada
    idTable.closePrivate();
    return null;
  }  
  // Array Aggregates

  // Returns the TypeDenoter for the Array Aggregate. Does not use the
  // given object.

  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    ast.elemCount = ast.AA.elemCount + 1;
    if (! eType.equals(elemType))
      reporter.reportError ("incompatible array-aggregate element", "", ast.E.position);
    return elemType;
  }

  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.E.visit(this, null);
    ast.elemCount = 1;
    return elemType;
  }

  // Record Aggregates

  // Returns the TypeDenoter for the Record Aggregate. Does not use the
  // given object.

  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    TypeDenoter fType = checkFieldIdentifier(rType, ast.I);
    if (fType != StdEnvironment.errorType)
      reporter.reportError ("duplicate field \"%\" in record",
                            ast.I.spelling, ast.I.position);
    ast.type = new MultipleFieldTypeDenoter(ast.I, eType, rType, ast.position);
    return ast.type;
  }

  public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    ast.type = new SingleFieldTypeDenoter(ast.I, eType, ast.position);
    return ast.type;
  }

  // Formal Parameters

  // Always returns null. Does not use the given object.

  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
    return null;
  }

  public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    ast.FPS.visit(this, null);
    return null;
  }

  public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    return null;
  }

  // Actual Parameters

  // Always returns null. Uses the given FormalParameter.

  public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (!(fp instanceof ConstFormalParameter))
      reporter.reportError("const actual parameter not expected here", "",
          ast.position);
    else if (eType instanceof ArrayTypeDenoter)
      return null;
    else if (!eType.equals(((ConstFormalParameter) fp).T))
      reporter.reportError("wrong type for const actual parameter", "",
          ast.E.position);
    return null;
  }

  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null){
        System.out.println(5);
      reportUndeclared (ast.I);
    }
    else if (! (binding instanceof FuncDeclaration ||
                binding instanceof FuncFormalParameter))
      reporter.reportError ("\"%\" is not a function identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof FuncFormalParameter))
      reporter.reportError ("func actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      TypeDenoter T = null;
      if (binding instanceof FuncDeclaration) {
        FPS = ((FuncDeclaration) binding).FPS;
        T = ((FuncDeclaration) binding).T;
      } else {
        FPS = ((FuncFormalParameter) binding).FPS;
        T = ((FuncFormalParameter) binding).T;
      }
      if (! FPS.equals(((FuncFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for function \"%\"",
                              ast.I.spelling, ast.I.position);
      else if (! T.equals(((FuncFormalParameter) fp).T))
        reporter.reportError ("wrong type for function \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null){
        System.out.println(6);
      reportUndeclared (ast.I);
    }
    else if (! (binding instanceof ProcDeclaration ||
                binding instanceof ProcFormalParameter))
      reporter.reportError ("\"%\" is not a procedure identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof ProcFormalParameter))
      reporter.reportError ("proc actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      if (binding instanceof ProcDeclaration)
        FPS = ((ProcDeclaration) binding).FPS;
      else
        FPS = ((ProcFormalParameter) binding).FPS;
      if (! FPS.equals(((ProcFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for procedure \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  public Object visitVarActualParameter(VarActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    if (! ast.V.variable)
      reporter.reportError ("actual parameter is not a variable", "",
                            ast.V.position);
    else if (! (fp instanceof VarFormalParameter))
      reporter.reportError ("var actual parameter not expected here", "",
                            ast.V.position);
    else if (! vType.equals(((VarFormalParameter) fp).T))
      reporter.reportError ("wrong type for var actual parameter", "",
                            ast.V.position);
    return null;
  }

  public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof EmptyFormalParameterSequence))
      reporter.reportError ("too few actual parameters", "", ast.position);
    return null;
  }

  public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof MultipleFormalParameterSequence))
      reporter.reportError ("too many actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((MultipleFormalParameterSequence) fps).FP);
      ast.APS.visit(this, ((MultipleFormalParameterSequence) fps).FPS);
    }
    return null;
  }

  public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof SingleFormalParameterSequence))
      reporter.reportError ("incorrect number of actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((SingleFormalParameterSequence) fps).FP);
    }
    return null;
  }

  // Type Denoters

  // Returns the expanded version of the TypeDenoter. Does not
  // use the given object.

  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
    return StdEnvironment.anyType;
  }

  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    if ((Integer.valueOf(ast.IL.spelling).intValue()) == 0)
      reporter.reportError ("arrays must not be empty", "", ast.IL.position);
    return ast;
  }

  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
    return StdEnvironment.booleanType;
  }

  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
    return StdEnvironment.errorType;
  }

  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
        System.out.println(7);
      reportUndeclared (ast.I);
      return StdEnvironment.errorType;
    } else if (! (binding instanceof TypeDeclaration)) {
      reporter.reportError ("\"%\" is not a type identifier",
                            ast.I.spelling, ast.I.position);
      return StdEnvironment.errorType;
    }
    return ((TypeDeclaration) binding).T;
  }

  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
    ast.FT = (FieldTypeDenoter) ast.FT.visit(this, null);
    return ast;
  }

  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    ast.FT.visit(this, null);
    return ast;
  }

  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    return ast;
  }

  // Literals, Identifiers and Operators
  public Object visitCharacterLiteral(CharacterLiteral CL, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitIdentifier(Identifier I, Object o) {
    Declaration binding = idTable.retrieve(I.spelling);
    if (binding != null)
      I.decl = binding;
    return binding;
  }

  public Object visitIntegerLiteral(IntegerLiteral IL, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitOperator(Operator O, Object o) {
    Declaration binding = idTable.retrieve(O.spelling);
    if (binding != null)
      O.decl = binding;
    return binding;
  }

  // Value-or-variable names

  // Determines the address of a named object (constant or variable).
  // This consists of a base object, to which 0 or more field-selection
  // or array-indexing operations may be applied (if it is a record or
  // array).  As much as possible of the address computation is done at
  // compile-time. Code is generated only when necessary to evaluate
  // index expressions at run-time.
  // currentLevel is the routine level where the v-name occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the object is addressed at run-time.
  // It returns the description of the base object.
  // offset is set to the total of any field offsets (plus any offsets
  // due to index expressions that happen to be literals).
  // indexed is set to true iff there are any index expressions (other
  // than literals). In that case code is generated to compute the
  // offset due to these indexing operations at run-time.

  // Returns the TypeDenoter of the Vname. Does not use the
  // given object.

  public Object visitDotVname(DotVname ast, Object o) {
    ast.type = null;
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    if (! (vType instanceof RecordTypeDenoter))
      reporter.reportError ("record expected here", "", ast.V.position);
    else {
      ast.type = checkFieldIdentifier(((RecordTypeDenoter) vType).FT, ast.I);
      if (ast.type == StdEnvironment.errorType)
        reporter.reportError ("no field \"%\" in this record type",
                              ast.I.spelling, ast.I.position);
    }
    return ast.type;
  }

  public Object visitSimpleVname(SimpleVname ast, Object o) {
    ast.variable = false;
    ast.type = StdEnvironment.errorType;
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null){
        System.out.println(8);
      reportUndeclared(ast.I);
    }
    else
      if (binding instanceof ConstDeclaration) {
        ast.type = ((ConstDeclaration) binding).E.type;
        ast.variable = false;
      } else if (binding instanceof VarDeclaration) {
        ast.type = ((VarDeclaration) binding).T;
        ast.variable = true;
      } else if (binding instanceof ConstFormalParameter) {
        ast.type = ((ConstFormalParameter) binding).T;
        ast.variable = false;
      } else if (binding instanceof VarFormalParameter) {
        ast.type = ((VarFormalParameter) binding).T;
        ast.variable = true;
      } else if (binding instanceof VarDeclarationBecomes) { 
        ast.type = ((VarDeclarationBecomes) binding).E;
        ast.variable = true; // Se agrega como una variable
    } else if (binding instanceof ForBecomesCommand) {
        ast.type = ((ForBecomesCommand) binding).E.type;
        ast.variable = true; // Se agrega como una variable
      
      
      
    }else
        reporter.reportError ("\"%\" is not a const or var identifier",
                              ast.I.spelling, ast.I.position);
    return ast.type;
  }

  public Object visitSubscriptVname(SubscriptVname ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (vType != StdEnvironment.errorType) {
      if (! (vType instanceof ArrayTypeDenoter))
        reporter.reportError ("array expected here", "", ast.V.position);
      else {
        if (! eType.equals(StdEnvironment.integerType))
          reporter.reportError ("Integer expression expected here", "",
				ast.E.position);
        ast.type = ((ArrayTypeDenoter) vType).T;
      }
    }
    return ast.type;
  }

  // Programs

  public Object visitProgram(Program ast, Object o) {
    ast.C.visit(this, null);
    return null;
  }

  // Checks whether the source program, represented by its AST, satisfies the
  // language's scope rules and type rules.
  // Also decorates the AST as follows:
  //  (a) Each applied occurrence of an identifier or operator is linked to
  //      the corresponding declaration of that identifier or operator.
  //  (b) Each expression and value-or-variable-name is decorated by its type.
  //  (c) Each type identifier is replaced by the type it denotes.
  // Types are represented by small ASTs.

  public void check(Program ast) {
    ast.visit(this, null);
  }

  /////////////////////////////////////////////////////////////////////////////

  public Checker (ErrorReporter reporter) {
    this.reporter = reporter;
    this.idTable = new IdentificationTable ();
    establishStdEnvironment();
  }

  private IdentificationTable idTable;
  private static SourcePosition dummyPos = new SourcePosition();
  private ErrorReporter reporter;

  // Reports that the identifier or operator used at a leaf of the AST
  // has not been declared.

  private void reportUndeclared (Terminal leaf) {
      System.out.println("Ini");
     
    reporter.reportError("\"%\" is not declared", leaf.spelling, leaf.position);
  }


  private static TypeDenoter checkFieldIdentifier(FieldTypeDenoter ast, Identifier I) {
    if (ast instanceof MultipleFieldTypeDenoter) {
      MultipleFieldTypeDenoter ft = (MultipleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      } else {
        return checkFieldIdentifier (ft.FT, I);
      }
    } else if (ast instanceof SingleFieldTypeDenoter) {
      SingleFieldTypeDenoter ft = (SingleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      }
    }
    return StdEnvironment.errorType;
  }


  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private TypeDeclaration declareStdType (String id, TypeDenoter typedenoter) {

    TypeDeclaration binding;

    binding = new TypeDeclaration(new Identifier(id, dummyPos), typedenoter, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ConstDeclaration declareStdConst (String id, TypeDenoter constType) {

    IntegerExpression constExpr;
    ConstDeclaration binding;

    // constExpr used only as a placeholder for constType
    constExpr = new IntegerExpression(null, dummyPos);
    constExpr.type = constType;
    binding = new ConstDeclaration(new Identifier(id, dummyPos), constExpr, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ProcDeclaration declareStdProc (String id, FormalParameterSequence fps) {

    ProcDeclaration binding;

    binding = new ProcDeclaration(new Identifier(id, dummyPos), fps,
                                  new EmptyCommand(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private FuncDeclaration declareStdFunc (String id, FormalParameterSequence fps,
                                          TypeDenoter resultType) {

    FuncDeclaration binding;

    binding = new FuncDeclaration(new Identifier(id, dummyPos), fps, resultType,
                                  new EmptyExpression(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // unary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private UnaryOperatorDeclaration declareStdUnaryOp
    (String op, TypeDenoter argType, TypeDenoter resultType) {

    UnaryOperatorDeclaration binding;

    binding = new UnaryOperatorDeclaration (new Operator(op, dummyPos),
                                            argType, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // binary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private BinaryOperatorDeclaration declareStdBinaryOp
    (String op, TypeDenoter arg1Type, TypeDenoter arg2type, TypeDenoter resultType) {

    BinaryOperatorDeclaration binding;

    binding = new BinaryOperatorDeclaration (new Operator(op, dummyPos),
                                             arg1Type, arg2type, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates small ASTs to represent the standard types.
  // Creates small ASTs to represent "declarations" of standard types,
  // constants, procedures, functions, and operators.
  // Enters these "declarations" in the identification table.

  private final static Identifier dummyI = new Identifier("", dummyPos);

  private void establishStdEnvironment () {

    // idTable.startIdentification();
    StdEnvironment.booleanType = new BoolTypeDenoter(dummyPos);
    StdEnvironment.integerType = new IntTypeDenoter(dummyPos);
    StdEnvironment.charType = new CharTypeDenoter(dummyPos);
    StdEnvironment.anyType = new AnyTypeDenoter(dummyPos);
    StdEnvironment.errorType = new ErrorTypeDenoter(dummyPos);

    StdEnvironment.booleanDecl = declareStdType("Boolean", StdEnvironment.booleanType);
    StdEnvironment.falseDecl = declareStdConst("false", StdEnvironment.booleanType);
    StdEnvironment.trueDecl = declareStdConst("true", StdEnvironment.booleanType);
    StdEnvironment.notDecl = declareStdUnaryOp("\\", StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.andDecl = declareStdBinaryOp("/\\", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.orDecl = declareStdBinaryOp("\\/", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);

    StdEnvironment.integerDecl = declareStdType("Integer", StdEnvironment.integerType);
    StdEnvironment.maxintDecl = declareStdConst("maxint", StdEnvironment.integerType);
    StdEnvironment.addDecl = declareStdBinaryOp("+", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.subtractDecl = declareStdBinaryOp("-", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.multiplyDecl = declareStdBinaryOp("*", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.divideDecl = declareStdBinaryOp("/", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.moduloDecl = declareStdBinaryOp("//", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.lessDecl = declareStdBinaryOp("<", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notgreaterDecl = declareStdBinaryOp("<=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.greaterDecl = declareStdBinaryOp(">", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notlessDecl = declareStdBinaryOp(">=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);

    StdEnvironment.charDecl = declareStdType("Char", StdEnvironment.charType);
    StdEnvironment.chrDecl = declareStdFunc("chr", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos), StdEnvironment.charType);
    StdEnvironment.ordDecl = declareStdFunc("ord", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos), StdEnvironment.integerType);
    StdEnvironment.eofDecl = declareStdFunc("eof", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.eolDecl = declareStdFunc("eol", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.getDecl = declareStdProc("get", new SingleFormalParameterSequence(
                                      new VarFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.putDecl = declareStdProc("put", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.getintDecl = declareStdProc("getint", new SingleFormalParameterSequence(
                                            new VarFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.putintDecl = declareStdProc("putint", new SingleFormalParameterSequence(
                                            new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.geteolDecl = declareStdProc("geteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.puteolDecl = declareStdProc("puteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.equalDecl = declareStdBinaryOp("=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
    StdEnvironment.unequalDecl = declareStdBinaryOp("\\=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);

  }

  // while Exp end
  @Override
  public Object visitWhileEndCommand(WhileEndCommand aThis, Object o) {
    TypeDenoter eType = (TypeDenoter) aThis.E.visit(this, null);
    if (!eType.equals(StdEnvironment.booleanType)) {
      reporter.reportError("Boolean expression expected here", "", aThis.E.position);
    }
    return null;
  }
    //if Exp then Com1 ( | Expi then Comi )* else Com2 end
    @Override
    public Object visitThenCommandAST(ThenCommand aThis, Object o) {
        aThis.C.visit(this, null);
        return null;
    }
   // do command
    @Override
    public Object visitDoCommandAST(DoCommand aThis, Object o) {
         aThis.C.visit(this, null);
         return null;
    }
    @Override
    public Object visitRepeatCommand(RepeatCommand aThis, Object o) {
    aThis.WhileC.visit(this, aThis);
    return null;    }
    
    @Override
    public Object visitRepeatTimesCommand(RepeatTimesCommand aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Object visitRepeatUntilAST(RepeatUntilAST aThis, Object o) {
        aThis.UntilC.visit(this, aThis);
        return null;
    }
    // repeat until Exp do Com end
    @Override
    public Object visitUntilCommand(UntilCommand aThis, Object o) {
       TypeDenoter eType = (TypeDenoter) aThis.I.visit(this, null);
        if (!eType.equals(StdEnvironment.booleanType)) {
          reporter.reportError("Boolean expression expected here", "", aThis.I.position);
        }
        if(o != null){
          RepeatDeclaration repeat = new RepeatDeclaration(dummyPos);
          idTable.openScope();
          if(((RepeatUntilAST) o).I == null)
              idTable.enter("", repeat);
          else
              idTable.enter(((RepeatUntilAST) o).I.spelling, repeat);

          if(repeat.duplicated)
            reporter.reportError("identifier \"%\" already declared", 
              ((RepeatUntilAST) o).I.spelling, ((RepeatUntilAST) o).position);

          aThis.C.visit(this, null);
          idTable.closeScope();
        }
        else
          aThis.C.visit(this, null);
        return null;
    }    
    @Override
    public Object visitDoWhileCommand(DoWhileCommand aThis, Object o) {
         TypeDenoter eType = (TypeDenoter) aThis.E.visit(this, null);
        if (!eType.equals(StdEnvironment.booleanType)) {
          reporter.reportError("Boolean expression expected here", "", aThis.E.position);
        }
        return null;
    }
     // Repeat do Com while Exp end
    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileAST aThis, Object o) {
    RepeatDeclaration repeat = new RepeatDeclaration(dummyPos);
        if(aThis.I != null){
          idTable.openScope();
          idTable.enter(aThis.I.spelling, repeat);
          if(repeat.duplicated)
            reporter.reportError("identifier \"%\" already declared", aThis.I.spelling, aThis.position);

          aThis.C.visit(this, null);
          idTable.closeScope();
        }
        else{
          idTable.openScope();
          idTable.enter("", repeat);
          aThis.C.visit(this, null);
          idTable.closeScope();
        }

        aThis.DoWhile.visit(this, null);
    return null;    }

    @Override
    public Object visitVarDeclarationBecomes(VarDeclarationBecomes aThis, Object o) {
         TypeDenoter eType = (TypeDenoter) aThis.Ex.visit(this, null);

        // El tipo de dato del identificador es el de la expresion
        aThis.E = eType;

        // Ingresar el identificador a la tabla
        idTable.enter(aThis.I.spelling, aThis);

        // Determinar si el identificador se encuentra duplicado
        if (aThis.duplicated)
          reporter.reportError("identifier \"%\" already declared",
              aThis.I.spelling, aThis.position);

        return null;
    }
    @Override
    public Object visitDoUntilCommand(DoUntilCommand aThis, Object o) {
        TypeDenoter eType = (TypeDenoter) aThis.E.visit(this, null);
        if (!eType.equals(StdEnvironment.booleanType)) {
          reporter.reportError("Boolean expression expected here", "", aThis.E.position);
        }
        return null;
    }
     //repeat do com until exp end
    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilAST aThis, Object o) {
        RepeatDeclaration repeat = new RepeatDeclaration(dummyPos);
        if(aThis.I != null){
          idTable.openScope();
          idTable.enter(aThis.I.spelling, repeat);
          if(repeat.duplicated)
            reporter.reportError("identifier \"%\" already declared", aThis.I.spelling, aThis.position);

          aThis.C.visit(this, null);
          idTable.closeScope();
        }
        else{
          idTable.openScope();
          idTable.enter("", repeat);
          aThis.C.visit(this, null);
          idTable.closeScope();
        }

        aThis.DoUntil.visit(this, null);
        return null;
    }
    
    
    @Override
    public Object visitForBecomesCommand(ForBecomesCommand aThis, Object o) {
       TypeDenoter eType = (TypeDenoter) aThis.E.visit(this, null);
        if (!eType.equals(StdEnvironment.integerType))
          reporter.reportError("Integer Expression expected here", "", aThis.E.position);

        return null;
    }
// for Id from Exp1 to Exp2 do Com end
    @Override
    public Object visitForBecomesAST(ForBecomesAST aThis, Object o) {
        aThis.ForBecomes.visit(this, null); // exp1
        aThis.E.visit(this, null); // exp2
        idTable.openScope(); // Se inicia el scope para el com y ids.
        
        idTable.enter(aThis.ForBecomes.I.spelling, aThis.ForBecomes); // ingresa el id del for
        if (aThis.ForBecomes.duplicated)
          reporter.reportError("identifier \"%\" already declared", aThis.I.spelling, aThis.position);

        aThis.DoC.visit(this, null); // command
        idTable.closeScope(); // Se cierra el scope.
        return null;
    }
     // for Id from Exp1 to Exp2 while Exp3 do Com end
    @Override
    public Object visitRepeatForWhile(RepeatForWhile aThis, Object o) {
    aThis.ForBecomes.visit(this, null); // from exp1
    aThis.E.visit(this, null); // To exp2

    idTable.openScope(); // Se inicia el scope, para los id y el com.
    idTable.enter(aThis.ForBecomes.I.spelling, aThis.ForBecomes); // ingresa el id del for
    if (aThis.ForBecomes.duplicated)
      reporter.reportError("identifier \"%\" already declared", aThis.I.spelling, aThis.position);

    aThis.whileC.E.visit(this, null);
    aThis.whileC.C.visit(this, null);
    //aThis.whileC.visit(this, null); // while exp do command (exp3)
    idTable.closeScope(); // Se cierra el scope.
    return null;
    }
    // Autores: Gabriel Mora
  // for Id from Exp1 to Exp2 until Exp3 do Com end
    @Override
    public Object visitRepeatForUntil(RepeatForUntil aThis, Object o) {
        aThis.ForBecomes.visit(this, null); // from exp1
        aThis.E.visit(this, null); // To exp2
        idTable.openScope(); // Se inicia el scope para los id y el com.
      

        idTable.enter(aThis.ForBecomes.I.spelling, aThis.ForBecomes); // ingresa el id del for
        if (aThis.ForBecomes.duplicated)
          reporter.reportError("identifier \"%\" already declared", aThis.I.spelling, aThis.position);

        aThis.UntilC.visit(this, null); // until exp do command (exp3)
        idTable.closeScope(); // Se cierra el scope.
        return null;
    }    
    @Override
    public Object visitDotDCommandAST(DotDCommand aThis, Object o) {
        TypeDenoter eType = (TypeDenoter) aThis.CLC.visit(this, null);
        if (!eType.equals(StdEnvironment.integerType)) {
          reporter.reportError("Integer expression expected here", "", aThis.CLC.position);
        }
        return null;
    }    

    @Override
    public Object visitDotDCommandLiteral(DotDCommandLiteral aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitRecDeclaration(RecDeclaration aThis, Object o) {
          aThis.dAST.visit(this, null);
    return null;
    }
//verificar
    @Override
    public Object visitVarDeclarationBecomes(VarDeclaration ast, Object o) {
        ast.T = (TypeDenoter) ast.T.visit(this, null);
        idTable.enter(ast.I.spelling, ast);
        if (ast.duplicated)
          reporter.reportError("identifier \"%\" already declared",
              ast.I.spelling, ast.position);

        return null;
    }
    
    /*
    private void agregarFunc(FuncDeclaration aThis) {

    aThis.T = (TypeDenoter) aThis.T.visit(this, null);

    // Agregar a la tabla
    idTable.enter(aThis.I.spelling, aThis); // permits recursion

    // Determinar si el identificador esta repetido
    if (aThis.duplicated) {
      reporter.reportError("identifier \"%\" already declared",
          aThis.I.spelling, aThis.position);
    }

    idTable.openScope();

    aThis.FPS.visit(this, null);

    idTable.closeScope();

    // Colocar como visitado
    aThis.visited = true;
  }
    
  private void agregarProc(ProcDeclaration aThis) {

    // Agregar a la tabla
    idTable.enter(aThis.I.spelling, aThis);

    // Determinar si el identificador esta repetido
    if (aThis.duplicated) {
      reporter.reportError("identifier \"%\" already declared",
          aThis.I.spelling, aThis.position);
    }

    idTable.openScope();

    aThis.FPS.visit(this, null);
    // aThis.C.visit(this, null); Bug

    idTable.closeScope();

    // Colocar como visitado
    aThis.visited = true;
  }
  
  private void visitarProcFuncs(SequentialDeclarationProcFuncs aThis) {
    if (aThis.D2 instanceof FuncDeclaration) {
      agregarFunc((FuncDeclaration) aThis.D2);
      aThis.D1.visit(this, null);
    } else {
      if (aThis.D2 instanceof ProcDeclaration) {
        agregarProc((ProcDeclaration) aThis.D2);
        aThis.D1.visit(this, null);
      }
    }
  }
  @Override
  public Object visitSequentialDeclarationProcFuncs(SequentialDeclarationProcFuncs aThis, Object o) {

    // Caso base para la recursion
    if (aThis.D2 instanceof ProcDeclaration && ((ProcDeclaration) aThis.D2).visited) {
      return null;
    }
    // Caso base para la recursion
    if (aThis.D2 instanceof FuncDeclaration && ((FuncDeclaration) aThis.D2).visited) {
      return null;
    }

    // Determina si es un proc funcs
    if (aThis.D1 instanceof SequentialDeclarationProcFuncs) {
      visitarProcFuncs(aThis);
    }
    // Otros casos
    else {
      if (aThis.D1 instanceof ProcDeclaration) {
        agregarProc((ProcDeclaration) aThis.D1);
      } else {
        if (aThis.D1 instanceof FuncDeclaration) {
          agregarFunc((FuncDeclaration) aThis.D1);
        }
      }
      if (aThis.D2 instanceof ProcDeclaration) {
        agregarProc((ProcDeclaration) aThis.D2);
      } else {
        if (aThis.D2 instanceof FuncDeclaration) {
          agregarFunc((FuncDeclaration) aThis.D2);
        }
      }
    }
    // Vistar los hijos
    aThis.D1.visit(this, null);
    aThis.D2.visit(this, null);
    return null;
  }
  */

    @Override
    public Object visitSequentialDeclarationProcFuncs(SequentialDeclarationProcFuncs aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
