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
public class SingleThen extends ThenCommand{
     public SingleThen(ThenCommand cAST, Expression eAST, SourcePosition thePosition) {
        super (thePosition);
        TC = cAST;
        E = eAST;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitSingleThen(this, o);
    }
    public ThenCommand TC; 
    public Expression E;
    
}
