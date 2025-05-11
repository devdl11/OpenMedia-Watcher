package fr.dl11.openmedia.console.components;

public record ConsoleCommand(String command, String description, ICommand executor) {
    public ConsoleCommand(String command, ICommand executor) {
        this(command, "", executor);
    }
}
