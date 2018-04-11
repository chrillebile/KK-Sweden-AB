package server.Repositories;

import org.springframework.stereotype.Repository;
import server.Models.Recipe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class RecipeRepository extends server.Repositories.Repository{

    public List<Recipe> getRecipes(){
        String query = "SELECT * FROM recipes";

        List<Recipe> recipes = null;

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            recipes = new ArrayList<>();
            while(rs.next()){
                recipes.add(new Recipe(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public Recipe getRecipe(Integer id){
        String query = "SELECT * FROM recipes WHERE id=?";

        Recipe recipe = null;
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                recipe = new Recipe(rs.getInt("id"), rs.getString("name"));
            }else {
                throw new NoSuchElementException("Element not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipe;
    }
}
