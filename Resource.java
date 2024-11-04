public class Resource {
    String type; // Type of resource, e.g., "Food", "Water", "Medicine"
    int quantity; // Quantity of the resource

    public Resource(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}