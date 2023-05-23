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
public class SelectCommand extends Command{
    
    public SelectCommand (Expression eAST, CasesCommand ccAST, SourcePosition thePosition) {
        super (thePosition);
        E = eAST;
        CC = ccAST;
        C = null;
    }
    public SelectCommand (Expression eAST, CasesCommand ccAST, Command cAST,SourcePosition thePosition) {
        super (thePosition);
        E = eAST;
        CC = ccAST;
        C = cAST;
    }
    
    public Object visit(Visitor v, Object O){
        return v.visitSelectCommand(this, O);
    }
    
    public Expression E;
    public CasesCommand CC;
    public Command C;
    
}
