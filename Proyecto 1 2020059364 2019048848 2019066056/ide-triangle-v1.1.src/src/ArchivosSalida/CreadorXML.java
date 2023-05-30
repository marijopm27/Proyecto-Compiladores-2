/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArchivosSalida;

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
import Triangle.AbstractSyntaxTrees.DoCommand;
import Triangle.AbstractSyntaxTrees.DoUntilCommand;
import Triangle.AbstractSyntaxTrees.DoWhileCommand;
import Triangle.AbstractSyntaxTrees.DotDCommand;
import Triangle.AbstractSyntaxTrees.DotDCommand2;
import Triangle.AbstractSyntaxTrees.DotDCommandLiteral;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForBecomesAST;
import Triangle.AbstractSyntaxTrees.ForBecomesCommand;
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
import Triangle.AbstractSyntaxTrees.PrivateDeclaration;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecDeclaration;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilAST;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileAST;
import Triangle.AbstractSyntaxTrees.RepeatForUntil;
import Triangle.AbstractSyntaxTrees.RepeatForWhile;
import Triangle.AbstractSyntaxTrees.RepeatTimesCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilAST;
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
import Triangle.AbstractSyntaxTrees.ThenCommand;
import Triangle.AbstractSyntaxTrees.TimesCommand;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.UntilCommand;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarDeclarationBecomes;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import Triangle.AbstractSyntaxTrees.WhileEndCommand;
import Triangle.CodeGenerator.Field;
import Triangle.CodeGenerator.KnownAddress;
import Triangle.CodeGenerator.KnownRoutine;
import Triangle.CodeGenerator.KnownValue;
import Triangle.CodeGenerator.UnknownAddress;
import Triangle.CodeGenerator.UnknownRoutine;
import Triangle.CodeGenerator.UnknownValue;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Celina Madrigal Murillo
 */
public class CreadorXML implements Visitor{
    
    private final FileWriter writerXML;
    
    public CreadorXML(FileWriter escritor) {
        this.writerXML = escritor;
    }
    
private void addLine(String line) {
    try {
        writerXML.write(line);
        writerXML.write(System.lineSeparator());
    } catch (IOException e) {
        System.err.println("Error al agregar linea al XML: " + e.getMessage());
    }
}
    
    @Override
    public Object visitDoCommandAST(DoCommand aThis, Object o) {
        addLine("<DoCommand>");
        aThis.C.visit(this, null);
        addLine("</DoCommand>");
        return(null);
    }

    @Override
    public Object visitRepeatCommand(RepeatCommand aThis, Object o) {
        addLine("<RepeatCommand>");
        aThis.WhileC.visit(this, null);
        addLine("</RepeatCommand>");
        return(null);
    }
    
    @Override
    public Object visitRepeatUntilAST(RepeatUntilAST aThis, Object o) { 
        addLine("<RepeatUntil>");
        
         if(aThis.I != null){
            aThis.I.visit(this, null);
        }
        aThis.UntilC.visit(this, null);
        
        addLine("</RepeatUntil>");
        return(null);
    }
    
    @Override
    public Object visitUntilCommand(UntilCommand aThis, Object o) { 
        addLine("<UntilCommand>");
        aThis.I.visit(this, null);
        aThis.C.visit(this, null);
        addLine("</UntilCommand>");
        return(null);
    }
    
