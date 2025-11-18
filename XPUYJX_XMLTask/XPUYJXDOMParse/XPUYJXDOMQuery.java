package XPUYJXDOMParse;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XPUYJXDOMQuery {
    public static void main(String[] argv) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("XPUYJX_XML.xml"); // vagy teljes út: d:\XPUYJXWebXML\beadando\XPUYJX_XML.xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());

        // Dealerek
        NodeList dealers = doc.getElementsByTagName("PorscheDealer");
        System.out.println("\nDealerek:");
        for (int i = 0; i < dealers.getLength(); i++) {
            Element pd = (Element) dealers.item(i);
            System.out.println("  [dealerID=" + pd.getAttribute("dealerID") + "] Manager: " + textOf(pd, "managerName")
                    + " - Employees: " + textOf(pd, "numOfEmployees") + " - Rating: " + textOf(pd, "rating"));
        }

        // Ügyfelek
        NodeList customers = doc.getElementsByTagName("Customer");
        System.out.println("\nÜgyfelek:");
        for (int i = 0; i < customers.getLength(); i++) {
            Element c = (Element) customers.item(i);
            System.out.println("  [Cid=" + c.getAttribute("Cid") + ", P-C=" + c.getAttribute("P-C") + "] " +
                    textOf(c, "name") + " - " + textOf(c, "phone") + " - " + textOf(c, "email") +
                    " (born: " + textOf(c, "birthDate") + ")");
            Element addr = firstElement(c, "address");
            if (addr != null) {
                System.out.println("    Cím: " + textOf(addr, "city") + ", " + textOf(addr, "street") + " " + textOf(addr, "houseNumber"));
            }
        }

        // CustomerRel
        NodeList crels = doc.getElementsByTagName("CustomerRel");
        System.out.println("\nCustomerRel:");
        for (int i = 0; i < crels.getLength(); i++) {
            Element cr = (Element) crels.item(i);
            System.out.println("  [purchaseID=" + cr.getAttribute("purchaseID") + ", D-C=" + cr.getAttribute("D-C") + "] " +
                    "Loyalty: " + textOf(cr, "loyaltyPoints") + " - Prefered: " + textOf(cr, "preferedTechnician"));
        }

        // Járművek
        NodeList vehicles = doc.getElementsByTagName("Vehicle");
        System.out.println("\nJárművek:");
        for (int i = 0; i < vehicles.getLength(); i++) {
            Element v = (Element) vehicles.item(i);
            System.out.println("  [VIN=" + v.getAttribute("VIN") + ", V-P=" + v.getAttribute("V-P") + "] " +
                    textOf(v, "model") + " (" + textOf(v, "year") + ") - " + textOf(v, "color") +
                    ", mileage: " + textOf(v, "mileage") + ", purchased: " + textOf(v, "purchaseDate"));
        }

        // Alkatrészek
        NodeList parts = doc.getElementsByTagName("Part");
        System.out.println("\nAlkatrészek:");
        for (int i = 0; i < parts.getLength(); i++) {
            Element p = (Element) parts.item(i);
            System.out.println("  [Pid=" + p.getAttribute("Pid") + ", P-P=" + p.getAttribute("P-P") + "] " +
                    textOf(p, "name") + " - " + textOf(p, "manufacturer"));
            Element price = firstElement(p, "listPrice");
            if (price != null) {
                System.out.println("    Ár: " + price.getTextContent().trim() + " " + price.getAttribute("currency"));
            }
            Element compat = firstElement(p, "compatibleModels");
            if (compat != null) {
                NodeList mods = compat.getElementsByTagName("model");
                System.out.print("    Kompatibilis modellek: ");
                for (int j = 0; j < mods.getLength(); j++) {
                    System.out.print(mods.item(j).getTextContent().trim());
                    if (j < mods.getLength() - 1) System.out.print(", ");
                }
                System.out.println();
            }
        }

        // Technikusok
        NodeList techs = doc.getElementsByTagName("Technician");
        System.out.println("\nTechnikusok:");
        for (int i = 0; i < techs.getLength(); i++) {
            Element t = (Element) techs.item(i);
            System.out.print("  [Tid=" + t.getAttribute("Tid") + ", t-P=" + t.getAttribute("t-P") + "] " +
                    textOf(t, "name") + " - hired: " + textOf(t, "hireDate") + " - phone: " + textOf(t, "phone"));
            NodeList certs = t.getElementsByTagName("certifications");
            if (certs.getLength() > 0) {
                System.out.print(" - certs: ");
                for (int j = 0; j < certs.getLength(); j++) {
                    System.out.print(certs.item(j).getTextContent().trim());
                    if (j < certs.getLength() - 1) System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    private static String textOf(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0 && nl.item(0) != null) return nl.item(0).getTextContent().trim();
        return "";
    }

    private static Element firstElement(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0 && nl.item(0) instanceof Element) return (Element) nl.item(0);
        return null;
    }
}
