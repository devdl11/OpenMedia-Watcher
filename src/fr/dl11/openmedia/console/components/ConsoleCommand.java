package fr.dl11.openmedia.console.components;

/**
 * Represents a console command in the application.
 *
 * <p>This record encapsulates the command name, its description, and the executor
 * responsible for handling the command's logic. It provides a convenient way to
 * define and manage commands in the console application.
 *
 * @param command     The name of the command.
 * @param description A brief description of the command (optional).
 * @param executor    The {@link ICommand} instance responsible for executing the command.
 */
public record ConsoleCommand(String command, String description, ICommand executor) {

    /**
     * Constructs a new ConsoleCommand with an empty description.
     *
     * <p>This constructor allows creating a command with only its name and executor,
     * defaulting the description to an empty string.
     *
     * @param command  The name of the command.
     * @param executor The {@link ICommand} instance responsible for executing the command.
     */
    public ConsoleCommand(String command, ICommand executor) {
        this(command, "", executor);
    }
}