    @Override
    public Object visitDoWhileCommand(DoWhileCommand aThis, Object o) { 
        addLine("<DoWhileCommand>");
        aThis.E.visit(this, null);
        addLine("</DoWhileCommand>");
        return(null);
    }

    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileAST aThis, Object o) { 
        addLine("<RepeatDoWhileCommand>");
        
        aThis.DoWhile.visit(this, null);
        aThis.C.visit(this, null);
        addLine("</RepeatDoWhileCommand>");
        return(null);}
    
    @Override
    public Object visitDoUntilCommand(DoUntilCommand aThis, Object o) {
        addLine("<DoUntilCommand>");
        aThis.E.visit(this, null);
        addLine("</DoUntilCommand>");
        return(null);
    }

    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilAST aThis, Object o) {
        addLine("<RepeatDoUntilCommand>");
        aThis.DoUntil.visit(this, null);
        aThis.C.visit(this, null);
        addLine("</RepeatDoUntilCommand>");
        return(null);}
    
    @Override
    public Object visitForBecomesCommand(ForBecomesCommand aThis, Object o) { 
        addLine("<ForBecomesCommand>");
        aThis.I.visit(this, null);
        aThis.E.visit(this, null);   
        addLine("</ForBecomesCommand>");
        return(null);
    }
    
    @Override
    public Object visitForBecomesAST(ForBecomesAST aThis, Object o) { 
        addLine("<ForBecomes>");
        aThis.E.visit(this, null);
        aThis.ForBecomes.visit(this, null);
        aThis.DoC.visit(this, null);
        addLine("</ForBecomes>");
        return(null);
    }

    @Override
    public Object visitRepeatForWhile(RepeatForWhile aThis, Object o) { 
        
        addLine("<RepeatForWhile>");
        
        if(aThis.I != null){
            aThis.I.visit(this, null);
        }
        aThis.E.visit(this, null);
        aThis.ForBecomes.visit(this, null);
        aThis.whileC.visit(this, null);
      
        addLine("</RepeatForWhile>");
        
        return(null);
    }

    public Object visitRepeatForUntil(RepeatForUntil aThis, Object o) { 
        addLine("<RepeatForUntil>");
         if(aThis.I != null){
            aThis.I.visit(this, null);
        }
        aThis.E.visit(this, null);
        aThis.ForBecomes.visit(this, null);
        aThis.UntilC.visit(this, null);
        addLine("</RepeatForUntil>");
        return(null);
    }

    @Override
    public Object visitDotDCommandAST(DotDCommand aThis, Object o) { 
        addLine("<DotDCommand>");
        aThis.CLC.visit(this, null);
        addLine("</DotDCommand>");
        return(null);
    }
    
    @Override
    public Object visitRepeatTimesCommand(RepeatTimesCommand aThis, Object o) {
        addLine("<RepeatTimesCommand>");
        aThis.TimesC.visit(this, null);
        addLine("</RepeatTimesCommand>");
        return(null);
    }  
    
    @Override
    public Object visitAssignCommand(AssignCommand ast, Object o) { 
      addLine("<AssingCommand>");
      ast.V.visit(this, null);
      ast.E.visit(this, null);
      addLine("</AssingCommand>");
      return(null);
  }
  
  public Object visitCallCommand(CallCommand ast, Object o) { 
      addLine("<CallCommand>");
      ast.I.visit(this, null);
      ast.APS.visit(this, null);
      addLine("</CallCommand>");
      return(null);
  }
  @Override
  public Object visitCaseCommand(CaseCommand ast, Object o) { 
      addLine("<CaseCommand>");
      ast.CL.visit(this, null);
      ast.C.visit(this, null);
      addLine("</CaseCommand>");
      return(null);
  }
  @Override
  public Object visitCasesCommand(CasesCommand ast, Object o) { 
      addLine("<CasesCommand>");
      ast.SC.visit(this, null);
      ast.MC.visit(this, null);
      addLine("</CasesCommand>");
      return(null);
  }
  @Override
  public Object visitSelectCommand(SelectCommand ast, Object o) { 
      addLine("<SelectCommand>");
      ast.CC.visit(this, null);
      ast.C.visit(this, null);
      ast.E.visit(this, null);
      addLine("<SelectCommand>");
      return(null);
  }
  @Override
  public Object visitEmptyCommand(EmptyCommand ast, Object o) { 
      addLine("<EmptyCommand>");
      addLine("</EmptyCommand>");
      return(null);
  }
  @Override
  public Object visitIfCommand(IfCommand ast, Object o) { 
      addLine("<IfCommand>");
      ast.E.visit(this, null);
      ast.C1.visit(this, null);
      ast.C2.visit(this, null);
      addLine("<IfCommand>");
      return(null);
  }
  @Override
  public Object visitLetCommand(LetCommand ast, Object o) {     
      addLine("<LetCommand>");
      ast.D.visit(this, null);
      ast.C.visit(this, null);
      addLine("</LetCommand>");
      
      return(null);
  }
  @Override
  public Object visitSequentialCommand(SequentialCommand ast, Object o) { 
      addLine("<SequentialCommand>");
      ast.C1.visit(this, null);
      ast.C2.visit(this, null);
      addLine("</SequentialCommand>");
      
      return(null);
  }
  @Override
  public Object visitSequentialCases(SequentialCases ast, Object o) { 
      addLine("<SequentialCases>");
      ast.CC1.visit(this, null);
      ast.CC2.visit(this, null);
      addLine("</SequentialCases>");
      return(null);
  }
  @Override
  public Object visitWhileCommand(WhileCommand ast, Object o) { 
      addLine("<WhileCommand>");
      ast.E.visit(this, null);
      ast.C.visit(this, null);
      addLine("</WhileCommand>");
      return(null);
  }
