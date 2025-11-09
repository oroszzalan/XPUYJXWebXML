import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomReadXPUYJX1 {
    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

        // Ellenőrzés: pontos elérési út
        File xmlFile = new File("D:\\XPUYJXWebXML\\XPUYJX_1029\\DOMModifyXPUYJX\\XPUYJX_XML.xml");

        if (!xmlFile.exists()) {
            System.out.println("❌ Nem található az XML fájl: " + xmlFile.getAbsolutePath());
            return;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document XPUYJX = dBuilder.parse(xmlFile);
        XPUYJX.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + XPUYJX.getDocumentElement().getNodeName());

        NodeList nList = XPUYJX.getElementsByTagName("etterem");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            System.out.println("\nAktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String ekod = elem.getAttribute("E1"); // attribútum neve az XML alapján
                String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                String varos = elem.getElementsByTagName("város").item(0).getTextContent();
                String utca = elem.getElementsByTagName("utca").item(0).getTextContent();
                String hazszam = elem.getElementsByTagName("házszám").item(0).getTextContent();
                String csillag = elem.getElementsByTagName("csillag").item(0).getTextContent();

                System.out.println("Étterem kódja: " + ekod);
                System.out.println("Név: " + nev);
                System.out.println("Cím: " + varos + ", " + utca + " " + hazszam);
                System.out.println("Csillag: " + csillag);
            }
        }
    }
}
