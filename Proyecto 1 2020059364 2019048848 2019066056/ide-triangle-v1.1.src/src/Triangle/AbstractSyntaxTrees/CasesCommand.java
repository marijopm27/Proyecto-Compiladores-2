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
public class CasesCommand extends Command{
    
    public CasesCommand(SingleCase scAST, SourcePosition thePosition) {
        super(thePosition);
        SC = scAST;
        MC = null;
    }

    public CasesCommand(MultipleCase mcAST, SourcePosition thePosition) {
        super(thePosition);
        MC = mcAST;
        SC = null;
    }

    public Object visit(Visitor v, Object arg) {
        return v.visitCasesCommand(this, arg);
    }

    public SingleCase SC;
    public MultipleCase MC;
    
}
