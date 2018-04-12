package server.Resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import server.Models.Recipe;

public class RecipeResource {

    private final long id;
    private final String name;

    public RecipeResource(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
    }

    @JsonProperty("id")
    public long getRecipeId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
