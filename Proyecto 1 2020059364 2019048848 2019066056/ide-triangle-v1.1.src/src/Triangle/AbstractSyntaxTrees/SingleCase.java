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
public class SingleCase extends CaseCommand{
     
    public SingleCase(CaseCommand ccAST, SourcePosition thePosition) {
        super(thePosition);
        CC = ccAST;
    }

    public Object visit(Visitor v, Object object) {
        return v.visitSingleCase(this, object);
    }

    public CaseCommand CC;
    
}
