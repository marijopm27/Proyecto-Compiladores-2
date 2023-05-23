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
public class DotDCommandLiteral extends Command{
    public DotDCommandLiteral(CaseLiteralCommand cAST, SourcePosition thePosition) {
        super(thePosition);
        CLC = cAST;
    }

    public Object visit(Visitor v, Object object) {
        return v.visitDotDCommandLiteral(this, object);
    }

    public CaseLiteralCommand CLC;
    
}