@Override
  public Object visitTimesCommand(TimesCommand ast, Object o) { 
      addLine("<TimesCommand>");
      ast.C.visit(this, null);
      addLine("</TimesCommand>");
      return(null);
  }  
@Override
    public Object visitCaseLiteralCommand(CaseLiteralCommand ast, Object o) { 
        addLine("<CaseLiteralCommand>");
        ast.CL.visit(this, null);
        ast.IL.visit(this, null);
        addLine("</CaseLiteralCommand>");
        return(null);
    }
@Override
    public Object visitCaseRangeCommand(CaseRangeCommand ast, Object o) { 
        addLine("<CaseRangeCommand>");
        ast.CLC.visit(this, null);
        ast.DCL.visit(this, null);
        addLine("</CaseRangeCommand>");
        return(null);
     }
    
    public Object visitDotDCommandLiteralAST(DotDCommandLiteral ast, Object o){
        addLine("<DotDCommandLiteral>");
        ast.CLC.visit(this, null);
        addLine("</DotDCommandLiteral>");
        return(null);
    }
@Override
    public Object visitDotDCommand2(DotDCommand2 ast, Object o){
        addLine("<DotDCommand>");
        ast.CLCT.visit(this, null);
        addLine("</DotDCommand>");
        return(null);
    }
    @Override
    public Object visitBarCommandCaseRange(BarCommandCaseRange ast, Object obj){
        addLine("<BarCommandCaseRange>");
        ast.CRC.visit(this, null);
        addLine("</BarCommandCaseRange>");
        return(null);
    }
@Override
     public Object visitCaseLiterals(CaseLiterals ast, Object o) { 
         addLine("<CaseLiterals>");
         ast.MCR.visit(this, null);
         ast.SCR.visit(this, null);
         addLine("</CaseLiterals>");
          return(null);
      }
