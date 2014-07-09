package com.dictiographer.view.panels;

import com.dictiographer.controller.DictiographerUtils;
import com.dictiographer.model.Constants;
import com.dictiographer.view.AbstractContainerRenderer;
import entry.EntryImage;
import entry.Example;
import entry.Translation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 11:09 PM
 */
public class ExPanel extends AbstractContainerRenderer {
    private byte[] audio;
    private String fileExt;

    public JTextField exampleTextField;
    public JTextField gebrTextField;
    public JTextField sourceTextField;
    public JTextField explanationTextField;

    private JFileChooser fc;
    public JButton playButton;
    public JButton deleteButton;


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
                    fileExt = DictiographerUtils.getExtension(file);
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
    public Action playAudioAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                File tempFile = File.createTempFile("audio", "." + fileExt);
                tempFile.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(audio);
                fos.close();

//                Player playMP3 = Manager.createPlayer(tempFile.toURI().toURL());
//                playMP3.realize();
//                playMP3.start();
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
        }
    };

    @Override
    public void setData(Object inputObject) {
        if (inputObject == null) return;
        super.setData(inputObject);

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
        ArrayList<Translation> translations = new ArrayList();
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
