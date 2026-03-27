# MCArtifact API

The core API for the **MCArtifact** ecosystem. This module provides the essential interfaces for managing, storing, and retrieving artifact data within Minecraft, ensuring high performance and thread safety.

## Overview

The MCArtifact API is built around two primary concepts:

1. **Asynchronous Access (`IArtifactProvider`)**: Ensures that high-latency database or I/O operations do not block the Minecraft server's main thread.

2. **Synchronous Storage (`IArtifactDB`)**: Provides a straightforward, low-level data access layer for raw storage implementations (e.g., File, SQLite, MySQL, MongoDB).

## Core Interfaces

### `IArtifactProvider`

Located in `io.github.mcartifact.api`. This is the main entry point for developers interacting with the MCArtifact system.

- All methods return a `CompletableFuture`.
- Supports bulk operations for efficient data handling.
- **Methods include:** `saveItem`, `loadItems`, `itemExists`, `delete`, `count`, and `getRawMap`.

### `IArtifactDB`

Located in `io.github.mcartifact.api.db`. This interface represents the underlying database implementation.

- Performs synchronous, blocking operations.
- Uses standard Java types for broad compatibility.
- **Methods include:** `save`, `loads`, `exists`, `delete`, `count`, and `getRawMap`.

## Terminology

- **`itemId`**: The unique String identifier used to query and store a specific artifact.
- **`itemBase64`**: The Base64 encoded string representation of an item's data (such as a serialized `ItemStack`).

## Integration

To implement a custom storage solution, developers should implement `IArtifactDB` to handle the actual data persistence, and then wrap that database instance within an `IArtifactProvider` implementation to handle the asynchronous execution off the main server thread.
