package fr.dl11.openmedia.console.components;

import fr.dl11.openmedia.console.Console;
import fr.dl11.openmedia.core.DataGraphManager;

/**
 * Interface representing a command in the console application.
 *
 * <p>Implementations of this interface define the logic for specific commands
 * that can be executed within the console. Each command is provided with
 * arguments, a data graph manager, and a console instance for interaction.
 */
public interface ICommand {

    /**
     * Executes the command with the provided arguments.
     *
     * @param args    The command-line arguments passed to the command.
     * @param graph   The {@link DataGraphManager} instance for managing data graphs.
     * @param console The {@link Console} instance for user interaction.
     */
    void execute(String[] args, DataGraphManager graph, Console console);
}