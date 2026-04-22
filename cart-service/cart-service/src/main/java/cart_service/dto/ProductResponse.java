    package cart_service.dto;

    public class ProductResponse {

        private Integer id;
        private String name;
        private Double price;
        private Integer stock;

        public ProductResponse() {}

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }
    }
