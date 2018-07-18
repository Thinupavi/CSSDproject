package sensor;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.awt.Color;
import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import static java.lang.Integer.parseInt;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import sensor.Sensor;
import sensor.SensorGUI;
import sensor.SensorStations;
import sensor.SetOfSensorStations;
import sensor.SetOfSensors;

public class SensorStationGUI extends javax.swing.JFrame {

    private SensorStations selectedStations = null;
    private Sensor selectedSensor = null;
    private SetOfSensors theSensors = new SetOfSensors();
    private SetOfSensorStations theStations = new SetOfSensorStations();
    private boolean changesSaved; // set when changes have been applied/changes saved
    private String queryInput = null; // user inputted value when searching

    /** Creates new form SensorStaionGUI */
    public SensorStationGUI() 
    {
        initComponents();
        setGUIModalities(); // used to set all jDialogs to application model

        hideFilterBtn(btnResetSenstaionsFilter);
        
        // set to true for data-refresh
        File stations = new File("Files/SensorStation.ser");
  //      File smonitors = new File("Files/sensors.ser");
        if (!stations.exists()){
            File f = new File("Files/");
            if(!f.exists())
                f.mkdir();
            
          resetData();
        }else{
              deserializeStationsSet("Files/SensorStation.ser");
//            deserializeSensormonitorSet("Files/sensors.ser");
        }
        
        setGUITxt();
        refreshStationsJList(null);
        refreshSmonitorsJLTable();
        SensorGUI.deserializeSensorsSet("Files/sensor.ser");
        
        // initially set to true until everything is loaded in
        changesSaved = true;
    }
    public SetOfSensorStations senddata(){
        return this.theStations;
    }
    private void resetData(){
        SensorStations stations1  = new SensorStations(0,"Colombo01",2,79.8437,6.9378);
        SensorStations stations2  = new SensorStations(1,"Colombo02",3,79.8549,6.9222);
        SensorStations stations3  = new SensorStations(2,"Colombo03",4,79.8549,6.9010);
        SensorStations stations4  = new SensorStations(3,"Colombo04",5,79.8563,6.8865);


        theStations.addSensorStations(stations1);
        theStations.addSensorStations(stations2);
        theStations.addSensorStations(stations3);
        theStations.addSensorStations(stations4);
 
 }
    
    
    private void filterStations(){
        SetOfSensorStations filStations = new SetOfSensorStations();
        //clear selectedStation before begining
        clearSelectedStation();
        if(jComboStationFilter.getSelectedIndex() == 0){
            // if combo box is 0, Station is searching by ID
            try{
                filStations = theStations.getSensorStationsFromSearch(parseInt(jTxtStationSearchInput.getText()));
            }catch(Exception e){
                // do something here
            }
        }
        else 
        {
            // if combo box is 1, Station is searching by Station Name
            try{
                filStations = theStations.getSensorStationsnameFromSearch(jTxtStationSearchInput.getText());
            }catch(Exception e){
                // do something here
            }
        }
        if (!filStations.isEmpty()){
            refreshStationsJList(filStations);
            btnResetSenstaionsFilter.setVisible(true);
            setGUITxt();
        }else{
            //error handler for when no results are returned...
            lblNoStaionsQResultsTxt.setVisible(true);
        }
        jDialogStationSearch.setVisible(false);
    }
    
    
    private void setGUIModalities(){
        jDialogStationSearch.setModalityType(APPLICATION_MODAL);
        jDialogQuitConf.setModalityType(APPLICATION_MODAL); 
        jDialogDelConf.setModalityType(APPLICATION_MODAL); 
        jDialogCRUDStationForm.setModalityType(APPLICATION_MODAL); 
        jDialogStationSearch.setModalityType(APPLICATION_MODAL);
        jDialogViewStations.setModalityType(APPLICATION_MODAL);

    }
    
    private void hideFilterBtn(JButton filterBtn){
        filterBtn.setVisible(false);
        setGUITxt();
    }
    
    private void showChangesSaved(){
        if (changesSaved==false){
            lblChangesSaved.setVisible(true);
            lblChangesSaved1.setVisible(true);
            changesSaved = true;
        }
    }
    
    private void setGUITxt(){
        lblNoStaionsQResultsTxt.setVisible(false);
        lblChangesSaved.setVisible(false);
        changesSaved = false;
        // sets Station count on main GUI
        lblMainStationCnt.setText(String.valueOf(theStations.size()));
       // lblNoStaionsQResultsTxt1.setVisible(false);
        lblChangesSaved1.setVisible(false);
        changesSaved = false;
        // sets Station count on main GUI
//        lblMainStationCnt1.setText(String.valueOf(jTable3.getRowCount()));
    }
    
    private void saveAll(){
        clearSelectedStation();
        serializeStationsSet("Files/SensorStation.ser");
//        serializeSensorMonitorsSet("Files/sensors.ser");
        showChangesSaved();
    }

    private void showStationSearchGUI(){        
        jDialogStationSearch.pack();
        jComboStationFilter.setSelectedIndex(0); // reset combo back to start
        jTxtStationSearchInput.setText("");
        jDialogStationSearch.setVisible(true);
    }
    
    
    private void viewstationGUI(){
        jDialogViewStations.pack();
        //populate the stations table
        try{
            Object [] rowData=new Object[2];
            DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            
            for(int i = 0; i<theStations.size();i++)
            {
                  rowData[0]=theStations.get(i).getStationID();
                  rowData[1]=theStations.get(i).getDestination();
                  model.addRow(rowData);
            }
            }
        catch(Exception ex)
        {
            
        }
        
        Showaddtable();
        jDialogViewStations.setVisible(true);
    }
    
