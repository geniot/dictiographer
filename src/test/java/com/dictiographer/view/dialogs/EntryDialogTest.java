package com.dictiographer.view.dialogs;

import com.dictiographer.model.Constants;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.BindableAdapter;
import entry.EntryObjectModel;
import org.uispec4j.Panel;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import javax.swing.*;


/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 21/07/14
 */
public class EntryDialogTest extends AbstractTestCase {
    public void testHeadwordRequired() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    Bindable b = new BindableAdapter();
                    updateThread();

                    EntryDialog entryDialog = new EntryDialog(null, b, null, Constants.NEW_ACTION);
                    Panel panel = new Panel(entryDialog.entryDialogPanel.getMainPanel());
                    assertEquals(messageSource.getMessage("title.new", null, locale), entryDialog.getTitle());

                    WindowInterceptor.init(panel.getButton(messageSource.getMessage("button.save", null, locale)).triggerClick())
                            .process(new WindowHandler() {
                                public Trigger process(Window dialog) {
                                    JOptionPane pane = (JOptionPane) dialog.getInternalAwtContainer().getComponent(0);
                                    assertEquals(messageSource.getMessage("ERROR_HEADWORD_REQUIRED", null, locale), pane.getMessage().toString());
                                    return dialog.getButton("OK").triggerClick();
                                }
                            })
                            .run();
                }
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    public void testSimpleEntry() {
        try {
            final String testHeadword = "some text";

            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {

                    Bindable b = new BindableAdapter() {
                        @Override
                        public void setData(Object data) {
                            EntryObjectModel eom = (EntryObjectModel) data;
                            assertEquals(eom.getHeadword(), testHeadword);
                        }
                    };
                    updateThread();

                    EntryDialog entryDialog = new EntryDialog(null, b, null, Constants.NEW_ACTION);
                    Panel panel = new Panel(entryDialog.entryDialogPanel.getMainPanel());

                    assertEquals("", panel.findSwingComponent(JTextField.class, "headword").getText());
                    panel.findSwingComponent(JTextField.class, "headword").setText(testHeadword);

                    panel.getButton(messageSource.getMessage("button.save", null, locale)).click();
                }
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Override
    public String getLang() {
        return "by";
    }
}
