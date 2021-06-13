package com.dictiographer.desktop.view.entry.panels;

import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.entry.EntryImage;
import com.dictiographer.desktop.model.entry.Example;
import com.dictiographer.desktop.model.entry.Translation;
import com.dictiographer.desktop.presenter.AudioFilePlayer;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.dialogs.ImageDialog;
import com.dictiographer.shared.model.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;


public class ExPanel extends MySwingEngine {
    private byte[] audio;
    private String fileExt;

    public JTextField exampleTextField;
    public JTextField gebrTextField;
    public JTextField sourceTextField;
    public JTextField explanationTextField;

    private JFileChooser fc;
    public JButton playButton;
    public JButton deleteButton;

    public EntryImage[] images;

    public Box contentPanel;

    private AudioFilePlayer audioFilePlayer = new AudioFilePlayer();


    public ExPanel() {
        init("descriptors/ExPanel.xml");
    }

    public EntryImage[] getImages() throws Exception {
        return images;
    }

    public Action selectAudioAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                fc = new JFileChooser();
                if (Constants.MEDIA_CURRENT_DIRECTORY != null) {
                    fc.setCurrentDirectory(new File(Constants.MEDIA_CURRENT_DIRECTORY));
                }
                int returnVal = fc.showOpenDialog(container);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    fileExt = Utils.getExtension(file);
                    Constants.MEDIA_CURRENT_DIRECTORY = file.getAbsolutePath();
                    FileInputStream fis = new FileInputStream(fc.getSelectedFile());
                    audio = new byte[fis.available()];
                    fis.read(audio);
                    fis.close();
                    playButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    public Action imageAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                ImageDialog id = new ImageDialog(getClosestWindow(container), ExPanel.this, images);
                id.setVisible(true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

    public Action playAudioAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                Thread audioThread = new Thread() {
                    public void run() {
                        try {
                            audioFilePlayer.play(audio);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                };
                audioThread.start();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action deleteAudionAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            fileExt = null;
            audio = null;
            playButton.setEnabled(false);
            deleteButton.setEnabled(false);
            audioFilePlayer.stop();
        }
    };

    public Action addTranslationAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            addSingleTranslation(null);
        }
    };


    @Override
    public void setData(Object inputObject) {
        if (inputObject == null) return;


        if (inputObject instanceof EntryImage[]) {
            images = ((EntryImage[]) inputObject).length == 0 ? null : (EntryImage[]) inputObject;
            return;
        }

        if (inputObject instanceof Example) {
            Example ex = (Example) inputObject;
            if (ex.getAudio() != null) {
                audio = ex.getAudio();
                fileExt = ex.getExt();
                playButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
            if (ex.getMeta() != null) gebrTextField.setText(ex.getMeta());
            if (ex.getExample() != null) exampleTextField.setText(ex.getExample());
            if (ex.getSource() != null) sourceTextField.setText(ex.getSource());
            if (ex.getExplanation() != null) explanationTextField.setText(ex.getExplanation());
            if (ex.getTranslations() != null) {
                for (Translation tr : ex.getTranslations()) {
                    addSingleTranslation(tr);
                }
            }
            images = ex.getImages();
        }
    }

    protected void addSingleTranslation(Translation tr) {
        SingleTranslationPanel stp = new SingleTranslationPanel(contentPanel);
        if (tr != null) {
            stp.setData(tr);
        }
        FormContentPanel formContentPanel = new FormContentPanel(stp.getMainPanel(), stp);
        contentPanel.add(formContentPanel, contentPanel.getComponentCount());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    @Override
    public Object getData(Object d) {
        Example ex = (Example) d;
        if (audio != null) {
            ex.setAudio(audio);
            CRC32 crc32 = new CRC32();
            crc32.update(ex.getAudio());
            ex.setChecksum(crc32.getValue());
            ex.setExt(fileExt);
        }
        if (!exampleTextField.getText().trim().equals("")) ex.setExample(exampleTextField.getText().trim());
        if (!sourceTextField.getText().trim().equals("")) ex.setSource(sourceTextField.getText().trim());
        if (!gebrTextField.getText().trim().equals("")) ex.setMeta(gebrTextField.getText().trim());
        if (!explanationTextField.getText().trim().equals("")) ex.setExplanation(explanationTextField.getText().trim());
        ArrayList<Translation> translations = new ArrayList<Translation>();
        for (int i = 0; i < contentPanel.getComponentCount(); i++) {
            FormContentPanel fcp = (FormContentPanel) contentPanel.getComponent(i);
            SingleTranslationPanel singleTranslationPanel = (SingleTranslationPanel) fcp.getForm();
            if (singleTranslationPanel.isEmpty()) continue;
            Translation t = new Translation();
            singleTranslationPanel.getData(t);
            translations.add(t);
        }
        ex.setTranslations(translations.size() == 0 ? null : translations.toArray(new Translation[translations.size()]));
        ex.setImages(images);
        return d;
    }

    @Override
    public boolean isEmpty() {
        if (!exampleTextField.getText().trim().equals("")) return false;
        if (!sourceTextField.getText().trim().equals("")) return false;
        if (!explanationTextField.getText().trim().equals("")) return false;
        if (!gebrTextField.getText().trim().equals("")) return false;
        for (int i = 0; i < contentPanel.getComponentCount(); i++) {
            FormContentPanel fcp = (FormContentPanel) contentPanel.getComponent(i);
            SingleTranslationPanel stf = (SingleTranslationPanel) fcp.getForm();
            if (!stf.isEmpty()) return false;
        }
        if (images != null) return false;
        if (audio != null) return false;
        return true;
    }

}
