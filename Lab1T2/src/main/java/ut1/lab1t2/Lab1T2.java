package ut1.lab1t2;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;




public class Lab1T2 {
    public static void main(String[] args){
        try{
            //DOOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            Document doc = builder.parse(new File("imperio.xml"));
            
            doc.getDocumentElement().normalize();
            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());
            
            NodeList listaImp = doc.getElementsByTagName("nave");
            
            for (int i = 0; i < listaImp.getLength(); i++) {
                Node nodo = listaImp.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String id = elemento.getAttribute("id");
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    String piloto = elemento.getElementsByTagName("piloto").item(0).getTextContent();
                    System.out.println("ID: " + id + " | Nombre: " + nombre + " | Piloto: " + piloto);
                }
            }
            //SAXX
            SAXParserFactory saxfactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxfactory.newSAXParser();
            
            DefaultHandler manejador = new DefaultHandler() {
                private boolean esNombre = false;

            @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes){
                    if (qName.equalsIgnoreCase("nave")) {
                        String clase = attributes.getValue("clase");
                        System.out.print("Clase de la nave: " + clase);
                    } else if (qName.equalsIgnoreCase("nombre")) {
                        esNombre = true;
                    }
                }
                
            @Override
                public void characters(char[] ch, int start, int length){
                    if (esNombre) {
                        String texto = new String(ch, start, length).trim();
                        if (!texto.isEmpty()) {
                        System.out.print(" | Nombre: " + texto);
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("nombre")) {
                    esNombre = false;
                }
                }
            };

            saxParser.parse(new File("imperio.xml"), manejador);
            
            //Binding
            Personaje personaje = new Personaje(
                    "Luke Skywalker",
                    "Comandante / Caballero Jedi"
            );

            // Crear el contexto JAXB para la clase Personaje
            JAXBContext contexto = JAXBContext.newInstance(Personaje.class);

            // Crear el Marshaller
            Marshaller marshaller = contexto.createMarshaller();

            // Formatear el XML para que sea legible
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            FileOutputStream salida = new FileOutputStream("rebelde.xml");

            marshaller.marshal(personaje, salida);

            salida.close();
            System.out.println("Archivo rebelde.xml creado correctamente.");
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}
