package com.drinksleo.controller;

import com.drinksleo.dao.Ingredient;
import com.drinksleo.dao.MeasureTypes;
import com.drinksleo.service.IngredientServiceInterface;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    IngredientServiceInterface ingredientService;


    @GetMapping("/all")
    public List<Ingredient> getAll(){
        return ingredientService.getAll();
    }

    @GetMapping("/allMeasureTypes")
    public List<String>  getAllMeasureTypes(){
        //MeasureTypes result[] = MeasureTypes.values();
        List<String> result = new ArrayList<>();
        for(MeasureTypes x : MeasureTypes.values()){
            result.add(x.toString()); //.getDescricao()
        }
        return result;
    }
}
