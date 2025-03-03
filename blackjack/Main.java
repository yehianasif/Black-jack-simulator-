package blackjack;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int totalSimulations = 1000;  // Number of simulations
        int gamesPerSimulation = 1000;

        // Hit thresholds to compare
        int[] hitThresholds = {1, 15, 16, 17, 18, 19, 21, 22};

        // Store results for graphing
        Map<Integer, double[]> winRates = new HashMap<>();

        for (int threshold : hitThresholds) {
            double[] winPercentages = new double[totalSimulations];
            double totalPercentage = 0.0;

            for (int j = 0; j < totalSimulations; j++) {
                int playerWins = 0;

                for (int i = 0; i < gamesPerSimulation; i++) {
                    Deck deck = new Deck();
                    Hand playerHand = new Hand();
                    Hand dealerHand = new Hand();

                    // Initial deal
                    playerHand.addCard(deck.drawCard());
                    playerHand.addCard(deck.drawCard());
                    dealerHand.addCard(deck.drawCard());
                    dealerHand.addCard(deck.drawCard());

                    // Player hits until reaching at least the threshold
                    while (playerHand.getTotalValue() < threshold) {
                        playerHand.addCard(deck.drawCard());
                    }

                    // Dealer hits until at least 17
                    while (dealerHand.getTotalValue() < 17) {
                        dealerHand.addCard(deck.drawCard());
                    }

                    int playerTotal = playerHand.getTotalValue();
                    int dealerTotal = dealerHand.getTotalValue();

                    // Determine the winner
                    if (playerTotal > 21) {
                        // Player busts, dealer wins
                    } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
                        playerWins++; // Player wins
                    }
                }

                // Compute win percentage for this batch
                double winPercentage = (playerWins / (double) gamesPerSimulation) * 100;
                winPercentages[j] = winPercentage;
                totalPercentage += winPercentage;
            }

            winRates.put(threshold, winPercentages);
        }

        // Generate Graph
        SwingUtilities.invokeLater(() -> createGraph(winRates, totalSimulations));
    }

    private static void createGraph(Map<Integer, double[]> winRates, int totalSimulations) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (Map.Entry<Integer, double[]> entry : winRates.entrySet()) {
            int threshold = entry.getKey();
            double[] winPercentages = entry.getValue();
            XYSeries series = new XYSeries("Stop at " + threshold);

            for (int i = 0; i < totalSimulations; i++) {
                series.add(i + 1, winPercentages[i]);  // (Simulation Number, Win %)
            }

            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Player Win Percentage for Different Hit Strategies",
                "Simulation Batch",
                "Win Percentage",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        JFrame frame = new JFrame("Blackjack Win Rate Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
