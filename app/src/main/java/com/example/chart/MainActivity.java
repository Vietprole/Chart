package com.example.chart;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.ui.Label;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnyChartView anyChartView1 = findViewById(R.id.any_chart_view1);
        APIlib.getInstance().setActiveAnyChartView(anyChartView1);
        anyChartView1.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(MainActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        int healthy_chicken = 13;
        int sick_chicken = 7;
        int total_chicken = healthy_chicken + sick_chicken;
        float healthy_ratio = (float) healthy_chicken / total_chicken * 100;

        List<DataEntry> data1 = new ArrayList<>();
        data1.add(new ValueDataEntry("Healthy Chicken", healthy_chicken));
        data1.add(new ValueDataEntry("Sick Chicken", sick_chicken));

        pie.data(data1);
        pie.palette(new String[]{"#02d132", "#e80202"});

        pie.title("Chicken health status in the farm");

        pie.labels().position("inside");
        pie.innerRadius(75);

        Label centerLabel = pie.label(0);
        String centerString = String.format("%.0f", healthy_ratio) + "%";
        centerLabel.text(centerString);
        centerLabel.fontSize(30d);
        centerLabel.fontWeight("bold");
        if (healthy_ratio < 85) {
            centerLabel.fontColor("#e80202");
        } else {
            centerLabel.fontColor("#02d132");
        }
        centerLabel.offsetX("50%");
        centerLabel.offsetY("55%");
        centerLabel.anchor("center");

        Label centerLabel1 = pie.label(1);
        String centerString1 = "Healthy";
        centerLabel1.text(centerString1);
        centerLabel1.fontSize(18d);
        centerLabel1.fontStyle("bold");
        centerLabel1.offsetX("49%");
        centerLabel1.offsetY("63%");
        centerLabel1.anchor("center");
//
//        Label centerLabel2 = pie.label(2);
//        String centerString2 = "Total: " + total_chicken + "Chicken";
//        centerLabel2.text(centerString1);
//        centerLabel2.fontSize(12d);
//        centerLabel2.fontStyle("bold");
//        centerLabel2.offsetX("50%");
//        centerLabel2.offsetY("60%");
//        centerLabel2.anchor("center");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Status")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView1.setChart(pie);

        AnyChartView anyChartView2 = findViewById(R.id.any_chart_view2);
        APIlib.getInstance().setActiveAnyChartView(anyChartView2);
        anyChartView2.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();
        String[] hour = {"2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24"};
//        int[] raw_data = {0, 0, 600, 500, 340, 500, 260, 420, 500, 0, 0, 0};
        int[] raw_data = {0, 0, 500, 400, 440, 600, 360, 320, 400, 0, 0, 0};
        List<DataEntry> data2 = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < hour.length; i++) {
            data2.add(new ValueDataEntry(hour[i], raw_data[i]));
            sum += raw_data[i];
        }

        Column column = cartesian.column(data2);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }g");

        cartesian.animation(true);
        cartesian.title("Amount of food chicken consumed in a day");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }g");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Hour");
        cartesian.yAxis(0).title("Food consumed");

        Label textMarker = cartesian.label(3);
        textMarker.text("Total: " + sum + "g");
        textMarker.fontSize(14d);
        textMarker.fontColor("#000000");
        textMarker.offsetX("60%");
        textMarker.offsetY("15%");
        textMarker.anchor("bottom");

        Label textMarker1 = cartesian.label(4);
        textMarker1.text("Average: " + sum/24 + "g");
        textMarker1.fontSize(14d);
        textMarker1.fontColor("#000000");
        textMarker1.offsetX("60%");
        textMarker1.offsetY("20%");
        textMarker1.anchor("bottom");


        anyChartView2.setChart(cartesian);
    }
}