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
public class CompoundSingleDeclaration extends Declaration{
    public CompoundSingleDeclaration(Declaration declarationAST, SourcePosition position){
        super (position);
        dAST = declarationAST;
    }
    
    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCompoundSingleDeclaration(this, o);
    }
    
    public Declaration dAST;
}