@Override
     public Object visitThenCommandAST(ThenCommand aThis, Object o) { // 
         addLine("<ThenCommand>");
          aThis.C.visit(this, null);
          addLine("</ThenCommand>");
          return(null);
        }
     @Override
     public Object visitVarDeclarationBecomes(VarDeclarationBecomes aThis, Object o) { // 
            addLine("<VarDeclarationBecomes>");
            aThis.E.visit(this, null);
            aThis.I.visit(this, null);
            addLine("</VarDeclarationBecomes>");
            return(null);
        }
     
     @Override
     public Object visitArrayExpression(ArrayExpression ast, Object o) { 
        addLine("<ArrayExpresssion>");
        ast.AA.visit(this, null);
        addLine("</ArrayExpresssion>");
        return(null);
  }
  @Override
  public Object visitBinaryExpression(BinaryExpression ast, Object o) { 
      addLine("<BinaryExpresssion>");
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.O.visit(this, null);
      addLine("</BinaryExpresssion>");
      return(null);
  }
  @Override
  public Object visitCallExpression(CallExpression ast, Object o) { 
      addLine("<CallExpresssion>");
      ast.I.visit(this, null);
      ast.APS.visit(this, null);
      addLine("</CallExpresssion>");
      return(null);
  }
  @Override
  public Object visitCharacterExpression(CharacterExpression ast, Object o) { 
      addLine("<CharacterExpresssion>");
      ast.CL.visit(this, null);
      addLine("</CharacterExpresssion>");
      return(null);
  }
  @Override
  public Object visitEmptyExpression(EmptyExpression ast, Object o) {  
      addLine("<EmptyExpresssion>");
      addLine("</EmptyExpresssion>");
      return(null);
  }
  @Override
  public Object visitIfExpression(IfExpression ast, Object o) {  
      addLine("<IfExpresssion>");
      ast.E1.visit(this, null);
      ast.E2.visit(this, null);
      ast.E3.visit(this, null);
      addLine("</IfExpresssion>");
      return(null);
  }
  @Override
  public Object visitIntegerExpression(IntegerExpression ast, Object o) { 
      addLine("<IntegerExpresssion>");
      addLine("</IntegerExpresssion>");
      return(null);
  }
  @Override
  public Object visitLetExpression(LetExpression ast, Object o) { 
      addLine("<LetExpresssion>");
      ast.D.visit(this, null);
      ast.E.visit(this, null);
      addLine("</LetExpresssion>");
      return(null);
  }
  @Override
  public Object visitRecordExpression(RecordExpression ast, Object o) {   
      addLine("<RecordExpresssion>");
      ast.RA.visit(this, null);
      addLine("</RecordExpresssion>");
      return(null);
  }
  @Override
  public Object visitUnaryExpression(UnaryExpression ast, Object o) { 
      addLine("<UnaryExpresssion>");
      ast.E.visit(this, null);
      ast.O.visit(this, null);
      addLine("</UnaryExpresssion>");
      return(null);
  }
  @Override
  public Object visitVnameExpression(VnameExpression ast, Object o) {
      addLine("<VnameExpresssion>");
      ast.V.visit(this, null);
      addLine("</VnameExpresssion>");
      return(null);
  }
   @Override
   public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {    
      addLine("<BinaryOperatorDeclaration>");
      addLine("</BinaryOperatorDeclaration>");
      return(null);
  }
   @Override
  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {   
     addLine("<ConstDeclaration>");
      ast.E.visit(this, null);
      ast.I.visit(this, null);
      addLine("</ConstDeclaration>");
      return(null);
  }
  @Override
  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {    
      addLine("<FuncDeclaration>");
      ast.T.visit(this, null);            
      ast.FPS.visit(this, null);
      ast.E.visit(this, null);
      addLine("</FuncDeclaration>");    
      return(null);
  }
  @Override
  public Object visitProcDeclaration(ProcDeclaration ast, Object o) { 
      
      addLine("<ProcDeclaration>");
      ast.FPS.visit(this, null);
      ast.C.visit(this, null);
      addLine("</ProcDeclaration>");   
      return(null);
  }
  @Override
  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
      addLine("<SequentialDeclaration>");  
      ast.D1.visit(this, null);
      ast.D2.visit(this, null);
      addLine("</SequentialDeclaration>");  
      return(null);
  }
  @Override
  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) { 
      addLine("<TypeDeclaration>");  
      ast.T.visit(this, null);
      addLine("</TypeDeclaration>");  
      return(null);
  }
  @Override
  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {    
      addLine("<UnaryOperatorDeclaration>");  
      addLine("</UnaryOperatorDeclaration>");  
      return(null);
  }
  @Override
  public Object visitVarDeclaration(VarDeclaration ast, Object o) {      
      addLine("<VarDeclaration>");  
      addLine("</VarDeclaration>");
      ast.T.visit(this, null);
      return(null);
  }
  @Override
  public Object visitPrivateDeclaration(PrivateDeclaration ast, Object o) { 
      addLine("<PrivateDeclaration>");
      ast.D1.visit(this, null);
      ast.D2.visit(this, null);
      addLine("</PrivateDeclaration>");
      return(null);
  }    
