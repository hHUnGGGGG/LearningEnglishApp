package Scripts.Module;

public class CardModule {
    private String word;
    private String define;

    public CardModule () {

    }

    public CardModule (String word, String define) {
        setWord(word);
        setDefine(define);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefine() {
        return define;
    }

    public void setDefine(String define) {
        this.define = define;
    }
}
