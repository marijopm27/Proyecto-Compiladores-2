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
public class SequentialCases extends CaseCommand{
    public SequentialCases (CaseCommand cc1AST, CaseCommand cc2AST, SourcePosition thePosition) {
    super (thePosition);
    CC1 = cc1AST;
    CC2 = cc2AST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitSequentialCases(this, o);
  }

  public CaseCommand CC1, CC2;
    
}
