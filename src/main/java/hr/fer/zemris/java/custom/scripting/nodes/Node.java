package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Node
 *
 * @author Mihael
 */
public class Node {
    /**
     * Children collection
     */
    private ArrayIndexedCollection collection = null;

    /**
     * Adds child in collection
     *
     * @param child child we want to add
     */
    public void addChildNode(Node child) {
        if (collection == null) {
            collection = new ArrayIndexedCollection();
        }
        collection.add( child );
    }

    /**
     * Returns number of children
     *
     * @return int number of children
     */
    public int numberOfChildren() {
        if (collection == null)
            return 0;
        return collection.size();
    }

    /**
     * Return child at given index
     *
     * @param index index
     * @return Node child
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Node getChild(int index) {
        return (Node) collection.get( index );
    }
}
