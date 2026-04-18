package com.club.escalade.util;

/**
 * Utilitaires de normalisation pour les recherches textuelles.
 */
public final class SearchTextUtils {

    private SearchTextUtils() {
    }

    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static String normalizeForContains(String value) {
        String normalized = normalize(value);
        return normalized == null ? "" : normalized;
    }
}
