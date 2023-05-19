package nt.uz.ecommerce.dto;

public enum OrderStatus {
    BEING_COLLECTED("BEING_COLLECTED"),
    DELIVERED("DELIVERED"),
    CLOSED("CLOSED");
    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
