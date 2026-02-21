// Gotten from slides at https://wwu.instructure.com/courses/1854078/files/folder/Reading%20Material/Other%20Topics?preview=145300650
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

// functional cohesion
// Note: add card and part names to scene and role
// since in the .xml file their names (plus role dialogue)
// go unused as of now.
public class Parser {
    // Guessing what functions will be needed
    public static void main(String[] args) {
        Document doc = getDocFromFile("cards.xml");
        buildScenes(doc).size();

        doc = getDocFromFile(".xml");
    }
    
    // Given a .xml path, will return a doc object with .xml data inside.
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

    // Builds a list of scenes based on document given (Must be cards.xml)
    // and returns them.
    public static List<Scene> buildScenes(Document doc) {
        NodeList card_nodes = doc.getDocumentElement().getElementsByTagName("card");
        List<Scene> scenes_list = new ArrayList<Scene>();

        for (int i = 0; i < card_nodes.getLength(); i++) {
            Node card = card_nodes.item(i);
            scenes_list.add(new Scene(getBudget(card), buildStarRoles(card)));
        }

        return scenes_list;
    }

    // get budget of a card node
    private static int getBudget(Node card) {
        return Integer.valueOf(card.getAttributes().getNamedItem("budget").getNodeValue());
    }

    // build star roles of a card node
    private static List<Role> buildStarRoles(Node card) {
        NodeList part_nodes = card.getChildNodes();
        List<Role> star_roles = new ArrayList<Role>();

        for (int i = 0; i < part_nodes.getLength(); i++) {
            Node part = part_nodes.item(i);

            // verify that part is actually a part
            if (part.getNodeName() == "part") {
                int rank = Integer.valueOf(getAttribute(part, "level"));
                star_roles.add(new Role(rank, true));
            }
        }

        return star_roles;
    }

    // get attribute value of given node
    private static String getAttribute(Node node, String attribute) {
        return node.getAttributes().getNamedItem(attribute).getNodeValue();
    }
}