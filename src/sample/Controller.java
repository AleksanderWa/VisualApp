package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;


public class Controller extends Task<Integer> {
    @FXML
    private Label cpu_usage = new Label();

    @FXML
    private Label ram_usage = new Label();

    @FXML
    private ToggleButton on_off_btn = new ToggleButton();

    @FXML
    private LineChart<Number,Number> usageChart;

    private static Integer counter = 0;
    private static Resources res_object = new Resources();
    private XYChart.Series<Number,Number> seriesCpu = new XYChart.Series<>();
    private XYChart.Series<Number,Number> seriesRam = new XYChart.Series<>();
    private boolean startState = false;


    public void refreshData(){
        usageChart.getData().add(seriesCpu);
        usageChart.getData().add(seriesRam);
        startState = true;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), ev -> {
            if (startState){
               on_off_btn.setDisable(true);
            }
            res_object.getInfo();
            cpu_usage.setText(res_object.convertToString(res_object.getCpuLoad()));
            ram_usage.setText(res_object.convertToString(res_object.getRamLoad()) + " / " + res_object.convertToString(res_object.getMaxRam()));
            chartDraw(res_object);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void chartDraw(Resources res_object){
        seriesCpu.getData().add(new XYChart.Data<>(counter,res_object.getCpuLoad()*100));
        seriesRam.getData().add(new XYChart.Data<>(counter,res_object.getRamPercent()));
        counter++;

    }

    public ToggleButton getOn_off_btn() {
        return on_off_btn;
    }

    @Override
    protected Integer call() throws Exception {

        return null;
    }
}
