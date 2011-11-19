package cz.cvut.fit.gephi.snametrics.erdosnumber;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.jdesktop.swingx.JXHeader;
import org.openide.util.Lookup;

/**
 *
 * @author Jaroslav Kuchar
 */
public class ErdosNumberMetricPanel extends JPanel {

    private List<Node> listNode;
    private JList list;
    private JXHeader header;

    public ErdosNumberMetricPanel() {
        this.setLayout(new BorderLayout());
        header = new JXHeader();
        header.setTitle("Erdös Number");
        header.setDescription("Select node, which will be the source node. (e.g. Paul Erdös)");
        this.add(header,BorderLayout.NORTH);


        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        GraphModel model = graphController.getModel();
        Graph graph = model.getGraph();
        listNode = Arrays.asList(graph.getNodes().toArray());
        Collections.sort(listNode, new NodeByLabelComparator());
        list = new JList(listNode.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);
        list.setCellRenderer(new NodeListRenderer());
        JScrollPane listScroller = new JScrollPane(list);        
        this.add(listScroller);
    }

    public void setSelected(int index) {
        list.setSelectedIndex(index);
    }

    public Node getSelected() {
        return (Node) list.getSelectedValue();
    }
}
