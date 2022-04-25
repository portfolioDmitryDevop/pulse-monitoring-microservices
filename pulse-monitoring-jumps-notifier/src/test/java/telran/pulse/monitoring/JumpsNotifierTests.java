package telran.pulse.monitoring;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.dto.SensorJump;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.AssertTrue;


@SpringBootTest
@Import({MailSenderValidatorAutoConfiguration.class, TestChannelBinderConfiguration.class})
class JumpsNotifierTests {

    private static final int SENSOR_ID =500 ;
    private static final int PREVIOUS_VALUE = 20;
    private static final int CURRENT_VALUE = 200;
    private static final String DOCTOR_EMAIL = "doctor@gmail.com";
    private static final String DOCTOR_NAME = "Doctor";
    private static final String PATIENT_NAME = "Patien";
    final SensorJump sensorJump = new SensorJump(SENSOR_ID,
            PREVIOUS_VALUE,
            CURRENT_VALUE);
    final DoctorPatientData doctorPatientData
            = new DoctorPatientData(DOCTOR_EMAIL,
            DOCTOR_NAME,
            PATIENT_NAME);

    @RegisterExtension
    GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("pulse", "12345"));
    @Autowired
    InputDestination producer;
    @MockBean
    DataProviderClient client;
    @Value("${app.mail.subject}")
    String subject;
    @Test
    void test() throws MessagingException {
        Mockito.when(client.getData(SENSOR_ID))
                .thenReturn(doctorPatientData);
        producer.send(new GenericMessage<SensorJump>(sensorJump));
        MimeMessage message = greenMail.getReceivedMessages()[0];
        Assert.assertEquals(DOCTOR_EMAIL,
                message.getAllRecipients()[0].toString()
                );
        Assert.assertEquals(subject, message.getSubject());
        String text = GreenMailUtil.getBody(message);
        Assert.assertTrue(text.contains(DOCTOR_NAME));
        Assert.assertTrue(text.contains(PATIENT_NAME));
        Assert.assertTrue(text.contains(""+PREVIOUS_VALUE));
        Assert.assertTrue(text.contains(""+CURRENT_VALUE));

    }

}
