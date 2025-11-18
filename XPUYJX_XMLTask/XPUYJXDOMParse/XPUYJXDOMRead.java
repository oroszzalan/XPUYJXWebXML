package XPUYJXDOMParse;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XPUYJXDOMRead {
    public static void main(String[] argv) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("XPUYJX_XML.xml"); // vagy teljes elérési út: d:\XPUYJXWebXML\beadando\XPUYJX_XML.xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());

        // PorscheDealer
        NodeList dealers = doc.getElementsByTagName("PorscheDealer");
        System.out.println("\nPorscheDealerek:");
        for (int i = 0; i < dealers.getLength(); i++) {
            Element pd = (Element) dealers.item(i);
            System.out.println("Dealer ID: " + pd.getAttribute("dealerID"));
            System.out.println("  Manager: " + textOf(pd, "managerName"));
            System.out.println("  Employees: " + textOf(pd, "numOfEmployees"));
            System.out.println("  Rating: " + textOf(pd, "rating"));
        }

        // Customers
        NodeList customers = doc.getElementsByTagName("Customer");
        System.out.println("\nÜgyfelek:");
        for (int i = 0; i < customers.getLength(); i++) {
            Element c = (Element) customers.item(i);
            System.out.println("Customer ID: " + c.getAttribute("Cid") + "  P-C: " + c.getAttribute("P-C"));
            System.out.println("  Név: " + textOf(c, "name"));
            System.out.println("  Telefon: " + textOf(c, "phone"));
            System.out.println("  Email: " + textOf(c, "email"));
            System.out.println("  Születési dátum: " + textOf(c, "birthDate"));
            Element addr = firstElement(c, "address");
            if (addr != null) {
                System.out.println("  Cím: " + textOf(addr, "city") + ", " + textOf(addr, "street") + " " + textOf(addr, "houseNumber"));
            }
        }

        // CustomerRel
        NodeList crels = doc.getElementsByTagName("CustomerRel");
        System.out.println("\nCustomerRel:");
        for (int i = 0; i < crels.getLength(); i++) {
            Element cr = (Element) crels.item(i);
            System.out.println("PurchaseID: " + cr.getAttribute("purchaseID") + "  D-C: " + cr.getAttribute("D-C"));
            System.out.println("  LoyaltyPoints: " + textOf(cr, "loyaltyPoints"));
            System.out.println("  PreferedTechnician: " + textOf(cr, "preferedTechnician"));
        }

        // Vehicles
        NodeList vehicles = doc.getElementsByTagName("Vehicle");
        System.out.println("\nJárművek:");
        for (int i = 0; i < vehicles.getLength(); i++) {
            Element v = (Element) vehicles.item(i);
            System.out.println("VIN: " + v.getAttribute("VIN") + "  V-P: " + v.getAttribute("V-P"));
            System.out.println("  Model: " + textOf(v, "model"));
            System.out.println("  Year: " + textOf(v, "year"));
            System.out.println("  Color: " + textOf(v, "color"));
            System.out.println("  Mileage: " + textOf(v, "mileage"));
            System.out.println("  PurchaseDate: " + textOf(v, "purchaseDate"));
        }

        // Parts
        NodeList parts = doc.getElementsByTagName("Part");
        System.out.println("\nAlkatrészek:");
        for (int i = 0; i < parts.getLength(); i++) {
            Element p = (Element) parts.item(i);
            System.out.println("Part ID: " + p.getAttribute("Pid") + "  P-P: " + p.getAttribute("P-P"));
            System.out.println("  Name: " + textOf(p, "name"));
            System.out.println("  Manufacturer: " + textOf(p, "manufacturer"));
            Element price = firstElement(p, "listPrice");
            if (price != null) {
                System.out.println("  ListPrice: " + price.getTextContent().trim() + " " + price.getAttribute("currency"));
            }
            Element compat = firstElement(p, "compatibleModels");
            if (compat != null) {
                NodeList mods = compat.getElementsByTagName("model");
                System.out.print("  CompatibleModels: ");
                for (int j = 0; j < mods.getLength(); j++) {
                    System.out.print(mods.item(j).getTextContent().trim());
                    if (j < mods.getLength() - 1) System.out.print(", ");
                }
                System.out.println();
            }
        }

        // Technicians
        NodeList techs = doc.getElementsByTagName("Technician");
        System.out.println("\nTechnikusok:");
        for (int i = 0; i < techs.getLength(); i++) {
            Element t = (Element) techs.item(i);
            System.out.println("Technician ID: " + t.getAttribute("Tid") + "  t-P: " + t.getAttribute("t-P"));
            System.out.println("  Név: " + textOf(t, "name"));
            System.out.println("  HireDate: " + textOf(t, "hireDate"));
            NodeList certs = t.getElementsByTagName("certifications");
            System.out.print("  Certifications: ");
            for (int j = 0; j < certs.getLength(); j++) {
                System.out.print(certs.item(j).getTextContent().trim());
                if (j < certs.getLength() - 1) System.out.print(", ");
            }
            System.out.println();
            System.out.println("  Phone: " + textOf(t, "phone"));
        }
    }

    private static String textOf(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0 && nl.item(0) != null) {
            return nl.item(0).getTextContent().trim();
        }
        return "";
    }

    private static Element firstElement(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0 && nl.item(0) instanceof Element) {
            return (Element) nl.item(0);
        }
        return null;
    }
}