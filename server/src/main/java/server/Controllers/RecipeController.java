package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.DataResponse;
import server.Models.Recipe;
import server.Repositories.RecipeRepository;
import server.Resources.RecipeResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for recipe.
 */
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Retrieve all recipes.
     *
     * @return List of all recipes as an api response.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getAllRecipes() {
        List<Recipe> recipeList = recipeRepository.getRecipes();
        List<RecipeResource> recipeResourceList = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            recipeResourceList.add(new RecipeResource(recipe));
        }
        return new ResponseEntity<>(new DataResponse(recipeResourceList), HttpStatus.OK);
    }

    /**
     * Retrieve a given recipe.
     *
     * @param id ID of the recipe.
     * @return Given recipe.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getRecipe(@PathVariable("id") String id) {
        Recipe recipe = recipeRepository.getRecipe(Integer.parseInt(id));
        RecipeResource recipeResource = new RecipeResource(recipe);
        return new ResponseEntity<>(new DataResponse(recipeResource), HttpStatus.OK);
    }
}
