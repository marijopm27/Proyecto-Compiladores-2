/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Triangle.AbstractSyntaxTrees;
import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Celina Madrigal Murillo
 */
public class CaseRangeCommand extends Command{
    
    
    public CaseRangeCommand (CaseLiteralCommand clAST, SourcePosition thePosition){
        super(thePosition);
        CLC = clAST;
        DCL = null;
    }

    public CaseRangeCommand (CaseLiteralCommand clAST, DotDCommandLiteral tcAST, SourcePosition thePosition){
        super(thePosition);
        CLC = clAST;
        DCL = tcAST;
    }
    
    public CaseRangeCommand (SourcePosition thePosition){
        super(thePosition);
        CLC = null;
        DCL = null;
    }
    
    public Object visit(Visitor v, Object o){
        return v.visitCaseRangeCommand(this, o);
    }
    
    public CaseLiteralCommand CLC;
    public DotDCommandLiteral DCL; 
}
