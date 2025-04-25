//package view;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.LineAndShapeRenderer;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class LineChartExample extends JFrame {
//
//    public LineChartExample() {
//        setTitle("Biểu đồ Đường - Doanh thu theo tháng");
//        setSize(850, 600);
//        setLocationRelativeTo(null); // Căn giữa màn hình
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Tạo dữ liệu
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(10, "Doanh thu", "Tháng 1");
//        dataset.addValue(15, "Doanh thu", "Tháng 2");
//        dataset.addValue(8, "Doanh thu", "Tháng 3");
//        dataset.addValue(18, "Doanh thu", "Tháng 4");
//        dataset.addValue(20, "Doanh thu", "Tháng 5");
//
//        // Tạo biểu đồ
//        JFreeChart lineChart = ChartFactory.createLineChart(
//                "Biểu đồ doanh thu theo tháng",
//                "Tháng",
//                "Triệu đồng",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//
//        // Tùy chỉnh giao diện cho đẹp hơn
//        CategoryPlot plot = lineChart.getCategoryPlot();
//        plot.setBackgroundPaint(Color.white); // nền biểu đồ
//        plot.setRangeGridlinePaint(Color.gray); // màu các đường lưới
//
//        // Renderer để chỉnh nét vẽ
//        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
//        renderer.setSeriesPaint(0, new Color(0, 123, 255)); // màu line
//        renderer.setSeriesStroke(0, new BasicStroke(2.5f)); // độ dày
//        plot.setRenderer(renderer);
//
//        // Chỉnh font tiêu đề và trục
//        lineChart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
//        lineChart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 14));
//        plot.getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//        plot.getRangeAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//
//        // Hiển thị biểu đồ
//        ChartPanel chartPanel = new ChartPanel(lineChart);
//        chartPanel.setPreferredSize(new Dimension(800, 550));
//        setContentPane(chartPanel);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            LineChartExample chart = new LineChartExample();
//            chart.setVisible(true);
//        });
//    }
//}
