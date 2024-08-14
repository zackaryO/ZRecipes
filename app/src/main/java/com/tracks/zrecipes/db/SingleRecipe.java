// java/com/tracks/zrecipes/db/SingleRecipe.java
package com.tracks.zrecipes.db;

import androidx.room.Entity;

@Entity
public class SingleRecipe {
    String id, title, instructions, summary;
    ExtendedIngredient[] extendedIngredients;
    AnalyzedInstruction[] analyzedInstructions;

    public SingleRecipe(String id, String title, String summary, String instructions,
                        ExtendedIngredient[] extendedIngredients, AnalyzedInstruction[] analyzedInstructions) {
        this.id = id;
        this.summary = summary;
        this.title = title;
        this.instructions = instructions;
        this.extendedIngredients = extendedIngredients;
        this.analyzedInstructions = analyzedInstructions;
    }

    public String getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ExtendedIngredient[] getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(ExtendedIngredient[] extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public String getAnalyzedInstructions() {

        return getAnalyzedInstructionsString(analyzedInstructions);
    }

    public String getAnalyzedInstructionsString(AnalyzedInstruction[] analyzedInstructions) {
        StringBuilder sb = new StringBuilder();
        for (AnalyzedInstruction analyzedInstruction : analyzedInstructions) {
            sb.append("Name: " + analyzedInstruction.getName() + "\n");
            for (Step step : analyzedInstruction.getSteps()) {
                sb.append("Step " + step.getNumber() + ": " + step.getStep() + "\n");
                if (step.getIngredients() != null) {
                    for (Ingredient ingredient : step.getIngredients()) {
                        sb.append("Ingredient ID: " + ingredient.getId() + "\n");
                        sb.append("Ingredient Name: " + ingredient.getName() + "\n");
                    }
                }
                if (step.getEquipment() != null) {
                    for (Equipment equipment : step.getEquipment()) {
                        sb.append("Equipment ID: " + equipment.getId() + "\n");
                        sb.append("Equipment Name: " + equipment.getName() + "\n");
                    }
                }
            }
        }
        return sb.toString();
    }







    public void setAnalyzedInstructions(AnalyzedInstruction[] analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }
}
