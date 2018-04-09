package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import java.lang.management.ManagementFactory;

public class Controller {
    @FXML
    private Label cpu_usage = new Label();
    @FXML
    private ToggleButton on_off_btn;
    public Resources res_object = new Resources();

    public void refreshData(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> cpu_usage.setText(getInfo())));
                System.out.println(getInfo());
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private String getInfo(){
        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Double usage = os.getSystemCpuLoad();
        double temp = usage * 100;

        return String.format("%1.2f", temp);
    }
}
