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
public class RepeatUntilAST extends Command {
    
  public Identifier I;
  public UntilCommand UntilC;
  
  public RepeatUntilAST (Identifier iAST, UntilCommand UntilAST, SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    UntilC = UntilAST;
  }
  public RepeatUntilAST (UntilCommand UntilAST, SourcePosition thePosition) {
    super (thePosition);
    I = null;
    UntilC = UntilAST;
  }
  
  public Object visit(Visitor v, Object o) {
    return v.visitRepeatUntilAST(this, o);
  }


}
