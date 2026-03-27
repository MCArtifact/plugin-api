# MCArtifact API

The core API for the **MCArtifact** ecosystem. This module provides the essential interfaces for managing, storing, and retrieving artifact data within Minecraft, ensuring high performance and thread safety.

## Overview

The MCArtifact API is built around two primary concepts:

1. **Asynchronous Access (`IArtifactProvider`)**: Ensures that high-latency database or I/O operations do not block the Minecraft server's main thread.

2. **Synchronous Storage (`IArtifactDB`)**: Provides a straightforward, low-level data access layer for raw storage implementations (e.g., File, SQLite, MySQL, MongoDB).

---

## Threading Model

- `IArtifactProvider` methods are **non-blocking** and safe to call on the main thread.
- `IArtifactDB` methods are **blocking** and MUST NOT be called on the main thread.

---

## Usage Example

```java
IArtifactProvider provider = ...;

// Save item
provider.saveItem("sword_1", base64)
    .thenAccept(success -> {
        if (success) {
            System.out.println("Saved!");
        }
    });

// Load items
provider.loadItems(List.of("sword_1"))
    .thenAccept(map -> {
        String base64 = map.get("sword_1");
    });
```

---

## Core Interfaces

### `IArtifactProvider`

Located in `io.github.mcartifact.api`. This is the main entry point for developers interacting with the MCArtifact system.

- All methods return a `CompletableFuture`.
- Supports bulk operations for efficient data handling.

<details>
<summary><b>saveItem</b></summary>

Asynchronously saves an item's Base64 representation.

**Parameters:**
- `itemId` (`String`): unique identifier of the item
- `itemBase64` (`String`): Base64 encoded item data

**Returns:**
- `CompletableFuture<Boolean>` — true if saved successfully

**Example:**
```java
provider.saveItem("sword_1", base64);
```

</details>

<details>
<summary><b>loadItems</b></summary>

Asynchronously retrieves Base64 strings for multiple Item IDs.

**Parameters:**
- `itemIds` (`Collection<String>`): collection of item IDs

**Returns:**
- `CompletableFuture<Map<String, String>>`

</details>

<details>
<summary><b>itemExists</b></summary>

Checks if specified Item IDs exist.

**Parameters:**
- `itemIds` (`Collection<String>`): collection of item IDs

**Returns:**
- `CompletableFuture<Map<String, Boolean>>`

</details>

<details>
<summary><b>delete</b></summary>

Removes an item.

**Parameters:**
- `itemId` (`String`): item ID to delete

**Returns:**
- `CompletableFuture<Boolean>`

</details>

<details>
<summary><b>count</b></summary>

Retrieves total count.

**Returns:**
- `CompletableFuture<Integer>`

</details>

<details>
<summary><b>getRawMap</b></summary>

Returns all stored data.

**Returns:**
- `CompletableFuture<Map<String, String>>`

</details>

---

### `IArtifactDB`

Located in `io.github.mcartifact.api.db`. This interface represents the underlying database implementation.

- Performs synchronous, blocking operations.
- Uses standard Java types for broad compatibility.

<details>
<summary><b>save</b></summary>

Persists an artifact.

**Parameters:**
- `itemId` (`String`): unique identifier
- `itemBase64` (`String`): Base64 encoded item data

**Returns:**
- `boolean`

</details>

<details>
<summary><b>loads</b></summary>

Loads multiple artifacts.

**Parameters:**
- `itemIds` (`Collection<String>`): collection of item IDs

**Returns:**
- `Map<String, String>`

</details>

<details>
<summary><b>exists</b></summary>

Checks existence of items.

**Parameters:**
- `itemIds` (`Collection<String>`): collection of item IDs

**Returns:**
- `Map<String, Boolean>`

</details>

<details>
<summary><b>delete</b></summary>

Deletes an item.

**Parameters:**
- `itemId` (`String`): item ID

**Returns:**
- `boolean`

</details>

<details>
<summary><b>count</b></summary>

Total number of items.

**Returns:**
- `int`

</details>

<details>
<summary><b>getRawMap</b></summary>

Returns all stored data.

**Returns:**
- `Map<String, String>`

</details>

---

## Terminology

- **`itemId`**: The unique String identifier used to query and store a specific artifact.
- **`itemBase64`**: The Base64 encoded string representation of an item's data (such as a serialized `ItemStack`).

---

## Integration

To implement a custom storage solution:

1. Implement `IArtifactDB` for persistence.
2. Wrap it inside an `IArtifactProvider` for async execution.

---

## Design Principles

- Separation of concerns (async vs sync)
- No blocking operations on main thread
- Designed for high concurrency
