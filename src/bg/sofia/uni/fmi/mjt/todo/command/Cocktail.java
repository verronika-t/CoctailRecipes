package bg.sofia.uni.fmi.mjt.todo.command;

import com.google.gson.Gson;

import java.util.Set;

public record Cocktail(String name, Set<Ingredient> ingredients) {

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
