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
    // These are all simpler versions of the below methods.
    // Not that efficient, but easy to use.
    public static List<Scene> buildScenes() {
        return Parser.buildScenes(getDocFromFile("cards.xml"));
    }
    
    public static List<ActingSet> buildActingSets() {
        return Parser.buildActingSets(getDocFromFile("board.xml"));
    }
    
    public static NonActingSet buildTrailer() {
        return Parser.buildTrailer(getDocFromFile("board.xml"));
    }
    
    public static CastingOffice buildCastingOffice() {
        return Parser.buildCastingOffice(getDocFromFile("board.xml"));
    }

    // Guessing what functions will be needed
    public static void main(String[] args) {
        // just testing around
        Parser parser = new Parser();
        
        Document doc = getDocFromFile("cards.xml");
        System.out.println(parser.buildScenes().size());

        doc = getDocFromFile("board.xml");
        System.out.println(parser.buildActingSets().size());
        parser.buildTrailer();
        parser.buildCastingOffice();
        
        parser.buildScenes();
        parser.buildActingSets();
        parser.buildTrailer();
        CastingOffice office = parser.buildCastingOffice();
        System.out.println(office.getNeighbors());
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
    ///NEED A HAND PARSING TO GET THE TITLE
    private static HashMap<String, Role> buildStarRoles(Node card) {
        NodeList part_nodes = card.getChildNodes();
        HashMap<String, Role> star_roles = new HashMap<String, Role>();

        for (int i = 0; i < part_nodes.getLength(); i++) {
            Node part = part_nodes.item(i);

            // verify that part is actually a part
            if (part.getNodeName() == "part") {
                String title = getAttribute(part, "name");
                int rank = Integer.valueOf(getAttribute(part, "level"));
                boolean is_star = true;
                Role addRole = new Role(title, rank, is_star);
                star_roles.put(title, addRole);
            }
        }

        return star_roles;
    }

    private static List<Role> buildExtrasRoles(Node card) {
        List<Node> part_nodes = getSubNodes(getSubNodes(card, "parts").get(0), "part");
        ArrayList<Role> star_roles = new ArrayList<Role>();
        
        for (int i = 0; i < part_nodes.size(); i++) {
            Node part = part_nodes.get(i);

            // verify that part is actually a part
            if (part.getNodeName().equals("part")) {
                String title = getAttribute(part, "name");
                int rank = Integer.valueOf(getAttribute(part, "level"));
                boolean is_star = false;
                Role addRole = new Role(title, rank, is_star);
                star_roles.add(addRole);
            }
        }

        return star_roles;
    }

    // get attribute value of given node as string 
    // (nums can be converted via Integer.valueOf(getAttributes(...)))
    private static String getAttribute(Node node, String attribute) {
        return node.getAttributes().getNamedItem(attribute).getNodeValue();
    }

    // build acting sets from doc (must be board.xml)
    public static List<ActingSet> buildActingSets(Document doc) {
        NodeList set_nodes = doc.getDocumentElement().getElementsByTagName("set");
        List<ActingSet> acting_sets_list = new ArrayList<ActingSet>();

        for (int i = 0; i < set_nodes.getLength(); i++) {
            Node set = set_nodes.item(i);
            ActingSet acting_set_instance = new ActingSet(getAttribute(set, "name"), getShotTokens(set));
            
            List<String> neighbors = getNeighbors(set);

            for (int j = 0; j < neighbors.size(); j++) {
                acting_set_instance.addNeighbors(neighbors.get(j));
            }

            List<Role> extras_roles = buildExtrasRoles(set);

            for (int j = 0; j < extras_roles.size(); j++) {
                acting_set_instance.addExtraRoles(extras_roles.get(j));
            }

            acting_sets_list.add(acting_set_instance);
        }

        return acting_sets_list;
    }

    // get num shot tokens from set node
    private static int getShotTokens(Node set) {
        int shot_tokens_num = 0;
        NodeList children = set.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node takes_node = children.item(i);

            // verify that takes_node is actually the takes node
            if (takes_node.getNodeName() == "takes") {
                NodeList takes_children = takes_node.getChildNodes();

                for (int j = 0; j < takes_children.getLength(); j++) {
                    Node take_node = takes_children.item(j);
                    if (take_node.getNodeName() == "take") {
                        shot_tokens_num += 1;
                    }
                }
            }
        }

        return shot_tokens_num;
    }

    private static List<String> getNeighbors(Node set) {
        List<String> neighbors = new ArrayList<String>();
        NodeList children = set.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node neighbors_node = children.item(i);

            // verify neighbors_node is actually the neighbors node
            if (neighbors_node.getNodeName() == "neighbors") {
                NodeList neighbor_nodes = neighbors_node.getChildNodes();

                for (int j = 0; j < neighbor_nodes.getLength(); j++) {
                    Node neighbor_node = neighbor_nodes.item(j);

                    // verify neighbor_node is actually a neighbor node
                    if (neighbor_node.getNodeName() == "neighbor") {
                        neighbors.add(getAttribute(neighbor_node, "name"));
                    }
                }
            }
        }

        return neighbors;
    }

    // build trailer from given document (board.xml) and return it
    public static NonActingSet buildTrailer(Document doc) {
        NonActingSet trailer = new NonActingSet("trailer");
        Node trailer_node = doc.getDocumentElement().getElementsByTagName("trailer").item(0);

        List<String> neighbors = getNeighbors(trailer_node);

        for (int j = 0; j < neighbors.size(); j++) {
            trailer.addNeighbors(neighbors.get(j));
        }

        return trailer;
    }

    // build casting office from given document (board.xml) and return it
    public static CastingOffice buildCastingOffice(Document doc) {
        CastingOffice casting_office = new CastingOffice("office");
        Node office_node = doc.getDocumentElement().getElementsByTagName("office").item(0);

        int[] dollar_costs = new int[7];
        int[] credit_costs = new int[7];

        List<Node> costs = getSubNodes(getSubNodes(office_node, "upgrades").get(0), "upgrade");
        for (int i = 0; i < costs.size(); i++) {
            Node cost = costs.get(i);

            if (getAttribute(cost, "currency").equals("dollar")) {
                int level = Integer.valueOf(getAttribute(cost, "level"));
                dollar_costs[level] = Integer.valueOf(getAttribute(cost, "amt"));
            }

            if (getAttribute(cost, "currency").equals("credit")) {
                int level = Integer.valueOf(getAttribute(cost, "level"));
                credit_costs[level] = Integer.valueOf(getAttribute(cost, "amt"));
            }
        }

        casting_office.setDolCost(dollar_costs);
        casting_office.setCredCost(credit_costs);

        List<String> neighbors = getNeighbors(office_node);

        for (int j = 0; j < neighbors.size(); j++) {
            casting_office.addNeighbors(neighbors.get(j));
        }

        return casting_office;
    }

    // given a node, return list of all subnodes with given tag name
    private static List<Node> getSubNodes(Node node, String name) {
        List<Node> sub_nodes = new ArrayList<Node>();
        NodeList children = node.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child.getNodeName() == name) {
                sub_nodes.add(child);
            }
        }

        return sub_nodes;
    }
}