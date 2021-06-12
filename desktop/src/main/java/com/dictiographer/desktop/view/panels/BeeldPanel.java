package com.dictiographer.desktop.view.panels;

import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.entry.EntryImage;
import com.dictiographer.desktop.model.entry.ImageLink;
import com.dictiographer.desktop.view.ImageLinkLabel;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.shared.model.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class BeeldPanel extends MySwingEngine {
    JFileChooser fc;
    JPanel contentPanel;
    JLayeredPane layeredPane;
    JSplitPane splitPane;
    JList anchorsList;
    JTextField anchorTextField;
    JButton addButton;
    JButton deleteButton;
    JButton upButton;
    JButton downButton;


    BufferedImage bufferedImage;
    String fileExt;


    JLabel imageLabel;
    boolean drag;


    public BeeldPanel() {
        init("descriptors/BeeldPanel.xml");
        anchorsList.setModel(new DefaultListModel());

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(390, 400));
        contentPanel.add(layeredPane);
    }

    public Action addAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (!anchorTextField.getText().trim().equals("")) {
                addImageLink(0, 0, anchorTextField.getText().trim());
            }
        }
    };

    private void addImageLink(int xoffset, int yoffset, String text) {
        Integer num = ((DefaultListModel) anchorsList.getModel()).size() + 1;
        final ImageLinkLabel imageLinkLabel = genAnchor(num);
        imageLinkLabel.setLinkText(text == null ? "" : text);
        if (((DefaultListModel) anchorsList.getModel()).contains(imageLinkLabel)) {
            return;
        }

        ((DefaultListModel) anchorsList.getModel()).addElement(imageLinkLabel);
        anchorsList.setSelectedValue(imageLinkLabel, true);
        anchorTextField.setText("");

        imageLinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource() == imageLinkLabel) {
                    drag = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drag = false;
            }
        });
        imageLinkLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drag == true) {
                    ImageLinkLabel imageLinkLabel = (ImageLinkLabel) e.getSource();
                    imageLinkLabel.setLocation(imageLinkLabel.getX() + e.getX() - 10, imageLinkLabel.getY() + e.getY() - 10);
                }
            }
        });

        imageLinkLabel.setLocation(xoffset, yoffset);
        layeredPane.add(imageLinkLabel, new Integer(num + 100));
    }

    public Action deleteAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (anchorsList.getSelectedValue() != null) {
                ImageLinkLabel selEl = (ImageLinkLabel) anchorsList.getSelectedValue();
                int selInd = anchorsList.getSelectedIndex();
                ((DefaultListModel) anchorsList.getModel()).removeElement(selEl);
                layeredPane.remove(selEl);
                if (((DefaultListModel) anchorsList.getModel()).size() > 0) {
                    selInd = selInd >= ((DefaultListModel) anchorsList.getModel()).size() ? ((DefaultListModel) anchorsList.getModel()).size() - 1 : selInd;
                    selInd = selInd < 0 ? 0 : selInd;
                    anchorsList.setSelectedIndex(selInd);
                }
                updateOrder();
                splitPane.setDividerLocation(splitPane.getDividerLocation());
            }
        }
    };


    public Action upAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int indexOfSelected = anchorsList.getSelectedIndex();
            swapElements(indexOfSelected, indexOfSelected - 1);
            indexOfSelected = indexOfSelected - 1;
            anchorsList.setSelectedIndex(indexOfSelected);
            anchorsList.updateUI();
        }
    };
    public Action downAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int indexOfSelected = anchorsList.getSelectedIndex();
            swapElements(indexOfSelected, indexOfSelected + 1);
            indexOfSelected = indexOfSelected + 1;
            anchorsList.setSelectedIndex(indexOfSelected);
            anchorsList.updateUI();
        }
    };

    private void swapElements(int pos1, int pos2) {
        try {
            ImageLinkLabel tmp = (ImageLinkLabel) ((DefaultListModel) anchorsList.getModel()).get(pos1);
            ((DefaultListModel) anchorsList.getModel()).set(pos1, ((DefaultListModel) anchorsList.getModel()).get(pos2));
            ((DefaultListModel) anchorsList.getModel()).set(pos2, tmp);

            updateOrder();

        } catch (ArrayIndexOutOfBoundsException e) {
            //keep silent
        }
    }

    private void updateOrder() {
        for (int i = 0; i < ((DefaultListModel) anchorsList.getModel()).size(); i++) {
            ImageLinkLabel el = (ImageLinkLabel) ((DefaultListModel) anchorsList.getModel()).get(i);
            el.setText(String.valueOf(i + 1));
        }
    }

    public Action chooseImageAction = new AbstractAction() {
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
                    BufferedImage img = ImageIO.read(file);
                    setImage(img);


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void setImage(BufferedImage img) {
        bufferedImage = img;
        ImageIcon ii = new ImageIcon(img);
        if (imageLabel != null) {
            layeredPane.remove(imageLabel);
        }
        Dimension dim = new Dimension(ii.getIconWidth(), ii.getIconHeight());

        imageLabel = new JLabel(ii);

        imageLabel.setPreferredSize(dim);
        imageLabel.setSize(dim);
        imageLabel.setMaximumSize(dim);
        imageLabel.setMinimumSize(dim);

        layeredPane.add(imageLabel, new Integer(100));
        imageLabel.setLocation(0, 0);

        layeredPane.setPreferredSize(dim);
        layeredPane.setSize(dim);
        layeredPane.setMaximumSize(dim);
        layeredPane.setMinimumSize(dim);

        addButton.setEnabled(true);
        deleteButton.setEnabled(true);
        upButton.setEnabled(true);
        downButton.setEnabled(true);

        splitPane.setDividerLocation(splitPane.getDividerLocation());
    }

    private ImageLinkLabel genAnchor(int num) {
        ImageLinkLabel anchor = new ImageLinkLabel();
        anchor.setSize(new Dimension(20, 20));
        anchor.setPreferredSize(new Dimension(20, 20));
        anchor.setMaximumSize(new Dimension(20, 20));
        anchor.setMinimumSize(new Dimension(20, 20));
        anchor.setOpaque(true);
        anchor.setHorizontalAlignment(SwingConstants.CENTER);
        anchor.setBackground(Color.black);
        anchor.setForeground(Color.white);
        anchor.setText(String.valueOf(num));
        return anchor;
    }


    @Override
    public void setData(Object inputObject) {
        if (inputObject == null) {
            return;
        }
        EntryImage data = (EntryImage) inputObject;
        if (data.getImage() != null) {
            setImage(Utils.bytesToBufferedImage(data.getImage()));
            fileExt = data.getExt();
            if (data.getImageLinks() != null) {
                for (ImageLink il : data.getImageLinks()) {
                    addImageLink(il.getXoffset(), il.getYoffset(), il.getText());
                }
            }
        }
    }

    @Override
    public Object getData(Object d) {
        EntryImage data = (EntryImage) d;
        ArrayList<ImageLink> al = new ArrayList<ImageLink>();
        for (int i = 0; i < anchorsList.getModel().getSize(); i++) {
            ImageLinkLabel ill = (ImageLinkLabel) anchorsList.getModel().getElementAt(i);
            ImageLink il = new ImageLink();
            il.setText(ill.getLinkText());
            il.setXoffset(ill.getX());
            il.setYoffset(ill.getY());
            al.add(il);
        }
        if (al.size() > 0) {
            data.setImageLinks(al.toArray(new ImageLink[al.size()]));
        }
        if (bufferedImage != null) {
            data.setExt(fileExt);
            data.setImage(Utils.bufferedImageToBytes(fileExt, bufferedImage));
            CRC32 crc32 = new CRC32();
            crc32.update(data.getImage());
            data.setChecksum(crc32.getValue());
        }
        return data;
    }

    @Override
    public boolean isEmpty() {
        return bufferedImage == null && anchorsList.getModel().getSize() == 0;
    }

}
