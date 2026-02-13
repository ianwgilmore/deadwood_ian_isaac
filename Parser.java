//going to need to parse xml in a way, unsure of what that is going to look like as of now

// Gotten from slides at https://wwu.instructure.com/courses/1854078/files/folder/Reading%20Material/Other%20Topics?preview=145300650
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Parser {
    // Guessing what functions will be needed
    public static void main(String[] args) {
        Document doc = getDocFromFile("cards.xml");
        printDoc(doc);
    }
    
    // Again from slides link above
    public static Document getDocFromFile(String filename) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc = null;

        try{
            db = dbf.newDocumentBuilder();
            doc = db.parse(filename);
        } catch (Exception ex){
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        
        return doc;
    } // exception handling
    
    public static void printCardsDoc(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        System.out.println("-LIST OF CARD NAMES-");

        for (int i = 0; i < cards.getLength(); i++) {
            Node card = cards.item(i);
            String name = card.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println(name);
        }
    }
}