    private void Showaddtable(){
        //populate the sensor table
        try{
            Object [] rowData1=new Object[2];
            DefaultTableModel model1= (DefaultTableModel) jTable2.getModel();
            model1.setRowCount(0);
            
            
            for(int i = 0; i<SensorGUI.theSensors.size();i++)
            {
                  rowData1[0]=SensorGUI.theSensors.get(i).getSensorNumber();
                  rowData1[1]=SensorGUI.theSensors.get(i).getName();
                  model1.addRow(rowData1);
            }
            }
        catch(Exception ex)
        {
            
        }
        
        jDialogViewStations.setVisible(true);
    }
    

    private void showQuitConfGUI(){
        if (changesSaved==false){
            jDialogQuitConf.pack(); 
            jDialogQuitConf.setVisible(true);
        }
        else{
            System.exit(0);
        }
    }
    
    private void showQuitConfGUI1(){
        if (changesSaved==false){
            jDialogQuitConf.pack(); 
            jDialogQuitConf.setVisible(true);

        }
        else{
            jDialogViewStations.setVisible(false);
        }
    }
    private void showStationCRUDGUI(String reason) {
        jTxtStationNameInput.setEnabled(true);
        jTxtNASensors.setEnabled(true);
        jTxtLattitude.setEnabled(true);
        jTxtLongtitude.setEnabled(true);
        jTxtStationNameInput.setText("");
        jTxtNASensors.setText("");
        jTxtLattitude.setText("");
        jTxtLongtitude.setText("");
        jDialogCRUDStationForm.pack(); 
        
        if (reason == "new"){
            jTxtStationIDInput.setText(Integer.toString(
                    theStations.lastElement().getStationID()+1));
            jTxtNASensors.setEnabled(false);
            btnStationFormApply.setText("Add");
        }else{
            jTxtStationIDInput.setText(String.valueOf(
            selectedStations.getStationID()));
            jTxtStationNameInput.setText(selectedStations.getDestination());
            jTxtNASensors.setText(String.valueOf(selectedStations.getNoofActiveSensors()));
            jTxtLattitude.setText(String.valueOf(selectedStations.getLattitude()));
            jTxtLongtitude.setText(String.valueOf(selectedStations.getLongitude()));
            
            if(reason == "edit"){
            btnStationFormApply.setText("Save");
            jTxtStationNameInput.setEnabled(false);
            jTxtLattitude.setEnabled(false);
            jTxtLongtitude.setEnabled(false);
            
            }else{
                btnStationFormApply.setText("Delete");
                jTxtStationNameInput.setEnabled(false);
                jTxtLattitude.setEnabled(false);
                jTxtNASensors.setEnabled(false);
                jTxtLongtitude.setEnabled(false);
            
            }
        }
        jDialogCRUDStationForm.setVisible(true);
    } 
    private void refreshStationsJList(SetOfSensorStations filtStations){
        if (filtStations == null){
            jListSensorstations.setListData(theStations);
        }else{
            jListSensorstations.setListData(filtStations);
        }
    }
    
