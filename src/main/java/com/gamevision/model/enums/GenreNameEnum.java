package com.gamevision.model.enums;

public enum GenreNameEnum {
    RPG("Role-playing"),
    SANDBOX("Sandbox"),
    STRATEGY("Strategy"),
    SHOOTER("Shooter"),
    SIMULATION("Simulation"),
    SPORTS("Sports"),
    PUZZLE("Puzzle"),
    AA("Action adventure"),
    SH("Survival-horror"),
    PLATFORMER("Platformer");

    private final String genreName;

    GenreNameEnum(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }


}
