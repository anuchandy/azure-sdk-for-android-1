// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.storage.blob.transfer;

import android.os.Build;

import androidx.annotation.NonNull;

import com.azure.android.storage.blob.StorageBlobAsyncClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A map containing the {@link StorageBlobAsyncClient} to be used for low-level storage
 * service calls.
 */
public final class StorageBlobClientMap {
    /**
     * Map with key as user defined unique identifier and value as associated
     * blob storage client.
     */
    private Map<String, StorageBlobAsyncClient> map = new ConcurrentHashMap<>();
    // The singleton instance of StorageBlobClientMap.
    private static StorageBlobClientMap INSTANCE = null;
    // An object to synchronize the creation of the singleton StorageBlobClientMap.
    private static final Object INIT_LOCK = new Object();
    // An object to synchronize the operation of adding an entry to the map.
    private static final Object ADD_LOCK = new Object();
    /**
     * Retrieves the singleton instance of {@link StorageBlobClientMap}.
     *
     * @return The singleton instance of {@link StorageBlobClientMap}.
     */
    public static @NonNull StorageBlobClientMap getInstance() {
        synchronized (INIT_LOCK) {
            if (INSTANCE == null) {
                INSTANCE = new StorageBlobClientMap();
            }
            return INSTANCE;
        }
    }

    private StorageBlobClientMap() {
    }

    /**
     * Add a {@link StorageBlobAsyncClient} to this map.
     *
     * @param storageBlobClientId The unique ID of the {@link StorageBlobAsyncClient}.
     * @param storageBlobAsyncClient The blob storage client.
     * @throws IllegalArgumentException If a {@link StorageBlobAsyncClient} with the same ID already exists in the map.
     */
    public void add(@NonNull String storageBlobClientId, @NonNull StorageBlobAsyncClient storageBlobAsyncClient) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.map.compute(storageBlobClientId, (id, existingClient) -> {
                if (existingClient != null) {
                    throw new IllegalArgumentException(
                        "A StorageBlobClient with id '" + storageBlobClientId + "' already exists.");
                } else {
                    return storageBlobAsyncClient;
                }
            });
        } else {
            synchronized (ADD_LOCK) {
                if (this.contains(storageBlobClientId)) {
                    throw new IllegalArgumentException(
                        "A StorageBlobClient with id '" + storageBlobClientId + "' already exists.");
                } else {
                    this.map.put(storageBlobClientId, storageBlobAsyncClient);
                }
            }
        }
    }


    /**
     * Get the {@link StorageBlobAsyncClient} for a specified id.
     *
     * @param storageBlobClientId the unique id of the {@link StorageBlobAsyncClient} to retrieve
     * @return the blob storage client if exists, null otherwise
     */
    public StorageBlobAsyncClient get(String storageBlobClientId) {
        return this.map.get(storageBlobClientId);
    }

    /**
     * Check if the map contains a {@link StorageBlobAsyncClient} for the specified id.
     *
     * @param storageBlobClientId the unique id of the blob storage client
     * @return {@code true} if this map contains a blob storage client for the specified id
     */
    public boolean contains(@NonNull String storageBlobClientId) {
        return this.map.containsKey(storageBlobClientId);
    }

    /**
     * Check this map is empty.
     *
     * @return {@code true} if this map contains no entries
     */
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
}
