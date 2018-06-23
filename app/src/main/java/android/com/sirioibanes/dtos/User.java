package android.com.sirioibanes.dtos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.AbstractMap;

public class User implements Parcelable {
    private String email;
    private String nickname;
    private String telefono;
    private String nombre;
    private String apellido;
    private AbstractMap<String, Boolean> eventos;

    public User(@NonNull final String nombre, @NonNull final String apellido, @NonNull final String nickName,
                @NonNull final String email, @NonNull final String telefono,
                @NonNull final AbstractMap<String, Boolean> eventos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickName;
        this.email = email;
        this.telefono = telefono;
        this.eventos = eventos;
    }

    public User() {

    }

    protected User(final Parcel in) {
        email = in.readString();
        nickname = in.readString();
        telefono = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        in.readMap(eventos, Boolean.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(final Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(final int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public AbstractMap<String, Boolean> getEventos() {
        return eventos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(email);
        dest.writeString(nickname);
        dest.writeString(telefono);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeMap(eventos);
    }

    public void setEventos(AbstractMap<String, Boolean> eventos) {
        this.eventos = eventos;
    }
}