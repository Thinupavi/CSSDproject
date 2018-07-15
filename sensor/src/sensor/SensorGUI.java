package sensor;

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
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class SensorGUI extends javax.swing.JFrame {

    private Sensor selectedSensor = null;
    private SetOfSensors theSensors = new SetOfSensors();
    private boolean changesSaved; // set when changes have been applied/changes saved
    private String queryInput = null; // user inputted value when searching

    /** Creates new form LibraryGUI */
    public SensorGUI() 
    {
        initComponents();
        setGUIModalities(); // used to set all jDialogs to application modal

        hideFilterBtn(btnResetSensFilter);
        
        // set to true for data-refresh
        File sensors = new File("Files/sensors.ser");
        
        if (!sensors.exists()){
            File f = new File("Files/");
            if(!f.exists())
                f.mkdir();
            
        }else{
            deserializeSensorsSet("Files/sensors.ser");
        }
        
        setGUITxt();
        refreshSensorsJList(null);
        
        // initially set to true until everything is loaded in
        changesSaved = true;
    }
    
    
    
    private void filterSensors(){
        SetOfSensors filtSens = new SetOfSensors();
        //clear selectedSensor before begining
        clearSelectedSensor();
        if(jComboSenFilter.getSelectedIndex() == 0){
            // if combo box is 0, sensor is searching by ID
            try{
                filtSens = theSensors.getSensorsFromSearch(parseInt(jTxtSenSearchInput.getText()));
            }catch(Exception e){
                // do something here
            }
        }
        else 
        {
            // if combo box is 1, sensor is searching by Name
            try{
                filtSens = theSensors.getSensorsFromSearch(jTxtSenSearchInput.getText());
            }catch(Exception e){
                // do something here
            }
        }
        if (!filtSens.isEmpty()){
            refreshSensorsJList(filtSens);
            btnResetSensFilter.setVisible(true);
            setGUITxt();
        }else{
            //error handler for when no results are returned...
            lblNoSenQResultsTxt.setVisible(true);
        }
        jDialogSenSearch.setVisible(false);
    }
    
    
    private void setGUIModalities(){
        jDialogQuitConf.setModalityType(APPLICATION_MODAL); 
        jDialogDelConf.setModalityType(APPLICATION_MODAL); 
        jDialogCRUDSenForm.setModalityType(APPLICATION_MODAL); 
        jDialogSenSearch.setModalityType(APPLICATION_MODAL);
    }
    
    private void hideFilterBtn(JButton filterBtn){
        filterBtn.setVisible(false);
        setGUITxt();
    }
    
    private void showChangesSaved(){
        if (changesSaved==false){
            lblChangesSaved.setVisible(true);
            changesSaved = true;
        }
    }
    
    private void setGUITxt(){
        lblNoSenQResultsTxt.setVisible(false);
        lblChangesSaved.setVisible(false);
        changesSaved = false;
        
    }
    
    private void saveAll(){
        clearSelectedSensor();
        serializeSensorsSet("Files/sensors.ser");
        showChangesSaved();
    }
    
    private void showSenSearchGUI(){        
        jDialogSenSearch.pack();
        jComboSenFilter.setSelectedIndex(0); // reset combo back to start
        jTxtSenSearchInput.setText("");
        jDialogSenSearch.setVisible(true);
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
    
    private void showSenCRUDGUI(String reason) {
        jTxtSenNameInput.setEnabled(true);
        jTxtSenNameInput.setText("");
        jTxtSenStatusInput.setText("");
        jTxtSenFrequencyInput.setText("");
        jDialogCRUDSenForm.pack(); 
        
        if (reason == "new"){
            jTxtSenIDInput.setText(Integer.toString(
                    theSensors.lastElement().getSensorNumber()+1));
            btnSenFormApply.setText("Add");
        }else{
            jTxtSenIDInput.setText(String.valueOf(
                selectedSensor.getSensorNumber()));
            jTxtSenNameInput.setText(selectedSensor.getName());
            jTxtSenStatusInput.setText(selectedSensor.getStatus());
            jTxtSenFrequencyInput.setText(selectedSensor.getFrequency());
            
            if(reason == "edit"){
                btnSenFormApply.setText("Save");
            }else{
                btnSenFormApply.setText("Delete");
                jTxtSenNameInput.setEnabled(false);
            }
        }
        jDialogCRUDSenForm.setVisible(true);
    }
    
    public void loanBook(){
        
    }
    
    public void acceptReturn(){
        
    }
    
    public void showCurrentLoans(){
        
    }
    
    public void selectBook(){
        
    }
    
    public void selectSensor(){
        
    }
    
    private void refreshSensorsJList(SetOfSensors filtSensors){
        if (filtSensors == null){
            jListSensors.setListData(theSensors);
        }else{
            jListSensors.setListData(filtSensors);
        }
    }
    
    
    
    private void setSelectedSensor(){
        // Get value from jlist (SetOfSensors) and cast to sensor
        selectedSensor = (Sensor)jListSensors.getSelectedValue();
        btnEditSen.setEnabled(true);
        
            btnDelSen.setEnabled(true);
            btnDelSen.setToolTipText(null);
       
    }
    
    
    private void clearSelectedSensor(){
        jListSensors.clearSelection();
        lblNoSenQResultsTxt.setVisible(false);
        selectedSensor = null;
        refreshSensorsJList(null);
        btnEditSen.setEnabled(false);
        btnDelSen.setEnabled(false);
    }
   
    private void serializeSensorsSet(String fLoc){
        try
        {
            FileOutputStream fileOut = 
                    new FileOutputStream(fLoc);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            
            for(Sensor sen : theSensors) {
                /* Function setup for when data is loaded back in, so books  
                can be re-initialized without dups */
            }
            objOut.writeObject(theSensors);
            
            objOut.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fLoc);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        // completely refresh data, if its been saved without quitting
        deserializeSensorsSet(fLoc);
    }

    
    private void deserializeSensorsSet(String fLoc)
    {
        //failsafe
        theSensors.removeAllElements();
        try
        {
            FileInputStream fileIn = new FileInputStream(fLoc);
            ObjectInputStream objIn = new ObjectInputStream (fileIn);

            Object obj = objIn.readObject();
            
            // check read object
            if (obj instanceof Vector)
            {
                SetOfSensors eSensors = (SetOfSensors) obj;
                for(int x = 0; x < eSensors.size(); x++){
                    Sensor eSen = new Sensor(eSensors.elementAt(x).getSensorNumber(),
                            eSensors.elementAt(x).getName(), 
                            eSensors.elementAt(x).getStatus(),
                            eSensors.elementAt(x).getFrequency());
                    theSensors.addSensor(eSen);
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
        jDialogCRUDSenForm = new javax.swing.JDialog();
        lblSenFormTitle = new javax.swing.JLabel();
        btnSenFormCancel = new javax.swing.JButton();
        jSeparatorMemFormHeader = new javax.swing.JSeparator();
        btnSenFormApply = new javax.swing.JButton();
        jTxtSenStatusInput = new javax.swing.JTextPane();
        lblSenNameInputTxt = new javax.swing.JLabel();
        jTxtSenIDInput = new javax.swing.JTextPane();
        lbSenIDInputTxt = new javax.swing.JLabel();
        lblSenFrequencyInputTxt1 = new javax.swing.JLabel();
        lbSenStatusInputTxt2 = new javax.swing.JLabel();
        jTxtSenNameInput = new javax.swing.JTextPane();
        jTxtSenFrequencyInput = new javax.swing.JTextPane();
        jDialogSenSearch = new javax.swing.JDialog();
        lblSenSearchTitle = new javax.swing.JLabel();
        btnSenSearch = new javax.swing.JButton();
        jSeparatorMemSearchHeader = new javax.swing.JSeparator();
        lblSenSearchFilterTxt = new javax.swing.JLabel();
        lblSenSearchInputTxt = new javax.swing.JLabel();
        jComboSenFilter = new javax.swing.JComboBox();
        jTxtSenSearchInput = new javax.swing.JTextField();
        jPanelContainer = new javax.swing.JPanel();
        btnSaveChanges = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        lblChangesSaved = new javax.swing.JLabel();
        btnAddSen = new javax.swing.JButton();
        btnEditSen = new javax.swing.JButton();
        btnDelSen = new javax.swing.JButton();
        btnQuerySensors = new javax.swing.JButton();
        lblNoSenQResultsTxt = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListSensors = new javax.swing.JList();
        lblSensorsJlistHead = new javax.swing.JLabel();
        btnResetSensFilter = new javax.swing.JButton();

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

        jDialogCRUDSenForm.setLocationByPlatform(true);
        jDialogCRUDSenForm.setMinimumSize(new java.awt.Dimension(460, 226));

        lblSenFormTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSenFormTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSenFormTitle.setText("Sensor");

        btnSenFormCancel.setText("Cancel");
        btnSenFormCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSenFormCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSenFormCancelActionPerformed(evt);
            }
        });

        btnSenFormApply.setText("Add");
        btnSenFormApply.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSenFormApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSenFormApplyActionPerformed(evt);
            }
        });

        jTxtSenStatusInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtSenStatusInput.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        lblSenNameInputTxt.setText("Sensor Name");

        jTxtSenIDInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtSenIDInput.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTxtSenIDInput.setText("number");
        jTxtSenIDInput.setToolTipText("");
        jTxtSenIDInput.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTxtSenIDInput.setEnabled(false);
        jTxtSenIDInput.setFocusable(false);

        lbSenIDInputTxt.setText("Sensor ID");

        lblSenFrequencyInputTxt1.setText("Frequency");

        lbSenStatusInputTxt2.setText("Status");

        jTxtSenNameInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtSenNameInput.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jTxtSenFrequencyInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTxtSenFrequencyInput.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        javax.swing.GroupLayout jDialogCRUDSenFormLayout = new javax.swing.GroupLayout(jDialogCRUDSenForm.getContentPane());
        jDialogCRUDSenForm.getContentPane().setLayout(jDialogCRUDSenFormLayout);
        jDialogCRUDSenFormLayout.setHorizontalGroup(
            jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparatorMemFormHeader)
                    .addComponent(lblSenFormTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                                .addComponent(btnSenFormCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSenFormApply, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                                .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                                        .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbSenIDInputTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSenNameInputTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtSenIDInput, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTxtSenNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                                        .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbSenStatusInputTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                            .addComponent(lblSenFrequencyInputTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtSenStatusInput, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTxtSenFrequencyInput, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 16, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jDialogCRUDSenFormLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbSenIDInputTxt, lbSenStatusInputTxt2, lblSenFrequencyInputTxt1, lblSenNameInputTxt});

        jDialogCRUDSenFormLayout.setVerticalGroup(
            jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSenFormTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorMemFormHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtSenIDInput)
                    .addComponent(lbSenIDInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSenNameInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtSenNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbSenStatusInputTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtSenStatusInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                        .addComponent(lblSenFrequencyInputTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jDialogCRUDSenFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSenFormCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSenFormApply, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(jDialogCRUDSenFormLayout.createSequentialGroup()
                        .addComponent(jTxtSenFrequencyInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jDialogCRUDSenFormLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lbSenIDInputTxt, lbSenStatusInputTxt2, lblSenFrequencyInputTxt1, lblSenNameInputTxt});

        jDialogSenSearch.setLocationByPlatform(true);
        jDialogSenSearch.setMinimumSize(new java.awt.Dimension(470, 257));
        jDialogSenSearch.setResizable(false);
        jDialogSenSearch.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jDialogSenSearchWindowActivated(evt);
            }
        });

        lblSenSearchTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSenSearchTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSenSearchTitle.setText("Sensor Search");

        btnSenSearch.setText("Search");
        btnSenSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSenSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSenSearchActionPerformed(evt);
            }
        });

        lblSenSearchFilterTxt.setText("Search By");

        lblSenSearchInputTxt.setText("Search Value");

        jComboSenFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "Name" }));

        jTxtSenSearchInput.setMinimumSize(new java.awt.Dimension(72, 26));
        jTxtSenSearchInput.setName(""); // NOI18N
        jTxtSenSearchInput.setPreferredSize(new java.awt.Dimension(72, 26));

        javax.swing.GroupLayout jDialogSenSearchLayout = new javax.swing.GroupLayout(jDialogSenSearch.getContentPane());
        jDialogSenSearch.getContentPane().setLayout(jDialogSenSearchLayout);
        jDialogSenSearchLayout.setHorizontalGroup(
            jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogSenSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparatorMemSearchHeader)
                    .addComponent(lblSenSearchTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogSenSearchLayout.createSequentialGroup()
                .addGap(0, 25, Short.MAX_VALUE)
                .addGroup(jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSenSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialogSenSearchLayout.createSequentialGroup()
                        .addGroup(jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSenSearchFilterTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSenSearchInputTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboSenFilter, 0, 236, Short.MAX_VALUE)
                            .addComponent(jTxtSenSearchInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        jDialogSenSearchLayout.setVerticalGroup(
            jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogSenSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSenSearchTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorMemSearchHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenSearchFilterTxt)
                    .addComponent(jComboSenFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jDialogSenSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenSearchInputTxt)
                    .addComponent(jTxtSenSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnSenSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JoBas Library Application");
        setLocationByPlatform(true);
        setResizable(false);

        btnSaveChanges.setBackground(new java.awt.Color(153, 255, 153));
        btnSaveChanges.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSaveChanges.setText("Save Changes");
        btnSaveChanges.setActionCommand("queryMembersNo");
        btnSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChangesActionPerformed(evt);
            }
        });

        btnQuit.setBackground(new java.awt.Color(255, 102, 102));
        btnQuit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnQuit.setText("Quit");
        btnQuit.setActionCommand("queryMembersNo");
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });

        lblChangesSaved.setForeground(new java.awt.Color(0, 204, 0));
        lblChangesSaved.setText("Changes Saved");

        btnAddSen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddSen.setText("Add Sensor");
        btnAddSen.setToolTipText("");
        btnAddSen.setActionCommand("queryMembersNo");
        btnAddSen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSenActionPerformed(evt);
            }
        });

        btnEditSen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditSen.setText("Edit Sensor");
        btnEditSen.setToolTipText("");
        btnEditSen.setActionCommand("queryMembersNo");
        btnEditSen.setEnabled(false);
        btnEditSen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSenActionPerformed(evt);
            }
        });

        btnDelSen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDelSen.setText("Del Sensor");
        btnDelSen.setToolTipText("");
        btnDelSen.setActionCommand("queryMembersNo");
        btnDelSen.setEnabled(false);
        btnDelSen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelSenActionPerformed(evt);
            }
        });

        btnQuerySensors.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnQuerySensors.setText("Query Sensors");
        btnQuerySensors.setActionCommand("queryMembersNo");
        btnQuerySensors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuerySensorsActionPerformed(evt);
            }
        });

        lblNoSenQResultsTxt.setForeground(new java.awt.Color(255, 0, 0));
        lblNoSenQResultsTxt.setText("No Results returned!");

        jListSensors.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListSensors.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListSensors.setFocusable(false);
        jListSensors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jListSensorsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jListSensors);

        lblSensorsJlistHead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSensorsJlistHead.setText("Sensors");

        btnResetSensFilter.setBackground(new java.awt.Color(255, 102, 102));
        btnResetSensFilter.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnResetSensFilter.setText("clear filter");
        btnResetSensFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btnResetSensFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSensFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelContainerLayout = new javax.swing.GroupLayout(jPanelContainer);
        jPanelContainer.setLayout(jPanelContainerLayout);
        jPanelContainerLayout.setHorizontalGroup(
            jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelContainerLayout.createSequentialGroup()
                        .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelContainerLayout.createSequentialGroup()
                                .addComponent(lblSensorsJlistHead)
                                .addGap(153, 153, 153)
                                .addComponent(btnResetSensFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddSen)
                            .addComponent(btnEditSen, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDelSen, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelContainerLayout.createSequentialGroup()
                        .addComponent(btnQuerySensors, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(lblNoSenQResultsTxt))
                    .addGroup(jPanelContainerLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblChangesSaved)
                        .addGap(40, 40, 40)
                        .addComponent(btnSaveChanges)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanelContainerLayout.setVerticalGroup(
            jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetSensFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSensorsJlistHead))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanelContainerLayout.createSequentialGroup()
                        .addComponent(btnAddSen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnEditSen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(btnDelSen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuerySensors, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoSenQResultsTxt))
                .addGap(18, 18, 18)
                .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblChangesSaved)
                    .addGroup(jPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        showQuitConfGUI();
    }//GEN-LAST:event_btnQuitActionPerformed

    private void btnQuitConfJQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitConfJQActionPerformed
        // TODO add your handling code here:
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

    private void btnSenFormApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSenFormApplyActionPerformed
        // TODO add your handling code here:
        String btnTxt = btnSenFormApply.getText();
        String senName = new String();
        String senStatus = new String();
        String senFrequency = new String();

        senName = jTxtSenNameInput.getText();
        senStatus = jTxtSenStatusInput.getText();
        senFrequency = jTxtSenFrequencyInput.getText();
        if(!btnTxt.isEmpty() && !senName.trim().isEmpty() && !senStatus.trim().isEmpty() && !senFrequency.trim().isEmpty()){
            if (btnTxt.equals("Add")){ // btn txt == add
                Sensor sensor = new Sensor(theSensors.lastElement().getSensorNumber()+1,senName, senStatus,senFrequency);
                theSensors.addSensor(sensor);
            }else if (btnTxt.equals("Save")){ // btn txt == edit
                selectedSensor.setName(senName);
                selectedSensor.setStatus(senStatus);
                selectedSensor.setFrequency(senFrequency);
            }else{ // btn txt == delete
                // Need to stop this happening if user has books on loan
                theSensors.removeSensor(selectedSensor);
            }
        
            setGUITxt();

            jDialogCRUDSenForm.setVisible(false);
            clearSelectedSensor();
            refreshSensorsJList(null);
        }
    }//GEN-LAST:event_btnSenFormApplyActionPerformed

    private void btnSenFormCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSenFormCancelActionPerformed
        // TODO add your handling code here:
        jDialogCRUDSenForm.setVisible(false);
    }//GEN-LAST:event_btnSenFormCancelActionPerformed

    private void btnSenSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSenSearchActionPerformed
        // TODO add your handling code here:
        filterSensors();        
    }//GEN-LAST:event_btnSenSearchActionPerformed

    private void jDialogSenSearchWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogSenSearchWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_jDialogSenSearchWindowActivated

    private void btnResetSensFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSensFilterActionPerformed
        // TODO add your handling code here:
        hideFilterBtn(this.btnResetSensFilter);
        clearSelectedSensor();
    }//GEN-LAST:event_btnResetSensFilterActionPerformed

    private void btnEditSenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSenActionPerformed
        // TODO add your handling code here:

        showSenCRUDGUI("edit");
    }//GEN-LAST:event_btnEditSenActionPerformed

    private void btnAddSenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSenActionPerformed
        // TODO add your handling code here:
        showSenCRUDGUI("new");
    }//GEN-LAST:event_btnAddSenActionPerformed

    private void btnDelSenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelSenActionPerformed
        // TODO add your handling code here:

        // HERE
        showSenCRUDGUI("delete");
    }//GEN-LAST:event_btnDelSenActionPerformed

    private void jListSensorsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListSensorsMousePressed
        // TODO add your handling code here:
        setSelectedSensor();
    }//GEN-LAST:event_jListSensorsMousePressed

    private void btnQuerySensorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuerySensorsActionPerformed
        showSenSearchGUI();
    }//GEN-LAST:event_btnQuerySensorsActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SensorGUI().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSen;
    private javax.swing.JButton btnDelSen;
    private javax.swing.JButton btnEditSen;
    private javax.swing.JButton btnQuerySensors;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnQuitConfJQ;
    private javax.swing.JButton btnQuitConfNo;
    private javax.swing.JButton btnQuitConfSQ;
    private javax.swing.JButton btnQuitConfYes;
    private javax.swing.JButton btnResetSensFilter;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnSenFormApply;
    private javax.swing.JButton btnSenFormCancel;
    private javax.swing.JButton btnSenSearch;
    private javax.swing.JComboBox jComboSenFilter;
    private javax.swing.JDialog jDialogCRUDSenForm;
    private javax.swing.JDialog jDialogDelConf;
    private javax.swing.JDialog jDialogQuitConf;
    private javax.swing.JDialog jDialogSenSearch;
    private javax.swing.JList jListSensors;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparatorDelConfHeader;
    private javax.swing.JSeparator jSeparatorMemFormHeader;
    private javax.swing.JSeparator jSeparatorMemSearchHeader;
    private javax.swing.JSeparator jSeparatorQuitConfHeader;
    private javax.swing.JTextPane jTxtSenFrequencyInput;
    private javax.swing.JTextPane jTxtSenIDInput;
    private javax.swing.JTextPane jTxtSenNameInput;
    private javax.swing.JTextField jTxtSenSearchInput;
    private javax.swing.JTextPane jTxtSenStatusInput;
    private javax.swing.JLabel lbSenIDInputTxt;
    private javax.swing.JLabel lbSenStatusInputTxt2;
    private javax.swing.JLabel lblChangesSaved;
    private javax.swing.JLabel lblDelConfTitle;
    private javax.swing.JLabel lblNoSenQResultsTxt;
    private javax.swing.JLabel lblQuitConfTitle;
    private javax.swing.JLabel lblSenFormTitle;
    private javax.swing.JLabel lblSenFrequencyInputTxt1;
    private javax.swing.JLabel lblSenNameInputTxt;
    private javax.swing.JLabel lblSenSearchFilterTxt;
    private javax.swing.JLabel lblSenSearchInputTxt;
    private javax.swing.JLabel lblSenSearchTitle;
    private javax.swing.JLabel lblSensorsJlistHead;
    // End of variables declaration//GEN-END:variables

}
