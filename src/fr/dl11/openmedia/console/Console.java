package fr.dl11.openmedia.console;

import fr.dl11.openmedia.console.commands.ObserverManagerCommand;
import fr.dl11.openmedia.console.commands.ShowDataCommand;
import fr.dl11.openmedia.console.commands.SimulateEventCommand;
import fr.dl11.openmedia.console.components.ConsoleCommand;
import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.observers.BaseObserver;

import java.util.*;

public class Console {
    private final DataGraphManager dataGraphManager;
    private final Map<String, ConsoleCommand> consoleCommands;
    public final Scanner scanner;
    public List<BaseObserver> activeObservers;

    private void initCommands() {
        if (!consoleCommands.isEmpty()) {
            return;
        }

        consoleCommands.put("exit", new ConsoleCommand("exit", "Exit the console", (args, graph, console) -> {
            System.out.println("Exiting...");
            System.exit(0);
        }));

        consoleCommands.put("help", new ConsoleCommand("help", "Show help", (args, graph, console) -> {
            System.out.println("Available commands:");
            for (ConsoleCommand command : consoleCommands.values()) {
                System.out.println(command.command() + ": " + command.description());
            }
        }));

        consoleCommands.put("show", new ConsoleCommand("show", "Show Data", new ShowDataCommand()));
        consoleCommands.put("simulate", new ConsoleCommand("simulate", "Simulate Data", new SimulateEventCommand()));
        consoleCommands.put("observers", new ConsoleCommand("observers", "Manage observers", new ObserverManagerCommand()));
    }

    public Console(DataGraphManager dataGraphManager) {
        this.dataGraphManager = dataGraphManager;
        this.activeObservers = new ArrayList<>();
        consoleCommands = new HashMap<>();
        scanner = new Scanner(System.in);

        initCommands();
    }

    public void show() {
        System.out.println("Welcome to the OpenMedia console!");
        System.out.println("Type 'help' to see available commands.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            String[] args = input.split(" ");
            if (args.length == 0) continue;

            String command = args[0];
            ConsoleCommand consoleCommand = consoleCommands.get(command);

            if (consoleCommand == null) {
                System.out.println("Unknown command: " + command);
                continue;
            }

            consoleCommand.executor().execute(args, dataGraphManager, this);
        }
    }
}
