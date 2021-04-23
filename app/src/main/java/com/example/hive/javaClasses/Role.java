package com.example.hive.javaClasses;

public class Role {
    public String nomRole;
    public int idRole;

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public Role(String nomRole, int idRole){
        this.nomRole=nomRole;
        this.idRole=idRole;
    }

    public Role(String nomRole){
        this.nomRole = nomRole;
    }
}
