package com.bookspot.test;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;

public class TestInsertUtils {

    public static class LibraryBuilder {
        private Long id = null;
        private String name = "Sample 도서관";
        private String libraryCode = null;
        private double latitude = 0d;
        private double longitude = 0d;

        public LibraryBuilder id(long id) {this.id = id; return this;}
        public LibraryBuilder name(String name) {this.name = name; return this;}
        public LibraryBuilder libraryCode(String libraryCode) {this.libraryCode = libraryCode; return this;}
        public LibraryBuilder latitude(double latitude) {this.latitude = latitude; return this;}
        public LibraryBuilder longitude(double longitude) {this.longitude = longitude; return this;}

        public void insert(JdbcTemplate jdbcTemplate) {
            if(id == null)
                throw new IllegalArgumentException("도서관 Insert 시 ID는 필수");

            jdbcTemplate.update("""
                INSERT INTO bookspot_test.library
                (id, name, library_code, location)
                VALUES(?, ?, ?, ST_GeomFromText(CONCAT('POINT(', ?, ' ', ?, ')'), 4326));
                """, ps -> {
                        ps.setLong(1, id);
                        ps.setString(2, name);
                        ps.setString(3, libraryCode == null ? String.valueOf(id) : libraryCode);
                        ps.setDouble(4, latitude);
                        ps.setDouble(5, longitude);
                    }
            );
        }
    }

    public static class BookBuilder {
        private static final String WITHOUT_ID_QUERY ="""
                INSERT INTO bookspot_test.book
                (isbn13, title, loan_count, created_at, updated_at, monthly_loan_increase, subject_code)
                VALUES(?, ?, ?, ?, NOW(6), ?, ?);
                """;

        private static final String WITH_ID_QUERY ="""
                INSERT INTO bookspot_test.book
                (id, isbn13, title, loan_count, created_at, updated_at, monthly_loan_increase, subject_code)
                VALUES(?, ?, ?, ?, ?, NOW(6), ?, ?);
                """;

        private Long id = null;
        private String isbn13 = null;
        private String title = "Sample Title";
        private LocalDate createdAt = LocalDate.now();
        private int loanCount = 0;
        private int monthlyLoanIncrement = 0;
        private String subjectCode = null;

        public BookBuilder id(long id) {
            this.id = id;
            return this;
        }

        public BookBuilder isbn13(String isbn13) {
            this.isbn13 = isbn13;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder loanCount(int loanCount) {
            this.loanCount = loanCount;
            return this;
        }

        public BookBuilder monthlyLoanIncrement(int monthlyLoanIncrement) {
            this.monthlyLoanIncrement = monthlyLoanIncrement;
            return this;
        }

        public BookBuilder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BookBuilder subjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
            return this;
        }

        public void insert(JdbcTemplate jdbcTemplate) {
            if (id == null && isbn13 == null)
                throw new IllegalArgumentException("책 Insert 시 ISBN13과 ID 둘 중 하나는 필수 설정");

            if (id == null) {
                jdbcTemplate.update(WITHOUT_ID_QUERY, ps -> {
                            ps.setString(1, isbn13);
                            ps.setString(2, title);
                            ps.setInt(3, loanCount);
                            ps.setDate(4, Date.valueOf(createdAt));
                            ps.setInt(5, monthlyLoanIncrement);
                            ps.setString(6, subjectCode);
                        }
                );
                return;
            }

            jdbcTemplate.update(WITH_ID_QUERY, ps -> {
                        ps.setLong(1, id);
                        ps.setString(2, isbn13 == null ? "%013d".formatted(id) : isbn13);
                        ps.setString(3, title);
                        ps.setInt(4, loanCount);
                        ps.setDate(5, Date.valueOf(createdAt));
                        ps.setInt(6, monthlyLoanIncrement);
                        ps.setString(7, subjectCode);
                    }
            );
        }
    }

    public static class LibraryStockBuilder {
        private Long bookId;
        private Long libraryId;
        private LocalDate createdAt = LocalDate.now();
        private LocalDate updatedAt = LocalDate.now();
        private String subjectCode = null;

        private static final String INSERT_SQL = """
                INSERT INTO library_stock
                (book_id, library_id, created_at, updated_at_time, subject_code)
                VALUES(?, ?, ?, ?, ?);
                """;

        public LibraryStockBuilder bookId(long bookId) {
            this.bookId = bookId;
            return this;
        }

        public LibraryStockBuilder libraryId(long libraryId) {
            this.libraryId = libraryId;
            return this;
        }

        public LibraryStockBuilder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public LibraryStockBuilder updatedAt(LocalDate updatedAt) {
            this.createdAt = updatedAt;
            return this;
        }

        public LibraryStockBuilder subjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
            return this;
        }

        public void insert(JdbcTemplate jdbcTemplate) {
            if (bookId == null && libraryId == null)
                throw new IllegalArgumentException("도서관 재고에 (책ID, 도서관ID)는 필수 설정");

            jdbcTemplate.update(INSERT_SQL, ps -> {
                        ps.setLong(1, bookId);
                        ps.setLong(2, libraryId);
                        ps.setDate(3, Date.valueOf(createdAt));
                        ps.setDate(4, Date.valueOf(updatedAt));
                        ps.setString(5, subjectCode);
                    }
            );
        }
    }

    public static LibraryBuilder libraryBuilder() {
        return new LibraryBuilder();
    }
    public static BookBuilder bookBuilder() {
        return new BookBuilder();
    }
    public static LibraryStockBuilder libraryStockBuilder() {
        return new LibraryStockBuilder();
    }
}
