package rs.raf.student.repository;

import rs.raf.student.model.Activity;
import rs.raf.student.model.Article;
import rs.raf.student.model.Comment;
import rs.raf.student.model.Destination;
import rs.raf.student.model.User;
import rs.raf.student.model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetReader {

    public static Article readArticle(ResultSet resultSet) throws SQLException {
        return new Article
            (
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getLong("author_id"),
                resultSet.getLong("destination_id"),
                resultSet.getDate("created_at").toLocalDate(),
                resultSet.getLong("visits")
            );
    }

    public static Activity readActivity(ResultSet resultSet) throws SQLException {
        return new Activity(resultSet.getLong("id"),
                            resultSet.getString("name"));
    }

    public static Destination readDestination(ResultSet resultSet) throws SQLException {
        return new Destination
            (
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
            );
    }

    public static User readUser(ResultSet resultSet) throws SQLException {
        return new User
            (
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("salt"),
                resultSet.getString("password"),
                resultSet.getLong("role_id"),
                resultSet.getBoolean("enabled")
            );
    }

    public static UserRole readUserRole(ResultSet resultSet) throws SQLException {
        return new UserRole
            (
                resultSet.getLong("id"),
                resultSet.getString("name")
            );
    }

    public static Comment readComments(ResultSet resultSet) throws SQLException {
        return new Comment
            (
                resultSet.getLong("id"),
                resultSet.getString("content"),
                resultSet.getString("display_name"),
                resultSet.getLong("article_id"),
                resultSet.getDate("created_at").toLocalDate()
            );
    }

}
