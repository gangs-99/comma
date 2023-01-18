<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
</head>
<body>
	<section>
		<div id="enrollBox">
			<h1 id="enrollTitle" class="pointColor fontStyle">마이페이지</h1>
			<form action="">
				<fieldset>
					<table>
						<tbody>
							<tr>
								<td class="labelTd fontStyle"><label for="nicknameId"></label></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="inputTd fontStyle" colspan="6"><input
									type="text" id="nicknameId" name="nicknameId" readonly
									value="nickname" /></td>
							</tr>
							<tr>
								<td class="errorTd fontStyle"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="labelTd fontStyle"><label for="password"></label></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="inputTd fontStyle" colspan="6"><input
									type="password" id="password" name="password"
									placeholder="비밀번호" /></td>
							</tr>
							<tr>
								<td class="errorTd fontStyle"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="labelTd fontStyle"><label for="birthDay"></label></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="inputTd fontStyle" colspan="6"><input
									type="text" id="birthDay" name="birthDay"
									placeholder="생년월일(YYMMDD)" /></td>
							</tr>
							<tr>
								<td class="errorTd fontStyle"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="labelTd fontStyle"><label for="gender"></label></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="inputTd fontStyle" colspan="6"><input
									type="text" id="gender" name="gender" placeholder="성별" /></td>
							</tr>
							<tr>
								<td class="errorTd fontStyle"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="labelTd fontStyle"><label for="email"></label></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="inputTd fontStyle" colspan="6"><input
									type="text" id="email" name="email" placeholder="이메일" /></td>
							</tr>
							<tr>
								<td class="errorTd fontStyle"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
							    <td colspan="6"><input type="button" value="정보수정" /></td>
							</tr>
							<tr>
								<td colspan="6"><input type="button" value="회원탈퇴" /></td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</form>
		</div>
	</section>
</body>
</html>


