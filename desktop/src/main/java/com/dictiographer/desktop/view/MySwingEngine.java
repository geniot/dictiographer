package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.panels.FormContentPanel;
import org.swixml.Localizer;
import org.swixml.SwingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;


public abstract class MySwingEngine extends SwingEngine implements Bindable {
    public JPanel mainPanel;
    public Container container;

    public void init(String desc) {
        try {
            getTaglib().registerTag("dndtabbedpane", DnDTabbedPane.class);
            getTaglib().registerTag("layeredpane", JLayeredPane.class);
            container = render(desc);

            mainPanel.registerKeyboardAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCancel();
                }
            }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel() {
        getClosestWindow(getMainPanel()).dispose();
    }

    @Override
    public abstract void setData(Object d);

    @Override
    public abstract Object getData(Object d);

    @Override
    public boolean isEmpty() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true); // You might want to set modifier to public first.
                if (field.getDeclaringClass().equals(JCheckBox.class)) {
                    JCheckBox checkBox = (JCheckBox) field.get(this);
                    if (checkBox != null && checkBox.isSelected()) return false;
                } else if (field.getDeclaringClass().equals(JTextField.class)) {
                    JTextField textField = (JTextField) field.get(this);
                    if (textField != null && !textField.getText().trim().equals("")) return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }



    public Action cancelAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            getClosestWindow(getMainPanel()).dispose();
        }
    };


    public Localizer getLocalizer() {
        return MyThreadLocal.get().getLocalizer();
    }

    protected Window getClosestWindow(Container p) {
        while (p != null) {
            if (p instanceof Window) {
                return (Window) p;
            }
            p = p.getParent();
        }
        return null;
    }



    protected void set(JTextField tf, String s) {
        if (tf != null && s != null) {
            tf.setText(s);
        }
    }

    public boolean implementsInterface(Object object, Class interf){
        return interf.isInstance(object);
    }

    protected void set(JCheckBox checkBox, Boolean val) {
        if (checkBox != null && val != null) {
            checkBox.setSelected(val.booleanValue());
        }
    }

    protected void set(JTextField textField, Object data, String s) {
        try {
            if (textField != null && !textField.getText().trim().equals("") && data != null && s != null) {
                Field f = null;
                try {
                    f = data.getClass().getDeclaredField(s);
                } catch (NoSuchFieldException e) {
                    return;
                }
                f.setAccessible(true);
                f.set(data, textField.getText().trim());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void set(JCheckBox checkBox, Object data, String s) {
        try {
            if (checkBox != null && checkBox.isSelected() && data != null && s != null) {
                Field f = null;
                try {
                    f = data.getClass().getDeclaredField(s);
                } catch (NoSuchFieldException e) {
                    return;
                }
                f.setAccessible(true);
                f.set(data, checkBox.isSelected());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected JComboBox getPosComboBox() throws Exception {
        Container c = container;
        while (c != null) {
            try {
                c = c.getParent();
                if (c == null) break;
                if (c instanceof FormContentPanel) {
                    Object tmp = ((FormContentPanel) c).getForm();
                    JComboBox f = (JComboBox) getInheritedPrivateFieldValue(tmp, tmp.getClass(), "posComboBox");
                    if (f != null) {
                        return f;
                    }
                } else {
                    Field f = c.getClass().getDeclaredField("posComboBox");
                    if (f.get(c) != null) {
                        return (JComboBox) f.get(c);
                    }
                }
            } catch (NoSuchFieldException e) {
            }
        }
        throw new Exception("Couldn't find posComboBox in the hierarchy");
    }

    private Object getInheritedPrivateFieldValue(Object thisObj, Class<?> type, String fieldName) throws IllegalAccessException {
        Class<?> i = type;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                if (!field.isSynthetic() && field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field.get(thisObj);
                }
            }
            i = i.getSuperclass();
        }

        return null;
    }
}