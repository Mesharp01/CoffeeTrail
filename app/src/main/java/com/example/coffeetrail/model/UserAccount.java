package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

//@Fts4  /* Supports full-text search */
@Entity(tableName = "useraccount", indices = {@Index(value = {"name"},
        unique = true)})
public class UserAccount implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mUid;

    @NonNull
    @ColumnInfo(name = "name")
    public String mName;
    @NonNull
    @ColumnInfo(name = "password")
    public String mPassword;
    public UserAccount(@NonNull String name,
                       @NonNull String password) {
        mName = name;
        mPassword = password;
    }


    public String getName() { return mName; }
    public String getPassword() { return mPassword; }

    public int getId(){return mUid;}
    /* equals(), hashCode(), and toString() methods */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return mName.equals(that.mName) && mPassword.equals(that.mPassword);
    }
    @Override
    public int hashCode() {
        return Objects.hash(mUid, mName, mPassword);
    }
    @NonNull
    @Override
    public String toString() {
        return "UserAccount{" +
                "uid=" + mUid +
                "; name='" + mName + '\'' +
                "; password='" + mPassword + '\'' +
                '}';
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }
            if (hasUppercase && hasLowercase && hasNumber) {
                break;
            }
        }

        if (!hasUppercase || !hasLowercase || !hasNumber) {
            return false;
        }
        return true;
    }



}