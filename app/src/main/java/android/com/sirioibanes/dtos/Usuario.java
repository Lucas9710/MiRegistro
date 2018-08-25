package android.com.sirioibanes.dtos;

public class Usuario {

    public String nombre;
    public String apellido;
    public String telefono;
    public String nickname;
    public String mail;

    public Usuario(String nombre, String apellido, String telefono, String nickname, String mail) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.nickname = nickname;
        this.mail = mail;
    }
}
