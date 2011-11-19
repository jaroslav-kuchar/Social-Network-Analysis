package cz.cvut.fit.gephi.snametrics.clusteringcoefficient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.jdesktop.swingx.JXHeader;

/**
 *
 * @author Jaroslav Kuchar
 */
public class ClusteringMetricPanel extends JPanel implements ActionListener {

    private boolean directed = false;
    private ButtonGroup bg;
    private JRadioButton chbDirected;
    private JRadioButton chbUndirected;
    private JXHeader header;

    public ClusteringMetricPanel() {
        this.setLayout(new BorderLayout());
        header = new JXHeader();
        header.setTitle("Clustering Coefficient");
        header.setDescription("Computes Clustering coefficient of nodes.");
        this.add(header, BorderLayout.NORTH);

        chbDirected = new JRadioButton("Directed");
        chbUndirected = new JRadioButton("UnDirected");

        chbDirected.setSelected(directed);
        chbUndirected.setSelected(!directed);

        chbDirected.addActionListener(this);
        chbUndirected.addActionListener(this);

        bg = new ButtonGroup();
        bg.add(chbDirected);
        bg.add(chbUndirected);

        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(chbUndirected);
        radioPanel.add(chbDirected);
        radioPanel.setPreferredSize(new Dimension(200, 90));

        this.add(radioPanel, BorderLayout.CENTER);

    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.directed = chbDirected.isSelected();
    }
}
