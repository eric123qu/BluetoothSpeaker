package com.haier.ai.bluetoothspeaker.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qx on 16-12-1.
 */

@Entity
public class Note {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 654067880)
    public Note(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
