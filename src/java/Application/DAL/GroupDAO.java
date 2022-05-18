package Application.DAL;

import Application.BE.Account;
import Application.BE.Citizen;
import Application.BE.Group;
import Application.BE.School;
import Application.DAL.DBConnector.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {

    private long return_id = -1;

    public Boolean create(Group input) {
        Boolean worked = false;
        String sqlCreateGroup = """
                INSERT INTO [Group] 
                (groupName, FK_Citizen)
                VALUES
                (?, ?)
                """;


        String sqlAccountGroup = """
                INSERT INTO AccountGroup 
                (FK_GroupID, FK_MemberID)
                VALUES
                (?, ?)
                """;
        Connection conn = DBConnectionPool.getInstance().checkOut();

        try {
            PreparedStatement pscg = conn.prepareStatement(sqlCreateGroup, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement psag = conn.prepareStatement(sqlAccountGroup);

            pscg.setString(1,input.getGroupName());
            pscg.setInt(2,input.getCitizen().getId());

            ResultSet rs = pscg.getGeneratedKeys();
            if (rs.next())
                this.return_id = rs.getInt(1);

            for (Account account: input.getGroupMembers()){
                psag.setLong(1,return_id);
                psag.setInt(2,input.getCitizen().getId());
                worked = psag.execute();
            }
            psag.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return worked;
    }

    public void updateGroup(Group input) throws SQLException {
        String sqlUpdate = """
                UPDATE Group
                SET groupName = ?,
                FK_Citizen = ?    
                WHERE GID = ?            
                """;

        Connection conn = DBConnectionPool.getInstance().checkOut();
        try {
            PreparedStatement psug = conn.prepareStatement(sqlUpdate);
            psug.setString(1,input.getGroupName());
            psug.setInt(2,input.getCitizen().getId());
            psug.setInt(3,input.getId());

            psug.execute();

            // updateGroupMembers is being called after the main baseGroup information have been changed.
            updateGroupMembers(input);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private Boolean updateGroupMembers(Group input)
    {
        Boolean worked = false;
        String sqlDeleteMembers = """
                DELETE FROM AccountGroup
                WHERE FK_MemberID = ?
                """;

        String sqlNewGroupMembers = """
                INSERT INTO AccountGroup
                (FK_GroupID, FK_MemberID)
                VALUES (?, ?)
                """;

        Connection conn = DBConnectionPool.getInstance().checkOut();
        try {
            PreparedStatement psgm = conn.prepareStatement(sqlDeleteMembers);
            PreparedStatement psng = conn.prepareStatement(sqlNewGroupMembers);
            psgm.setInt(1,input.getId());

            if (psgm.execute()) {
                for (Account account : input.getGroupMembers()) {
                    psng.setInt(1, input.getId());
                    psng.setInt(2, account.getId());
                    worked = psng.execute();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return worked;
    }

    public Group read(int id) {
        String sqlread = """
                SELECT 
                GID, groupName, FK_Citizen
                FROM [Group]
                WHERE GID = ?,
                """;
        Connection conn = DBConnectionPool.getInstance().checkOut();
        try {
            PreparedStatement psrg = conn.prepareStatement(sqlread);
            psrg.setInt(1, id);

            ResultSet rs = psrg.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("GID");
                String groupName = rs.getString("groupName")
            }
        }
    }

    private List<Account> readMembers(int groupID)
    {
        List<Account> members = new ArrayList<>();
        String sqlReadGroup = """
               SELECT AID, username,hashed_pwd, firstname, lastname, email, privilegeLevel, School.schoolName, School.SID, School.FK_Zipcode, Zipcode.city FROM Account
               JOIN AccountGroup ON Account.AID = AccountGroup.FK_MemberID
               JOIN School ON Account.FK_AccountSchool = School.SID
               JOIN Zipcode ON School.FK_Zipcode = Zipcode.Zip
               WHERE FK_GroupID = ?
                """;

        Connection conn = DBConnectionPool.getInstance().checkOut();

        try {
            PreparedStatement psrg = conn.prepareStatement(sqlReadGroup);
            psrg.setInt(1,groupID);

            ResultSet rs = psrg.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("AID");
                String username = rs.getString("username");
                String hashed_pwd = rs.getString("hashed_pwd");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                int privilegeLevel = rs.getInt("privilegeLevel");
                String schoolName = rs.getString("schoolName");
                int zipCode = rs.getInt("School.FK_Zipcode");
                int SID = rs.getInt("School.SID");
                String cityName = rs.getString("Zipcode.city");
                School school = new School(SID, schoolName, zipCode, cityName);
                Account account = new Account(id, username, hashed_pwd, firstname, lastname, email, school, privilegeLevel);
                members.add(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return members;
    }

    public List<Group> readAll() {
        return null;
    }

    public void delete(int id) {

    }
}
