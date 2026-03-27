package io.github.mcartifact.api.db;

import java.util.Collection;
import java.util.Map;

/**
 * Represents the low-level database storage for artifacts.
 * <p>
 * This interface provides direct, synchronous access to the underlying 
 * data store using standard Java types. It is typically used by the 
 * implementation of an {@link io.github.mcartifact.api.IArtifactProvider}.
 * </p>
 */
public interface IArtifactDB {

    /**
     * Persists an artifact's Base64 representation into the database.
     *
     * @param itemId     The unique identifier for the item.
     * @param itemBase64 The Base64 encoded string representing the item's data.
     * @return {@code true} if the data was successfully saved/updated.
     */
    boolean save(String itemId, String itemBase64);

    /**
     * Performs a synchronous lookup for multiple artifacts by their Item IDs.
     *
     * @param itemIds A collection of unique identifiers to look up.
     * @return A {@link Map} where the keys are the Item IDs found, and the values 
     * are their corresponding Base64 strings.
     */
    Map<String, String> loads(Collection<String> itemIds);

    /**
     * Checks if the specified Item IDs currently exist in the database.
     *
     * @param itemIds A collection of unique identifiers to check.
     * @return A {@link Map} where the keys are the Item IDs and the values are 
     * {@code true} if the ID exists in storage.
     */
    Map<String, Boolean> exists(Collection<String> itemIds);

    /**
     * Removes an entry from the database associated with the given Item ID.
     *
     * @param itemId The unique identifier for the item to be deleted.
     * @return {@code true} if the item existed and was removed.
     */
    boolean delete(String itemId);

    /**
     * Returns the total number of artifact Item IDs currently stored in the database.
     *
     * @return The total count of entries.
     */
    int count();

    /**
     * Provides a raw view of all stored artifact data.
     *
     * @return A {@link Map} containing all Item IDs and their corresponding Base64 strings.
     */
    Map<String, String> getRawMap();
}
