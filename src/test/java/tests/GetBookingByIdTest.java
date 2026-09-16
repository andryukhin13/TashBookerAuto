package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.Booking;
import core.models.BookingById;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingByIdTest {

    private APIClient apiClient;
    private ObjectMapper objectMapper;

    // Инициализация API клиента перед каждым тестом
    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetBookingById() throws Exception {
        // Выполняем запрос к эндпоинту /booking/:id через APIClient
        Response response = apiClient.getBookingById(1);

        // Проверяем, что статус-код ответа равен 200
        assertThat(response.getStatusCode()).isEqualTo(200);

        // Десериализуем тело ответа в строку bookings
        String responseBody = response.getBody().asString();
        BookingById booking = objectMapper.readValue(responseBody, BookingById.class);

        // Проверяем, что тело ответа не пустое
        assertThat(booking.getFirstname()).isEqualTo("Sally");
        assertThat(booking.getLastname()).isEqualTo("Brown");
        assertThat(booking.getTotalprice()).isEqualTo(111);
        assertThat(booking.isDepositpaid()).isEqualTo(true);
        assertThat(booking.getAdditionalneeds()).isEqualTo("Breakfast");
    }
}
