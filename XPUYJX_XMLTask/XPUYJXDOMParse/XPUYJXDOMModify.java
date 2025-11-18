package XPUYJXDOMParse;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class XPUYJXDOMModify {
    public static void main(String[] args) {
        try {
            File inputFile = new File("d:\\XPUYJXWebXML\\beadando\\XPUYJXDOMParse\\XPUYJX_XML.xml"); // vagy teljes út: 

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());

            // 1) PorscheDealer: növeljük a numOfEmployees értékét +5-re
            NodeList dealers = doc.getElementsByTagName("PorscheDealer");
            if (dealers.getLength() > 0) {
                Element dealer = (Element) dealers.item(0);
                Element numElem = firstElement(dealer, "numOfEmployees");
                if (numElem != null) {
                    try {
                        int num = Integer.parseInt(numElem.getTextContent().trim());
                        num += 5;
                        numElem.setTextContent(String.valueOf(num));
                        System.out.println("PorscheDealer numOfEmployees módosítva: " + num);
                    } catch (NumberFormatException ignore) {}
                }
            }

            // 2) Customer C_1: telefonszám módosítása
            Element custC1 = findElementByAttribute(doc.getElementsByTagName("Customer"), "Cid", "C_1");
            if (custC1 != null) {
                Element phone = firstElement(custC1, "phone");
                if (phone != null) {
                    phone.setTextContent("+36-20-999-9999");
                    System.out.println("Customer C_1 phone módosítva: " + phone.getTextContent());
                }
            }

            // 3) CustomerRel PU_4: loyaltyPoints +50
            Element crPU4 = findElementByAttribute(doc.getElementsByTagName("CustomerRel"), "purchaseID", "PU_4");
            if (crPU4 != null) {
                Element lp = firstElement(crPU4, "loyaltyPoints");
                if (lp != null) {
                    try {
                        int val = Integer.parseInt(lp.getTextContent().trim());
                        val += 50;
                        lp.setTextContent(String.valueOf(val));
                        System.out.println("CustomerRel PU_4 loyaltyPoints módosítva: " + val);
                    } catch (NumberFormatException ignore) {}
                }
            }

            // 4) Vehicle WP0ZZZ99ZTS392129: mileage növelése +2000
            Element veh = findElementByAttribute(doc.getElementsByTagName("Vehicle"), "VIN", "WP0ZZZ99ZTS392129");
            if (veh != null) {
                Element mileage = firstElement(veh, "mileage");
                if (mileage != null) {
                    try {
                        int m = Integer.parseInt(mileage.getTextContent().trim());
                        m += 2000;
                        mileage.setTextContent(String.valueOf(m));
                        System.out.println("Vehicle VIN ...2129 mileage módosítva: " + m);
                    } catch (NumberFormatException ignore) {}
                }
            }

            // 5) Part P_004: listPrice -10%
            Element part004 = findElementByAttribute(doc.getElementsByTagName("Part"), "Pid", "P_004");
            if (part004 != null) {
                Element price = firstElement(part004, "listPrice");
                if (price != null) {
                    try {
                        double p = Double.parseDouble(price.getTextContent().trim());
                        p = Math.round(p * 0.9 * 100.0) / 100.0;
                        price.setTextContent(String.format("%.2f", p));
                        System.out.println("Part P_004 listPrice módosítva: " + price.getTextContent() + " " + price.getAttribute("currency"));
                    } catch (NumberFormatException ignore) {}
                }
            }

            // 6) Technician T_5: új certifications elem hozzáadása
            Element techT5 = findElementByAttribute(doc.getElementsByTagName("Technician"), "Tid", "T_5");
            if (techT5 != null) {
                Element newCert = doc.createElement("certifications");
                newCert.setTextContent("Battery Systems");
                techT5.appendChild(newCert);
                System.out.println("Technician T_5 új certification hozzáadva.");
            }

            // Output és mentés
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            System.out.println("\n--- MÓDOSÍTOTT XML ---");
            transformer.transform(source, new StreamResult(System.out));

            File outFile = new File("XPUYJX_XML_modified.xml");
            transformer.transform(source, new StreamResult(outFile));
            System.out.println("\nMentve: " + outFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element firstElement(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0 && nl.item(0) instanceof Element) return (Element) nl.item(0);
        return null;
    }

    private static Element findElementByAttribute(NodeList list, String attrName, String attrValue) {
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n instanceof Element) {
                Element e = (Element) n;
                if (attrValue.equals(e.getAttribute(attrName))) return e;
            }
        }
        return null;
    }
}