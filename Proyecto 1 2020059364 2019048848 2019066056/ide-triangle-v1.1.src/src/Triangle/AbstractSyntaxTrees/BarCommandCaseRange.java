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
public class BarCommandCaseRange extends Command{
    public BarCommandCaseRange (CaseRangeCommand east, SourcePosition thePosition) {
    super (thePosition);
    CRC = east;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitBarCommandCaseRange(this, o);
  }

  public CaseRangeCommand CRC;    
    
}
