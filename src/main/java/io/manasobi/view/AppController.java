package io.manasobi.view;

import de.felixroske.jfxsupport.FXMLController;
import io.manasobi.domain.PayloadTaskHandler;
import io.manasobi.kafka.KafkaMessageProducer;
import io.manasobi.view.log.LogbackLogAppender;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.OutputStream;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Slf4j()
@FXMLController
public class AppController {

    @FXML
    private ChoiceBox size;

    @FXML
    private TextArea console;

    @FXML
    private Button execBtn;

    @FXML
    private TextField zookeeperUrl;

    @Autowired
    private KafkaMessageProducer producer;

    @Autowired
    private PayloadTaskHandler taskHandler;

    public void handleExecuteButtonAction() {

        LogbackLogAppender.setTextArea(console);

        /*Alert startAlert = new Alert(Alert.AlertType.INFORMATION);
        startAlert.setTitle("Anypoint Kafka Producer ver-1.0.3");
        startAlert.setHeaderText("작업이 시작되었습니다.");
        startAlert.showAndWait();*/


        log.info("작업이 시작되었습니다.");

        int sizeParam = Integer.valueOf(size.getSelectionModel().getSelectedItem().toString().replaceAll(",", ""));
        sizeParam = sizeParam / 10;

        producer.process(console, sizeParam);

        /*console.getParent().getScene().setCursor(Cursor.WAIT);
        Platform.runLater(() -> console.getParent().getScene().setCursor(Cursor.WAIT));*/

        /*

        boolean enableTruncateTableJob = isTruncateTable.isSelected();

        */



        //

        //Platform.runLater(() -> console.getParent().getScene().setCursor(Cursor.DEFAULT));
        //console.getParent().getScene().setCursor(Cursor.DEFAULT);

    }

    public void handleMouseEnteredAction() { execBtn.setCursor(Cursor.HAND); }

    public void handleMouseExitAction() {
        execBtn.setCursor(Cursor.DEFAULT);
    }

}
