package org.scoula.jdbc_ex;

import org.scoula.jdbc_ex.view.UserView;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserView userView = new UserView();
        userView.run();

    }

}
