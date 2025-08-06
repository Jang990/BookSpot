package com.bookspot.book.infra.search.cond;

import lombok.Getter;

@Getter
public class BookCategoryCond {

    private static final String VALUE_FORMAT = "%03d.%s";
    protected static final String TOP_FILED = "book_categories.top_category";
    protected static final String MID_FIELD = "book_categories.mid_category";
    protected static final String LEAF_FIELD = "book_categories.leaf_category";

    private String categoryField;
    private String categoryValue;

    private BookCategoryCond(String categoryField, String categoryValue) {
        this.categoryField = categoryField;
        this.categoryValue = categoryValue;
    }

    public static BookCategoryCond top(int categoryId, String categoryName) {
        String categoryValue = buildValue(categoryId, categoryName);
        return new BookCategoryCond(
                TOP_FILED,
                categoryValue
        );
    }

    public static BookCategoryCond mid(int categoryId, String categoryName) {
        String categoryValue = buildValue(categoryId, categoryName);
        return new BookCategoryCond(
                MID_FIELD,
                categoryValue
        );
    }

    public static BookCategoryCond leaf(int categoryId, String categoryName) {
        String categoryValue = buildValue(categoryId, categoryName);
        return new BookCategoryCond(
                LEAF_FIELD,
                categoryValue
        );
    }


    private static String buildValue(int categoryId, String categoryName) {
        return VALUE_FORMAT.formatted(categoryId, categoryName);
    }
}
