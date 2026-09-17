package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class BarChartPanel extends JPanel{
    private Map<String, Integer> data;

    public BarChartPanel() {
        this.data = new LinkedHashMap<>();
        setBackground(Color.WHITE);
    }

    public void setData(Map<String, Integer> data) {

        this.data = data != null
                ? data
                : new LinkedHashMap<>();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (data.isEmpty()) {
            g.drawString(
                    "No data available",
                    20,
                    30
            );
            return;
        }

        int width = getWidth();
        int height = getHeight();

        int left = 50;
        int bottom = 40;
        int top = 25;

        int chartHeight =
                height - top - bottom;

        int chartWidth =
                width - left - 20;

        int max = 1;

        for (Integer value : data.values()) {
            if (value > max) {
                max = value;
            }
        }

        // Axes
        g.drawLine(
                left,
                top,
                left,
                height - bottom
        );

        g.drawLine(
                left,
                height - bottom,
                width - 20,
                height - bottom
        );

        int numberOfBars =
                data.size();

        int space =
                chartWidth / numberOfBars;

        int barWidth =
                Math.max(
                        15,
                        space / 2
                );

        int index = 0;

        for (Map.Entry<String, Integer> entry :
                data.entrySet()) {

            int value =
                    entry.getValue();

            int barHeight =
                    (int) (
                            ((double) value / max)
                                    * (chartHeight - 20)
                    );

            int x =
                    left
                            + index * space
                            + (space - barWidth) / 2;

            int y =
                    height
                            - bottom
                            - barHeight;

            g.fillRect(
                    x,
                    y,
                    barWidth,
                    barHeight
            );

            g.drawString(
                    String.valueOf(value),
                    x,
                    y - 5
            );

            String name =
                    entry.getKey();

            if (name.length() > 10) {
                name =
                        name.substring(0, 10)
                                + "...";
            }

            g.drawString(
                    name,
                    x,
                    height - 20
            );

            index++;
        }
    }

}
