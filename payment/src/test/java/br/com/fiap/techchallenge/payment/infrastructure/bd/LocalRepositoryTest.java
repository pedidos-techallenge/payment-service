package br.com.fiap.techchallenge.payment.infrastructure.bd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocalRepositoryTest {

    private LocalRepository localRepository;

    @BeforeEach
    void setUp() {
        localRepository = new LocalRepository();
    }

    // createPayment
    @Test
    void testCreatePaymentInsertsNewPayment() {
        localRepository.createPayment("1234");
        assertEquals("CREATED", localRepository.getPaymentStatus("1234"));
    }

    // setPaymentStatus(orderId, status)
    @Test
    void setPaymentStatusUpdatesStatus() {
        localRepository.createPayment("1234");
        localRepository.setPaymentStatus("1234", "APPROVED");
        assertEquals("APPROVED", localRepository.getPaymentStatus("1234"));
    }

    @Test
    void setPaymentStatusThrowsWhenNewStatusIsNull() {
        localRepository.createPayment("1234");
        assertThrows(
                IllegalArgumentException.class,
                () -> localRepository.setPaymentStatus("1234", null)
        );
    }

    @Test
    void setPaymentStatusThrowsWhenOrderIdIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> localRepository.setPaymentStatus(null, "APPROVED")
        );
    }

    @Test
    void setPaymentStatusWhenOrderIdNotFound() {
        String orderId = "1234";
        localRepository.setPaymentStatus(orderId, "APPROVED");
        assertNull(localRepository.getPaymentStatus(orderId));
    }

    // setPaymentStatus(orderId, status, QRCode)
    @Test
    void setPaymentStatusWithQRCodeUpdatesStatusAndQRCode() {
        localRepository.createPayment("1234");
        localRepository.setPaymentStatus("1234", "APPROVED", "QRCode123");
        assertEquals("APPROVED", localRepository.getPaymentStatus("1234"));
        assertEquals("QRCode123", localRepository.getPaymentQRCode("1234"));
    }

    // getPaymentStatus
    @Test
    void getPaymentStatusReturnsStatusSuccessfully() {
        localRepository.createPayment("1234");
        assertEquals("CREATED", localRepository.getPaymentStatus("1234"));
    }
    @Test
    void getPaymentStatusReturnsNullWhenQRCodeIsNull() {
        localRepository.createPayment("1234");
        assertEquals("CREATED", localRepository.getPaymentStatus("1234"));
    }

    @Test
    void getPaymentStatusReturnsNullForNonExistentOrder() {
        assertNull(localRepository.getPaymentStatus("9999"));
    }

    @Test
    void getPaymentStatusThrowsWhenOrderIdIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> localRepository.getPaymentStatus(null)
        );
    }

    // getPaymentQRCode
    @Test
    void getPaymentQRCodeReturnsQRCodeSuccessfully() {
        localRepository.createPayment("1234");
        localRepository.setPaymentStatus("1234", "APPROVED", "QRCode123");
        assertEquals("QRCode123", localRepository.getPaymentQRCode("1234"));
    }

    @Test
    void getPaymentQRCodeReturnsNullWhenQRCodeIsNull() {
        localRepository.createPayment("1234");
        assertNull(localRepository.getPaymentQRCode("1234"));
    }

    @Test
    void getPaymentQRCodeReturnsNullForNonExistentOrder() {
        assertNull(localRepository.getPaymentQRCode("9999"));
    }

    @Test
    void getPaymentQRCodeThrowsWhenOrderIdIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> localRepository.getPaymentQRCode(null)
        );
    }
}
