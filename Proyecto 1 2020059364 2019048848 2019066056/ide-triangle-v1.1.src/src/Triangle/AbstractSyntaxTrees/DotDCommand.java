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
public class DotDCommand extends Command{
    
    public DotDCommand(Expression eAST, SourcePosition thePosition) {
        super(thePosition);
        CLC = eAST;
    }

    public Object visit(Visitor v, Object object) {
        return v.visitDotDCommandAST(this, object);
    }

    public Expression CLC;
    
   }
