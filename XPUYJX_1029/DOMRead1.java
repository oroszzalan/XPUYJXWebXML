import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMRead1 {

    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

        // XML fájl megnyitása
        File xmlFile = new File("XPUYJX_orarend.xml");

        // DocumentBuilderFactory létrehozása
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        // DOM fa felépítése
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
        System.out.println("-----------------------------------");

        // Összes "ora" elem lekérése
        NodeList nList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            System.out.println("\nAktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getAttribute("id");
                String tipus = elem.getAttribute("tipus");
                String targy = elem.getElementsByTagName("targy").item(0).getTextContent();
                String nap = elem.getElementsByTagName("nap").item(0).getTextContent();
                String tol = elem.getElementsByTagName("tol").item(0).getTextContent();
                String ig = elem.getElementsByTagName("ig").item(0).getTextContent();
                String helyszin = elem.getElementsByTagName("helyszin").item(0).getTextContent();
                String szak = elem.getElementsByTagName("szak").item(0).getTextContent();

                // Oktatók többes kezelése
                NodeList oktatok = elem.getElementsByTagName("oktato");
                StringBuilder oktatoLista = new StringBuilder();
                for (int j = 0; j < oktatok.getLength(); j++) {
                    oktatoLista.append(oktatok.item(j).getTextContent());
                    if (j < oktatok.getLength() - 1) oktatoLista.append(", ");
                }

                // Kiírás
                System.out.println("ID: " + id);
                System.out.println("Típus: " + tipus);
                System.out.println("Tárgy: " + targy);
                System.out.println("Nap: " + nap);
                System.out.println("Idő: " + tol + " - " + ig);
                System.out.println("Helyszín: " + helyszin);
                System.out.println("Oktató(k): " + oktatoLista);
                System.out.println("Szak: " + szak);
            }
        }
    }
}
