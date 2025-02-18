package org.example.server;

import org.example.annotations.GetMapping;
import org.example.annotations.RequestParam;
import org.example.annotations.RestController;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private static final Map<String, Method> annotatedRoutes = new HashMap<>();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // Método para inicializar las rutas anotadas
    public static void initializeRoutes() {

        scanAndLoadComponents("org.example.annotastions");
        scanAndLoadComponents("org.example.Controllers");
    }

    public static void scanAndLoadComponents(String basePackage) {

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = basePackage.replace('.', '/');
            var resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                var resource = resources.nextElement();
                String decodedPath = java.net.URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                File directory = new File(decodedPath);

                if (directory.exists()) {
                    String[] files = directory.list();
                    if (files != null) {
                        for (String file : files) {
                            if (file.endsWith(".class")) {
                                String className = basePackage + "." + file.substring(0, file.length() - 6);
                                System.out.println("Cargando clase: " + className);
                                loadComponent(Class.forName(className));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadComponent(Class<?> c) {
        if (!c.isAnnotationPresent(RestController.class)) {
            return;
        }

        for (Method m : c.getDeclaredMethods()) {
            if (m.isAnnotationPresent(GetMapping.class)) {
                GetMapping annotation = m.getAnnotation(GetMapping.class);
                annotatedRoutes.put(annotation.value(), m);
                System.out.println("Endpoint cargado: " + annotation.value() + " -> " + m.getName());
            }
        }
    }

    public void run() {
        try {
            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String requestLine = in.readLine();
            if (requestLine != null) {
                String[] requestParts = requestLine.split(" ");
                if (requestParts.length >= 2) {
                    String method = requestParts[0];
                    String file = requestParts[1];
                    URI requestFile = new URI(file);
                    String path = requestFile.getPath();
                    String query = requestFile.getQuery();

                    System.out.println("Método: " + method + ", Path: " + path);

                    if ("GET".equals(method)) {
                        if (annotatedRoutes.containsKey(path)) {
                            handleAnnotatedRoute(path, query, out);
                        } else {
                            // Si el path es "/" servir index.html
                            if ("/".equals(path)) {
                                path = "/index.html";
                            }
                            serveStaticFile(path, out);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAnnotatedRoute(String path, String query, OutputStream out) throws IOException {
        try {
            Method method = annotatedRoutes.get(path);

            // Obtener los parámetros del método
            java.lang.reflect.Parameter[] parameters = method.getParameters();
            Object[] args = new Object[parameters.length];

            // Parsear los parámetros de la query
            Map<String, String> queryParams = parseQueryParams(query);

            // Preparar los argumentos para cada parámetro
            for (int i = 0; i < parameters.length; i++) {
                java.lang.reflect.Parameter param = parameters[i];
                RequestParam annotation = param.getAnnotation(  RequestParam.class);

                if (annotation != null) {
                    String paramName = annotation.value();
                    String defaultValue = annotation.defaultValue();
                    String value = queryParams.getOrDefault(paramName, defaultValue);
                    args[i] = value;
                } else {
                    // Si no tiene anotación, usar null o un valor por defecto
                    args[i] = null;
                }
            }

            // Invocar el método con los argumentos preparados
            Object result;
            if (java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                result = method.invoke(null, args);
            } else {
                // Si el método no es estático, crear una instancia de la clase
                Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
                result = method.invoke(instance, args);
            }

            String responseBody = "{\"message\": \"" + result + "\"}";
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + responseBody.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                    "\r\n" +
                    responseBody;

            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            sendError(null, out, 500, "Error interno del servidor");
        }
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return params;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = "";
                value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(key, value);
            }
        }
        return params;
    }

    private void serveStaticFile(String path, OutputStream out) throws IOException {
        try {
            // Eliminar el slash inicial para buscar en resources
            String resourcePath = path.startsWith("/") ? path.substring(1) : path;
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("public/" + resourcePath);

            if (inputStream != null) {
                byte[] fileBytes = inputStream.readAllBytes();
                String contentType = Files.probeContentType(Path.of(path));
                //String contentType = getContentType(path);

                String headers = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Length: " + fileBytes.length + "\r\n" +
                        "\r\n";

                out.write(headers.getBytes(StandardCharsets.UTF_8));
                out.write(fileBytes);
                out.flush();
            } else {
                sendError(inputStream, out, 404, "Archivo no encontrado");
            }
        } catch (IOException e) {
            sendError(null, out, 500, "Error interno del servidor");
        }
    }

    private void sendError(InputStream inputStream, OutputStream out, int code, String message) throws IOException {
        inputStream = ClientHandler.class.getClassLoader().getResourceAsStream("public" + File.separator + "404RemFound.html");
        byte[] fileBytes = inputStream.readAllBytes();
        try {
            String errorResponse = "HTTP/1.1 " + code + " " + message + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n";
            out.write(errorResponse.getBytes(StandardCharsets.UTF_8));
            out.write(fileBytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}