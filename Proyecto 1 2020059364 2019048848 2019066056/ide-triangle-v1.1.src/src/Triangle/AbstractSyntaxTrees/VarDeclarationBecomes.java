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
public class VarDeclarationBecomes extends Declaration{
  public VarDeclarationBecomes (Identifier iAST, TypeDenoter eAST, SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    E = eAST;
  }
  
  public VarDeclarationBecomes (Identifier iAST, Expression eAST,
                         SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    Ex = eAST;
  }


  public Object visit(Visitor v, Object o) {
    return v.visitVarDeclarationBecomes(this, o);
  }

  public Identifier I;
  public TypeDenoter E;
  public Expression Ex;
}
    

