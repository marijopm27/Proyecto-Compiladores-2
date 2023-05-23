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
public class CaseLiteralCommand extends Declaration{
    
    public CaseLiteralCommand (CharacterLiteral cAST, SourcePosition thePosition) {
        super (thePosition);
        CL = cAST;
        IL = null;
    }
    
    public CaseLiteralCommand (IntegerLiteral cAST, SourcePosition thePosition) {
        super (thePosition);
        IL = cAST;
        CL = null;
    }
    
    public Object visit(Visitor v, Object O){
        return v.visitCaseLiteralCommand(this, O);
    }
    
    public CharacterLiteral CL;
    public IntegerLiteral IL;
    
}
