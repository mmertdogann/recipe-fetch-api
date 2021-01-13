package com.mendix.recipe.fetch.api.helper;

import com.mendix.recipe.fetch.api.model.Amount;
import com.mendix.recipe.fetch.api.model.Direction;
import com.mendix.recipe.fetch.api.model.Ingredient;
import com.mendix.recipe.fetch.api.model.Recipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmlHelper {

    final String path = new File("src/main/resources/recipes/").getAbsolutePath();

    private List<Recipe> recipes = new ArrayList<>();

    public static XmlHelper instance;

    public static List<Path> listFiles(Path path) throws IOException {

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;
    }

    public void parseXml() {
        List<Path> paths = null;
        try {
            paths = listFiles(Paths.get(path));

            for (Path path : paths) {
                Set<String> categories = new HashSet<>();
                List<Ingredient> ingredients = new ArrayList<>();
                Direction direction = new Direction();
                Recipe recipe = new Recipe();
                File fXmlFile = new File(path.toUri());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                // Parse title
                recipe.setTitle(doc.getElementsByTagName("title").item(0).getTextContent());

                // Parse yield
                recipe.setYield(doc.getElementsByTagName("yield").item(0).getTextContent());

                // Parse each category item
                NodeList categoryNodeList = doc.getElementsByTagName("cat");

                for (int temp = 0; temp < categoryNodeList.getLength(); temp++) {
                    categories.add(categoryNodeList.item(temp).getTextContent());
                }

                // Parse each ingredient and its amount
                NodeList ingredientNodeList = doc.getElementsByTagName("ing");

                for (int temp = 0; temp < ingredientNodeList.getLength(); temp++) {
                    if (ingredientNodeList.item(temp).getNodeType() == Node.ELEMENT_NODE) {
                        Element ingredient = (Element) ingredientNodeList.item(temp);
                        Ingredient newIngredient = new Ingredient(ingredient.getElementsByTagName("item").item(0).getTextContent(),
                                new Amount(ingredient.getElementsByTagName("qty").item(0).getTextContent(),
                                        ingredient.getElementsByTagName("unit").item(0).getTextContent()));

                        ingredients.add(newIngredient);
                    }
                }

                // Parse direction and set the step
                direction.setStep(doc.getElementsByTagName("directions").item(0).getTextContent());

                recipe.setCategories(categories);
                recipe.setIngredients(ingredients);
                recipe.setDirections(direction);

                recipes.add(recipe);
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
