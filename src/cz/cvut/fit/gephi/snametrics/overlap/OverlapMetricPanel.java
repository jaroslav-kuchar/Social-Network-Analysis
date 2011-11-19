package cz.cvut.fit.gephi.snametrics.overlap;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXHeader;

/**
 *
 * @author Jaroslav Kuchar
 */
public class OverlapMetricPanel extends JPanel {
    private JXHeader header;

    public OverlapMetricPanel() {
        this.setLayout(new BorderLayout());
        header = new JXHeader();
        header.setTitle("Neighborhood Overlap and Embeddedness");
        header.setDescription("Computes Neighborhood Overlap and Embeddedness of edges.");
        this.add(header,BorderLayout.NORTH);
    }
    
}
