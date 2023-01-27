const ws = new WebSocket(`ws://${location.host}/comma/commaWebSocket`);
console.log(`ws://${location.host}/comma/commaWebSocket`);
ws.addEventListener('open', (e) => {
	console.log('open : ', e);
});
ws.addEventListener('message', (e) => {
	console.log('message : ', e);
	const wrapper = document.querySelector("#msg-container");
	const notification = document.querySelector("#notification");
	
	const {message, messageType, datetime, sender, receiver} = JSON.parse(e.data);
	console.log(message, messageType, datetime, sender, receiver);
	
	switch (messageType) {
		case "NOTIFY_NEW_LETTER" :
			const i = document.createElement("i");
			i.classList.add('fa-regular', 'fa-bell', 'bell');
			i.addEventListener('click', () => {
				alert(message);
				i.remove();
			});
			
			notification.append(i);
			break;
		case "NOTIFY_NEW_COMMENT" :
			const notification = document.querySelector("#notification");
			const i1 = document.createElement("i");
			i1.classList.add('fa-solid', 'fa-bell', 'bell');
			i1.addEventListener('click', () => {
				alert(message);
				i1.remove();
			});
			
			notification.append(i1);
			break;
		case "CHATROOM_ENTER" :
			wrapper.insertAdjacentHTML('beforeend', `<li class="line">${sender}님이 입장했습니다.</li>`);
			// beforeend : 마지막에 넣어달라
			break;
		case "CHATROOM_LEAVE" :
			wrapper.insertAdjacentHTML('beforeend', `<li class="line">${sender}님이 퇴장했습니다.</li>`);		
			break;
		case "CHAT_MSG" :
			wrapper.insertAdjacentHTML('beforeend', `<li class="left"><span class="badge">${sender}</span> ${message}</li>`);		
			break;
	} // switch end
	
	// 스크롤 처리
	//wrapper.scrollTop = wrapper.scrollHeight;
});
ws.addEventListener('error', (e) => {
	console.log('error : ', e);
});
ws.addEventListener('close', (e) => {
	console.log('close : ', e);
});