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
public class RepeatTimesCommand extends Command{

    
    public RepeatTimesCommand (Expression eAST, TimesCommand Times, SourcePosition thePosition) {
    super (thePosition);
    E= eAST;
    TimesC = Times;
  }
public RepeatTimesCommand (TimesCommand Times, SourcePosition thePosition) {
    super (thePosition);
    TimesC = Times;
  }


     public Object visit(Visitor v, Object o) {
    return v.visitRepeatTimesCommand(this, o);
  }
  public Expression E;
  public TimesCommand TimesC;

}
