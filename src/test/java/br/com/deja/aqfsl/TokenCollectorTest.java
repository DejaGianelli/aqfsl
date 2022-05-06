package br.com.deja.aqfsl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Check if token are collected correctly in order")
class TokenCollectorTest implements TokenCollector {

    private String tokenStream;
    private int currentPosition;
    private static final String SEPARATOR = ",";

    @BeforeEach
    void setUp() {
        tokenStream = "";
    }

    @Test
    void complexSequence() {
        var query = "id=gt:100,price=gte:10,price=lte:100;sort=created;order=asc";
        var lexer = new Lexer(this);

        lexer.lex(query);

        assertTokenStream(
                "#id#,=,gt,:,#100#,',',#price#,=,gte,:,#10#,',',#price#,=,lte,:,#100#,;,sort=,#created#,;,order=,asc",
                tokenStream);
    }

    @Test
    void sequence() {
        var query = "price=lte:100;sort=created;order=asc";
        var lexer = new Lexer(this);

        lexer.lex(query);

        assertTokenStream("#price#,=,lte,:,#100#,;,sort=,#created#,;,order=,asc", tokenStream);
    }

    @Test
    void onlyOrder() {
        var query = "order=asc";
        var lexer = new Lexer(this);

        lexer.lex(query);

        assertTokenStream("order=,asc", tokenStream);
    }

    @Test
    void onlySort() {
        var query = "sort=created";
        var lexer = new Lexer(this);

        lexer.lex(query);

        assertTokenStream("sort=,#created#", tokenStream);
    }

    @Test
    void empty() {
        var query = "";
        var lexer = new Lexer(this);

        lexer.lex(query);

        assertTokenStream("", tokenStream);
    }

    @Override
    public void name(String name, int pos) {
        concatToken("#" + name + "#");
        this.currentPosition = pos;
    }

    @Override
    public void orderSpec(int pos) {
        concatToken("order=");
        this.currentPosition = pos;
    }

    @Override
    public void asc(int pos) {
        concatToken("asc");
        this.currentPosition = pos;
    }

    @Override
    public void desc(int pos) {
        concatToken("desc");
        this.currentPosition = pos;
    }

    @Override
    public void sortSpec(int pos) {
        concatToken("sort=");
        this.currentPosition = pos;
    }

    @Override
    public void colon(int pos) {
        concatToken(":");
        this.currentPosition = pos;
    }

    @Override
    public void semiColon(int pos) {
        concatToken(";");
        this.currentPosition = pos;
    }

    @Override
    public void comma(int pos) {
        concatToken("','");
        this.currentPosition = pos;
    }

    @Override
    public void equal(int pos) {
        concatToken("=");
        this.currentPosition = pos;
    }

    @Override
    public void lte(int pos) {
        concatToken("lte");
        this.currentPosition = pos;
    }

    @Override
    public void gte(int pos) {
        concatToken("gte");
        this.currentPosition = pos;
    }

    @Override
    public void lt(int pos) {
        concatToken("lt");
        this.currentPosition = pos;
    }

    @Override
    public void gt(int pos) {
        concatToken("gt");
        this.currentPosition = pos;
    }

    private void concatToken(String token) {
        tokenStream = tokenStream.concat(token + SEPARATOR);
    }

    private String removeLastComma(String tokenStream) {
        if (!tokenStream.isEmpty())
            return tokenStream.substring(0, tokenStream.length() - 1);
        return tokenStream;
    }

    private void assertTokenStream(String expected, String tokenStream) {
        assertEquals(expected, removeLastComma(tokenStream));
    }
}