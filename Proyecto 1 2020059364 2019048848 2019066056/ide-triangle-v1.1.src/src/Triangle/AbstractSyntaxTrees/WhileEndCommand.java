/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Triangle.AbstractSyntaxTrees;
import Triangle.SyntacticAnalyzer.SourcePosition;
/**
 *
 * @author gmora
 */
public class WhileEndCommand extends Command {
    
    public WhileEndCommand(Expression eAST, SourcePosition commandPos) {
    super(commandPos);
    E = eAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitWhileEndCommand(this, o);
  }

  public Expression E;
    
}
