package com.exraion.beu.adapter.ingredient

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.Ingredient

class IngredientDiffCallback(
    private val old: List<Ingredient>,
    private val new: List<Ingredient>
): BaseDiffUtil<Ingredient, String>(old, new) {
    override fun Ingredient.getItemIdentifier(): String = ingredient
}