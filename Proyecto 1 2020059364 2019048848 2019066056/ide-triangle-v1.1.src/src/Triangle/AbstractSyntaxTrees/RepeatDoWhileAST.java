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
public class RepeatDoWhileAST extends Command{
    public Command C;
    public Identifier I;
    public DoWhileCommand DoWhile;
    
    public RepeatDoWhileAST(Identifier iAST, Command cAST, DoWhileCommand DWhile, SourcePosition commandPos) {
        super(commandPos);
        C = cAST;
        I = iAST;
        DoWhile = DWhile;
    }
    
    public RepeatDoWhileAST(Command cAST, DoWhileCommand DWhile, SourcePosition commandPos) {
        super(commandPos);
        C = cAST;
        I = null;
        DoWhile = DWhile;
    }
    
    public Object visit(Visitor v, Object o) {
        return v.visitRepeatDoWhileCommand(this, o);
    }


}
