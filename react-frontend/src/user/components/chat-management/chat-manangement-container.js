import React from 'react';
import { Card, CardBody, Input, Button } from 'reactstrap';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { HOST } from '../../../commons/hosts';

class ChatComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            messages: [],
            inputText: '',
            stompClient: null,
            typingUser: null  // new state property
        };
    }

    componentDidMount() {
        this.connect();
    }

    componentWillUnmount() {
        this.disconnect();
    }

    connect = () => {
        const socket = new SockJS(HOST.chat_api + '/ws');
        const stompClient = Stomp.over(socket);
        stompClient.connect({withCredentials: false}, frame => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/public', messageOutput => {
                this.handleMessageOutput(JSON.parse(messageOutput.body));
            });
            
            stompClient.subscribe('/topic/typing', typingOutput => {
                this.handleTypingNotification(JSON.parse(typingOutput.body));
            });
        });
        this.setState({ stompClient });
        console.log("WebSocket connection established");
    }

    disconnect = () => {
        if (this.state.stompClient) {
            this.state.stompClient.disconnect();
        }
        console.log("Disconnected from WebSocket");
    }

    handleMessageOutput = (message) => {
        if (message.type === 'CHAT') {
            this.setState(prevState => ({
                messages: [...prevState.messages, message]
            }));
        } else {
            this.handleTypingNotification(message);
        }
        console.log("Received message: ", message);
    };

    handleSendMessage = () => {
        if (this.state.inputText.trim()) {
            const message = {
                type: 'CHAT',
                content: this.state.inputText,
                sender: localStorage.getItem("username") // Retrieve username from localStorage
            };
            this.setState({ inputText: '' }); // Clear the input field
            this.state.stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
            console.log("Sent message: ", message);
    
        }
    };

    // Handles changes in the chat input field.
    handleInputChange = (event) => {
        this.setState({ inputText: event.target.value });
        this.notifyTypingStatus(true); // Notifies that the user has started typing
    };


    // Sends a typing status notification.
    notifyTypingStatus = (isTyping) => {
        const typingMessage = {
            sender: localStorage.getItem("username"),
            isTyping: isTyping
        };

        this.state.stompClient.send("/app/typing", {}, JSON.stringify(typingMessage));
    };
    // Handles incoming typing notifications.
    handleTypingNotification = (message) => {
        if (message.typing) {
            this.updateTypingUser(message.sender);
            console.log("GAYGAY");
        } else {
            this.clearTypingNotification();
        }
    };
    
    // Updates the state to show who is currently typing.
    updateTypingUser = (username) => {
        this.setState({ typingUser: username });
    };
    
    // Clears the current typing notification.
    clearTypingNotification = () => {
        this.setState({ typingUser: null });
    };

    render() {
        return (
            <Card>
                <CardBody>
                    <div className="chat-window">
                        <div className="messages">
                            {this.state.messages.map((message, index) => (
                                <div key={index} className={message.sender === localStorage.getItem("username") ? 'message self' : 'message'}>
                                    {message.content}
                                </div>
                            ))}
                        </div>
                        {/* Typing notification display */}
                        {this.state.typingUser && (
                            <div className="typing-notification">
                                {this.state.typingUser} is typing...
                            </div>
                        )}
                        <div className="input-area">
                            <Input
                                type="text"
                                value={this.state.inputText}
                                onChange={this.handleInputChange}
                                placeholder="Type a message..."
                            />
                            <Button color="primary" onClick={this.handleSendMessage}>Send</Button>
                        </div>
                    </div>
                </CardBody>
            </Card>
        );
    }
}

export default ChatComponent;
