package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;


public class Controller extends TreeMap implements Initializable {
    @FXML
    private Label cpu_usage = new Label();

    @FXML
    private Label ram_usage = new Label();

    @FXML
    private Label disk_space = new Label();

    @FXML
    private ToggleButton on_off_btn = new ToggleButton();

    @FXML
    private LineChart<Number, Number> usageChart;

    private static final int ramCalc = 1048576;
    private static final int cpuCalc = 100;
    private static Integer counter = 0;
    private static Resources res_object = new Resources();
    private XYChart.Series<Number, Number> seriesCpu = new XYChart.Series<>();
    private XYChart.Series<Number, Number> seriesRam = new XYChart.Series<>();
    private boolean startState = false;
    private Timeline timeline;
    private Map<Calendar,Resources> sortedResuources = new TreeMap<>();
    private Calendar currentDate;
    private void refreshData() {

        timeline = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            if (!startState) {
                timeline.stop();
            }

            res_object.getInfo();
            cpu_usage.setText(res_object.convertToString(res_object.getCpuLoad(),cpuCalc));
            ram_usage.setText(res_object.convertToString(res_object.getRamLoad(),ramCalc) + " / " + res_object.convertToString(res_object.getMaxRam(),ramCalc));
            disk_space.setText(res_object.convertToString(res_object.getFreeDisk(),ramCalc) + " MB");
            chartDraw(res_object);
            currentDate = Calendar.getInstance();
            //Thread worker = new Thread(runnable);
            //worker.start();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 9999; ++i) {
                System.out.println(i);
            }
        }
    };

    private void chartDraw(Resources res_object) {
        seriesCpu.getData().add(new XYChart.Data<>(counter, res_object.getCpuLoad() * 100));
        seriesRam.getData().add(new XYChart.Data<>(counter, res_object.getRamPercent()));
        counter++;
    }

    public void startStopBtn() {

        if (on_off_btn.isSelected()) {
            startState = true;
            refreshData();
        } else
            startState = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usageChart.getData().add(seriesCpu);
        usageChart.getData().add(seriesRam);
    }



}
