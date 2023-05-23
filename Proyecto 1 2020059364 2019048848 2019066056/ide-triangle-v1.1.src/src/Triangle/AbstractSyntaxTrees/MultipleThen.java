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
public class MultipleThen extends ThenCommand{
    public MultipleThen(MultipleThen cAST1, ThenCommand cAST2, Expression eAST, SourcePosition thePosition) {
        super (thePosition);
        TC1 = cAST1;
        TC2 = cAST2;
        E = eAST;
    }
    
    public MultipleThen(ThenCommand cAST1, SourcePosition thePosition) {
        super (thePosition);
        TC1 = cAST1;
        TC2 = null;
        E = null;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitMultipleThen(this, o);
    }
    
    public ThenCommand TC1, TC2; 
    public Expression E;
    
}
