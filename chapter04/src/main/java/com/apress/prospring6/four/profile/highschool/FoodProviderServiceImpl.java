package com.apress.prospring6.four.profile.highschool;

import java.util.List;

import com.apress.prospring6.four.profile.Food;
import com.apress.prospring6.four.profile.FoodProviderService;

public class FoodProviderServiceImpl implements FoodProviderService {

    @Override
    public List<Food> provideLunchSet() {
        return List.of(new Food("Coke"), new Food("Hamburger"), new Food("Fries"));
    }
}
