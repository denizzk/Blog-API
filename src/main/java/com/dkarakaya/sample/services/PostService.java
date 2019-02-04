package com.dkarakaya.sample.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.dkarakaya.sample.databasehelper.DatabaseConnection;
import com.dkarakaya.sample.models.Post;

public class PostService {

	// gets all posts that exist in db
	public List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<Post>();
		String sql = "select * from post";

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Post p = new Post();
				p.setId(rs.getInt("postId"));
				p.setImageUrl(rs.getString("postImageUrl"));
				p.setMessage(rs.getString("postMessage"));
				p.setLikeCount(rs.getInt("postLikeCount"));
				p.setCreationDate(rs.getTimestamp("postCreationDate"));
				p.setLastModifiedDate(rs.getTimestamp("postLastModificationDate"));
				p.setLinks(null);
				posts.add(p);
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	// gets all posts created in given year from db
	public List<Post> getAllPostsForYear(int year) {
		List<Post> posts = new ArrayList<>(getAllPosts());
		List<Post> postsForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if (!posts.isEmpty()) {
			for (Post p : posts) {
				cal.setTime(p.getCreationDate());
				if (cal.get(Calendar.YEAR) == year) {
					postsForYear.add(p);
				}
			}

			return postsForYear;
		}
		return new ArrayList<>();
	}

	// gets all posts created in given month from db
	public List<Post> getAllPostsForMonth(int month, int year) {
		List<Post> postsForYear = new ArrayList<>(getAllPostsForYear(year));
		List<Post> postsForMonth = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if (!postsForYear.isEmpty()) {
			for (Post p : postsForYear) {
				cal.setTime(p.getCreationDate());
				if (cal.get(Calendar.MONTH) == month - 1) { // january == 0
					postsForMonth.add(p);
				}
			}

			return postsForMonth;
		}
		return new ArrayList<Post>();
	}

	// gets all posts created in given day from db
	public List<Post> getAllPostsForDay(int day, int month, int year) {
		List<Post> postsForMonth = new ArrayList<>(getAllPostsForMonth(month, year));
		List<Post> postsForDay = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if (!postsForMonth.isEmpty()) {
			for (Post p : postsForMonth) {
				cal.setTime(p.getCreationDate());
				if (cal.get(Calendar.DATE) == day) {
					postsForDay.add(p);
				}
			}

			return postsForDay;
		}
		return new ArrayList<Post>();
	}

	// gets all posts created in today from db
	public List<Post> getAllPostsForToday() {
		List<Post> postsForToday = new ArrayList<Post>();
		String sql = "select * from post where DATE(postCreationDate)=CURDATE()";

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Post p = new Post();
				p.setId(rs.getInt("postId"));
				p.setImageUrl(rs.getString("postImageUrl"));
				p.setMessage(rs.getString("postMessage"));
				p.setLikeCount(rs.getInt("postLikeCount"));
				p.setCreationDate(rs.getTimestamp("postCreationDate"));
				p.setLastModifiedDate(rs.getTimestamp("postLastModificationDate"));
				p.setLinks(null);
				postsForToday.add(p);
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return postsForToday;
	}

	// get posts with the given start and size constraints
	public List<Post> getAllPostsPaginated(int start, int size) {
		ArrayList<Post> list = new ArrayList<Post>(getAllPosts());
		if (start + size > list.size()) {
			if (start < list.size())
				return list.subList(start, list.size());
			return new ArrayList<>();
		}

		return list.subList(start, start + size);
	}

	// gets a post for a given postId from db
	public Post getPost(int id) {
		Post p = new Post();
		String sql = "select * from post where postId=" + id;

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				p.setId(rs.getInt("postId"));
				p.setImageUrl(rs.getString("postImageUrl"));
				p.setMessage(rs.getString("postMessage"));
				p.setLikeCount(rs.getInt("postLikeCount"));
				p.setCreationDate(rs.getTimestamp("postCreationDate"));
				p.setLastModifiedDate(rs.getTimestamp("postLastModificationDate"));
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (p.getId() == 0)
			return null;

		return p;
	}

	// adds a post to db
	public Post addPost(Post post) {
		String sql = "insert into post (postImageUrl, postMessage, postLikeCount) values (?,?,?)";

		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (post.getImageUrl() == null) {
				st.setString(1, "");
				post.setImageUrl("");
			} else
				st.setString(1, post.getImageUrl());
			st.setString(2, post.getMessage());
			st.setInt(3, post.getLikeCount());
			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				post.setId(rs.getInt(1));
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
	}

	// updates a post that exists in db
	public Post updatePost(Post post) {
		String sql = "update post set postImageUrl=?, postMessage=?, postLikeCount=? where postId=?";
		int updateCount = 0;
		if (post.getId() <= 0) {
			return null;
		}
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			if (post.getImageUrl() == null) {
				st.setString(1, "");
				post.setImageUrl("");
			} else
				st.setString(1, post.getImageUrl());
			st.setString(2, post.getMessage());
			st.setInt(3, post.getLikeCount());
			st.setInt(4, post.getId());
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

		post.setId(post.getId());
		return post;
	}

	// deletes a post if exists in db
	public void removePost(int id) {
		String sql = "delete from post where postId=?";

		if (id <= 0) {

		} else {
			try {
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, id);
				st.executeUpdate();
				st.close();
				DatabaseConnection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
