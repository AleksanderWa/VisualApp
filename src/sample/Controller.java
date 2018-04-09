package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;

public class Controller {
    @FXML
    private Label cpu_usage = new Label();

    @FXML
    private Label ram_usage = new Label();

    @FXML
    private ToggleButton on_off_btn;

    @FXML
    private LineChart<String,Number> usageChart;

    private static Resources res_object = new Resources();

    public void refreshData() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            res_object.getInfo();
            cpu_usage.setText(convertToString(res_object.getCpuLoad()));
            ram_usage.setText(convertToString(res_object.getRamLoad()) + " / " + convertToString(res_object.getMaxRam()));
            XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
            series.getData().add(new XYChart.Data<String, Number>("CPU",res_object.getCpuLoad()*100));
            usageChart.getData().add(series);

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private String convertToString(double usage) {
        double temp = usage * 100;
        return String.format("%1.2f", temp);
    }

    private String convertToString(long usage) {
        Long temp = usage / 1048576;
        return temp.toString();
    }
}
