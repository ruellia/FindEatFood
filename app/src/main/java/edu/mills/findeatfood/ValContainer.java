package edu.mills.findeatfood;

/**
 * Generic container class that allows for safe getting and setting of an object.
 */
public class ValContainer<T> {
    private T val;

    /**
     * Default constructor for ValContainer.
     * Does not set a value.
     */
    public ValContainer() {
    }

    /**
     * Constructor for ValContainer.
     * Creates the ValContainer with an initial value.
     * @param val the initial value
     */
    public ValContainer(T val) {
        this.val = val;
    }

    /**
     * Gets the current value from the container.
     * @return the current value
     */
    public T getVal() {
        return val;
    }

    /**
     * Sets the new value of the container.
     * @param val the new value
     */
    public void setVal(T val) {
        this.val = val;
    }
}