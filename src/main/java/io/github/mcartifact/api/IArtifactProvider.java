package io.github.mcartifact.api;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Provides asynchronous access and management for artifacts.
 * <p>
 * This interface is the primary entry point for the MCArtifact system, 
 * wrapping database and I/O operations in {@link CompletableFuture} to 
 * prevent blocking the Minecraft server's main thread.
 * </p>
 */
public interface IArtifactProvider {

    /**
     * Asynchronously saves an item's Base64 representation.
     *
     * @param itemId     The unique identifier for the item.
     * @param itemBase64 The Base64 encoded string of the item.
     * @return A {@link CompletableFuture} resolving to {@code true} if the save was successful.
     */
    CompletableFuture<Boolean> saveItem(String itemId, String itemBase64);

    /**
     * Asynchronously retrieves the Base64 strings for multiple Item IDs.
     *
     * @param itemIds A collection of unique identifiers to look up.
     * @return A {@link CompletableFuture} resolving to a map of found Item IDs and their Base64 data.
     */
    CompletableFuture<Map<String, String>> loadItems(Collection<String> itemIds);

    /**
     * Asynchronously checks if the specified Item IDs exist.
     *
     * @param itemIds A collection of unique identifiers to check.
     * @return A {@link CompletableFuture} resolving to a map indicating existence for each ID.
     */
    CompletableFuture<Map<String, Boolean>> itemExists(Collection<String> itemIds);

    /**
     * Asynchronously removes an item from the provider.
     *
     * @param itemId The unique identifier for the item to be deleted.
     * @return A {@link CompletableFuture} resolving to {@code true} if the item was successfully deleted.
     */
    CompletableFuture<Boolean> delete(String itemId);

    /**
     * Asynchronously retrieves the total count of stored artifacts.
     *
     * @return A {@link CompletableFuture} resolving to the total number of entries.
     */
    CompletableFuture<Integer> count();

    /**
     * Asynchronously retrieves a raw view of all stored artifact data.
     *
     * @return A {@link CompletableFuture} resolving to a map of all Item IDs and their Base64 data.
     */
    CompletableFuture<Map<String, String>> getRawMap();
}