<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div id=counselingEnrollTitle>
	<h1>고민 게시판</h1>
</div>

	<table>
		<tr>
			<td>제목작성 :</td>

			<td class="inputTd fontStyle">
				<input type="text" id="title" name="title" placeholder="제목을 입력해 주세요.">
			</td>
		</tr>

		<tr>
			<td>내용작성 :</td>

			<td class="inputTd fontStyle">
				<textarea name="content" cols="85" rows="15" id="content" placeholder="내용을 입력해 주세요."></textarea>
			</td>
		</tr>


		<tr>
			<td>공개 나이대 선택 : </td>
		</tr>
		
		


		<tr>
			<td>공개 성별 선택 : </td>
			
			<td class="input fontsytle">
		    남자 <input type="radio" name="gender" value ="남자">
		    여자 <input type="radio" name="gender" value ="여자">
		    전체공개 <input type="radio" name="gender" value ="전체공개">
			</td>
		</tr>

		
		<tr>
			<td>
				<input type="submit" id ="write" value="고민 작성하기">
			</td>
		<tr>
		
		
	</table>

</body>
</html>

				<!-- crtl shift f 줄맞춤  -->