package ws.endpoint;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import ws.MessageType;
import ws.config.CommaWebSocketConfigurator;

@ServerEndpoint(value = "/helloWebSocket", configurator = CommaWebSocketConfigurator.class)
public class CommaWebSocket {

	/**
	 * 사용자의 웹소켓 세션을 관리하는 맵 (공용자원느낌)
	 * Key - String memberId
	 * Value - Session session
	 * 
	 * 멀티쓰레드 환경에서 동기화(각 쓰레드가 사용하는 순서를 지정) 필수
	 */
	public static Map<String, Session> clientMap = Collections.synchronizedMap(new HashMap<>());
	
	/**
	 * 채팅방별 사용자 맵
	 *  - Key: String chatroomId
	 *  - Value: Set<String> chatMembers
	 */
	public static Map<String, Set<String>> chatParticipantMap = Collections.synchronizedMap(new HashMap<>());
	
	public void log() {
		System.out.printf("[현재 접속자 (%d명)] : %s%n", clientMap.size(), clientMap.keySet());
		System.out.printf("[현재 채팅방 수 (%d개)] : %s%n", chatParticipantMap.size(), chatParticipantMap);
	} // log() end
	
	/**
	 * 채팅방에 사용자 추가
	 *  - 처음 채팅방에 입장하는 경우, chatroomId와 사용자Set을 초기화해야 한다.
	 */
	private void addToChatroom(String chatroomId, String memberId) {
		Set<String> participantSet = chatParticipantMap.get(chatroomId);
		
		if (participantSet == null) {
			participantSet = new HashSet<>();
			chatParticipantMap.put(chatroomId, participantSet);
		} // if() end
		participantSet.add(memberId);
	} // addToChatroom() end

	/**
	 * 채팅방에서 사용자 제거
	 *  - 채팅방에 남은 마지막 사용자를 제거하는 경우, 채팅방까지 제거
	 * @param chatroomId
	 * @param memberId
	 */
	private void removeFromChatroom(String chatroomId, String memberId) {
		Set<String> participantSet = chatParticipantMap.get(chatroomId);
		participantSet.remove(memberId);
		
		// 마지막 사용자인 경우
		if (participantSet.size() == 0) {
			chatParticipantMap.remove(chatroomId); // 관리하는 채팅방에서 제거
		}
	} // removeFromChatroom() end
	
	private void sendChatroomEnterMsg(String chatroomId, String memberId, Session session) {
		Map<String, Object> data = new HashMap<>();
		data.put("chatroomId", chatroomId);
		data.put("sender", memberId);
		data.put("messageType", MessageType.CHATROOM_ENTER);
		data.put("datetime", System.currentTimeMillis());
		
		onMessage(new Gson().toJson(data), session);
	} // sendChatroomEnterMsg() end

	private void sendChatroomLeaveMsg(String chatroomId, String memberId, Session session) {
		Map<String, Object> data = new HashMap<>();
		data.put("chatroomId", chatroomId);
		data.put("sender", memberId);
		data.put("messageType", MessageType.CHATROOM_LEAVE);
		data.put("datetime", System.currentTimeMillis());
		
		onMessage(new Gson().toJson(data), session);
	} // sendChatroomLeaveMsg() end
	
	@OnOpen
	public void onOpen(EndpointConfig config, Session session) {
		Map<String, Object> userProp = config.getUserProperties();
		String memberId = (String) userProp.get("memberId");
		String chatroomId = (String) userProp.get("chatroomId");
		
		// 웹소켓 세션 관리 맵에 추가
		clientMap.put(memberId, session);
		
		// session 설정맵에 사용자아이디 추가 (close 때 사용)
		Map<String, Object> sessionUserProp = session.getUserProperties();
		sessionUserProp.put("memberId", memberId);
		
		if (chatroomId != null) {
			sessionUserProp.put("chatroomId", chatroomId);
			addToChatroom(chatroomId, memberId);
			sendChatroomEnterMsg(chatroomId, memberId, session);
		}
		
		log();
		
	} // onOpen() end

	@OnMessage
	public void onMessage(String msg, Session session) {
		System.out.println("msg@OnMessage = " + msg);
		
		// json => map
		Map<String, Object> data = new Gson().fromJson(msg, Map.class);
		// { "messageType" : "CHAT", "message" : "ㅋㅋㅋ", "datetime" : 13415154312, ... }
		
		String chatroomId = (String) data.get("chatroomId");
		MessageType messageType = MessageType.valueOf((String) data.get("messageType"));		
		
		switch (messageType) {
		case HELLOWORLD :
			Collection<Session> sessions = clientMap.values(); // 현재 접속중인 웹소켓 세션
			for (Session sess : sessions) {
				Basic basic = sess.getBasicRemote(); // 웹소켓 세션에 출력객체
				try {
					basic.sendText(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // for each end
			break;
		case CHAT_MSG :
		case CHATROOM_ENTER :
		case CHATROOM_LEAVE :
			Set<String> participantSet = chatParticipantMap.get(chatroomId);
			for (String participant : participantSet) {
				Session sess = clientMap.get(participant);
				Basic basic = sess.getBasicRemote();
				try {
					basic.sendText(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // for each end
			break;
		} // switch end
	} // onMessage() end
	
	@OnError
	public void onError(Throwable e) {
		e.printStackTrace();
	} // onError() end
	
	@OnClose
	public void onClose(Session session) {
		Map<String, Object> sessionUserProp = session.getUserProperties();
		String memberId = (String) sessionUserProp.get("memberId");
		String chatroomId = (String) sessionUserProp.get("chatroomId");
		
		// 웹소켓 세션 관리 맵에서 제거
		clientMap.remove(memberId);
		if (chatroomId != null) {
			removeFromChatroom(chatroomId, memberId);
			sendChatroomLeaveMsg(chatroomId, memberId, session);
		}
		
		log();
	} // onClose() end
	
} // class end