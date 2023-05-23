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
public class RepeatDoUntilAST extends Command{

    public Command C;
    public Identifier I;
    public DoUntilCommand DoUntil;
    
    public RepeatDoUntilAST(Identifier iAST, Command cAST, DoUntilCommand UntilAST, SourcePosition commandPos) {
        super(commandPos);
        C = cAST;
        I = iAST;
        DoUntil = UntilAST;
    }
    
    public RepeatDoUntilAST(Command cAST, DoUntilCommand UntilAST, SourcePosition commandPos) {
        super(commandPos);
        C = cAST;
        I = null;
        DoUntil = UntilAST;
    }
    public Object visit(Visitor v, Object o) {
        return v.visitRepeatDoUntilCommand(this, o);
    }

}

       
