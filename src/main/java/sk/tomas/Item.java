package sk.tomas;

import lombok.Data;

@Data
public class Item{
    private String id;
    private String name;
    private String priceWithWat;
    private String priceWithoutWat;
    private String category;

    public Item(String id, String name, String priceWithWat, String priceWithoutWat, String category) {
        this.id = id.replaceAll("Kód: ", "");
        this.name = name;
        this.priceWithWat = priceWithWat.replaceAll(" s DPH","");
        this.priceWithoutWat = priceWithoutWat;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", priceWithoutWat='" + priceWithoutWat + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
