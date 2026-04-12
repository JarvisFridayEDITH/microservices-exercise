package cart_service.entiy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class CartEntity {
    @GeneratedValue
    @Id
    int id;
    int userid;

    public CartEntity() {
    }

    public CartEntity(int userid) {
        this.userid = userid;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserid() {
        return userid;
    }



}
