package com.dictiographer.view.dialogs.grammar;

import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.ThreadContext;
import entry.Grammar;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;


/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public abstract class GrammarDialog extends AbstractContainerRenderer {
    public JPanel cardPanel;
    public Bindable parent;
    private String cardName;


    public GrammarDialog(Bindable p, Grammar grammar, String cn) {
        parent = p;
        cardName = cn;
        String lang = MyThreadLocal.get().getLang();
        String grammarDialogDescriptor = "descriptors/" + lang + "/GrammarDialog" + cardName.toUpperCase() + ".xml";
        if (getClass().getClassLoader().getResourceAsStream(grammarDialogDescriptor) == null) {
            return;
        }
        init(grammarDialogDescriptor);

//        CardLayout cl = (CardLayout) (cardPanel.getLayout());
//        cl.show(cardPanel, cardName);
        if (grammar != null) {
            setData(grammar);
        }

        ((JDialog) container).pack();
        container.setVisible(true);
    }

    /**
     * Should call method implemented by parent, swixml needs this action field to be initialized before binding
     */
    public Action propagateAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                propagate();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            parent.setData(getData(null));
            ((JDialog) container).dispose();
        }
    };

    public void propagate() {
        throw new NotImplementedException();
    }


    public static void main(String[] args) {
        ThreadContext context = new ThreadContext();
        context.setLang("by");
        MyThreadLocal.set(context);
        new GrammarDialogLauncher(null, null, "pry");
    }


    protected void set(JTextField tf, String s) {
        if (tf != null && s != null) {
            tf.setText(s);
        }
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
                    data.getClass().getDeclaredField(s);
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


}
