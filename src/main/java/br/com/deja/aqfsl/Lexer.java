package br.com.deja.aqfsl;

import java.util.regex.Pattern;

public class Lexer {
    private final TokenCollector collector;
    private int position;

    public Lexer(TokenCollector collector) {
        this.collector = collector;
    }

    public void lex(String query) {
        if (query == null) return;
        query = sanitize(query);
        findTokens(query);
    }

    private void findTokens(String query) {

        if (query.length() == 0) return;

        if (query.startsWith("order=")) {
            findOrderSpecToken(query);
        } else if (query.startsWith("asc")) {
            findAscToken(query);
        } else if (query.startsWith("desc")) {
            findDescToken(query);
        } else if (query.startsWith("sort=")) {
            findSortSpecToken(query);
        } else if (query.startsWith(":")) {
            findColonSpecToken(query);
        } else if (query.startsWith(";")) {
            findSemiColonToken(query);
        } else if (query.startsWith(",")) {
            findCommaToken(query);
        } else if (query.startsWith("=")) {
            findEqualToken(query);
        } else if (query.startsWith("lte")) {
            findLteToken(query);
        } else if (query.startsWith("gte")) {
            findGteToken(query);
        } else if (query.startsWith("lt")) {
            findLtToken(query);
        } else if (query.startsWith("gt")) {
            findGtToken(query);
        } else {
            findNameToken(query);
        }
    }

    private void findDescToken(String query) {
        collector.asc(position);
        query = getQuery(query, "desc");
        findTokens(query);
    }

    private void findColonSpecToken(String query) {
        collector.colon(position);
        query = getQuery(query, ":");
        findTokens(query);
    }

    private void findSemiColonToken(String query) {
        collector.semiColon(position);
        query = getQuery(query, ";");
        findTokens(query);
    }

    private void findCommaToken(String query) {
        collector.comma(position);
        query = getQuery(query, ",");
        findTokens(query);
    }

    private void findEqualToken(String query) {
        collector.equal(position);
        query = getQuery(query, "=");
        findTokens(query);
    }

    private void findGteToken(String query) {
        collector.gte(position);
        query = getQuery(query, "gte");
        findTokens(query);
    }

    private void findLtToken(String query) {
        collector.lt(position);
        query = getQuery(query, "lt");
        findTokens(query);
    }

    private void findGtToken(String query) {
        collector.gt(position);
        query = getQuery(query, "gt");
        findTokens(query);
    }

    private void findSortSpecToken(String query) {
        collector.sortSpec(position);
        query = getQuery(query, "sort=");
        findTokens(query);
    }

    private void findAscToken(String query) {
        collector.asc(position);
        query = getQuery(query, "asc");
        findTokens(query);
    }

    private void findOrderSpecToken(String query) {
        collector.orderSpec(position);
        query = getQuery(query, "order=");
        findTokens(query);
    }

    private void findLteToken(String query) {
        collector.lte(position);
        query = getQuery(query, "lte");
        findTokens(query);
    }

    private void updatePosition(String token) {
        position = position + token.length();
    }

    private void findNameToken(String query) {
        var matcher = Pattern.compile("\\w+").matcher(query);
        if (matcher.find()) {
            var name = matcher.group();
            collector.name(name, position);
            query = getQuery(query, name);
            findTokens(query);
        }
    }

    private String getQuery(String query, String token) {
        var newQuery = query.substring(token.length());
        updatePosition(token);
        return newQuery;
    }

    private String sanitize(String query) {
        return query.trim();
    }
}