     private void refreshSmonitorsJLTable(){
         try{
            Object [] rowData=new Object[2];
            DefaultTableModel model= (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            
            for(int i = 0; i<theSensors.size();i++)
            {
                  rowData[0]=theSensors.get(i).getSensorNumber();
                  rowData[1]=theSensors.get(i).getName();
                  model.addRow(rowData);
            }
            }
        catch(Exception ex)
        {
            
        }      
    }
    
    private void setSelectedStation(){
        // Get value from jlist (SetOfStations) and cast to Stations
        selectedStations = (SensorStations)jListSensorstations.getSelectedValue();
        btnEditSenStations.setEnabled(true);
        
        btnDelSenStaions.setEnabled(true);
        btnDelSenStaions.setToolTipText(null);
       
    }
    
    
    private void clearSelectedStation(){
        jListSensorstations.clearSelection();
        lblNoStaionsQResultsTxt.setVisible(false);
        selectedStations = null;
        refreshStationsJList(null);
        btnEditSenStations.setEnabled(false);
        btnDelSenStaions.setEnabled(false);
    }
   
    private void serializeStationsSet(String fLoc){
        try
        {
            FileOutputStream fileOut = 
                    new FileOutputStream(fLoc);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            
            for(SensorStations station : theStations) {
                /* Function setup for when data is loaded back in, so sensors  
                can be re-initialized without dups */
                station.returnAllBOL(); 
            }
            objOut.writeObject(theStations);
            
            objOut.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fLoc);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        // completely refresh data, if its been saved without quitting
        deserializeStationsSet(fLoc);
    }

    
    private void deserializeStationsSet(String fLoc)
    {
        //failsafe
        theStations.removeAllElements();
        try
        {
            FileInputStream fileIn = new FileInputStream(fLoc);
            ObjectInputStream objIn = new ObjectInputStream (fileIn);

            Object obj = objIn.readObject();
            
            // check read object
            if (obj instanceof Vector)
            {
                SetOfSensorStations Stations = (SetOfSensorStations) obj;
                for(int x = 0; x < Stations.size(); x++){
                    SensorStations eStations = new SensorStations(Stations.elementAt(x).getStationID(),
                            Stations.elementAt(x).getDestination(), 
                            Stations.elementAt(x).getNoofActiveSensors(),
                            Stations.elementAt(x).getLattitude(), 
                            Stations.elementAt(x).getLongitude());

                    theStations.addSensorStations(eStations);
                }
            }
            fileIn.close();
            objIn.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
 
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogQuitConf = new javax.swing.JDialog();
        btnQuitConfJQ = new javax.swing.JButton();
        btnQuitConfSQ = new javax.swing.JButton();
        lblQuitConfTitle = new javax.swing.JLabel();
        jSeparatorQuitConfHeader = new javax.swing.JSeparator();
        jDialogDelConf = new javax.swing.JDialog();
        btnQuitConfYes = new javax.swing.JButton();
        lblDelConfTitle = new javax.swing.JLabel();
        jSeparatorDelConfHeader = new javax.swing.JSeparator();
        btnQuitConfNo = new javax.swing.JButton();
        jDialogCRUDStationForm = new javax.swing.JDialog();
        lblStationFormTitle = new javax.swing.JLabel();
        btnStationFormCancel = new javax.swing.JButton();
        jSeparatorStationFormHeader = new javax.swing.JSeparator();
        btnStationFormApply = new javax.swing.JButton();
        jTxtStationNameInput = new javax.swing.JTextPane();
        lblStationNameInputTxt = new javax.swing.JLabel();
        jTxtStationIDInput = new javax.swing.JTextPane();
        lblStationIDInputTxt = new javax.swing.JLabel();
        lblNoAsensorsInputTxt1 = new javax.swing.JLabel();
        jTxtNASensors = new javax.swing.JTextPane();
        lblStationNameInputTxt2 = new javax.swing.JLabel();
        jTxtLattitude = new javax.swing.JTextPane();
        lblStationNameInputTxt3 = new javax.swing.JLabel();
        jTxtLongtitude = new javax.swing.JTextPane();
        jDialogStationSearch = new javax.swing.JDialog();
        lblStationSearchTitle = new javax.swing.JLabel();
        btnStationSearch = new javax.swing.JButton();
        jSeparatorStationSearchHeader = new javax.swing.JSeparator();
        lblStationSearchFilterTxt = new javax.swing.JLabel();
        lblStationSearchInputTxt = new javax.swing.JLabel();
        jComboStationFilter = new javax.swing.JComboBox();
        jTxtStationSearchInput = new javax.swing.JTextField();
        jDialogViewStations = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnQuit1 = new javax.swing.JButton();
        btnSaveChanges1 = new javax.swing.JButton();
        lblChangesSaved1 = new javax.swing.JLabel();
        btnQueryStations1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnQueryStations2 = new javax.swing.JButton();
        btnQueryStations3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jDialogaddSSM = new javax.swing.JDialog();
        jDialogremoveSSM = new javax.swing.JDialog();
        jDialogsearchSM = new javax.swing.JDialog();
        jPanelContainer = new javax.swing.JPanel();
        btnAddSenStaions = new javax.swing.JButton();
        btnEditSenStations = new javax.swing.JButton();
        btnDelSenStaions = new javax.swing.JButton();
        btnQueryStations = new javax.swing.JButton();
        lblNoStaionsQResultsTxt = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListSensorstations = new javax.swing.JList();
        lblSensorsJlistHead = new javax.swing.JLabel();
        btnResetSenstaionsFilter = new javax.swing.JButton();
        btnviewSenStaions = new javax.swing.JButton();
        lblMainStationCnt = new javax.swing.JLabel();
        lblMainSensorStationsCntTxt = new javax.swing.JLabel();
        btnQuit = new javax.swing.JButton();
        btnSaveChanges = new javax.swing.JButton();
        lblChangesSaved = new javax.swing.JLabel();

        jDialogQuitConf.setLocationByPlatform(true);
        jDialogQuitConf.setMinimumSize(new java.awt.Dimension(503, 169));
        jDialogQuitConf.setResizable(false);

        btnQuitConfJQ.setText("Just Quit");
        btnQuitConfJQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitConfJQActionPerformed(evt);
            }
        });

