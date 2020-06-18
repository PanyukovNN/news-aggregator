package com.zylex.newsagregator.controller;

import com.zylex.newsagregator.model.News;
import com.zylex.newsagregator.repository.NewsEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/news")
public class NewsController {

    public static final int PAGE_SIZE = 9;

    private final NewsEntryRepository newsEntryRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public NewsController(NewsEntryRepository newsEntryRepository) {
        this.newsEntryRepository = newsEntryRepository;
    }

    @GetMapping(value = "/", produces = "text/html")
    public String getAllRecipes(Model model) {
        List<News> newsEntries = newsEntryRepository.findAll();

        model.addAttribute("news", newsEntries);

        return "newsList";
    }

//    @ResponseBody
//    @GetMapping(value = "/", produces = "application/json")
//    public Page<RecipeCardDto> getRecipesPage(@RequestParam int page) {
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));
//
//        return recipeRepository.findAllByToPublicationIsTrue(pageable).map(RecipeCardDto::new);
//    }

//    @GetMapping(value = "/{id}", produces = "text/html")
//    public String getRecipe(@PathVariable long id,
//                            HttpServletRequest request,
//                            Model model) {
//        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
//        if (!recipe.getSections().isEmpty()) {
//            Section section = recipe.getSections().get(0);
//            if (section != null) {
//                List<Recipe> similarRecipes = recipeRepository.findTop7BySectionsContaining(section);
//                similarRecipes.remove(recipe);
//                if (similarRecipes.size() > 6) {
//                    similarRecipes.remove(6);
//                }
//                model.addAttribute("similarRecipes", similarRecipes);
//            }
//        }
//        recipe.incrementViews();
//        if (recipe.getId() != 0) {
//            recipeRepository.save(recipe);
//        }
//        model.addAttribute("recipe", recipe);
//
//        User user = (User) recipe.getAuthor();
//        model.addAttribute("authorRecipesNumber", recipeRepository.countByAuthor(user));
//
//        request.getSession().setAttribute("url_prior_login", request.getHeader("Referer"));
//
//        return "recipe";
//    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = "application/json")
    public News getRecipeJson(@PathVariable long id) {
        return newsEntryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

//    @GetMapping("/add")
//    public String getSaveRecipe(Model model) {
//        model.addAttribute("allSections", sectionRepository.findAll());
//        return "recipeSaveUpdate";
//    }

//    @PostMapping("/add")
//    public String postSaveRecipe(
//            @RequestParam String name,
//            @RequestParam String description,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam String cookTime,
//            @RequestParam String serving,
//            @RequestParam String complexity,
//            @RequestParam(required = false) Long dish,
//            @RequestParam String ingredients,
//            @RequestParam String method,
////            @RequestParam(required = false, name = "sections") List<String> sectionNameList,
//            @RequestParam Long section,
//            @RequestParam String toPublication) throws IOException {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) userService.loadUserByUsername(authentication.getName());
//
//        Recipe newRecipe = new Recipe(name,
//                description,
//                cookTime == null || cookTime.isEmpty()
//                        ? LocalTime.of(0, 0)
//                        : LocalTime.parse(cookTime),
//                serving == null || serving.isEmpty()
//                        ? 0
//                        : Integer.parseInt(serving),
//                complexity,
//                splitByNewLine(ingredients),
//                method,
//                dish != null && dish != 0
//                        ? Arrays.asList(dishRepository.findById(dish).orElseThrow(IllegalArgumentException::new))
//                        : Collections.emptyList(),
////                sectionNameList != null
////                        ? sectionNameList.stream().map(sectionRepository::findByName).collect(Collectors.toList())
////                        : Collections.emptyList(),
//                section != null && section != 0
//                        ? Arrays.asList(sectionRepository.findById(section).orElseThrow(IllegalArgumentException::new))
//                        : Collections.emptyList(),
//                user,
//                !toPublication.isEmpty());
//
//        if (newRecipe.isToPublication()) {
//            newRecipe.setPublicationDateTime(LocalDateTime.now());
//        }
//
//        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
//            String resultFileName = uploadFile(file);
//            newRecipe.setMainImage(resultFileName);
//        }
//
//        recipeRepository.save(newRecipe);
//
//        return "redirect:/recipe/" + newRecipe.getId();
//    }

//    @GetMapping("/edit")
//    public String getUpdateRecipe(
//            @RequestParam long id,
//            Model model) {
//        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
//        model.addAttribute("recipe", recipe);
//        model.addAttribute("allSections", sectionRepository.findAll());
//
//        return "recipeSaveUpdate";
//    }

//    @PostMapping("/edit")
//    public String postUpdateRecipe(
//            @RequestParam long id,
//            @RequestParam String name,
//            @RequestParam String description,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("cookTime") String cookTimeStr,
//            @RequestParam String serving,
//            @RequestParam String complexity,
//            @RequestParam(required = false) Long dish,
//            @RequestParam String ingredients,
//            @RequestParam String method,
////            @RequestParam(required = false, name = "sections") List<String> sectionNameList,
//            @RequestParam Long section,
//            @RequestParam String toPublication) throws IOException {
//        Recipe editedRecipe = recipeRepository.findById(id).orElse(new Recipe());
//        editedRecipe.setName(name);
//        editedRecipe.setDescription(description);
//        editedRecipe.setCookTime(cookTimeStr == null || cookTimeStr.isEmpty()
//                ? LocalTime.of(0, 0)
//                : LocalTime.parse(cookTimeStr));
//        if (serving == null || serving.isEmpty()) {
//            editedRecipe.setServing(0);
//        } else {
//            editedRecipe.setServing(Integer.parseInt(serving));
//        }
//        editedRecipe.setMethod(method);
////        if (sectionNameList != null) {
////            editedRecipe.setSections(sectionNameList.stream().map(sectionRepository::findByName).collect(Collectors.toList()));
////        } else {
////            editedRecipe.setSections(Collections.emptyList());
////        }
//        editedRecipe.getSections().clear();
//        if (section != null && section != 0) {
//            editedRecipe.getSections().add(sectionRepository.findById(section).orElseThrow(IllegalArgumentException::new));
//        }
//        editedRecipe.setComplexity(complexity);
//
//        editedRecipe.setIngredients(splitByNewLine(ingredients));
//        boolean toPublicationBool = !toPublication.isEmpty();
//        if (toPublicationBool) {
//            editedRecipe.setPublicationDateTime(LocalDateTime.now());
//        }
//        editedRecipe.setToPublication(toPublicationBool);
//        editedRecipe.getDishes().clear();
//        if (dish != null && dish != 0) {
//            editedRecipe.getDishes().add(dishRepository.findById(dish).orElseThrow(IllegalArgumentException::new));
//        }
//
//        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
//            String resultFileName = uploadFile(file);
//            editedRecipe.setMainImage(resultFileName);
//        }
////        editedRecipe.setPublicationDateTime(LocalDateTime.now());
//        recipeRepository.save(editedRecipe);
//
//        return "redirect:/recipe/" + id;
//    }

//    @GetMapping("/delete")
//    public String getDeleteRecipe(@RequestParam long id,
//                                  HttpServletRequest request) {
//        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
//        if (recipe.getName() != null) {
//            recipeRepository.delete(recipe);
//        }
//
//        HttpSession session = request.getSession();
//        if (session != null) {
//            String redirectUrl = (String) session.getAttribute("url_prior_login");
//            if (redirectUrl != null) {
//                session.removeAttribute("url_prior_login");
//                return "redirect:" + redirectUrl;
//            }
//        }
//
//        return "redirect:/";
//    }

    private String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            uploadDir.mkdir();
        }
        String uidFile = UUID.randomUUID().toString();
        String resultFileName = uidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }

    private List<String> splitByNewLine(@RequestParam String ingredients) {
        return Arrays.stream(ingredients.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
