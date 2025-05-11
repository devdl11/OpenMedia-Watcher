package fr.dl11.openmedia.console.components;

import fr.dl11.openmedia.console.Console;
import fr.dl11.openmedia.core.DataGraphManager;

public interface ICommand {
    void execute(String[] args, DataGraphManager graph, Console console);
}
