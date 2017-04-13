package io.manasobi.view.log;

import ch.qos.logback.core.AppenderBase;
import io.manasobi.utils.DateUtils;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by Administrator on 2015-11-03.
 */
public class LogbackLogAppender extends AppenderBase<Object> {

    private static volatile TextArea textArea = null;

    public static void setTextArea(final TextArea textArea) {
        LogbackLogAppender.textArea = textArea;
    }


    @Override
    protected void append(Object eventObject) {

        String timestamp = DateUtils.getCurrentTimestampAsString();
        timestamp = timestamp.length() == 22 ? timestamp + "0" : timestamp;

        final String message =  timestamp + " " + eventObject.toString() + "\n";

        // Append formatted message to text area using the Thread.
        try {
            Platform.runLater(() -> {
                try {
                    if (textArea != null) {
                        if (textArea.getText().length() == 0) {
                            textArea.setText(message);
                        } else {
                            textArea.selectEnd();
                            textArea.insertText(textArea.getText().length(),
                                    message);
                        }
                    }
                } catch (final Throwable t) {
                    System.out.println("Unable to append log to text area: "
                            + t.getMessage());
                }
            });
        } catch (final IllegalStateException e) {
            // ignore case when the platform hasn't yet been iniitialized
        }
    }
}
