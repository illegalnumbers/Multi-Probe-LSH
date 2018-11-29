package ui;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class LshUi {

    public static final int DEFAULT_WIDTH = 640, DEFAULT_HEIGHT = 480;

    public static final int DEFAULT_NUMBER_OF_HASHFUNCTIONS = 4;
    public static final int DEFAULT_NUMBER_OF_HASHTABLES = 1;
    public static final double DEFAULT_SLOT_WIDTH = 0.1;
    public static final boolean DEFAULT_USE_EIGENVECTORS = false;

    private int numberOfHashFunctions, numberOfHashTables;
    private double slotWidth;
    private boolean useEigenVectorsForHashing;

    public LshUi() {
        this.numberOfHashFunctions = DEFAULT_NUMBER_OF_HASHFUNCTIONS;
        this.numberOfHashTables = DEFAULT_NUMBER_OF_HASHTABLES;
        this.slotWidth = DEFAULT_SLOT_WIDTH;
        this.useEigenVectorsForHashing = DEFAULT_USE_EIGENVECTORS;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Locality Sensitive Hashing - Image Similarity Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        //Create the menu bar.  Make it have a green background.
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(DEFAULT_WIDTH, 20));

        //Create user menus
        JMenu fileMenu, searchMenu, settingsMenu, metricsMenu;
        JMenuItem buildIndexMenuItem, loadIndexMenuItem, exitMenuItem, searchByUrlMenuItem, searchByFileMenuItem, numberOfHashFunctionsMenuItem, numberOfHashTablesMenuItem, slotWidthMenuItem;
        JCheckBoxMenuItem useEigenVectorHashCheckBoxMenuItem;

        //File menu
        fileMenu = new JMenu("File");
        buildIndexMenuItem = new JMenuItem("Build Index");
        buildIndexMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {buildIndex();} });
        fileMenu.add(buildIndexMenuItem);
        loadIndexMenuItem = new JMenuItem("Load Index");
        loadIndexMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {loadIndex();} });
        fileMenu.add(loadIndexMenuItem);
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {System.exit(0);} });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        //Search menu
        searchMenu = new JMenu("Search");
        searchByUrlMenuItem = new JMenuItem("Search  by URL");
        searchByUrlMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {searchByUrl();} });
        searchMenu.add(searchByUrlMenuItem);
        searchByFileMenuItem = new JMenuItem("Search  by Image File");
        searchByFileMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {searchByImageFile();} });
        searchMenu.add(searchByFileMenuItem);
        menuBar.add(searchMenu);

        //Settings menu
        settingsMenu = new JMenu("Settings");
        numberOfHashFunctionsMenuItem = new JMenuItem("Set Number of Hash Functions");
        numberOfHashFunctionsMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {setNumberOfHashFunctions();} });
        settingsMenu.add(numberOfHashFunctionsMenuItem);
        numberOfHashTablesMenuItem = new JMenuItem("Set Number of Hash Tables");
        numberOfHashTablesMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {setNumberOfHashTables();} });
        settingsMenu.add(numberOfHashTablesMenuItem);
        slotWidthMenuItem = new JMenuItem("Set Slot Width");
        slotWidthMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {setSlotWidth();} });
        settingsMenu.add(slotWidthMenuItem);
        useEigenVectorHashCheckBoxMenuItem = new JCheckBoxMenuItem("Hash with Eigen Vectors");
        useEigenVectorHashCheckBoxMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {toggleUseEigenVectors();} });
        settingsMenu.add(useEigenVectorHashCheckBoxMenuItem);
        menuBar.add(settingsMenu);

        //Metrics menu
        metricsMenu = new JMenu("Metrics");
        menuBar.add(metricsMenu);

        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(this.new ImagePanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void buildIndex() {

    }

    private void loadIndex() {

    }

    private void searchByUrl() {

    }

    private void searchByImageFile() {

    }

    private void setNumberOfHashFunctions() {

        int numberOfHashFunctions = 0;
        String hashFunctionsCount = null;
        while(true) {
            hashFunctionsCount = JOptionPane.showInputDialog("Number of Hash Functions: ", this.numberOfHashFunctions);
            if (hashFunctionsCount == null) {
                break;
            }
            try {
                numberOfHashFunctions = Integer.parseInt(hashFunctionsCount);
                this.numberOfHashFunctions = numberOfHashFunctions;
                break;
            } catch (NumberFormatException e) {

            }

        }

    }

    private void setNumberOfHashTables() {

        int numberOfHashTables = 0;
        String hashTablesCount = null;
        while(true) {
            hashTablesCount = JOptionPane.showInputDialog("Number of Hash Tables: ", this.numberOfHashTables);
            if (hashTablesCount == null) {
                break;
            }
            try {
                numberOfHashTables = Integer.parseInt(hashTablesCount);
                this.numberOfHashTables = numberOfHashTables;
                break;
            } catch (NumberFormatException e) {

            }

        }
    }

    private void setSlotWidth() {

        double slotWidth = 0.0;
        String slotWidthString = null;
        while(true) {
            slotWidthString = JOptionPane.showInputDialog("Slot Width: ", this.slotWidth);
            if (slotWidthString == null) {
                break;
            }
            try {
                slotWidth = Double.parseDouble(slotWidthString);
                this.slotWidth = slotWidth;
                break;
            } catch (NumberFormatException e) {

            }

        }

    }

    private void toggleUseEigenVectors() {

        if (this.useEigenVectorsForHashing) {
            this.useEigenVectorsForHashing = false;
        } else {
            this.useEigenVectorsForHashing = true;
        }

    }

    //Panel to show images
    private class ImagePanel extends JPanel {

        private List<URL> imagesToDisplay;

        public ImagePanel() {
            this.imagesToDisplay = new ArrayList<URL>();
            setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        }

        public void setImagesToDisplay(List<URL> imagesToDisplay) {
            this.imagesToDisplay = imagesToDisplay;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                for (URL imageUrl : imagesToDisplay) {
                    super.paintComponent(g);
                    g.drawImage(ImageIO.read(imageUrl), 0, 0, this);
                }
            } catch (IOException e) {
            }
        }

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LshUi().createAndShowGUI();
            }
        });
    }


}