@Override
  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) { 
      addLine("<MultipleArrayAggregate>");
      ast.AA.visit(this, null);
      ast.E.visit(this, null);
      addLine("</MultipleArrayAggregate>");
      return(null);
  }
  @Override
  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) { 
      addLine("<SingleArrayAggregate>");
      ast.E.visit(this, null);
      addLine("</SingleArrayAggregate>");
      return(null);
  }
@Override
  // Record Aggregates
  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) { 
      addLine("<MultipleRecordAggregate>");
      ast.E.visit(this, null);
      ast.I.visit(this, null);
      ast.RA.visit(this, null);
      addLine("</MultipleRecordAggregate>");
      return(null);
  }
  @Override
  public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) { 
      addLine("<singleRecordAggregate>");
      ast.E.visit(this, null);
      ast.I.visit(this, null);
      addLine("</singleRecordAggregate>");
      return(null);
  }
   @Override
  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {       
      addLine("<ConstFormalParameter>");
      ast.I.visit(this, null);
      ast.T.visit(this, null);
      addLine("</ConstFormalParameter>");
      return(null);
  }
  @Override
  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {       
      addLine("<FuncFormalParameter>");
      ast.FPS.visit(this, null);      
      ast.T.visit(this, null);     
      addLine("</FuncFormalParameter>");
      return(null);
  }
  @Override
  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {       
      addLine("<ProcFormalParameter>");
      ast.FPS.visit(this, null);      
      addLine("</ProcFormalParameter>");
      return(null);
  }
  @Override
  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {       
      addLine("<ProcFormalParameter>");
      ast.T.visit(this, null);
      addLine("</ProcFormalParameter>");
      return(null);
  }
  @Override
  public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) { 
      addLine("<EmptyFormalParameterSequence>");
      addLine("</EmptyFormalParameter>");
      return(null);
  }
  @Override
  public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) { 
      addLine("<MultipleFormalParameterSequence>");
      ast.FP.visit(this, null);
      ast.FPS.visit(this, null);
      addLine("</MultipleFormalParameterSequence>");
      return(null);
  }
  @Override
  public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) { 
      addLine("<SingleFormalParameterSequence>");
      ast.FP.visit(this, null);
      addLine("</SingleFormalParameterSequence>");
      return(null);
  }
