package huongdan.hungnguyenco.hungnguyenvn.gridviewdemo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung on 2/6/2017.
 */

public class Cake {
    private String key;
    private String name;
    private String kind;
    private String price;
    private String des;
    private String image;

    public Cake() {
    }

    public Cake(String name, String kind, String price, String des, String image) {
        this.name = name;
        this.kind = kind;
        this.price = price;
        this.des = des;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("kind", kind);
        result.put("price", price);
        result.put("des", des);
        result.put("image", image);

        return result;
    }

    public void setNewMenu(Cake newCake){
        name = newCake.name;
        kind = newCake.kind;
        price = newCake.price;
        des = newCake.des;
        image = newCake.image;
    }
}
