# 💬 Online Chat Application (Java WebSocket + STOMP + Swing Client)

This is a full-stack **real-time chat application** built using **Spring Boot WebSocket (STOMP protocol)** on the backend and a **JavaFX GUI client**. It supports multiple users chatting simultaneously with live message updates and connected user tracking.

![Chat App Screenshot](src/main/java/com/siddh/Online_Chat_Application/assests/WorkingImage.png)

---

## 🚀 Features

✅ Real-time communication using WebSocket + STOMP  
✅ Swing-based rich desktop UI  
✅ Displays connected users dynamically  
✅ Sends and receives JSON messages  
✅ Graceful connect/disconnect handling  
✅ Deployed backend on [Render](https://online-chat-application-hy1a.onrender.com)  

---

## 🛠️ Tech Stack

| Layer     | Technology                          |
|-----------|--------------------------------------|
| Backend   | Java, Spring Boot, WebSocket, STOMP |
| Frontend  | JavaSwing       |
| Deployment| Docker + Render.com                 |

> Please note: The server may take time to wake up if it's been inactive (Render free tier).
---
# For best experience, run locally by cloning this repo and following the instructions below
## 📦 How to Run locally

✅ Prerequisites
Make sure the following are installed:

- Java 17+  (21 recommended)
- Maven (3.9.2 or greater)
- An IDE like IntelliJ (recommended) or Ecilpse

🚦 Steps to Run

- Clone the Repository (use following commands)
  
```bash
git clone https://github.com/Siddharth3271/Online-Chat-Application.git
cd Online-Chat-Application
```

- Open Project in IntelliJ
- Go to [](src/main/java/com/siddh/Online_Chat_Application/client/MyStompClient.java)
- Change the String "url" from [](https://online-chat-application-hy1a.onrender.com/ws) to [](ws://localhost:8081/ws)
- Run the backend server from [](src/main/java/com/siddh/Online_Chat_Application/OnlineChatApplication.java)
- Run the client server from [](src/main/java/com/siddh/Online_Chat_Application/client/App.java)

## Run at least two client server to check the communication between two clients

# Steps for creating two clients in intellj
- Go to Run/Debug Configuration Selector in IDE
- Click on edit configuration
- select '+' icon then click on Application
- set the Main Class as App Class and name the Configuration as "App2_Client" or name of your choice
- apply and save it

> run both the client servers and start sending messages from both GUIs.




