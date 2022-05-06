package br.com.deja.aqfsl;

import java.util.ArrayList;
import java.util.List;

public class AQFSLSyntax {
    public List<FilterSpec> filterSpecs = new ArrayList<>();
    public SortSpec sortSpec;
    public OrderSpec orderSpec;
    public List<SyntaxErrors> syntaxErrors = new ArrayList<>();
    public boolean done = false;

    public static class FilterSpec {
        public String name;
        public FilterExpression filterExpression;
    }

    public static class SortSpec {
        public String sortBy;
    }

    public static class OrderSpec {
        public String order;
    }

    public static class FilterExpression {
        public String operator;
        public String filter;
    }

    public static class SyntaxErrors {
    }
}
