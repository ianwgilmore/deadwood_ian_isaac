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

// Note: add card and part names to scene and role
// since in the .xml file their names (plus role dialogue)
// go unused as of now.
public class Parser {
    // Guessing what functions will be needed
    public static void main(String[] args) {
        Document doc = getDocFromFile("cards.xml");
        
        // Test: prints out all card budgets,
        // of which there are 40.
        List<Scene> scenes = getCardsFromDoc(doc);
        for (int i = 0; i < scenes.size(); i++) {
            System.out.println(scenes.get(i).budget);
        }
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
    
    // Prints scenes and their roles from card.xml imported doc
    public static void printCardsDoc(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        System.out.println("-LIST OF CARD NAMES-");

        for (int i = 0; i < cards.getLength(); i++) {
            Node card = cards.item(i);
            String card_name = card.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("    " + card_name);
            NodeList children = card.getChildNodes();

            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);
                if (sub.getNodeName() == "part") {
                    String part_name = sub.getAttributes().getNamedItem("name").getNodeValue();
                    System.out.println("        Part: " + part_name);
                }
            }
        }
    }

    // Given a doc object (must be created from card.xml),
    // this method returns all scenes from doc.
    public static List<Scene> getCardsFromDoc(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");
        
        List<Scene> cards_list = new ArrayList<Scene>();

        for (int i = 0; i < cards.getLength(); i++) { // for card
            Node card = cards.item(i);
            String card_name = card.getAttributes().getNamedItem("name").getNodeValue();
            NodeList children = card.getChildNodes();

            int budget = Integer.valueOf(card.getAttributes().getNamedItem("budget").getNodeValue());
            ArrayList<Role> star_roles = new ArrayList<Role>();

            for (int j = 0; j < children.getLength(); j++) { // for role
                Node sub = children.item(j);

                if (sub.getNodeName() == "part") {
                    int rank = Integer.valueOf(sub.getAttributes().getNamedItem("level").getNodeValue());
                    star_roles.add(new Role(rank, true));
                }

                NodeList sub_children = sub.getChildNodes();
            }

            cards_list.add(new Scene(budget, star_roles));
        }

        return cards_list;
    }

    // Board.xml parsing is separated because each subtree is
    // formatted differently in the board.xml file
    public static List<ActingSet> getActingSetsFromDoc(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList board = root.getElementsByTagName("board");
        
        List<ActingSet> acting_sets_list = new ArrayList<ActingSet>();

        for (int i = 0; i < board.getLength(); i++) { // for card
            Node acting_set = board.item(i);
            String acting_set_name = acting_set.getAttributes().getNamedItem("name").getNodeValue();
            NodeList children = acting_set.getChildNodes();

            int num_takes = 0;
            ArrayList<Role> extras_roles = new ArrayList<Role>();


            for (int j = 0; j < children.getLength(); j++) { // for role
                Node sub = children.item(j);

                if (sub.getNodeName() == "part") {
                    int rank = Integer.valueOf(sub.getAttributes().getNamedItem("level").getNodeValue());
                    extras_roles.add(new Role(rank, false));
                } else if (sub.getNodeName() == "takes") {
                    NodeList sub_children = sub.getChildNodes();
                    num_takes = sub_children.getLength();
                }
            }

            ActingSet acting_set_instance = new ActingSet(acting_set_name, num_takes);

            for (int j = 0; j < extras_roles.size(); j++) {
                acting_set_instance.addExtraRoles(extras_roles.get(j));
            }

            acting_sets_list.add(acting_set_instance);
        }

        return acting_sets_list;
    }

    //public static Set getTrailerFromDoc(Document doc) {}
    //public static Set getOfficeFromDoc(Document doc) {}
}