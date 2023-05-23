/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Triangle.AbstractSyntaxTrees;
import Triangle.SyntacticAnalyzer.SourcePosition;
/**
 *
 * @author María José Porras Maroto
 */
public class RepeatForWhile extends Command{
    public ForBecomesCommand ForBecomes;   
    public DotDCommand E;
    public WhileCommand whileC;
    public Identifier I; 
    
    public RepeatForWhile(Identifier iAST, ForBecomesCommand ForFromVar, DotDCommand eAST, WhileCommand whileAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
        ForBecomes = ForFromVar;    
        E = eAST;
        whileC = whileAST;
    }
    public RepeatForWhile(ForBecomesCommand ForFromVar, DotDCommand eAST, WhileCommand whileAST, SourcePosition thePosition) {
        super (thePosition);
        I = null;
        ForBecomes = ForFromVar;    
        E = eAST;
        whileC = whileAST;
    }
    @Override
    public Object visit(Visitor v, Object o) {
      return v.visitRepeatForWhile(this, o);
    }

   
}
