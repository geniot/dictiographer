package entry;


import entry.grammar.by.GrammarDZE;
import entry.grammar.by.GrammarNAZ;
import entry.grammar.by.GrammarPRY;
import entry.grammar.nl.GrammarBNW;
import entry.grammar.nl.GrammarVNW;
import entry.grammar.nl.GrammarWEW;
import entry.grammar.nl.GrammarZNW;
import entry.grammar.nl.GrammarZNW;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * User: Vitaly Sazanovich
 * Date: 27/11/12
 * Time: 19:21
 * Email: Vitaly.Sazanovich@gmail.com
 */
public class Grammar implements Serializable {

    private GrammarDZE grammarDZE;
    private GrammarNAZ grammarNAZ;
    private GrammarPRY grammarPRY;

    private GrammarBNW grammarBNW;
    private GrammarVNW grammarVNW;
    private GrammarWEW grammarWEW;
    private GrammarZNW grammarZNW;

    private String posKey;


    public boolean isEmpty() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                Object o = f.get(this);
                if (o != null) {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public String getPosKey() {
        return posKey;
    }

    public void setPosKey(String posKey) {
        this.posKey = posKey;
    }

    public GrammarNAZ getGrammarNAZ() {
        return grammarNAZ;
    }

    public void setGrammarNAZ(GrammarNAZ grammarNAZ) {
        this.grammarNAZ = grammarNAZ;
    }

    public GrammarBNW getGrammarBNW() {
        return grammarBNW;
    }

    public void setGrammarBNW(GrammarBNW grammarBNW) {
        this.grammarBNW = grammarBNW;
    }

    public GrammarDZE getGrammarDZE() {
        return grammarDZE;
    }

    public void setGrammarDZE(GrammarDZE grammarDZE) {
        this.grammarDZE = grammarDZE;
    }

    public GrammarVNW getGrammarVNW() {
        return grammarVNW;
    }

    public void setGrammarVNW(GrammarVNW grammarVNW) {
        this.grammarVNW = grammarVNW;
    }

    public GrammarWEW getGrammarWEW() {
        return grammarWEW;
    }

    public void setGrammarWEW(GrammarWEW grammarWEW) {
        this.grammarWEW = grammarWEW;
    }

    public GrammarZNW getGrammarZNW() {
        return grammarZNW;
    }

    public void setGrammarZNW(GrammarZNW grammarZNW) {
        this.grammarZNW = grammarZNW;
    }

    public GrammarPRY getGrammarPRY() {
        return grammarPRY;
    }

    public void setGrammarPRY(GrammarPRY grammarPRY) {
        this.grammarPRY = grammarPRY;
    }
}
