package com.dictiographer.desktop.model.entry.grammar.by;

import java.io.Serializable;

public class GrammarDZE implements Serializable {
    private Boolean nonPersonalForm;
    private Boolean finite;
    private Boolean infinite;

    private String presentSing1;
    private String presentPl1;
    private String presentSing2;
    private String presentPl2;
    private String presentSing3;
    private String presentPl3;
    private String pastSing1;
    private String pastPl1;
    private String pastSing2;
    private String pastPl2;
    private String pastSing3;
    private String pastPl3;

    private String imperSing;
    private String imperPl;

    private String finiteForm;
    private String infiniteForm;

    private String participle;


    public boolean isEmpty() {
        return false;
    }

    public Boolean getNonPersonalForm() {
        return nonPersonalForm;
    }

    public void setNonPersonalForm(Boolean nonPersonalForm) {
        this.nonPersonalForm = nonPersonalForm;
    }

    public Boolean getFinite() {
        return finite;
    }

    public void setFinite(Boolean finite) {
        this.finite = finite;
    }

    public Boolean getInfinite() {
        return infinite;
    }

    public void setInfinite(Boolean infinite) {
        this.infinite = infinite;
    }

    public String getPresentSing1() {
        return presentSing1;
    }

    public void setPresentSing1(String presentSing1) {
        this.presentSing1 = presentSing1;
    }

    public String getPresentPl1() {
        return presentPl1;
    }

    public void setPresentPl1(String presentPl1) {
        this.presentPl1 = presentPl1;
    }

    public String getPresentSing2() {
        return presentSing2;
    }

    public void setPresentSing2(String presentSing2) {
        this.presentSing2 = presentSing2;
    }

    public String getPresentPl2() {
        return presentPl2;
    }

    public void setPresentPl2(String presentPl2) {
        this.presentPl2 = presentPl2;
    }

    public String getPresentSing3() {
        return presentSing3;
    }

    public void setPresentSing3(String presentSing3) {
        this.presentSing3 = presentSing3;
    }

    public String getPresentPl3() {
        return presentPl3;
    }

    public void setPresentPl3(String presentPl3) {
        this.presentPl3 = presentPl3;
    }

    public String getPastSing1() {
        return pastSing1;
    }

    public void setPastSing1(String pastSing1) {
        this.pastSing1 = pastSing1;
    }

    public String getPastPl1() {
        return pastPl1;
    }

    public void setPastPl1(String pastPl1) {
        this.pastPl1 = pastPl1;
    }

    public String getPastSing2() {
        return pastSing2;
    }

    public void setPastSing2(String pastSing2) {
        this.pastSing2 = pastSing2;
    }

    public String getPastPl2() {
        return pastPl2;
    }

    public void setPastPl2(String pastPl2) {
        this.pastPl2 = pastPl2;
    }

    public String getPastSing3() {
        return pastSing3;
    }

    public void setPastSing3(String pastSing3) {
        this.pastSing3 = pastSing3;
    }

    public String getPastPl3() {
        return pastPl3;
    }

    public void setPastPl3(String pastPl3) {
        this.pastPl3 = pastPl3;
    }

    public String getImperSing() {
        return imperSing;
    }

    public void setImperSing(String imperSing) {
        this.imperSing = imperSing;
    }

    public String getImperPl() {
        return imperPl;
    }

    public void setImperPl(String imperPl) {
        this.imperPl = imperPl;
    }

    public String getFiniteForm() {
        return finiteForm;
    }

    public void setFiniteForm(String finiteForm) {
        this.finiteForm = finiteForm;
    }

    public String getInfiniteForm() {
        return infiniteForm;
    }

    public void setInfiniteForm(String infiniteForm) {
        this.infiniteForm = infiniteForm;
    }

    public String getParticiple() {
        return participle;
    }

    public void setParticiple(String participle) {
        this.participle = participle;
    }
}
