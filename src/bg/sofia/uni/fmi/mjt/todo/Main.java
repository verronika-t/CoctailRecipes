package bg.sofia.uni.fmi.mjt.todo;

import bg.sofia.uni.fmi.mjt.todo.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.todo.server.Server;
import bg.sofia.uni.fmi.mjt.todo.storage.CocktailStorage;
import bg.sofia.uni.fmi.mjt.todo.storage.DefaultCocktailStorage;
import bg.sofia.uni.fmi.mjt.todo.storage.InMemoryStorage;
import bg.sofia.uni.fmi.mjt.todo.storage.Storage;

public class Main {

    public static void main(String[] args) {

        CocktailStorage cocktailStorage = new DefaultCocktailStorage();
        CommandExecutor commandExecutor = new CommandExecutor(cocktailStorage);
        Server server = new Server(6666, commandExecutor);

        server.start();


    }
}
