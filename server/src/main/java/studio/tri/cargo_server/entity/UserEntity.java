package studio.tri.cargo_server.entity;
import com.fasterxml.jackson.annotation.*;

public class UserEntity {

    @JsonProperty("uid")
    private int uid;
    @JsonProperty("username")
    private String username;
    @JsonProperty("passwd")
    private String passwd;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("type")
    private int type;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
