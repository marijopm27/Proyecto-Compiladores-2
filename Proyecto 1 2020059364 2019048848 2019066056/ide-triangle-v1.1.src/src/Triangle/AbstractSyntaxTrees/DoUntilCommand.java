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
public class DoUntilCommand extends Command{
    public Expression E;
    
    public DoUntilCommand (Expression eAST, SourcePosition thePosition) {
      super (thePosition);
      E = eAST;
    }

    public Object visit(Visitor v, Object o) {
      return v.visitDoUntilCommand(this, o);
    }


}
