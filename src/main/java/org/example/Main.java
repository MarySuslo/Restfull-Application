package org.example;

import org.example.db.ConnectionManagerImpl;

public class Main {
    public static void main(String[] args) {
        new DataBaseContext().init(new ConnectionManagerImpl().getConnection());
    }
}