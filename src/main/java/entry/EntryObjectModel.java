package entry;


import java.io.Serializable;

/**
 * User: Vitaly Sazanovich
 * Date: 20/11/12
 * Time: 17:01
 * Email: Vitaly.Sazanovich@gmail.com
 */
public class EntryObjectModel implements Serializable {
    private String version;//not rendered
    private String headword;//not rendered

    private String syllables;
    private EntryLink[] zie;
    private PartOfSpeech[] partOfSpeeches;
    private Idioom[] idioms;


    public String getPreparedSyllables() {
        if (syllables != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("<span class='hw'>");
            sb.append(syllables.replaceAll("\\|", "&#183;").replaceAll("\\[", "<span class='stress'>").replaceAll("\\]", "</span>"));
            sb.append("</span>");
            return sb.toString();
        } else {
            return "";
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public EntryLink[] getZie() {
        return zie;
    }

    public void setZie(EntryLink[] zie) {
        this.zie = zie;
    }

    public Idioom[] getIdioms() {
        return idioms;
    }

    public void setIdioms(Idioom[] idioms) {
        this.idioms = idioms;
    }

    public String getSyllables() {
        return syllables;
    }

    public void setSyllables(String syllables) {
        this.syllables = syllables;
    }

    public PartOfSpeech[] getPartOfSpeeches() {
        return partOfSpeeches;
    }

    public void setPartOfSpeeches(PartOfSpeech[] partOfSpeeches) {
        this.partOfSpeeches = partOfSpeeches;
    }

    public String getHeadword() {
        return headword;
    }

    public void setHeadword(String headword) {
        this.headword = headword;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(headword);
        if (syllables != null) {
            sb.append("\n");
            sb.append("\t");
            sb.append(syllables);
        }
        return sb.toString();
    }
}
