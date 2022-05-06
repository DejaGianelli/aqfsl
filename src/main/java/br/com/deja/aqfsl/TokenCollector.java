package br.com.deja.aqfsl;

public interface TokenCollector {
    void name(String name, int pos);
    void orderSpec(int pos);
    void asc(int pos);
    void desc(int pos);
    void sortSpec(int pos);
    void colon(int pos);
    void semiColon(int pos);
    void comma(int pos);
    void equal(int pos);
    void lte(int pos);
    void gte(int pos);
    void lt(int pos);
    void gt(int pos);
}
