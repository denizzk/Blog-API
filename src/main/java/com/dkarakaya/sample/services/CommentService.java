package com.dkarakaya.sample.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.dkarakaya.sample.databasehelper.DatabaseConnection;
import com.dkarakaya.sample.models.Comment;

public class CommentService {

	// gets all comments for a given postId that exist in db
	public List<Comment> getCommentsOfPost(int postId) {
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "select * from comment where postId=" + postId;

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Comment c = new Comment();
				c.setId(rs.getInt("commentId"));
				c.setPostId(rs.getInt("postId"));
				c.setMessage(rs.getString("commentMessage"));
				c.setLikeCount(rs.getInt("commentLikeCount"));
				c.setCreatedDate(rs.getTimestamp("commentCreationDate"));
				c.setLastModifiedDate(rs.getTimestamp("commentLastModifiedDate"));
				comments.add(c);
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comments;
	}

	// gets all comments of post that created in given year from db
	public List<Comment> getCommentsOfPostForYear(int postId, int year) {
		List<Comment> comments = new ArrayList<>(getCommentsOfPost(postId));
		List<Comment> commentsForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if (!comments.isEmpty()) {
			for (Comment c : comments) {
				cal.setTime(c.getCreatedDate());
				if (cal.get(Calendar.YEAR) == year) {
					commentsForYear.add(c);
				}
			}

			return commentsForYear;
		}
		return new ArrayList<>();
	}

	// gets all comments created in given month from db
	public List<Comment> getCommentsOfPostForMonth(int postId, int month, int year) {
		List<Comment> commentsForYear = new ArrayList<>(getCommentsOfPostForYear(postId, year));
		List<Comment> commentsForMonth = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if (!commentsForYear.isEmpty()) {
			for (Comment c : commentsForYear) {
				cal.setTime(c.getCreatedDate());
				if (cal.get(Calendar.MONTH) == month - 1) { // january == 0
					commentsForMonth.add(c);
				}
			}

			return commentsForMonth;
		}
		return new ArrayList<Comment>();
	}

	// gets all comments created in given day from db
	public List<Comment> getCommentsOfPostForDay(int postId, int day, int month, int year) {
		List<Comment> commentsForMonth = new ArrayList<>(getCommentsOfPostForMonth(postId, month, year));
		List<Comment> commentsForDay = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if (!commentsForMonth.isEmpty()) {
			for (Comment c : commentsForMonth) {
				cal.setTime(c.getCreatedDate());
				if (cal.get(Calendar.DATE) == day) {
					commentsForDay.add(c);
				}
			}

			return commentsForDay;
		}
		return new ArrayList<Comment>();
	}

	// gets all comments created in today from db
	public List<Comment> getCommentsOfPostForToday(int postId) {
		List<Comment> commentsForToday = new ArrayList<Comment>();
		String sql = "select * from comment where DATE(commentCreationDate)=CURDATE() and postId=" + postId;

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Comment c = new Comment();
				c.setId(rs.getInt("commentId"));
				c.setPostId(rs.getInt("postId"));
				c.setMessage(rs.getString("commentMessage"));
				c.setLikeCount(rs.getInt("commentLikeCount"));
				c.setCreatedDate(rs.getTimestamp("commentCreationDate"));
				c.setLastModifiedDate(rs.getTimestamp("commentLastModifiedDate"));
				commentsForToday.add(c);
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentsForToday;
	}

	// gets comments with the given start and size constraints
	public List<Comment> getCommentsOfPostPaginated(int postId, int start, int size) {
		ArrayList<Comment> list = new ArrayList<Comment>(getCommentsOfPost(postId));
		if (start + size > list.size()) {
			if (start < list.size())
				return list.subList(start, list.size());
			return new ArrayList<>();
		}

		return list.subList(start, start + size);
	}

	// gets a comment for a given postId and commentId from db
	public Comment getComment(int postId, int commentId) {
		Comment c = new Comment();
		String sql = "select * from comment where commentId=" + commentId + " and postId=" + postId;

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				c = new Comment();
				c.setId(rs.getInt("commentId"));
				c.setPostId(rs.getInt("postId"));
				c.setMessage(rs.getString("commentMessage"));
				c.setLikeCount(rs.getInt("commentLikeCount"));
				c.setCreatedDate(rs.getTimestamp("commentCreationDate"));
				c.setLastModifiedDate(rs.getTimestamp("commentLastModifiedDate"));
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (c.getId() == 0)
			return null;

		return c;
	}

	// adds a posted comment for a given postId to db
	public Comment addComment(int postId, Comment comment) {
		String sql = "insert into comment (postId, commentMessage, commentLikeCount) values (?,?,?)";

		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, postId);
			st.setString(2, comment.getMessage());
			st.setInt(3, comment.getLikeCount());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				comment.setId(rs.getInt(1));
				comment.setPostId(postId);
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comment;
	}

	// updates a posted comment that exists in db
	public Comment updateComment(int postId, int commentId, Comment comment) {
		String sql = "update comment set commentMessage=?, commentLikeCount=? where postId=? and commentId=?";
		int updateCount = 0;
		if (commentId <= 0 || postId <= 0) {
			return null;
		}
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, comment.getMessage());
			st.setInt(2, comment.getLikeCount());
			st.setInt(3, postId);
			st.setInt(4, commentId);
			st.executeUpdate();
			updateCount = st.getUpdateCount();
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (updateCount == 0)
			return null;

		comment.setId(commentId);
		comment.setPostId(postId);
		return comment;
	}

	// deletes a posted comment if exists in db
	public void removeComment(int postId, int commentId) {
		String sql = "delete from comment where commentId=? and postId=?";
		if (commentId <= 0) {

		} else {
			try {
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, commentId);
				st.setInt(2, postId);
				st.executeUpdate();
				st.close();
				DatabaseConnection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
