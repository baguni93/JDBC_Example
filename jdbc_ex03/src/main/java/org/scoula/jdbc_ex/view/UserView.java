package org.scoula.jdbc_ex.view;

import org.scoula.jdbc_ex.dao.UserDao;
import org.scoula.jdbc_ex.dao.UserDaoImpl;
import org.scoula.jdbc_ex.domain.Point;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private final Scanner sc;
    private final UserDaoImpl userDao;


    public UserView() {
        this.sc = new Scanner(System.in);
        this.userDao = new UserDaoImpl();
    }

    public void run() throws SQLException {

        while(true){
            int choice = selectMenu();

            if      (choice == 1) { createUser(); }
            else if (choice == 2) { getList(); }
            else if (choice == 3) { getUser(); }
            else if (choice == 4) { updateUser(); }
            else if (choice == 5) { deleteUser(); }
            else if (choice == 6) { showListWithPoint(); }
            else if (choice == 7) { showMaxPointUser(); }
            else if (choice == 9) { onClose(); }
            else { System.out.println("선택이 올바르지 않습니다."); }
        }

    }

    private int selectMenu() {

        System.out.println("\n=============================");
        System.out.println("  회원 관리 시스템");
        System.out.println("=============================");
        System.out.println(" 1. 회원 등록");
        System.out.println(" 2. 회원 목록 조회");
        System.out.println(" 3. 회원 1명 조회");
        System.out.println(" 4. 회원 수정");
        System.out.println(" 5. 회원 삭제");
        System.out.println("-----------------------------");
        System.out.println(" 6. 포인트 가 있는 회원만 조회");
        System.out.println(" 7. 가장 높은 포인트를 갖는 회원 조회");
        System.out.println("-----------------------------");
        System.out.println(" 9. 종료");
        System.out.println("=============================");
        System.out.print("선택>> ");

        int id = Integer.parseInt(sc.nextLine());

        return id;
    }


    private void createUser() {
        System.out.println("\n--- 회원 등록 ---");

        System.out.print("ID       : ");
        String id = sc.nextLine();
        if (id.isBlank()) {
            System.out.println("ID는 필수입니다.");
            return;
        }

        System.out.print("PASSWORD : ");
        String password = sc.nextLine();

        System.out.print("NAME     : ");
        String name = sc.nextLine();

        System.out.print("ROLE     : ");
        String role = sc.nextLine();

        UserVO userVo  = new UserVO(id, password,name,role , null);
        int count = userDao.create(userVo);

        if(count ==1){
            System.out.println("등록 완료");
        }
        else{
            System.out.println("등록 실패");
        }

    }

    private void getList() throws SQLException {
        System.out.println("\n--- 회원 목록 ---");

        var userList = userDao.getList();
        this.printList(userList);

    }

    private void getUser() throws SQLException {
        System.out.println("\n--- 회원 조회 ---");

        System.out.print("조회 할 ID       : ");
        String id = sc.nextLine();

        var userOpt =  userDao.get(id);

        if(userOpt.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }

        userOpt.ifPresent(System.out::println);
    }

    private void updateUser() throws SQLException {
        System.out.println("\n--- 회원 수정 ---");

        System.out.print("수정 할 ID       : ");
        String id = sc.nextLine();

        int count = userDao.update(id , "수정" , "관리자");

        if(count == 1) {
            System.out.println("수정 완료");
        }
        else{
            System.out.println("등록된 회원이 없습니다.");
        }
    }

    private void deleteUser() throws SQLException {
        System.out.println("\n--- 회원 삭제 ---");

        System.out.print("수정 할 ID       : ");
        String id = sc.nextLine();

        int count = userDao.delete(id);

        if(count == 1) {
            System.out.println("삭제 완료");
        }
        else{
            System.out.println("등록된 회원이 없습니다.");
        }
    }

    private void showListWithPoint() throws SQLException {

        System.out.println("\n--- 포인트 가 있는 회원만 조회 ---");

        var userList = userDao.getShowListWithPoint();
        this.printList(userList);
    }


    private void showMaxPointUser() throws SQLException {

        System.out.println("\n--- 가장 높은 포인트를 갖는 회원 조회---");

        var userOpt = userDao.getShowMaxPointUser();

        if(userOpt.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
        }
        else{
            System.out.println(userOpt.get());
        }


    }


    private void onClose(){
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);
    }

    private void printList(List<UserVO> userList){
        System.out.println("\n--- 회원 목록 ---");

        if(userList.isEmpty()){

            System.out.println("등록된 회원이 없습니다.");
        }
        else{
            for(UserVO user : userList){
                System.out.println(
                        "[ID] " + user.getId() +
                                " | [NAME] " + user.getName() +
                                " | [ROLE] " + user.getRole() +
                                " | [POINT] " + user.getPoints()
                );
            }
        }

    }
}
