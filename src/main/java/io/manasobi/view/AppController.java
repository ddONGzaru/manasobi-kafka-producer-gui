package io.manasobi.view;

import de.felixroske.jfxsupport.FXMLController;
import io.manasobi.domain.PayloadTaskHandler;
import io.manasobi.domain.Point;
import io.manasobi.kafka.KafkaMessageProducer;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by tw.jang on 2017-04-13.
 */
@FXMLController
public class AppController {

    @FXML
    private TextField page;

    @FXML
    private ChoiceBox size;

    @FXML
    private CheckBox isTruncateTable;

    @FXML
    private TextArea console;

    @FXML
    private Button execBtn;

    @FXML
    private TextField zookeeperUrl;

    @FXML
    private TextArea processStatsDesc;

    @Autowired
    private KafkaMessageProducer producer;

    @Autowired
    private PayloadTaskHandler taskHandler;

    public void handleExecuteButtonAction() {

        processStatsDesc.setText("전송할 메시지를 생성합니다...");



        int totalSize = Integer.valueOf(size.getSelectionModel().getSelectedItem().toString().replaceAll(",", ""));

        taskHandler.process(totalSize);

        while (totalSize == taskHandler.count.get()) {

            StringBuilder sb = new StringBuilder();
            sb.append("전송할 메시지를 생성합니다...\n");
            sb.append(taskHandler.count.get());

            processStatsDesc.setText(sb.toString());
        }

        Alert startAlert = new Alert(Alert.AlertType.INFORMATION);
        startAlert.setTitle("Anypoint Kafka Producer ver-1.0.3");
        startAlert.setHeaderText("작업이 시작되었습니다.");
        startAlert.showAndWait();

        /*console.getParent().getScene().setCursor(Cursor.WAIT);
        Platform.runLater(() -> console.getParent().getScene().setCursor(Cursor.WAIT));*/

        /*int pageParam = Integer.valueOf(page.getText());

        int sizeParam = Integer.valueOf(size.getSelectionModel().getSelectedItem().toString().replaceAll(",", ""));
        sizeParam = sizeParam / 10;

        boolean enableTruncateTableJob = isTruncateTable.isSelected();

        */



        //producer.process(console, pageParam, sizeParam, enableTruncateTableJob);

        //Platform.runLater(() -> console.getParent().getScene().setCursor(Cursor.DEFAULT));
        //console.getParent().getScene().setCursor(Cursor.DEFAULT);

    }

    public void handleMouseEnteredAction() { execBtn.setCursor(Cursor.HAND); }

    public void handleMouseExitAction() {
        execBtn.setCursor(Cursor.DEFAULT);
    }

}
