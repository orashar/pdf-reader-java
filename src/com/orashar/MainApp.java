package com.orashar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainApp {

    public static void main(final String args[]) {
        JFrame myframe = new JFrame("JToolBar Example");
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        JButton newTool = new JButton("New");
        JButton openTool = new JButton("Open");
        JButton cutTool = new JButton("Cut");
        JButton copyTool = new JButton("Cppy");
        JButton pasteTool = new JButton("Paste");
        toolbar.add(newTool);
        toolbar.addSeparator();
        toolbar.add(openTool);
        toolbar.addSeparator();
        toolbar.add(cutTool);
        toolbar.addSeparator();
        toolbar.add(copyTool);
        toolbar.addSeparator();
        toolbar.add(pasteTool);
        Container contentPane = myframe.getContentPane();

        toolbar.setBounds(0, 100, myframe.getWidth(), 100);
        contentPane.add(toolbar, BorderLayout.NORTH);
        JTextArea textArea = new JTextArea();
        JScrollPane mypane = new JScrollPane(textArea);
        contentPane.add(mypane, BorderLayout.EAST);

        JMenu file, edit, help;
        JMenuItem newFile, openFile, cutEdit, copyEdit, pasteEdit;

        JMenuBar mb=new JMenuBar();
        file=new JMenu("File");
        edit=new JMenu("Edit");
        help=new JMenu("Help");
        newFile=new JMenuItem("New File");
        openFile=new JMenuItem("Open");
        cutEdit=new JMenuItem("Cut");
        copyEdit=new JMenuItem("Copy");
        pasteEdit=new JMenuItem("Paste");
        file.add(newFile); file.add(openFile);
        edit.add(cutEdit); edit.add(copyEdit); edit.add(pasteEdit);
        mb.add(file);mb.add(edit);mb.add(help);

        mb.setBounds(0, 0, myframe.getWidth(), 500);
        myframe.setJMenuBar(mb);

        JTextArea ta = new JTextArea();
        ta.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\n\n2\n3\n4\n5\n6\n7\n8\n9");

        //build a controller
        SwingController controller = new SwingController();

// Build a SwingViewFactory configured with the controller
        SwingViewBuilder factory = new SwingViewBuilder(controller);

// Use the factory to build a JPanel that is pre-configured
//with a complete, active Viewer UI.
        JPanel viewerComponentPanel = factory.buildViewerPanel();

// add copy keyboard command
        ComponentKeyBinding.install(controller, viewerComponentPanel);

// add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));

        JScrollPane scrollPaneTa = new JScrollPane(ta);
        scrollPaneTa.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneTa.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ta.setBounds(30, 200, myframe.getWidth(), myframe.getHeight()-200);

        myframe.add(scrollPaneTa);

        openTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc=new JFileChooser();
                int i=fc.showOpenDialog(myframe);
                if(i==JFileChooser.APPROVE_OPTION){
                    File f=fc.getSelectedFile();
                    String filepath=f.getPath();
                    try{
                        BufferedReader br=new BufferedReader(new FileReader(filepath));
                        String s1="",s2="";
                        while((s1=br.readLine())!=null){
                            System.out.println("Reading a line");
                            s2+=s1+"\n";
                        }
                        ta.setText(s2);
                        br.close();
                    }catch (Exception ex) {ex.printStackTrace();  }
                }
            }
        });

        cutTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ta.setText(ta.getSelectedText());
            }
        });

        myframe.setSize(850, 850);
        myframe.setVisible(true);
    }
}
