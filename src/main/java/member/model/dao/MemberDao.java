package member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import member.model.dto.Gender;
import member.model.dto.Member;
import member.model.dto.MemberRole;
import member.model.exception.MemberException;

public class MemberDao {
	
	Properties prop = new Properties();
	
	public MemberDao() {
		String path = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();
		try {
			prop.load(new FileReader(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public List<Member> selectAllMember(Connection conn){
		List<Member> memberList = new ArrayList<>();
		String sql = prop.getProperty("selectAllMember");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					memberList.add(handleMemberResultSet(rset));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MemberException("닉네임 가져오기 오류!", e);
		}
		
		return memberList;
	}
	
	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setNickname(rset.getString("nickname"));
		member.setPassword(rset.getString("password"));
		member.setGender(Gender.valueOf(rset.getString("gender")));
		member.setEmail(rset.getString("email"));
		member.setEnrollDate(rset.getDate("enroll_date"));
		member.setMemberRole(MemberRole.valueOf(rset.getString("member_role")));
		member.setWarningCount(rset.getInt("warning_count"));
		member.setOriginalFilename(rset.getString("original_filename"));
		member.setRenamedFilename(rset.getString("renamed_filename"));
		
		return member;
	}


	public Member selectOneMember(Connection conn, String id) {
		Member member = null;
		String sql = prop.getProperty("selectOneMember");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			
			try(ResultSet rset = pstmt.executeQuery()){
				if(rset.next()) {
					member = handleMemberResultSet(rset);
				}
			}
		} catch (SQLException e) {
			throw new MemberException("로그인 실패!", e);
		}
		return member;
	}


	public int insertMember(Connection conn, Member member) {
		int result = 0;
		String sql = prop.getProperty("insertMember");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getNickname());
			pstmt.setString(3, member.getPassword());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getGender().toString());
			pstmt.setInt(6, member.getAge());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("회원가입오류!", e);
		}
		return result;
	}
}
