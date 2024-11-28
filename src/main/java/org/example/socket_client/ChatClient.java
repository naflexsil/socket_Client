package org.example.socket_client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес сервера (по умолч. localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) {
            host = "localhost";
        }

        System.out.print("Введите порт сервера (по умолч. 12345): ");
        int port = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Введите ваш никнейм: ");
        String nickname = scanner.nextLine().trim();

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(nickname);

            // Поток для получения сообщений с сервера
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка чтения сообщений с сервера: " + e.getMessage());
                }
            }).start();

            System.out.println("Добро пожаловать, " + nickname + "!");
            System.out.println("Краткая информация:");
            System.out.println("- Чтобы отправить ЛС: введите /w, выберите пользователя и напишите сообщение.");
            System.out.println("- Чтобы отправить широковещательное сообщение, просто напишите текст и нажмите Enter.");
            System.out.println("- Чтобы увидеть список пользователей, введите /users.");
            System.out.println("- Для выхода используйте сочетание клавиш Ctrl+C.");

            while (true) {
                String message = scanner.nextLine();
                if (message.equals("/w")) {
                    out.println("/users");
                    System.out.println("Введите имя получателя:");
                    String recipient = scanner.nextLine();
                    System.out.println("Введите ваше сообщение:");
                    String privateMessage = scanner.nextLine();
                    out.println("/w " + recipient + " " + privateMessage);
                } else {
                    out.println(message);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу: " + e.getMessage());
        }
    }
}
