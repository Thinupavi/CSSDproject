package HomeMainScreen;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A waypoint that is represented by a button on the map.
 *
 * @author Daniel Stahr
 */
public class SwingWaypoint extends DefaultWaypoint {
    private final JButton button;
    private final String text;
    private final GeoPosition coord;
    private final int activesensonsor;

    public SwingWaypoint(String text, GeoPosition coord,int activesensonsor) throws IOException {
        super(coord);
        this.text = text;
        this.coord = coord;
        this.activesensonsor = activesensonsor;
        button = new JButton();
        Image img = ImageIO.read(getClass().getResource("../marker.png"));
        button.setIcon(new ImageIcon(img));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addMouseListener(new SwingWaypointMouseListener());
        button.setVisible(true);
        
    }

    JButton getButton() {
        return button;
    }
    

    private class SwingWaypointMouseListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(button, "Station Name : " + text+"\n Latitude : "+coord.getLatitude()+" \n Longitude : "+coord.getLongitude()+"\n Active Sensors :"+activesensonsor,"Station Details",JOptionPane.PLAIN_MESSAGE);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
}
