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
public class MultipleCaseRange extends CaseRangeCommand{
    
    public MultipleCaseRange(CaseRangeCommand cAST1, SourcePosition thePosition) {
        super(thePosition);
        CRC1 = cAST1;
        CRC2 = null;
    }

    public MultipleCaseRange(MultipleCaseRange cAST1, CaseRangeCommand cAST2, SourcePosition thePosition) {
        super(thePosition);
        CRC1 = cAST1;
        CRC2 = cAST2;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitMultipleCaseRange(this, o);
    }
    
    public CaseRangeCommand CRC1,CRC2;
    
}
