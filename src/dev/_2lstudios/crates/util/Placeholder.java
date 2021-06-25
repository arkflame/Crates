package dev._2lstudios.crates.util;

public class Placeholder {
    private final String replaced;
    private final String replacement;

    public Placeholder(final String replaced, final String replacement) {
        this.replaced = replaced;
        this.replacement = replacement;
    }

    public Placeholder(final String replaced, final int amount) {
        this.replaced = replaced;
        this.replacement = String.valueOf(amount);
    }

    public String getReplaced() {
        return replaced;
    }

    public String getReplacement() {
        return replacement;
    }
}
