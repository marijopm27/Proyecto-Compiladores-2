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
public class MultipleCase extends CaseCommand{
   
    public MultipleCase(CaseCommand cAST1, CaseCommand cAST2, SourcePosition thePosition) {
        super(thePosition);
        CC1 = cAST1;
        CC2 = cAST2;
    }

    public MultipleCase(CaseCommand cAST1, SourcePosition thePosition) {
        super(thePosition);
        CC1 = cAST1;
        CC2 = null;
    }

    public Object visit(Visitor v, Object object) {
        return v.visitMultipleCase(this, object);
    }

    public CaseCommand CC1,CC2;
}
