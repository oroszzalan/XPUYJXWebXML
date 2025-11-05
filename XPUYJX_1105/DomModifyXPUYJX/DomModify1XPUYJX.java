import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class DomModify1XPUYJX {


    public static void main(String argv[]) {

        try {
            File inputFile = new File("XPUYJX_orarend.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Node ora = doc.getFirstChild();

            Node orak = doc.getElementsByTagName("ora").item(1);


            
            NodeList list = orak.getChildNodes();

            Element ujOraado = doc.createElement("oraado");
            ujOraado.setTextContent("Dr. Kovács Béla");
            ora.appendChild(ujOraado);

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);


                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                        

                    if ("tipus".equals(eElement.getNodeName())) {
                        if ("gyakorlat".equals(eElement.getTextContent())) {
                            eElement.setTextContent("eloadas");
                        }
                    }
                }
            }
            // Tartalom konzolra írása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //Ez DOMSource tartalmazza a DOM fát. Egy bemeneti forrás létrehozása egy DOM csomóponttal.
            DOMSource source = new DOMSource(doc);

            System.out.println("--Módosított fájl---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


