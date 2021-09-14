package com.example.pavilion.remind2;

/**
 * Created by PAVILION on 20/05/2018.
 */

public class notification {
    private int num;
    private double stats;
    private String titre,module,description;
    public notification(){}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public notification(double stats, String titre, String module, String description, int num) {
        this.stats = stats;
        this.titre = titre;
        this.module = module;
        this.description = description;
        this.num=num;

    }

    public double getStats() {
        return stats;
    }

    public void setStats(double stats) {
        this.stats = stats;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
