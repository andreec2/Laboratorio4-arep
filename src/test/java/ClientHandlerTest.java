// ClientHandlerTest.java


import org.example.server.HttpServer;
import org.junit.jupiter.api.*;
import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    private Thread serverThread;

    @BeforeEach
    void setUp() {
        serverThread = new Thread(() -> HttpServer.startServer());
        serverThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {
        HttpServer.stopServer();
    }

    private String sendGetRequest(String path) throws IOException {
        try (Socket socket = new Socket("localhost", 6000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            boolean bodyStarted = false;
            boolean emptyLineFound = false;

            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) {
                    emptyLineFound = true;
                    continue;
                }
                if (emptyLineFound) {
                    bodyStarted = true;
                }
                if (bodyStarted) {
                    response.append(line);
                }
            }

            return response.toString();
        }
    }

    @Test
    void testGetIndexHtml() throws IOException {
        try (Socket socket = new Socket("localhost", 6000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /index.html HTTP/1.1");
            out.println("Host: localhost");
            out.println(); // Línea vacía importante
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("200 OK"),
                    "La respuesta debería contener '200 OK', pero fue: " + response);

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                content.append(line).append("\n");
            }
            assertTrue(content.toString().contains("Content-Type: text/html"),
                    "La respuesta debería incluir el Content-Type correcto");
        }
    }



    @Test
    void testFileNotFound() throws IOException {
        try (Socket socket = new Socket("localhost", 6000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /archivo-no-existe.html HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("404"));
        }
    }

    @Test
    void testMathOperations() throws IOException {
        // Probar la operación de suma
        testMathEndpoint("/sum?a=5&b=3", "8");

        // Probar la operación de resta
        testMathEndpoint("/res?a=10&b=4", "6");

        // Probar la operación de multiplicación
        testMathEndpoint("/mul?a=6&b=7", "42");
    }

    private void testMathEndpoint(String path, String expectedResult) throws IOException {
        try (Socket socket = new Socket("localhost", 6000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            assertNotNull(response);
            assertTrue(response.contains("200 OK"));

            // Leer hasta encontrar el cuerpo de la respuesta
            StringBuilder content = new StringBuilder();
            String line;
            boolean bodyFound = false;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty() && !bodyFound) {
                    bodyFound = true;
                    continue;
                }
                if (bodyFound) {
                    content.append(line);
                }
            }

            assertTrue(content.toString().contains(expectedResult),
                    "La respuesta debería contener el resultado esperado: " + expectedResult);
        }
    }


    void testGreetingWithDefaultValue() throws IOException {
        String response = sendGetRequest("/greeting");
        assertTrue(response.contains("Hola World"),
                "Debería usar el valor por defecto 'World'");
    }

    @Test
    void testGreetingWithCustomName() throws IOException {
        String name = URLEncoder.encode("Juan Carlos", StandardCharsets.UTF_8);
        String response = sendGetRequest("/greeting?name=" + name);
        assertTrue(response.contains("Hola Juan Carlos"),
                "Debería usar el nombre proporcionado");
    }

    @Test
    void testPi() throws IOException {
        String response = sendGetRequest("/pi");
        assertTrue(response.contains(String.valueOf(Math.PI)),
                "Debería devolver el valor de PI");
    }


    @Test
    void testSumWithValidInput() throws IOException {
        String response = sendGetRequest("/sum?a=5&b=3");
        assertTrue(response.contains("8"),
                "Debería retornar la suma correcta");
    }

    @Test
    void testSumWithDefaultValues() throws IOException {
        String response = sendGetRequest("/sum");
        assertTrue(response.contains("0"),
                "Debería usar valores por defecto (0)");
    }

    @Test
    void testSumWithInvalidInput() throws IOException {
        String response = sendGetRequest("/sum?a=abc&b=3");
        assertTrue(response.contains("Entrada incorrecta"),
                "Debería manejar entrada inválida");
    }

    @Test
    void testResWithValidInput() throws IOException {
        String response = sendGetRequest("/res?a=10&b=3");
        assertTrue(response.contains("7"),
                "Debería retornar la resta correcta");
    }

    @Test
    void testResWithNegativeResult() throws IOException {
        String response = sendGetRequest("/res?a=3&b=10");
        assertTrue(response.contains("-7"),
                "Debería manejar resultados negativos");
    }

    @Test
    void testMulWithValidInput() throws IOException {
        String response = sendGetRequest("/mul?a=4&b=5");
        assertTrue(response.contains("20"),
                "Debería retornar la multiplicación correcta");
    }

    @Test
    void testMulWithZero() throws IOException {
        String response = sendGetRequest("/mul?a=0&b=5");
        assertTrue(response.contains("0"),
                "Debería manejar multiplicación por cero");
    }


@Test
void testAllControllersAreLoaded() throws IOException {
    // Verificar que ambos controladores están cargados probando una ruta de cada uno
    String greetingResponse = sendGetRequest("/greeting");
    String nombreResponse = sendGetRequest("/nombre");

    assertNotNull(greetingResponse, "GreetingController debería estar cargado");
    assertNotNull(nombreResponse, "AndresController debería estar cargado");
}
}