        btnQuitConfSQ.setText("Save & Quit");
        btnQuitConfSQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitConfSQActionPerformed(evt);
            }
        });

        lblQuitConfTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblQuitConfTitle.setText("Would you like to save before quitting?");

        javax.swing.GroupLayout jDialogQuitConfLayout = new javax.swing.GroupLayout(jDialogQuitConf.getContentPane());
        jDialogQuitConf.getContentPane().setLayout(jDialogQuitConfLayout);
        jDialogQuitConfLayout.setHorizontalGroup(
            jDialogQuitConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogQuitConfLayout.createSequentialGroup()
                .addGroup(jDialogQuitConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogQuitConfLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jDialogQuitConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblQuitConfTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparatorQuitConfHeader)))
                    .addGroup(jDialogQuitConfLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(btnQuitConfJQ)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitConfSQ)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jDialogQuitConfLayout.setVerticalGroup(
            jDialogQuitConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogQuitConfLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblQuitConfTitle)
                .addGap(18, 18, 18)
                .addComponent(jSeparatorQuitConfHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jDialogQuitConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitConfJQ)
                    .addComponent(btnQuitConfSQ))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jDialogDelConf.setLocationByPlatform(true);
        jDialogDelConf.setMinimumSize(new java.awt.Dimension(453, 165));
        jDialogDelConf.setResizable(false);

        btnQuitConfYes.setText("Yes");
        btnQuitConfYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitConfYesActionPerformed(evt);
            }
        });

        lblDelConfTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblDelConfTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDelConfTitle.setText("Are you sure you want to delete this?");

        btnQuitConfNo.setText("No");
        btnQuitConfNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitConfNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialogDelConfLayout = new javax.swing.GroupLayout(jDialogDelConf.getContentPane());
        jDialogDelConf.getContentPane().setLayout(jDialogDelConfLayout);
        jDialogDelConfLayout.setHorizontalGroup(
            jDialogDelConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogDelConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogDelConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparatorDelConfHeader)
                    .addComponent(lblDelConfTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogDelConfLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQuitConfNo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnQuitConfYes, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );
        jDialogDelConfLayout.setVerticalGroup(
            jDialogDelConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogDelConfLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblDelConfTitle)
                .addGap(18, 18, 18)
                .addComponent(jSeparatorDelConfHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jDialogDelConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitConfNo)
                    .addComponent(btnQuitConfYes))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jDialogCRUDStationForm.setLocationByPlatform(true);
        jDialogCRUDStationForm.setMinimumSize(new java.awt.Dimension(460, 226));

        lblStationFormTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblStationFormTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStationFormTitle.setText("Sensor Stations");

        btnStationFormCancel.setBackground(new java.awt.Color(51, 51, 255));
        btnStationFormCancel.setText("Cancel");
        btnStationFormCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnStationFormCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStationFormCancelActionPerformed(evt);
            }
        });

        btnStationFormApply.setBackground(new java.awt.Color(51, 51, 255));
        btnStationFormApply.setText("variable");
        btnStationFormApply.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnStationFormApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStationFormApplyActionPerformed(evt);
            }
        });

        jTxtStationNameInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtStationNameInput.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        lblStationNameInputTxt.setText("Stations Name");

        jTxtStationIDInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtStationIDInput.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTxtStationIDInput.setForeground(new java.awt.Color(255, 255, 255));
        jTxtStationIDInput.setText("number");
        jTxtStationIDInput.setToolTipText("");
        jTxtStationIDInput.setCaretColor(new java.awt.Color(255, 255, 255));
        jTxtStationIDInput.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jTxtStationIDInput.setEnabled(false);
        jTxtStationIDInput.setFocusable(false);

        lblStationIDInputTxt.setText("Station ID");

        lblNoAsensorsInputTxt1.setText("No Of Active Sensors");

        jTxtNASensors.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtNASensors.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTxtNASensors.setForeground(new java.awt.Color(255, 255, 255));

        lblStationNameInputTxt2.setText("Lattitude");

        jTxtLattitude.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtLattitude.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        lblStationNameInputTxt3.setText("Longtitude");

        jTxtLongtitude.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtLongtitude.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        javax.swing.GroupLayout jDialogCRUDStationFormLayout = new javax.swing.GroupLayout(jDialogCRUDStationForm.getContentPane());
        jDialogCRUDStationForm.getContentPane().setLayout(jDialogCRUDStationFormLayout);
        jDialogCRUDStationFormLayout.setHorizontalGroup(
            jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCRUDStationFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparatorStationFormHeader)
                    .addComponent(lblStationFormTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogCRUDStationFormLayout.createSequentialGroup()
                        .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jDialogCRUDStationFormLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnStationFormCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnStationFormApply, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDialogCRUDStationFormLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblStationIDInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblStationNameInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(lblNoAsensorsInputTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(lblStationNameInputTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(lblStationNameInputTxt3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtStationNameInput, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                    .addComponent(jTxtNASensors, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                    .addComponent(jTxtLattitude, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                    .addComponent(jTxtLongtitude, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                    .addComponent(jTxtStationIDInput))))
                        .addGap(16, 16, 16)))
                .addContainerGap())
        );
        jDialogCRUDStationFormLayout.setVerticalGroup(
            jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCRUDStationFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStationFormTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorStationFormHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtStationIDInput, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(lblStationIDInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblStationNameInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtStationNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNoAsensorsInputTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtNASensors, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblStationNameInputTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtLattitude, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblStationNameInputTxt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtLongtitude, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jDialogCRUDStationFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStationFormCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(btnStationFormApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jDialogStationSearch.setLocationByPlatform(true);
        jDialogStationSearch.setMinimumSize(new java.awt.Dimension(470, 257));
        jDialogStationSearch.setResizable(false);
        jDialogStationSearch.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jDialogStationSearchWindowActivated(evt);
            }
        });

        lblStationSearchTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblStationSearchTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStationSearchTitle.setText("Sensor Stations Search");

        btnStationSearch.setText("Search");
        btnStationSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnStationSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStationSearchActionPerformed(evt);
            }
        });

        lblStationSearchFilterTxt.setText("Search By");

        lblStationSearchInputTxt.setText("Search Value");

        jComboStationFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "StationID", "Station Name", "No Of Active Sensors", " " }));

        jTxtStationSearchInput.setMinimumSize(new java.awt.Dimension(72, 26));
        jTxtStationSearchInput.setName(""); // NOI18N
        jTxtStationSearchInput.setPreferredSize(new java.awt.Dimension(72, 26));

        javax.swing.GroupLayout jDialogStationSearchLayout = new javax.swing.GroupLayout(jDialogStationSearch.getContentPane());
        jDialogStationSearch.getContentPane().setLayout(jDialogStationSearchLayout);
        jDialogStationSearchLayout.setHorizontalGroup(
            jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogStationSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparatorStationSearchHeader)
                    .addComponent(lblStationSearchTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogStationSearchLayout.createSequentialGroup()
                .addGap(0, 25, Short.MAX_VALUE)
                .addGroup(jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnStationSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialogStationSearchLayout.createSequentialGroup()
                        .addGroup(jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblStationSearchFilterTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStationSearchInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboStationFilter, 0, 236, Short.MAX_VALUE)
                            .addComponent(jTxtStationSearchInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        jDialogStationSearchLayout.setVerticalGroup(
            jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogStationSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStationSearchTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorStationSearchHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStationSearchFilterTxt)
                    .addComponent(jComboStationFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jDialogStationSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStationSearchInputTxt)
                    .addComponent(jTxtStationSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnStationSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jDialogViewStations.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialogViewStationsWindowClosing(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sensor Station", "Location"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnQuit1.setBackground(new java.awt.Color(255, 102, 102));
        btnQuit1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQuit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-shutdown-30.png"))); // NOI18N
        btnQuit1.setText("Quit");
        btnQuit1.setActionCommand("queryMembersNo");
        btnQuit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuit1ActionPerformed(evt);
            }
        });

        btnSaveChanges1.setBackground(new java.awt.Color(153, 255, 153));
        btnSaveChanges1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSaveChanges1.setText("Save Changes");
        btnSaveChanges1.setActionCommand("queryMembersNo");
        btnSaveChanges1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChanges1ActionPerformed(evt);
            }
        });

        lblChangesSaved1.setForeground(new java.awt.Color(0, 204, 0));
        lblChangesSaved1.setText("Changes Saved");

        btnQueryStations1.setBackground(new java.awt.Color(51, 51, 255));
        btnQueryStations1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnQueryStations1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-search-30.png"))); // NOI18N
        btnQueryStations1.setText("Search Sensor Monitors");
        btnQueryStations1.setActionCommand("queryMembersNo");
        btnQueryStations1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryStations1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sensor Monitor ID", "Sensor Monitor Name"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        btnQueryStations2.setBackground(new java.awt.Color(51, 51, 255));
        btnQueryStations2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnQueryStations2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-plus-30.png"))); // NOI18N
        btnQueryStations2.setText("Add Senor Monitor to Station");
        btnQueryStations2.setActionCommand("queryMembersNo");
        btnQueryStations2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryStations2ActionPerformed(evt);
            }
        });

        btnQueryStations3.setBackground(new java.awt.Color(51, 51, 255));
        btnQueryStations3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnQueryStations3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-delete-30.png"))); // NOI18N
        btnQueryStations3.setText("Remove Senor Monitor From Station");
        btnQueryStations3.setActionCommand("queryMembersNo");
        btnQueryStations3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryStations3ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sensor Station Name", "Sensor Monitor ID", "Sensor Monitor Name"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jDialogViewStationsLayout = new javax.swing.GroupLayout(jDialogViewStations.getContentPane());
        jDialogViewStations.getContentPane().setLayout(jDialogViewStationsLayout);
        jDialogViewStationsLayout.setHorizontalGroup(
            jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogViewStationsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblChangesSaved1)
                .addGap(39, 39, 39)
                .addComponent(btnSaveChanges1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuit1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(jDialogViewStationsLayout.createSequentialGroup()
                .addGroup(jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jDialogViewStationsLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnQueryStations2)
                            .addGap(18, 18, 18)
                            .addComponent(btnQueryStations3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnQueryStations1))
                        .addGroup(jDialogViewStationsLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(67, 67, 67)
                            .addComponent(jScrollPane3)))
                    .addGroup(jDialogViewStationsLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialogViewStationsLayout.setVerticalGroup(
            jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogViewStationsLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQueryStations2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQueryStations1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQueryStations3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jDialogViewStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChangesSaved1)
                    .addComponent(btnSaveChanges1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuit1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jDialogaddSSMLayout = new javax.swing.GroupLayout(jDialogaddSSM.getContentPane());
        jDialogaddSSM.getContentPane().setLayout(jDialogaddSSMLayout);
        jDialogaddSSMLayout.setHorizontalGroup(
            jDialogaddSSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialogaddSSMLayout.setVerticalGroup(
            jDialogaddSSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialogremoveSSMLayout = new javax.swing.GroupLayout(jDialogremoveSSM.getContentPane());
        jDialogremoveSSM.getContentPane().setLayout(jDialogremoveSSMLayout);
        jDialogremoveSSMLayout.setHorizontalGroup(
            jDialogremoveSSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialogremoveSSMLayout.setVerticalGroup(
            jDialogremoveSSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialogsearchSMLayout = new javax.swing.GroupLayout(jDialogsearchSM.getContentPane());
        jDialogsearchSM.getContentPane().setLayout(jDialogsearchSMLayout);
        jDialogsearchSMLayout.setHorizontalGroup(
            jDialogsearchSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialogsearchSMLayout.setVerticalGroup(
            jDialogsearchSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SmartCity Sensor Stations");
        setLocationByPlatform(true);
        setResizable(false);

        jPanelContainer.setPreferredSize(new java.awt.Dimension(1175, 642));

        btnAddSenStaions.setBackground(new java.awt.Color(51, 51, 255));
        btnAddSenStaions.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAddSenStaions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-plus-30.png"))); // NOI18N
        btnAddSenStaions.setText("Add Sensor Stations");
        btnAddSenStaions.setToolTipText("");
        btnAddSenStaions.setActionCommand("queryMembersNo");
        btnAddSenStaions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSenStaionsActionPerformed(evt);
            }
        });

        btnEditSenStations.setBackground(new java.awt.Color(102, 102, 255));
        btnEditSenStations.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEditSenStations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-compose-30.png"))); // NOI18N
        btnEditSenStations.setText("Edit Sensor Stations");
        btnEditSenStations.setToolTipText("");
        btnEditSenStations.setActionCommand("queryMembersNo");
        btnEditSenStations.setEnabled(false);
        btnEditSenStations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSenStationsActionPerformed(evt);
            }
        });

        btnDelSenStaions.setBackground(new java.awt.Color(102, 102, 255));
        btnDelSenStaions.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDelSenStaions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-delete-30.png"))); // NOI18N
        btnDelSenStaions.setText("Delete Sensor Stations");
        btnDelSenStaions.setToolTipText("");
        btnDelSenStaions.setActionCommand("queryMembersNo");
        btnDelSenStaions.setEnabled(false);
        btnDelSenStaions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelSenStaionsActionPerformed(evt);
            }
        });

        btnQueryStations.setBackground(new java.awt.Color(102, 102, 255));
        btnQueryStations.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnQueryStations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-search-30.png"))); // NOI18N
        btnQueryStations.setText("Search Sensor Stations");
        btnQueryStations.setActionCommand("queryMembersNo");
        btnQueryStations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryStationsActionPerformed(evt);
            }
        });

        lblNoStaionsQResultsTxt.setForeground(new java.awt.Color(255, 0, 0));
        lblNoStaionsQResultsTxt.setText("No Results returned!");

        jListSensorstations.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListSensorstations.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListSensorstations.setFocusable(false);
        jListSensorstations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jListSensorstationsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jListSensorstations);

        lblSensorsJlistHead.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSensorsJlistHead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSensorsJlistHead.setText("Sensors Stations");

        btnResetSenstaionsFilter.setBackground(new java.awt.Color(255, 102, 102));
        btnResetSenstaionsFilter.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnResetSenstaionsFilter.setText("clear filter");
        btnResetSenstaionsFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btnResetSenstaionsFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSenstaionsFilterActionPerformed(evt);
            }
        });

        btnviewSenStaions.setBackground(new java.awt.Color(102, 102, 255));
        btnviewSenStaions.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnviewSenStaions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-view-30.png"))); // NOI18N
        btnviewSenStaions.setText("View Sensor Stations");
        btnviewSenStaions.setToolTipText("");
        btnviewSenStaions.setActionCommand("queryMembersNo");
        btnviewSenStaions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnviewSenStaionsActionPerformed(evt);
            }
        });

        lblMainStationCnt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMainStationCnt.setText("Num");

        lblMainSensorStationsCntTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMainSensorStationsCntTxt.setText("Sensor Station Count:");

        javax.swing.GroupLayout jPanelContainerLayout = new javax.swing.GroupLayout(jPanelContainer);
        jPanelContainer.setLayout(jPanelContainerLayout);
        jPanelContainerLayout.setHorizontalGroup(
            jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerLayout.createSequentialGroup()
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelContainerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDelSenStaions))
                    .addGroup(jPanelContainerLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelContainerLayout.createSequentialGroup()
                                .addComponent(lblMainSensorStationsCntTxt)
                                .addGap(74, 74, 74)
                                .addComponent(lblMainStationCnt))
                            .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanelContainerLayout.createSequentialGroup()
                                    .addComponent(btnAddSenStaions)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEditSenStations)
                                    .addGap(248, 248, 248))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelContainerLayout.createSequentialGroup()
                                    .addComponent(lblSensorsJlistHead, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnResetSenstaionsFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(29, 29, 29)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnQueryStations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnviewSenStaions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNoStaionsQResultsTxt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelContainerLayout.setVerticalGroup(
            jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelSenStaions, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditSenStations, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSenStaions, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetSenstaionsFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSensorsJlistHead, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelContainerLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMainStationCnt)
                            .addComponent(lblMainSensorStationsCntTxt))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelContainerLayout.createSequentialGroup()
                        .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnQueryStations, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNoStaionsQResultsTxt))
                        .addGap(74, 74, 74)
                        .addComponent(btnviewSenStaions, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        btnQuit.setBackground(new java.awt.Color(255, 102, 102));
        btnQuit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-home-30.png"))); // NOI18N
        btnQuit.setText("Back to Home");
        btnQuit.setActionCommand("queryMembersNo");
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });

        btnSaveChanges.setBackground(new java.awt.Color(153, 255, 153));
        btnSaveChanges.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSaveChanges.setText("Save Changes");
        btnSaveChanges.setActionCommand("queryMembersNo");
        btnSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChangesActionPerformed(evt);
            }
        });

        lblChangesSaved.setForeground(new java.awt.Color(0, 204, 0));
        lblChangesSaved.setText("Changes Saved");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblChangesSaved)
                        .addGap(26, 26, 26)
                        .addComponent(btnSaveChanges)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuit)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChangesSaved))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveChangesActionPerformed
        // TODO add your handling code here:
        lblChangesSaved.setVisible(true);
        saveAll();
        
    }//GEN-LAST:event_btnSaveChangesActionPerformed

    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitActionPerformed
        // TODO add your handling code here:
        
        //showQuitConfGUI();
        try {
            // TODO add your handling code here:
            new HomeMainScreen.HomeScreen().setVisible(true);
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(SensorStationGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnQuitActionPerformed

    private void btnQuitConfJQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitConfJQActionPerformed
        System.exit(0);    
    }//GEN-LAST:event_btnQuitConfJQActionPerformed

    private void btnQuitConfSQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitConfSQActionPerformed
        // TODO add your handling code here:
        saveAll();
        System.exit(0);
    }//GEN-LAST:event_btnQuitConfSQActionPerformed

    private void btnQuitConfYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitConfYesActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnQuitConfYesActionPerformed

    private void btnQuitConfNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitConfNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQuitConfNoActionPerformed

    private void btnStationFormApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStationFormApplyActionPerformed
        // TODO add your handling code here:
       
        String btnTxt = btnStationFormApply.getText();
        String StationName = new String();
        int NoActiveSnesors ;
        double lattitude;
        double longtitude;
        
        StationName = jTxtStationNameInput.getText();
        jTxtNASensors.setText("0");
        NoActiveSnesors = Integer.parseInt(jTxtNASensors.getText());
        lattitude = Double.parseDouble(jTxtLattitude.getText());
        longtitude = Double.parseDouble(jTxtLongtitude.getText());
        if(!btnTxt.isEmpty() && !StationName.trim().isEmpty() && !jTxtLattitude.getText().isEmpty() &&!jTxtLongtitude.getText().isEmpty() ){
            try{
            if (btnTxt.equals("Add")){ // btn txt == add
                SensorStations station = new SensorStations(theStations.lastElement().getStationID()+1,StationName,lattitude,longtitude);
                theStations.add(station);
            }else if (btnTxt.equals("Save")){ // btn txt == edit
                selectedStations.setDestination(StationName);
            }else{ // btn txt == delete
               theStations.removeSensorStations(selectedStations);
            }
        
            setGUITxt();

            jDialogCRUDStationForm.setVisible(false);
            clearSelectedStation();
            refreshStationsJList(null);
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this,"Lattitude and Longtitude MUST be only numbers.\nPlease check and try again.","Warning",JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Please fill every field.","Warning",JOptionPane.WARNING_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btnStationFormApplyActionPerformed

    private void btnStationFormCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStationFormCancelActionPerformed
        // TODO add your handling code here:
        jDialogCRUDStationForm.setVisible(false);
    }//GEN-LAST:event_btnStationFormCancelActionPerformed

    private void btnStationSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStationSearchActionPerformed
        // TODO add your handling code here:
        filterStations();        
    }//GEN-LAST:event_btnStationSearchActionPerformed

    private void jDialogStationSearchWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogStationSearchWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_jDialogStationSearchWindowActivated

    private void btnResetSenstaionsFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSenstaionsFilterActionPerformed
        // TODO add your handling code here:
        hideFilterBtn(this.btnResetSenstaionsFilter);
        clearSelectedStation();
    }//GEN-LAST:event_btnResetSenstaionsFilterActionPerformed

    private void btnEditSenStationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSenStationsActionPerformed
        // TODO add your handling code here:

        showStationCRUDGUI("edit");
    }//GEN-LAST:event_btnEditSenStationsActionPerformed

    private void btnAddSenStaionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSenStaionsActionPerformed
        // TODO add your handling code here:
        showStationCRUDGUI("new");
    }//GEN-LAST:event_btnAddSenStaionsActionPerformed

    private void btnDelSenStaionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelSenStaionsActionPerformed
        // TODO add your handling code here:

        // HERE
        showStationCRUDGUI("delete");
    }//GEN-LAST:event_btnDelSenStaionsActionPerformed

    private void jListSensorstationsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListSensorstationsMousePressed
        // TODO add your handling code here:
        setSelectedStation();
    }//GEN-LAST:event_jListSensorstationsMousePressed

    private void btnQueryStationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueryStationsActionPerformed
        showStationSearchGUI();
    }//GEN-LAST:event_btnQueryStationsActionPerformed

    private void btnviewSenStaionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnviewSenStaionsActionPerformed
        // TODO add your handling code here:
        viewstationGUI();
    }//GEN-LAST:event_btnviewSenStaionsActionPerformed

    private void btnQuit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuit1ActionPerformed
        // TODO add your handling code here:
        showQuitConfGUI1();
    }//GEN-LAST:event_btnQuit1ActionPerformed

    private void btnSaveChanges1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveChanges1ActionPerformed
        // TODO add your handling code here:
        lblChangesSaved1.setVisible(true);
        saveAll();
    }//GEN-LAST:event_btnSaveChanges1ActionPerformed

    private void btnQueryStations1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueryStations1ActionPerformed
        // TODO add your handling code here:
        try{
            int row= jTable1.getSelectedRow();
            
            if(row!=-1){
                
                int memNo= Integer.parseInt(jTable1.getModel().getValueAt(row, 0).toString());
                Object [] rowData= new Object[4];
                DefaultTableModel model= (DefaultTableModel) jTable3.getModel();
                model.setRowCount(0);
                SensorStations member= theStations.getSensorStations(memNo);
                SetOfSensors temp=member.getSmonitors();
                
                for(int i=0;i<temp.size();i++){
                    
                    rowData[0]=temp.get(i).getSensormontior().getDestination();
                    rowData[1]=temp.get(i).getSensorNumber();
                    rowData[2]=temp.get(i).getName();
                    model.addRow(rowData); 
                }
             
                }
            else{
                JOptionPane.showMessageDialog(this, "Please select a Station", "Oops.", JOptionPane.WARNING_MESSAGE);
            }  
        }
        catch(Exception e){
            System.out.println("Error "+e.getMessage());
            e.printStackTrace();
        }
        
        
        
    }//GEN-LAST:event_btnQueryStations1ActionPerformed

    private void btnQueryStations2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueryStations2ActionPerformed
        // TODO add your handling code here:
         try{
            //getting details of selected rows
            int rowSStations= jTable1.getSelectedRow();
            int rowSmonitors= jTable2.getSelectedRow();
            
            //checking if any row is selected
            if(rowSStations!=-1 && rowSmonitors!=-1){
                
                int SStationnNo=Integer.parseInt(jTable1.getModel().getValueAt(rowSStations, 0).toString());
                int SensormonitorNo=Integer.parseInt(jTable2.getModel().getValueAt(rowSmonitors, 0).toString());
                int SensorSIndex=-1;
                
                //getting the objects from arraylists            

                selectedSensor =SensorGUI.theSensors.getSensorsFromSearch(SensormonitorNo).firstElement();
                selectedStations=theStations.getSensorStationsFromSearch(SStationnNo).firstElement();
             
                //finding the selected station object's index from the arraylist
                for(int i=0;i<theStations.size();i++){
                    
                    
                    if(selectedStations==theStations.get(i)){
                        
                       SensorSIndex=i;
                        
                        break;
                    }
                    
                }
                
                //checking for the selected sensors
                for(int i=0;i<SensorGUI.theSensors.size();i++){
                    if(selectedSensor==SensorGUI.theSensors.get(i)){
                                                           
                    if(SensorSIndex!=-1){
                                
                                boolean b=theStations.get(SensorSIndex).Addsensorsmonitor(selectedSensor);
                              
                                if(b){
                                    SensorGUI.theSensors.get(i).setSensormontior(theStations.get(SensorSIndex));

                                    JOptionPane.showMessageDialog(this, "Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                                
                            }
                            else{
                                JOptionPane.showMessageDialog(this, "Station is not found", "Invalid Station", JOptionPane.WARNING_MESSAGE);
                        
                            }
                                            
                        break;
                    }
                    else{
                       // JOptionPane.showMessageDialog(this, "Sensor Monitor is not found", "Invaild sensor Monitor", JOptionPane.WARNING_MESSAGE);
                        }
                }
                
            }
            else{
                JOptionPane.showMessageDialog(this, "Please select entries from Stations table and Sensors table", "Select a Stations and Monitors", JOptionPane.WARNING_MESSAGE);
                        
            }
//            
            }
//            
        
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error Occurred!\n"+e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE );
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnQueryStations2ActionPerformed

    private void btnQueryStations3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueryStations3ActionPerformed
        // TODO add your handling code here:
        int rowSStations=jTable1.getSelectedRow();
        int rowSmonitors=jTable3.getSelectedRow();
        
        if(rowSStations!=-1 && rowSmonitors!=-1){
                int SStationnNo=Integer.parseInt(jTable1.getModel().getValueAt(rowSStations, 0).toString());
                int SensormonitorNo=Integer.parseInt(jTable3.getModel().getValueAt(rowSmonitors, 1).toString());

                
                //getting the objects from arraylists            

                selectedSensor =SensorGUI.theSensors.getSensorsFromSearch(SensormonitorNo).firstElement();
                selectedStations=theStations.getSensorStationsFromSearch(SStationnNo).firstElement();
            
            if(selectedStations!=null && selectedSensor!=null){
                
                for(int i=0;i<theStations.size();i++){
                    
                    if(selectedStations==theStations.get(i)){
                        selectedSensor.setSensormontior(null);
                        System.out.println("Station details "+selectedSensor.toString());
                       // selectedSensor.setSensormontior(null);
                        theStations.get(i).RemovesensorMontitor(selectedSensor);
                        break;
                    }
                    
                }
                
                for(int i=0;i<SensorGUI.theSensors.size();i++){
                    
                    if(selectedSensor==SensorGUI.theSensors.get(i)){
                        SensorGUI.theSensors.get(i).setSensormontior(null);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Successfully Removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        
                
            }
            else{
                JOptionPane.showMessageDialog(this, "Sensor Station or Sensor Monitor doesn't exist.", "Oops", JOptionPane.ERROR_MESSAGE);
        
            }    
        }
        else{
            JOptionPane.showMessageDialog(this, "Please select Sensor Station or Sensor Monitor", "Oops", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnQueryStations3ActionPerformed

    private void jDialogViewStationsWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogViewStationsWindowClosing
        // TODO add your handling code here:
        serializeStationsSet("Files/SensorStation.ser");
        SensorGUI.serializeSensorsSet("Files/sensor.ser");
    }//GEN-LAST:event_jDialogViewStationsWindowClosing

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try    {
                    UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
                }   catch (Exception e)    {
                    e.printStackTrace();
                }
                new SensorStationGUI().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSenStaions;
    private javax.swing.JButton btnDelSenStaions;
    private javax.swing.JButton btnEditSenStations;
    private javax.swing.JButton btnQueryStations;
    private javax.swing.JButton btnQueryStations1;
    private javax.swing.JButton btnQueryStations2;
    private javax.swing.JButton btnQueryStations3;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnQuit1;
    private javax.swing.JButton btnQuitConfJQ;
    private javax.swing.JButton btnQuitConfNo;
    private javax.swing.JButton btnQuitConfSQ;
    private javax.swing.JButton btnQuitConfYes;
    private javax.swing.JButton btnResetSenstaionsFilter;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnSaveChanges1;
    private javax.swing.JButton btnStationFormApply;
    private javax.swing.JButton btnStationFormCancel;
    private javax.swing.JButton btnStationSearch;
    private javax.swing.JButton btnviewSenStaions;
    private javax.swing.JComboBox jComboStationFilter;
    private javax.swing.JDialog jDialogCRUDStationForm;
    private javax.swing.JDialog jDialogDelConf;
    private javax.swing.JDialog jDialogQuitConf;
    private javax.swing.JDialog jDialogStationSearch;
    private javax.swing.JDialog jDialogViewStations;
    private javax.swing.JDialog jDialogaddSSM;
    private javax.swing.JDialog jDialogremoveSSM;
    private javax.swing.JDialog jDialogsearchSM;
    private javax.swing.JList jListSensorstations;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparatorDelConfHeader;
    private javax.swing.JSeparator jSeparatorQuitConfHeader;
    private javax.swing.JSeparator jSeparatorStationFormHeader;
    private javax.swing.JSeparator jSeparatorStationSearchHeader;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextPane jTxtLattitude;
    private javax.swing.JTextPane jTxtLongtitude;
    private javax.swing.JTextPane jTxtNASensors;
    private javax.swing.JTextPane jTxtStationIDInput;
    private javax.swing.JTextPane jTxtStationNameInput;
    private javax.swing.JTextField jTxtStationSearchInput;
    private javax.swing.JLabel lblChangesSaved;
    private javax.swing.JLabel lblChangesSaved1;
    private javax.swing.JLabel lblDelConfTitle;
    private javax.swing.JLabel lblMainSensorStationsCntTxt;
    private javax.swing.JLabel lblMainStationCnt;
    private javax.swing.JLabel lblNoAsensorsInputTxt1;
    private javax.swing.JLabel lblNoStaionsQResultsTxt;
    private javax.swing.JLabel lblQuitConfTitle;
    private javax.swing.JLabel lblSensorsJlistHead;
    private javax.swing.JLabel lblStationFormTitle;
    private javax.swing.JLabel lblStationIDInputTxt;
    private javax.swing.JLabel lblStationNameInputTxt;
    private javax.swing.JLabel lblStationNameInputTxt2;
    private javax.swing.JLabel lblStationNameInputTxt3;
    private javax.swing.JLabel lblStationSearchFilterTxt;
    private javax.swing.JLabel lblStationSearchInputTxt;
    private javax.swing.JLabel lblStationSearchTitle;
    // End of variables declaration//GEN-END:variables

}
