package entry;

import java.io.Serializable;

/**
 * User: Vitaly Sazanovich
 * Date: 12/25/12
 * Time: 1:25 PM
 */
public class Idioom implements Serializable {
    private String idioom;
    private IdiomExplanation[] idiomExplanations;

    public IdiomExplanation[] getIdiomExplanations() {
        return idiomExplanations;
    }

    public void setIdiomExplanations(IdiomExplanation[] idiomExplanations) {
        this.idiomExplanations = idiomExplanations;
    }

    public String getIdioom() {
        return idioom;
    }

    public void setIdioom(String idioom) {
        this.idioom = idioom;
    }
}
