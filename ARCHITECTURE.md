# MCArtifact Architecture

This document outlines the architectural design and data flow of the MCArtifact API layer.  
The primary goal of this architecture is to decouple the asynchronous service requirements of a Minecraft server from the synchronous realities of database transactions.

## Design Pattern: Provider & DB Separation

The architecture strictly separates the **Service Layer** from the **Data Access Layer**.

### 1. The Service Layer (`IArtifactProvider`)

- **Purpose**: Acts as the API gateway for plugins and server logic.
- **Concurrency**: Fully asynchronous. Every method returns a `CompletableFuture<T>`.
- **Role**: It is responsible for accepting requests from the main server thread, offloading the work to an asynchronous thread pool (e.g., `Bukkit.getScheduler().runTaskAsynchronously`), calling the underlying `IArtifactDB`, and returning the promise.

### 2. The Data Access Layer (`IArtifactDB`)

- **Purpose**: Handles direct data persistence and retrieval.
- **Concurrency**: Synchronous / Blocking.
- **Role**: Interacts directly with the file system, SQL database, or NoSQL document store. It expects to be called from an off-main thread context provided by the `IArtifactProvider`.

## Data Flow Diagram

1. **Request**: A Minecraft plugin requests an item via `IArtifactProvider#loadItems(Collection<String> itemIds)`.
2. **Offload**: The Provider creates a `CompletableFuture` and schedules an asynchronous task.
3. **Execution**: On the async thread, the Provider calls the synchronous `IArtifactDB#loads(itemIds)`.
4. **Resolution**: The DB fetches the Base64 strings and returns the `Map<String, String>`.
5. **Completion**: The `CompletableFuture` completes, allowing the original plugin to process the returned data (e.g., decode the Base64 and give the item to a player) safely on the main thread via `.thenAcceptSync` or similar Bukkit schedulers.

## Standardized Terminology

To ensure consistency across the codebase, the following parameter standards are enforced at the API level:

| Concept | Variable Name | Type | Description |
| :--- | :--- | :--- | :--- |
| **Identifier** | `itemId` | `String` | The unique key representing an artifact. |
| **Data Payload** | `itemBase64` | `String` | The serialized data of the artifact encoded in Base64 format. |

## Bulk Operations Strategy

The API is designed for high-performance inventory management. Instead of iterating through singular `#getArtifact` calls, the architecture provides `loadItems` and `itemExists` which accept Collections. Implementations of `IArtifactDB` should optimize these methods (e.g., using SQL `WHERE id IN (...)`) to minimize database round-trips.
