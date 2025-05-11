# OpenMedia Watcher

## Project Overview

OpenMedia Watcher is a Java-based application developed as part of the ET3 IIM curriculum at Polytech. The project focuses on modeling, processing, and observing data related to media entities, including persons, organizations, publications, and their interconnections. It features a data ingestion pipeline, an event-driven architecture, and a console-based user interface for interaction and data simulation.

The system is designed to parse raw data from various sources, build a graph representation of the relationships between different media entities, and allow users to query this data, simulate new events, and set up observers to monitor specific data changes.

## Core Features

* **Data Modeling**: Represents media entities (Media, Persons, Organizations, Publications) and their relationships (e.g., Person-Media links, Organization-Organization links).
* **Data Ingestion Pipeline**:
    * Parses raw data from sources like TSV files (simulated via URL downloads in `Main.java`).
    * Maps raw data records to structured data objects.
* **Event-Driven Architecture**:
    * Utilizes an `EventBus` for decoupled communication between components.
    * Various events are published when new data is processed or simulated (e.g., `NewPublicationEvent`, `NewMediaEvent`, `NewPersonMediaLinkEvent`).
* **Data Graph Management**: (Work in Progress)
    * `DataGraphManager` maintains an in-memory graph of all entities and their links.
    * Handles the addition and updating of entities and links based on published events.
* **Contextualizers**: Process incoming data events and determine the appropriate actions or further events to publish based on the current state of the data graph.
* **Console User Interface**:
    * **Show Data (`ShowDataCommand`)**: Allows users to display detailed information about media, persons, and organizations, including their attributes and links. Supports sorting for person listings.
    * **Simulate Event (`SimulateEventCommand`)**: Provides an interactive way to manually create and publish new data events, such as creating a new article, a new media entity, or linking existing entities.
    * **Manage Observers (`ObserverManagerCommand`)**: Allows users to create, list, and remove specialized observers that react to specific data events based on defined criteria.
* **Observer Pattern**:
    * `BaseObserver` and specialized observer classes (e.g., `PublicationContentObserver`, `PublicationMediaObserver`) can be registered to listen for specific events on the `EventBus`.
    * Observers trigger actions (like printing to the console) when their criteria are met by an event.

## Project Structure (Key Packages)

* `fr.dl11.openmedia`: Root package.
    * `common.types`: Enum types used throughout the project (e.g., `PublicationType`, `NewsType`).
    * `common.events`: Base event classes.
    * `console`: Contains the console UI logic, including command definitions and the main console loop.
        * `commands`: Implementations of different console commands (`ShowDataCommand`, `SimulateEventCommand`, `ObserverManagerCommand`).
    * `core`: Core components of the application.
        * `handlers`: Event handlers that react to specific events and update the `DataGraphManager`.
        * `EventBus.java`: The central event bus implementation.
        * `DataGraphManager.java`: Manages the data graph.
    * `contextualizers`: Classes responsible for contextualizing raw data events into more specific system events.
    * `data`: Data object definitions (e.g., `MediaData`, `PublicationData`) and mappers.
        * `mapper`: Custom data mappers.
        * `organization`, `person`: Sub-packages for organization and person-specific data structures.
    * `datasource`: Components related to data sources, parsing, and mapping.
        * `binding`: Annotations for data binding.
        * `data`: Raw data structures.
        * `events`: Events specific to the data source pipeline.
        * `mappers`: Data mappers (e.g., `DefaultDataMapper`).
        * `parsers`: Parsers for different data formats (e.g., `CSVParser`, `PublicationParser`).
    * `events`: Definitions for various system events.
        * `data`: Events related to specific data entities (e.g., `NewMediaEvent`, `NewPersonEvent`).
    * `models`: Model classes representing the primary entities (e.g., `MediaModel`, `PersonModel`).
    * `observers`: Base and abstract observer classes.
        * `specialized`: Concrete, specialized observer implementations.
    * `utils`: Utility classes (e.g., `URLDownloader`).
* `Main.java`: Entry point of the application, initializes the system and starts the console.

## Key Concepts & Technologies

* **Object-Oriented Programming (OOP)**: Core Java principles.
* **Design Patterns**:
    * **Event Bus**: For decoupled communication.
    * **Observer Pattern**: For reacting to changes and specific events.
    * **Command Pattern**: For implementing console actions.
* **Data Structures**: Use of `Map`, `List`, etc., for managing data.
* **Reflection**: Used to dynamically bind data to objects.
* **Console I/O**: Interaction with the user via `System.in` and `System.out`.

## How to Run

1.  **Prerequisites**:
    * Java Development Kit (JDK) installed (e.g., JDK 17 or later).
    * An IDE like IntelliJ IDEA or Eclipse, or build tools like Maven/Gradle
2.  **Compilation**:
    * If using an IDE, it should handle compilation automatically.
3.  **Execution**:
    * Run the `Main` class.
        ```bash
        # (Assuming you are in the project root directory and compiled to 'out' folder)
        java -cp out Main
        ```
    * The application will initialize by attempting to download data from predefined URLs and then present a console menu.

## Usage

Once the application is running, you will be presented with a command prompt (`>`). Type `help` to see the list of available commands:

* `show [media|person|organization]`: Displays data.
* `simulate`: Guides you through creating and publishing new data events.
* `observers`: Allows you to manage (list, create, remove) specialized data observers.
* `exit`: Closes the application.

Follow the on-screen prompts for each command.

## Author

* David Luca - Student at Polytech, ET3 IIM

## Future Enhancements

* Persistent data storage (e.g., database integration instead of in-memory).
* More sophisticated data validation and error handling.
* Expansion of observer capabilities with more complex conditions.
* A graphical user interface (GUI) instead of a console interface.
* Unit and integration tests.
