package bg.sofia.uni.fmi.mjt.todo.command;

import bg.sofia.uni.fmi.mjt.todo.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todo.exceptions.CocktailNotFoundException;
import bg.sofia.uni.fmi.mjt.todo.storage.CocktailStorage;
import bg.sofia.uni.fmi.mjt.todo.storage.DefaultCocktailStorage;
import bg.sofia.uni.fmi.mjt.todo.storage.Storage;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandExecutor {
    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
        "Invalid count of arguments: \"%s\" expects %d arguments. Example: \"%s\"";

    private static final String CREATE = "create";
    private static final String GET = "get";
    private static final String DISCONNECT = "disconnect";

    private CocktailStorage cocktailStorage;

    public CommandExecutor(CocktailStorage cocktailStorage) {
        this.cocktailStorage = cocktailStorage;
    }

    public String execute(Command cmd) throws CocktailAlreadyExistsException, CocktailNotFoundException {
        return switch (cmd.command()) {
            case CREATE -> createCocktail(cmd.arguments());
            case GET -> getCocktail(cmd.arguments());
            default -> "Unknown command";
        };
    }

    private String createCocktail(String[] args) throws CocktailAlreadyExistsException {
        if (args.length < 2) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, CREATE, 2, CREATE + "<cocktail_name> [<ingredient_name>=<ingredient_amount> ...]");
        }

        String cocktailName = args[0];
        Set<Ingredient> ingredients = new HashSet<>();

        for (int i = 1; i < args.length; i++) {
            String[] ingredientData = args[i].split("=");
            String name = ingredientData[0];
            String amount = ingredientData[1];
            Ingredient ingredient = new Ingredient(name, amount);
            ingredients.add(ingredient);
        }

        Cocktail cocktail = new Cocktail(cocktailName, ingredients);
        cocktailStorage.createCocktail(cocktail);
        return String.format("Added new %s cocktail.", cocktail.name());

    }

    private String getCocktail(String[] args) throws CocktailNotFoundException {

        if (args.length < 1) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, GET, 1, GET + "<cocktail_name> [<ingredient_name>=<ingredient_amount> ...]");
        }
        String str = "";

        switch (args[0]) {
            case "all" -> str = cocktailStorage.getCocktails().toString();
            case "by-name" -> {
                String name = args[1];
                str = cocktailStorage.getCocktail(name).toString();
            }
            case "by-ingredient" -> {
                String ingredient = args[1];
                str = cocktailStorage.getCocktailsWithIngredient(ingredient).toString();
            }
            default -> {
                return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, GET, 1, GET + "<cocktail_name> [<ingredient_name>=<ingredient_amount> ...]");
            }
        }

        return str;
    }
}
