/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Triangle.AbstractSyntaxTrees;
import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Gabriel Mora Estribí
 */
public class DoCommand extends Command{
   public DoCommand (Command cAST, SourcePosition thePosition) {
    super (thePosition);
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitDoCommandAST(this, o);
  }

  public Command C;
}
