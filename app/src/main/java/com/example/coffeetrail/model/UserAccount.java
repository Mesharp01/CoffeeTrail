package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;
import java.util.UUID;

@Fts4  /* Supports full-text search */
@Entity(tableName = "useraccount")
public class UserAccount {

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


}