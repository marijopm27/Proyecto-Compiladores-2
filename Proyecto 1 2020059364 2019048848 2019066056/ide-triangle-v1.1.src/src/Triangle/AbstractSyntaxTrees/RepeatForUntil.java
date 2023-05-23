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
public class RepeatForUntil extends Command{
    public ForBecomesCommand ForBecomes;   
    public DotDCommand E;
    public UntilCommand UntilC;
    public Identifier I; 
    
    public RepeatForUntil(Identifier iAST, ForBecomesCommand ForFromVar, DotDCommand eAST, UntilCommand whileAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
        ForBecomes = ForFromVar;    
        E = eAST;
        UntilC = whileAST;
    }
    public RepeatForUntil(ForBecomesCommand ForFromVar, DotDCommand eAST, UntilCommand whileAST, SourcePosition thePosition) {
        super (thePosition);
        I = null;
        ForBecomes = ForFromVar;    
        E = eAST;
        UntilC = whileAST;
    }
    @Override
    public Object visit(Visitor v, Object o) {
      return v.visitRepeatForUntil(this, o);
    }
}
