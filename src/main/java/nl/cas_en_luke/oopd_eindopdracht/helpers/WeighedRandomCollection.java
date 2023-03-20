package nl.cas_en_luke.oopd_eindopdracht.helpers;

import java.util.ArrayList;

public class WeighedRandomCollection {
    public interface IWeighedItem {
        /**
         * Gets the weight of the weighed item.
         * @return the weight.
         */
        double getWeight();
    }

    private ArrayList<IWeighedItem> weighedItems;
    private double weightSum;

    /**
     * Constructs a new weighed random collection.
     */
    public WeighedRandomCollection() {
        this.weighedItems = new ArrayList<>();
        this.weightSum = 0.0;
    }

    /**
     * Adds the given weighed item to the collections.
     *
     * @param weighedItem the weighed item to add.
     */
    public void add(final IWeighedItem weighedItem) {
        this.weightSum += weighedItem.getWeight();

        this.weighedItems.add(weighedItem);
    }

    /**
     * Removes the given weighed item from the collections.
     *
     * @param weighedItem the weighed item to remove.
     */
    public void remove(final IWeighedItem weighedItem) {
        this.weightSum -= weighedItem.getWeight();

        this.weighedItems.remove(weighedItem);
    }

    /**
     * Gets a random weighed item.
     * @return the random weighed item.
     */
    public IWeighedItem getRandomWeighedItem() {
        final double threshold = Math.random() * weightSum;
        double subWeightSum = 0.0;

        for (final IWeighedItem weighedItem : weighedItems) {
            subWeightSum += weighedItem.getWeight();

            if (subWeightSum >= threshold) {
                return weighedItem;
            }
        }

        return null;
    }
}
