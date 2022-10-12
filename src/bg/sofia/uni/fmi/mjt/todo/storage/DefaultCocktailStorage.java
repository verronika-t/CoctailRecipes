package bg.sofia.uni.fmi.mjt.todo.storage;

import bg.sofia.uni.fmi.mjt.todo.command.Cocktail;
import bg.sofia.uni.fmi.mjt.todo.command.Ingredient;
import bg.sofia.uni.fmi.mjt.todo.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todo.exceptions.CocktailNotFoundException;
import com.google.gson.Gson;

import java.util.*;

public class DefaultCocktailStorage implements CocktailStorage {

    Map<String, Cocktail> cocktailsMap;

    public DefaultCocktailStorage() {
        this.cocktailsMap = new HashMap<>();
    }

    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {
        if (cocktailsMap.containsKey(cocktail.name())) {
            throw new CocktailAlreadyExistsException(String.format("%s cocktail already exist.", cocktail.name()));
        }

        cocktailsMap.put(cocktail.name(), cocktail);
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return Collections.unmodifiableCollection(cocktailsMap.values());
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        List<Cocktail> cocktailsWithIngradient = new ArrayList<>();
        for (Cocktail cocktail : cocktailsMap.values()) {
           if (cocktail.ingredients().stream().anyMatch(i -> i.name().equals(ingredientName))) {
               cocktailsWithIngradient.add(cocktail);
           }
        }
        return Collections.unmodifiableList(cocktailsWithIngradient);
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        if (!cocktailsMap.containsKey(name)){
            throw new CocktailNotFoundException(String.format("Not found %s cocktail.", name));
        }
        return cocktailsMap.get(name);
    }
}
