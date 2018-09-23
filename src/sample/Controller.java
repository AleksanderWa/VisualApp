package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @FXML
    private ProgressBar cpu_indicator = new ProgressBar();

    @FXML
    private ProgressBar ram_indicator = new ProgressBar();

    private static final int ramCalc = 1048576;
    private static final int cpuCalc = 100;
    private static Integer counter = 0;
    private static Resources res_object;
    private XYChart.Series<Number, Number> seriesCpu;
    private XYChart.Series<Number, Number> seriesRam;
    private boolean startState = false;
    private Timeline timeline;
    private Map<String,Resources> sortedResuources;
    private static DateFormat dateFormat;
    private static Date date;
    private Thread thread;
    @FXML
    private VBox vbox_main = new VBox();
    private void refreshData() {

        timeline = new Timeline(new KeyFrame(Duration.millis(350), ev -> {
            if (!startState) {
                timeline.stop();
            }

            Runnable myRunnable = new Runnable(){

                public void run(){
                    res_object.getInfo();
                    System.out.println("finishing runnable");
                }
            };

            Thread thread = new Thread(myRunnable);
            thread.start();
            System.out.println("setting text");
            cpu_usage.setText(res_object.convertToString(res_object.getCpuLoad(),cpuCalc) + " %");
            ram_usage.setText(res_object.convertToString(res_object.getRamLoad(),ramCalc) + " / " + res_object.convertToString(res_object.getMaxRam(),ramCalc));
            disk_space.setText(res_object.convertToString(res_object.getFreeDisk(),ramCalc) + " MB");
            chartDraw(res_object);
            date = new Date();
            cpu_indicator.setProgress(res_object.getCpuLoad());
            ram_indicator.setProgress(res_object.getRamLoadDouble() / res_object.getMaxRamDouble());
            thread = new Thread(new Resources());
            sortedResuources.put(dateFormat.format(date),res_object);
            thread.start();

            //System.out.println("Thread from runnable Controller class " + Thread.currentThread().getId());
            System.out.println("Number of running threads: " + Thread.activeCount());
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
        try {

            seriesCpu.getData().add(new XYChart.Data<>(counter, res_object.getCpuLoad() * 100));
            seriesRam.getData().add(new XYChart.Data<>(counter, res_object.getRamPercent()));
            counter++;
        }catch(Exception e){
            System.exit(20);
        }
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
        res_object = new Resources();
        seriesRam = new XYChart.Series<>();
        seriesCpu = new XYChart.Series<>();
        sortedResuources = new TreeMap<>();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        usageChart.getData().add(seriesCpu);
        usageChart.getData().add(seriesRam);
    }
}
