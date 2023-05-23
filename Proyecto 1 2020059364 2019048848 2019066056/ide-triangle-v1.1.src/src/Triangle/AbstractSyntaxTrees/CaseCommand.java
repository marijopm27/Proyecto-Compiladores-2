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
public class CaseCommand extends Command{
  
    public CaseCommand(CaseLiterals clAST, Command cAST, SourcePosition thePosition) {
        super(thePosition);
        CL = clAST;
        C = cAST;
    }

    public CaseCommand(SourcePosition thePosition) {
        super(thePosition);
        CL = null;
        C = null;
    }

    public Object visit(Visitor v, Object arg) {
        return v.visitCaseCommand(this, arg);
    }

    public CaseLiterals CL;
    public Command C;
    
}
