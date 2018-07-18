/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeMainScreen;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import librarysystem.SensorStationGUI;
import sensor.SensorStations;
import sensor.SetOfSensorStations;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
/**
 *
 * @author ASUS
 */
public class HomeScreen extends javax.swing.JFrame {

    private JLabel mapholder;

    /**
     * Home screen Frame
     * @throws java.io.IOException
     */
    public HomeScreen() throws IOException {
        initComponents();
    }
    
    public List<SwingWaypoint> getMarkers() throws IOException{
        SensorStationGUI stationdata = new SensorStationGUI();
        SetOfSensorStations stations = stationdata.senddata();
        Vector datafromfile = stations;
        List<SensorStations> list =(List<SensorStations>) datafromfile.stream().collect(Collectors.toList());
        List<SwingWaypoint> listo = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            listo.add(new SwingWaypoint(list.get(i).getDestination(), new GeoPosition(list.get(i).getLongitude(),list.get(i).getLattitude()),list.get(i).getNoofActiveSensors()));
        }
        
        //error catching if no stations
        if(listo.isEmpty()){
            System.out.println("The Serialization File was empty, adding sample sensor station");
            listo.add(new SwingWaypoint("Sample Sensor station", new GeoPosition(6.927079,79.861244),0));
        }
        //end error catching
        return listo;
    }

    @SuppressWarnings("unchecked")                        
    private void initComponents() throws IOException {

        componentholder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addsensor = new javax.swing.JButton();
        removesensor = new javax.swing.JButton();
        viewsensor = new javax.swing.JButton();
        mapcont = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));
        setTitle("SmartCity Sensor Stations");

        //componentholder.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(153, 153, 255));

        addsensor.setBackground(new java.awt.Color(153, 153, 153));
        addsensor.setText("View Sensor Stations");
        addsensor.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewSensorStations(evt);
            }
        });

        removesensor.setBackground(new java.awt.Color(255, 255, 255));
        removesensor.setText("View All Sensors");
        removesensor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewAllSensors(evt);
            }
        });

        viewsensor.setBackground(new java.awt.Color(255, 255, 255));
        viewsensor.setText("Back to Admin");
        viewsensor.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               BacktoAdmin(evt);
            }
        });

        // Create a TileFactoryInfo for OSM
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        // Setup JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        // Set the focus
        GeoPosition Mapcenter = new GeoPosition(6.927079,79.861244);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(Mapcenter);

        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));


        Set<SwingWaypoint> waypoints = new HashSet<>(getMarkers());

        // Set the overlay painter
        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        swingWaypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(swingWaypointPainter);

        // Add the JButtons to the map viewer
        for (SwingWaypoint w : waypoints) {
            mapViewer.add(w.getButton());
        }

        mapViewer.setPreferredSize(new Dimension(1100, 450));
        mapcont.add(mapViewer);
        mapcont.setPreferredSize(new Dimension(1100, 450));

        javax.swing.GroupLayout componentholderLayout = new javax.swing.GroupLayout(componentholder);
        componentholder.setLayout(componentholderLayout);
        componentholderLayout.setHorizontalGroup(
            componentholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentholderLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(viewsensor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156)
                .addComponent(addsensor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(removesensor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(componentholderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(mapcont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        componentholderLayout.setVerticalGroup(
            componentholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentholderLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(componentholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removesensor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addsensor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewsensor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(componentholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(componentholderLayout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jLabel1))
                    .addGroup(componentholderLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(mapcont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(componentholder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(componentholder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }                     

    private void ViewSensorStations(java.awt.event.ActionEvent evt) {                                          
        new librarysystem.SensorStationGUI().setVisible(true);
        this.dispose();
    }                                         

    private void ViewAllSensors(java.awt.event.ActionEvent evt) {                                             
        new sensor.SensorGUI().setVisible(true);
        this.dispose();
    }                                            

    private void BacktoAdmin(java.awt.event.ActionEvent evt) {                                           
        new AdminInterface.CommonInterface().setVisible(true);
        this.dispose();
    }                                          
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                try    {
                    UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
                }   catch (Exception e)    {
                    e.printStackTrace();
                }
                    HomeScreen screen = new HomeScreen();
                    screen.setSize(1175,642);
                    screen.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
                 

    private javax.swing.JButton addsensor;
    private javax.swing.JPanel componentholder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mapcont;
    private javax.swing.JButton removesensor;
    private javax.swing.JButton viewsensor;
    // End of variables declaration                   
}