@Override
  // Actual Parameters
  public Object visitConstActualParameter(ConstActualParameter ast, Object o) { 
       addLine("<ConstActualParameter>");
      ast.E.visit(this, null);
      addLine("</ConstActualParameter>");
      return(null);
  }
  @Override
  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) { 
      addLine("<FuncActualParameter>");
      ast.I.visit(this, null);
      addLine("</FuncActualParameter>");
      return(null);
  }
  @Override
  public Object visitProcActualParameter(ProcActualParameter ast, Object o) { 
      addLine("<ProcActualParameter>");
      ast.I.visit(this, null);
      addLine("</ProcActualParameter>");
      return(null);
  }
  @Override
  public Object visitVarActualParameter(VarActualParameter ast, Object o) { 
      addLine("<VarActualParameter>");
      ast.V.visit(this, null);
      addLine("</VarActualParameter>");
      return(null);
  }
  @Override
  public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {   
      addLine("<EmptyActualParameterSequence>");
      addLine("</EmptyActualParameterSequence>");
      return(null);
  }
  @Override
  public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) { 
      addLine("<MultipleActualParameterSequence>");
      ast.AP.visit(this, null);
      ast.APS.visit(this, null);
      addLine("</MultipleActualParameterSequence>");
      return(null);
  }
  @Override
  public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {  
      addLine("<SingleActualParameterSequence>");
      ast.AP.visit(this, null);
      addLine("</SingleActualParameterSequence>");
      return(null);
  }
  @Override
  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {  
      addLine("<AnyTypeDenoter>");
      addLine("</AnyTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) { 
      addLine("<ArrayTypeDenoter>");
      ast.IL.visit(this, null);
      ast.T.visit(this, null);
      addLine("</ArrayTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
      addLine("<BoolTypeDenoter>");
      addLine("</BoolTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
      addLine("<CharTypeDenoter>");
      addLine("</CharTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) { 
      addLine("<ErrorTypeDenoter>");
      addLine("</ErrorTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) { 
       addLine("<SimpleTypeDenoter>");
       addLine("</SimpleTypeDenoter>");
      ast.I.visit(this, null);
      
      return(null);
  }
  @Override
  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) { 
      addLine("<IntTypeDenoter>");
      addLine("</IntTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {   
      addLine("<RecordTypeDenoter>");
      ast.FT.visit(this, null);
      addLine("</RecordTypeDenoter>");
      return(null);
  }
@Override
  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) { 
      addLine("<MultipleFieldTypeDenoter>");
      ast.FT.visit(this, null);
      ast.I.visit(this, null);
      ast.T.visit(this, null);
      addLine("</MultipleFieldTypeDenoter>");

      return(null);
  }
  @Override
  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) { 
      addLine("<SingleFieldTypeDenoter>");
      ast.I.visit(this, null);
      ast.T.visit(this, null);
      addLine("</SingleFieldTypeDenoter>");
      return(null);
  }
  @Override
  public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {  
       addLine("<CharacterLiteral>");
       addLine("</CharacterLiteral>");
      return(null);
  }
  @Override
  public Object visitIdentifier(Identifier ast, Object o) { 
      addLine("<Identifier>");
      addLine("</Identifier>");
      return(null);
  }
  @Override
  public Object visitIntegerLiteral(IntegerLiteral ast, Object o) { 
      addLine("<IntegerLiteral>");
      addLine("</IntegerLiteral>");
      return(null);
  }
  @Override
  public Object visitOperator(Operator ast, Object o) { 
      if("<".equals(ast.spelling))
            addLine("<Operator value='&lt;'>");
        else if(">".equals(ast.spelling))
            addLine("<Operator value='&gt;'>");
        else
            addLine("<Operator value= '" + ast.spelling + "'>");
        if(ast.decl != null){
            ast.decl.visit(this, null);
        }
        addLine("</Operator>");
        return(null);     
  }
  @Override
  public Object visitDotVname(DotVname ast, Object o) {
      addLine("<DotVname>");
      ast.I.visit(this, null);
      ast.V.visit(this, null);
      addLine("</DotVname>");
      return(null);
  }
  @Override
  public Object visitSimpleVname(SimpleVname ast, Object o) { 
      addLine("<SimpleVname>");
      ast.I.visit(this, null);
      addLine("</SimpleVname>");
      return(null);
  }
  @Override
  public Object visitSubscriptVname(SubscriptVname ast, Object o) { 
      addLine("<SubscriptVname>");
      ast.E.visit(this, null);
      ast.V.visit(this, null);
      addLine("</SubscriptVname>");
      return(null);
  }
  @Override
   public Object visitProgram(Program ast, Object o) { 
      addLine("<Program>");
      ast.C.visit(this, null);
      addLine("</Program>");
      return(null);
  }
    @Override
    public Object visitDotDCommandLiteral(DotDCommandLiteral aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public Object visitMultipleCaseRange(MultipleCaseRange aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitSingleCase(SingleCase aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitMultipleCase(MultipleCase aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitSingleCaseRange(SingleCaseRange aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitSingleThen(SingleThen aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitMultipleThen(MultipleThen aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitWhileEndCommand(WhileEndCommand aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitVarDeclarationBecomes(VarDeclaration ast, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object visitRecDeclaration(RecDeclaration aThis, Object o) {
        addLine("<RecDeclaration>");
      
        aThis.dAST.visit(this, null);
      
        addLine("</RecDeclaration>");
        return(null);    }
}
