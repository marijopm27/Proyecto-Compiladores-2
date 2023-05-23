/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArchivosSalida;
import Triangle.SyntacticAnalyzer.Token;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author María José Porras Maroto
 */


public class ArchivoHTML {
    FileWriter writerHTML;
    String nombreHTML;
    

    public void escribir(String content){
        try (FileWriter writerHTML = new FileWriter(this.nombreHTML, true)){
            writerHTML.write("\n");
            writerHTML.write(content);
            writerHTML.write("\n");
            writerHTML.close();
        } catch (IOException e) {
           System.out.println("Error: no se pudo escribir la nueva linea la HTML");
        }
    }
//    
    public void HTML(Token currentToken){
        switch(currentToken.kind){
            
            //Casos para palabras reservadas, identificadores y literales
            //Unicamente las escribe en negrita en el archivo HTML
            case Token.ARRAY:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.CONST:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.DO:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.ELSE:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.END:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.FOR:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.FROM:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.FUNC:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.IF:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }    
            case Token.IN:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.LET:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.OF:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.PRIVATE: 
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.PROC:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.REC:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.RECORD:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.REPEAT:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.SELECT:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.SKIP:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.THEN:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.TIMES:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.TO:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.TYPE:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.UNTIL:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.VAR:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.WHEN:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            }     
            case Token.WHILE:
            {
                escribir("<b>"+ currentToken.spelling +"</b>");
                break;
            } 
            
            //Literales en azul
            case Token.INTLITERAL:
            {
                escribir(" <font style='padding-left:1em' color=\"blue\"><tt> "+ currentToken.spelling +" </tt></font> ");
                break;
            }    
            case Token.CHARLITERAL:
            {
                escribir(" <font style='padding-left:1em' color=\"blue\"><tt> "+ currentToken.spelling +" </tt></font> ");
                break;
            }
            
            //Van escritos en negro sin resaltar
            case Token.IDENTIFIER:
            {
                escribir(" <font style='padding-left:1em' color:'black'><tt> "+ currentToken.spelling +" </tt></font> ");
                break;
            }    
            case Token.OPERATOR:
            {
                escribir(" <font style='padding-left:1em' color:'black'><tt> "+ currentToken.spelling +" </tt></font> ");
                break;
            }

           case Token.EOT: { //el encontrar un token.EOT significa que sale de la generacion del html
                break;
            }
            default:
                break;
        }
    }
    public ArchivoHTML(String nombreArchivo) {
        this.nombreHTML = nombreArchivo;
        
    }
}
