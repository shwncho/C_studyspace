public class Book {
    private String name;
    private int page;

    public boolean equals(Book book){
        return (this.name.equals(book.name) &&
                this.page == book.page);
    }
}
