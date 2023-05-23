/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArchivosSalida;
import Triangle.AbstractSyntaxTrees.Program;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Celina Madrigal Murillo
 */
public class ArchivoXML {
    public static void crearXML(Program ast, String fileName) throws IOException {
    try (FileWriter writerXML = new FileWriter(fileName)) {
        writerXML.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        CreadorXML xmlCreator = new CreadorXML(writerXML);
        ast.visit(xmlCreator, null);
    } catch (IOException e) {
        System.err.println("Error al crear el archivo XML: " + e.getMessage());
        throw e;
    }
}
}
