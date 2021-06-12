package com.dictiographer.desktop.model.entry.grammar.by;

import java.io.Serializable;

public class GrammarNAZ implements Serializable {
    private String genderKey;

    private Boolean onlySingular;
    private Boolean onlyPlural;

    private String singular;
    private String plural;
    private String singularR;
    private String pluralR;
    private String singularD;
    private String pluralD;
    private String singularV;
    private String pluralV;
    private String singularT;
    private String pluralT;
    private String singularM;
    private String pluralM;


    public String getGenderKey() {
        return genderKey;
    }

    public void setGenderKey(String genderKey) {
        this.genderKey = genderKey;
    }

    public Boolean getOnlySingular() {
        return onlySingular;
    }

    public void setOnlySingular(Boolean onlySingular) {
        this.onlySingular = onlySingular;
    }

    public Boolean getOnlyPlural() {
        return onlyPlural;
    }

    public void setOnlyPlural(Boolean onlyPlural) {
        this.onlyPlural = onlyPlural;
    }

    public String getSingular() {
        return singular;
    }

    public void setSingular(String singular) {
        this.singular = singular;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public String getSingularR() {
        return singularR;
    }

    public void setSingularR(String singularR) {
        this.singularR = singularR;
    }

    public String getPluralR() {
        return pluralR;
    }

    public void setPluralR(String pluralR) {
        this.pluralR = pluralR;
    }

    public String getSingularD() {
        return singularD;
    }

    public void setSingularD(String singularD) {
        this.singularD = singularD;
    }

    public String getPluralD() {
        return pluralD;
    }

    public void setPluralD(String pluralD) {
        this.pluralD = pluralD;
    }

    public String getSingularV() {
        return singularV;
    }

    public void setSingularV(String singularV) {
        this.singularV = singularV;
    }

    public String getPluralV() {
        return pluralV;
    }

    public void setPluralV(String pluralV) {
        this.pluralV = pluralV;
    }

    public String getSingularT() {
        return singularT;
    }

    public void setSingularT(String singularT) {
        this.singularT = singularT;
    }

    public String getPluralT() {
        return pluralT;
    }

    public void setPluralT(String pluralT) {
        this.pluralT = pluralT;
    }

    public String getSingularM() {
        return singularM;
    }

    public void setSingularM(String singularM) {
        this.singularM = singularM;
    }

    public String getPluralM() {
        return pluralM;
    }

    public void setPluralM(String pluralM) {
        this.pluralM = pluralM;
    }

    public boolean isEmpty() {
        return false;
    }
}
