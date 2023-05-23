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
public class CaseLiterals extends Command{
     public CaseLiterals(SingleCaseRange scAST, SourcePosition thePosition) {
        super(thePosition);
        SCR = scAST;
        MCR = null;
    }
    
    public CaseLiterals(MultipleCaseRange mcAST, SourcePosition thePosition) {
        super(thePosition);
        MCR = mcAST;
        SCR = null;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitCaseLiterals(this, o);
    }
    
    public SingleCaseRange SCR;
    public MultipleCaseRange MCR;
}
