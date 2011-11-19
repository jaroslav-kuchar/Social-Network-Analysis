package cz.cvut.fit.gephi.snametrics.erdosnumber;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.gephi.graph.api.Node;

/**
 *
 * @author Jaroslav Kuchar
 */
public class NodeListRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderedLabel = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Node n = (Node) value;
        // renderedLabel.setText(n.getNodeData().getId() + " - " + n.getNodeData().getLabel());
        renderedLabel.setText(n.getNodeData().getLabel() + " (" + n.getNodeData().getId() + ")");
        return renderedLabel;
    }
}
